package gamelogic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import sessionmanagement.SessionManager.Session;

public class Game {

	private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private static final SecureRandom RANDOM = new SecureRandom();
	
	private static final int TOKEN_LENGTH = 5;
	
	private static String generateLobbyID() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < TOKEN_LENGTH; i++) {
			sb.append(CHAR_SET.charAt(RANDOM.nextInt(CHAR_SET.length())));
		}
		if (GameManager.getInstance().isLobbyIDUsed(sb.toString())) {
			return generateLobbyID();
		} 
		return sb.toString();
	}
	
	private final List<Player> playerList = new ArrayList<Player>();
	
	private final List<GameBoard> gameBoardList = new ArrayList<GameBoard>();
	
	private final String lobbyID;
	
	public Game() {
		lobbyID = generateLobbyID();
	}
	
	public String getLobbyID() {
		return lobbyID;
	}

}
