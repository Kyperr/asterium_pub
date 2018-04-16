package general;

public class Player {
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
