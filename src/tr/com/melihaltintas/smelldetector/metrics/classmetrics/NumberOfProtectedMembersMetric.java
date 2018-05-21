package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;

//NProtM - Number of Protected Members
//The number of protected methods and attributes of a class
//Used for Refused Parent Bequest
public class NumberOfProtectedMembersMetric extends MetricVisitor {

	private HashSet<String> protectedFields;
	private HashSet<String> protectedMethods;

	private HashSet<IBinding> protectedMembersBinding;


	public NumberOfProtectedMembersMetric() {
		protectedFields = new HashSet<String>();
		protectedMethods = new HashSet<String>();

		protectedMembersBinding = new HashSet<>();
	}

	public boolean visit(TypeDeclaration typeDeclaration) {

		FieldDeclaration[] fields = typeDeclaration.getFields();
		MethodDeclaration[] methods = typeDeclaration.getMethods();
		for (FieldDeclaration field : fields) {
			int modifiers = field.getModifiers();
			if ((modifiers & Modifier.PROTECTED) > 0) {
				if (protectedFields.add(typeDeclaration.getName() + "."
						+ ((VariableDeclarationFragment) field.fragments().get(0)).getName().toString())) {
					Object o = field.fragments().get(0);
					if (o instanceof VariableDeclarationFragment) {
						protectedMembersBinding.add(((VariableDeclarationFragment) o).resolveBinding());
					}
					metricResult++;
				}
			}
		}

		for (MethodDeclaration method : methods) {
			int modifiers = method.getModifiers();
			if ((modifiers & Modifier.PROTECTED) > 0 && !method.isConstructor()) {
				if (protectedMethods.add(method.resolveBinding().getKey())) {
					protectedMembersBinding.add(method.resolveBinding());
					metricResult++;
				}
			}
		}
		return true;
	}

	public HashSet<String> getProtectedFields() {
		return protectedFields;
	}

	public HashSet<String> getProtectedMethods() {
		return protectedMethods;
	}

	public HashSet<IBinding> getProtectedMembersBinding() {
		return protectedMembersBinding;
	}



}
