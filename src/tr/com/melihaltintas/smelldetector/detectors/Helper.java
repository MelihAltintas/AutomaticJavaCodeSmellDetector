package tr.com.melihaltintas.smelldetector.detectors;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Helper {

	public static boolean isSuperClassOrEqual(ITypeBinding subClass, ITypeBinding superClass) {
		ITypeBinding currentType = subClass;
		if (subClass.isEqualTo(superClass))
			return true;

		while (currentType.getSuperclass() != null) {
			if (currentType.getSuperclass().isEqualTo(superClass)) {
				return true;
			}
			currentType = currentType.getSuperclass();
		}
		return false;
	}

	public static CompilationUnit getCompilationUnit(ICompilationUnit Iunit) {

		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(Iunit);
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		return cu;

	}

	public static boolean isMethodOverridden(MethodDeclaration methodDeclaration) {

		IMethodBinding methodBinding = methodDeclaration.resolveBinding();
		ITypeBinding methodDeclaringClass = methodDeclaration.resolveBinding().getDeclaringClass();

		while (methodDeclaringClass != null) {
			ITypeBinding superClass = methodDeclaringClass.getSuperclass();
			if (superClass == null || !superClass.isFromSource())
				break;

			for (IMethodBinding superClassMethod : superClass.getDeclaredMethods()) {
				if (methodBinding.getName().equals(superClassMethod.getName())) {
					if (methodBinding.getParameterTypes().equals(superClassMethod.getParameterTypes())) {
						return true;
					}

				}
			}
			methodDeclaringClass = methodDeclaringClass.getSuperclass();

		}
		return false;
	}

	public static boolean isCandidateForRefusedParentBequestOrTraditionBreaker(TypeDeclaration typeDeclaration) {

		ITypeBinding superClass = typeDeclaration.resolveBinding().getSuperclass();

		if (superClass == null) {
			return false;
		}

		if (!superClass.isFromSource() || superClass.isInterface()) {

			return false;
		}

		return true;
	}

	public static int getStartPosition(ASTNode node) {
		int startPosition = 0;
		int nodePos = 0;
		
		if (node instanceof MethodDeclaration) {
			nodePos = ((MethodDeclaration) node).getName().getStartPosition();
			startPosition = ((CompilationUnit) node.getRoot()).getLineNumber(nodePos);
		} else if (node instanceof TypeDeclaration){
			nodePos = ((TypeDeclaration) node).getName().getStartPosition();
			startPosition = ((CompilationUnit) node.getRoot()).getLineNumber(nodePos);
		}else {
			startPosition = ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition());
		}
		
		
		
		
		return startPosition;
	}

	public static int getEndPosition(ASTNode node) {

		int endPosition = ((CompilationUnit) node.getRoot()).getLineNumber(node.getStartPosition() + node.getLength());
		return endPosition;
	}

}
