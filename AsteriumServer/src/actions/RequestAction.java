package actions;

import sessionmanagement.SessionManager.Session;

public abstract class RequestAction extends Action {

	public RequestAction(String name, Session callingSession) {
		super(name, callingSession);
	}

	@Override
	public final void run() {
		doAction();
		sendResponse();
	}
	
	protected abstract void sendResponse();
	

}
