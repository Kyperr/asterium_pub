package com.toozo.asteriumwebserver.actions;

import java.util.UUID;


/**
 * Abstract {@link Action} class representing a {@link Response}.
 */
public abstract class ResponseAction extends Action {

	private final Integer errorCode;
	
	
	/**
	 * Constructs a new {@link ResponseAction}.
	 * @param name The name of the {@link ResponseAction}.
	 * @param callingSession The {@link Session} using this {@link ResponseAction}.
	 */
	public ResponseAction(final String name, final String authToken, final Integer errorCode, final UUID messageID) {
		super(name, authToken, messageID);
		this.errorCode = errorCode;
	}
	
	

	@Override
	/**
	 * {@inheritDoc}
	 */
	public final void run() {
		super.run();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

}
