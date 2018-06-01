package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

public class ControlModule extends AbstractLocationItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Control Module";
	public static final String DESC = "Used to upgrade an Unfinished Rescue Beacon to a complete Rescue Beacon.";
	public static final String FLAV = "";
	public static final String IMG = "module.png";
	public static final Collection<Location.LocationType> CAN_BE_USED_IN;
	static {
		Collection<Location.LocationType> locs = new ArrayList<Location.LocationType>();
		
		locs.add(Location.LocationType.CONTROL_ROOM);
		
		CAN_BE_USED_IN = Collections.unmodifiableCollection(locs);
	}
	// =====================
	
	// ====== CONSTRUCTORS =====
	public ControlModule() {
		super(NAME, DESC, FLAV, IMG, CAN_BE_USED_IN);
	}
	// =========================
	
	// ===== METHODS =====
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		state.setControlModuleUsed(true);
		Inventory communalInventory = state.getCommunalInventory();
		for (AbstractItem item : communalInventory) {
			if (item.getName().equals("Unfinished Rescue Beacon")) {
				communalInventory.remove(item);
			}
		}
		communalInventory.add(new RescueBeacon());
	}
	// ====================

}
