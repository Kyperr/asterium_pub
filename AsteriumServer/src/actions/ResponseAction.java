package actions;

import sessionmanagement.SessionManager.Session;

public abstract class ResponseAction extends Action {

	public ResponseAction(String name, Session callingSession) {
		super(name, callingSession);
	}

	@Override
	public final void run() {
		super.run();
	}

}
