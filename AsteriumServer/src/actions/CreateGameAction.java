package actions;

import java.io.IOException;
import java.util.UUID;

import actiondata.CreateGameResponseData;
import actiondata.ErroredResponseData;
import gamelogic.Game;
import gamelogic.GameManager;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

/**
 * A {@link RequestAction} which creates a new Game.
 * 
 * @author Studio Toozo
 */
public class CreateGameAction extends RequestAction {

	/**
	 * Create a new {@link CreateGameAction} for callingSession.
	 * @param callingSession the {@link Session} for which the {@link Game} will be created.
	 */
	public CreateGameAction(final Session callingSession, final UUID messageID) {
		super(Action.CREATE_GAME, callingSession, messageID);
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
					this.getCallingSession().getAuthToken());
			
			message = new Response(cgrData, 0, this.getMessageID());

		} else { // If game was not created...
			// Generate error response
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.FAILED_TO_CREATE_GAME, this.getMessageID());
		}

		// Send the response
		try {
			this.getCallingSession().sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get a {@link CreateGameAction} based on message.
	 * 
	 * @param sender the {@link Session} used for this {@link CreateGameACtion}.
	 * @param message the {@link Message} containing the {@link CreateGameAction}.
	 * @return a {@link CreateGameAction} containing the data from message.
	 */
	public static CreateGameAction fromMessage(final Session sender, final Message message) {
		// This is not used here yet, but is here in case anything gets added later.
		//CreateGameActionData action = CreateGameActionData.class.cast(actionData);

		return new CreateGameAction(sender, message.getMessageID());
	}

}
