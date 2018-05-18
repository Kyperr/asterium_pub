package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;
import java.util.Objects;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

/**
 * The abstract for an item that is equippable.
 * 
 * @author Greg Schmitt
 */
public abstract class AbstractEquipmentItem extends AbstractItem {
	// ===== FIELDS =====
	private boolean isEquipped;
	private EquipmentSlot equipmentType;
	// ==================
	
	// ===== CONSTRUCTORS =====
	/**
	 * Abstract constructor which defines which type of equipment this is.
	 * @param equipmentType the {@link EquipmentSlot} to which this item is native.
	 */
	protected AbstractEquipmentItem(EquipmentSlot equipmentType) {
		super();
		this.equipmentType = equipmentType;
	}
	// ========================
	
	// ===== GETTERS =====
	/**
	 * @return which {@link EquipmentSlot} this item belongs in.
	 */
	public EquipmentSlot getType() {
		return this.equipmentType;
	}
	
	/**
	 * @return whether this item is currently equipped.
	 */
	public boolean isEquipped() {
		return this.isEquipped;
	}
	// ===================
	
	// ===== SETTERS =====
	public void setEquipped(boolean equipped) {
		this.isEquipped = equipped;
	}
	// ===================
	
	// ===== METHODS =====
	@Override
	public void use(GameState state, 
					PlayerCharacter user, 
					Collection<PlayerCharacter> targets,
					boolean fromCommunalInventory) {
		this.equip(user);
	}
	
	/**
	 * @see com.toozo.asteriumwebserver.gamelogic.items.equipment.Equipment#equip(AbstractEquipmentItem)
	 * 
	 * @param equipper The {@link PlayerCharacter} equipping this item.
	 */
	public void equip(PlayerCharacter equipper) {
		equipper.getEquipment().equip(this);
	}
	
	/**
	 * If possible, unequips item from equipper and adds it to equipper's inventory.
	 * Possible reasons for an unsuccessful unequip include:
	 * Equipper is null,
	 * Equipper's inventory is full,
	 * Equipper did not have this item equipped.
	 * 
	 * @param equipper The {@link PlayerCharacter} from whom this item should be unequipped.
	 * @return whether the item was successfully unequipped.
	 */
	public boolean unequip(PlayerCharacter equipper) {
		EquipmentSlot mySlot = this.equipmentType;
		
		if (equipper != null) {
			Inventory equipperInventory = equipper.getInventory();
			Equipment equipperEquipment = equipper.getEquipment();
			
			// If item is in slot and inventory has room, 
			// move it to inventory and return whether it worked.
			if (this == equipperEquipment.itemIn(mySlot) && !equipperInventory.isFull()) {
				return equipperInventory.add(equipperEquipment.removeFrom(mySlot));
			}
		}
		
		// Something went wrong!
		return false;
	}
	// ===================
}