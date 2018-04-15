package sockettools;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketListener {
	
	private final int port;

	/* Condition: is the server loop running? */
	private boolean run = false;

	/* Listener for connections. */
	private ServerSocket listener;

	public ServerSocketListener(int port) throws IOException {
		this.port = port;
		listener = new ServerSocket(port);
	}

	public void start() throws IOException {
		// We're listening
		run = true;
		listen();
	}

	public void stop() {
		// We're DONE
		run = false;
	}

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
