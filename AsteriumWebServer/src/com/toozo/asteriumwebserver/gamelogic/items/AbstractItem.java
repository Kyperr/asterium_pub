package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Bandage;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodChest;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodCrate;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.FoodPack;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Medkit;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.RescueBeacon;
import com.toozo.asteriumwebserver.gamelogic.items.consumables.Syringe;

/**
 * The abstract class for an item that can be used by a {@link PlayerCharacter}.
 * 
 * @author Studio Toozo
 */
public abstract class AbstractItem {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "";
	
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
			put(Bandage.NAME, Bandage::new);
			put(Medkit.NAME, Medkit::new);
			put(Syringe.NAME, Syringe::new);
			
			put(FoodPack.NAME, FoodPack::new);
			put(FoodCrate.NAME, FoodCrate::new);
			put(FoodChest.NAME, FoodChest::new);
			
			put(RescueBeacon.NAME, RescueBeacon::new);
		}
	};
	// =====================
	
	// ===== FIELDS =====
	private String name;
	// ==================
	
	// ===== CONSTRUCTORS =====
	protected AbstractItem(final String name) {
		this.name = name;
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
	// ===================
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractItem other = (AbstractItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
