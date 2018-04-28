package actions;

import sessionmanagement.SessionManager.Session;

/**
 * Abstract Action class representing a Request.
 */
public abstract class RequestAction extends Action {

	/**
	 * Constructs a new RequestAction.
	 * @param name The name of the RequestAction.
	 * @param callingSession The session using this RequestAction.
	 */
	public RequestAction(String name, Session callingSession) {
		super(name, callingSession);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public final void run() {
		doAction();
	}
}
