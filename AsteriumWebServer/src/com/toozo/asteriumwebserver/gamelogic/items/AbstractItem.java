package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodItem;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.HealItem;

/**
 * The abstract class for an item that can be used by a {@link PlayerCharacter}.
 * 
 * @author Studio Toozo
 */
public abstract class AbstractItem {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "";
	public static final Random RNG = new Random();
	
	private static final Map<String, Supplier<AbstractItem>> ITEM_LOOKUP = new HashMap<String, Supplier<AbstractItem>>() {
		/**
		 * Auto-generated unique identifier for ITEM_LOOKUP
		 */
		private static final long serialVersionUID = 3292064164504904735L;
		
		/*
		 * Static block in which ITEM_LOOKUP is populated. As new Items are written,
		 * their corresponding constructors should be added here.
		 */
		{
			put(HealItem.BANDAGE_NAME, HealItem::createBandage);
			put(HealItem.MEDKIT_NAME, HealItem::createMedkit);
			put(HealItem.TRIAGE_NAME, HealItem::createTriage);
			
			put(FoodItem.PACK_NAME, FoodItem::createPack);
			put(FoodItem.CRATE_NAME, FoodItem::createCrate);
			put(FoodItem.CHEST_NAME, FoodItem::createChest);
		}
	};
	// =====================
	
	// ===== FIELDS =====
	private String name;
	private Map<Double, Supplier<? extends AbstractItem>> factoryProbabilities;
	// ==================
	
	// ===== CONSTRUCTORS =====
	protected AbstractItem(final String name, 
						   final Map<Double, Supplier<? extends AbstractItem>> factoryProbabilities) {
		this.name = name;
		this.factoryProbabilities = factoryProbabilities;
	}
	// ========================
	
	// ===== GETTERS =====
	public String getName() {
		if (this.name != null) {
			return this.name;
		} else {
			return DEFAULT_NAME;
		}
	}
	// ===================
	
	// ===== SETTERS =====
	/**
	 * Change the name of this Item.
	 * 
	 * @param newName The new name. If null, name will be unchanged.
	 */
	public void setName(final String newName) {
		if (newName != null) {
			this.name = newName;
		} else if (this.name == null) {
			this.name = DEFAULT_NAME;
		}
	}
	// ===================
	
	// ===== METHODS =====
	/**
	 * Constructs the appropriate Item based on itemName.
	 * 
	 * @param itemName The name of the item to construct.
	 * @return A new Item based on itemName.
	 */
	public static AbstractItem getItem(final String itemName) {		
		return AbstractItem.ITEM_LOOKUP.get(itemName).get();
	}
	
	/**
	 * Use this item.
	 * 
	 * Should be implemented by specific item class (e.g. Medkit) 
	 * to perform whatever should happen when this item is used.
	 * 
	 * @param state The state of the game which may be changed by this item's effect.
	 * @param user The {@link PlayerCharacter} which is using this item.
	 * @param targets The {@link PlayerCharacter}s which this item may affects.
	 * @param fromCommunalInventory True if this item is in the communal inventory,
	 * 								false if it's in user's inventory.
	 */
	public abstract void use(final GameState state, 
							 final PlayerCharacter user, 
							 final Collection<PlayerCharacter> targets,
							 final boolean fromCommunalInventory);
	
	/**
	 * Apply the effect of this item.
	 * 
	 * @param state The state of the game which may be changed by this item's effect.
	 * @param user The {@link PlayerCharacter} which is using this item.
	 * @param targets The {@link PlayerCharacter}s which this item may affects.
	 */
	public abstract void applyEffect(final GameState state, final PlayerCharacter user, final Collection<PlayerCharacter> targets);
	
	/**
	 * Probabilistically select a subitem from the implementing superitem class.
	 * 
	 * e.g. HealItem.getLoot() returns Bandage 60% of the time, 
	 * Medkit 30% of the time, Triage 10% of the time.
	 * 
	 * @return a subitem.
	 */
	public AbstractItem getLoot() {
		int i;
		
		// Generate array of thresholds based on probabilities
		// Example: 
		// 		Probabilities -> [0.6, 0.3, 0.1] 
		//		Thresholds ----> [0.6, 0.9, 1.0]
		Double[] weights = new Double[this.factoryProbabilities.keySet().size()];
		this.factoryProbabilities.keySet().toArray(weights);
		for (i = 1; i < weights.length; i++) {
			weights[i] += weights[i - 1];
		}
		
		// Determine which factory should be called
		double random = RNG.nextDouble();
		for (i = 0; random > weights[i]; i++) {}
		
		// Call the selected factory and return the result
		return this.factoryProbabilities.get(weights[i]).get();
	}
	// ===================
}
