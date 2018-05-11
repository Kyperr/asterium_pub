package main;

import actiondata.CreateGameRequestData;
import actiondata.CreateGameResponseData;
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
		ccHandler.concurrentSend(msg, (message) -> {
			// Join the lobby.
			System.out.println("message: " + message.toString());
			
			// Get lobby ID
			CreateGameResponseData cgrData = CreateGameResponseData.fromMessage(message);
			String lobbyID = cgrData.getLobbyID();
			
			// Get GameBoardData
			JoinAsGameBoardRequestData.GameBoardData myData = new JoinAsGameBoardRequestData.GameBoardData();
			
			// Send JoinAsGameBoardRequest
			System.out.println("Game created! Joining game as GameBoard...");
			JoinAsGameBoardRequestData jgbData = new JoinAsGameBoardRequestData(lobbyID, myData);
			Request joinRequest = new Request(jgbData);
			String joinMessage = joinRequest.jsonify().toString();
			ccHandler.concurrentSend(joinMessage, (joinResponse) -> System.out.println("Joined!"));
		});
		
		while(true) {
			
		}
	}

}
