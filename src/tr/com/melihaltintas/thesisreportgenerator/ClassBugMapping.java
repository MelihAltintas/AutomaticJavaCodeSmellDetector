package tr.com.melihaltintas.thesisreportgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ClassBugMapping {
	

	public String projectName;

	public String version;

	public String className;

	public HashMap<String, String> metricValues;
	
	public int bug;
	
	public List<Smell> smells;
	
	public ClassBugMapping() {
		metricValues = new HashMap<>();
		smells = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return className;
	}
	
	
	
}
