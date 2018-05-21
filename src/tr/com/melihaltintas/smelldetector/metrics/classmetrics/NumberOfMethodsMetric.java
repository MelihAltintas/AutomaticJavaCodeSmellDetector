package tr.com.melihaltintas.smelldetector.metrics.classmetrics;


import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//NOM - Number of Methods
//The number of methods of a class
//Used for Refused Parent Bequest, Tradition Breaker

public class NumberOfMethodsMetric extends MetricVisitor {

	public boolean visit(TypeDeclaration typeDeclaration) {

		metricResult =  typeDeclaration.getMethods().length;

		return true;
	}

}
