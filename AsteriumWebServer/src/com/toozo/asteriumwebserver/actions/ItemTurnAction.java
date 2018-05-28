package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Location;
import com.toozo.asteriumwebserver.gamelogic.Player;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import actiondata.ItemTurnRequestData;
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
public class ItemTurnAction extends RequestAction {

	private String locationID;
	private String itemName;

	public ItemTurnAction(final String authToken, final UUID messageID, 
					  final String locationID, final String itemName) {
		super(Action.ITEM_TURN, authToken, messageID);
		this.locationID = locationID;
		this.itemName = itemName;
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
			String itemName = this.itemName;
			
			
			/**
			 * TODO: No error handling here? For shame.
			 */
			
			if (state != null) {
				// use character to search (uses their stats)
				PlayerCharacter character = state.getCharacter(auth);
				
				if (Action.VERBOSE) {
					System.out.printf("%s is performing activity %s in %s\n",
									  player.getPlayerName(),
									  itemName,
									  location.getName());
				}
				
				if (character != null) {
					
					
					/**
					 * Check if inv has item. Check if item's list of locations contains the location specified. If so, use it.
					 */
					
					boolean isPresent = false;
					for(AbstractItem item : character.getInventory()) {
						if(item.getName().equals(itemName)) {

							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									try {
										Collection<PlayerCharacter> col = new ArrayList<>();
										col.add(character);
										item.use(game.getGameState(), character, col, false);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							};
							game.addTurnAction(player, runnable);							
							
							
							isPresent = true;
							break;
						}
					}
					
					SuccessResponseData data = new SuccessResponseData(ActionData.ITEM_TURN_ACTION);
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
	 * Get a {@link ItemTurnAction} based on actionData.
	 * 
	 * @param message The {@link Message} containing the {@link ItemTurnAction}.
	 * @return a {@link ItemTurnAction} containing the data from message.
	 */
	public static ItemTurnAction fromMessage(final Message message) {
		ItemTurnRequestData action = ItemTurnRequestData.class.cast(message.getActionData());
		return new ItemTurnAction(message.getAuthToken(), message.getMessageID(), 
							  action.getLocationID(), 
							  action.getActivityName());

	}

}
