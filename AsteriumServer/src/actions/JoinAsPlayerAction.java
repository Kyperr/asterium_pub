package actions;

import java.io.IOException;
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
import message.Response;
import sessionmanagement.SessionManager.Session;

/**
 * An Action which adds a Player to a Game.
 * 
 * @author Studio Toozo
 */
public class JoinAsPlayerAction extends RequestAction {
	// The Game to which the Player will be added.
	Optional<Game> game;
	
	// The lobby ID of game.
	Optional<String> lobby_id;
	
	// The Player which is being added to game.
	Optional<PlayerData> playerData;

	/**
	 * Construct a JoinAsPlayerAction.
	 * 
	 * @param callingSession the session using this Action.
	 * @param lobbyID the ID of the lobby of the game which the player should be added to.
	 * @param playerData the data of the player which should be added to the game.
	 */
	public JoinAsPlayerAction(final Session callingSession, final String lobbyID, final PlayerData playerData) {
		super(Action.JOIN_AS_PLAYER, callingSession);
		this.lobby_id = Optional.of(lobbyID);
		this.playerData = Optional.of(playerData);
	}

	@Override
	/**
	 * Constructs a Player based on this.playerData and adds it to the game.
	 */
	protected void doAction() {
		Game game;
		Message message;
		
		// If both fields exist...
		if (this.lobby_id.isPresent() && this.playerData.isPresent()) {
			// Get the game that corresponds to lobby id.
			game = GameManager.getInstance().getGame(this.lobby_id.get());
			PlayerData data = this.playerData.get();
			
			// Construct the player.
			Player player = new Player(this.getCallingSession(), data.getName());
			
			// Try adding the player to the game.
			try {
				game.addPlayer(player);
				// Construct success response.
				JoinAsPlayerActionData jpaData = new JoinAsPlayerActionData(this.lobby_id.get(), this.playerData.get());
				message = new Response(jpaData, 0);
			} catch (final GameFullException ex) { // If game is full...
				// Construct game full response.
				ErroredActionData ead = new ErroredActionData(this.getName());
				message = new Response(ead, SendErrorAction.GAME_FULL);
			}

			// Send the response back to the calling session.
			try {
				this.getCallingSession().sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else { // If one or more of the fields were not provided...
			// Create an error response.
			ErroredActionData ead = new ErroredActionData(this.getName());
			message = new Response(ead, SendErrorAction.EMPTY_FIELDS);
			
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
	 * Get a JoinAsPlayerAction based on actionData.
	 * 
	 * @param sender The session using this JoinAsPlayerAction.
	 * @param actionData The {@link ActionData} containing the JoinAsPlayerAction.
	 * @return a JoinAsPlayerAction containing the data from actionData.
	 */
	public static JoinAsPlayerAction fromActionData(Session sender, ActionData actionData) {
		JoinAsPlayerActionData action = JoinAsPlayerActionData.class.cast(actionData);
		return new JoinAsPlayerAction(sender, action.getLobbyID(), action.getPlayerData());

	}

}
