package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfMethodsMetric;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;


//NOM - Number of Methods 
//The number of methods of a class
//Used for Refused Parent Bequest, Tradition Breaker

public class NOMTest {

	private int field1;
	
	@Test
	public void nomTest() {   //+1
		System.out.println("NOMTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(NOMTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {
				
				NumberOfMethodsMetric numberOfMethodsMetric = new NumberOfMethodsMetric();
				typeDeclaration.accept(numberOfMethodsMetric);
				double metricResult = numberOfMethodsMetric.getMetricResult();

				
				assertEquals("NOMTest is wrong ", 6, metricResult, 0);

				return true;
			}
		});

		System.out.println("NOMTest stopped");
	}

	public void method1() {  //+1
		
	}
	
	private void method2() {  //+1
		
	}
	
	protected int method3() {  //+1
		return 5;
	}
	
	public int getField1() {   //+1
		return field1;
	}

	public void setField1(int field1) { //+1
		this.field1 = field1;
	}
	
	
}
