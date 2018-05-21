package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.AccessToForeignDataVisitor;

//ATFD - Access To Foreign Data
//The number of attributes from unrelated classes that are accessed directly
//or by invoking accessor methods 
//Used for God Class, Feature Envy

public class AccessToForeignDataClassMetric extends MetricVisitor {
	
	
	public boolean visit(TypeDeclaration typeDeclaration) {

		AccessToForeignDataVisitor accessToForeignDataVisitor = new AccessToForeignDataVisitor(
				typeDeclaration.resolveBinding());

		typeDeclaration.accept(accessToForeignDataVisitor);
		metricResult += accessToForeignDataVisitor.getMetricResult();
		System.out.println("accessed fields : " +accessToForeignDataVisitor.getAccessedFields());
		System.out.println("accessed getters : " +accessToForeignDataVisitor.getGetters());
		System.out.println("accessed setters : " +accessToForeignDataVisitor.getGetters());
		return true;
	}
	

}
