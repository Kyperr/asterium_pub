package main;

import java.net.URI;
import java.net.URISyntaxException;

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

	private static final String SERVER_ADDRESS = "ws://localhost:8080/AsteriumWebServer/Game";
	
	/**
	 * Creates a game.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		ClientConnectionHandler ccHandler = new ClientConnectionHandler(SERVER_ADDRESS);

		CreateGameRequestData cgaData = new CreateGameRequestData();

		Request request = new Request(cgaData, "");
		String msg = request.jsonify().toString();

		if (VERBOSE) {
			System.out.println("Client sending message to server:\n" + msg);
		}

		ccHandler.send(msg, (message) -> {
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
			Request joinRequest = new Request(jgbData, "");
			String joinMessage = joinRequest.jsonify().toString();
			ccHandler.send(joinMessage, (joinResponse) -> System.out.println("Joined!"));
		});
		
		
	}

}
