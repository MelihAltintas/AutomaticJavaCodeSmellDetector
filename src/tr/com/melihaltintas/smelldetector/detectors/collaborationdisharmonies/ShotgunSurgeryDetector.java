package tr.com.melihaltintas.smelldetector.detectors.collaborationdisharmonies;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.ChangingMetric;

public class ShotgunSurgeryDetector extends ASTVisitor {

	private double CM; // CHANGING METHODS

	private double CC; // CHANGING CLASSES

	public boolean visit(MethodDeclaration methodDeclaration) {
		if (methodDeclaration.isConstructor() && Modifier.isAbstract(methodDeclaration.getModifiers()))
			return false;
		ChangingMetric changingMetricVisitor = new ChangingMetric();
		methodDeclaration.accept(changingMetricVisitor);

		CM = changingMetricVisitor.getCM();

		CC = changingMetricVisitor.getCC();

		System.out.println("******** outer caller methods of " + methodDeclaration.getName().toString() + " == "
				+ changingMetricVisitor.getCallerMethods());

		System.out.println("******** outer caller classes of " + methodDeclaration.getName().toString() + " == "
				+ changingMetricVisitor.getCallerClasses());
		return true;
	}

	@Override
	public void endVisit(MethodDeclaration methodDec) {
		if (methodDec.isConstructor() && Modifier.isAbstract(methodDec.getModifiers()))
			return ;
		System.out.println(
				">>> ShotgunSurgeryDetector >>> " + " Method :  " + methodDec.getName().toString() + " >>> Class :  "
						+ methodDec.resolveBinding().getDeclaringClass().getName() + "  CM : " + CM + "  CC : " + CC);

		if (OperationIsCalledByTooManyOtherMethods() && IncomingCallsAreFromManyClasses()) {

			MarkerCreator.addMarker(methodDec, MarkerType.ShotgunSurgery);
		}

	}

	public boolean OperationIsCalledByTooManyOtherMethods() {

		if (CM > MetricThresholds.SHORT_MEMORY_CAP) {
			return true;
		}

		return false;
	}

	public boolean IncomingCallsAreFromManyClasses() {
		if (CC > MetricThresholds.CC_MANY) {
			return true;
		}
		return false;
	}

}
