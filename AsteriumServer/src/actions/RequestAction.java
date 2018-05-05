package actions;

import java.util.UUID;

import sessionmanagement.SessionManager.Session;

/**
 * Abstract Action class representing a Request.
 */
public abstract class RequestAction extends Action {

	//protected UUID messageID;
	
	/**
	 * Constructs a new RequestAction.
	 * @param name The name of the RequestAction.
	 * @param callingSession The session using this RequestAction.
	 */
	public RequestAction(final String name, final Session callingSession, final UUID messageID) {
		super(name, callingSession, messageID);
		//this.messageID = messageID;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public final void run() {
		doAction();
	}
}
