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
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter.StatBlock;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.items.ItemLoot;
import com.toozo.asteriumwebserver.gamelogic.items.LootPool;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Antibiotics;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Bandage;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodChest;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodCrate;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodPack;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FuelBarrel;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FuelCanister;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FuelCell;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Inhaler;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Medkit;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.ParasiteBGone;
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
import com.toozo.asteriumwebserver.gamelogic.items.location.ControlModule;
import com.toozo.asteriumwebserver.gamelogic.items.location.ParasiteTestKit;
import com.toozo.asteriumwebserver.gamelogic.items.location.PowerSupply;
import com.toozo.asteriumwebserver.gamelogic.items.location.RadioDish;
import com.toozo.asteriumwebserver.gamelogic.items.location.RescueBeacon;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.SyncData;
import actiondata.SyncGameBoardDataRequestData;
import actiondata.SyncPlayerClientDataRequestData;
import actiondata.SyncPlayerListRequestData;
import actiondata.TurnSummaryRequestData;
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
	public static final int STARVING_HEALTH_SUBTRACTED = 1;
	public static final int STARTING_FUEL = 100;
	public static final int FUEL_DECREMENT = 10;
	public static final int STARTING_DAY = 0;
	public static final String HUMAN_VICTORY_MESSAGE = "The parasite has been defeated. Humans live another day!";
	public static final String PARASITE_VICTORY_MESSAGE = "The parasite accomplished its goal, the humans are defeated.";
	public static final String HUMAN_WIN_MESSAGE = "You win! The humans completed victory condition: %s.";
	public static final String HUMAN_LOSS_MESSAGE = "You lost because the parasite completed victory condition: %s.";
	public static final String PARASITE_WIN_MESSAGE = "You win! The parasite(s) completed victory condition: %s.";
	public static final String PARASITE_LOSS_MESSAGE = "You lost because the humans completed victory condition: %s.";
	public static final String MULTIPLE_SUMMARY_FORMAT = "%s (x%d)";
	public static final String BEACON_VC_NAME = "Rescue Beacon Activated";
	public static final String FUEL_VC_NAME = "Out of Fuel";
	public static final String PARASITES_VC_NAME = "All Humans Turned to Parasites";
	public static final String UNDISCOVERED_VC_NAME = "Parasite Escaped Undiscovered";
	
	public static final boolean VERBOSE = true;

	// LOOT POOLS
	// Control Room
	public static final List<ItemLoot> CONTROL_ROOM_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		probs.add(new ItemLoot(RescueBeacon::new, 100, 0.0, 0.0, (state)->true));

		CONTROL_ROOM_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool CONTROL_ROOM_LOOT_POOL = new LootPool(CONTROL_ROOM_ITEM_LOOT);

	// Medbay
	public static final List<ItemLoot> MEDBAY_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();
		
		List<ItemLoot> probs = new ArrayList<ItemLoot>();
		
		// Heal Items (40%)
		probs.add(new ItemLoot(Bandage::new, 15, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(Medkit::new, 15, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(Syringe::new, 10, 0.0, 0.0, (state)->true));
		// Exposure Items (30%)
		probs.add(new ItemLoot(ParasiteBGone::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(Inhaler::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(Antibiotics::new, 10, 0.0, 0.0, (state)->true));
		// Food packs (15%)
		probs.add(new ItemLoot(FoodPack::new, 15, 0.0, 0.0, (state)->true));
		// Parasite Test Kit (5%)
		probs.add(new ItemLoot(ParasiteTestKit::new, 15, 0.0, 0.0, (state)->true));
		// Books (5%)
		probs.add(new ItemLoot(Book::new, 5, 0.0, 0.0, (state)->true));
		// Tinfoil Hat (5%)
		probs.add(new ItemLoot(TinfoilHatEquipmentItem::new, 5, 0.0, 0.0, (state)->true));
		

		MEDBAY_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool MEDBAY_LOOT_POOL = new LootPool(MEDBAY_ITEM_LOOT);

	// Mess Hall
	public static final List<ItemLoot> CAFETERIA_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		// Food (75%)
		probs.add(new ItemLoot(FoodPack::new, 25, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FoodCrate::new, 25, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FoodChest::new, 25, 0.0, 0.0, (state)->true));
		// Letterman Jacket (10%)
		probs.add(new ItemLoot(LettermanJacketEquipmentItem::new, 10, 0.0, 0.0, (state)->true));
		// Fuel Cell (10%)
		probs.add(new ItemLoot(FuelCell::new, 10, 0.0, 0.0, (state)->true));
		// Inhaler (5%)
		probs.add(new ItemLoot(Inhaler::new, 5, 0.0, 0.0, (state)->true));

		CAFETERIA_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool CAFETERIA_LOOT_POOL = new LootPool(CAFETERIA_ITEM_LOOT);

	// Armory
	public static final List<ItemLoot> ARMORY_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		// Equipment (85%)
		probs.add(new ItemLoot(TinfoilHatEquipmentItem::new, 21, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(HareyGlovesEquipmentItem::new, 21, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(HoverSkatesEquipmentItem::new, 21, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(LettermanJacketEquipmentItem::new, 22, 0.0, 0.0, (state)->true));
		// Victory Item (2.5%)
		probs.add(new ItemLoot(RadioDish::new, 3, 0.0, 0.0, GameState::radioConditional));
		probs.add(new ItemLoot(PowerSupply::new, 3, 0.0, 0.0, GameState::psuConditional));
		probs.add(new ItemLoot(ControlModule::new, 3, 0.0, 0.0, GameState::moduleConditional));
		// Fuel (7.5%)
		probs.add(new ItemLoot(FuelCell::new, 3, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelCanister::new, 2, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelBarrel::new, 2, 0.0, 0.0, (state)->true));
		// Medkit (5%)
		probs.add(new ItemLoot(Medkit::new, 5, 0.0, 0.0, (state)->true));

		ARMORY_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool ARMORY_LOOT_POOL = new LootPool(ARMORY_ITEM_LOOT);

	// Library
	public static final List<ItemLoot> LIBRARY_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		// Books (90%)
		probs.add(new ItemLoot(Book::new, 9, 0.0, 0.0, (state)->true));
		// Harey Gloves (7.5%)
		probs.add(new ItemLoot(HareyGlovesEquipmentItem::new, 8, 0.0, 0.0, (state)->true));
		// Victory Item (2.5%)
		probs.add(new ItemLoot(RadioDish::new, 3, 0.0, 0.0, GameState::radioConditional));
		probs.add(new ItemLoot(PowerSupply::new, 3, 0.0, 0.0, GameState::psuConditional));
		probs.add(new ItemLoot(ControlModule::new, 3, 0.0, 0.0, GameState::moduleConditional));

		LIBRARY_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool LIBRARY_LOOT_POOL = new LootPool(LIBRARY_ITEM_LOOT);

	// Engine Room
	public static final List<ItemLoot> ENGINE_ROOM_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		// Fuel (70%)
		// Bandage (10%)
		// Food Pack (10%)
		// Victory Item (10%)
		
		probs.add(new ItemLoot(FuelCanister::new, 30, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelBarrel::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(RescueBeacon::new, 60, 0.0, 0.0, (state)->true));

		ENGINE_ROOM_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool ENGINE_ROOM_LOOT_POOL = new LootPool(ENGINE_ROOM_ITEM_LOOT);

	// Vehicle Bay
	public static final List<ItemLoot> VEHICLE_BAY_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		// Hover Skates (10%)
		// Victory Item (10%)
		// Fuel (60%)
		// Inhaler (10%)
		// Book(10%)
		
		probs.add(new ItemLoot(FuelCell::new, 50, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelCanister::new, 25, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelBarrel::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(RescueBeacon::new, 15, 0.0, 0.0, (state)->true));

		VEHICLE_BAY_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool VEHICLE_BAY_LOOT_POOL = new LootPool(VEHICLE_BAY_ITEM_LOOT);

	// Dorms
	public static final List<ItemLoot> DORMS_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();
		
		// Food
		probs.add(new ItemLoot(FoodPack::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FoodCrate::new, 5, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FoodChest::new, 5, 0.0, 0.0, (state)->true));
		// Fuel
		probs.add(new ItemLoot(FuelCell::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelCanister::new, 5, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelBarrel::new, 5, 0.0, 0.0, (state)->true));
		// Exposure
		probs.add(new ItemLoot(Inhaler::new, 6, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(Antibiotics::new, 3, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(ParasiteBGone::new, 3, 0.0, 0.0, (state)->true));
		// Heal
		probs.add(new ItemLoot(Bandage::new, 6, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(Medkit::new, 3, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(Syringe::new, 3, 0.0, 0.0, (state)->true));
		// Equipment
		probs.add(new ItemLoot(TinfoilHatEquipmentItem::new, 7, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(HareyGlovesEquipmentItem::new, 7, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(HoverSkatesEquipmentItem::new, 7, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(LettermanJacketEquipmentItem::new, 7, 0.0, 0.0, (state)->true));
		// Book
		probs.add(new ItemLoot(Book::new, 8, 0.0, 0.0, (state)->true));
		// Victory
		probs.add(new ItemLoot(RadioDish::new, 3, 0.0, 0.0, GameState::radioConditional));
		probs.add(new ItemLoot(PowerSupply::new, 3, 0.0, 0.0, GameState::psuConditional));
		probs.add(new ItemLoot(ControlModule::new, 3, 0.0, 0.0, GameState::moduleConditional));

		DORMS_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool DORMS_LOOT_POOL = new LootPool(DORMS_ITEM_LOOT);

	// Hydroponics
	public static final List<ItemLoot> HYDROPONICS_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		// High-end food (50%)
		// High-end fuel (50%)
		
		probs.add(new ItemLoot(FuelCell::new, 50, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelCanister::new, 25, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelBarrel::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(RescueBeacon::new, 15, 0.0, 0.0, (state)->true));

		HYDROPONICS_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool HYDROPONICS_LOOT_POOL = new LootPool(HYDROPONICS_ITEM_LOOT);

	// Research Lab
	public static final List<ItemLoot> RESEARCH_ITEM_LOOT;
	static {
		List<ItemLoot> probs = new ArrayList<ItemLoot>();

		// Books (50%)
		// Test kit (15%)
		// Victory (15%)
		// Exposure (20%)
		
		probs.add(new ItemLoot(FuelCell::new, 50, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelCanister::new, 25, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(FuelBarrel::new, 10, 0.0, 0.0, (state)->true));
		probs.add(new ItemLoot(RescueBeacon::new, 15, 0.0, 0.0, (state)->true));

		RESEARCH_ITEM_LOOT = Collections.unmodifiableList(probs);
	}
	public static final LootPool RESEARCH_LOOT_POOL = new LootPool(RESEARCH_ITEM_LOOT);

	public enum GamePhase {

		PLAYERS_JOINING(GameState::playerJoining),

		GAME_INITIALIZING(GameState::initializeGame),

		PLAYER_TURNS(GameState::initiatePlayerTurnPhase),

		TURN_RESOLVE(GameState::initiateTurnResolvePhase),

		TURN_SUMMARY(GameState::initiateTurnSummaryPhase),

		END_SUMMARY(GameState::initiateEndSummaryPhase),

		GAME_OVER(game -> {

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
		// ============== Tier 0 ===============

		// ~~~~~~~ X axis 0 ~~~~~~~~~~~
		// Make a new location
		// Name, location type, loot pool, X axis position.
		Location home = new Location("Control Room", Location.LocationType.CONTROL_ROOM, CONTROL_ROOM_LOOT_POOL, 0);
		// add the activities that can be done at this location
		home.addActivity(Activity.REST, Activity.restActivity);
		// add this location to the map locations
		// key is the map location, value is the location
		locations.put("1", home);

		// ~~~~~~~ X axis 1 ~~~~~~~~~~~
		Location library = new Location("Library", Location.LocationType.LIBRARY, LIBRARY_LOOT_POOL, 1);
		library.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("8", library);

		Location dorms = new Location("Dorms", Location.LocationType.RESIDENTIAL, DORMS_LOOT_POOL, 1);
		dorms.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("20", dorms);

		// ~~~~~~~ X axis 2 ~~~~~~~~~~~
		Location cafeteria = new Location("Cafeteria", Location.LocationType.MESS_HALL, CAFETERIA_LOOT_POOL, 2);
		cafeteria.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("3", cafeteria);

		// ============== Tier 1 ===============

		// ~~~~~~~ X axis 3 ~~~~~~~~~~~
		Location med_bay = new Location("Med Bay", Location.LocationType.MED_BAY, MEDBAY_LOOT_POOL, 3);
		med_bay.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("4", med_bay);

		// ~~~~~~~ X axis 4 ~~~~~~~~~~~
		Location armory = new Location("Armory", Location.LocationType.ARMORY, ARMORY_LOOT_POOL, 4);
		armory.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("17", armory);

		Location engineRoom = new Location("Engine Room", Location.LocationType.ENGINE_ROOM, ENGINE_ROOM_LOOT_POOL, 4);
		engineRoom.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("23", engineRoom);

		// ============== Tier 2 ===============

		// ~~~~~~~ X axis 5 ~~~~~~~~~~~
		Location hydroponics = new Location("Hydroponics Bay", Location.LocationType.HYDROPONICS, HYDROPONICS_LOOT_POOL, 5);
		hydroponics.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("18", hydroponics);

		// ~~~~~~~ X axis 6 ~~~~~~~~~~~
		Location vehicleBay = new Location("Vehicle Bay", Location.LocationType.VEHICLE_BAY, VEHICLE_BAY_LOOT_POOL, 6);
		vehicleBay.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("25", vehicleBay);

		Location research = new Location("Research Lab", Location.LocationType.RESEARCH_LAB, RESEARCH_LOOT_POOL, 6);
		research.addActivity(Activity.SEARCH, Activity.searchActivity);
		locations.put("13", research);
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
	private boolean radioDishUsed;
	private boolean powerSupplyUsed;
	private boolean controlModuleUsed;
	private boolean rescueBeaconUsed;
	// An ordered list of victory conditions, where the index is the tie-breaker
	// priority.
	// i.e. if victoryConditions = [A, B], A == true and B == true, B will take
	// precedence.
	private List<VictoryCondition> victoryConditions;
	private Inventory communalInventory;
	private List<String> communalSummary;
	// ===========================

	// ===== STATIC METHODS =====
	
	private static final boolean radioConditional(GameState state) {
		return !state.radioDishUsed();
	}
	
	private static final boolean psuConditional(GameState state) {
		return state.radioDishUsed() && !state.powerSupplyUsed();
	}
	
	private static final boolean moduleConditional(GameState state) {
		return state.radioDishUsed() && state.powerSupplyUsed() && !state.controlModuleUsed();
	}
	
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
		state.addVictoryCondition(new VictoryCondition(BEACON_VC_NAME, 
													   VictoryCondition::getBeaconProgress, false));
		state.addVictoryCondition(new VictoryCondition(FUEL_VC_NAME,
													   VictoryCondition::getFuelProgress, true));
		state.addVictoryCondition(new VictoryCondition(PARASITES_VC_NAME,
													   VictoryCondition::areAllPlayersParasites, true));
		state.addVictoryCondition(new VictoryCondition(UNDISCOVERED_VC_NAME,
													   VictoryCondition::isParasiteUndiscovered, true));

		if (VERBOSE) {
			System.out.println("Game initialized. Starting game...");
		}

		state.setGamePhase(GamePhase.PLAYER_TURNS);
		state.gamePhase.executePhase(state);
	}

	private static final void initiatePlayerTurnPhase(GameState state) {
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
		state.setDay(state.getDay() + 1);

		state.game.setAllCharactersNotReady();

		state.syncPlayerClients();
		try {
			syncGameBoards(state);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Location.initVisitedLocations(locations.values());

		List<String> playerMessages = new ArrayList<String>();
		
		// Use resources
		int numberOfPlayers = state.game.getPlayers().size();
		boolean starving = state.getFood() <= 0;
		int foodToConsume = Math.min(FOOD_DECREMENT_PER_PLAYER * numberOfPlayers, state.getFood());
		int fuelToConsume = Math.min(FUEL_DECREMENT, state.getFuel());
		
		state.setFood(state.getFood() - foodToConsume);
		state.setFuel(state.getFuel() - fuelToConsume);
		
		if (!starving) {
			playerMessages.add(String.format("You consumed %d food.", (int) Math.floor(foodToConsume / numberOfPlayers)));
		} else {
			playerMessages.add(String.format("You took %d damage from hunger.", STARVING_HEALTH_SUBTRACTED));
		}
		
		state.addSummaryMessage(foodToConsume + " food was consumed.");
		state.addSummaryMessage(fuelToConsume + " fuel was used.");
		
		// Add playerMessages to all PC's summaries
		for (PlayerCharacter character : state.getCharacters()) {
			if (starving) {
				StatBlock newStats = character.getBaseStats();
				newStats.setStat(Stat.HEALTH, Math.max(newStats.getStat(Stat.HEALTH) - 1, 0));
				character.setStats(newStats);
			}
			for (String message : playerMessages) {
				character.addSummaryMessage(message);
			}
		}

		// Check victory conditions
		VictoryCondition lastVC = null;
		VictoryCondition vc = null;
		List<VictoryCondition> victoryConditions = state.getVictoryConditions();
		
		// Iterate over VCs, looking for complete VCs and keeping track of the last complete VC.
		for (int i = 0; i < victoryConditions.size(); i++) {
			vc = victoryConditions.get(i);
			
			if (vc.isComplete(state)) {
				state.setGameOver(true);
				lastVC = vc;
			}
		}

		if (state.gameOver() && lastVC != null) { // GAME OVER
			state.clearSummary();
			
			String lastVCname = lastVC.getName();
			if (VERBOSE) {
				System.out.println(String.format("Game complete. Victory condition: %s.", lastVCname));
				System.out.println("Displaying end summary...");
			}

			boolean forHumans = lastVC.isForHumans();
			state.setHumansWon(forHumans);

			// Add end summary message for GameBoards.
			if (forHumans) {
				state.addSummaryMessage(HUMAN_VICTORY_MESSAGE);
			} else {
				state.addSummaryMessage(PARASITE_VICTORY_MESSAGE);
			}

			// Add end summary message for each Player.
			for (PlayerCharacter character : state.getCharacters()) {
				character.clearSummary();
				if (character.isParasite()) {
					if (forHumans) {
						character.addSummaryMessage(String.format(PARASITE_LOSS_MESSAGE, lastVCname));
					} else {
						character.addSummaryMessage(String.format(PARASITE_WIN_MESSAGE, lastVCname));
					}
				} else {
					// character is human
					if (forHumans) {
						character.addSummaryMessage(String.format(HUMAN_WIN_MESSAGE, lastVCname));
					} else {
						character.addSummaryMessage(String.format(HUMAN_LOSS_MESSAGE, lastVCname));
					}
				}
			}

			state.setGamePhase(GamePhase.END_SUMMARY);
		} else { // GAME NOT OVER
			// Run all player's actions
			for (Player player : state.game.getPlayers()) {
				String auth = player.getAuthToken();
				Runnable action = state.game.getTurnAction(auth);
				action.run();
			}

			// Clear action queue
			state.game.resetTurnActionMap();

			// Reset the map of locations the parasite(s) visited
			Location.resetVisitedLcations();

			if (VERBOSE) {
				System.out.println("Turns resolved. New turns phase...");
			}

			state.setGamePhase(GamePhase.TURN_SUMMARY);
		}

		state.gamePhase.executePhase(state);
	}

	private static final void initiateTurnSummaryPhase(GameState state) {
		GameState.summarizePlayers(state);
		
		try {
			GameState.summarizeGameBoards(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		state.setGamePhase(GamePhase.PLAYER_TURNS);
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

		// Send a final turn summary with the victory/loss messages.
		try {
			GameState.summarizeGameBoards(state);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameState.summarizePlayers(state);
		
		state.setGamePhase(GamePhase.GAME_OVER);
		state.executePhase();
	}

	private static final void summarizePlayers(GameState state) {
		String auth;
		PlayerCharacter character;
		TurnSummaryRequestData data;
		Request request;
		Session session;

		// Send turn summaries to players
		for (Player player : state.game.getPlayers()) {
			auth = player.getAuthToken();
			character = state.getCharacter(auth);
			List<String> summary = character.getTurnSummary();
			
			if (!summary.isEmpty()) {
				data = new TurnSummaryRequestData(summary);
				request = new Request(data, auth);
	
				try {
					session = SessionManager.getInstance().getSession(auth);
					synchronized (session) {
						session.getBasicRemote().sendText(request.jsonify().toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				character.clearSummary();
			}
		}
	}

	private static final void summarizeGameBoards(GameState state) throws IOException {
		String auth;
		TurnSummaryRequestData data;
		Request request;
		Session session;
		
		if (!state.communalSummary.isEmpty()) {
			for (GameBoard gameBoard : state.game.getGameBoards()) {
				auth = gameBoard.getAuthToken();
				data = new TurnSummaryRequestData(state.communalSummary);
				request = new Request(data, auth);
	
				try {
					session = SessionManager.getInstance().getSession(auth);
					synchronized (session) {
						session.getBasicRemote().sendText(request.jsonify().toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	
			state.clearSummary();
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

			SyncData.LocationData locData = new SyncData.LocationData(s, l.getName(), type, l.getActivityNames());

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
					useLocations.add(SyncData.LocationData.LocationType.valueOf(locType.toString()));
				}
			}

			itemData = new SyncData.ItemData(item.getName(), item.getDescription(), item.getFlavorText(),
					item.getImagePath(), isLocationItem, useLocations);
			itemDatas.add(itemData);
		}

		ActionData syncGBRequestData = new SyncGameBoardDataRequestData(food, fuel, day, state.gameOver(),
				state.humansWon(), loc, playerDatas, victoryDatas, itemDatas, state.getGamePhase().toString());

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
		this.communalSummary = new ArrayList<String>();
		this.day = 0;
		this.humansWon = false;
		this.gamePhase = GamePhase.PLAYERS_JOINING;
		if (VERBOSE) {
			System.out.println("Player join phase...");
		}
		this.radioDishUsed = false;
		this.powerSupplyUsed = false;
		this.controlModuleUsed = false;
		this.rescueBeaconUsed = false;
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
	public List<VictoryCondition> getVictoryConditions() {
		return this.victoryConditions;
	}

	/**
	 * @return a {@link Collection} of references to all complete
	 *         {@link VictoryCondition}s in this GameState.
	 */
	public List<VictoryCondition> getCompleteVictoryConditions() {
		List<VictoryCondition> result = new ArrayList<VictoryCondition>();

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

	public List<String> getSummary() {
		return new ArrayList<String>(this.communalSummary.size());
	}
	
	public boolean radioDishUsed() {
		return this.radioDishUsed;
	}
	
	public boolean powerSupplyUsed() {
		return this.powerSupplyUsed;
	}
	
	public boolean controlModuleUsed() {
		return this.controlModuleUsed;
	}
	
	public boolean rescueBeaconUsed() {
		return this.rescueBeaconUsed;
	}
	// ===================

	// ===== SETTERS =====
	public void setRadioDishUsed(boolean isUsed) {
		this.radioDishUsed = isUsed;
	}
	
	public void setPowerSupplyUsed(boolean isUsed) {
		this.powerSupplyUsed = isUsed;
	}
	
	public void setControlModuleUsed(boolean isUsed) {
		this.controlModuleUsed = isUsed;
	}
	
	public void setRescueBeaconUsed(boolean isUsed) {
		this.rescueBeaconUsed = isUsed;
	}
	
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

	public void addSummaryMessage(String message) {
		int repeatNumber;
		int i, j;
		String oldMessage;
		boolean added = false;
		
		for (i = this.communalSummary.size() - 1; i >= 0; i--) {
			oldMessage = this.communalSummary.get(i);
			if (oldMessage.equals(message)) {
				// If basic message exists in list, add "message x2".
				this.communalSummary.remove(i);
				this.communalSummary.add(String.format(MULTIPLE_SUMMARY_FORMAT, message, 2));
				added = true;
			} else if (oldMessage.matches(String.format("%s \\(x([0-9]+)\\)", message))) {
				// If repeated message exists in list, add "message x[repeatNumber + 1]".
				
				// == Get repeatNumber ==
				// Move j to index of the x
				for (j = oldMessage.length() - 2; Character.isDigit(oldMessage.charAt(j)); j--);
				// Get number from j+1 to end of string
				try {
					repeatNumber = Integer.parseInt(oldMessage.substring(j + 1, oldMessage.length() - 1));
				} catch (NumberFormatException e) {
					// Something went wrong, ignore repeated message.
					repeatNumber = 1;
				}
				// ======================
				
				if (repeatNumber > 1) {
					this.communalSummary.remove(i);
					this.communalSummary.add(String.format(MULTIPLE_SUMMARY_FORMAT, message, repeatNumber));
					added = true;
				}
			}
		}
		
		if (!added) {
			// Message did not exist.
			this.communalSummary.add(message);
		}
	}

	public void clearSummary() {
		this.communalSummary.clear();
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
	
	public final void syncGameBoards() {
		try {
			GameState.syncGameBoards(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

				SyncData.LocationData locData = new SyncData.LocationData(s, l.getName(), type, l.getActivityNames());

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
		for (AbstractItem item : pChar.getInventory()) {
			Collection<SyncData.LocationData.LocationType> useLocations = new ArrayList<SyncData.LocationData.LocationType>();
			isLocationItem = item.getIsLocationItem();
			if (isLocationItem) {
				AbstractLocationItem loc_item = AbstractLocationItem.class.cast(item);
				for (LocationType locType : loc_item.getUseLocations()) {
					useLocations.add(SyncData.LocationData.LocationType.valueOf(locType.toString()));
				}
			}
			SyncData.ItemData itemData = new SyncData.ItemData(item.getName(), item.getDescription(),
					item.getFlavorText(), item.getImagePath(), isLocationItem, useLocations);
			personalInv.add(itemData);
		}

		Map<SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType, SyncData.ItemData> equipment = new HashMap<SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType, SyncData.ItemData>();
		Loadout load = pChar.getEquipment();

		for (EquipmentSlot slot : EquipmentSlot.values()) {
			Collection<SyncData.LocationData.LocationType> useLocations = new ArrayList<SyncData.LocationData.LocationType>();
			if (load.slotFull(slot)) {
				SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType type = SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData.EquipmentType
						.valueOf(slot.toString());
				AbstractEquipmentItem equipmentItem = load.itemIn(slot);
				SyncData.ItemData item = new SyncData.ItemData(equipmentItem.getName(), equipmentItem.getDescription(),
						equipmentItem.getFlavorText(), equipmentItem.getImagePath(), false, useLocations);
				equipment.put(type, item);
			}
		}

		SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData loadout = new SyncPlayerClientDataRequestData.PlayerCharacterData.LoadoutData(
				equipment);

		SyncPlayerClientDataRequestData.PlayerCharacterData dChar = new SyncPlayerClientDataRequestData.PlayerCharacterData(
				pChar.getCharacterName(), pChar.isParasite(), stat, pChar.getExposure(), personalInv, loadout,
				game.turnTaken(player), game.getPlayerIsReady(auth));

		List<SyncData.ItemData> inventory = new ArrayList<SyncData.ItemData>();
		for (AbstractItem item : getCommunalInventory()) {
			Collection<SyncData.LocationData.LocationType> useLocations = new ArrayList<SyncData.LocationData.LocationType>();
			isLocationItem = item.getIsLocationItem();
			if (isLocationItem) {
				AbstractLocationItem loc_item = AbstractLocationItem.class.cast(item);
				for (LocationType locType : loc_item.getUseLocations()) {
					useLocations.add(SyncData.LocationData.LocationType.valueOf(locType.toString()));
				}
			}
			SyncData.ItemData itemData = new SyncData.ItemData(item.getName(), item.getDescription(),
					item.getFlavorText(), item.getImagePath(), isLocationItem, useLocations);
			inventory.add(itemData);
		}

		SyncPlayerClientDataRequestData data = new SyncPlayerClientDataRequestData(getFood(), getFuel(), getDay(),
				playerWon, loc, dChar, characters, getGamePhase().toString(), inventory);

		return data;

	}

	// ==================================

}
