package tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.OperationMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;

public class BrainClassDetector extends ASTVisitor {

	private double CLOC; // Total size of methods in class
	private double WMC;
	private double TCC;

	private double brainMethodCount;

	public BrainClassDetector (CompilationUnit compilationUnit) {
		BrainMethodDetector brainMethodDetector = new BrainMethodDetector();
		compilationUnit.accept(brainMethodDetector);
		brainMethodCount = brainMethodDetector.getMetricResult();
	}
	public boolean visit(TypeDeclaration typeDeclaration) {



		WMC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.WeightedMethodCount);

		TCC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.TightClassCohesion);

		for (MethodDeclaration methodDeclaration : typeDeclaration.getMethods()) {
			CLOC += MetricCalculator.OperationMetricCalculate(methodDeclaration, OperationMetricEnum.LineOfCode);
		}

		return true;
	}

	@Override
	public void endVisit(TypeDeclaration typeDeclaration) {

		System.out.println(">>> BrainClassDetector " + " >>> Class :  " + typeDeclaration.getName().toString()
				+ " WMC : " + WMC + " CLOC [Total size of methods in class]: " + CLOC + " TCC : " + TCC
				+ " BRAIN METHOD COUNT " + brainMethodCount);

		if (ClassIsVeryComplexAndNonCohesive() && (ClassContainsMoreThanOneBrainMethodAndVeryLarge()
				|| ClassContainsOnlyOneBrainMethodButExtremelyLargeAndComplex())) {

			MarkerCreator.addMarker(typeDeclaration, MarkerType.BrainClass);

		}
		super.endVisit(typeDeclaration);
	}

	public boolean ClassContainsMoreThanOneBrainMethodAndVeryLarge() {
		if (brainMethodCount > 1 && CLOC >= MetricThresholds.CLOC_VERY_HIGH ) {
			return true;
		}
		return false;
	}

	public boolean ClassContainsOnlyOneBrainMethodButExtremelyLargeAndComplex() {
		if (brainMethodCount == 1 && CLOC >= 2* MetricThresholds.CLOC_VERY_HIGH  && WMC >= 2 * MetricThresholds.WMC_VERY_HIGH_THRESHOLD) {
			return true;
		}
		return false;
	}

	public boolean ClassIsVeryComplexAndNonCohesive() {
		if (WMC >= MetricThresholds.WMC_VERY_HIGH_THRESHOLD && TCC < MetricThresholds.HALF) {
			return true;
		}
		return false;
	}
}
