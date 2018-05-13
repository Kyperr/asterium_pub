package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.CreateGameResponseData;
import actiondata.ErroredResponseData;
import message.Message;
import message.Response;

/**
 * A {@link RequestAction} which creates a new Game.
 * 
 * @author Studio Toozo
 */
public class CreateGameAction extends RequestAction {

	/**
	 * Create a new {@link CreateGameAction} for callingSession.
	 * 
	 * @param callingSession
	 *            the {@link Session} for which the {@link Game} will be created.
	 */
	public CreateGameAction(String authToken, final UUID messageID) {
		super(Action.CREATE_GAME, authToken, messageID);
	}

	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * Creates a new game and registers it with the {@link GameManager}.
	 */
	protected void doAction() {
		// Attempt to create the game.
		Game game = GameManager.getInstance().createGame();

		Message message;
		// If game was created...
		if (game != null) {
			// Generate response data
			CreateGameResponseData cgrData = new CreateGameResponseData(game.getLobbyID(),
					this.getCallingAuthToken());

			message = new Response(cgrData, 0, this.getMessageID(), this.getCallingAuthToken());

		} else { // If game was not created...
			// Generate error response
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.FAILED_TO_CREATE_GAME, this.getMessageID(),
					this.getCallingAuthToken());
		}

		// Send the response
		try {
			SessionManager.getInstance().getSession(getCallingAuthToken()).getBasicRemote()
					.sendText(message.jsonify().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get a {@link CreateGameAction} based on message.
	 * 
	 * @param sender
	 *            the {@link Session} used for this {@link CreateGameACtion}.
	 * @param message
	 *            the {@link Message} containing the {@link CreateGameAction}.
	 * @return a {@link CreateGameAction} containing the data from message.
	 */
	public static CreateGameAction fromMessage(final Message message) {
		return new CreateGameAction(message.getAuthToken(), message.getMessageID());
	}

}
