package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//NAS - Number of Added Services
//The number of public methods of a class that are not overridden or specialized
//from the ancestor classes
//Used for Tradition Breaker

public class NumberOfAddedServicesMetric extends MetricVisitor {

	private List<String>notOverriddenPublicMethods;
	
	public NumberOfAddedServicesMetric() {
		notOverriddenPublicMethods = new ArrayList<>();
	}
	public boolean visit(TypeDeclaration typeDeclaration) {

		MethodDeclaration[] methods = typeDeclaration.getMethods();
		for (MethodDeclaration method : methods) {
			int modifiers = method.getModifiers();
			if ((modifiers & Modifier.PUBLIC) > 0 && !Modifier.isStatic(method.getModifiers()) && !method.isConstructor()) {
				if (!Helper.isMethodOverridden(method)) {
					metricResult++;
					notOverriddenPublicMethods.add(method.resolveBinding().getKey());

				}
			}
		}
		return true;
	}
	public List<String> getNotOverriddenPublicMethods() {
		return notOverriddenPublicMethods;
	}
	
	

}
