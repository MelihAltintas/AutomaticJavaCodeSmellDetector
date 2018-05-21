package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.metrics.classmetrics.NumberOfPublicAttributesMetric;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;

//NOPA - Number of Public Attributes
//The number of public attributes of a class  [no static no final]
//Used for Data Class  

public class NOPATest {

	
	private int field1;  
	protected final int field2 = 3;
	public final int field3 =4;
	public static  int field4;
	private int field5;
	public  int field7;  //+1
	public  int field8;  //+1
	public  int field9;  //+1
	
	
	@Test
	public void nopaTest() {   
		System.out.println("NOPATest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(NOPATest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {
				
				NumberOfPublicAttributesMetric numberOfPublicAttributesMetric = new NumberOfPublicAttributesMetric();
				typeDeclaration.accept(numberOfPublicAttributesMetric);
				double metricResult = numberOfPublicAttributesMetric.getMetricResult();

				
				assertEquals("NOPATest is wrong ", 3, metricResult, 0);

				return true;
			}
		});

		System.out.println("NOPATest stopped");
	}
}
