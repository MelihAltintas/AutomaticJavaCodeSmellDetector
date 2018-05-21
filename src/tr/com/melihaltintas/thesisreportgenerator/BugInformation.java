package tr.com.melihaltintas.thesisreportgenerator;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class BugInformation {
	
	private static final BugInformation bugInformation = new BugInformation();
	private  HashMap<String,ClassBugMapping> classBugMappings;
	
    private BugInformation() {
    	
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		
		String promiseDataPath = root.getLocation().toString() + "/promisedata.csv";
		
		classBugMappings = CsvProcess.readCsv(promiseDataPath);
    }

    public static BugInformation getInstance() {
        return bugInformation; 
    }
    
    public void createThesisReport() {
    	
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		
		String resultPath = root.getLocation().toString() + "/promisedatasmellmapping.csv";
		CsvProcess.writeCsv(resultPath, classBugMappings);
		
		String faultNonFaultPath = root.getLocation().toString() + "/faultnonfault.csv";
		CsvProcess.writeFaultNonFaultTable(faultNonFaultPath, classBugMappings);
		
		String correlationPath = root.getLocation().toString() + "/correlation.csv";
		CsvProcess.writeCorrelationTable(correlationPath, classBugMappings);
    }

	public ClassBugMapping getClassBugMapping(String ClassName) {
		return classBugMappings.get(ClassName);
	}
	
	public boolean isAvailable() {
		
		return classBugMappings != null;
	}

	public HashMap<String, ClassBugMapping> getClassBugMappings() {
		return classBugMappings;
	}
	
	
	
	
	
    
    
}
