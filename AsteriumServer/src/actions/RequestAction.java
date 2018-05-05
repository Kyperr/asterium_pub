package actions;

import java.util.UUID;

import sessionmanagement.SessionManager.Session;

/**
 * Abstract {@link Action} class representing a {@link Request}.
 * 
 * @author Studio Toozo
 */
public abstract class RequestAction extends Action {
	
	/**
	 * Constructs a new {@link RequestAction}.
	 * @param name The name of the {@link RequestAction}.
	 * @param callingSession The {@link Session} using this {@link RequestAction}.
	 */
	public RequestAction(final String name, final Session callingSession, final UUID messageID) {
		super(name, callingSession, messageID);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public final void run() {
		doAction();
	}
}
