package gamelogic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import exceptions.GameFullException;

/**
 * {@link Game} representing a single game state. 
 * 
 * @author Studio Toozo
 */
public class Game {

	/*
	 * The character set used to generate random strings.
	 */
	private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/*
	 * The maximum number of players allowed to join a game.
	 */
	private static final int MAX_PLAYERS = 16;

	/*
	 * Used to generate random strings for lobby IDs.
	 */
	private static final SecureRandom RANDOM = new SecureRandom();

	/*
	 * The length of lobby IDs generated.
	 */
	private static final int TOKEN_LENGTH = 5;

	/*
	 * Generates a sequence of random, upper case letters to be used as a lobby ID. 
	 */
	private static String generateLobbyID() {
		// For building the sequence of random letters.
		StringBuilder sb = new StringBuilder();

		// Append the next random letter to the string.
		for (int i = 0; i < TOKEN_LENGTH; i++) {
			sb.append(CHAR_SET.charAt(RANDOM.nextInt(CHAR_SET.length())));
		}
		
		// If the generated lobby ID is in use in another game, try again.
		if (GameManager.getInstance().isLobbyIDUsed(sb.toString())) {
			return generateLobbyID();
		}
		
		// Once the lobby ID is unique, return it.
		return sb.toString();
	}

	/*
	 * The game's list of player clients. 
	 */
	private final List<Player> playerList = new ArrayList<Player>();

	/*
	 * The game's list of gameboard clients.
	 */
	private final List<GameBoard> gameBoardList = new ArrayList<GameBoard>();

	/*
	 * The game's lobby ID, used to allow player clients to join.
	 */
	private final String lobbyID;

	/**
	 * Creates and returns a {@link Game} that has a lobby ID.
	 */
	public Game() {
		lobbyID = generateLobbyID();
	}

	/**
	 * @return The {@link Game}'s lobby ID, used to allow players to join the game.
	 */
	public String getLobbyID() {
		return lobbyID;
	}

	/**
	 * Register a new {@link Player} in the {@link Game}. They are added to the {@link Game}'s list of players.
	 * @param player The player client.
	 * @throws GameFullException When the game has already reached the max number of players.
	 */
	public void addPlayer(final Player player) throws GameFullException {
		// Check to see that the game is not already full.
		if (this.playerList.size() <= MAX_PLAYERS) {
			this.playerList.add(player);
		} else {
			throw new GameFullException();
		}
	}
	
	/**
	 * Registers a new {@link GameBoard} in the {@link Game}. They are added to the {@link Game}'s list of GameBoards.
	 * 
	 * @param gameBoard The game board client.
	 */
	public void addGameBoard(final GameBoard gameBoard) {
		this.gameBoardList.add(gameBoard);
	}

}
