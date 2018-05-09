package actions;

import java.io.IOException;
import java.util.UUID;

import actiondata.ErroredResponseData;
import actiondata.JoinAsPlayerRequestData;
import exceptions.GameFullException;
import gamelogic.Game;
import gamelogic.GameManager;
import gamelogic.Player;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

/**
 * A {@link RequestAction} which adds a {@link Player} to a {@link Game}.
 * 
 * @author Studio Toozo
 */
public class JoinAsPlayerAction extends RequestAction {
	// The Game to which the Player will be added.
	Game game;

	// The lobby ID of game.
	String lobby_id;

	// The Player which is being added to game.
	JoinAsPlayerRequestData.PlayerData playerData;

	/**
	 * Construct a {@link JoinAsPlayerAction}.
	 * 
	 * @param callingSession the {@link Session} using this {@link Action}.
	 * @param lobbyID the ID of the lobby of the {@link Game} which the {@link Player} should be added to.
	 * @param playerData the data of the {@link Player} which should be added to the {@link Game}.
	 * @param messageID the {@link Message}'s id.
	 */
	public JoinAsPlayerAction(final Session callingSession, final String lobbyID, 
							  final JoinAsPlayerRequestData.PlayerData playerData, 
							  final UUID messageID) {
		super(Action.JOIN_AS_PLAYER, callingSession, messageID);
		this.lobby_id = lobbyID;
		this.playerData = playerData;
	}

	@Override
	/**
	 * Constructs a {@link Player} based on this.playerData and adds it to the {@link Game}.
	 */
	protected void doAction() {
		Game game;
		Message message;

		// Get the game that corresponds to lobby id.
		game = GameManager.getInstance().getGame(this.lobby_id);

		if (game == null) {
			sendError(SendErrorAction.NO_SUCH_LOBBY);
			return;
		}

		JoinAsPlayerRequestData.PlayerData data = this.playerData;

		// Construct the player.
		Player player = new Player(this.getCallingSession(), data.getName());

		// Try adding the player to the game.
		try {
			game.addPlayer(player);
			// Construct success response.
			JoinAsPlayerRequestData jpaData = new JoinAsPlayerRequestData(this.lobby_id, this.playerData);
			message = new Response(jpaData, 0, this.getMessageID());
		} catch (final GameFullException ex) { // If game is full...
			// Construct game full response.
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.GAME_FULL, this.getMessageID());
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
	 * Get a {@link JoinAsPlayerAction} based on actionData.
	 * 
	 * @param sender The {@link Session} using this {@link JoinAsPlayerAction}.
	 * @param message The {@link Message} containing the {@link JoinAsPlayerAction}.
	 * @return a {@link JoinAsPlayerAction} containing the data from message.
	 */
	public static JoinAsPlayerAction fromMessage(Session sender, final Message message) {
		JoinAsPlayerRequestData action = JoinAsPlayerRequestData.class.cast(message.getActionData());
		return new JoinAsPlayerAction(sender, action.getLobbyID(), action.getPlayerData(), message.getMessageID());

	}

}
