package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.Player;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ErroredResponseData;
import actiondata.ToggleReadyUpResponseData;
import message.Message;
import message.Response;

/**
 * Action which allows a {@link Player} to indicate that their {@link Character}
 * is ready, and they are ready for the {@link Game} to start. Performed in the
 * PLAYERS_JOINING phase.
 * 
 * @author Studio Toozo
 */
public class ToggleReadyUpAction extends RequestAction {

	public ToggleReadyUpAction(final String authToken, final UUID messageID) {
		super(Action.TOGGLE_READY_UP, authToken, messageID);
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
		// Check to see if the player auth token was invalid / they are not a real
		// player
		if (game != null) {
			boolean isReady = game.toggleReady(auth);
			String playerName = game.getPlayer(auth).getPlayerName();
			
			if (Action.VERBOSE) {
				System.out.printf("Player %s is %s\n", playerName, isReady? "ready" : "not ready");
			}
			
			ToggleReadyUpResponseData data = new ToggleReadyUpResponseData(isReady);
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
	 * Get a {@link ToggleReadyUpAction} based on message.
	 * 
	 * @param message
	 *            the {@link Message} containing the {@link ToggleReadyUpAction}.
	 * @return a {@link ToggleReadyUpAction} containing the data from message.
	 */
	public static ToggleReadyUpAction fromMessage(final Message message) {
		return new ToggleReadyUpAction(message.getAuthToken(), message.getMessageID());
	}

}
