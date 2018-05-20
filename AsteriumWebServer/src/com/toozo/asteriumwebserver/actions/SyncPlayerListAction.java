package com.toozo.asteriumwebserver.actions;

import java.util.UUID;

import message.Message;

public class SyncPlayerListAction extends RequestAction {

	
	public SyncPlayerListAction(final String authToken, final UUID messageID) {
		super(Action.SYNC_PLAYER_LIST, authToken, messageID);
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Get a {@link SyncPlayerListAction} based on message.
	 * 
	 * @param message the {@link Message} containing the {@link SyncPlayerListAction}.
	 * @return a {@link SyncPlayerListAction} containing the data from message.
	 */
	public static SyncPlayerListAction fromMessage(final Message message) {
		return new SyncPlayerListAction(message.getAuthToken(), message.getMessageID());
	}
}
