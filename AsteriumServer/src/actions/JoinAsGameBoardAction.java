package actions;

import java.io.IOException;
import java.util.UUID;

import actiondata.JoinAsGameBoardRequestData;
import actiondata.JoinAsGameBoardResponseData;
import gamelogic.Game;
import gamelogic.GameBoard;
import gamelogic.GameManager;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

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
	public JoinAsGameBoardAction(final Session callingSession, final String lobbyID,
			final JoinAsGameBoardRequestData.GameBoardData gameBoardData, final UUID messageID) {
		super(Action.JOIN_AS_GAMEBOARD, callingSession, messageID);
		this.lobby_id = lobbyID;
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
		GameBoard gameBoard = new GameBoard(this.getCallingSession());

		// Add the game board to the game.
		game.addGameBoard(gameBoard);

		// Construct success response.
		JoinAsGameBoardResponseData jpaData = new JoinAsGameBoardResponseData(getCallingSession().getAuthToken());
		message = new Response(jpaData, 0, this.getMessageID(), this.getCallingSession().getAuthToken());

		// Send the response back to the calling session.
		try {
			this.getCallingSession().sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get a {@link JoinAsGameBoardAction} based on a message.
	 * 
	 * @param sender The {@link Session} using this {@link JoinAsGameBoardAction}.
	 * @param message The {@link Message} containing the {@link JoinAsGameBoardAction}.
	 * @return a {@link JoinAsGameBoardAction} containing the data from message.
	 */
	public static JoinAsGameBoardAction fromMessage(final Session sender, final Message message) {
		JoinAsGameBoardRequestData action = JoinAsGameBoardRequestData.class.cast(message.getActionData());
		return new JoinAsGameBoardAction(sender, action.getLobbyID(), action.getGameBoardData(),
				message.getMessageID());
	}

}
