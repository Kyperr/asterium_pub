package actiondata;

import org.json.JSONObject;

public abstract class AbstractResponseActionData extends AbstractActionData {

	public AbstractResponseActionData(final String name) {
		super(name);
	}

	@Override
	public abstract JSONObject jsonify();

}
