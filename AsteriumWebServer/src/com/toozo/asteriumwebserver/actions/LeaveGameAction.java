package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ErroredResponseData;
import actiondata.SuccessResponseData;
import message.Message;
import message.Response;

public class LeaveGameAction extends RequestAction {

	public LeaveGameAction(final String authToken, final UUID messageID) {
		super(Action.LEAVE_GAME, authToken, messageID);
	}

	@Override
	protected void doAction() {
		String auth = getCallingAuthToken();
		GameManager gameManager = GameManager.getInstance();
		Game game = gameManager.getGameForPlayer(auth);
		Message message;
		if (game != null) {
			if (Action.VERBOSE) {
				System.out.printf("Player %s has left the game\n", 
								  game.getPlayer(auth).getPlayerName());
			}
			game.removeClient(auth);
			gameManager.removeClient(auth);			
			SuccessResponseData data = new SuccessResponseData(Action.LEAVE_GAME);
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
	 * Get a {@link LeaveGameAction} based on message.
	 * 
	 * @param message
	 *            the {@link Message} containing the {@link LeaveGameAction}.
	 * @return a {@link LeaveGameAction} containing the data from message.
	 */
	public static LeaveGameAction fromMessage(final Message message) {
		return new LeaveGameAction(message.getAuthToken(), message.getMessageID());
	}
}
