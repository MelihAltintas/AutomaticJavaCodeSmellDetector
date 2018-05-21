package tr.com.melihaltintas.smelldetector.metricfactory;

public class Enums {
	
	public enum ClassMetricEnum {
		WeightedMethodCount,
		AccessToForeignData,
		TightClassCohesion,
		NumberOfPublicAttributes,
		NumberOfAccessorMethods,
		WeightOfClass,
		NumberOfProtectedMembersMetric,
		NumberOfMethodsMetric,
		AverageMethodWeightMetric,
		BaseClassOverridingRatioMetric,
		BaseClassUsageRatioMetric,
		NumberOfAddedServicesMetric,
		NumberOfPublicMethodsMetric,
		LineOfCode,
	}
	
	public enum OperationMetricEnum {
		LocalityOfAttributeAccesses,
		ForeignDataProviders,
		AccessToForeignData,
		Cyclo,
		LineOfCode,
		NumberOfAccessedVariables,
		MaxNestingLevel,
		CouplingIntensityMetric,
		CouplingDispersionMetric,
	}
}
