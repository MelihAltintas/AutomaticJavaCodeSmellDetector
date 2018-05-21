package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.AccessToForeignDataVisitor;

public class AccessToForeignDataMethodMetric extends MetricVisitor {

	public boolean visit(MethodDeclaration methodDeclaration) {

		if(methodDeclaration.isConstructor() && Modifier.isAbstract(methodDeclaration.getModifiers())) return false;
		
		try {
			AccessToForeignDataVisitor accessToForeignDataVisitor = new AccessToForeignDataVisitor(
					methodDeclaration.resolveBinding().getDeclaringClass());

			methodDeclaration.accept(accessToForeignDataVisitor);
			metricResult += accessToForeignDataVisitor.getMetricResult();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return true;
	}

}
