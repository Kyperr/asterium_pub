package gamelogic;

import sessionmanagement.SessionManager.Session;

public class Client {

	private Session session;

	protected Client(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public void setSession(final Session session) {
		this.session = session;
	}

}
