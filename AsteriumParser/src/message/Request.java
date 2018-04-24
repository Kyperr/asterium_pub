package message;

import org.json.JSONObject;

import actiondata.ActionData;

public class Request extends Message {

	public Request(ActionData actionData) {
		super(Message.MessageType.REQUEST, actionData);
	}

	@Override
	protected void addToJSONify(JSONObject jo) {
		
	}

}
