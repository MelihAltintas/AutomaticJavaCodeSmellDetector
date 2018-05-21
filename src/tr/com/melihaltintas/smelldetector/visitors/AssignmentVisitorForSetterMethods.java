package tr.com.melihaltintas.smelldetector.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;

public class AssignmentVisitorForSetterMethods extends ASTVisitor {

	private int isSetterCandidate = 0;
	private ITypeBinding currentClass;

	public AssignmentVisitorForSetterMethods(ITypeBinding currentClass) {
		this.currentClass = currentClass;
	}

	public boolean visit(Assignment assignment) {

		Expression leftHandSide = assignment.getLeftHandSide();
		Expression rightHandSide = assignment.getRightHandSide();

		VariableFieldVisitor fieldVisitor = new VariableFieldVisitor(currentClass);
		VariableParameterVisitor parameterVisitor = new VariableParameterVisitor();

		leftHandSide.accept(fieldVisitor);
		rightHandSide.accept(parameterVisitor);

		boolean leftHandSideIsField = fieldVisitor.isField();
		boolean rightHandSideIsParameter = parameterVisitor.isParameter();

		if (rightHandSideIsParameter && leftHandSideIsField) {
			isSetterCandidate++;
		}

		return true;
	}

	public boolean isSetter() {

		return isSetterCandidate > 0 ? true : false;

	}
}
