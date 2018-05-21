package tr.com.melihaltintas.smelldetector.metrics.classmetrics;


import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.CycloVisitor;


//WMC - Weighted Method Count
//The sum of the statical complexity of all methods of a class. The CYCLO metric
//is used to quantify the method’s complexity 
//Used for Refused Parent Bequest, Tradition Breaker, God Class, DataClass, Brain Class

public class WeightedMethodCountMetric extends MetricVisitor{

	public boolean visit(TypeDeclaration typeDeclaration){
		
		MethodDeclaration[] methods = typeDeclaration.getMethods();
		
		for (MethodDeclaration method : methods) {
			
			CycloVisitor cycloVisitor = new CycloVisitor(method);		
			
			try{
				method.accept(cycloVisitor);
				double cyclo = cycloVisitor.getMetricResult();
				metricResult += cyclo;				
			}catch(Exception e){
				e.printStackTrace();
			}
						
		}
		return true;		
	}

}
