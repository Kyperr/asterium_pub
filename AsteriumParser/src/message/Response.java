package message;

import org.json.JSONObject;

import actiondata.ActionData;

public class Response  extends Message {

	private Integer errorCode;
	
	public Response(ActionData actionData, Integer errorCode) {
		super(Message.MessageType.REQUEST, actionData);
		this.errorCode = errorCode;
	}

	@Override
	protected void addToJSONify(JSONObject jsonObject) {
		jsonObject.put("error_code", this.errorCode.toString());
	}

}