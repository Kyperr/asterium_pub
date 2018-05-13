package com.toozo.asteriumwebserver.actions;

import java.util.UUID;

import javax.websocket.Session;


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
	public RequestAction(final String name, String authToken, final UUID messageID) {
		super(name, authToken, messageID);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public final void run() {
		doAction();
	}
}
