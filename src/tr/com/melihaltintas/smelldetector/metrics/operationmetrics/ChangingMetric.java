package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import java.util.HashSet;

import org.eclipse.core.commands.ITypedParameter;
import org.eclipse.core.runtime.NullProgressMonitor;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.internal.corext.callhierarchy.CallHierarchy;
import org.eclipse.jdt.internal.corext.callhierarchy.MethodWrapper;
import tr.com.melihaltintas.smelldetector.detectors.Helper;

//CM - Changing Methods
//The number of distinct methods that call the measured method 
//Used for Shotgun Surgery

//CC - Changing Classes
//The number of classes in which the methods that call the measured method
//Used for Shotgun Surgery

@SuppressWarnings("restriction")
public class ChangingMetric extends ASTVisitor {

	private HashSet<String> callerMethods;
	private HashSet<String> callerClasses;

	private double CM; // CHANGING METHODS

	private double CC; // CHANGING CLASSES

	public ChangingMetric() {
		callerMethods = new HashSet<>();
		callerClasses = new HashSet<>();
	}

	public boolean visit(MethodDeclaration methodDeclaration) {
		try {
			if (methodDeclaration.resolveBinding() == null)
				return false;

			IMethodBinding binding = methodDeclaration.resolveBinding();
			IMethod method = (IMethod) binding.getJavaElement(); // SystemInfo.getMethod(binding);
			if (method == null) {
				System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!! ChangingMetric");
				return false;

			}
			HashSet<IMethod> callers = getCallersOf(method);
			for (IMethod caller : callers) {
				IType callerClass = caller.getDeclaringType();
				
				ITypeBinding currentClass =  methodDeclaration.resolveBinding().getDeclaringClass();
				
				while(currentClass != null) {
					if(currentClass.getKey().equals(callerClass.getKey())) {
						return true;
					}
					currentClass = currentClass.getSuperclass();
				}
				
				if (callerClasses.add(callerClass.getKey())) {
					CC++;
				}
				if (callerMethods.add(caller.getKey())) {
					CM++;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
		return true;
	}

	public HashSet<IMethod> getCallersOf(IMethod m) {

		CallHierarchy callHierarchy = CallHierarchy.getDefault();

		IMember[] members = { m };

		MethodWrapper[] methodWrappers = callHierarchy.getCallerRoots(members);
		HashSet<IMethod> callers = new HashSet<IMethod>();
		for (MethodWrapper mw : methodWrappers) {
			MethodWrapper[] mw2 = mw.getCalls(new NullProgressMonitor());
			HashSet<IMethod> temp = getIMethods(mw2);
			callers.addAll(temp);
		}

		return callers;
	}

	HashSet<IMethod> getIMethods(MethodWrapper[] methodWrappers) {
		HashSet<IMethod> c = new HashSet<IMethod>();
		for (MethodWrapper m : methodWrappers) {
			IMethod im = getIMethodFromMethodWrapper(m);
			if (im != null) {
				c.add(im);
			}
		}
		return c;
	}

	IMethod getIMethodFromMethodWrapper(MethodWrapper m) {
		try {
			IMember im = m.getMember();
			if (im.getElementType() == IJavaElement.METHOD) {
				return (IMethod) m.getMember();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HashSet<String> getCallerMethods() {
		return callerMethods;
	}

	public HashSet<String> getCallerClasses() {
		return callerClasses;
	}

	public double getCM() {
		return CM;
	}

	public double getCC() {
		return CC;
	}

}
