package com.toozo.asteriumwebserver.sessionmanager;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

/**
 * Creates a singleton that stores sessions and is used to acquire {@link Session}s.
 * 
 * @author Studio Toozo
 *
 */
public final class SessionManager {

	private static final String CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final SecureRandom RANDOM = new SecureRandom();

	private static final int TOKEN_LENGTH = 64;

	// The instance.
	private static SessionManager sessionManager;

	/**
	 * Returns the single instance of the {@link SessionManager}
	 * 
	 * @return
	 */
	public static synchronized SessionManager getInstance() {
		if (sessionManager == null) {
			sessionManager = new SessionManager();
		}
		return sessionManager;
	}

	/**
	 * Needs to be private.
	 */
	private SessionManager() {

	}

	private Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

	/**
	 * Will look up a session with the given authToken and return the
	 * {@link Session} object.
	 * 
	 * @param authToken
	 *            - {@link String}
	 * @return {@link Session}
	 */
	public Session getSession(String authToken) {
		return sessions.get(authToken);
	}

	public void remapSession(String authToken, Session session) {
		this.sessions.put(authToken, session);
	}
	
	public String registerNewSession(Session session) {
		String authToken = generateAuthToken();
		sessions.put(authToken, session);
		return authToken;
	}
	
	public void removeSession(String authToken) {
		sessions.remove(authToken);
	}

	/**
	 * Generates an auth token for this session.
	 * @return
	 */
	public static String generateAuthToken() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < TOKEN_LENGTH; i++) {
			sb.append(CHAR_SET.charAt(RANDOM.nextInt(CHAR_SET.length())));
		}

		return sb.toString();
	}
	
	
	
}
