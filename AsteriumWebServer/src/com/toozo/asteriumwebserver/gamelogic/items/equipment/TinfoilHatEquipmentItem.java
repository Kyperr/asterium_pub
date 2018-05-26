package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.HashMap;
import java.util.Map;

import com.toozo.asteriumwebserver.gamelogic.Stat;

public class TinfoilHatEquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Tinfoil Hat";
	public static final String DESC = "Increases your intuition by 1 while equipped";
	public static final String FLAV = "This piece of inginuity makes you able to avoid the "
			+ "trickery of the tyrannical government. Guard it well.";
	public static final String IMG = "head.png";
	public static final EquipmentSlot SLOT = EquipmentSlot.HEAD;
	public static final Map<Stat, Integer> BOOSTS = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.INTUITION, 1);
		}
	};

	// =====================
	
	// ===== CONSTRUCTORS =====

	public TinfoilHatEquipmentItem() {
		super(NAME, DESC, FLAV, IMG, SLOT, BOOSTS);
	}
	// ========================
	 

}
