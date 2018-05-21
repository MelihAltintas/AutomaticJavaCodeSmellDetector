package tr.com.melihaltintas.smelldetector.visitors;

import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.eclipse.jdt.core.BindingKey;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.SimpleName;
import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class AccessToForeignDataVisitor extends MetricVisitor {

	private ITypeBinding currentClassBinding;
	private HashSet<String> accessedFields;
	private HashSet<String> getters;
	private HashSet<String> setters;

	public AccessToForeignDataVisitor(ITypeBinding currentClassBinding) {
		accessedFields = new HashSet<>();
		getters = new HashSet<>();
		setters = new HashSet<>();
		this.currentClassBinding = currentClassBinding;
	}



	public boolean visit(SimpleName node) {

		try {
			IBinding binding = node.resolveBinding();

			if (binding instanceof IVariableBinding) { // variable

				IVariableBinding variable = (IVariableBinding) binding;

				if (variable.getDeclaringClass() != null) {
					ITypeBinding variableDeclarationClassBinding = variable.getDeclaringClass(); // accessed variable
																									// class

					boolean isSuperClassOrEqual = Helper.isSuperClassOrEqual(currentClassBinding,
							variableDeclarationClassBinding);

					if (!variableDeclarationClassBinding.isInterface() && !variableDeclarationClassBinding.isEnum()
							&& variable.isField() && !isSuperClassOrEqual && variable.getDeclaringClass().isFromSource()
							&& !Modifier.isFinal(variable.getModifiers())) {

						if (accessedFields.add(variable.getDeclaringClass().getName() + "." + variable.getName())) {

							metricResult++;
						}

					}
				}

			} else if (binding instanceof IMethodBinding) {
				IMethodBinding methodBinding = (IMethodBinding) binding;

				if (methodBinding == null) {
					return false;
				}
				if (methodBinding.getDeclaringClass() == null) {
					return false;
				}

				if (!methodBinding.getDeclaringClass().isEqualTo(currentClassBinding) // outer method
																						// call
						&& methodBinding.getDeclaringClass().isFromSource()) {

					IMethod method = (IMethod) methodBinding.getJavaElement();

					if (method == null) {
					
						return false;
					}

					ICompilationUnit unit = method.getCompilationUnit();

					ASTParser parser = ASTParser.newParser(AST.JLS9);
					parser.setKind(ASTParser.K_COMPILATION_UNIT);
					parser.setSource(unit);
					parser.setResolveBindings(true);
					CompilationUnit cu = (CompilationUnit) parser.createAST(null);

					MethodDeclaration decl = (MethodDeclaration) cu.findDeclaringNode(methodBinding.getKey());

					if (decl == null)
						return false;

					boolean isGetter = AccessorVisitor.isGetter(decl);
					boolean isSetter = AccessorVisitor.isSetter(decl);

					if (isGetter) {
						if (getters.add(methodBinding.getKey())) {
							metricResult++;

						}
					} else if (isSetter) {
						if (setters.add(methodBinding.getKey())) {
							metricResult++;

						}
					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public HashSet<String> getAccessedFields() {
		return accessedFields;
	}

	public HashSet<String> getGetters() {
		return getters;
	}

	public HashSet<String> getSetters() {
		return setters;
	}

}
