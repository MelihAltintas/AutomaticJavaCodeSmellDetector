package tr.com.melihaltintas.smelldetector.metrics.operationmetrics;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//LOC - Lines of Code
//The number of lines of code of an operation, including blank lines and comments
//Used for Brain Method, Brain Class

public class LineOfCodeMethodMetric extends MetricVisitor {

	private int startPosition;
	private int endPosition;

	@Override
	public boolean visit(MethodDeclaration methodDeclaration) {

		startPosition = ((CompilationUnit) methodDeclaration.getRoot())
				.getLineNumber(methodDeclaration.getStartPosition());
		endPosition = ((CompilationUnit) methodDeclaration.getRoot())
				.getLineNumber(methodDeclaration.getStartPosition() + methodDeclaration.getLength());

		return true;
	}

	@Override
	public void endVisit(MethodDeclaration node) {
		metricResult = endPosition - startPosition + 1;
	}
}

