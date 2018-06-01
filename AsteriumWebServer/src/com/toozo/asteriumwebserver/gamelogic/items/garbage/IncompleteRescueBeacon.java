package com.toozo.asteriumwebserver.gamelogic.items.garbage;

public class IncompleteRescueBeacon extends AbstractGarbageItem {
	public static final String NAME = "Incomplete Rescue Beacon";
	public static final String DESCRIPTION = "1/3 complete.";
	public static final String FLAVOR = "Still missing a Power Supply and Control Module.";
	public static final String IMAGE = "incomplete_beacon.png";
	
	public IncompleteRescueBeacon() {
		super(NAME, DESCRIPTION, FLAVOR, IMAGE);
	}
}
