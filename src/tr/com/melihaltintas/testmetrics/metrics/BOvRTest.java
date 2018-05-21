package tr.com.melihaltintas.testmetrics.metrics;
//
//BOvR - Base Class Overriding Ratio
//The number of methods of the measured class that override methods from
//the base class, divided by the total number of methods in the class
//Used for Refused Parent Bequest

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.BaseClassOverridingRatioMetric;
import tr.com.melihaltintas.testmetrics.helper.BaseClass;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

public class BOvRTest extends BaseClass{
	
	@Test
	public void bovrTest() {  
		System.out.println("BOvRTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(BOvRTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {
				
				BaseClassOverridingRatioMetric baseClassOverridingRatioMetric = new BaseClassOverridingRatioMetric();
				typeDeclaration.accept(baseClassOverridingRatioMetric);
				double metricResult = baseClassOverridingRatioMetric.getMetricResult();

				
				assertEquals("BOvRTest is wrong ", 3/(double)6, metricResult,0);

				return true;
			}
		});

		System.out.println("BOvRTest stopped");
	}
	
	protected void normalMethod1() {
		System.out.println("normalMethod1");
	}
	protected void normalMethod2() {
		System.out.println("normalMethod2");
	}
	@Override
	protected void baseMethod1() {
		System.out.println("baseMethod2");
	}
	
	@Override
	public void baseMethod3() {
		System.out.println("baseMethod3");
	}
	
	@Override
	public void baseMethod2() {
		System.out.println("baseMethod2");
	}
}
