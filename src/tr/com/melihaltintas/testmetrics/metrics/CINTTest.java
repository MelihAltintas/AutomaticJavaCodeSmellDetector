package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.visitors.CouplingIntensityVisitor;
import tr.com.melihaltintas.testmetrics.helper.BaseClass;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;
import tr.com.melihaltintas.testmetrics.helper.DummyClass;

//CINT - Coupling Intensity
//Definition The number of distinct operations called by the measured operation
//Used for Intensive Coupling, Dispersed Coupling
public class CINTTest extends BaseClass{

	@Test
	public void cintTest() {
		System.out.println("CINTTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(CINTTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration methodDeclaration) {
				CouplingIntensityVisitor couplingIntensityVisitor = new CouplingIntensityVisitor(methodDeclaration);
				methodDeclaration.accept(couplingIntensityVisitor);
				double metricResult = couplingIntensityVisitor.getMetricResult();
				
				System.out.println("CINT is calculated for : " + methodDeclaration.getName().toString() + " result : "
						+ metricResult);
				System.out.println("CINT calledMethods : " + couplingIntensityVisitor.getCalledDistinctMethods());
				if (methodDeclaration.getName().toString().equals("method1")) {
					assertEquals("CINT of method1() is wrong ", 1, metricResult, 0);
				}
				return true;
			}
		});

		System.out.println("CINTTest stopped");
	}
	
	public void method1() {
		
		DummyClass dummy = new DummyClass();
		dummy.dummyMethod();   // outer call +1
		
		method2();  // no outer  own method
		method3();  // no outer  own method
		method2();  // no outer  own method
		baseMethod1();  // no outer this method inherited from base class
	}
	
	public void method2() {
		
		System.out.println("method2");
	}
	
	public void method3() {
		System.out.println("method3");
		
	}
	
	
	
	

}
