package tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;

public class DataClassDetector extends ASTVisitor {

	private double NOPA;
	private double NOAM;
	private double WMC;
	private double WOC;

	public boolean visit(TypeDeclaration typeDeclaration) {

		WOC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.WeightOfClass);

		WMC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.WeightedMethodCount);

		NOPA = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.NumberOfPublicAttributes);

		NOAM = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.NumberOfAccessorMethods);

		return true;
	}

	@Override
	public void endVisit(TypeDeclaration typeDeclaration) {

		System.out.println(">>> DataClassDetector " + " >>> Class :  " + typeDeclaration.getName().toString()
				+ " WOC : " + WOC + " NOPA : " + NOPA + " NOAM : " + NOAM + " WMC : " + WMC);

		if (InterfaceOfClassRevealsDataRatherThanOfferingServices()) {
			if (ClassRevealsManyAttributesAndNotComplex()) {
				MarkerCreator.addMarker(typeDeclaration, MarkerType.DataClass);
			}

		}

		super.endVisit(typeDeclaration);
	}

	private boolean ClassRevealsManyAttributesAndNotComplex() {
		return (NOPA + NOAM > MetricThresholds.FEW_THRESHOLD
				&& WMC < MetricThresholds.WMC_HIGH_THRESHOLD)
				|| (NOPA + NOAM > MetricThresholds.ACCESSORACCESS_PLUS_FIELDACCESS_MANY_THRESHOLD
						&& WMC < MetricThresholds.WMC_VERY_HIGH_THRESHOLD);

	}

	private boolean InterfaceOfClassRevealsDataRatherThanOfferingServices() {

		return WOC < MetricThresholds.ONE_THIRD_THRESHOLD;
	}

}
