package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.operationmetrics.LineOfCodeMethodMetric;
import tr.com.melihaltintas.smelldetector.visitors.CycloVisitor;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//LOC - Lines of Code
//The number of lines of code of an operation, including blank lines and comments
//Used for Brain Method, Brain Class
public class LOCMethodTest {

	
	@Test
	public void locMethodTest() {
		System.out.println("LOCMethodTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(LOCMethodTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration methodDeclaration) {
				LineOfCodeMethodMetric lineOfCodeMethodMetric = new LineOfCodeMethodMetric();
				methodDeclaration.accept(lineOfCodeMethodMetric);
				double metricResult = lineOfCodeMethodMetric.getMetricResult();
				
				System.out.println("LineOfCodeMethodMetric is calculated for : " + methodDeclaration.getName().toString() + " result : "
						+ metricResult);
							
				if (methodDeclaration.getName().toString().equals("method1")) {
					assertEquals("LineOfCodeMethodMetric of method1() is wrong ", 3, metricResult, 0);
				} else if (methodDeclaration.getName().toString().equals("method2")) {
					assertEquals("LineOfCodeMethodMetric of method2() is wrong ", 6, metricResult, 0);
				} else if (methodDeclaration.getName().toString().equals("method3")) {
					assertEquals("LineOfCodeMethodMetric of method3() is wrong ", 8, metricResult, 0);
				}
				return true;
			}
		});

		System.out.println("LOCMethodTest stopped");
	}
	
	/* method 1*/
	public void method1() {
		System.out.println("method1");
	}
	
	/* method 2*/
	public void method2() {
		
		
		/* deneme*/
		System.out.println("method2");
	}
	
	/*  method 3*/ 
	public void method3() {
		for (int i = 0; i < 15; i++) {
			System.out.println(i);
		}
		
		System.out.println("hello");
		System.out.println("world");
	}

}
