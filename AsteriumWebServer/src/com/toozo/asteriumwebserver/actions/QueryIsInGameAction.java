package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import actiondata.QueryIsInGameResponseData;
import actiondata.SuccessResponseData;
import message.Message;
import message.Response;

public class QueryIsInGameAction extends RequestAction {

	public QueryIsInGameAction(final String authToken, final UUID messageID) {
		super(Action.QUERY_IS_IN_GAME, authToken, messageID);
	}

	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * Toggles the ready status for a {@link Player}.
	 */
	protected void doAction() {
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message;

		if (game != null) {
			QueryIsInGameResponseData data = new QueryIsInGameResponseData(true);
			message = new Response(data, 0, this.getMessageID(), this.getCallingAuthToken());
		} else {
			QueryIsInGameResponseData data = new QueryIsInGameResponseData(false);
			message = new Response(data, 0, this.getMessageID(), this.getCallingAuthToken());
		}
		// Send the response back to the calling session.
		try {
			SessionManager.getInstance().getSession(getCallingAuthToken()).getBasicRemote()
			.sendText(message.jsonify().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a {@link ToggleReadyUpAction} based on message.
	 * 
	 * @param message the {@link Message} containing the {@link ToggleReadyUpAction}.
	 * @return a {@link ToggleReadyUpAction} containing the data from message.
	 */
	public static QueryIsInGameAction fromMessage(final Message message) {
		return new QueryIsInGameAction(message.getAuthToken(), message.getMessageID());
	}

}