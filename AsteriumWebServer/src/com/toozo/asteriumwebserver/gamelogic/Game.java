package com.toozo.asteriumwebserver.gamelogic;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.toozo.asteriumwebserver.actions.Action;
import com.toozo.asteriumwebserver.exceptions.GameFullException;

/**
 * {@link Game} representing a single game state.
 * 
 * @author Studio Toozo
 */
public class Game extends Thread {

	// ===== STATIC FIELDS =====
	//The character set used to generate random strings.
	private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// The maximum number of players allowed to join a game.
	private static final int MAX_PLAYERS = 16;

	// Used to generate random strings for lobby IDs.
	private static final SecureRandom RANDOM = new SecureRandom();

	// The length of lobby IDs generated.
	private static final int TOKEN_LENGTH = 5;
	// =========================

	// ===== INSTANCE FIELDS =====
	// Indicates that players still need this game object.
	private boolean isNotAbandoned = true;
	
	private final String lobbyID;
	
	private final Map<String, Player> playerList = new ConcurrentHashMap<String, Player>();
	
	private Map<String, Boolean> playerReadyMap;
	
	private final Map<String, GameBoard> gameBoardList = new ConcurrentHashMap<String, GameBoard>();

	private GameState gameState;
	
	/*
	 * The game's map of turn actions. Maps players to their turn action(s).
	 */
	private final Map<Player, Runnable> turnActionMap = new ConcurrentHashMap<Player, Runnable>() {
		private static final long serialVersionUID = 1L;

		@Override
		public Runnable put(Player player, Runnable runnable) {
			return super.put(player, runnable);
		}
	};
	// ===========================

	// ===== STATIC METHODS =====
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
	// ==========================

	// ===== CONSTRUCTORS =====
	/**
	 * Creates and returns a {@link Game} that has a lobby ID.
	 */
	public Game() {
		lobbyID = generateLobbyID();
		gameState = new GameState(this);

		this.playerReadyMap = new ConcurrentHashMap<String, Boolean>();
	}
	// ========================
	
	// ===== GETTERS =====
	/**
	 * @return The {@link Game}'s lobby ID, used to allow players to join the game.
	 */
	public String getLobbyID() {
		return lobbyID;
	}
	
	/**
	 * @return a {@link Collection} of {@link Player}s that are in this Game.
	 */
	public Collection<Player> getPlayers() {
		return playerList.values();
	}
	
	public Collection<GameBoard> getGameBoards() {
		return this.gameBoardList.values();
	}


	public Player getPlayer(String authToken) {
		return playerList.get(authToken);
	}


	public GameState getGameState() {
		return gameState;
	}
	
	public boolean allCharactersReady() {
		for (Boolean bool : playerReadyMap.values()) {
			if (!bool) {
				return false;
			}
		}
		return true;
	}
	// ===================
	
	// ===== SETTERS =====
	/**
	 * Toggles whether the {@link PlayerCharacter} belonging
	 * to the {@link Player} with authToken is ready or not.
	 * 
	 * @param authToken The auth token of the {@link Player}
	 */
	public synchronized boolean toggleReady(final String authToken) {
			boolean isReady = !playerReadyMap.get(authToken);
			this.playerReadyMap.put(authToken, isReady);
			this.getGameState().executePhase();
			return isReady;
	}
	
	public void setAllCharactersNotReady() {
		for(String auth : playerReadyMap.keySet()) {
			playerReadyMap.put(auth, false);
		}
	}
	// ===================
	
	// ===== OTHER INSTANCE METHODS =====
	@Override
	public void run() {
		while (isNotAbandoned) {
			try {
				synchronized (this) {
					wait();
				}
				System.err.println("Executing game phase.");
				GameState state = this.getGameState();
				state.executePhase();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void executePhase() {
		synchronized (this) {
			notify();
		}
	}

	/**
	 * Register a new {@link Player} in the {@link Game}. They are added to the
	 * {@link Game}'s list of players.
	 * 
	 * @param player
	 *            The player client.
	 * @throws GameFullException
	 *             When the game has already reached the max number of players.
	 */
	public synchronized void addPlayer(final Player player) throws GameFullException {
		// Check to see that the game is not already full.
		if (this.playerList.size() <= MAX_PLAYERS) {
			// Add Player to Game
			String authToken = player.getAuthToken();
			this.playerList.put(authToken, player);
			this.playerReadyMap.put(authToken, false);
			
			// Add PlayerCharacter to GameState
			PlayerCharacter character = new PlayerCharacter();
			this.getGameState().addPlayerCharacter(authToken, character);
			
			// Register with GameManager
			GameManager.getInstance().registerPlayerToGame(player.getAuthToken(), this);
		} else {
			throw new GameFullException();
		}
	}

	/**
	 * Registers a new {@link GameBoard} in the {@link Game}. They are added to the
	 * {@link Game}'s list of GameBoards.
	 * 
	 * @param gameBoard
	 *            The game board client.
	 */
	public void addGameBoard(final GameBoard gameBoard) {
		this.gameBoardList.put(gameBoard.getAuthToken(), gameBoard);
	}

	/**
	 * Adds a turn action to the {@link Game}'s turnActionMap.
	 * 
	 * @param player
	 *            {@link Player}
	 * @param action
	 *            {@link Action}
	 */
	public void addTurnAction(final Player player, final Runnable runnable) {
		this.turnActionMap.put(player, runnable);
	}
	// ==================================
}
