package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.CommunalInventoryRequestData;
import actiondata.InventoryData.ItemData;
import actiondata.ErroredResponseData;
import actiondata.SuccessResponseData;
import message.Message;
import message.Response;

/**
 * A {@link RequestAction} to move an {@link AbstractItem} to the communal inventory
 * from a {@link PlayerCharacter}'s personal inventory, or to move it from the communal
 * inventory to their personal inventory.
 * 
 * @author Studio Toozo
 */
public class CommunalInventoryAction extends Action {
	public static final String TAKE_MESSAGE = "You took %s from the communal inventory.";
	public static final String GIVE_MESSAGE = "You put %s into the communal inventory.";
	public static final String PUBLIC_MESSAGE = "A %s was removed from the communal inventory";
	
	private GameState state;
	private PlayerCharacter owner;
	private ItemData itemData;
	private boolean personalToCommunal;

	public CommunalInventoryAction(final String authToken, final UUID messageID, 
								   final ItemData item, final boolean personalToCommunal) {
		super(Action.COMMUNAL_INVENTORY, authToken, messageID);
		this.state = GameManager.getInstance().getGameForPlayer(authToken).getGameState();
		this.owner = this.state.getCharacter(authToken);
		this.itemData = item;
		this.personalToCommunal = personalToCommunal;
	}

	@Override
	protected void doAction() {
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message = null;
		if (game != null) {
			GameState state = game.getGameState();
			PlayerCharacter owner = this.owner;
			boolean success = false;
			Inventory ownerInventory = owner.getInventory();
			Inventory communalInventory = state.getCommunalInventory();
			String nameToMatch = this.itemData.getName();
			AbstractItem matchingItem = null;
			
			if (this.personalToCommunal) {
				// Check ownerInventory
				for (AbstractItem item : ownerInventory) {
					if (item.getName().equals(nameToMatch)) {
						matchingItem = item;
						break;
					}
				}
				
				if (matchingItem != null) {
					// Item found
					ownerInventory.remove(matchingItem);
					communalInventory.add(matchingItem);
					if (Action.VERBOSE) {
						System.out.printf("%s put %s into the communal inventory.\n", 
										  owner.getCharacterName(), nameToMatch);
					}
					owner.addSummaryMessage(String.format(GIVE_MESSAGE, nameToMatch));
					success = true;
				} else {
					// Item not found, construct error response
					ErroredResponseData data = new ErroredResponseData(Action.COMMUNAL_INVENTORY);
					message = new Response(data, SendErrorAction.NO_SUCH_ITEM_IN_PERSONAL_INVENTORY, this.getMessageID(),
							auth);
				}
			} else {
				// Check communalInventory
				for (AbstractItem item : communalInventory) {
					if (item.getName().equals(nameToMatch)) {
						matchingItem = item;
						break;
					}
				}
				
				if (matchingItem != null) {
					// Item found
					communalInventory.remove(matchingItem);
					ownerInventory.add(matchingItem);					
					if (Action.VERBOSE) {
						System.out.printf("%s took %s out of the communal inventory.\n", 
										  owner.getCharacterName(), nameToMatch);
					}
					owner.addSummaryMessage(String.format(TAKE_MESSAGE, nameToMatch));
					success = true;
				} else {
					// Item not found, construct error respond
					ErroredResponseData data = new ErroredResponseData(Action.COMMUNAL_INVENTORY);
					message = new Response(data, SendErrorAction.NO_SUCH_ITEM_IN_COMMUNAL_INVENTORY, 
										   this.getMessageID(), auth);
				}
			}
			
			if (success) {
				state.addSummaryMessage(String.format(PUBLIC_MESSAGE, nameToMatch));
				state.syncPlayerClients();
				SuccessResponseData data = new SuccessResponseData(Action.COMMUNAL_INVENTORY);
				message = new Response(data, 0, this.getMessageID(), auth);
			}
		} else {
			ErroredResponseData data = new ErroredResponseData(Action.COMMUNAL_INVENTORY);
			message = new Response(data, SendErrorAction.GAME_NOT_FOUND, this.getMessageID(), auth);
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
	 * Get a {@link CommunalInventoryAction} based on actionData.
	 * 
	 * @param message
	 *            The {@link Message} containing the {@link CommunalInventoryAction}.
	 * @return a {@link CommunalInventoryAction} containing the data from message.
	 */
	public static CommunalInventoryAction fromMessage(final Message message) {
		CommunalInventoryRequestData data = CommunalInventoryRequestData.class.cast(message.getActionData());
		return new CommunalInventoryAction(message.getAuthToken(), message.getMessageID(),
				data.getItem(), data.personalToCommunal());

	}

}
