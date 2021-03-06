package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameBoard;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.JoinAsGameBoardRequestData;
import actiondata.JoinAsGameBoardResponseData;
import message.Message;
import message.Response;

/**
 * A {@link RequestAction} which associates a {@link GameBoard} to a {@link Game}.
 * 
 * @author Studio Toozo
 */
public class JoinAsGameBoardAction extends RequestAction {
	// The Game to which the GameBoard will be added.
	Game game;

	// The lobby ID of game.
	String lobby_id;

	// The GameBoard which is being added to game.
	JoinAsGameBoardRequestData.GameBoardData gameBoardData;

	/**
	 * Construct a {@link JoinAsGameBoardAction}.
	 * 
	 * @param callingSession the session using this {@link Action}.
	 * @param lobbyID the ID of the lobby of the game to which the{@link GameBoard} should be added.
	 * @param gameBoardData the data of the {@link GameBoard} which should be added to the game.
	 */
	public JoinAsGameBoardAction(final String authToken, final String lobbyID,
			final JoinAsGameBoardRequestData.GameBoardData gameBoardData, final UUID messageID) {
		super(Action.JOIN_AS_GAMEBOARD, authToken, messageID);
		this.lobby_id = lobbyID.toUpperCase();
		this.gameBoardData = gameBoardData;
	}

	@Override
	/**
	 * Constructs a {@link GameBoard} based on this.gameBoardData and adds it to the game.
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

		
		// Construct the GameBoard.
		GameBoard gameBoard = new GameBoard(this.getCallingAuthToken());

		// Add the game board to the game.
		game.addGameBoard(gameBoard);

		if (Action.VERBOSE) {
			System.out.printf("New game board joined lobby %s\n", game.getLobbyID());
		}
		
		// Construct success response.
		JoinAsGameBoardResponseData jpaData = new JoinAsGameBoardResponseData(this.getCallingAuthToken());
		message = new Response(jpaData, 0, this.getMessageID(), this.getCallingAuthToken());

		// Send the response back to the calling session.
		try {
			Session session = SessionManager.getInstance().getSession(getCallingAuthToken());
			synchronized (session) {
				session.getBasicRemote().sendText(message.jsonify().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get a {@link JoinAsGameBoardAction} based on a message.
	 * 
	 * @param message The {@link Message} containing the {@link JoinAsGameBoardAction}.
	 * @return a {@link JoinAsGameBoardAction} containing the data from message.
	 */
	public static JoinAsGameBoardAction fromMessage(final Message message) {
		JoinAsGameBoardRequestData action = JoinAsGameBoardRequestData.class.cast(message.getActionData());
		return new JoinAsGameBoardAction(message.getAuthToken(), action.getLobbyID(), action.getGameBoardData(),
				message.getMessageID());
	}

}
