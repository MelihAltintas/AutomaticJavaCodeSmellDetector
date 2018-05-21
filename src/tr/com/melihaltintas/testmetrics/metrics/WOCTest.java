package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.WeightOfClassMetric;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//WOC - Weight Of a Class
//The number of “functional” public methods divided by the total number of public members 
//Used for Data Class
public class WOCTest {

	private int a; // defined for getter setter
	private int b; // defined for getter setter

	public int c; // public field
	public int d; // public field
	
	public WOCTest() { // constructor not a functional method

	}

	@Test
	public void wOCTest() {    // functional method
		System.out.println("WOCTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(WOCTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {

				WeightOfClassMetric weightOfClassMetric = new WeightOfClassMetric();
				typeDeclaration.accept(weightOfClassMetric);
				double metricResult = weightOfClassMetric.getMetricResult();

				int totalPublicFunctionalMethodCountForThisClass = 6; // no getter setter constructor [publicmethods]
				int totalPublicMethodsCount = 10; // all public methods[exclude constructor]
				int totalPublicFieldCount = 2; // all public field
				
				int totalPublicMembers = totalPublicMethodsCount +totalPublicFieldCount;
				
				System.out.println("Total public functional method : " + weightOfClassMetric.getFunctionaPublicMethods());
				System.out.println("Total public field : " + weightOfClassMetric.getPublicFields());
				System.out.println("Total public method : " + weightOfClassMetric.getTotalPublicMethods());
				System.out.println("Total public members : " + (weightOfClassMetric.getPublicFields()+weightOfClassMetric.getTotalPublicMethods()));
				
				assertEquals("WOCTest[functional method] is wrong ",
						(double) totalPublicFunctionalMethodCountForThisClass,
						weightOfClassMetric.getFunctionaPublicMethods(), 0);
				
				assertEquals("WOCTest[public field] is wrong ",
						totalPublicFieldCount,
						weightOfClassMetric.getPublicFields(), 0);
				
				assertEquals("WOCTest[total public method] is wrong ",
						totalPublicMethodsCount,
						weightOfClassMetric.getTotalPublicMethods(), 0);
				
				
				assertEquals("WOCTest is wrong ",
						(double) totalPublicFunctionalMethodCountForThisClass / (double) totalPublicMembers,
						metricResult, 0);

				return true;
			}
		});

		System.out.println("WOCTest stopped");
	}

	public void functionalMethod1() { // functional method
		System.out.println("1");
	}

	public void functionalMethod2() { // functional method
		System.out.println("2");
	}

	public void functionalMethod3() { // functional method
		System.out.println("3");
	}

	public void functionalMethod4() { // functional method
		System.out.println("4");
	}

	public void functionalMethod5() { // functional method
		System.out.println("5");
	}

	private void noFunctionalMethod1() { // no functional method because private
		System.out.println("1");
	}

	private void noFunctionalMethod2() { // no functional method because private
		System.out.println("2");
	}

	private void noFunctionalMethod3() { // no functional method because private
		System.out.println("3");
	}

	// getter not a functional method
	public int getA() {
		return a;
	}

	// setter not a functional method
	public void setA(int a) {
		this.a = a;
	}

	// getter not a functional method
	public int getB() {
		return b;
	}

	// setter not a functional method
	public void setB(int b) {
		this.b = b;
	}

}
