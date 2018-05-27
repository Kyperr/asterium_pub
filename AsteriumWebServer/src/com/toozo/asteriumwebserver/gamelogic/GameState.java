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

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Location.LocationType;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.items.ItemLoot;
import com.toozo.asteriumwebserver.gamelogic.items.LootPool;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Bandage;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodChest;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodCrate;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodPack;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Medkit;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.RescueBeacon;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Syringe;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.AbstractEquipmentItem;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.EquipmentSlot;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.HareyGlovesEquipmentItem;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.HoverSkatesEquipmentItem;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.LettermanJacketEquipmentItem;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.Loadout;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.TinfoilHatEquipmentItem;
import com.toozo.asteriumwebserver.gamelogic.items.location.AbstractLocationItem;
import com.toozo.asteriumwebserver.gamelogic.items.location.Book;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.SyncData;
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
	public static final int FOOD_DECREMENT_PER_PLAYER = 1;
	public static final int STARTING_FUEL = 100;
	public static final int FUEL_DECREMENT = 10;
	public static final int STARTING_DAY = 0;

	public static final boolean VERBOSE = true;

	// LOOT POOLS
	// Control Room
	public static final List<ItemLoot> CONTROL_ROOM_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();
		
		probs.add(new ItemLoot(RescueBeacon::new, 100, 0.0, 0.0));

		CONTROL_ROOM_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool CONTROL_ROOM_LOOT_POOL = new LootPool(CONTROL_ROOM_ITEM_LOOT);
	
	// Medbay
	public static final List<ItemLoot> MEDBAY_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		probs.add(new ItemLoot(Bandage::new, 60, 0.0, 0.0));
		probs.add(new ItemLoot(Medkit::new, 30, 0.0, 0.0));
		probs.add(new ItemLoot(Syringe::new, 9, 0.0, 0.0));
		probs.add(new ItemLoot(RescueBeacon::new, 1, 0.0, 0.0));

		MEDBAY_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool MEDBAY_LOOT_POOL = new LootPool(MEDBAY_ITEM_LOOT);

	// Mess Hall
	public static final List<ItemLoot> CAFETERIA_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		probs.add(new ItemLoot(FoodPack::new, 60, 0.0, 0.0));
		probs.add(new ItemLoot(FoodCrate::new, 30, 0.0, 0.0));
		probs.add(new ItemLoot(FoodChest::new, 9, 0.0, 0.0));
		probs.add(new ItemLoot(RescueBeacon::new, 1, 0.0, 0.0));

		CAFETERIA_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool CAFETERIA_LOOT_POOL = new LootPool(CAFETERIA_ITEM_LOOT);
	
	// Armory
	public static final List<ItemLoot> ARMORY_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		probs.add(new ItemLoot(TinfoilHatEquipmentItem::new, 24, 0.0, 0.0));
		probs.add(new ItemLoot(HareyGlovesEquipmentItem::new, 24, 0.0, 0.0));
		probs.add(new ItemLoot(HoverSkatesEquipmentItem::new, 24, 0.0, 0.0));
		probs.add(new ItemLoot(LettermanJacketEquipmentItem::new, 24, 0.0, 0.0));
		probs.add(new ItemLoot(RescueBeacon::new, 4, 0.0, 0.0));

		ARMORY_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool ARMORY_LOOT_POOL = new LootPool(ARMORY_ITEM_LOOT);
	
	// Library
	public static final List<ItemLoot> LIBRARY_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		probs.add(new ItemLoot(Book::new, 99, 0.0, 0.0));
		probs.add(new ItemLoot(RescueBeacon::new, 1, 0.0, 0.0));

		LIBRARY_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool LIBRARY_LOOT_POOL = new LootPool(LIBRARY_ITEM_LOOT);

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
		Location home = new Location("Control Room", Location.LocationType.CONTROL_ROOM, CONTROL_ROOM_LOOT_POOL, 0);
		home.addActivity(Activity.REST, Activity.restActivity);
		home.addActivity(Activity.USE_LOCATION_ITEM, Activity.useLocationItemActivity);
		locations.put("1", home);

		Location med_bay = new Location("Med Bay", Location.LocationType.MED_BAY, MEDBAY_LOOT_POOL, 1);
		med_bay.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("2", med_bay);

		Location cafeteria = new Location("Cafeteria", Location.LocationType.MESS_HALL, CAFETERIA_LOOT_POOL, 2);
		cafeteria.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("3", cafeteria);
		
		Location library = new Location("Library", Location.LocationType.LIBRARY, LIBRARY_LOOT_POOL, 3);
		library.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("4", library);
		
		Location armory = new Location("Armory", Location.LocationType.ARMORY, ARMORY_LOOT_POOL, 4);
		armory.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("5", armory);
		
	};
	// =========================

	// ===== INSTANCE FIELDS =====
	/* Map of player auth token to character */
	private Game game;
	private GamePhase gamePhase;
	private int food;
	private int fuel;
	private int day;
	private boolean gameOver;
	private boolean humansWon;
	private Map<String, PlayerCharacter> authCharacterMap;
	private Map<String, PlayerCharacter> nameCharacterMap;
	// An ordered list of victory conditions, where the index is the tie-breaker priority.
	// i.e. if victoryConditions = [A, B], A == true and B == true, B will take precedence.
	private List<VictoryCondition> victoryConditions;
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
		if (state.game.allCharactersReady()) {
			// Here is where we would validate game state to make sure everything is ready
			// to start.
			// if(validateGameState()){

			if (VERBOSE) {
				System.out.println("Game initializing...");
			}

			state.setGamePhase(GamePhase.GAME_INITIALIZING);
			state.game.setAllCharactersNotReady();
			state.gamePhase.executePhase(state);
			// }
		}
	}

	private static final void initializeGame(GameState state) {
		state.food = STARTING_FOOD_PER_PLAYER * state.game.getPlayers().size();
		state.fuel = STARTING_FUEL;
		state.day = STARTING_DAY;
		state.addVictoryCondition(new VictoryCondition(VictoryCondition::getBeaconProgress, false));
		state.addVictoryCondition(new VictoryCondition(VictoryCondition::getFuelProgress, true));
		state.addVictoryCondition(new VictoryCondition(VictoryCondition::isParasiteUndiscovered, true));

		if (VERBOSE) {
			System.out.println("Game initialized. Starting game...");
		}

		state.setGamePhase(GamePhase.PLAYER_TURNS);
		state.gamePhase.executePhase(state);
	}

	private static final void initiatePlayerTurnPhase(GameState state) {
		state.setDay(state.getDay() + 1);
		state.syncPlayerClients();
		try {
			syncGameBoards(state);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Is there an action for every player and is everyone ready?? If so:
		if (state.game.areAllTurnsSubmitted() && state.game.allCharactersReady()) {
			if (VERBOSE) {
				System.out.println("Resolving player turns...");
			}
			state.setGamePhase(GamePhase.TURN_RESOLVE);
			state.gamePhase.executePhase(state);
		}
	}

	private static final void initiateTurnResolvePhase(GameState state) {

		state.game.setAllCharactersNotReady();

		state.syncPlayerClients();
		try {
			syncGameBoards(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		state.setFood(state.getFood() - (FOOD_DECREMENT_PER_PLAYER * state.game.getPlayers().size()));
		state.setFuel(state.getFuel() - FUEL_DECREMENT);
		
		// Check victory conditions
		for (VictoryCondition vc : state.getVictoryConditions()) {
			if (vc.isComplete(state)) {
				state.setGameOver(true);
				state.setHumansWon(vc.isForHumans());
			}
		}
		
		// Run all player's actions
		for (Player player : state.game.getPlayers()) {
			String auth = player.getAuthToken();
			Runnable action = state.game.getTurnAction(auth);
			action.run();
		}
		
		// Clear action queue
		state.game.resetTurnActionMap();
		
		// Resolve victory conditions or handle turn actions.
		if (state.gameOver()) {
			if (VERBOSE) {
				System.out.println("Game complete. Displaying end summary...");
			}
			
			state.setGamePhase(GamePhase.END_SUMMARY);
		} else {
			if (VERBOSE) {
				System.out.println("Turns resolved. New turns phase...");
			}

			// TODO Implement turn summaries phase and go there instead.
			state.setGamePhase(GamePhase.PLAYER_TURNS);
		}
		
		state.gamePhase.executePhase(state);
	}

	private static final void initiateEndSummaryPhase(GameState state) {
		state.syncPlayerClients();
		try {
			syncGameBoards(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static final void syncGameBoards(GameState state) throws IOException {
		int food = state.getFood();
		int fuel = state.getFuel();
		int day = state.getDay();

		// Construct collection of LocationData
		List<SyncData.LocationData> loc = new ArrayList<SyncData.LocationData>();

		for (String s : state.getMapLocations()) {
			Location l = state.getAtMapLocation(s);
			// if(l.distance <= player.stamina)//pseudocode
			SyncData.LocationData.LocationType type = SyncData.LocationData.LocationType
					.valueOf(l.getType().toString());

			SyncData.LocationData locData = new SyncData.LocationData(s,
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
		Collection<SyncData.ItemData> itemDatas = new ArrayList<SyncData.ItemData>();
		SyncData.ItemData itemData;
		boolean isLocationItem;
		Collection<SyncData.LocationData.LocationType> useLocations = new ArrayList<SyncData.LocationData.LocationType>();
		for (final AbstractItem item : state.getCommunalInventory()) {
			isLocationItem = item.getIsLocationItem();
			if (isLocationItem) {
				AbstractLocationItem loc_item = AbstractLocationItem.class.cast(item);
				for (LocationType locType : loc_item.getUseLocations()) {
					useLocations
							.add(SyncData.LocationData.LocationType.valueOf(locType.toString()));
				}
			}

			itemData = new SyncData.ItemData(item.getName(), item.getDescription(),
					item.getFlavorText(), item.getImagePath(), isLocationItem, useLocations);
			itemDatas.add(itemData);
		}

		ActionData syncGBRequestData = new SyncGameBoardDataRequestData(food, fuel, day, 
																		state.gameOver(),
																		state.humansWon(),
																		loc, playerDatas, 
																		victoryDatas,
																		itemDatas, 
																		state.getGamePhase().toString());

		// Send sync to all GameBoards
		for (GameBoard gameBoard : state.game.getGameBoards()) {
			Message syncGBMessage = new Request(syncGBRequestData, gameBoard.getAuthToken());

			Session session = SessionManager.getInstance().getSession(gameBoard.getAuthToken());
			synchronized (session) {
				session.getBasicRemote().sendText(syncGBMessage.jsonify().toString());
			}
		}
	}
	// ==========================

	// ===== CONSTRUCTORS =====
	public GameState(Game game) {
		this.game = game;
		this.authCharacterMap = new ConcurrentHashMap<String, PlayerCharacter>();
		this.nameCharacterMap = new ConcurrentHashMap<String, PlayerCharacter>();
		this.victoryConditions = new ArrayList<VictoryCondition>();
		this.communalInventory = new Inventory();
		this.day = 0;
		this.humansWon = false;
		this.gamePhase = GamePhase.PLAYERS_JOINING;
		if (VERBOSE) {
			System.out.println("Player join phase...");
		}

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
	
	public boolean gameOver() {
		return this.gameOver;
	}
	
	public boolean humansWon() {
		return this.gameOver() && this.humansWon;
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
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public void setHumansWon(boolean humansWon) {
		this.humansWon = humansWon;
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

	public final void syncPlayerClients() {
		for (String auth : this.game.getPlayerAuths()) {
			this.syncPlayerClient(auth);
		}

	}

	public final void syncPlayerClient(String auth) {
		Player p = this.game.getPlayer(auth);

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

	// This needs to be code reviewed, it's getting to be a mess.
	public SyncPlayerClientDataRequestData createSyncPlayerClientDataRequestData(Player player) {
		List<SyncData.LocationData> loc = new ArrayList<SyncData.LocationData>();

		PlayerCharacter tpc = getCharacter(player.getAuthToken());

		for (String s : getMapLocations()) {
			Location l = getAtMapLocation(s);
			// if the character's stamina allows them to access this location
			if (l.getCost() <= tpc.getEffectiveStats().getStat(Stat.STAMINA)) {
				SyncData.LocationData.LocationType type = SyncData.LocationData.LocationType
						.valueOf(l.getType().toString());

				SyncData.LocationData locData = new SyncData.LocationData(
						s, l.getName(), type, l.getActivityNames());

				loc.add(locData);
			}
		}

		String auth = player.getAuthToken();

		PlayerCharacter pChar = getCharacter(auth);
		
		// Determine if this player has won or not.
		boolean playerWon = this.gameOver() && (pChar.isParasite() ^ this.humansWon());

		Collection<String> characters = new ArrayList<String>();
		for (PlayerCharacter pc : getCharacters()) {
			characters.add(pc.getCharacterName());
		}

		SyncPlayerClientDataRequestData.PlayerCharacterData.StatsData stat = new SyncPlayerClientDataRequestData.PlayerCharacterData.StatsData(
				pChar.getEffectiveStats().getStat(Stat.HEALTH), pChar.getEffectiveStats().getStat(Stat.STAMINA),
				pChar.getEffectiveStats().getStat(Stat.LUCK), pChar.getEffectiveStats().getStat(Stat.INTUITION));

		List<SyncData.ItemData> personalInv = new ArrayList<SyncData.ItemData>();
		boolean isLocationItem;
		Collection<SyncData.LocationData.LocationType> useLocations = new ArrayList<SyncData.LocationData.LocationType>();
		for (AbstractItem item : pChar.getInventory()) {
			isLocationItem = item.getIsLocationItem();
			if (isLocationItem) {
				AbstractLocationItem loc_item = AbstractLocationItem.class.cast(item);
				for (LocationType locType : loc_item.getUseLocations()) {
					useLocations
							.add(SyncData.LocationData.LocationType.valueOf(locType.toString()));
				}
			}
			SyncData.ItemData itemData = new SyncData.ItemData(
					item.getName(), item.getDescription(), item.getFlavorText(), item.getImagePath(), isLocationItem,
					useLocations);
			personalInv.add(itemData);
		}

		Map<SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType, SyncData.ItemData> equipment = new HashMap<SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType, SyncData.ItemData>();
		Loadout load = pChar.getEquipment();
		useLocations.clear();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (load.slotFull(slot)) {
				SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType type = SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType
						.valueOf(slot.toString());
				AbstractEquipmentItem equipmentItem = load.itemIn(slot);
				SyncData.ItemData item = new SyncData.ItemData(
						equipmentItem.getName(), equipmentItem.getDescription(), equipmentItem.getFlavorText(),
						equipmentItem.getImagePath(), false, useLocations);
				equipment.put(type, item);
			}
		}

		SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData loadout = new SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData(
				equipment);

		SyncPlayerClientDataRequestData.PlayerCharacterData dChar = new SyncPlayerClientDataRequestData.PlayerCharacterData(
				pChar.getCharacterName(), pChar.isParasite(), stat, personalInv, loadout, game.turnTaken(player),
				game.getPlayerIsReady(auth));

		List<SyncData.ItemData> inventory = new ArrayList<SyncData.ItemData>();
		useLocations.clear();
		for (AbstractItem item : getCommunalInventory()) {
			isLocationItem = item.getIsLocationItem();
			if (isLocationItem) {
				AbstractLocationItem loc_item = AbstractLocationItem.class.cast(item);
				for (LocationType locType : loc_item.getUseLocations()) {
					useLocations
							.add(SyncData.LocationData.LocationType.valueOf(locType.toString()));
				}
			}
			SyncData.ItemData itemData = new SyncData.ItemData(
					item.getName(), item.getDescription(), item.getFlavorText(), item.getImagePath(), isLocationItem,
					useLocations);
			inventory.add(itemData);
		}

		SyncPlayerClientDataRequestData data = new SyncPlayerClientDataRequestData(getFood(), 
																				   getFuel(), 
																				   getDay(),
																				   playerWon,
																				   loc,
																				   dChar, 
																				   characters, 
																				   getGamePhase().toString(), inventory);

		return data;

	}

	// ==================================

}
