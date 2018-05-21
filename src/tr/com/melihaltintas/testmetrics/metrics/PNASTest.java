package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfAddedServicesMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfPublicMethodsMetric;
import tr.com.melihaltintas.testmetrics.helper.BaseClass;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//PNAS - Percentage of Newly Added Services
//The number of public methods of a class that are not overridden or specialized
//from the ancestors, divided by the total number of public methods
//Used for Tradition Breaker
public class PNASTest extends BaseClass {

	@Test
	public void pnasTest() { // not overriden public method +1
		System.out.println("PNASTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(PNASTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {

				NumberOfAddedServicesMetric numberOfAddedServicesMetric = new NumberOfAddedServicesMetric();
				typeDeclaration.accept(numberOfAddedServicesMetric);
				double NAS = numberOfAddedServicesMetric.getMetricResult();

				System.out.println("nas : " + NAS);
				NumberOfPublicMethodsMetric numberOfPublicMethodsMetric = new NumberOfPublicMethodsMetric();
				typeDeclaration.accept(numberOfPublicMethodsMetric);
				double numberOfPublicMethods = numberOfPublicMethodsMetric.getMetricResult();

				System.out.println("numberOfPublicMethods : " + numberOfPublicMethods);
				System.out.println("PublicMethods : " + numberOfPublicMethodsMetric.getPublicMethods());
				
				System.out.println("not overridden PublicMethods " + numberOfAddedServicesMetric.getNotOverriddenPublicMethods());
				assertEquals("PNASTest is wrong ", 3 / (double)5, NAS / numberOfPublicMethods, 0);

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
	public void baseMethod2() { // overridden not counted

		System.out.println("hello");
	}

	@Override
	public void baseMethod3() { // overridden not counted

		System.out.println("hello");
	}

	public void normalMethod() { // not overriden public method +1
		System.out.println("hello");
	}

	private void normalMethod2() { // private not counted
		System.out.println("hello");
	}

	public void normalMethod3() { // not overriden public method +1
		System.out.println("hello");
	}
}
