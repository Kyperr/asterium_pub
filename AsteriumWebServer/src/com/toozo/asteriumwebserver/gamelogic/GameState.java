package com.toozo.asteriumwebserver.gamelogic;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.SyncGameBoardDataRequestData;
import actiondata.SyncPlayerClientDataRequestData;
import actiondata.SyncPlayerListRequestData;
import message.Message;
import message.Request;

/**
 * Representational state of the game.
 * 
 * @author Studio Toozo
 */
public class GameState {
	// ===== CONSTANTS & ENUMS =====
	public static final int STARTING_FOOD_PER_PLAYER = 5;
	public static final int STARTING_FUEL = 100;

	public enum GamePhase {

		PLAYERS_JOINING(GameState::playerJoining),

		GAME_INITIALIZING(GameState::initializeGame),

		PLAYER_TURNS(GameState::initiatePlayerTurnPhase),

		TURN_RESOLVE(game -> {

		}),

		TURN_SUMMARY(game -> {

		}),

		END_SUMMARY(game -> {

		}),

		START_SUMMARY(game -> {

		});

		private final Consumer<GameState> phaseSequence;

		private GamePhase(Consumer<GameState> phaseSequence) {
			this.phaseSequence = phaseSequence;
		}

		public void executePhase(GameState state) {
			this.phaseSequence.accept(state);
		}

	}
	// =============================

	// ===== STATIC FIELDS =====
	private static Map<String, Location> locations = new HashMap<String, Location>();

	// Initialize the locations
	{
		// Make a new location
		Location home = new Location(Location.LocationType.CONTROL_ROOM);
		// Make a new room with a room id and location
		locations.put("1", home);

		Location med_bay = new Location(Location.LocationType.MED_BAY);
		med_bay.addActivity(Activity.SEARCH, Activity.searchActivity);

		locations.put("2", med_bay);
		locations.put("3", med_bay);
	};
	// =========================

	// ===== INSTANCE FIELDS =====
	/* Map of player auth token to character */
	private Game game;
	private GamePhase gamePhase;
	private int food;
	private int fuel;
	private Map<String, PlayerCharacter> playerCharacterMap;
	private Collection<VictoryCondition> victoryConditions;
	private Inventory communalInventory;
	// ===========================

	// ===== STATIC METHODS =====
	/**
	 * Definition of what should be performed during the players
	 * 
	 * @param state
	 *            The current state of the game.
	 */
	private static final void playerJoining(GameState state) {
		System.err.println("Running Player Joining Phase.");
		if (state.game.allCharactersReady()) {
			syncPlayerList(state);
			// Here is where we would validate game state to make sure everything is ready
			// to start.
			// if(validateGameState()){
			state.setGamePhase(GamePhase.GAME_INITIALIZING);
			state.gamePhase.executePhase(state);
			state.game.setAllCharactersNotReady();
			// }
		}
	}

	private static final void initializeGame(GameState state) {
		System.err.println("Initializing Game.");
		// TODO Initialize game
		state.setGamePhase(GamePhase.PLAYER_TURNS);
		state.gamePhase.executePhase(state);
	}

	private static final void initiatePlayerTurnPhase(GameState state) {
		syncPlayerClients(state);
		syncGameBoards(state);
	}

