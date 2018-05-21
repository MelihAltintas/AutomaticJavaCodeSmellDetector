package tr.com.melihaltintas.smelldetector.metricfactory;

import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.OperationMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.AccessToForeignDataClassMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.AverageMethodWeightMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.BaseClassOverridingRatioMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.BaseClassUsageRatioMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.LineOfCodeClassMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfAccessorMethodsMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfAddedServicesMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfMethodsMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfProtectedMembersMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfPublicAttributesMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfPublicMethodsMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.TightClassCohesionMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.WeightOfClassMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.WeightedMethodCountMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.AccessToForeignDataMethodMetric;

import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.CouplingDispersionMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.CouplingIntensityMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.CycloMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.ForeignDataProvidersMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.LineOfCodeMethodMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.LocalityOfAttributeAccessesMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.MaxNestingMetric;
import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.NumberOfAccessedVariablesMetric;

public class MetricFactory {

	private static final MetricFactory INSTANCE = new MetricFactory();

	private MetricFactory() {
	}

	public static MetricFactory getInstance() {
		return INSTANCE;
	}

	public MetricVisitor getClassMetric(ClassMetricEnum classMetric) {
		switch (classMetric) {
		case WeightedMethodCount:
			return new WeightedMethodCountMetric();
		case AccessToForeignData:
			return new AccessToForeignDataClassMetric();
		case TightClassCohesion:
			return new TightClassCohesionMetric();
		case NumberOfPublicAttributes:
			return new NumberOfPublicAttributesMetric();
		case NumberOfAccessorMethods:
			return new NumberOfAccessorMethodsMetric();
		case WeightOfClass:
			return new WeightOfClassMetric();
		case NumberOfProtectedMembersMetric:
			return new NumberOfProtectedMembersMetric();
		case NumberOfMethodsMetric:
			return new NumberOfMethodsMetric();
		case AverageMethodWeightMetric:
			return new AverageMethodWeightMetric();
		case BaseClassOverridingRatioMetric:
			return new BaseClassOverridingRatioMetric();
		case BaseClassUsageRatioMetric:
			return new BaseClassUsageRatioMetric();
		case NumberOfAddedServicesMetric:
			return new NumberOfAddedServicesMetric();
		case NumberOfPublicMethodsMetric:
			return new NumberOfPublicMethodsMetric();
		case LineOfCode:
			return new LineOfCodeClassMetric();
		default:
			return null;
		}

	}

	public MetricVisitor getOperationMetric(OperationMetricEnum operationMetric) {
		switch (operationMetric) {
		case LocalityOfAttributeAccesses:
			return new LocalityOfAttributeAccessesMetric();
		case ForeignDataProviders:
			return new ForeignDataProvidersMetric();
		case AccessToForeignData:
			return new AccessToForeignDataMethodMetric();
		case Cyclo:
			return new CycloMetric();
		case LineOfCode:
			return new LineOfCodeMethodMetric();
		case NumberOfAccessedVariables:
			return new NumberOfAccessedVariablesMetric();
		case MaxNestingLevel:
			return new MaxNestingMetric();
		case CouplingIntensityMetric:
			return new CouplingIntensityMetric();
		case CouplingDispersionMetric:
			return new CouplingDispersionMetric();
		default:
			return null;
		}

	}
}
