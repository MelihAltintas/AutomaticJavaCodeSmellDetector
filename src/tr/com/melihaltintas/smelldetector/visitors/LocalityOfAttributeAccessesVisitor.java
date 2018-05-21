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

public class LocalityOfAttributeAccessesVisitor extends MetricVisitor {

	private MethodDeclaration currentMethod;
	private HashSet<String> accessedOwnFields;
	private HashSet<String> accessedForeignFields;
	private int accessedForeignData;
	private int accessedOwnData;

	public LocalityOfAttributeAccessesVisitor(MethodDeclaration currentMethod) {
		accessedOwnFields = new HashSet<>();
		accessedForeignFields = new HashSet<>();
		this.currentMethod = currentMethod;

	}

	@Override
	public boolean visit(SimpleName node) {

		IBinding binding = node.resolveBinding();

		if (binding instanceof IVariableBinding) { // variable

			IVariableBinding variable = (IVariableBinding) binding;

			if (variable.getDeclaringClass() != null) {
				ITypeBinding variableDeclarationClassBinding = variable.getDeclaringClass(); // accessed variable class

				boolean isSuperClassOrEqual = Helper.isSuperClassOrEqual(
						currentMethod.resolveBinding().getDeclaringClass(), variableDeclarationClassBinding);

				if (!variableDeclarationClassBinding.isInterface() && !variableDeclarationClassBinding.isEnum()
						&& variable.isField() && variable.getDeclaringClass().isFromSource()) {

					if (isSuperClassOrEqual) {
						if (accessedOwnFields.add(variable.getKey())) {
							accessedOwnData++;
						}
					} else {
						if (accessedForeignFields.add(variable.getKey())) {
							accessedForeignData++;
						}
					}
				}

			}

		}

		else if (binding instanceof IMethodBinding)

		{ // is method invocation

			IMethodBinding methodBinding = (IMethodBinding) binding;

			if (methodBinding.getDeclaringClass().isFromSource()) {

				ICompilationUnit unit = (ICompilationUnit) binding.getJavaElement()
						.getAncestor(IJavaElement.COMPILATION_UNIT);

				ASTParser parser = ASTParser.newParser(AST.JLS9);
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				parser.setSource(unit);
				parser.setResolveBindings(true);
				CompilationUnit cu = (CompilationUnit) parser.createAST(null);

				MethodDeclaration decl = (MethodDeclaration) cu.findDeclaringNode(binding.getKey());
				boolean isGetter = AccessorVisitor.isGetter(decl);
				boolean isSetter = AccessorVisitor.isSetter(decl);

				if (isGetter || isSetter) {

					if (methodBinding.getDeclaringClass()
							.isEqualTo(currentMethod.resolveBinding().getDeclaringClass())) {
						if (accessedOwnFields.add(methodBinding.getKey())) {
							accessedOwnData++;
						}
					} else {
						if (accessedForeignFields.add(methodBinding.getKey())) {
							accessedForeignData++;
						}
					}

				}

			}
		}
		return true;
	}

	@Override
	public double getMetricResult() {

		if (accessedOwnData == 0 && accessedOwnData + accessedForeignData == 0)
			return 1;
		return (double) accessedOwnData / (double) (accessedOwnData + accessedForeignData);
	}

	public MethodDeclaration getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(MethodDeclaration currentMethod) {
		this.currentMethod = currentMethod;
	}

	public HashSet<String> getAccessedOwnFields() {
		return accessedOwnFields;
	}

	public HashSet<String> getAccessedForeignFields() {
		return accessedForeignFields;
	}

}
