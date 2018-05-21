package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.CycloVisitor;


public class CycloMetric extends MetricVisitor {
	
	public boolean visit(MethodDeclaration methodDeclaration) {

		CycloVisitor cycloVisitor = new CycloVisitor(methodDeclaration);

		methodDeclaration.accept(cycloVisitor);
		metricResult += cycloVisitor.getMetricResult();

		return true;
	}

}
