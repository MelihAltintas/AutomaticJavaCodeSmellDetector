package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.MaxNestingLevelVisitor;

//MAXNESTING - Maximum Nesting Level
// The maximum nesting level of control structures within an operation
// The maximum number of nested statements (if,for,while...)
// when statements are nested too deeply in code they become difficult to understand
public class MaxNestingMetric extends MetricVisitor {

	public boolean visit(MethodDeclaration methodDeclaration) {

		if (Modifier.isAbstract(methodDeclaration.getModifiers())) {
			metricResult = 0;
			return true;

		}

		MaxNestingLevelVisitor maxNestingLevelVisitor = new MaxNestingLevelVisitor();

		methodDeclaration.accept(maxNestingLevelVisitor);
		metricResult += maxNestingLevelVisitor.getMetricResult();

		return true;
	}
}
