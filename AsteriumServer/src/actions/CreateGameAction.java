package actions;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import actiondata.CreateGameResponseData;
import actiondata.ErroredResponseData;
import gamelogic.Game;
import gamelogic.GameManager;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

/**
 * An Action which creates a new Game.
 * 
 * @author Studio Toozo
 */
public class CreateGameAction extends RequestAction {
	// The Game which is created by this CreateGameAction.
	private Optional<Game> game;

	/**
	 * Create a new CreateGameAction for callingSession.
	 * @param callingSession the session for which the Game will be created.
	 */
	public CreateGameAction(Session callingSession, final UUID messageID) {
		super(Action.CREATE_GAME, callingSession, messageID);
	}


	@Override
	/**
	 * {@inheritDoc}
	 * 
	 * Creates a new game and registers it with the GameManager.
	 */
	protected void doAction() {
		// Attempt to create the game.
		Game game = GameManager.getInstance().createGame();
		this.game = Optional.of(game);

		Message message;
		// If game was created...
		if (this.game.isPresent()) {
			// Generate response data
			game = this.game.get();
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

	public static CreateGameAction fromMessage(Session sender, final Message message) {
		// This is not used here yet, but is here in case anything gets added later.
		//CreateGameActionData action = CreateGameActionData.class.cast(actionData);

		return new CreateGameAction(sender, message.getMessageID());
	}

}
