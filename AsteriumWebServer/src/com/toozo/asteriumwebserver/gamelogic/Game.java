package com.toozo.asteriumwebserver.gamelogic;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.actions.Action;
import com.toozo.asteriumwebserver.exceptions.GameFullException;
import com.toozo.asteriumwebserver.exceptions.InvalidNameException;
import com.toozo.asteriumwebserver.exceptions.PlayerNameTakenException;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

/**
 * {@link Game} representing a single game state.
 * 
 * @author Studio Toozo
 */
public class Game extends Thread {

	// ===== STATIC FIELDS =====
	// This thread will wake itself up every WAKEUP_MS milliseconds.
	public static final long WAKEUP_MS = 10000;
	
	public static final boolean VERBOSE = true;
	
	// The character set used to generate random strings.
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
	private boolean hasBeenJoined = false;
	private boolean isAbandoned = false;

	private final String lobbyID;

	private final Map<String, Player> playerList = new ConcurrentHashMap<String, Player>();

	private Map<String, Boolean> playerReadyMap;

	private final Map<String, GameBoard> gameBoardList = new ConcurrentHashMap<String, GameBoard>();

	private GameState gameState;

	/*
	 * The game's map of turn actions. Maps players to their turn action(s).
	 */
	private final Map<String, Runnable> turnActionMap = new ConcurrentHashMap<String, Runnable>() {
		private static final long serialVersionUID = 1L;

		@Override
		public Runnable put(String player, Runnable runnable) {
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
	
	public Collection<String> getPlayerAuths() {
		return this.playerList.keySet();
	}

	public GameState getGameState() {
		return gameState;
	}

	public boolean getPlayerIsReady(final String auth) {
		return playerReadyMap.get(auth);
	}
	
	public boolean turnTaken(final Player player) {
		if (turnActionMap.get(player.getAuthToken()) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean allCharactersReady() {
		for (Boolean bool : playerReadyMap.values()) {
			if (!bool) {
				return false;
			}
		}		
		return !playerReadyMap.isEmpty();
	}
	
	public Runnable getTurnAction(String auth) {
		return this.turnActionMap.get(auth);
	}
	// ===================

	// ===== SETTERS =====
	/**
	 * Toggles whether the {@link PlayerCharacter} belonging to the {@link Player}
	 * with authToken is ready or not.
	 * 
	 * @param authToken
	 *            The auth token of the {@link Player}
	 */
	public synchronized boolean toggleReady(final String authToken) {
		boolean isReady = !playerReadyMap.get(authToken);
		this.playerReadyMap.put(authToken, isReady);
		this.getGameState().executePhase();
		this.getGameState().syncGameBoardsPlayerList();
		synchronized (this) {
			notify();
		}
		return isReady;
	}
	
	public synchronized void setReadyStatus(final String authToken, final boolean ready) {
		this.playerReadyMap.put(authToken, ready);
		this.getGameState().executePhase();
		this.getGameState().syncGameBoardsPlayerList();
		synchronized (this) {
			notify();
		}
	}

	public void setAllCharactersNotReady() {
		for (String auth : playerReadyMap.keySet()) {
			playerReadyMap.put(auth, false);
		}
	}
	// ===================

	// ===== OTHER INSTANCE METHODS =====
	@Override
	public void run() {
		while (!this.isAbandoned) {
			try {
				synchronized (this) {
					wait(WAKEUP_MS);
				}
				
				GameState state = this.getGameState();
				state.executePhase();
				
				// Check if this game should be removed.
				if (this.hasBeenJoined) {
					SessionManager manager = SessionManager.getInstance();
					String auth;
					Session session;
					
					// Assume all GBs and PCs are not open.
					this.isAbandoned = true;
					
					// Check if any GBs are open.
					for (GameBoard gb : this.gameBoardList.values()) {
						auth = gb.getAuthToken();
						session = manager.getSession(auth);
						
						this.isAbandoned &= !session.isOpen();
						
						if (!this.isAbandoned) {
							break;
						}
					}
					
					// If no GBs were open, check if any PCs are open.
					if (this.isAbandoned) {
						for (Player p : this.playerList.values()) {
							auth = p.getAuthToken();
							session = manager.getSession(auth);
							
							this.isAbandoned &= !session.isOpen();
							
							if (!this.isAbandoned) {
								break;
							}
						}
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// this.isAbandoned == true, so remove this game from GameManager's map of games
		if (VERBOSE) {
			System.out.println("Game was abandoned. Removing game...");
		}
		GameManager.getInstance().removeGame(this.getLobbyID());
		
	}

	public synchronized void executePhase() {
		synchronized (this) {
			notify();
		}
	}

	/**
	 * Removes the {@link Client} from the Game.
	 * 
	 * @param authToken The auth token of the {@link Client} to be removed.
	 */
	public synchronized void removeClient(final String authToken) {
		//the map.remove methods will 
		//"[remove] the mapping for a key from this map if it is present"
		//if the auth token is from a player client:
		this.turnActionMap.remove(authToken);
		this.playerReadyMap.remove(authToken);
		this.playerList.remove(authToken);
		//if the auth token is form a game board: 
		this.gameBoardList.remove(authToken);
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
	public synchronized void addPlayer(final Player player) throws GameFullException, 
																   PlayerNameTakenException, 
																   InvalidNameException {
		// Check to see that the game is not already full.
		if (this.playerList.size() >= MAX_PLAYERS) {
			throw new GameFullException();
		}
		
		if (!player.nameValid()) {
			throw new InvalidNameException();
		}
		
		for (final Player otherPlayer : this.getPlayers()) {
			if (player.getPlayerName().equals(otherPlayer.getPlayerName())) {
				throw new PlayerNameTakenException();
			}
		}

		// Add Player to Game
		String authToken = player.getAuthToken();
		this.playerList.put(authToken, player);
		this.playerReadyMap.put(authToken, false);

		// Add PlayerCharacter to GameState
		PlayerCharacter character = new PlayerCharacter(player.getPlayerName());
		this.getGameState().addPlayerCharacter(authToken, character);
		this.hasBeenJoined = true;

		// Register with GameManager
		GameManager.getInstance().registerPlayerToGame(player.getAuthToken(), this);
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
		this.hasBeenJoined = true;
	}

	/**
	 * Adds a turn action to the {@link Game}'s turnActionMap.
	 * 
	 * @param player
	 *            {@link Player}
	 * @param action
	 *            {@link Action}
	 */
	public synchronized void addTurnAction(final Player player, final Runnable runnable) {
		this.turnActionMap.put(player.getAuthToken(), runnable);
		synchronized (this) {
			notify();
		}
	}

	public synchronized boolean areAllTurnsSubmitted() {
		boolean bool = turnActionMap.size() > 0;
		for(Player player : getPlayers()) {
			bool &= turnActionMap.containsKey(player.getAuthToken());
		}
		return bool;
	}

	public synchronized void resetTurnActionMap() {
		turnActionMap.clear();
	}

	// ==================================
}
