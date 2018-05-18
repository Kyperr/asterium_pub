package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import com.toozo.asteriumwebserver.gamelogic.Game;
import com.toozo.asteriumwebserver.gamelogic.GameManager;
import com.toozo.asteriumwebserver.gamelogic.GameState;
import com.toozo.asteriumwebserver.gamelogic.Inventory;
import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.Item;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ErroredResponseData;
import actiondata.SuccessResponseData;
import actiondata.UsePersonalItemRequestData;
import actiondata.UsePersonalItemRequestData.ItemData;
import actiondata.UsePersonalItemRequestData.PlayerCharacterData;
import message.Message;
import message.Response;

/**
 * A {@link RequestAction} which uses an {@link Item} from a 
 * {@link PlayerCharacter}'s personal {@link Inventory}. 
 * @author Studio Toozo
 */
public class UsePersonalItemAction extends Action {

	private PlayerCharacterData userData;
	private Collection<PlayerCharacterData> targetsData;
	private ItemData itemData;

	public UsePersonalItemAction(final String authToken, final UUID messageID, final PlayerCharacterData user,
			final Collection<PlayerCharacterData> targets, final ItemData item) {
		super(Action.USE_PERSONAL_ITEM, authToken, messageID);
		this.userData = user;
		this.targetsData = targets;
		this.itemData = item;
	}

	@Override
	protected void doAction() {
		String auth = getCallingAuthToken();
		Game game = GameManager.getInstance().getGameForPlayer(auth);
		Message message = null;
		if (game != null) {
			GameState state = game.getGameState();
			PlayerCharacter user = state.getCharacter(userData.getAuthToken());
			Collection<PlayerCharacter> targets = new HashSet<PlayerCharacter>();

			for (PlayerCharacterData targetData : targetsData) {
				PlayerCharacter target = state.getCharacter(targetData.getAuthToken());
				targets.add(target);
			}

			Item item = Item.getItem(itemData.getItemID());
			if (user != null && item != null && user.getInventory().contains(item)) {
				item.use(state, user, targets, false);
				SuccessResponseData data = new SuccessResponseData(Action.USE_PERSONAL_ITEM);
				message = new Response(data, 0, this.getMessageID(), auth);
			} else {
				// No Such Item In Personal Inventory Error
				ErroredResponseData data = new ErroredResponseData(Action.USE_PERSONAL_ITEM);
				message = new Response(data, SendErrorAction.NO_SUCH_ITEM_IN_PERSONAL_INVENTORY, this.getMessageID(),
						auth);
			}
		} else {
			ErroredResponseData data = new ErroredResponseData(Action.USE_PERSONAL_ITEM);
			message = new Response(data, SendErrorAction.GAME_NOT_FOUND, this.getMessageID(), auth);
		}
		// Send the response back to the calling session.
		try {
			SessionManager.getInstance().getSession(getCallingAuthToken()).getBasicRemote()
					.sendText(message.jsonify().toString());
		} catch (IOException e) {
			// Error cannot be sent, so display in console
			e.printStackTrace();
		}
	}

	/**
	 * Get a {@link UsePersonalItemAction} based on actionData.
	 * 
	 * @param message
	 *            The {@link Message} containing the {@link UsePersonalItemAction}.
	 * @return a {@link UsePersonalItemAction} containing the data from message.
	 */
	public static UsePersonalItemAction fromMessage(final Message message) {
		UsePersonalItemRequestData data = UsePersonalItemRequestData.class.cast(message.getActionData());
		return new UsePersonalItemAction(message.getAuthToken(), message.getMessageID(), data.getUser(),
				data.getTargets(), data.getItem());

	}

}
