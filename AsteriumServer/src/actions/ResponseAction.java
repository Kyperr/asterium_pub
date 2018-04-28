package actions;

import sessionmanagement.SessionManager.Session;

/**
 * Abstract Action class representing a Response.
 */
public abstract class ResponseAction extends Action {

	/**
	 * Constructs a new ResponseAction.
	 * @param name The name of the ResponseAction.
	 * @param callingSession The session using this ResponseAction.
	 */
	public ResponseAction(String name, Session callingSession) {
		super(name, callingSession);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public final void run() {
		super.run();
	}

}
