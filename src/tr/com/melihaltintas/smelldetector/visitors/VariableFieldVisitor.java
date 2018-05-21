package tr.com.melihaltintas.smelldetector.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;

public class VariableFieldVisitor extends ASTVisitor {

	private boolean isField = false;
	
	private ITypeBinding currentClass;

	public VariableFieldVisitor(ITypeBinding currentClass) {
		this.currentClass = currentClass;
	}
	
	@Override
	public boolean visit(SimpleName variable) {
		IBinding binding = variable.resolveBinding();
		if (binding instanceof IVariableBinding) {
			IVariableBinding variableBinding = (IVariableBinding) binding;

			if(variableBinding.isField() && variableBinding.getDeclaringClass().isEqualTo(currentClass)){
				isField = true;
			}else {
				isField = false;
			}
		}

		return true;
	}

	public boolean isField() {
		return isField;
	}

	public void setField(boolean isField) {
		this.isField = isField;
	}
	
	
}
