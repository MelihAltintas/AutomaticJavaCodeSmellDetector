package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.visitors.MaxNestingLevelVisitor;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//MAXNESTING - Maximum Nesting Level
//The maximum nesting level of control structures within an operation
//Used for Intensive Coupling, Dispersed Coupling

public class NestingLevelTest {

	@Test
	public void nestingLevelTest() {

		System.out.println("nestingLevelTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(NestingLevelTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration methodDeclaration) {
				MaxNestingLevelVisitor maxNestingLevelVisitor = new MaxNestingLevelVisitor();
				methodDeclaration.accept(maxNestingLevelVisitor);
				double metricResult = maxNestingLevelVisitor.getMetricResult();

				System.out.println("NestingLevel is calculated for : " + methodDeclaration.getName().toString()
						+ " result : " + metricResult);
				if (methodDeclaration.getName().toString().equals("NestingLevel0")) {
					assertEquals("nestingLevel of NestingLevel0() is wrong ", 0, metricResult, 0);
				} else if (methodDeclaration.getName().toString().equals("NestingLevel1")) {
					assertEquals("nestingLevel of NestingLevel0() is wrong ", 1, metricResult, 0);
				} else if (methodDeclaration.getName().toString().equals("NestingLevel2")) {
					assertEquals("nestingLevel of NestingLevel2() is wrong ", 2, metricResult, 0);
				} else if (methodDeclaration.getName().toString().equals("NestingLevel4")) {
					assertEquals("nestingLevel of NestingLevel5()) is wrong ", 4, metricResult, 0);
				}
				return true;
			}
		});

		System.out.println("nestingLevelTest stopped");
	}

	public void NestingLevel0() {
		System.out.println("0");
	}

	public void NestingLevel1() {
		int a = 1;
		if (a > 1) { // 1
			System.out.println("1");
		} else {
			System.out.println("!1");
		}
	}

	public void NestingLevel2() {
		int a = 1;
		if (a > 1) { // 1
			while (true) { // 2
				System.out.println(true);
			}
		} else {
			System.out.println("!1");
		}
	}

	public void NestingLevel4() {
		int a = 1;
		if (a > 1) { // 1
			while (true) { // 2
				if (a == 2) { // 3
					break;
				} else {
					for (int i = 0; i < 10; i++) { // 4
						System.out.println(i);
					}
				}
			}
		} else {
			System.out.println("!1");
		}
	}
}
