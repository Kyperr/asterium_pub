package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;

public class LettermanJacketEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String LETTERMAN_JACKET_NAME = "Letterman Jacket";
	public static final EquipmentSlot LETTERMAN_JACKET_SLOT = EquipmentSlot.TORSO;
	public static final Map<Stat, Integer> LETTERMAN_JACKET_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.STAMINA, 1);
		}
	};
	// =====================

	// ===== CONSTRUCTORS =====

	public LettermanJacketEquipmentItem(String name, EquipmentSlot equipmentType, Map<Stat, Integer> boosts) {
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
