package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.detectors.MethodPair;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.TightClassCohesionMetric;
import tr.com.melihaltintas.testmetrics.helper.BaseClass;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//TCC - Tight Class Cohesion
//The relative number of method pairs of a class that access in common at
//least one attribute of the measured class 
//Used for God Class, Brain Class
public class TCCTest extends BaseClass {

	private int field1;
	private int field2;
	private int field3;

	// total method count = 10 possible method pair = 10*9/2 = 45
	// method pair :14
	// Pair : methodUseField1AndField2()<=>methodUseField1()V
	// Pair : methodUseField1AndField2()<=>methodUseField3AndField2()V
	// Pair : methodUseField1AndField2()<=>methodUseField2()V
	// Pair : methodUseField1AndField2()<=>methodUseFieldInheritedAndField1()V
	// Pair : methodUseField1AndField2()<=>methodUseField3AndField2AndField1()V
	
	// Pair : methodUseField1()<=>methodUseFieldInheritedAndField1()V
	// Pair : methodUseField1()<=>methodUseField3AndField2AndField1()V
	
	// Pair : methodUseField3AndField2()<=>methodUseField3()V
	// Pair : methodUseField3AndField2()<=>methodUseField2()V
	// Pair : methodUseField3AndField2()<=>methodUseField3AndField2AndField1()V
	
	// Pair : methodUseField3()<=>methodUseField3AndField2AndField1()V
	
	// Pair : methodUseField2()<=>methodUseField3AndField2AndField1()V
	
	// Pair : methodUseFieldInherited()<=>methodUseFieldInheritedAndField1()V
	
	// Pair : methodUseFieldInheritedAndField1()<=>methodUseField3AndField2AndField1()V
	@Test
	public void tccTest() {
		System.out.println("TCCTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(TCCTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {

				TightClassCohesionMetric tightClassCohesionMetric = new TightClassCohesionMetric();
				typeDeclaration.accept(tightClassCohesionMetric);
				double metricResult = tightClassCohesionMetric.getMetricResult();

				System.out.println("Pairs : ");
				for (MethodPair<String, String> pair : tightClassCohesionMetric.getMethodPairs()) {
					System.out.println(pair);
				}
				assertEquals("tccTest is wrong ", 14 / (double) 45, metricResult, 0);

				return true;
			}
		});

		System.out.println("TCCTest stopped");
	}

	public void methodUseField1AndField2() {
		System.out.println(field1);
		System.out.println(field2);
	}

	public void methodUseField1() {
		System.out.println(field1);
	}

	private void methodUseField3AndField2() {
		System.out.println(field3);
		System.out.println(field2);
	}

	private void methodUseField3() {
		System.out.println(field3);
	}

	public void methodUseField2() {
		System.out.println(field2);
	}

	private void methodUseAnyThing() {
		System.out.println("hello");

	}

	private void methodUseFieldInherited() {
		System.out.println(fieldInherited);

	}

	private void methodUseFieldInheritedAndField1() {
		System.out.println(fieldInherited);
		System.out.println(field1);

	}

	private void methodUseField3AndField2AndField1() {
		System.out.println(field3);
		System.out.println(field2);
		System.out.println(field1);
	}
}
