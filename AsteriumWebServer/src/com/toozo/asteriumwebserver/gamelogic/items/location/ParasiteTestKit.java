package com.toozo.asteriumwebserver.gamelogic.items.location;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

public class ParasiteTestKit extends AbstractLocationItem {
	// ===== CONSTANTS =====
	public static final String NAME = "Parasite Test Kit";
	public static final double ACCURACY = 0.9;
	public static final String DESCRIPTION = String.format("Reveals parasites with %d%% accuracy.", (int) Math.floor(ACCURACY * 100));
	public static final String FLAVOR_TEXT = "Well... we're pretty sure.";
	public static final String IMAGE_NAME = "testkit.png";
	public static final String MESSAGE = "You tested %s, and the test came back %s.";
	public static final String POSITIVE = "positive";
	public static final String NEGATIVE = "negative";
	
	public static final Collection<Location.LocationType> CAN_BE_USED_IN;
	static {
		Collection<Location.LocationType> useLocations = new HashSet<Location.LocationType>();
		
		useLocations.add(Location.LocationType.CONTROL_ROOM);
		
		CAN_BE_USED_IN = Collections.unmodifiableCollection(useLocations);
	}
	
	private Random RNG = new Random();
	// =====================
	
	// ===== CONSTRUCTORS =====
	public ParasiteTestKit() {
		super(NAME, DESCRIPTION, FLAVOR_TEXT, IMAGE_NAME, CAN_BE_USED_IN);
	}
	// ========================
	
	@Override
	/**
	 * Note: If multiple targets are specified, only uses it on one. 
	 * 		 If the Collection is ordered, it will /probably/ be used on the first target,
	 * 		 but this is not guaranteed.
	 */
	public void applyEffect(GameState state, PlayerCharacter user, Collection<PlayerCharacter> targets) {
		double random = RNG.nextDouble();
		boolean accurate = false;
		if (random <= ACCURACY) {
			accurate = true;
		}
		
		Iterator<PlayerCharacter> iterator = targets.iterator();
		
		if (iterator.hasNext()) {
			PlayerCharacter target = iterator.next();
			boolean trueParasite = target.isParasite();
			
			// Determine whether test is positive or negative
			String posneg;
			if (accurate ^ trueParasite) {
				posneg = NEGATIVE;
			} else {
				posneg = POSITIVE;
			}
			
			user.addSummaryMessage(String.format(MESSAGE, user.getCharacterName(), posneg));
		}
	}

}
