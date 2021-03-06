package com.toozo.asteriumwebserver.gamelogic.items.garbage;

public class UnfinishedRescueBeacon extends AbstractGarbageItem {
	public static final String NAME = "Unfinished Rescue Beacon";
	public static final String DESCRIPTION = "2/3 complete.";
	public static final String FLAVOR = "Still missing a Control Module.";
	public static final String IMAGE = "unfinished_beacon.png";
	
	public UnfinishedRescueBeacon() {
		super(NAME, DESCRIPTION, FLAVOR, IMAGE);
	}
}
