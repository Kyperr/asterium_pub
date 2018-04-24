package actions;

import sessionmanagement.SessionManager.Session;

public class SendErrorAction extends ResponseAction {

	public static final Integer ACTION_NOT_FOUND = 10;
	public static final Integer INCORRECT_ACTION_MAPPING = 11;
	public static final Integer EMPTY_FIELDS = 12;
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
	
	public SendErrorAction(Session callingSession, String erroredName, Integer errorCode) {
		super(erroredName, callingSession);
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub
		
	}

}
