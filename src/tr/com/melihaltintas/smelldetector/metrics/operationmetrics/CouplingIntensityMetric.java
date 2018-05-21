package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.CouplingIntensityVisitor;

//CINT - Coupling Intensity
//Definition The number of distinct operations called by the measured operation
//Used for Intensive Coupling, Dispersed Coupling

public class CouplingIntensityMetric extends MetricVisitor {

	public boolean visit(MethodDeclaration methodDeclaration) {

		if (Modifier.isAbstract(methodDeclaration.getModifiers())) {
			return false;
		}
		CouplingIntensityVisitor couplingIntensityVisitor = new CouplingIntensityVisitor(methodDeclaration);

		methodDeclaration.accept(couplingIntensityVisitor);
		metricResult += couplingIntensityVisitor.getMetricResult();

		return true;
	}
}
