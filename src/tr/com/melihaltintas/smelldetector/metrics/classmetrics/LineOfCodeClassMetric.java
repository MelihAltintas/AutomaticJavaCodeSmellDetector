package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class LineOfCodeClassMetric extends MetricVisitor{

	public boolean visit(TypeDeclaration typeDeclaration) {

		int startPosition = ((CompilationUnit) typeDeclaration.getRoot())
				.getLineNumber(typeDeclaration.getStartPosition());
		int endPosition = ((CompilationUnit) typeDeclaration.getRoot())
				.getLineNumber(typeDeclaration.getStartPosition() + typeDeclaration.getLength());

		metricResult = endPosition - startPosition + 1;
		return true;
	}
}
