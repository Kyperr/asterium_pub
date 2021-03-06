package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.websocket.Session;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.AbstractEquipmentItem;
import com.toozo.asteriumwebserver.gamelogic.items.equipment.Loadout;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ErroredResponseData;
import actiondata.SuccessResponseData;
import actiondata.UseItemRequestData;
import actiondata.InventoryData.ItemData;
import message.Message;
import message.Response;

/**
 * A {@link RequestAction} from a {@link PlayerCharacter} which uses an
 * {@link AbstractItem} from an {@link Inventory}.
 * 
 * @author Studio Toozo
 */
public class UseItemAction extends Action {
	private GameState state;
	private PlayerCharacter user;
	private Collection<PlayerCharacter> targets;
	private ItemData itemData;
	private boolean isCommunal;
	private boolean isEquipped;

	public UseItemAction(final String authToken, final UUID messageID, final Collection<String> targets,
			final ItemData item, final boolean isCommunal, final boolean isEquipped) {
		super(Action.USE_ITEM, authToken, messageID);
		this.state = GameManager.getInstance().getGameForPlayer(authToken).getGameState();
		this.user = this.state.getCharacter(authToken);
		this.targets = new ArrayList<PlayerCharacter>();
		for (String name : targets) {
			this.targets.add(this.state.getCharacterByName(name));
		}
		this.itemData = item;
		this.isCommunal = isCommunal;
		this.isEquipped = isEquipped;
	}

	@Override
	protected void doAction() {
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message = null;
		if (game != null) {
			GameState state = game.getGameState();
			PlayerCharacter user = this.user;
			Collection<PlayerCharacter> targets = this.targets;

			String item = this.itemData.getName();
			AbstractItem use = null;
			if (user != null && item != null) {
				// Get correct inventory
				if (!this.isEquipped) {
				Inventory inventory;
				if (this.isCommunal) {
					inventory = state.getCommunalInventory();
				} else {
					inventory = user.getInventory();
				}
				for (AbstractItem i : inventory) {
					if (i.getName().equals(item)) {
						use = i;
						break;
					}
				}
				} else {
					Loadout loadout = user.getEquipment();
					for (AbstractEquipmentItem equippedItem : loadout) {
						if (equippedItem.getName().equals(item)) {
							use = equippedItem;
						}
					}
				}
				
				if (use != null) {
					use.use(state, user, targets, this.isCommunal);
				}

				if (Action.VERBOSE) {
					String targetNames = "";
					boolean hasTargets = !targets.isEmpty();

					if (hasTargets) {
						// Add all but last target name
						String lastName = null;
						for (PlayerCharacter target : targets) {
							if (lastName != null) {
								targetNames += lastName + ", ";
							}
							lastName = target.getCharacterName();
						}

						// Add last target name
						if (targetNames != "") {
							targetNames += "and ";
						}
						targetNames += lastName;
					}

					System.out.printf("%s has used %s%s\n", user.getCharacterName(), item,
							hasTargets ? "" : " on " + targetNames);
				}

				SuccessResponseData data = new SuccessResponseData(Action.USE_ITEM);
				message = new Response(data, 0, this.getMessageID(), auth);

				// Update player clients
				if (this.isCommunal) {
					game.getGameState().syncPlayerClients();
				} else {
					game.getGameState().syncPlayerClient(auth);
				}

			} else {
				// No Such Item In Personal Inventory Error
				ErroredResponseData data = new ErroredResponseData(Action.USE_ITEM);
				message = new Response(data, SendErrorAction.NO_SUCH_ITEM_IN_PERSONAL_INVENTORY, this.getMessageID(),
						auth);
			}
		} else {
			ErroredResponseData data = new ErroredResponseData(Action.USE_ITEM);
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
	 * Get a {@link UseItemAction} based on actionData.
	 * 
	 * @param message
	 *            The {@link Message} containing the {@link UseItemAction}.
	 * @return a {@link UseItemAction} containing the data from message.
	 */
	public static UseItemAction fromMessage(final Message message) {
		UseItemRequestData data = UseItemRequestData.class.cast(message.getActionData());
		return new UseItemAction(message.getAuthToken(), message.getMessageID(), data.getTargets(), data.getItem(),
				data.getIsCommunal(), data.getIsEquipped());

	}

}
