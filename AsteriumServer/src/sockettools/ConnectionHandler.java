package sockettools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import actiondata.ActionData;
import actions.Action;
import main.Parser;
import message.Message;
import sessionmanagement.SessionManager;
import sessionmanagement.SessionManager.Session;

/**
 * This object maintains a single connection with a client and listens for input
 * from the client. Any input is budded into a separate handling thread.
 *
 */
public class ConnectionHandler extends Thread {

	private static final int SOCKET_HANDLER_TIMEOUT = 60000;

	private static ExecutorService threadPoolExec = Executors.newCachedThreadPool();

	/*
	 * The Session for this ConnectionHandler thread. Has the associated socket and
	 * authentication token.
	 */
	private final Session session;

	private final Parser parser = new Parser();

	/*
	 * Run condition: true while thread should be alive, false when it should be
	 * killed.
	 */
	private boolean run = false;

	/**
	 * Constructs a {@link ConnectionHandler}. This also creates a full
	 * {@link Session} object for this connection.
	 * 
	 * @param socket
	 *            - {@link Socket}
	 * @throws IOException
	 *             - The internet probably dropped.
	 */
	public ConnectionHandler(Socket socket) throws IOException {
		this.session = SessionManager.getInstance().createSession(socket);
	}

	/**
	 * This runs, listens for input and buds the action into a new thread.
	 */
	public void run() {
		// We're running
		run = true;

		/*
		 * Using this ConnectionHandler thread's associated socket, set the timeout and
		 * create a new input stream. Read input from the stream, and call the parser to
		 * execute requests and responses.
		 */
		try {
			this.session.getSocket().setSoTimeout(SOCKET_HANDLER_TIMEOUT);
			InputStreamReader isr = new InputStreamReader(this.session.getSocket().getInputStream());
			BufferedReader br = new BufferedReader(isr);

			String line = "";
			while (run) {// is this needed?
				while (br.ready()) {

					line = br.readLine();

					handleMessage(line);
				}
			}

			/*
			 * If there has been a timeout, the user has been disconnected. Kill this
			 * thread.
			 */
		} catch (SocketTimeoutException e) {
			run = false;
			interrupt();

		} catch (IOException e) {
			// This probably need something additional.
			System.err.println("Did the internet drop?");
			e.printStackTrace();
		}
	}

	/**
	 * Called just in here. Just for decomposing the run() method. This actually
	 * executes the new thread.
	 * 
	 * @param messageString
	 *            - {@link String}
	 */
	private void handleMessage(String messageString) {

		Message message = this.parser.parse(messageString);

		ActionData actionData = message.getActionData();

		Action action = Action.getActionFor(this.session, actionData);

		threadPoolExec.execute(action);
	}

}
