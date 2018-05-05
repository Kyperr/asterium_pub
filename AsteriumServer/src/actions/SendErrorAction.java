package actions;

import java.util.UUID;

import sessionmanagement.SessionManager.Session;

/**
 * A ResponseAction which represents that an error has occurred.
 * 
 * @author Studio Toozo
 */
public class SendErrorAction extends ResponseAction {

	public static final Integer ACTION_NOT_FOUND = 10;
	public static final Integer INCORRECT_ACTION_MAPPING = 11;
	public static final Integer EMPTY_FIELDS = 12;
	public static final Integer NO_SUCH_LOBBY = 13;
	public static final Integer FAILED_TO_CREATE_GAME = 20;
	public static final Integer GAME_FULL = 30;
	
	/**
	 * 
	 * 
	 * THIS CLASS IS NOT IMPLEMENTED YET!!!!
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * Construct a SendErrorAction.
	 * @param callingSession The session which caused this error.
	 * @param erroredName The name of the error which occurred.
	 * @param errorCode The code of the error which occurred.
	 */
	public SendErrorAction(final String name, final Session callingSession, final Integer errorCode, final UUID messageID) {
		super(name, callingSession, errorCode, messageID);
	}

	@Override
	/**
	 * Not implemented yet. Do whatever needs to be done serverside to handle the error.
	 */
	protected void doAction() {
		// TODO Auto-generated method stub
	}

}
