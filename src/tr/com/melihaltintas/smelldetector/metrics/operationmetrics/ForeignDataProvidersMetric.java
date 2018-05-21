package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.ForeignDataProvidersVisitor;

public class ForeignDataProvidersMetric extends MetricVisitor {

	public boolean visit(MethodDeclaration methodDeclaration) {

		ForeignDataProvidersVisitor foreignDataProvidersVisitor = new ForeignDataProvidersVisitor(methodDeclaration);

		methodDeclaration.accept(foreignDataProvidersVisitor);
		metricResult += foreignDataProvidersVisitor.getMetricResult();
		System.out.println("*** Fooreign data providers : "+ foreignDataProvidersVisitor.getAccessedClasses());
		return true;
	}


}
