package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.statuseffects.AbstractStatusEffect;
import com.toozo.asteriumwebserver.gamelogic.statuseffects.AffectStats;

/**
 * The abstract for an {@link AbstractItem} that is equippable.
 * 
 * @author Studio Toozo
 */
public abstract class AbstractEquipmentItem extends AbstractItem {
	// ===== CONSTANTS =====
	public static final String EQUIP_MESSAGE = "You equipped %s.";
	public static final String UNEQUIP_MESSAGE = "You unequipped %s.";
	// =====================
	
	// ===== FIELDS =====
	private boolean isEquipped;
	private EquipmentSlot equipmentType;
	private Map<Stat, Integer> boosts;
	private String statusEffectName;
	// ==================

	// ===== CONSTRUCTORS =====
	/**
	 * Abstract constructor which defines which type of equipment this is.
	 * 
	 * @param equipmentType
	 *            the {@link EquipmentSlot} to which this item is native.
	 */
	public AbstractEquipmentItem(final String name, final String description, final String flavor, final String image,
			final EquipmentSlot equipmentType, final Map<Stat, Integer> boosts) {
		super(name, description, flavor, image, false);
		this.equipmentType = equipmentType;
		this.boosts = boosts;
		this.statusEffectName = this.getName() + " Equipped";
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
	public void use(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets,
			boolean fromCommunalInventory) {
		if (this.isEquipped) {
			this.unequip(user);
		} else {
			this.equip(user);
		}
		applyEffect(state, user, targets);
	}

	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {

	}

	/**
	 * @see com.toozo.asteriumwebserver.gamelogic.items.equipment.Loadout#equip(AbstractEquipmentItem)
	 * 
	 * @param equipper
	 *            The {@link PlayerCharacter} equipping this item.
	 */
	public void equip(PlayerCharacter equipper) {
		equipper.getEquipment().equip(this);
		equipper.addSummaryMessage(String.format(EQUIP_MESSAGE, this.getName()));
		
		// Add effect of this item
		Map<Stat, Function<Integer, Integer>> statModifiers = new HashMap<Stat, Function<Integer, Integer>>();
		for (Stat stat : this.boosts.keySet()) {
			statModifiers.put(stat, (oldStat) -> (oldStat + this.boosts.get(stat)));
		}
		AffectStats effect = new AffectStats(equipper, this.statusEffectName, statModifiers);
		equipper.addStatusEffect(effect);
	}

	/**
	 * If possible, unequips item from equipper and adds it to equipper's inventory.
	 * Possible reasons for an unsuccessful unequip include: Equipper is null,
	 * Equipper's inventory is full, Equipper did not have this item equipped.
	 * 
	 * @param equipper
	 *            The {@link PlayerCharacter} from whom this item should be
	 *            unequipped.
	 * @return whether the item was successfully unequipped.
	 */
	public boolean unequip(PlayerCharacter equipper) {
		EquipmentSlot mySlot = this.equipmentType;

		if (equipper != null) {
			Inventory equipperInventory = equipper.getInventory();
			Loadout equipperEquipment = equipper.getEquipment();

			// If item is in slot and inventory has room,
			// move it to inventory and return whether it worked.
			if (this == equipperEquipment.itemIn(mySlot) && !equipperInventory.isFull()) {
				equipper.addSummaryMessage(String.format(UNEQUIP_MESSAGE, this.getName()));
				this.setEquipped(false);
				boolean result = equipperInventory.add(equipperEquipment.removeFrom(mySlot));
				if (result) {
					for (AbstractStatusEffect se : equipper.getStatusEffects()) {
						if (se.getName().equals(this.statusEffectName)) {
							equipper.removeStatusEffect(se);
							break;
						}
					}
				}
				return result;
			}
		}

		// Something went wrong!
		return false;
	}
	// ===================
}
