package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.Player;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.QueryIsInGameResponseData;
import message.Message;
import message.Request;
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
			Session session = SessionManager.getInstance().getSession(getCallingAuthToken());
			synchronized (session) {
				session.getBasicRemote().sendText(message.jsonify().toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// This is for a BANDAID! It shoud be fixed after the class.
		if (game != null) {
			syncPlayerClient(game, game.getPlayer(auth));
		}

	}

	private void syncPlayerClient(Game game, Player player) {
		ActionData data = game.getGameState().createSyncPlayerClientDataRequestData(player);
		Request request = new Request(data, player.getAuthToken());

		try {
			Session session = SessionManager.getInstance().getSession(getCallingAuthToken());
			synchronized (session) {
				session.getBasicRemote().sendText(request.jsonify().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get a {@link ToggleReadyUpAction} based on message.
	 * 
	 * @param message
	 *            the {@link Message} containing the {@link ToggleReadyUpAction}.
	 * @return a {@link ToggleReadyUpAction} containing the data from message.
	 */
	public static QueryIsInGameAction fromMessage(final Message message) {
		return new QueryIsInGameAction(message.getAuthToken(), message.getMessageID());
	}

}