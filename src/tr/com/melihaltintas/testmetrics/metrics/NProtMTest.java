package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfProtectedMembersMetric;

import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//NProtM - Number of Protected Members
//The number of protected methods and attributes of a class
//Used for Refused Parent Bequest

public class NProtMTest {

	private int att1;
	public int att2;
	private int att3;
	public int att4;
	private int att5;
	public int att6;
	protected int att7; // protected
	protected int att8; // protected
	protected int att9; // protected

	@Test
	public void nProtMTest() {
		System.out.println("NProtMTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(NProtMTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {

				NumberOfProtectedMembersMetric numberOfProtectedMembersMetric = new NumberOfProtectedMembersMetric();
				typeDeclaration.accept(numberOfProtectedMembersMetric);
				double metricResult = numberOfProtectedMembersMetric.getMetricResult();
				int protectedMethodCount = 2;
				int protectedFieldCount = 3;
				System.out.println(
						"Protected fields : " + numberOfProtectedMembersMetric.getProtectedFields().toString());
				System.out.println(
						"Protected Methods : " + numberOfProtectedMembersMetric.getProtectedMethods().toString());
				assertEquals("NProtMTest[protected field count] is wrong ",protectedFieldCount , numberOfProtectedMembersMetric.getProtectedFields().size(), 0);
				assertEquals("NProtMTest[protected method count] is wrong ", protectedMethodCount, numberOfProtectedMembersMetric.getProtectedMethods().size(), 0);
				assertEquals("NProtMTest[number of protected members] is wrong ",
						protectedMethodCount + protectedFieldCount, numberOfProtectedMembersMetric.getMetricResult(), 0);

				return true;
			}
		});

		System.out.println("NProtMTest stopped");
	}

	public void method1() {

	}

	private void method2() {

	}

	protected void method3() { // protected

	}

	protected void method4() { // protected

	}
}
