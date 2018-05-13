package com.toozo.asteriumwebserver.actions;

import java.util.UUID;

public class TurnAction extends RequestAction {

	public TurnAction(final String authToken, final UUID messageID) {
		super(Action.TURN, authToken, messageID);
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub

	}

}
