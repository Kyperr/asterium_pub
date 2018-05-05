package actions;

import java.util.UUID;

import sessionmanagement.SessionManager.Session;

/**
 * Abstract Action class representing a Response.
 */
public abstract class ResponseAction extends Action {

	/**
	 * Constructs a new ResponseAction.
	 * @param name The name of the Response.
	 * @param callingSession The session using this ResponseAction.
	 */
	public ResponseAction(final String name, final Session callingSession, final Integer errorCode, final UUID messageID) {
		super(name, callingSession, messageID);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public final void run() {
		super.run();
	}

}
