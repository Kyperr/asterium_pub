package gamelogic;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import actions.Action;
import exceptions.GameFullException;

/**
 * {@link Game} representing a single game state. 
 * 
 * @author Studio Toozo
 */
public class Game extends Thread {

	// TODO: turn into complex enum?
	private enum GamePhase {
		
		PLAYERS_JOINING(Game::playerJoining),

		
		GAME_INITIALIZING(game -> {
			
		}),
		
		START_SUMMARY(game -> {
			
		}),
		
		PLAYER_TURNS(game -> {
			
		}),
		
		TURN_RESOLVE(game -> {
			
		}),
		
		TURN_SUMMARY(game -> {
			
		}),

		END_SUMMARY(game -> {
			
		});

		private final Consumer<Game> phaseSequence;

		GamePhase(Consumer<Game> phaseSequence) {
			this.phaseSequence = phaseSequence;
		}

		public void executePhase(Game game) {
			this.phaseSequence.accept(game);
		}

	}

	// ===================Static Vars========================================
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

	// =====================Instance Vars==========================
	// Indicates that players still need this game object.
	private boolean isNotAbandoned = true;

	private final String lobbyID;

	private final Map<String, Player> playerList = new ConcurrentHashMap<String, Player>();

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

	private GamePhase gamePhase;

	// ============================================================

	/**
	 * Creates and returns a {@link Game} that has a lobby ID.
	 */
	public Game() {
		lobbyID = generateLobbyID();
		gamePhase = GamePhase.PLAYERS_JOINING;
		gameState = new GameState();
	}

	@Override
	public void run() {
		while (isNotAbandoned) {
			try {
				synchronized (this) {
					wait();
				}
				this.gamePhase.executePhase(this);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return The {@link Game}'s lobby ID, used to allow players to join the game.
	 */
	public String getLobbyID() {
		return lobbyID;
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
			this.playerList.put(player.getSession().getAuthToken(), player);
			this.turnActionMap.put(player, new Runnable() {
				@Override
				public void run() {
					// do nothing, this is a "null" action
				}
				
			});
			this.gameState.addPlayer(player.getAuthToken());
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
		this.gameBoardList.put(gameBoard.getSession().getAuthToken(), gameBoard);
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

	public Collection<Player> getPlayers() {
		return playerList.values();
	}

	public Player getPlayer(String authToken) {
		return playerList.get(authToken);
	}

	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}

	public GameState getGameState() {
		return gameState;
	}
	
	//============Static <Game> Consumers to be used===================================
	private static final void playerJoining(Game game){
		if (game.getGameState().allCharactersReady()) {
			// Here is where we would validate game state to make sure everything is ready
			// to start.
			// if(validateGameState()){
			game.setGamePhase(GamePhase.GAME_INITIALIZING);
			// }
		}
	}
	
}
