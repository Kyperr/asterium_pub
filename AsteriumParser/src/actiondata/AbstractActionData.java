package actiondata;

public abstract class AbstractActionData implements ActionData {

	protected final String name;

	protected AbstractActionData(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public boolean equals(final Object other) {
		return other instanceof AbstractActionData && 
			   ((AbstractActionData) other).name.equals(this.name) && 
			   fieldsEqual(other);
	}
	
	protected abstract boolean fieldsEqual(final Object other);
}
