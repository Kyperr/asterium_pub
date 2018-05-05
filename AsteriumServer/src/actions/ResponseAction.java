package actions;

import java.util.UUID;

import sessionmanagement.SessionManager.Session;

/**
 * Abstract {@link Action} class representing a {@link Response}.
 */
public abstract class ResponseAction extends Action {

	/**
	 * Constructs a new {@link ResponseAction}.
	 * @param name The name of the {@link ResponseAction}.
	 * @param callingSession The {@link Session} using this {@link ResponseAction}.
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
