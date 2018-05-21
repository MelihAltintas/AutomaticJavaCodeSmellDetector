package tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies;


import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.OperationMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class BrainMethodDetector extends MetricVisitor {

	private double CYCLO;
	private double MAXNESTING;
	private double NOAV;
	private double LOC;

	@Override
	public boolean visit(MethodDeclaration methodDeclaration) {
		if (methodDeclaration.isConstructor())
			return false;

		CYCLO = MetricCalculator.OperationMetricCalculate(methodDeclaration, OperationMetricEnum.Cyclo);
		LOC = MetricCalculator.OperationMetricCalculate(methodDeclaration, OperationMetricEnum.LineOfCode);
		NOAV = MetricCalculator.OperationMetricCalculate(methodDeclaration,
				OperationMetricEnum.NumberOfAccessedVariables);
		MAXNESTING = MetricCalculator.OperationMetricCalculate(methodDeclaration, OperationMetricEnum.MaxNestingLevel);
		return true;

	}

	@Override
	public void endVisit(MethodDeclaration methodDeclaration) {
		
		if (methodDeclaration.isConstructor())
			return ;
		
		System.out.println(">>> BrainMethodDetector >>> " + " Method :  " + methodDeclaration.getName().toString()
				+ " CYCLO : " + CYCLO + " LOC : " + LOC + " NOAV : " + NOAV + " MAXNESTING : " + MAXNESTING);

		if (MethodIsExcessivelyLarge() && MethodHasManyConditionalBranches() && MethodHasDeepNesting()
				&& MethodUsesManyVariables()) {
			MarkerCreator.addMarker(methodDeclaration, MarkerType.BrainMethod);
			metricResult++;
		}
		super.endVisit(methodDeclaration);
	}

	public boolean MethodIsExcessivelyLarge() {
		if (LOC > MetricThresholds.LOC_HIGH / 2) {
			return true;
		}
		return false;
	}

	public boolean MethodHasManyConditionalBranches() {
		if (CYCLO >= MetricThresholds.CYCLO_HIGH) {
			return true;
		}
		return false;
	}

	public boolean MethodHasDeepNesting() {
		if (MAXNESTING >= MetricThresholds.SEVERAL) {
			return true;
		}
		return false;
	}

	public boolean MethodUsesManyVariables() {
		if (NOAV > MetricThresholds.NOAV_MANY) {
			return true;
		}
		return false;
	}

}
