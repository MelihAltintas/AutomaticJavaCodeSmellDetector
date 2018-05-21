package tr.com.melihaltintas.smelldetector.detectors.collaborationdisharmonies;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.OperationMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;

public class DispersedCouplingDetector extends ASTVisitor {

	private double CINT;

	private double CDISP;

	private double MAXNESTING;

	public boolean visit(MethodDeclaration methodDeclaration) {
		if (methodDeclaration.isConstructor() && Modifier.isAbstract(methodDeclaration.getModifiers()))
			return false;
		
		CINT = MetricCalculator.OperationMetricCalculate(methodDeclaration,
				OperationMetricEnum.CouplingIntensityMetric);

		CDISP = MetricCalculator.OperationMetricCalculate(methodDeclaration,
				OperationMetricEnum.CouplingDispersionMetric);

		MAXNESTING = MetricCalculator.OperationMetricCalculate(methodDeclaration, OperationMetricEnum.MaxNestingLevel);

		return true;
	}

	@Override
	public void endVisit(MethodDeclaration methodDec) {
		if (methodDec.isConstructor() && Modifier.isAbstract(methodDec.getModifiers()))
			return;
		try {
		System.out.println(">>> DispersedCouplingDetector >>> " + " Method :  " + methodDec.getName().toString()
				+ " >>> Class :  " + methodDec.resolveBinding().getDeclaringClass().getName() + "  CINT : " + CINT
				+ "  CDISP : " + CDISP + "  MAXNESTING:  " + MAXNESTING);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		if (MethodCallsFewMethodsFromEachOfLargeNumberOfUnrelatedClasses() && MethodHasFewNestedConditionals()) {
			MarkerCreator.addMarker(methodDec,MarkerType.DispersedCoupling);
		}
	}

	public boolean MethodCallsFewMethodsFromEachOfLargeNumberOfUnrelatedClasses() {

		if (CINT > MetricThresholds.SHORT_MEMORY_CAP && CDISP >= MetricThresholds.HALF) {
			return true;
		}
		return false;
	}

	public boolean MethodHasFewNestedConditionals() {
		return MAXNESTING > MetricThresholds.SHALLOW;

	}
}