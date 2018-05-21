package tr.com.melihaltintas.smelldetector.visitors;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class CouplingIntensityVisitor extends MetricVisitor {
	private MethodDeclaration currentMethod;
	private HashSet<String> calledDistinctMethods;

	public CouplingIntensityVisitor(MethodDeclaration currentMethod) {
		this.currentMethod = currentMethod;
		calledDistinctMethods = new HashSet<>();
	}

	@Override
	public boolean visit(MethodInvocation methodInvocation) {

		ITypeBinding currentMethodClass = currentMethod.resolveBinding().getDeclaringClass();

		ITypeBinding invokedMethodClass = null;
		try {
			invokedMethodClass = methodInvocation.resolveMethodBinding().getDeclaringClass();
		} catch (Exception ex) {
			return false;
		}
	
		if (!Helper.isSuperClassOrEqual(currentMethodClass, invokedMethodClass) && invokedMethodClass.isFromSource()) { // outer
																														// methods
			if (calledDistinctMethods.add(methodInvocation.resolveMethodBinding().getKey())) {
				metricResult++;
			}

		}

		return true;
	}

	public HashSet<String> getCalledDistinctMethods() {
		return calledDistinctMethods;
	}

}
