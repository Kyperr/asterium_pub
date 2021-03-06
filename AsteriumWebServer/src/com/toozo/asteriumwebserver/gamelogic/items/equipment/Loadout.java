package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

/**
 * A player's equipment, with slots for each type of {@link AbstractEquipmentItem}.
 * 
 * @author Greg Schmitt
 */
public class Loadout implements Iterable<AbstractEquipmentItem> {
	// ===== CONSTANTS =====
	public static final String OWNER_ERROR = "NULL OWNER PASSED TO LOADOUT CONSTRUCTOR";
	// =====================
	
	// ===== FIELDS =====
	private Map<EquipmentSlot, AbstractEquipmentItem> slots;
	private PlayerCharacter owner;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Construct an Equipment with all slots empty.
	 * @param owner The {@link PlayerCharacter} whose equipment this object will represent.
	 */
	public Loadout(PlayerCharacter owner) {
		this(owner, Arrays.asList());
	}
	
	/**
	 * Construct an Equipment with starterItem equipped.
	 * @param owner The {@link PlayerCharacter} whose equipment this object will represent.
	 */
	public Loadout(PlayerCharacter owner, AbstractEquipmentItem starterItem) {
		this(owner, Arrays.asList(starterItem));
	}
	
	/**
	 * Constructs a new Equipment with as many of the {@link AbstractEquipmentItem}s in
	 * starterItems equipped as possible.
	 * 
	 * If multiple items are of the same type (e.g. two headgear items), there is no guarantee
	 * which will be equipped.
	 * 
	 * @param owner The {@link PlayerCharacter} whose equipment this object will represent.
	 * @param starterItems a list of items which should be equipped if possible.
	 */
	public Loadout(PlayerCharacter owner, Collection<AbstractEquipmentItem> starterItems) {
		this.owner = Objects.requireNonNull(owner, OWNER_ERROR);
		this.slots = new ConcurrentHashMap<EquipmentSlot, AbstractEquipmentItem>();
		
		// Put items into slots
		EquipmentSlot type;
		if (starterItems != null) {
			for (AbstractEquipmentItem item : starterItems) {
				type = item.getType();
				boolean canEquip =  slotEmpty(type);
				item.setEquipped(canEquip);
				if (canEquip) {
					// Equip it
					this.slots.put(type, item);
				}
			}
		}
	}
	// ========================
	
	// ===== GETTERS =====
	/**
	 * @param slot The {@link EquipmentSlot} to be checked.
	 * @return True if the equipmentSlot is already occupied, false otherwise.
	 */
	public boolean slotFull(EquipmentSlot slot) {
		return this.slots.containsKey(slot);
	}
	
	/**
	 * @param slot The {@link EquipmentSlot} to be checked.
	 * @return True if the equipmentSlot is unoccupied, false otherwise.
	 */
	public boolean slotEmpty(EquipmentSlot slot) {
		return !slotFull(slot);
	}
	
	/**
	 * @param slot The slot from which to get the {@link AbstractEquipmentItem}.
	 * @return The {@link AbstractEquipmentItem} in slot if there is one, null otherwise.
	 */
	public AbstractEquipmentItem itemIn(EquipmentSlot slot) {
		return this.slots.get(slot);
	}
	// ===================
	
	// ===== METHODS =====
	/**
	 * If item is non-null and the owner of this Loadout has item, equips item to owner's 
	 * appropriate slot, otherwise does nothing. If equipper already has 
	 * an equipment item in that slot, swaps them.
	 * 
	 * @param item The item to be equipped.
	 * @param equipper The {@link PlayerCharacter} to whom this item should be equipped.
	 */
	public void equip(AbstractEquipmentItem item) {
		Inventory ownerInventory = owner.getInventory();
		
		// Make sure that equipper has the item.
		if (item != null && ownerInventory.contains(item)) {
			EquipmentSlot type = item.getType();
			ownerInventory.remove(item);
			
			if (owner.getEquipment().slotFull(type)) {
				// Move equipped item to inventory
				ownerInventory.add(this.removeFrom(type));
			}
			
			// Put item into slot
			this.slots.put(type, item);
			item.setEquipped(true);
		}
	}
	
	/**
	 * Removes the item from this loadout, if this item is equipped in this loadout.
	 * 
	 * @param item The loadout from which this item should be removed.
	 */
	/*
	public void unequip(AbstractEquipmentItem item) {
		EquipmentSlot slot = item.getType();
		if (this.itemIn(slot) == item) {
			this.removeFrom(slot);
			this.owner.getInventory().add(item);
			item.setEquipped(false);
		}
	}
	*/
	
	/**
	 * Removes and returns the {@link AbstractEquipmentItem} (if any) from the appropriate slot.
	 * 
	 * @param slot The slot from which the {@link AbstractEquipmentItem} should be removed.
	 * @return {@link AbstractEquipmentItem} that was equipped, or null if there was none.
	 */
	public AbstractEquipmentItem removeFrom(EquipmentSlot slot) {
		AbstractEquipmentItem result = this.slots.remove(slot);
		
		if (result != null) {
			result.setEquipped(false);
		}
		
		return result;
	}
	
	@Override
	public Iterator<AbstractEquipmentItem> iterator() {
		return this.slots.values().iterator();
	}
	// ===================

}
