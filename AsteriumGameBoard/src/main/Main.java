package main;

import org.json.JSONObject;

import actiondata.CreateGameActionData;
import message.Request;

public class Main {

	private static int PORT = 25632;

	public static void main(String[] args) {

		
		ClientConnectionHandler ccHandler = new ClientConnectionHandler();
		
		CreateGameActionData cgaData = new CreateGameActionData();
		
		Request request = new Request(cgaData);
		
		String msg = request.jsonify().toString();
		System.out.println("Message in main: " + msg);
		
		ccHandler.sendJSON(msg, (message) -> System.out.println(message.jsonify().toString()));
		
		while(true) {
			
		}
	}

}
