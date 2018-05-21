package tr.com.melihaltintas.smelldetector.markers;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import tr.com.melihaltintas.smelldetector.detectors.Helper;
import tr.com.melihaltintas.thesisreportgenerator.BugInformation;
import tr.com.melihaltintas.thesisreportgenerator.ClassBugMapping;
import tr.com.melihaltintas.thesisreportgenerator.Smell;
import tr.com.melihaltintas.thesisreportgenerator.Smell.SmellType;

public class MarkerCreator {

	public enum MarkerType {
		DataClass, GodClass, BrainClass, RefusedParentBequest, TraditionBreaker, FeatureEnvy, BrainMethod, ShotgunSurgery, IntensiveCoupling, DispersedCoupling,
	}

	public static void addMarker(TypeDeclaration typeDeclaration, MarkerType markerType) {

		IResource resource = typeDeclaration.resolveBinding().getJavaElement().getResource();
		addMarker(resource, markerType, Helper.getStartPosition(typeDeclaration),
				Helper.getEndPosition(typeDeclaration));

		if (BugInformation.getInstance().isAvailable()) {
			String className = typeDeclaration.resolveBinding().getQualifiedName();
			ClassBugMapping classBug = BugInformation.getInstance().getClassBugMapping(className);
			Smell smell = new Smell(markerType.toString(),SmellType.Class);
			if(classBug != null) {
				classBug.smells.add(smell);
			}else {
				/*classBug = new ClassBugMapping();
				classBug.className = "!!!BULUNAMADI BAK !!" + className;
				classBug.smells.add(smell);
				BugInformation.getInstance().getClassBugMappings().put(classBug.className, classBug);
				System.err.println("BULUNAMADI ==> " +className);*/
			}
		}
	}

	public static void addMarker(MethodDeclaration methodDeclaration, MarkerType markerType) {

		IResource resource = methodDeclaration.resolveBinding().getJavaElement().getResource();
		addMarker(resource, markerType, Helper.getStartPosition(methodDeclaration),
				Helper.getEndPosition(methodDeclaration));
		
		if (BugInformation.getInstance().isAvailable()) {
			String className = methodDeclaration.resolveBinding().getDeclaringClass().getQualifiedName();
			ClassBugMapping classBug = BugInformation.getInstance().getClassBugMapping(className);
			Smell smell = new Smell(markerType.toString(),methodDeclaration.getName().toString(),SmellType.Method,Helper.getStartPosition(methodDeclaration));
			if(classBug != null) {

				classBug.smells.add(smell);
			}else {
				
			/*	classBug = new ClassBugMapping();
				classBug.className = "!!!BULUNAMADI BAK !!" + className;
				classBug.smells.add(smell);
				BugInformation.getInstance().getClassBugMappings().put(classBug.className, classBug);
				System.err.println("BULUNAMADI ==> " +className);*/
			}
		}

	}

	private static void addMarker(IResource resource, MarkerType markerType, int startIndex, int stopIndex) {

		IMarker marker;
		try {
			marker = resource.createMarker("tr.com.melihaltintas.codesmellmarker");
			marker.setAttribute(IMarker.MESSAGE, markerType.toString());
			marker.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			marker.setAttribute(IMarker.LINE_NUMBER, startIndex);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
