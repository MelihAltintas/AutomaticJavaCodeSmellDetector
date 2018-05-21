package tr.com.melihaltintas.smelldetector.visitors;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class ProvidersVisitor extends MetricVisitor {
	private HashSet<String> providers;
	private MethodDeclaration currentMethod;

	public ProvidersVisitor(MethodDeclaration currentMethod) {
		this.currentMethod = currentMethod;
		providers = new HashSet<>();
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
			
			if (!Helper.isSuperClassOrEqual(currentMethodClass, invokedMethodClass)  && invokedMethodClass.isFromSource()) { // outer methods
				if (providers.add(invokedMethodClass.getKey())) {
					metricResult++;
				}

			}

		return true;
	}

	public HashSet<String> getProviders() {
		return providers;
	}

	
}
