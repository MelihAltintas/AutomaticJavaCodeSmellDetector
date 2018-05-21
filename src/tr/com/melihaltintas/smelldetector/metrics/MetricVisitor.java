package tr.com.melihaltintas.smelldetector.metrics;
import org.eclipse.jdt.core.dom.ASTVisitor;



public abstract class MetricVisitor extends ASTVisitor{

	protected double metricResult = 0;
	

	public double getMetricResult() {
		return metricResult;
	}
	
}
