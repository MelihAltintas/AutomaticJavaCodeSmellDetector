package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.NumberOfAccessedVariablesVisitor;


public class NumberOfAccessedVariablesMetric extends MetricVisitor{

	public boolean visit(MethodDeclaration methodDeclaration) {

		NumberOfAccessedVariablesVisitor numberOfAccessedVariablesVisitor = new NumberOfAccessedVariablesVisitor(methodDeclaration);

		methodDeclaration.accept(numberOfAccessedVariablesVisitor);
		metricResult += numberOfAccessedVariablesVisitor.getMetricResult();

		return true;
	}
}
