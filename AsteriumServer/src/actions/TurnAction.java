package actions;

import java.util.UUID;

import sessionmanagement.SessionManager.Session;

public class TurnAction extends RequestAction {

	public TurnAction(Session callingSession, UUID messageID) {
		super(Action.TURN, callingSession, messageID);
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub

	}

}
