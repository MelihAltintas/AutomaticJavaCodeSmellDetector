package tr.com.melihaltintas.thesisreportgenerator;

public class SmellFault{
	
	private String smellName;
	private int fault; 
	private int nonFault;
	
	
	public SmellFault(String smellName, int fault, int nonFault) {
		super();
		this.smellName = smellName;
		this.fault = fault;
		this.nonFault = nonFault;
	}
	
	public String getSmellName() {
		return smellName;
	}
	public void setSmellName(String smellName) {
		this.smellName = smellName;
	}
	public int getFault() {
		return fault;
	}
	public void setFault(int fault) {
		this.fault = fault;
	}
	public int getNonFault() {
		return nonFault;
	}
	public void setNonFault(int nonFault) {
		this.nonFault = nonFault;
	}
	
	
}
