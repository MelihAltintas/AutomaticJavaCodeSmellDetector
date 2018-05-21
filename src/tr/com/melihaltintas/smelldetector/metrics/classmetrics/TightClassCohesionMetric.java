package tr.com.melihaltintas.smelldetector.metrics.classmetrics;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.detectors.MethodPair;
import tr.com.melihaltintas.smelldetector.metrics.MetricVisitor;
import tr.com.melihaltintas.smelldetector.visitors.TightClassCohesionVisitor;


//TCC - Tight Class Cohesion
//The relative number of method pairs of a class that access in common at
//least one attribute of the measured class 
//Used for God Class, Brain Class

public class TightClassCohesionMetric extends MetricVisitor {

	private HashMap<MethodDeclaration, HashSet<String>> methodFieldsMap = new HashMap<MethodDeclaration, HashSet<String>>();
	private ArrayList<MethodDeclaration> allMethods = new ArrayList<>();
	private ArrayList<MethodPair<String,String>> methodPairs= new ArrayList<>();
	
	
	public boolean visit(TypeDeclaration typeDeclaration) {

		MethodDeclaration[] methods = typeDeclaration.getMethods();

		for (MethodDeclaration method : methods) {
			
			if (!method.isConstructor() && !Modifier.isAbstract(method.getModifiers())) { 
				
				TightClassCohesionVisitor tightClassCohesionVisitor = new TightClassCohesionVisitor(typeDeclaration);
				method.accept(tightClassCohesionVisitor);
				allMethods.add(method);
				methodFieldsMap.put(method, tightClassCohesionVisitor.getUsedClassFieldNames()); // method ==> used field mapping

			}

		}
		return true;
	}

	public int calculateNDC() {
		int ndc = 0;

		for (int i = 0; i < allMethods.size(); i++) {
			for (int j = i + 1; j < allMethods.size(); j++) {
				
				MethodDeclaration method1 = allMethods.get(i);   // method1
				MethodDeclaration method2 = allMethods.get(j);    // method2
				
				HashSet<String> usedFields1 = methodFieldsMap.get(method1);  // used fields from method1
				HashSet<String> usedFields2 = methodFieldsMap.get(method2);   // used fields from method2
				
				if (usedFields1 != null && usedFields2 != null) {
					if (!Collections.disjoint(usedFields1, usedFields2)) {  // if methods have common field
						MethodPair<String,String> pair = new MethodPair<String,String>(method1.resolveBinding().getKey(),method2.resolveBinding().getKey());
						methodPairs.add(pair);
						ndc++;   // methods are pair
					}
				}

			}
		}
		return ndc;
	}

	private int calculateNP() {  // number of possible  method pairs

		int numberOfMethods = allMethods.size();
		if (numberOfMethods == 1 || numberOfMethods == 0) {

			return 0;
		}
		int allPossibleMethodPair = (numberOfMethods * (numberOfMethods - 1)) / 2;
		return allPossibleMethodPair;

	}

	@Override
	public double getMetricResult() {  // tcc =  ndc / np

		if (allMethods.size() == 1 || allMethods.size() == 0) {
			return 1;
		}
		double ndc = (double) calculateNDC();
		double np = (double) calculateNP();

		if (np == 0)
			return 1;

		return ndc / np;
	}

	public ArrayList<MethodPair<String, String>> getMethodPairs() {
		return methodPairs;
	}
	
}
