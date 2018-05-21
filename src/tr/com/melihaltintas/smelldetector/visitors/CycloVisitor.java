package tr.com.melihaltintas.smelldetector.visitors;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//CYCLO - McCabe’s Cyclomatic Number
//Definition The number of linearly-independent paths through an operation [no abstract]
//Used for Brain Method

public class CycloVisitor extends MetricVisitor {

	private MethodDeclaration currentMethod;

	public CycloVisitor(MethodDeclaration currentMethod) {
		if (!((currentMethod.getModifiers() & Modifier.ABSTRACT) > 0)) {
			metricResult = 1; // base cyclo for method = 1
		} else {
			metricResult = 0;
		}
		
		this.currentMethod = currentMethod;

	}

	public boolean visit(CatchClause catchClause) {
		metricResult++; // catch clause cyclo ++
		return true;
	}

	public boolean visit(ConditionalExpression conditionalExpression) { // Expression ? Expression : Expression
		metricResult++; // conditionalExpression cyclo ++
		booleanOperatorCountInExpression(conditionalExpression.getExpression());
		return true;
	}

	public boolean visit(DoStatement doStatement) {
		metricResult++; // doStatement cyclo ++
		booleanOperatorCountInExpression(doStatement.getExpression());
		return true;
	}

	public boolean visit(ForStatement forStatement) {
		metricResult++; // forStatement cyclo ++
		booleanOperatorCountInExpression(forStatement.getExpression());
		return true;
	}

	public boolean visit(IfStatement ifStatement) {
		metricResult++; // ifStatement cyclo ++
		booleanOperatorCountInExpression(ifStatement.getExpression());
		return true;
	}

	public boolean visit(SwitchCase switchCase) {
		if (!switchCase.isDefault())
			metricResult++;
		booleanOperatorCountInExpression(switchCase.getExpression());
		return true;
	}

	public boolean visit(WhileStatement whileStatement) {

		metricResult++; // whileStatement cyclo ++
		booleanOperatorCountInExpression(whileStatement.getExpression());
		return true;
	}

	public boolean visit(ExpressionStatement expressionStatement) {

		booleanOperatorCountInExpression(expressionStatement.getExpression());
		return true;
	}

	public void booleanOperatorCountInExpression(Expression ex) { // || and &&
		if (ex != null) {
			String expression = ex.toString();
			int orCount = expression.split("\\|\\|", -1).length - 1;
			int andCount = expression.split("\\&\\&", -1).length - 1;
			metricResult += orCount;
			metricResult += andCount;
		}
	}

	public MethodDeclaration getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(MethodDeclaration currentMethod) {
		this.currentMethod = currentMethod;
	}

}
