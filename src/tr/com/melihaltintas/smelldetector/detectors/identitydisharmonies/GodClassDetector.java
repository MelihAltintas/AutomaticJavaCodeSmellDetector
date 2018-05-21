package tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;

public class GodClassDetector extends ASTVisitor {

	private double WMC;
	private double TCC;
	private double ATFD;

	public boolean visit(TypeDeclaration typeDeclaration) {

		WMC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.WeightedMethodCount);

		TCC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.TightClassCohesion);

		ATFD = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.AccessToForeignData);

		return true;
	}

	@Override
	public void endVisit(TypeDeclaration typeDeclaration) {

		if (ATFD > MetricThresholds.FEW_THRESHOLD && WMC >= MetricThresholds.WMC_VERY_HIGH_THRESHOLD
				&& TCC < MetricThresholds.ONE_THIRD_THRESHOLD) {
			
			MarkerCreator.addMarker(typeDeclaration,MarkerType.GodClass);

		}
		super.endVisit(typeDeclaration);
	}

}
