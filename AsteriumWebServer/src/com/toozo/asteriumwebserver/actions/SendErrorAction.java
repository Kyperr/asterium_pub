package com.toozo.asteriumwebserver.actions;

import java.util.UUID;

import message.Message;
import message.Response;

/**
 * A {@link ResponseAction} which represents that an error has occurred.
 * 
 * @author Studio Toozo
 */
public class SendErrorAction extends ResponseAction {

	public static final Integer MALFORMED_JSON = 7;
	public static final Integer ACTION_NOT_FOUND = 10;
	public static final Integer INCORRECT_ACTION_MAPPING = 11;
	public static final Integer EMPTY_FIELDS = 12;
	public static final Integer NO_SUCH_LOBBY = 13;
	public static final Integer NO_SUCH_ITEM_IN_PERSONAL_INVENTORY = 15;
	public static final Integer FAILED_TO_CREATE_GAME = 20;
	public static final Integer GAME_NOT_FOUND = 22;
	public static final Integer GAME_FULL = 30;

	/**
	 * Construct a {@link SendErrorAction}.
	 * 
	 * @param name
	 *            the name of the {@link Action} that erred.
	 * @param callingSession
	 *            the {@link Session}
	 * @param errorCode
	 *            the error code
	 * @param messageID
	 *            the {@link Message}'s id
	 */
	public SendErrorAction(final String name, final String authToken, final Integer errorCode, final UUID messageID) {
		super(name, authToken, errorCode, messageID);
	}

	@Override
	/**
	 * Not implemented yet. Do whatever needs to be done server-side to handle the
	 * error.
	 */
	protected void doAction() {
		// TODO Handle the error
		System.out.println("Error from " + this.getName());
	}

	/**
	 * Get a {@link SendErrorAction} based on actionData.
	 * 
	 * @param message
	 *            The {@link Message} containing the
	 *            {@link SendErrorAction}.
	 * @return a {@link SendErrorAction} containing the data from
	 *         message.
	 */
	public static SendErrorAction fromMessage(final Message message) {
		Response response = Response.class.cast(message);
		return new SendErrorAction(message.getActionData().getName(), message.getAuthToken(), response.getErrorCode(),
				message.getMessageID());

	}

}
