package tr.com.melihaltintas.eclipseintegration.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import tr.com.melihaltintas.thesisreportgenerator.BugInformation;
import tr.com.melihaltintas.thesisreportgenerator.ClassBugMapping;

public class GenerateThesisReport {

	@Execute
	public void execute(Shell shell) {

		BugInformation.getInstance().createThesisReport();

		System.out.println("Class count  : " + BugInformation.getInstance().getClassBugMappings().size());
		int bugFreeClass = 0;
		int smellFreeClass = 0;

		int bugClass = 0;
		int smellClass = 0;

		int bugCount = 0;
		int smellCount = 0;

		int maxBug = 0;
		int avarageBug = 0;
		
		int maxSmell = 0;
		int avarageSmell = 0;

		for (ClassBugMapping mapping : BugInformation.getInstance().getClassBugMappings().values()) {
			int bugSize = mapping.bug ;
			if (mapping.bug == 0) {
				bugFreeClass++;
			} else {
				bugClass++;
				maxBug = bugSize > maxBug ? bugSize : maxBug;
			}
			int smellSize = mapping.smells.size();
			if (smellSize == 0) {
				smellFreeClass++;

			} else {
				smellClass++;
				maxSmell = smellSize > maxSmell ? smellSize : maxSmell;
			}

			bugCount += mapping.bug;
			smellCount += mapping.smells.size();
		}
		System.out.println("Bug Free Class count  : " + bugFreeClass);
		System.out.println("Class which has bug count : " + bugClass);
		System.out.println("Total Bug Count : " + bugCount);

		System.out.println("Max Bug : " + maxBug);
		
		System.out.println("Avarage Bug : " + (double)bugCount/bugClass);

		System.out.println("Smell Free Class count  : " + smellFreeClass);
		System.out.println("Class which has smell count : " + smellClass);
		System.out.println("Total smell Count : " + smellCount);
		
		
		System.out.println("Max Smell : " + maxSmell);
		
		System.out.println("Avarage Smell : " + (double)smellCount/smellClass);

	}
}
