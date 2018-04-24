package actions;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONObject;

import actiondata.ActionData;
import actiondata.CreateGameActionData;
import actiondata.CreateGameResponseData;
import actiondata.ErroredActionData;
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
	}

	@Override
	protected void sendResponse() {
		Message message;
		if(this.game.isPresent()) {
			Game game = this.game.get();
			
			CreateGameResponseData cgrData = new CreateGameResponseData(game.getLobbyID(), this.getCallingSession().getAuthToken());
			
			message = new Response(cgrData, 0);
			
			
		} else {
			ErroredActionData ead = new ErroredActionData(this.getName());
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

		//This is not used here yet, but is here incase anything gets added later.
		CreateGameAction cgAction = CreateGameAction.class.cast(actionData);

		return new CreateGameAction(sender);
		
	}

}
