package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.CouplingIntensityVisitor;
import tr.com.melihaltintas.smelldetector.visitors.ProvidersVisitor;

//CDISP - Coupling Dispersion
//The number of classes in which the operations called from the measured
//operation are defined, divided by CINT
//Used for Intensive Coupling, Dispersed Coupling

public class CouplingDispersionMetric extends MetricVisitor {

	public boolean visit(MethodDeclaration methodDeclaration) {

		if (Modifier.isAbstract(methodDeclaration.getModifiers())) {
			return false;
		}
		CouplingIntensityVisitor couplingIntensityVisitor = new CouplingIntensityVisitor(methodDeclaration);
		ProvidersVisitor providersVisitor = new ProvidersVisitor(methodDeclaration);

		methodDeclaration.accept(couplingIntensityVisitor);
		methodDeclaration.accept(providersVisitor);

		double providerCount = providersVisitor.getMetricResult();
		double CINT = couplingIntensityVisitor.getMetricResult();

		if (CINT == 0)
			metricResult = 1;
		else {
			metricResult = providerCount / CINT;
		}
		return true;
	}

}
