package com.toozo.asteriumwebserver.gamelogic;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.items.LootPool;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Bandage;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodChest;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodCrate;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodPack;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Medkit;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.RescueBeacon;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Syringe;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.EquipmentSlot;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.Loadout;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.TinfoilHatEquipmentItem;
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
	public static final int STARTING_DAY = 0;
	
	// LOOT POOLS
	// 		Medbay
	public static final Map<Supplier<? extends AbstractItem>, Double> MEDBAY_LOOT_PROB;
	static {
		Map<Supplier<? extends AbstractItem>, Double> probs = new HashMap<Supplier<? extends AbstractItem>, Double>();
		
		probs.put(Bandage::new, 0.40);
		probs.put(Medkit::new, 0.20);
		probs.put(Syringe::new, 0.05);
		probs.put(TinfoilHatEquipmentItem::new, 0.25);
		probs.put(RescueBeacon::new, 0.10);
		
		MEDBAY_LOOT_PROB = Collections.unmodifiableMap(probs);
	}
	public static final LootPool MEDBAY_LOOT_POOL = new LootPool(MEDBAY_LOOT_PROB);
	
	//		Mess Hall
	public static final Map<Supplier<? extends AbstractItem>, Double> CAFETERIA_LOOT_PROB;
	static {
		Map<Supplier<? extends AbstractItem>, Double> probs = new HashMap<Supplier<? extends AbstractItem>, Double>();
	
		probs.put(FoodPack::new, 0.40);
		probs.put(FoodCrate::new, 0.20);
		probs.put(FoodChest::new, 0.05);
		probs.put(RescueBeacon::new, 0.10);
		
		CAFETERIA_LOOT_PROB = Collections.unmodifiableMap(probs);
	}
	public static final LootPool CAFETERIA_LOOT_POOL = new LootPool(CAFETERIA_LOOT_PROB);
	
	public enum GamePhase {

		PLAYERS_JOINING(GameState::playerJoining),

		GAME_INITIALIZING(GameState::initializeGame),

		PLAYER_TURNS(GameState::initiatePlayerTurnPhase),

		TURN_RESOLVE(GameState::initiateTurnResolvePhase),

		TURN_SUMMARY(game -> {

		}),

		END_SUMMARY(GameState::initiateEndSummaryPhase),

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
		Location home = new Location("Control Room", Location.LocationType.CONTROL_ROOM, MEDBAY_LOOT_POOL);
		locations.put("1", home);

		Location med_bay = new Location("Med Bay", Location.LocationType.MED_BAY, MEDBAY_LOOT_POOL);
		med_bay.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("2", med_bay);
		
		Location cafeteria = new Location("Cafeteria", Location.LocationType.MESS_HALL, CAFETERIA_LOOT_POOL);
		cafeteria.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("3", cafeteria);
	};
	// =========================

	// ===== INSTANCE FIELDS =====
	/* Map of player auth token to character */
	private Game game;
	private GamePhase gamePhase;
	private int food;
	private int fuel;
	private int day;
	private Map<String, PlayerCharacter> authCharacterMap;
	private Map<String, PlayerCharacter> nameCharacterMap;
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
			// Here is where we would validate game state to make sure everything is ready
			// to start.
			// if(validateGameState()){
			state.setGamePhase(GamePhase.GAME_INITIALIZING);
			state.game.setAllCharactersNotReady();
			state.gamePhase.executePhase(state);
			// }
		}
	}

	private static final void initializeGame(GameState state) {
		state.addVictoryCondition(new VictoryCondition(VictoryCondition::getBeaconProgress));
		state.setGamePhase(GamePhase.PLAYER_TURNS);
		state.gamePhase.executePhase(state);
	}

	private static final void initiatePlayerTurnPhase(GameState state) {
		state.setDay(state.getDay() + 1);
		state.syncPlayerClients();
		syncGameBoards(state);

		// Is there an action for every player and is everyone ready?? If so:
		if (state.game.areAllTurnsSubmitted() && state.game.allCharactersReady()) {
			state.setGamePhase(GamePhase.TURN_RESOLVE);
		}
	}

	private static final void initiateTurnResolvePhase(GameState state) {

		state.game.setAllCharactersNotReady();

		state.syncPlayerClients();
		syncGameBoards(state);

		if (state.getCompleteVictoryConditions().size() >= 1) {
			state.setGamePhase(GamePhase.END_SUMMARY);
		} else {
			state.setGamePhase(GamePhase.PLAYER_TURNS);

			// Should run everyone's actions here.
			for (Player player : state.game.getPlayers()) {
				String auth = player.getAuthToken();
				Runnable action = state.game.getTurnAction(auth);
				action.run();
			}

			state.game.resetTurnActionMap();
		}
		state.gamePhase.executePhase(state);
	}

	private static final void initiateEndSummaryPhase(GameState state) {
		// TODO Notify game board the game is over and clients that the game is over and
		// if they won or lost. Wrap up.
		state.syncPlayerClients();
		syncGameBoards(state);
	}

	private static final void syncGameBoards(GameState state) {
		int food = state.getFood();
		int fuel = state.getFuel();
		int day = state.getDay();

		// Construct collection of LocationData
		List<SyncGameBoardDataRequestData.LocationData> loc = new ArrayList<SyncGameBoardDataRequestData.LocationData>();

		for (String s : state.getMapLocations()) {
			Location l = state.getAtMapLocation(s);
			// if(l.distance <= player.stamina)//pseudocode
			SyncGameBoardDataRequestData.LocationData.LocationType type = SyncGameBoardDataRequestData.LocationData.LocationType
					.valueOf(l.getType().toString());

			SyncGameBoardDataRequestData.LocationData locData = new SyncGameBoardDataRequestData.LocationData(s,
					l.getName(), type, l.getActivityNames());

			loc.add(locData);
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

		ActionData syncGBRequestData = new SyncGameBoardDataRequestData(food, fuel, day, loc, playerDatas,
				victoryDatas, itemDatas, state.getGamePhase().toString());

		// Send sync to all GameBoards
		for (GameBoard gameBoard : state.game.getGameBoards()) {
			System.out.println("Game board: " + gameBoard.getAuthToken());

			Message syncGBMessage = new Request(syncGBRequestData, gameBoard.getAuthToken());

			Session session = SessionManager.getInstance().getSession(gameBoard.getAuthToken());
			synchronized (session) {
				session.getAsyncRemote().sendText(syncGBMessage.jsonify().toString());
			}
		}
	}
	// ==========================

	// ===== CONSTRUCTORS =====
	public GameState(Game game) {
		this.game = game;
		this.gamePhase = GamePhase.PLAYERS_JOINING;
		this.food = STARTING_FOOD_PER_PLAYER * this.game.getPlayers().size();
		this.fuel = STARTING_FUEL;
		this.authCharacterMap = new ConcurrentHashMap<String, PlayerCharacter>();
		this.nameCharacterMap = new ConcurrentHashMap<String, PlayerCharacter>();
		this.day = STARTING_DAY;
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

	/**
	 * @return the current day
	 */
	public int getDay() {
		return this.day;
	}

	public PlayerCharacter getCharacter(final String auth) {
		return authCharacterMap.get(auth);
	}
	
	public PlayerCharacter getCharacterByName(final String name) {
		return nameCharacterMap.get(name);
	}

	/**
	 * Gets a {@link Collection} of the {@link PlayerCharacter}s in the game.
	 * WARNING: Modifications to this Collection will affect the GameState's map of
	 * {@link PlayerCharacter}s.
	 * 
	 * @return Collection<Character> containing the game's Characters.
	 */
	public Collection<PlayerCharacter> getCharacters() {
		return this.authCharacterMap.values();
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

	/**
	 * Update the day
	 * 
	 * @param newDay
	 *            The new day
	 */
	public void setDay(final int newDay) {
		this.day = newDay;
	}
	// ===================

	// ===== OTHER INSTANCE METHODS =====
	public void addPlayerCharacter(final String playerAuthToken, final PlayerCharacter pc) {
		this.authCharacterMap.put(playerAuthToken, pc);
		this.nameCharacterMap.put(pc.getCharacterName(), pc);
		syncGameBoardsPlayerList();
	}
	
	public void addVictoryCondition(final VictoryCondition victory) {
		this.victoryConditions.add(victory);
	}

	/**
	 * This is a helper method to sync the player list on the game board.
	 */
	public void syncGameBoardsPlayerList() {

		// Create list of player data.
		Collection<SyncPlayerListRequestData.PlayerData> playerData = new ArrayList<>();
		for (Player p : game.getPlayers()) {

			// Construct the stats.
			// TODO: Woah, cumbersome as hell. There's a smarter way to do this.
			SyncPlayerListRequestData.PlayerData.DisplayStats dStats = new SyncPlayerListRequestData.PlayerData.DisplayStats(
					getCharacter(p.getAuthToken()).getBaseStats().getStat(Stat.INTUITION),
					getCharacter(p.getAuthToken()).getBaseStats().getStat(Stat.LUCK),
					getCharacter(p.getAuthToken()).getBaseStats().getStat(Stat.STAMINA));

			// Construct a player data object
			SyncPlayerListRequestData.PlayerData pData = new SyncPlayerListRequestData.PlayerData(p.getPlayerName(),
					game.getPlayerIsReady(p.getAuthToken()), dStats);

			playerData.add(pData);
		}

		SyncPlayerListRequestData data = new SyncPlayerListRequestData(playerData);

		// Send player lists to each game board.
		for (GameBoard gameBoard : game.getGameBoards()) {
			String auth = gameBoard.getAuthToken();

			Request request = new Request(data, auth);

			try {
				Session session = SessionManager.getInstance().getSession(auth);
				synchronized (session) {
					session.getBasicRemote().sendText(request.jsonify().toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

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

	private final void syncPlayerClients() {
		for (Player p : this.game.getPlayers()) {

			ActionData data = createSyncPlayerClientDataRequestData(p);
			Request request = new Request(data, p.getAuthToken());

			try {
				Session session = SessionManager.getInstance().getSession(p.getAuthToken());
				synchronized (session) {
					session.getBasicRemote().sendText(request.jsonify().toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public SyncPlayerClientDataRequestData createSyncPlayerClientDataRequestData(Player player) {
		System.err.println("Sending player client sync.");
		List<SyncPlayerClientDataRequestData.LocationData> loc = new ArrayList<SyncPlayerClientDataRequestData.LocationData>();

		for (String s : getMapLocations()) {
			Location l = getAtMapLocation(s);
			// if(l.distance <= player.stamina)//pseudocode
			SyncPlayerClientDataRequestData.LocationData.LocationType type = SyncPlayerClientDataRequestData.LocationData.LocationType
					.valueOf(l.getType().toString());

			SyncPlayerClientDataRequestData.LocationData locData = new SyncPlayerClientDataRequestData.LocationData(s,
					l.getName(), type, l.getActivityNames());

			loc.add(locData);
		}

		String auth = player.getAuthToken();

		PlayerCharacter pChar = getCharacter(auth);
		
		Collection<String> characters = new ArrayList<String>();
		for (PlayerCharacter pc : getCharacters()) {
			characters.add(pc.getCharacterName());
		}

		SyncPlayerClientDataRequestData.PlayerCharacterData.StatsData stat = new SyncPlayerClientDataRequestData.PlayerCharacterData.StatsData(
				pChar.getEffectiveStats().getStat(Stat.HEALTH), pChar.getEffectiveStats().getStat(Stat.STAMINA),
				pChar.getEffectiveStats().getStat(Stat.LUCK), pChar.getEffectiveStats().getStat(Stat.INTUITION));

		List<SyncPlayerClientDataRequestData.InventoryData> personalInv = new ArrayList<SyncPlayerClientDataRequestData.InventoryData>();
		for (AbstractItem item : pChar.getInventory()) {
			SyncPlayerClientDataRequestData.InventoryData itemData = new SyncPlayerClientDataRequestData.InventoryData(
					item.getName());
			personalInv.add(itemData);
		}

		Map<SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType, SyncPlayerClientDataRequestData.InventoryData> equipment = new HashMap<SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType, SyncPlayerClientDataRequestData.InventoryData>();
		Loadout load = pChar.getEquipment();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (load.slotFull(slot)) {
				SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType type = SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType
						.valueOf(slot.toString());
				SyncPlayerClientDataRequestData.InventoryData item = new SyncPlayerClientDataRequestData.InventoryData(
						load.itemIn(slot).getName());
				equipment.put(type, item);
			}
		}

		SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData loadout = new SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData(
				equipment);

		SyncPlayerClientDataRequestData.PlayerCharacterData dChar = new SyncPlayerClientDataRequestData.PlayerCharacterData(
				pChar.getCharacterName(), stat, personalInv, loadout, game.turnTaken(player), game.getPlayerIsReady(auth));

		List<SyncPlayerClientDataRequestData.InventoryData> inventory = new ArrayList<SyncPlayerClientDataRequestData.InventoryData>();
		for (AbstractItem item : getCommunalInventory()) {
			SyncPlayerClientDataRequestData.InventoryData itemData = new SyncPlayerClientDataRequestData.InventoryData(
					item.getName());
			inventory.add(itemData);
		}

		SyncPlayerClientDataRequestData data = new SyncPlayerClientDataRequestData(getFood(), getFuel(), getDay(), loc, dChar, characters,
				getGamePhase().toString(), inventory);

		return data;

	}

	// ==================================

}
