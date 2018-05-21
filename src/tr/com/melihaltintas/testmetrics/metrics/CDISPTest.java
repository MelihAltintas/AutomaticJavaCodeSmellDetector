package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.visitors.CouplingIntensityVisitor;
import tr.com.melihaltintas.smelldetector.visitors.ProvidersVisitor;
import tr.com.melihaltintas.testmetrics.helper.BaseClass;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;
import tr.com.melihaltintas.testmetrics.helper.DummyClass;

//CDISP - Coupling Dispersion
//The number of classes in which the operations called from the measured
//operation are defined, divided by CINT
//Used for Intensive Coupling(120), Dispersed Coupling(127)
public class CDISPTest extends BaseClass {

	@Test
	public void cdispTest() {
		System.out.println("CDISPTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(CDISPTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration methodDeclaration) {
				CouplingIntensityVisitor couplingIntensityVisitor = new CouplingIntensityVisitor(methodDeclaration);
				ProvidersVisitor providersVisitor = new ProvidersVisitor(methodDeclaration);

				methodDeclaration.accept(couplingIntensityVisitor);
				methodDeclaration.accept(providersVisitor);

				double providerCount = providersVisitor.getMetricResult();
				double CINT = couplingIntensityVisitor.getMetricResult();

				double metricResult = 0;

				if (CINT == 0)
					metricResult = 1;
				else {
					metricResult = providerCount / CINT;
				}
								
				System.out.println(
						methodDeclaration.getName().toString() + "  providers : " + providersVisitor.getProviders());
				if (methodDeclaration.getName().toString().equals("method1")) {
					assertEquals("CINT of method1() is wrong ", 3, CINT, 0);
					
					assertEquals("provider of method1() is wrong ", 2 , providerCount, 0);
					assertEquals("CDISP of method1() is wrong ", 2 / (double) 3, metricResult, 0);
				}

				return true;
			}
		});

		System.out.println("CDISPTest stopped");
	}

	public void method1() { // 2 provider 3 call

		DummyClass dummy = new DummyClass();
		dummy.dummyMethod(); // outer call +1 CINT +1 provider

		Helper.getStartPosition(null); // outer call +1 CINT +1 provider
		Helper.getEndPosition(null); // // outer call +1 CINT (provider already added)
		method2(); // no outer own method
		method3(); // no outer own method
		method2(); // no outer own method
		baseMethod1(); // no outer this method inherited from base class
	}

	public void method2() {

		System.out.println("method2");
	}

	public void method3() {
		System.out.println("method3");

	}

}