	private static final void syncPlayerList(GameState state) {
		String auth = "";
		Collection<SyncPlayerListRequestData.PlayerData> players = new HashSet<SyncPlayerListRequestData.PlayerData>();
		Request request;
		for (Player p : state.game.getPlayers()) {
			auth = p.getAuthToken();

			PlayerCharacter pChar = state.getCharacter(auth);

			SyncPlayerListRequestData.PlayerData playerData = new SyncPlayerListRequestData.PlayerData(pChar.getCharacterName(), 
					state.game.getPlayerIsReady(auth), pChar.getEffectiveStats().getStat(Stat.STAMINA),
					pChar.getEffectiveStats().getStat(Stat.LUCK), pChar.getEffectiveStats().getStat(Stat.INTUITION));
			
			players.add(playerData);

		}
		SyncPlayerListRequestData data = new SyncPlayerListRequestData(players);
		request = new Request(data, auth);
		try {
			SessionManager.getInstance().getSession(auth).getBasicRemote().sendText(request.jsonify().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final void syncPlayerClients(GameState state) {
		System.err.println("Sending player client sync.");
		List<SyncPlayerClientDataRequestData.LocationData> loc = new ArrayList<>();

		for (String s : state.getMapLocations()) {
			Location l = state.getAtMapLocation(s);
			// if(l.distance <= player.stamina)//pseudocode

			SyncPlayerClientDataRequestData.LocationData locData = new SyncPlayerClientDataRequestData.LocationData(s,
					l.getType().getJSONVersion(), l.getActivityNames());

			loc.add(locData);
		}

		for (Player p : state.game.getPlayers()) {
			String auth = p.getAuthToken();

			PlayerCharacter pChar = state.getCharacter(auth);

			SyncPlayerClientDataRequestData.PlayerCharacterData.Stats stat = new SyncPlayerClientDataRequestData.PlayerCharacterData.Stats(
					pChar.getEffectiveStats().getStat(Stat.HEALTH), pChar.getEffectiveStats().getStat(Stat.STAMINA),
					pChar.getEffectiveStats().getStat(Stat.LUCK), pChar.getEffectiveStats().getStat(Stat.INTUITION));

			SyncPlayerClientDataRequestData.PlayerCharacterData dChar = new SyncPlayerClientDataRequestData.PlayerCharacterData(
					pChar.getCharacterName(), stat);

			SyncPlayerClientDataRequestData data = new SyncPlayerClientDataRequestData(loc, dChar,
					state.getGamePhase().toString());

			Request request = new Request(data, auth);

			try {
				SessionManager.getInstance().getSession(auth).getBasicRemote().sendText(request.jsonify().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private static final void syncGameBoards(GameState state) {
		int food = state.getFood();
		int fuel = state.getFuel();

		// Construct collection of LocationData
		Collection<SyncGameBoardDataRequestData.LocationData> locationDatas = new ArrayList<SyncGameBoardDataRequestData.LocationData>();
		SyncGameBoardDataRequestData.LocationData location;
		for (String mapLocation : state.getMapLocations()) {
			location = new SyncGameBoardDataRequestData.LocationData(mapLocation,
					state.getAtMapLocation(mapLocation).getType().toString());
			locationDatas.add(location);
		}

		// Construct collection of PlayerData
		Collection<SyncGameBoardDataRequestData.PlayerCharacterData> playerDatas = new ArrayList<SyncGameBoardDataRequestData.PlayerCharacterData>();
		SyncGameBoardDataRequestData.PlayerCharacterData player;
		for (final PlayerCharacter c : state.getCharacters()) {
			player = new SyncGameBoardDataRequestData.PlayerCharacterData(c.getCharacterName(), Color.WHITE,
					"home_base");
			playerDatas.add(player);
		}

		// Construct collection of VictoryData
		Collection<SyncGameBoardDataRequestData.VictoryData> victoryDatas = new ArrayList<SyncGameBoardDataRequestData.VictoryData>();
		SyncGameBoardDataRequestData.VictoryData victory;
		for (final VictoryCondition vc : state.getVictoryConditions()) {
			victory = new SyncGameBoardDataRequestData.VictoryData(vc.getName(), vc.getProgress(state),
					VictoryCondition.COMPLETE_THRESHOLD);
			victoryDatas.add(victory);
		}

		// Get communal inventory data
		Collection<SyncGameBoardDataRequestData.ItemData> itemDatas = new ArrayList<SyncGameBoardDataRequestData.ItemData>();
		SyncGameBoardDataRequestData.ItemData itemData;
		for (final AbstractItem item : state.getCommunalInventory()) {
			itemData = new SyncGameBoardDataRequestData.ItemData(item.getName());
			itemDatas.add(itemData);
		}

		ActionData syncGBRequestData = new SyncGameBoardDataRequestData(food, fuel, locationDatas, playerDatas,
				victoryDatas, itemDatas);
		Message syncGBMessage = new Request(syncGBRequestData, "DanielSaysToLeaveTheAuthTokenBlank");

		// Send sync to all GameBoards
		for (GameBoard gameBoard : state.game.getGameBoards()) {
			SessionManager.getInstance().getSession(gameBoard.getAuthToken()).getAsyncRemote()
					.sendText(syncGBMessage.jsonify().toString());
		}

		state.setGamePhase(GamePhase.TURN_RESOLVE);
	}
	// ==========================

	// ===== CONSTRUCTORS =====
	public GameState(Game game) {
		this.game = game;
		this.gamePhase = GamePhase.PLAYERS_JOINING;
		this.food = STARTING_FOOD_PER_PLAYER * this.game.getPlayers().size();
		this.fuel = STARTING_FUEL;
		this.playerCharacterMap = new ConcurrentHashMap<String, PlayerCharacter>();
		this.victoryConditions = new ArrayList<VictoryCondition>();
		this.communalInventory = new Inventory();
	}
	// ========================

	// ===== GETTERS =====
	/**
	 * @return the current amount of food.
	 */
	public int getFood() {
		return this.food;
	}

	/**
	 * @return the current amount of fuel.
	 */
	public int getFuel() {
		return this.fuel;
	}

	public PlayerCharacter getCharacter(final String auth) {
		return playerCharacterMap.get(auth);
	}

	/**
	 * Gets a {@link Collection} of the {@link PlayerCharacter}s in the game.
	 * WARNING: Modifications to this Collection will affect the GameState's map of
	 * {@link PlayerCharacter}s.
	 * 
	 * @return Collection<Character> containing the game's Characters.
	 */
	public Collection<PlayerCharacter> getCharacters() {
		return this.playerCharacterMap.values();
	}

	/**
	 * @return a {@link Collection} of references to this GameState's
	 *         {@link VictoryCondition}s.
	 */
	public Collection<VictoryCondition> getVictoryConditions() {
		return this.victoryConditions;
	}

	/**
	 * @return a {@link Collection} of references to all complete
	 *         {@link VictoryCondition}s in this GameState.
	 */
	public Collection<VictoryCondition> getCompleteVictoryConditions() {
		Collection<VictoryCondition> result = new ArrayList<VictoryCondition>();

		// Add all complete victory conditions
		for (VictoryCondition vc : this.victoryConditions) {
			if (vc.isComplete(this)) {
				result.add(vc);
			}
		}

		return result;
	}

	/**
	 * @return The communal {@link Inventory} in this GameState.
	 */
	public Inventory getCommunalInventory() {
		return this.communalInventory;
	}

	public GamePhase getGamePhase() {
		return this.gamePhase;
	}

	public Location getLocation(String locationID) {
		return GameState.locations.get(locationID);
	}

	public Set<String> getMapLocations() {
		return GameState.locations.keySet();
	}

	public Location getAtMapLocation(String mapLocation) {
		return GameState.locations.get(mapLocation);
	}
	// ===================

	// ===== SETTERS =====
	/**
	 * Set the current {@link GamePhase} to newGamePhase.
	 * 
	 * @param gamePhase
	 *            The new {@link GamePhase}.
	 */
	public void setGamePhase(final GamePhase newGamePhase) {
		this.gamePhase = newGamePhase;
	}

	/**
	 * Update the amount of food in this GameState.
	 * 
	 * @param newFood
	 *            The new amount of food.
	 */
	public void setFood(final int newFood) {
		this.food = newFood;
	}

	/**
	 * Update the amount of fuel in this GameState.
	 * 
	 * @param newFuel
	 *            The new amount of fuel.
	 */
	public void setFuel(final int newFuel) {
		this.fuel = newFuel;
	}
	// ===================

	// ===== OTHER INSTANCE METHODS =====
	public void addPlayerCharacter(final String playerAuthToken, final PlayerCharacter pc) {
		this.playerCharacterMap.put(playerAuthToken, pc);
	}

	/**
	 * Add an item to the communal inventory.
	 * 
	 * @param item
	 *            the {@link AbstractItem} which should be added to the communal
	 *            {@link Inventory}.
	 */
	public void addCommunalItem(final AbstractItem item) {
		this.communalInventory.add(item);
	}

	/**
	 * Executes the current phase of the GameState.
	 */
	public void executePhase() {
		this.gamePhase.executePhase(this);
	}
	// ==================================

}
