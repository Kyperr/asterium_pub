package gamelogic;

import sessionmanagement.SessionManager.Session;

/**
 * {@link Client} class which allows the server to keep track of {@link Client} that are part of a {@link Game}.  
 * 
 * @author Daniel McBride, Jenna Hand, Bridgette Campbell, Greg Schmitt
 */
public class Client {

	/*
	 * The session/connection for the client.
	 */
	private Session session;

	/**
	 * Creates and returns a {@link Client} object. Assigns the given {@link Session} to the client.
	 * @param session The session to assign to the Client.
	 */
	protected Client(Session session) {
		this.session = session;
	}
	
	/**
	 * @return The {@link Client}'s current Session.
	 */
	public Session getSession() {
		return this.session;
	}
	
	/**
	 * @param session The new {@link Session} to assign to the Client.
	 */
	public void setSession(final Session session) {
		this.session = session;
	}

}
