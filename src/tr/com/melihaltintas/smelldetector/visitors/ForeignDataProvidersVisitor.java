package tr.com.melihaltintas.smelldetector.visitors;

import java.util.HashSet;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//FDP - Foreign Data Providers
//The number of classes in which the attributes accessed — in conformity with
//the ATFD metric — are defined
//Used for Feature Envy(84)
public class ForeignDataProvidersVisitor extends MetricVisitor {

	private MethodDeclaration currentMethod;
	private HashSet<String> accessedClasses;

	public ForeignDataProvidersVisitor(MethodDeclaration currentMethod) {
		this.currentMethod = currentMethod;
		accessedClasses = new HashSet<>();

	}

	public boolean visit(SimpleName node) {

		IBinding binding = node.resolveBinding();

		IMethodBinding currentMethodBinding = currentMethod.resolveBinding();
		if (currentMethodBinding == null)
			return false;
		ITypeBinding currentClassBinding = currentMethodBinding.getDeclaringClass();

		if (currentClassBinding == null)
			return false;

		if (binding instanceof IVariableBinding) { // variable

			IVariableBinding variable = (IVariableBinding) binding;

			if (variable.getDeclaringClass() != null) {
				ITypeBinding variableDeclarationClassBinding = variable.getDeclaringClass(); // accessed variable class
				// method class
				boolean isSuperClassOrEqual = Helper.isSuperClassOrEqual(currentClassBinding,
						variableDeclarationClassBinding);

				if (!variableDeclarationClassBinding.isInterface() && !variableDeclarationClassBinding.isEnum()
						&& variable.isField() && !isSuperClassOrEqual && variable.getDeclaringClass().isFromSource()) {
					
					if (accessedClasses.add(variableDeclarationClassBinding.getQualifiedName())) {
						metricResult++;
					}

				}
			}

		}

		else if (binding instanceof IMethodBinding) { // is method invocation

			IMethodBinding methodBinding = (IMethodBinding) binding;

			if (!methodBinding.getDeclaringClass().isEqualTo(currentClassBinding) // outer method
																				// call
					&& methodBinding.getDeclaringClass().isFromSource()) {

				ICompilationUnit unit = (ICompilationUnit) binding.getJavaElement()
						.getAncestor(IJavaElement.COMPILATION_UNIT);

				ASTParser parser = ASTParser.newParser(AST.JLS9);
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				parser.setSource(unit);
				parser.setResolveBindings(true);
				CompilationUnit cu = (CompilationUnit) parser.createAST(null);

				MethodDeclaration decl = (MethodDeclaration) cu.findDeclaringNode(binding.getKey()); // find the
																										// outer
																										// method
																										// declaration
				boolean isGetter = AccessorVisitor.isGetter(decl);
				boolean isSetter = AccessorVisitor.isSetter(decl);

				if (isGetter || isSetter) {
					if (accessedClasses.add(methodBinding.getDeclaringClass().getQualifiedName())) {

						metricResult++;
					}
				}
			}
		}
		return true;
	}

	public HashSet<String> getAccessedClasses() {
		return accessedClasses;
	}
	
	
}
