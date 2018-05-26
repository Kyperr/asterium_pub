package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.Stat;

public class HoverSkatesEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Hover Skates";
	public static final String DESC = "Increases your stamina by 1 while equipped";
	public static final String FLAV = "These hover skates might look silly, "
			+ "but they're faster than walking and you can avoid those weird puddles of goo on the floor.";
	public static final String IMG = "legs.png";
	public static final EquipmentSlot SLOT = EquipmentSlot.LEGS;
	public static final Map<Stat, Integer> BOOSTS = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.STAMINA, 1);
		}
	};
	// =====================

	// ===== CONSTRUCTORS =====

	public HoverSkatesEquipmentItem() {
		super(NAME, DESC, FLAV, IMG, SLOT, BOOSTS);
	}
	// ========================

}
