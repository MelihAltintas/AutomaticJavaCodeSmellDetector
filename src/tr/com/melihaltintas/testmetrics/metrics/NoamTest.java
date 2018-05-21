package tr.com.melihaltintas.testmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.Test;

import tr.com.melihaltintas.smelldetector.visitors.AccessorVisitor;
import tr.com.melihaltintas.testmetrics.helper.ComplationUnitLoader;
import tr.com.melihaltintas.testmetrics.helper.DummyClass;

//NOAM - Number of Accessor Methods
//The number of accessor (getter and setter) methods of a class
//Used for Data Class

public class NoamTest {

	public int field1;
	private int field2;

	@Test
	public void noamTest() {
		System.out.println("NoamTest started");

		CompilationUnit unit = ComplationUnitLoader.getCompilationUnit(NoamTest.class);

		unit.accept(new ASTVisitor() {

			@Override
			public boolean visit(TypeDeclaration typeDeclaration) {
				
				AccessorVisitor accessorVisitor = new AccessorVisitor();
				typeDeclaration.accept(accessorVisitor);
				double metricResult = accessorVisitor.getMetricResult();

				System.out.println("NoamTest getters : " + accessorVisitor.getGetters().toString());
				System.out.println("NoamTest setters : " + accessorVisitor.getSetters().toString());
				assertEquals("NoamTest is wrong ", 5, metricResult, 0);

				return true;
			}
		});

		System.out.println("NoamTest stopped");
	}

	// no getter because return another class field not own
	public int getDummyValue() {
		DummyClass dummy = new DummyClass();
		return dummy.dummyField;
	}

	// no setter because set another class field not own
	public void setDummyValue(int k) {
		DummyClass dummy = new DummyClass();
		dummy.dummyField = k;
	}
	
	// no setter because set different value instead of parameter
	public void setField(int k) {
		this.field1 = 5;
	}

	// no getter or setter
	public int getValue() {
		int a = 5;
		return a;
	}

	// no setter or getter
	public void setValue(int c) {
		int a = c;
		System.out.println(a);
	}

	// getter
	public int getField1() { // +1
		return field1;
	}

	// getter
	public int getField2() { // +1
		return field2;
	}

	// setter
	public void setField1(int field1) { // +1
		this.field1 = field1;
	}

	// setter
	public void setField2(int field2) { // +1

		if (field2 < 0) {
			this.field2 = 0;
			return;
		}
		this.field2 = field2;
	}

	// setter
	public void setFieldByString(String c) { // +1
		this.field1 = Integer.parseInt(c);
	}

	// no setter or getter
	public int calculate(int a, int b) {
		return a + b;
	}

}
