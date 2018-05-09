package actiondata;

/**
 * {@link AbstractActionData} is a blueprint for {@link ActionData} objects.
 * 
 * @author Studio Toozo
 *
 */
public abstract class AbstractActionData implements ActionData {

	protected final String name;

	protected AbstractActionData(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public boolean equals(final Object other) {
		if (other instanceof AbstractActionData) {
			AbstractActionData data = AbstractActionData.class.cast(other);
			return data.name.equals(this.name); 
		} else {
			return false;
		}
	}
}
