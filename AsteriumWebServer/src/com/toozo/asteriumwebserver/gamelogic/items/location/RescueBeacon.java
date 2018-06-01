package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public class RescueBeacon extends AbstractLocationItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Rescue Beacon";
	public static final String DESC = "Use to request a rescue.";
	public static final String FLAV = "This beacon will allow you to contact rescuers to save you.";
	public static final String IMG = "rescue.png";
	public static final Collection<Location.LocationType> CAN_BE_USED_IN;
	static {
		Collection<Location.LocationType> locs = new ArrayList<Location.LocationType>();
		
		locs.add(Location.LocationType.CONTROL_ROOM);
		
		CAN_BE_USED_IN = Collections.unmodifiableCollection(locs);
	}
	// =====================
	
	// ====== CONSTRUCTORS =====
	public RescueBeacon() {
		super(NAME, DESC, FLAV, IMG, CAN_BE_USED_IN);
	}
	// =========================
	
	// ===== METHODS =====
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		state.setRescueBeaconUsed(true);
	}
	// ====================

}
