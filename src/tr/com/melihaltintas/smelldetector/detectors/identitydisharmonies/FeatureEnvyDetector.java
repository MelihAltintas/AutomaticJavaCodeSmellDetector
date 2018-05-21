package tr.com.melihaltintas.smelldetector.detectors.identitydisharmonies;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.OperationMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;

public class FeatureEnvyDetector extends ASTVisitor {

	private double ATFD;
	private double LAA;
	private double FDP;

	@Override
	public boolean visit(MethodDeclaration methodDeclaration) {
		if (methodDeclaration.isConstructor() && Modifier.isAbstract(methodDeclaration.getModifiers()))
			return false;
		ATFD = MetricCalculator.OperationMetricCalculate(methodDeclaration, OperationMetricEnum.AccessToForeignData);
		LAA = MetricCalculator.OperationMetricCalculate(methodDeclaration,
				OperationMetricEnum.LocalityOfAttributeAccesses);
		FDP = MetricCalculator.OperationMetricCalculate(methodDeclaration, OperationMetricEnum.ForeignDataProviders);

		return true;

	}

	@Override
	public void endVisit(MethodDeclaration methodDeclaration) {

		if (methodDeclaration.isConstructor() && Modifier.isAbstract(methodDeclaration.getModifiers()))
			return;
		
		System.out.println(">>> FeatureEnvyDetector " + " >>> Class :  "
				+ methodDeclaration.resolveBinding().getDeclaringClass().getName().toString() + " >>> Method :  "
				+ methodDeclaration.getName().toString() + " LAA : " + LAA + " ATFD : " + ATFD + " FDP : " + FDP);

		if (ATFD > MetricThresholds.FEW_THRESHOLD && LAA < MetricThresholds.ONE_THIRD_THRESHOLD
				&& FDP <= MetricThresholds.FEW_THRESHOLD) {

			MarkerCreator.addMarker(methodDeclaration, MarkerType.FeatureEnvy);

		}
		super.endVisit(methodDeclaration);
	}

}
