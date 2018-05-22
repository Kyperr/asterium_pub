package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class HareyGlovesEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String HAREY_GLOVES_NAME = "Harey Gloves";
	public static final EquipmentSlot HAREY_GLOVES_SLOT = EquipmentSlot.ARMS;
	public static final Map<Stat, Integer> HAREY_GLOVES_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.LUCK, 1);
		}
	};
	// =====================

	// ===== CONSTRUCTORS =====
	
	public HareyGlovesEquipmentItem(String name, EquipmentSlot equipmentType, Map<Stat, Integer> boosts) {
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
