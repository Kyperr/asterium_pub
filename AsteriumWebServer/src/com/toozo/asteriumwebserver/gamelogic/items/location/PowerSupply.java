package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.items.garbage.UnfinishedRescueBeacon;

public class PowerSupply extends AbstractLocationItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Power Supply";
	public static final String DESC = "Used to upgrade an Incomplete Rescue Beacon to an Unfinished Rescue Beacon.";
	public static final String FLAV = "Supplies power.";
	public static final String IMG = "psu.png";
	public static final Collection<Location.LocationType> CAN_BE_USED_IN;
	static {
		Collection<Location.LocationType> locs = new ArrayList<Location.LocationType>();
		
		locs.add(Location.LocationType.CONTROL_ROOM);
		
		CAN_BE_USED_IN = Collections.unmodifiableCollection(locs);
	}
	// =====================
	
	// ====== CONSTRUCTORS =====
	public PowerSupply() {
		super(NAME, DESC, FLAV, IMG, CAN_BE_USED_IN);
	}
	// =========================
	
	// ===== METHODS =====
	@Override
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		state.setPowerSupplyUsed(true);
		Inventory communalInventory = state.getCommunalInventory();
		AbstractItem lastItem = null;
		for (AbstractItem item : communalInventory) {
			if (item.getName().equals("Incomplete Rescue Beacon")) {
				lastItem = item;
				break;
			}
		}
		if (lastItem != null) {
			communalInventory.remove(lastItem);
		}
		communalInventory.add(new UnfinishedRescueBeacon());
	}
	// ====================

}
