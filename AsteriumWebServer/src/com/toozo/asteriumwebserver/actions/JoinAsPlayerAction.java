package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.exceptions.GameFullException;
import com.toozo.asteriumwebserver.exceptions.InvalidNameException;
import com.toozo.asteriumwebserver.exceptions.PlayerNameTakenException;
import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.Player;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ErroredResponseData;
import actiondata.JoinAsPlayerRequestData;
import message.Message;
import message.Response;

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
	 * @param callingSession
	 *            the {@link Session} using this {@link Action}.
	 * @param lobbyID
	 *            the ID of the lobby of the {@link Game} which the {@link Player}
	 *            should be added to.
	 * @param playerData
	 *            the data of the {@link Player} which should be added to the
	 *            {@link Game}.
	 * @param messageID
	 *            the {@link Message}'s id.
	 */
	public JoinAsPlayerAction(final String lobbyID, final String authToken,
			final JoinAsPlayerRequestData.PlayerData playerData, final UUID messageID) {
		super(Action.JOIN_AS_PLAYER, authToken, messageID);
		this.lobby_id = lobbyID.toUpperCase();
		this.playerData = playerData;
	}

	@Override
	/**
	 * Constructs a {@link Player} based on this.playerData and adds it to the
	 * {@link Game}.
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
		Player player = new Player(this.getCallingAuthToken(), data.getName());

		// Try adding the player to the game.
		try {
			game.addPlayer(player);
			
			if (Action.VERBOSE) {
				System.out.printf("Player %s joined lobby %s\n",
								  player.getPlayerName(),
								  game.getLobbyID());
			}
			
			// Construct success response.
			JoinAsPlayerRequestData jpaData = new JoinAsPlayerRequestData(this.lobby_id, this.playerData);
			message = new Response(jpaData, 0, this.getMessageID(), this.getCallingAuthToken());
		} catch (final GameFullException ex) { // If game is full...
			// Construct game full error response.
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.GAME_FULL, this.getMessageID(), this.getCallingAuthToken());
		
		} catch (PlayerNameTakenException e) {
			// Construct name taken error response.
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.NAME_TAKEN, this.getMessageID(), this.getCallingAuthToken());
				
		} catch (InvalidNameException e) {
			// Construct invalid name error response.
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.INVALID_NAME, this.getMessageID(), this.getCallingAuthToken());
		}

		// Send the response back to the calling session.
		try {
			Session session = SessionManager.getInstance().getSession(getCallingAuthToken());
			synchronized (session) {
				session.getBasicRemote().sendText(message.jsonify().toString());
			}
		} catch (IOException e) {
			// Error cannot be sent, so display in console
			e.printStackTrace();
		}

	}

	/**
	 * Get a {@link JoinAsPlayerAction} based on actionData.
	 * 
	 * @param sender
	 *            The {@link Session} using this {@link JoinAsPlayerAction}.
	 * @param message
	 *            The {@link Message} containing the {@link JoinAsPlayerAction}.
	 * @return a {@link JoinAsPlayerAction} containing the data from message.
	 */
	public static JoinAsPlayerAction fromMessage(final Message message) {
		JoinAsPlayerRequestData action = JoinAsPlayerRequestData.class.cast(message.getActionData());
		return new JoinAsPlayerAction(action.getLobbyID(), message.getAuthToken(), action.getPlayerData(),
				message.getMessageID());

	}

}
