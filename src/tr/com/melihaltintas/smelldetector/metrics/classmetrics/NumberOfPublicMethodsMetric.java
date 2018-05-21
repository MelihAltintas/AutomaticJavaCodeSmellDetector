package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class NumberOfPublicMethodsMetric extends MetricVisitor {

	private List<String> publicMethods;

	public NumberOfPublicMethodsMetric() {
		publicMethods = new ArrayList<>();
	}

	public boolean visit(TypeDeclaration typeDeclaration) {

		MethodDeclaration[] methods = typeDeclaration.getMethods();

		for (MethodDeclaration method : methods) {
			int modifiers = method.getModifiers();
			if ((modifiers & Modifier.PUBLIC) > 0 
					&& !method.isConstructor() && !Modifier.isStatic(modifiers)) {
				metricResult++;
				publicMethods.add(method.resolveBinding().getKey());
			}

		}
		return true;
	}

	public List<String> getPublicMethods() {
		return publicMethods;
	}
	
	
}
