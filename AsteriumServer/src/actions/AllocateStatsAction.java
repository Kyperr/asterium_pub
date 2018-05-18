package actions;

import java.util.UUID;

import message.Message;
import sessionmanagement.SessionManager.Session;

public class AllocateStatsAction extends RequestAction {

	public AllocateStatsAction(final Session callingSession, final UUID messageID) {
		super(Action.ALLOCATE_STATS, callingSession, messageID);
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub

	}
	
	public static AllocateStatsAction fromMessage(Session sender, final Message message) {
		return null;
	}

}
