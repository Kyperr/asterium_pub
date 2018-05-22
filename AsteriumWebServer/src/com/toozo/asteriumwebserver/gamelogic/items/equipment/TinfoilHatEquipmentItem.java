package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class TinfoilHatEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String TINFOIL_HAT_NAME = "Tinfoil Hat";
	public static final EquipmentSlot TINFOIL_HAT_SLOT = EquipmentSlot.HEAD;
	public static final Map<Stat, Integer> TINFOIL_HAT_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.INTUITION, 1);
		}
	};

	// =====================
	
	// ===== CONSTRUCTORS =====

	public TinfoilHatEquipmentItem(String name, EquipmentSlot equipmentType, Map<Stat, Integer> boosts) {
		super(name, equipmentType, boosts);
	}
	// ========================

	// ===== METHODS ======
	// ========================

	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {

	}

}
