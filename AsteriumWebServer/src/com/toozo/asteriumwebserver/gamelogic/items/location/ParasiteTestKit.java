package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public class ParasiteTestKit extends AbstractLocationItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Parasite Test Kit";
	public static final String DESCRIPTION = "Reveals parasites with 90% accuracy.";
	public static final String FLAVOR_TEXT = "";
	public static final String IMAGE_NAME = "testkit.png";
	
	public static final Collection<Location.LocationType> CAN_BE_USED_IN;
	static {
		Collection<Location.LocationType> useLocations = new HashSet<Location.LocationType>();
		
		useLocations.add(Location.LocationType.CONTROL_ROOM);
		
		CAN_BE_USED_IN = Collections.unmodifiableCollection(useLocations);
	}
	// =====================
	
	// ===== CONSTRUCTORS =====
	public ParasiteTestKit() {
		super(NAME, DESCRIPTION, FLAVOR_TEXT, IMAGE_NAME, CAN_BE_USED_IN);
	}
	// ========================
	
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		// TODO Auto-generated method stub

	}

}
