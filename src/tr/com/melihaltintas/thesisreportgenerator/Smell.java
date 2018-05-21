package tr.com.melihaltintas.thesisreportgenerator;

public class Smell {

	public enum SmellType {
		Class, Method
	}

	private String smellName;
	private String methodName; // if smell type method
	private SmellType smellType;
	private int lineNum;

	public Smell(String smellName, SmellType smellType) {
		super();
		this.smellName = smellName;
		this.smellType = smellType;
	}

	public Smell(String smellName, String methodName, SmellType smellType, int lineNum) {
		super();
		this.smellName = smellName;
		this.methodName = methodName;
		this.smellType = smellType;
		this.lineNum = lineNum;
	}

	@Override
	public String toString() {
		if (smellType == SmellType.Class) {
			return "[ " + smellName + " ]";
		} else {
			return "[ " + smellName + " ==> method name : " + methodName + "(Line Num : "+lineNum+" )"+" ]";
		}
	}

	public String getSmellName() {
		return smellName;
	}

	public void setSmellName(String smellName) {
		this.smellName = smellName;
	}
	
	

}
