package actions;

import java.io.IOException;
import java.util.Optional;

import actiondata.ActionData;
import actiondata.CreateGameRequestData;
import actiondata.CreateGameResponseData;
import actiondata.ErroredResponseData;
import gamelogic.Game;
import gamelogic.GameManager;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

public class CreateGameAction extends RequestAction {

	private Optional<Game> game;

	public CreateGameAction(Session callingSession) {
		super(Action.CREATE_GAME, callingSession);
	}

	@Override
	protected void doAction() {
		Game game = GameManager.getInstance().createGame();
		this.game = Optional.of(game);

		Message message;
		if (this.game.isPresent()) {
			game = this.game.get();
			CreateGameResponseData cgrData = new CreateGameResponseData(game.getLobbyID(),
					this.getCallingSession().getAuthToken());
			message = new Response(cgrData, 0);

		} else {
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.FAILED_TO_CREATE_GAME);
		}

		try {
			this.getCallingSession().sendMessage(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static CreateGameAction fromActionData(Session sender, ActionData actionData) {

		// This is not used here yet, but is here in case anything gets added later.
		CreateGameRequestData action = CreateGameRequestData.class.cast(actionData);

		return new CreateGameAction(sender);

	}

}
