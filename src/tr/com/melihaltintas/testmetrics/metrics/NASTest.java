package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfAddedServicesMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfMethodsMetric;
import tr.com.melihaltintas.testmetrics.helper.BaseClass;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//NAS - Number of Added Services
//The number of public methods of a class that are not overridden or specialized
//from the ancestor classes Used for Tradition Breaker
public class NASTest extends BaseClass{

	
	@Test
	public void nasTest() {   // not overriden public method   +1
		System.out.println("NASTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(NASTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {
				
				NumberOfAddedServicesMetric numberOfAddedServicesMetric = new NumberOfAddedServicesMetric();
				typeDeclaration.accept(numberOfAddedServicesMetric);
				double metricResult = numberOfAddedServicesMetric.getMetricResult();

				System.out.println(numberOfAddedServicesMetric.getNotOverriddenPublicMethods());
				assertEquals("NASTest is wrong ", 3, metricResult, 0);

				return true;
			}
		});

		System.out.println("NASTest stopped");
	}
	
	@Override
	protected void baseMethod1() { // overridden not counted
		
		System.out.println("hello");
	}
	
	@Override 
	public void baseMethod2() {      // overridden not counted
		
		System.out.println("hello");
	}
	
	@Override
	public void baseMethod3() {      // overridden not counted
		
		System.out.println("hello");
	}
	
	public void normalMethod() {   // not overriden public method   +1
		System.out.println("hello");
	}
	
	private void normalMethod2() {   // private not counted
		System.out.println("hello");
	}
	
	public void normalMethod3() {     // not overriden public method  +1
		System.out.println("hello");
	}
}
