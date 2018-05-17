package com.toozo.asteriumwebserver.actions;

import java.util.UUID;

public class SyncLocationsAction extends RequestAction {

	public SyncLocationsAction(final String authToken, final UUID messageID) {
		super(Action.SYNC_LOCATIONS, authToken, messageID);
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub

	}

}
