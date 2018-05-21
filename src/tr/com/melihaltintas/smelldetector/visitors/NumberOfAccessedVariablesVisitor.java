package tr.com.melihaltintas.smelldetector.visitors;

import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//NOAV - Number of Accessed Variables
//The total number of variables accessed directly from the measured operation.
//Variables include parameters, local variables, but also instance variables
//and global variables
//Used for Brain Method

public class NumberOfAccessedVariablesVisitor extends MetricVisitor {

	private MethodDeclaration currentMethod;
	private HashSet<String> accessedVariables;

	public NumberOfAccessedVariablesVisitor(MethodDeclaration currentMethod) {
		this.currentMethod = currentMethod;
		accessedVariables = new HashSet<>();

	}

	public boolean visit(SimpleName node) {

		IBinding binding = node.resolveBinding();

		if (binding instanceof IVariableBinding) { // variable

			IVariableBinding variable = (IVariableBinding) binding;
			
			if(Modifier.isFinal(variable.getModifiers())) {
				return true;
			}
			
			if (accessedVariables.add(variable.getKey())) {
				metricResult++;
			}

		}
		return true;
	}

	public MethodDeclaration getCurrentMethod() {
		return currentMethod;
	}

	public void setCurrentMethod(MethodDeclaration currentMethod) {
		this.currentMethod = currentMethod;
	}

	public HashSet<String> getAccessedVariables() {
		return accessedVariables;
	}
	
	
	
	

}
