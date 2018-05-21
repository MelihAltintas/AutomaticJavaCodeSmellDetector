package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;


import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//BOvR - Base Class Overriding Ratio
//The number of methods of the measured class that override methods from
//the base class, divided by the total number of methods in the class
//Used for Refused Parent Bequest

public class BaseClassOverridingRatioMetric extends MetricVisitor {

	public boolean visit(TypeDeclaration typeDeclaration) {

		MethodDeclaration[] methods = typeDeclaration.getMethods();
		double overrideCount = 0;
		double totalCount = 0;
		for (MethodDeclaration method : methods) {
			int modifiers = method.getModifiers();
			if (!method.isConstructor() && !((modifiers & Modifier.STATIC) > 0) && !((modifiers & Modifier.ABSTRACT) > 0)) {
				if (Helper.isMethodOverridden(method)) {
					overrideCount++;
				}
				totalCount++;
				
			}

		}
		if(totalCount == 0) {
			metricResult = 1;
			return true;
		}
		metricResult = overrideCount / totalCount;
		return true;
	}

}
