package tr.com.melihaltintas.smelldetector.metrics.classmetrics;


import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.AccessorVisitor;


// NOAM - Number of Accessor Methods
// The number of the non-inherited accessor (getter and setter) methods of a class [public]  [no constructor no static no abstract]
// Used for Data Class

public class NumberOfAccessorMethodsMetric extends MetricVisitor{

	public boolean visit(TypeDeclaration typeDeclaration){
		
		AccessorVisitor accessorVisitor =  new AccessorVisitor();
		typeDeclaration.accept(accessorVisitor);
		metricResult = accessorVisitor.getMetricResult();
		return true;		
	}
}
