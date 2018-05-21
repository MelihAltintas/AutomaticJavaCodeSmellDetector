package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.AccessorVisitor;

//WOC - Weight Of a Class
//The number of “functional” public methods divided by the total number of public members [public fields and methods]
//Inherited members are not counted
//Used for Data Class

public class WeightOfClassMetric extends MetricVisitor {

	private int totalPublicMethods = 0;
	private int publicFields = 0;
	private int functionaPublicMethods = 0; // excludes constructors, getters, and setters

	public boolean visit(TypeDeclaration typeDeclaration) {

		MethodDeclaration[] methods = typeDeclaration.getMethods();
		FieldDeclaration[] fields = typeDeclaration.getFields();
		
		for (FieldDeclaration field : fields) {
			if (Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) == false) {
				publicFields++;
			}
		}

		for (MethodDeclaration method : methods) {

			if (Modifier.isPublic(method.getModifiers()) && !method.isConstructor()) {

				totalPublicMethods++;
				if (!AccessorVisitor.isGetter(method) && !AccessorVisitor.isSetter(method)
						&& Modifier.isAbstract(method.getModifiers()) == false) {
					functionaPublicMethods++;
				}
			}

		}
		return true;
	}

	@Override
	public double getMetricResult() {
		return totalPublicMethods + publicFields == 0 ? 1
				: functionaPublicMethods * 1.0 / (totalPublicMethods + publicFields);

	}

	public int getTotalPublicMethods() {
		return totalPublicMethods;
	}

	public int getPublicFields() {
		return publicFields;
	}

	public int getFunctionaPublicMethods() {
		return functionaPublicMethods;
	}

}
