package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ErroredResponseData;
import actiondata.SetReadyStatusRequestData;
import actiondata.SuccessResponseData;
import message.Message;
import message.Response;

public class SetReadyStatusAction extends RequestAction {

	private boolean readyStatus;
	
	public SetReadyStatusAction(final String authToken, final UUID messageID, final boolean readyStatus) {
		super(Action.SET_READY_STATUS, authToken, messageID);
		this.readyStatus = readyStatus;
	}

	@Override
	protected void doAction() {
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message;
		// Check to see if the player auth token was invalid / they are not a real
		// player
		if (game != null) {
			game.setReadyStatus(auth, this.readyStatus);
			
			SuccessResponseData data = new SuccessResponseData(Action.SET_READY_STATUS);
			message = new Response(data, 0, this.getMessageID(), this.getCallingAuthToken());
		} else {
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.GAME_NOT_FOUND, this.getMessageID(),
					this.getCallingAuthToken());
		}
		// Send the response back to the calling session.
		try {
			Session session = SessionManager.getInstance().getSession(getCallingAuthToken());
			synchronized (session) {
				session.getBasicRemote().sendText(message.jsonify().toString());
			}
		} catch (IOException e) {
			// Error cannot be sent, so display in console
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a {@link SetReadyStatusAction} based on actionData.
	 * 
	 * @param message The {@link Message} containing the {@link SetReadyStatusAction}.
	 * @return a {@link SetReadyStatusAction} containing the data from message.
	 */
	public static SetReadyStatusAction fromMessage(final Message message) {
		SetReadyStatusRequestData action = SetReadyStatusRequestData.class.cast(message.getActionData());
		return new SetReadyStatusAction(message.getAuthToken(), message.getMessageID(), action.getIsReady());

	}

}
