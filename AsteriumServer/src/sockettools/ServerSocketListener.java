package sockettools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This object listens for new connections and buds a new thread to handle that
 * specific connection.
 * 
 */
public class ServerSocketListener {

	private final int port;

	/* Condition: is the server loop running? */
	private boolean run = false;

	/* Listener for connections. */
	private ServerSocket listener;

	/**
	 * Constructs a ServerSocketListener to listen on the port specified.
	 * 
	 * @param port
	 *            - {@link Integer}
	 * @throws IOException
	 */
	public ServerSocketListener(int port) throws IOException {
		this.port = port;
		listener = new ServerSocket(port);
	}

	/**
	 * Starts listening.
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		// We're listening
		run = true;
		listen();
	}

	/**
	 * Stops listening.
	 */
	public void stop() {
		// We're DONE
		run = false;
	}

	/**
	 * While it hasn't been stopped, this will listen for a new connection and
	 * create a new {@link ConnectionHandler} to handle that specified connection.
	 * 
	 * @throws IOException
	 */
	private void listen() throws IOException {
		// We'll need a connection to listen for
		Socket socket;

		// Spin/listen
		while (run) {
			// Listen for a connection
			socket = listener.accept();

			// Make a new connection handler
			new ConnectionHandler(socket).run();

		}
	}
}
