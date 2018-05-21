package tr.com.melihaltintas.smelldetector.visitors;

import java.util.HashSet;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

public class TightClassCohesionVisitor extends MetricVisitor {

	private HashSet<String> usedClassFieldNames;
	private TypeDeclaration currentClass;

	public TightClassCohesionVisitor(TypeDeclaration currentClass) {
		this.currentClass = currentClass;
		usedClassFieldNames = new HashSet<>();
	}

	public boolean visit(SimpleName node) {

		IBinding binding = node.resolveBinding();

		if (binding instanceof IVariableBinding) {

			IVariableBinding variable = (IVariableBinding) binding;
			if (variable.getDeclaringClass() != null) {
				if (variable.isField() && variableDeclaredThisClassOrSuperClass(variable)) {
					usedClassFieldNames.add(node.toString());
				}
			}

		}
		return super.visit(node);
	}

	private boolean variableDeclaredThisClassOrSuperClass(IVariableBinding variable) {
		
		
		ITypeBinding currentClassBinding = currentClass.resolveBinding();
		ITypeBinding variableDeclarationClassBinding =  variable.getDeclaringClass();
		
		return  currentClassBinding.isAssignmentCompatible(variableDeclarationClassBinding);


	}

	public HashSet<String> getUsedClassFieldNames() {
		return usedClassFieldNames;
	}

	public void setUsedClassFieldNames(HashSet<String> usedClassFieldNames) {
		this.usedClassFieldNames = usedClassFieldNames;
	}

}
