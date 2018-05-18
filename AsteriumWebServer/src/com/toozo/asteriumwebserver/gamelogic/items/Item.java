package com.toozo.asteriumwebserver.gamelogic.items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public abstract class Item {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "";
	
	private static final Map<String, Supplier<Item>> ITEM_LOOKUP = new HashMap<String, Supplier<Item>> (){
		/**
		 * Auto-generated unique identifier for ITEM_LOOKUP
		 */
		private static final long serialVersionUID = 3292064164504904735L;
		
		/*
		 * Static block in which ITEM_LOOKUP is populated. As new Items are written,
		 * their corresponding constructors should be added here.
		 */
		{
			//something like: 
			//put("Medkit", Medkit::new Medkit);
		}

	{
	
	}
	};
	
	// =====================
	
	// ===== FIELDS =====
	private String name;
	// ==================
	
	// ===== CONSTRUCTORS =====
	protected Item(String name) {
		this.name = name;
	}
	
	protected Item() {
		this.name = DEFAULT_NAME;
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
	public void setName(String newName) {
		if (newName != null) {
			this.name = newName;
		} else if (this.name == null) {
			this.name = DEFAULT_NAME;
		}
	}
	// ===================
	
	// ===== METHODS =====
	
	public static Item getItem(final String itemName) {		
		return Item.ITEM_LOOKUP.get(itemName).get();
	}
	
	/**
	 * Use this item.
	 * 
	 * Should be implemented by specific item class (e.g. Medkit) 
	 * to apply the effect of that particular item.
	 * 
	 * @param state The state of the game which may be changed by this item's effect.
	 * @param user The {@link PlayerCharacter} which is using this item.
	 * @param targets The {@link PlayerCharacter}s which this item may affects.
	 * @param communalInventory If the {@link Item} came from the communal inventory
	 */
	public abstract void use(final GameState state, final PlayerCharacter user, 
							 final Collection<PlayerCharacter> targets, 
							 final boolean communalInventory);
	// ===================
}
