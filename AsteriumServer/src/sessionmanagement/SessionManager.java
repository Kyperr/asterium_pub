package sessionmanagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import message.Message;

public final class SessionManager {

	/**
	 * 
	 */
	private static SessionManager sessionManager;

	/**
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
	 * 
	 */
	private SessionManager() {

	}

	private Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

	public Session createSession(Socket socket) throws IOException {
		// Make a session
		Session session = new Session(socket);

		// Put it in the map
		sessions.put(session.getAuthToken(), session);

		return session;
	}

	public Session getSession(String authToken) {
		return sessions.get(authToken);
	}

	public static final class Session {

		private static final String CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		private static final SecureRandom RANDOM = new SecureRandom();

		private static final int TOKEN_LENGTH = 64;

		/**
		 * 
		 */
		private final Socket socket;

		
		private final PrintWriter printWriter;
		
		/**
		 * 
		 */
		private final String authToken;

		/**
		 * 
		 * @param socket
		 * @throws IOException 
		 */
		private Session(Socket socket) throws IOException {
			this.socket = socket;
			authToken = generateAuthToken();
			
			this.printWriter = new PrintWriter(socket.getOutputStream());
		}

		public Socket getSocket() {
			return socket;
		}

		public String getAuthToken() {
			return authToken;
		}

		public void sendMessage(Message message) throws IOException {
			System.out.println("Sending message.");
			this.printWriter.println(message.jsonify().toString());
			this.printWriter.flush();
		}

		/**
		 * 
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
