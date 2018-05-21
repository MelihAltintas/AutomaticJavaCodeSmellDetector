package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.lang.reflect.Modifier;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//NOPA - Number of Public Attributes
//The number of public attributes of a class  [no static no final]
//Used for Data Class

public class NumberOfPublicAttributesMetric extends MetricVisitor {

	public boolean visit(TypeDeclaration typeDeclaration) {

		FieldDeclaration[] fields = typeDeclaration.getFields();

		for (FieldDeclaration field : fields) {
			int modifiers = field.getModifiers();
			if ((modifiers & Modifier.PUBLIC) > 0 && !((modifiers & Modifier.STATIC) > 0)
					&& !((modifiers & Modifier.FINAL) > 0)) {
				metricResult++;
			}

		}
		return true;
	}
}
