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

import actiondata.DiscardItemRequestData;
import actiondata.ErroredResponseData;
import actiondata.InventoryData.ItemData;
import actiondata.SuccessResponseData;
import actiondata.UseItemRequestData;
import message.Message;
import message.Response;

/**
 * A {@link RequestAction} from a {@link PlayerCharacter} which uses an
 * {@link AbstractItem} from an {@link Inventory}.
 * 
 * @author Studio Toozo
 */
public class DiscardItemAction extends Action {
	public static final String MESSAGE = "You discarded %s.";
	private GameState state;
	private PlayerCharacter user;
	private ItemData itemData;

	public DiscardItemAction(final String authToken, final UUID messageID, final ItemData item) {
		super(Action.DISCARD_ITEM, authToken, messageID);
		this.state = GameManager.getInstance().getGameForPlayer(authToken).getGameState();
		this.user = this.state.getCharacter(authToken);
		this.itemData = item;
	}

	@Override
	protected void doAction() {
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message = null;
		if (game != null) {
			PlayerCharacter user = this.user;

			String item = this.itemData.getName();
			AbstractItem use = null;
			if (user != null && item != null) {
				for (AbstractItem i : user.getInventory()) {
					if (i.getName().equals(item)) {
						use = i;
						break;
					}
				}
				if (use != null) {
					use.discard(user);
					user.addSummaryMessage(String.format(MESSAGE, item));
					System.out.printf("%s has discarded %s.\n", user.getCharacterName(), item);

					SuccessResponseData data = new SuccessResponseData(Action.DISCARD_ITEM);
					message = new Response(data, 0, this.getMessageID(), auth);

					game.getGameState().syncPlayerClient(auth);
				} else {
					// No Such Item In Personal Inventory Error
					ErroredResponseData data = new ErroredResponseData(Action.DISCARD_ITEM);
					message = new Response(data, SendErrorAction.NO_SUCH_ITEM_IN_PERSONAL_INVENTORY,
							this.getMessageID(), auth);
				}
			}
		} else {
			ErroredResponseData data = new ErroredResponseData(Action.DISCARD_ITEM);
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
	 * Get a {@link DiscardItemAction} based on actionData.
	 * 
	 * @param message
	 *            The {@link Message} containing the {@link DiscardItemAction}.
	 * @return a {@link DiscardItemAction} containing the data from message.
	 */
	public static DiscardItemAction fromMessage(final Message message) {
		DiscardItemRequestData data = DiscardItemRequestData.class.cast(message.getActionData());
		return new DiscardItemAction(message.getAuthToken(), message.getMessageID(), data.getItem());

	}

}
