package actions;

import java.io.IOException;
import java.util.Optional;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import actiondata.JoinAsPlayerRequestData;
import actiondata.JoinAsPlayerRequestData.PlayerData;
import exceptions.GameFullException;
import gamelogic.Game;
import gamelogic.GameManager;
import gamelogic.Player;
import message.Message;
import message.Response;
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
	protected void doAction() {
		Game game;
		Message message;
		if (this.lobby_id.isPresent() && this.playerData.isPresent()) {
			game = GameManager.getInstance().getGame(this.lobby_id.get());
			PlayerData data = this.playerData.get();
			Player player = new Player(this.getCallingSession(), data.getName());
			try {
				game.addPlayer(player);
				JoinAsPlayerRequestData jpaData = new JoinAsPlayerRequestData(this.lobby_id.get(), this.playerData.get());
				message = new Response(jpaData, 0);
			} catch (final GameFullException ex) {
				ErroredResponseData ead = new ErroredResponseData(this.getName());
				message = new Response(ead, SendErrorAction.GAME_FULL);
			}

			try {
				this.getCallingSession().sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.EMPTY_FIELDS);
			try {
				this.getCallingSession().sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static JoinAsPlayerAction fromActionData(Session sender, ActionData actionData) {
		JoinAsPlayerRequestData action = JoinAsPlayerRequestData.class.cast(actionData);
		return new JoinAsPlayerAction(sender, action.getLobbyID(), action.getPlayerData());

	}

}
