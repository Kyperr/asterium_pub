package com.toozo.asteriumwebserver.actions;

import java.util.UUID;

import message.Message;

public class ClientToServerResponseAction extends ResponseAction {

	public ClientToServerResponseAction(String name, String authToken, Integer errorCode, UUID messageID) {
		super(name, authToken, errorCode, messageID);
	}

	@Override
	protected void doAction() {
	}

	/**
	 * Get a {@link ClientToServerResponseAction} based on actionData.
	 * 
	 * @param message
	 *            The {@link Message} containing the
	 *            {@link ClientToServerResponseAction}.
	 * @return a {@link ClientToServerResponseAction} containing the data from
	 *         message.
	 */
	public static ClientToServerResponseAction fromMessage(final Message message) {
		return new ClientToServerResponseAction(message.getActionData().getName(), message.getAuthToken(), 0,
				message.getMessageID());

	}
}
