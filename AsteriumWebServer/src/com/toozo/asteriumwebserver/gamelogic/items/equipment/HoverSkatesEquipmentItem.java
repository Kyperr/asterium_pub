package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class HoverSkatesEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String HOVER_SKATES_NAME = "Hover Skates";
	public static final EquipmentSlot HOVER_SKATES_SLOT = EquipmentSlot.LEGS;
	public static final Map<Stat, Integer> HOVER_SKATES_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.STAMINA, 1);
		}
	};
	// =====================

	// ===== CONSTRUCTORS =====

	public HoverSkatesEquipmentItem(String name, EquipmentSlot equipmentType, Map<Stat, Integer> boosts) {
		super(name, equipmentType, boosts);
		// TODO Auto-generated constructor stub
	}

	// ===== METHODS ======
	// ========================
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		// TODO Auto-generated method stub

	}

}
