package com.toozo.asteriumwebserver.actions;

import java.util.UUID;


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
	public static final Integer FAILED_TO_CREATE_GAME = 20;
	public static final Integer GAME_NOT_FOUND = 22;
	public static final Integer GAME_FULL = 30;
		
	/**
	 * Construct a {@link SendErrorAction}.
	 * 
	 * @param name the name of the {@link Action} that erred.
	 * @param callingSession the {@link Session} 
	 * @param errorCode the error code
	 * @param messageID the {@link Message}'s id 
	 */
	public SendErrorAction(final String name, final String authToken, final Integer errorCode, final UUID messageID) {
		super(name, authToken, errorCode, messageID);
	}

	@Override
	/**
	 * Not implemented yet. Do whatever needs to be done serverside to handle the error.
	 */
	protected void doAction() {
		// TODO Auto-generated method stub
	}

}