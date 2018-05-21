package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;



import org.eclipse.jdt.core.dom.MethodDeclaration;


import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.LocalityOfAttributeAccessesVisitor;



//LAA - Locality of Attribute Accesses
//The number of attributes from the method’s definition class, divided by the
//total number of variables accessed (including attributes used via accessor
//methods, see ATFD), whereby the number of local attributes accessed
//is computed in conformity with the LAA specifications
//Used for Feature Envy

public class LocalityOfAttributeAccessesMetric extends MetricVisitor{

	public boolean visit(MethodDeclaration methodDeclaration) {

		LocalityOfAttributeAccessesVisitor localityOfAttributeAccessesVisitor = new LocalityOfAttributeAccessesVisitor(methodDeclaration);

		methodDeclaration.accept(localityOfAttributeAccessesVisitor);
		metricResult += localityOfAttributeAccessesVisitor.getMetricResult();

		System.out.println("********* accessed own fields : " +localityOfAttributeAccessesVisitor.getAccessedOwnFields() );
		System.out.println("********* accessed foreign fields : " +localityOfAttributeAccessesVisitor.getAccessedForeignFields() );
		return true;
	}


}
