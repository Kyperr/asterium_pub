package main;

import actiondata.CreateGameRequestData;
import message.Request;
/**
 * Main.
 *
 */
public class Main {
	public static final boolean VERBOSE = true;

	private static final int PORT = 25632;
	
	private static String ADDRESS = "localhost"; 
	
	/**
	 * Creates a game.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		ClientConnectionHandler ccHandler = new ClientConnectionHandler(ADDRESS, PORT);
		
		CreateGameRequestData cgaData = new CreateGameRequestData();
		
		Request request = new Request(cgaData, "");
		
		String msg = request.jsonify().toString();
		
		if (VERBOSE) {
			System.out.println("Client sending message to server:\n" + msg);
		}
		
		ccHandler.sendJSON(msg, (message) -> System.out.println("Game created!"));
		
		while(true) {
			
		}
	}

}
