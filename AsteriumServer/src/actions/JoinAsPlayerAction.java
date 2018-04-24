package actions;

import java.util.Optional;

import actiondata.ActionData;
import actiondata.ErroredActionData;
import actiondata.JoinAsPlayerActionData;
import actiondata.JoinAsPlayerActionData.PlayerData;
import exceptions.GameFullException;
import gamelogic.Game;
import gamelogic.GameManager;
import gamelogic.Player;
import message.Message;
import sessionmanagement.SessionManager.Session;

public class JoinAsPlayerAction extends RequestAction {

	Optional<Game> game;
	Optional<String> lobby_id;
	Optional<PlayerData> playerData;
	
	public JoinAsPlayerAction(final Session callingSession, final String lobbyID, final PlayerData playerData) {
		super(Action.JOIN_AS_PLAYER, callingSession);
		this.lobby_id = Optional.of(lobbyID);
		this.playerData = Optional.of(playerData);
	}

	@Override
	protected void sendResponse() {
		Message message;
		if (this.lobby_id.isPresent() && this.playerData.isPresent()) {
			JoinAsPlayerActionData jpaData = new JoinAsPlayerActionData(this.lobby_id.get(), this.playerData.get());
			message = new Message(Message.MessageType.RESPONSE, jpaData);
		} else {
			ErroredActionData ead = new ErroredActionData(this.getName());
			message = new Message(Message.MessageType.RESPONSE, ead);
		}
	}

	@Override
	protected void doAction() {
		Game game;
		if (this.lobby_id.isPresent()) {
			game = GameManager.getInstance().getGame(this.lobby_id.get());
			try {
				PlayerData data = this.playerData.get();
				Player player = new Player(this.getCallingSession(), data.getName());
				game.addPlayer(player);
			} catch (final GameFullException ex) {
				
			}
		}
	}
	
	public static JoinAsPlayerAction fromActionData(Session sender, ActionData actionData) {
		JoinAsPlayerActionData action = JoinAsPlayerActionData.class.cast(actionData);
		return new JoinAsPlayerAction(sender, action.getLobbyID(), action.getPlayerData());
		
	}

}
