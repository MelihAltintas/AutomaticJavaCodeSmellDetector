package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import tr.com.melihaltintas.smelldetector.visitors.CycloVisitor;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

import org.junit.Test;;

//CYCLO - McCabe’s Cyclomatic Number
//The number of linearly-independent paths through an operation [McC76]
//Used for Brain Method

public class CycloTest {

	@Test
	public void cycloTest() {
		System.out.println("CycloTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(CycloTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(MethodDeclaration methodDeclaration) {
				CycloVisitor cycloVisitor = new CycloVisitor(methodDeclaration);
				methodDeclaration.accept(cycloVisitor);
				double metricResult = cycloVisitor.getMetricResult();
				System.out.println("Cyclo is calculated for : " + methodDeclaration.getName().toString() + " result : "
						+ metricResult);
				if (methodDeclaration.getName().toString().equals("cycloValue1")) {
					assertEquals("Cyclo of cycloValue1() is wrong ", 1, metricResult, 0);
				} else if (methodDeclaration.getName().toString().equals("cycloValue2")) {
					assertEquals("Cyclo of cycloValue2() is wrong ", 2, metricResult, 0);
				} else if (methodDeclaration.getName().toString().equals("cycloValue10")) {
					assertEquals("Cyclo of cycloValue10() is wrong ", 10, metricResult, 0);
				}
				return true;
			}
		});

		System.out.println("CycloTest stopped");
	}

	public void cycloValue1() { // cyclo = 1

	}

	public void cycloValue2() { // cyclo = 2
		int a = 1;
		int b = 2;
		if (a > b) {
			System.out.println(a > b);
		} else {
			System.out.println(b > a);

		}

	}

	public void cycloValue10() { // cyclo = 10
		int a = 1;
		int b = 2;
		boolean t = true;
		boolean f = false;
		if (f && (b == 2 ? t : false)) { // +3
			if (a == b) { // +1
				while (true) { // +1
					if (b < 15) { // +1
						break; // +1
					}
				}
			} else if (b == a && !f) { // +2
				b = t ? 4 : 5; // +1
			} else {
				a = b;
			}
		}
	}
	

}
