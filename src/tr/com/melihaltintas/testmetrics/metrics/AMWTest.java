package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfMethodsMetric;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.WeightedMethodCountMetric;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//AMW - Average Method Weight
//The average static complexity of all methods in a class. McCabe’s cyclomatic
//number is used to quantify the method’s complexity 
//Used for Refused Parent Bequest, Tradition Breaker

public class AMWTest {

	@Test
	public void amwTest() { // cyclo = 1

		System.out.println("AMWTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(AMWTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {

				WeightedMethodCountMetric weightedMethodCountMetric = new WeightedMethodCountMetric();
				typeDeclaration.accept(weightedMethodCountMetric);
				double WMC = weightedMethodCountMetric.getMetricResult();

				NumberOfMethodsMetric numberOfMethodsMetric = new NumberOfMethodsMetric();
				typeDeclaration.accept(numberOfMethodsMetric);
				double NOM = numberOfMethodsMetric.getMetricResult();

				assertEquals("AMWTest is wrong ", 14 /(double) 4, WMC / NOM, 0);

				return true;
			}
		});

		System.out.println("AMWTest stopped");
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
