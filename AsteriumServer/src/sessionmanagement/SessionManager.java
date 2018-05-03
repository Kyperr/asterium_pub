package sessionmanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import message.Message;

/**
 * Creates a singleton that stores sessions and is used to acquire sessions.
 * 
 * @author Daniel
 *
 */
public final class SessionManager {

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
	 * Given a socket, this will create a {@link Session} and add it to the
	 * collection of sessions.
	 */
	public Session createSession(Socket socket) throws IOException {
		// Make a session
		Session session = new Session(socket);

		// Put it in the map
		sessions.put(session.getAuthToken(), session);

		return session;
	}

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

	/**
	 * An object that stores all relevant data about a client's connection
	 * 
	 * @author Daniel
	 *
	 */
	public static final class Session {

		private static final String CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		private static final SecureRandom RANDOM = new SecureRandom();

		private static final int TOKEN_LENGTH = 64;

		private final Socket socket;

		private final PrintWriter printWriter;

		/**
		 * A secure string of characters used to authenticate a client.
		 */
		private final String authToken;

		/**
		 * 
		 * Constructs a Session object. Generates an auth token and stores a reference
		 * to the socket's output stream.
		 * 
		 * @param socket - {@link Socket} 
		 * @throws IOException
		 */
		private Session(Socket socket) throws IOException {
			this.socket = socket;
			authToken = generateAuthToken();

			this.printWriter = new PrintWriter(socket.getOutputStream());
		}

		/**
		 * returns the socket.
		 * @return
		 */
		public Socket getSocket() {
			return socket;
		}

		/**
		 * Returns the authentication token for this session.
		 * @return
		 */
		public String getAuthToken() {
			return authToken;
		}

		public void sendMessage(Message message) throws IOException {
			System.out.println("Sending message: " + message.jsonify().toString());
			this.printWriter.println(message.jsonify().toString());
			this.printWriter.flush();
		}

		/**
		 * Generates an auth token for this session.
		 * @return
		 */
		private static String generateAuthToken() {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < TOKEN_LENGTH; i++) {
				sb.append(CHAR_SET.charAt(RANDOM.nextInt(CHAR_SET.length())));
			}

			return sb.toString();
		}
	}

}
