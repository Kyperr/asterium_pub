package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Player;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import actiondata.SuccessResponseData;
import message.Message;
import message.Response;

/**
 * Action which allows a {@link Player} to indicate that their {@link Character} is ready, and
 * they are ready for the {@link Game} to start. Performed in the PLAYERS_JOINING phase.
 * 
 * @author Studio Toozo
 */
public class ReadyUpAction extends RequestAction {

	public ReadyUpAction(final String authToken, final UUID messageID) {
		super(Action.READY_UP, authToken, messageID);
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
		// Check to see if the player auth token was invalid / they are not a real player
		if (game != null) {
			GameState state = game.getGameState();
			state.toggleReady(auth);
			SuccessResponseData data = new SuccessResponseData(ActionData.READY_UP);
			message = new Response(data, 0, this.getMessageID(), this.getCallingAuthToken());
		} else {
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.GAME_NOT_FOUND, this.getMessageID(), this.getCallingAuthToken());
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
	 * Get a {@link ReadyUpAction} based on message.
	 * 
	 * @param message the {@link Message} containing the {@link ReadyUpAction}.
	 * @return a {@link ReadyUpAction} containing the data from message.
	 */
	public static ReadyUpAction fromMessage(final Message message) {
		return new ReadyUpAction(message.getAuthToken(), message.getMessageID());
	}

}
