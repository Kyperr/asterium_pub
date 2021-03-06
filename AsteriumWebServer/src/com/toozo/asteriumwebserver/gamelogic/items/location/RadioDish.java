package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.garbage.IncompleteRescueBeacon;

public class RadioDish extends AbstractLocationItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Radio Dish";
	public static final String DESC = "Used to create an Incomplete Rescue Beacon.";
	public static final String FLAV = "Allows a signal to reach the farthest reaches of space.";
	public static final String IMG = "dish.png";
	public static final Collection<Location.LocationType> CAN_BE_USED_IN;
	static {
		Collection<Location.LocationType> locs = new ArrayList<Location.LocationType>();
		
		locs.add(Location.LocationType.CONTROL_ROOM);
		
		CAN_BE_USED_IN = Collections.unmodifiableCollection(locs);
	}
	// =====================
	
	// ====== CONSTRUCTORS =====
	public RadioDish() {
		super(NAME, DESC, FLAV, IMG, CAN_BE_USED_IN);
	}
	// =========================
	
	// ===== METHODS =====
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		state.setRadioDishUsed(true);
		state.getCommunalInventory().add(new IncompleteRescueBeacon());
	}
	// ====================

}
