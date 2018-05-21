package tr.com.melihaltintas.smelldetector.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ReturnStatement;


public class ReturnStatementVisitorForGetterMethods extends ASTVisitor {


	private boolean isReturnStatementField = false;
	private ITypeBinding currentClass;

	public ReturnStatementVisitorForGetterMethods(ITypeBinding currentClass) {
		this.currentClass = currentClass;
	}

	public boolean visit(ReturnStatement rs) {
		VariableFieldVisitor variableVisitor  = new VariableFieldVisitor(currentClass);
		rs.accept(variableVisitor);
		isReturnStatementField = variableVisitor.isField();
		return true;
	}

	public boolean isReturnStatementField() {
		return isReturnStatementField;
	}

	public void setReturnStatementField(boolean isReturnStatementField) {
		this.isReturnStatementField = isReturnStatementField;
	}



}
