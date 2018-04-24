package main;

import actiondata.ActionData;
import actiondata.JoinAsPlayerActionData;
import actiondata.JoinAsPlayerActionData.PlayerData;
import message.Message;
import message.Request;

public class Example {

	public static final String JSON_FORMAT_ERR = "JSON not formatted properly.";
	public static final String JSON_EMPTY_ERR = "Empty field %s.";

	public static final Runnable CONST_ERROR_ACTION = new Runnable() {

		@Override
		public void run() {
			System.out.println("Fuck.");
		}
	};

	public static void main(String[] args) {

		Parser parser = new Parser();

		// String msg =
		// "{\"request\":{\"auth_token\":\"12345\",\"action_name\":\"join_as_player\",\"join_as_player\":{\"lobby_id\":\"Fuck
		// Yeah\",\"player_data\":{\"name\":\"Lieutenant Dudefella\"}}}}";

		// ActionData ad = parser.parseToActionData(msg);

		JoinAsPlayerActionData.PlayerData pd = new PlayerData("Daniel");
		ActionData joinAsPlayer = new JoinAsPlayerActionData("abcd", pd);

		Message message = new Request(joinAsPlayer);

		// Request r = new Request("join_as_player", joinAsPlayer.jsonify);

		System.out.println(message.jsonify().toString());

	}

}
