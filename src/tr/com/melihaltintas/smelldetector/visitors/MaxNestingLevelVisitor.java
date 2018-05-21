package tr.com.melihaltintas.smelldetector.visitors;

import org.eclipse.jdt.core.dom.Block;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class MaxNestingLevelVisitor  extends MetricVisitor{
		
	private int maxDepth = 0;  
	private int depth = 0;
	
	@Override
	public boolean visit(Block node) {
		depth++;
		return true;
	}
	
	@Override
	public void endVisit(Block node) {
		if(depth> maxDepth) {
			maxDepth = depth;
		}
		depth--;
	}
	
	@Override
	public double getMetricResult() {

		return maxDepth - 1;  // -1  because base method block should not be  counted
	}

}
