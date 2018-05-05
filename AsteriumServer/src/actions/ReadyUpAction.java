package actions;

import java.io.IOException;
import java.util.UUID;

import actiondata.ErroredResponseData;
import actiondata.ReadyUpResponseData;
import gamelogic.Game;
import gamelogic.GameManager;
import gamelogic.GameState;
import gamelogic.Player;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

/**
 * Action which allows a {@link Player} to indicate that their {@link Character} is ready, and
 * they are ready for the {@link Game} to start. Performed in the PLAYERS_JOINING phase.
 * 
 * @author Studio Toozo
 */
public class ReadyUpAction extends RequestAction {

	public ReadyUpAction(final Session callingSession, final UUID messageID) {
		super(Action.READY_UP, callingSession, messageID);
	}

	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * Toggles the ready status for a {@link Player}.
	 */
	protected void doAction() {
		String auth = getCallingSession().getAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message;
		// Check to see if the player auth token was invalid / they are not a real player
		if (game != null) {
			GameState state = game.getGameState();
			state.toggleReady(auth);
			ReadyUpResponseData data = new ReadyUpResponseData();
			message = new Response(data, 0, this.getMessageID());
		} else {
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.GAME_NOT_FOUND, this.getMessageID());
		}
		// Send the response back to the calling session.
		try {
			this.getCallingSession().sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a {@link ReadyUpAction} based on message.
	 * 
	 * @param sender the {@link Session} used for this {@link ReadyUpAction}.
	 * @param message the {@link Message} containing the {@link ReadyUpAction}.
	 * @return a {@link ReadyUpAction} containing the data from message.
	 */
	public static ReadyUpAction fromMessage(final Session callingSession, final Message message) {
		return new ReadyUpAction(callingSession, message.getMessageID());
	}

}
