package tr.com.melihaltintas.smelldetector.metrics;

import tr.com.melihaltintas.smelldetector.metricfactory.MetricFactory;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.OperationMetricEnum;

public class MetricCalculator {


	public static double ClassMetricCalculate(TypeDeclaration type, ClassMetricEnum metric) {
		MetricVisitor metricVisitor = MetricFactory.getInstance().getClassMetric(metric);
		type.accept(metricVisitor);
		return metricVisitor.getMetricResult();
	}
	
	public static double OperationMetricCalculate(MethodDeclaration method, OperationMetricEnum metric) {
		MetricVisitor metricVisitor = MetricFactory.getInstance().getOperationMetric(metric);
		method.accept(metricVisitor);
		return metricVisitor.getMetricResult();
	}
	
}
