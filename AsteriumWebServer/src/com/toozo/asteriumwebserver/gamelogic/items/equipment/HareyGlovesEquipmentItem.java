package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.Stat;

public class HareyGlovesEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Harey Gloves";
	public static final EquipmentSlot SLOT = EquipmentSlot.ARMS;
	public static final Map<Stat, Integer> BOOSTS = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.LUCK, 1);
		}
	};
	// =====================

	// ===== CONSTRUCTORS =====
	
	public HareyGlovesEquipmentItem() {
		super(NAME, SLOT, BOOSTS);
	}
	// ========================

}
