package main;

import actiondata.CreateGameRequestData;
import actiondata.JoinAsGameBoardRequestData;
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
		
		// Create a game.
		CreateGameRequestData cgData = new CreateGameRequestData();
		Request request = new Request(cgData);
		String msg = request.jsonify().toString();
		if (VERBOSE) {
			System.out.println("Client sending message to server:\n" + msg);
		}
		ccHandler.sendJSON(msg, (message) -> {
			String lobbyID = null;
			JoinAsGameBoardRequestData.GameBoardData myData = new JoinAsGameBoardRequestData.GameBoardData();
			System.out.println("Game created! Joining game as GameBoard...");
			System.out.println("message: " + message.toString());
			//JoinAsGameBoardRequestData jgbData = new JoinAsGameBoardRequestData(lobbyID, myData);
		});
		
		while(true) {
			
		}
	}

}
