package tr.com.melihaltintas.smelldetector.detectors.classificationdisharmonies;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator;
import tr.com.melihaltintas.smelldetector.markers.MarkerCreator.MarkerType;
import tr.com.melihaltintas.smelldetector.metricfactory.Enums.ClassMetricEnum;
import tr.com.melihaltintas.smelldetector.metrics.MetricCalculator;
import tr.com.melihaltintas.smelldetector.metrics.MetricThresholds;

public class TraditionBreaker extends ASTVisitor {

	private double NAS;
	private double PNAS;

	private double AMW;
	private double WMC;
	private double NOM;

	private double AMWFORBASECLASS;
	private double WMCFORBASECLASS;
	private double NOMFORBASECLASS;

	public boolean visit(TypeDeclaration typeDeclaration) {

		if (!Helper.isCandidateForRefusedParentBequestOrTraditionBreaker(typeDeclaration)) {
			return false;
		}
		ITypeBinding baseBinding = typeDeclaration.resolveBinding().getSuperclass();
		ICompilationUnit unit = (ICompilationUnit) baseBinding.getJavaElement()
				.getAncestor(IJavaElement.COMPILATION_UNIT);

		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		TypeDeclaration baseClass = (TypeDeclaration) cu.findDeclaringNode(baseBinding.getKey());

		NAS = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.NumberOfAddedServicesMetric);
		double numberOfPublicMethods = MetricCalculator.ClassMetricCalculate(typeDeclaration,
				ClassMetricEnum.NumberOfPublicMethodsMetric);
		PNAS = NAS / numberOfPublicMethods;

		WMC = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.WeightedMethodCount);
		NOM = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.NumberOfMethodsMetric);
		AMW = MetricCalculator.ClassMetricCalculate(typeDeclaration, ClassMetricEnum.AverageMethodWeightMetric);

		WMCFORBASECLASS = MetricCalculator.ClassMetricCalculate(baseClass, ClassMetricEnum.WeightedMethodCount);
		NOMFORBASECLASS = MetricCalculator.ClassMetricCalculate(baseClass, ClassMetricEnum.NumberOfMethodsMetric);
		AMWFORBASECLASS = MetricCalculator.ClassMetricCalculate(baseClass, ClassMetricEnum.AverageMethodWeightMetric);

		return true;
	}

	@Override
	public void endVisit(TypeDeclaration typeDeclaration) {
		if (!Helper.isCandidateForRefusedParentBequestOrTraditionBreaker(typeDeclaration)) {
			return;
		}
		System.out.println(">>> TraditionBreaker " + " >>> Class :  " + typeDeclaration.getName().toString()
				+ "  NAS : " + NAS + "  PNAS : " + PNAS + "  WMC : " + WMC + "  NOM : " + NOM + "  AMW : " + AMW
				+ "  WMCFORBASECLASS : " + WMCFORBASECLASS + "  NOMFORBASECLASS : " + NOMFORBASECLASS
				+ "  AMWFORBASECLASS : " + AMWFORBASECLASS);

		if (ExcessiveIncreaseOfChildClassInterface() && ChildClassHasSubstantialSizeAndComplexity()
				&& ParentClassIsNeitherSmallNorDumb()) {
			MarkerCreator.addMarker(typeDeclaration,MarkerType.TraditionBreaker);
			System.err.println(">>> TraditionBreaker " + " >>> Class :  " + typeDeclaration.getName().toString());
		}

	}

	private boolean ExcessiveIncreaseOfChildClassInterface() {
		// More newly added services than average NOM per class
		// Newly added services are dominant in child class
		if (NAS >= MetricThresholds.NOM_AVARAGE && PNAS >= MetricThresholds.TWOTHIRDS) {
			return true;
		}
		return false;
	}

	private boolean ChildClassHasSubstantialSizeAndComplexity() {
		// Method complexity in child class above average
		// Functional complexity of child class is very high
		// Class has substantial number of methods
		if ((AMW > MetricThresholds.AMW_AVARAGE || WMC >= MetricThresholds.WMC_VERY_HIGH_THRESHOLD)
				&& NOM >= MetricThresholds.NOM_HIGH) {
			return true;
		}
		return false;
	}

	private boolean ParentClassIsNeitherSmallNorDumb() {
		// Parent functional complexity above average
		// Parent has more than half of child's methods
		// Parent's complexity more than half of child'

		if (AMWFORBASECLASS > MetricThresholds.AMW_AVARAGE && NOMFORBASECLASS > (MetricThresholds.NOM_HIGH / 2)
				&& WMCFORBASECLASS >= (MetricThresholds.WMC_VERY_HIGH_THRESHOLD / 2)) {
			return true;
		}
		return false;
	}

}
