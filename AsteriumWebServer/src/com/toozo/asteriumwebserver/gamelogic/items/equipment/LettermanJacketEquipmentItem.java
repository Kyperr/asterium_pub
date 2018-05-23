package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.Stat;

public class LettermanJacketEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Letterman Jacket";
	public static final EquipmentSlot SLOT = EquipmentSlot.TORSO;
	public static final Map<Stat, Integer> BOOSTS = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.STAMINA, 1);
		}
	};
	// =====================

	// ===== CONSTRUCTORS =====

	public LettermanJacketEquipmentItem() {
		super(NAME, SLOT, BOOSTS);
	}
	// ========================
	
}
