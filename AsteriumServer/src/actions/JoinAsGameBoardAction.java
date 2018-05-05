package actions;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import actiondata.JoinAsGameBoardRequestData;
import gamelogic.Game;
import gamelogic.GameBoard;
import gamelogic.GameManager;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

/**
 * An Action which associates a {@link GameBoard} to a {@link Game}.
 * 
 * @author Studio Toozo
 */
public class JoinAsGameBoardAction extends RequestAction {
	// The Game to which the GameBoard will be added.
	Optional<Game> game;
	
	// The lobby ID of game.
	Optional<String> lobby_id;
	
	// The GameBoard which is being added to game.
	Optional<JoinAsGameBoardRequestData.GameBoardData> gameBoardData;

	/**
	 * Construct a JoinAsGameBoardAction.
	 * 
	 * @param callingSession the session using this Action.
	 * @param lobbyID the ID of the lobby of the game to which the GameBoard should be added.
	 * @param gameBoardData the data of the GameBoard which should be added to the game.
	 */
	public JoinAsGameBoardAction(final Session callingSession, final String lobbyID, 
								 final JoinAsGameBoardRequestData.GameBoardData gameBoardData,
								 final UUID messageID) {
		super(Action.JOIN_AS_GAMEBOARD, callingSession, messageID);
		this.lobby_id = Optional.of(lobbyID);
		this.gameBoardData = Optional.of(gameBoardData);
	}

	@Override
	/**
	 * Constructs a GameBoard based on this.gameBoardData and adds it to the game.
	 */
	protected void doAction() {
		Game game;
		Message message;
		
		// If both fields exist...
		if (this.lobby_id.isPresent() && this.gameBoardData.isPresent()) {
			// Get the game that corresponds to lobby id.
			game = GameManager.getInstance().getGame(this.lobby_id.get());
			
			// Use to get data for GameBoard constructor.
			//JoinAsGameBoardRequestData.GameBoardData data = this.gameBoardData.get();
			
			// Construct the GameBoard.
			GameBoard gameBoard = new GameBoard(this.getCallingSession());
			
			// Add the game board to the game.
			game.addGameBoard(gameBoard);
			// Construct success response.
			JoinAsGameBoardRequestData jpaData = new JoinAsGameBoardRequestData(this.lobby_id.get(), this.gameBoardData.get());
			message = new Response(jpaData, 0, this.getMessageID());

			// Send the response back to the calling session.
			try {
				this.getCallingSession().sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else { // If one or more of the fields were not provided...
			// Create an error response.
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.EMPTY_FIELDS, this.getMessageID());
			
			// Try to send the error response
			try {
				this.getCallingSession().sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get a JoinAsGameBoardAction based on actionData.
	 * 
	 * @param sender The session using this JoinAsGameBoardAction.
	 * @param actionData The {@link ActionData} containing the JoinAsGameBoardAction.
	 * @return a JoinAsGameBoardAction containing the data from actionData.
	 */
	public static JoinAsGameBoardAction fromActionData(final Session sender, final Message message) {
		JoinAsGameBoardRequestData action = JoinAsGameBoardRequestData.class.cast(message.getActionData());
		return new JoinAsGameBoardAction(sender, action.getLobbyID(), action.getGameBoardData(), message.getMessageID());
	}

}
