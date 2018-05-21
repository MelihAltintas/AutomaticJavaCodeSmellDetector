package tr.com.melihaltintas.smelldetector.metrics.classmetrics;


import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//AMW - Average Method Weight
//The average static complexity of all methods in a class. McCabe’s cyclomatic
//number is used to quantify the method’s complexity 
//Used for Refused Parent Bequest, Tradition Breaker

public class AverageMethodWeightMetric extends MetricVisitor {

	public boolean visit(TypeDeclaration typeDeclaration) {

		double WMC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.WeightedMethodCount);
		double NOM = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.NumberOfMethodsMetric);

		if (NOM == 0) {
			metricResult = 0;
			return true;
		} else {
			metricResult = WMC / NOM;

		}
		return true;

	}

}
