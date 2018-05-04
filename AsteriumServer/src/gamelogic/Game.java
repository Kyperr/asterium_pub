package gamelogic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import actions.Action;
import exceptions.GameFullException;

/**
 * Game representing a single game state. 
 * 
 * @author Daniel McBride, Jenna Hand, Bridgette Campbell, Greg Schmitt
 */
public class Game extends Thread {

	// TODO: turn into complex enum?
	private enum GamePhase {
		PLAYERS_JOINING, PLAYER_TURNS
	}
	
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
	
	private final String lobbyID;

	private final List<Player> playerList = new ArrayList<Player>();

	private final List<GameBoard> gameBoardList = new ArrayList<GameBoard>();

	
	
	/*
	 * The game's map of turn actions. Maps players to their turn action(s).
	 */
	private final Map<Player, Runnable> turnActionMap = new ConcurrentHashMap<Player, Runnable>(){

		@Override
		public Runnable put(Player player, Runnable runnable) {
			//Here is where we wake thread.
			synchronized (this) {
				Game.this.notify();
			}
			return super.put(player, runnable);
		}
	};
	
	private GamePhase phase = GamePhase.PLAYERS_JOINING;
	
	/**
	 * Creates and returns a {@link Game} that has a lobby ID.
	 */
	public Game() {
		lobbyID = generateLobbyID();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				synchronized (this) {
					wait();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// gp.do();
		}
	}

	/**
	 * @return The game's lobby ID, used to allow players to join the game.
	 */
	public String getLobbyID() {
		return lobbyID;
	}

	/**
	 * Register a new {@link Player} in the {@link Game}. They are added to the {@link Game}'s list of players.
	 * @param player The player client.
	 * @throws GameFullException When the game has already reached the max number of players.
	 */
	public synchronized void addPlayer(final Player player) throws GameFullException {
		// Check to see that the game is not already full.
		if (this.playerList.size() <= MAX_PLAYERS) {
			this.playerList.add(player);
			this.turnActionMap.put(player, new Runnable() {
				@Override
				public void run() {
					// do nothing, this is a "null" action
				}
			});
		} else {
			throw new GameFullException();
		}
	}
	
	
	/**
	 * Adds a turn action to the {@link Game}'s turnActionMap.
	 * 
	 * @param player {@link Player}
	 * @param action {@link Action}
	 */
	public void addTurnAction(final Player player, final Runnable runnable) {
		this.turnActionMap.put(player, runnable);
	}
	
	/*
	 * 
	 * @author Jenna
	 *
	 */
	private class GameState {
		
		Map<Player, Character> playerCharacterMap = new ConcurrentHashMap<Player, Character>();
	}

}
