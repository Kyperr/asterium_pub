package actions;

import java.util.Optional;

import actiondata.ActionData;
import actiondata.CreateGameActionData;
import actiondata.CreateGameResponseData;
import actiondata.ErroredActionData;
import gamelogic.Game;
import gamelogic.GameManager;
import message.Message;
import sessionmanagement.SessionManager.Session;

public class CreateGameAction extends RequestAction {
	
	private Optional<Game> game;
	
	public CreateGameAction(Session callingSession) {
		super(Action.CREATE_GAME, callingSession);
	}

	@Override
	protected void sendResponse() {
		Message message;
		if(this.game.isPresent()) {
			Game game = this.game.get();
			CreateGameResponseData cgrData = new CreateGameResponseData(game.getLobbyID(), this.getCallingSession().getAuthToken());
			message = new Message(Message.MessageType.RESPONSE, cgrData);
			
		} else {
			ErroredActionData ead = new ErroredActionData(this.getName());
			message = new Message(Message.MessageType.RESPONSE, ead);
		}

	}

	@Override
	protected void doAction() {
		Game game = GameManager.getInstance().createGame();
		this.game = Optional.of(game);
	}
	
	public static CreateGameAction fromActionData(Session sender, ActionData actionData) {

		//This is not used here yet, but is here in case anything gets added later.
		CreateGameActionData action = CreateGameActionData.class.cast(actionData);

		return new CreateGameAction(sender);
		
	}

}
