package com.toozo.asteriumwebserver.gamelogic.items.equipment;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Stat;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.statuseffects.AffectStats;

public class EquipmentItem extends AbstractEquipmentItem {
	// ===== CONSTANTS =====
	public static final String TINFOIL_HAT_NAME = "Tinfoil Hat";
	public static final EquipmentSlot TINFOIL_HAT_SLOT = EquipmentSlot.HEAD;
	public static final Map<Stat, Integer> TINFOIL_HAT_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.INTUITION, 1);
		}
	};

	public static final String LETTERMAN_JACKET_NAME = "Letterman Jacket";
	public static final EquipmentSlot LETTERMAN_JACKET_SLOT = EquipmentSlot.TORSO;
	public static final Map<Stat, Integer> LETTERMAN_JACKET_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.STAMINA, 1);
		}
	};

	public static final String HAREY_GLOVES_NAME = "Harey Gloves";
	public static final EquipmentSlot HAREY_GLOVES_SLOT = EquipmentSlot.ARMS;
	public static final Map<Stat, Integer> HAREY_GLOVES_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.LUCK, 1);
		}
	};

	public static final String HOVER_SKATES_NAME = "Hover Skates";
	public static final EquipmentSlot HOVER_SKATES_SLOT = EquipmentSlot.LEGS;
	public static final Map<Stat, Integer> HOVER_SKATES_BOOST = new HashMap<Stat, Integer>() {
		private static final long serialVersionUID = -8100615337521308301L;
		{
			put(Stat.STAMINA, 1);
		}
	};
	
	public static final Map<Double, Supplier<? extends AbstractItem>> FACTORY_PROBABILITIES;
	static {
		Map<Double, Supplier<? extends AbstractItem>> probsMap = new HashMap<Double, Supplier<? extends AbstractItem>>();
		probsMap.put(0.25, EquipmentItem::createTinfoilHat);
		probsMap.put(0.25, EquipmentItem::createLettermanJacket);
		probsMap.put(0.25, EquipmentItem::createHareyGloves);
		probsMap.put(0.25, EquipmentItem::createHoverSkates);
		FACTORY_PROBABILITIES = Collections.unmodifiableMap(probsMap);
	}
	// =====================

	// ===== FIELDS =====
	private Map<Stat, Integer> boosts;
	// ==================

	// ===== FACTORIES =====
	public static EquipmentItem createTinfoilHat() {
		return new EquipmentItem(TINFOIL_HAT_NAME, TINFOIL_HAT_SLOT, TINFOIL_HAT_BOOST);
	}

	public static EquipmentItem createLettermanJacket() {
		return new EquipmentItem(LETTERMAN_JACKET_NAME, LETTERMAN_JACKET_SLOT, LETTERMAN_JACKET_BOOST);
	}

	public static EquipmentItem createHareyGloves() {
		return new EquipmentItem(HAREY_GLOVES_NAME, HAREY_GLOVES_SLOT, HAREY_GLOVES_BOOST);
	}

	public static EquipmentItem createHoverSkates() {
		return new EquipmentItem(HOVER_SKATES_NAME, HOVER_SKATES_SLOT, HOVER_SKATES_BOOST);
	}
	// =====================

	// ===== CONSTRUCTORS =====
	public EquipmentItem(final String name, final EquipmentSlot equipmentType, final Map<Stat, Integer> boosts) {
		super(name, equipmentType, FACTORY_PROBABILITIES);
		this.boosts = boosts;
	}
	// ========================

	// ===== METHODS ======
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		Map<Stat, Function<Integer, Integer>> statModifiers = new HashMap<Stat, Function<Integer, Integer>>();
		for (Stat stat : this.boosts.keySet()) {
			statModifiers.put(stat, (oldStat) -> (oldStat + this.boosts.get(stat)));
		}
		String name = this.getName() + " Equipped";
		AffectStats effect = new AffectStats(user, name, statModifiers);
		user.addStatusEffect(effect);
	}

}
