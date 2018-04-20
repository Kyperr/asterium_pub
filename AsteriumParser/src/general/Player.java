package general;

public class Player {
	
	/* Argument names */ 
	public static final String NAME = "name";
	
	protected String name;
	
	public Player(final String name) {
		setName(name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
}
