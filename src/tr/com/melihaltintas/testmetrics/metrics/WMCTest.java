package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;
import tr.com.melihaltintas.smelldetector.metrics.classmetrics.WeightedMethodCountMetric;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;


//WMC - Weighted Method Count
//The sum of the statical complexity of all methods of a class. The CYCLO metric
//is used to quantify the method’s complexity
//Used for Refused Parent Bequest, Tradition Breaker, God Class, Data Class, Brain Class

public class WMCTest {  // wmc = 14 [sum cyclo]

	@Test
	public void wmcTest() {   // cyclo = 1
		System.out.println("WMCTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(WMCTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {
				
				WeightedMethodCountMetric weightedMethodCountMetric = new WeightedMethodCountMetric();
				typeDeclaration.accept(weightedMethodCountMetric);
				double metricResult = weightedMethodCountMetric.getMetricResult();

				
				assertEquals("WMCTest is wrong ", 14, metricResult, 0);

				return true;
			}
		});

		System.out.println("WMCTest stopped");
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
