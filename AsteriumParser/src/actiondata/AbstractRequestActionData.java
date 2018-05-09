package actiondata;

import org.json.JSONObject;

public abstract class AbstractRequestActionData extends AbstractActionData {

	public AbstractRequestActionData(final String name) {
		super(name);
	}

	@Override
	public abstract JSONObject jsonify();

}
