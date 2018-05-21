package tr.com.melihaltintas.smelldetector.visitors;

import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class AccessorVisitor extends MetricVisitor {

	private HashSet<String> getters;
	private HashSet<String> setters;

	public AccessorVisitor() {
		getters = new HashSet<>();
		setters = new HashSet<>();
	}

	public boolean visit(MethodDeclaration methodDeclaration) {

		if (Modifier.isPublic(methodDeclaration.getModifiers()) == false
				|| Modifier.isStatic(methodDeclaration.getModifiers())
				|| Modifier.isFinal(methodDeclaration.getModifiers()) || methodDeclaration.isConstructor()) {

			return false;
		}

		if (isGetter(methodDeclaration)) {

			if (getters.add(methodDeclaration.resolveBinding().getKey())) {
				metricResult++;
			}
		} else if (isSetter(methodDeclaration)) {

			if (setters.add(methodDeclaration.resolveBinding().getKey())) {
				metricResult++;
			}
		}

		return true;
	}

	public static boolean isSetter(MethodDeclaration methodDeclaration) {

		if(methodDeclaration == null) return false;
		
		if (Modifier.isPublic(methodDeclaration.getModifiers()) == false
				|| Modifier.isStatic(methodDeclaration.getModifiers())
				|| Modifier.isFinal(methodDeclaration.getModifiers()) || methodDeclaration.isConstructor()) {

			return false;
		}
		
		if (methodDeclaration.parameters().size() == 1) {
			AssignmentVisitorForSetterMethods AssignmentVisitorForSetterMethods = new AssignmentVisitorForSetterMethods(
					methodDeclaration.resolveBinding().getDeclaringClass());
			methodDeclaration.accept(AssignmentVisitorForSetterMethods);
			return AssignmentVisitorForSetterMethods.isSetter();
		}
		return false;
	}

	public static boolean isGetter(MethodDeclaration methodDeclaration) {

		if(methodDeclaration == null) return false;
		if (Modifier.isPublic(methodDeclaration.getModifiers()) == false
				|| Modifier.isStatic(methodDeclaration.getModifiers())
				|| Modifier.isFinal(methodDeclaration.getModifiers()) || methodDeclaration.isConstructor()) {

			return false;
		}
		
		if (methodDeclaration.parameters().size() == 0) {

			ReturnStatementVisitorForGetterMethods returnStatementVisitor = new ReturnStatementVisitorForGetterMethods(
					methodDeclaration.resolveBinding().getDeclaringClass());
			methodDeclaration.accept(returnStatementVisitor);
			return returnStatementVisitor.isReturnStatementField();
		}
		return false;
	}

	public HashSet<String> getGetters() {
		return getters;
	}

	public HashSet<String> getSetters() {
		return setters;
	}

}
