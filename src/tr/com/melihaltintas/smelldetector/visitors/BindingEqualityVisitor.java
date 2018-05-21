package tr.com.melihaltintas.smelldetector.visitors;

import java.util.HashSet;


import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.SimpleName;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class BindingEqualityVisitor extends MetricVisitor {

	private HashSet<IBinding> bindingList;

	public BindingEqualityVisitor(HashSet<IBinding> bindingList) {
		this.bindingList = bindingList;
	}

	@Override
	public boolean visit(SimpleName node) {
		for (IBinding binding : bindingList) {
			if (binding.getKey().equals(node.resolveBinding().getKey())) {
				metricResult++;
			}
		}
		
		return true;
	}
}
