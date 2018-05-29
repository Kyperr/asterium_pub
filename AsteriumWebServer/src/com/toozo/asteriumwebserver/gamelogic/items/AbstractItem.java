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
import com.toozo.asteriumwebserver.gamelogic.items.equipment.TinfoilHatEquipmentItem;

/**
 * The abstract class for an item that can be used by a {@link PlayerCharacter}.
 * 
 * @author Studio Toozo
 */
public abstract class AbstractItem {
	// ===== CONSTANTS =====
	public static final String DEFAULT_NAME = "";
	public static final String DEFAULT_DESC = "";
	public static final String DEFAULT_FLAV = "";
	public static final String DEFAULT_IMG = "";
	
	// =====================
	
	// ===== FIELDS =====
	private String name;
	private String description;
	private String flavor;
	private String image;
	private final boolean isLocationItem;
	// ==================
	
	// ===== CONSTRUCTORS =====
	protected AbstractItem(final String name, final String description, final String flavor, final String image, final boolean isLocationItem) {
		this.name = name;
		this.description = description;
		this.flavor = flavor;
		this.image = image;
		this.isLocationItem = isLocationItem;
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
	
	public String getDescription() {
		if (this.description != null) {
			return this.description;
		} else {
			return DEFAULT_DESC;
		}
	}
	
	public String getFlavorText() {
		if (this.flavor != null) {
			return this.flavor;
		} else {
			return DEFAULT_FLAV;
		}
	}
	
	public String getImagePath() {
		if (this.image != null) {
			return this.image;
		} else {
			return DEFAULT_IMG;
		}
	}
	
	public boolean getIsLocationItem() {
		return this.isLocationItem;
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

	/**
	 * Change the description of this Item.
	 * 
	 * @param newDescription The new description. If null, description will be unchanged.
	 */
	public void setDescription(final String newDescription) {
		if (newDescription != null) {
			this.description = newDescription;
		} else if (this.description == null) {
			this.description = DEFAULT_DESC;
		}
	}

	/**
	 * Change the flavor text of this Item.
	 * 
	 * @param newFlavor The new flavor text. If null, flavor text will be unchanged.
	 */
	public void setFlavorText(final String newFlavor) {
		if (newFlavor != null) {
			this.flavor = newFlavor;
		} else if (this.flavor == null) {
			this.flavor = DEFAULT_FLAV;
		}
	}

	/**
	 * Change the image path of this Item.
	 * 
	 * @param newPath The new image path. If null, image path will be unchanged.
	 */
	public void setImagePath(final String newPath) {
		if (newPath != null) {
			this.image = newPath;
		} else if (this.image == null) {
			this.image = DEFAULT_IMG;
		}
	}
	// ===================
	
	// ===== METHODS =====
	
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
