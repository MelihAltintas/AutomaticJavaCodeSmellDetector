package tr.com.melihaltintas.smelldetector.detectors.classificationdisharmonies;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;

public class RefusedParentBequestDetector extends ASTVisitor {

	private double NProtM;
	private double BUR;
	private double BOvR;
	private double AMW;
	private double WMC;
	private double NOM;

	public boolean visit(TypeDeclaration typeDeclaration) {

		if (!Helper.isCandidateForRefusedParentBequestOrTraditionBreaker(typeDeclaration)) {
			return false;
		}

		NProtM = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.NumberOfProtectedMembersMetric);
		WMC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.WeightedMethodCount);
		NOM = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.NumberOfMethodsMetric);
		AMW = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.AverageMethodWeightMetric);
		BOvR = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.BaseClassOverridingRatioMetric);
		BUR = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.BaseClassUsageRatioMetric);

		return true;
	}

	@Override
	public void endVisit(TypeDeclaration typeDeclaration) {
		if (!Helper.isCandidateForRefusedParentBequestOrTraditionBreaker(typeDeclaration)) {
			return;
		}
		
		if (((NProtM > MetricThresholds.FEW_THRESHOLD && BUR < MetricThresholds.ONE_THIRD_THRESHOLD)
				|| BOvR < MetricThresholds.ONE_THIRD_THRESHOLD)
				&& ((AMW > MetricThresholds.AMW_AVARAGE || WMC > MetricThresholds.WMC_AVARAGE)
						&& NOM > MetricThresholds.NOM_AVARAGE)) {
			MarkerCreator.addMarker(typeDeclaration, MarkerType.RefusedParentBequest);
		}

		System.out.println(">>> RefusedParentBequestDetector " + " >>> Class :  " + typeDeclaration.getName().toString()
				+ "  NProtM : " + NProtM + "  WMC : " + WMC + "  NOM : " + NOM + "  AMW : " + AMW + "  BOvR : " + BOvR
				+ "  BUR : " + BUR);

	}

}
