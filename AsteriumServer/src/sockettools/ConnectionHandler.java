package sockettools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

import actiondata.ActionData;
import actions.Action;
import main.Parser;
import message.Message;
import sessionmanagement.SessionManager;
import sessionmanagement.SessionManager.Session;

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

	public ConnectionHandler(Socket socket) throws IOException {
		this.session = SessionManager.getInstance().createSession(socket);
		// System.out.println("auth: " + session.getAuthToken());
		System.out.println("ConnectionHandler created");
	}

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
			while (run) {//is this needed?
				while (br.ready()) {
					System.out.println("Listening...");

					line = br.readLine();

					System.out.println("Connected: " + this.session.getSocket().isConnected());

					System.out.println("Budding thread to handle: " + line);

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
			e.printStackTrace();
			// !!! What circumstances will this crash under?
			// How should the program react to each circumstance?
		}
	}

	public void handleMessage(String messageString) {

		Message message = this.parser.parse(messageString);

		ActionData actionData = message.getActionData();

		Action action = Action.getActionFor(this.session, actionData);

		System.out.println("Handling a thread for: " + action.getName());
		threadPoolExec.execute(action);
	}

	public void thing(Function<String, String> func) {
	}

}
