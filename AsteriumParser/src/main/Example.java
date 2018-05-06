package main;

import actiondata.ActionData;
import actiondata.JoinAsPlayerRequestData;
import actiondata.JoinAsPlayerRequestData.PlayerData;
import message.Message;
import message.Request;

/**
 * The Example for AsteriumParser.
 * 
 * @author Studio Toozo
 *
 */
public class Example {

	public static final String JSON_FORMAT_ERR = "JSON not formatted properly.";
	public static final String JSON_EMPTY_ERR = "Empty field %s.";

	public static final Runnable CONST_ERROR_ACTION = new Runnable() {

		@Override
		public void run() {
			System.out.println("Error in Example");
		}
	};

	public static void main(String[] args) {

		JoinAsPlayerRequestData.PlayerData pd = new PlayerData("Daniel");
		ActionData joinAsPlayer = new JoinAsPlayerRequestData("abcd", pd);

		Message message = new Request(joinAsPlayer, "");
		System.out.println(message.jsonify().toString());

	}

}
