package main;

import actiondata.CreateGameRequestData;
import message.Request;
/**
 * Main.
 *
 */
public class Main {

	private static int PORT = 25632;
	
	private static String ADDRESS = "localhost"; 
	
	/**
	 * Creates a game.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		
		ClientConnectionHandler ccHandler = new ClientConnectionHandler(ADDRESS, PORT);
		
		CreateGameRequestData cgaData = new CreateGameRequestData();
		
		Request request = new Request(cgaData);
		
		String msg = request.jsonify().toString();
		System.out.println("Message in main: " + msg);
		
		ccHandler.sendJSON(msg, (message) -> System.out.println("Game created!"));
		
		while(true) {
			
		}
	}

}
