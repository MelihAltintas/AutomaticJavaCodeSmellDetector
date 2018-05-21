package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import org.eclipse.core.commands.ITypedParameter;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.AccessToForeignDataVisitor;
import tr.com.melihaltintas.smelldetector.visitors.BindingEqualityVisitor;

//BUR - Base Class Usage Ratio
//The number of inheritance-specific members used by the measured class,
//divided by the total number of inheritance-specific members from the base
//class
//Used for Refused Parent Bequest

public class BaseClassUsageRatioMetric extends MetricVisitor {

	public boolean visit(TypeDeclaration typeDeclaration) {

		ITypeBinding superClassBinding = typeDeclaration.resolveBinding().getSuperclass();

		IType superClassType = (IType) superClassBinding.getJavaElement();
		CompilationUnit superClassCu = Helper.getCompilationUnit(superClassType.getCompilationUnit());
		TypeDeclaration superClassDecl = (TypeDeclaration) superClassCu.findDeclaringNode(superClassBinding.getKey());

		NumberOfProtectedMembersMetric numberOfProtectedMembersMetric = new NumberOfProtectedMembersMetric();
		superClassDecl.accept(numberOfProtectedMembersMetric);
		double numOfProtectedMembers = numberOfProtectedMembersMetric.getMetricResult();

		if (numOfProtectedMembers == 0) {
			metricResult = 1;
			return true;
		} else {
			BindingEqualityVisitor bindingEqualityVisitor = new BindingEqualityVisitor(
					numberOfProtectedMembersMetric.getProtectedMembersBinding());
			typeDeclaration.accept(bindingEqualityVisitor);
			double usedProtectedMembers = bindingEqualityVisitor.getMetricResult();
			metricResult = usedProtectedMembers / numOfProtectedMembers;
			return true;
		}

	}

}
