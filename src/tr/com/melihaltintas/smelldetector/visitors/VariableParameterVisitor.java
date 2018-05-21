package tr.com.melihaltintas.smelldetector.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;

public class VariableParameterVisitor extends ASTVisitor {
	
	private boolean isParameter = false;
	
	@Override
	public boolean visit(SimpleName variable) {
		IBinding binding = variable.resolveBinding();
		if (binding instanceof IVariableBinding) {
			IVariableBinding variableBinding = (IVariableBinding) binding;
			if (!variableBinding.isParameter()) {
				isParameter = false;
			}else {
				isParameter = true;
			}
		}

		return true;
	}

	public boolean isParameter() {
		return isParameter;
	}

	public void setParameter(boolean isParameter) {
		this.isParameter = isParameter;
	}
	
	
}
