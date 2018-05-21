package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.NumberOfAccessedVariablesMetric;
import tr.com.melihaltintas.smelldetector.visitors.CycloVisitor;
import tr.com.melihaltintas.smelldetector.visitors.NumberOfAccessedVariablesVisitor;
import tr.com.melihaltintas.testmetrics.helper.BaseClass;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;
import tr.com.melihaltintas.testmetrics.helper.DummyClass;

//NOAV - Number of Accessed Variables
//The total number of variables accessed directly from the measured operation.
//Variables include parameters, local variables, but also instance variables
//and global variables
//Used for Brain Method
public class NOAVTest extends BaseClass {

	private int fieldPrivate;
	private final int fieldFinal = 5;  // no count because final
	@Test
	public void NOAVTest() {
		System.out.println("NOAVTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(NOAVTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration methodDeclaration) {
				
				NumberOfAccessedVariablesVisitor numberOfAccessedVariablesVisitor = new NumberOfAccessedVariablesVisitor(methodDeclaration);
				methodDeclaration.accept(numberOfAccessedVariablesVisitor);
				double metricResult = numberOfAccessedVariablesVisitor.getMetricResult();
		
				
				if (methodDeclaration.getName().toString().equals("method1")) {
					System.out.println("NOAV is calculated for : " + methodDeclaration.getName().toString() + " result : "
							+ metricResult);
					
					System.out.println("accessed variables " + numberOfAccessedVariablesVisitor.getAccessedVariables());
					assertEquals("NOAV of method1() is wrong ", 7, metricResult, 0);
				} 
				return true;
			}
		});

		System.out.println("NOAVTest stopped");
	}
	
	public void method1(int parameter1,int parameter2) {  // two parameter  //+2
		int localVar = 5;  // local var  //+1
		System.out.println(fieldPrivate);  // field access   //+1
		System.out.println(fieldFinal);   // no field access because final
		
		
		System.out.println(fieldPrivate);  // already counted  

		
		DummyClass dummyClass = new DummyClass();  // local var  //+1 
		System.out.println(dummyClass.dummyField);  // field access  //+1
		
		
		System.out.println(dummyClass.dummyField);  // already counted 
		
		System.out.println(publicInherited);    //+1  base class field access ++1
		
		
	}

}
