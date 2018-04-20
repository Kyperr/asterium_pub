package general;

public class PlayerData {
	
	/* Argument names */ 
	public static final String NAME = "name";
	
	protected String name;
	
	public PlayerData(final String name) {
		setName(name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
}
