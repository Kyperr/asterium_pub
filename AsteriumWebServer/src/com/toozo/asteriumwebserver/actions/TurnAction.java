package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.Player;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import actiondata.SuccessResponseData;
import actiondata.TurnRequestData;
import message.Message;
import message.Response;

/**
 * A {@link RequestAction} which adds a {@link Player}'s turn to 
 * the {@link Game}'s queue of turns to be processed.
 * 
 * @author Studio Toozo
 */
public class TurnAction extends RequestAction {

	private String locationID;
	private String activityName;

	public TurnAction(final String authToken, final UUID messageID, 
					  final String locationID, final String activityName) {
		super(Action.TURN, authToken, messageID);
		this.locationID = locationID;
		this.activityName = activityName;
	}

	@Override
	protected void doAction() {
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message = null;
		if (game != null) {
			GameState state = game.getGameState();
			Player player = game.getPlayer(auth);
			Location location = state.getLocation(this.locationID);
			String activityName = this.activityName;
			if (state != null) {
				// use character to search (uses their stats)
				PlayerCharacter character = state.getCharacter(auth);
				
				if (Action.VERBOSE) {
					System.out.printf("%s is performing activity %s in %s\n",
									  player.getPlayerName(),
									  activityName,
									  location.getName());
				}
				
				if (character != null) {
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							try {
								location.doActivity(activityName, game, character);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					game.addTurnAction(player, runnable);
					SuccessResponseData data = new SuccessResponseData(ActionData.TURN_ACTION);
					message = new Response(data, 0, this.getMessageID(), auth);
				}
			}
		} else {
			ErroredResponseData ead = new ErroredResponseData(this.getName());
			message = new Response(ead, SendErrorAction.GAME_NOT_FOUND, this.getMessageID(), auth);
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
	 * Get a {@link TurnAction} based on actionData.
	 * 
	 * @param message The {@link Message} containing the {@link TurnAction}.
	 * @return a {@link TurnAction} containing the data from message.
	 */
	public static TurnAction fromMessage(final Message message) {
		TurnRequestData action = TurnRequestData.class.cast(message.getActionData());
		return new TurnAction(message.getAuthToken(), message.getMessageID(), 
							  action.getLocationID(), 
							  action.getActivityName());

	}

}
