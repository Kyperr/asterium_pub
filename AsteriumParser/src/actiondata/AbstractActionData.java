package actiondata;

public abstract class AbstractActionData implements ActionData {

	protected final String name;
	
	protected AbstractActionData(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
