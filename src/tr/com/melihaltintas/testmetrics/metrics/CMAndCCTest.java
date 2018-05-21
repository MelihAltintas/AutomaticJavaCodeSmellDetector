package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.ChangingMetric;
import tr.com.melihaltintas.smelldetector.visitors.CycloVisitor;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//CM - Changing Methods
//The number of distinct methods that call the measured method 
//Used for Shotgun Surgery


//CC - Changing Classes
//The number of classes in which the methods that call the measured method
//Used for Shotgun Surgery

public class CMAndCCTest {

	@Test
	public void CMAndCCTest() {

		System.out.println("CMAndCCTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(CMAndCCTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration methodDeclaration) {
				ChangingMetric ChangingMetric = new ChangingMetric();
				methodDeclaration.accept(ChangingMetric);
				
				
				double cc = ChangingMetric.getCC();
				double cm = ChangingMetric.getCM();
				

				if (methodDeclaration.getName().toString().equals("method1")) {
					System.out.println("cc is calculated for : " + methodDeclaration.getName().toString() + " result : "
							+ cc);
					
					System.out.println("cc is calculated for : " + methodDeclaration.getName().toString() + " result : "
							+ cm);
					
					assertEquals("CC of method1() is wrong ", 1, cc, 0);
					assertEquals("CM of method1() is wrong ", 1, cm, 0);
				}
				return true;
			}
		});

		System.out.println("CMAndCCTest stopped");
	}
	
	public void method1() {
		System.out.println("method1 called");
	}
	
	public void method2() {
		method1();
	}

	
	public void method3() {
		method1();
	}
}
