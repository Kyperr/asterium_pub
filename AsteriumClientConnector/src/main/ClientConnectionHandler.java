package main;

import java.util.function.Consumer;

import message.Message;

/**
 * An object that is used to establish and maintain a connection to the server as well as send messages.
 * 
 *
 */
public class ClientConnectionHandler {

	private static int PORT = 25632;
	private static int NUMBER_OF_SENDER_THREADS = 1;

	private ServerConnection serverConnection;
	private Parser parser;
	private ListenerThread listener;
	private SenderThreadPool senders;

	/**
	 * Creates a ClientConnectionHandler. This creates a {@link ServerConnection}, a
	 * {@link Parser}, a {@link ListenerThread}, and a {@link SenderThreadPool}.
	 */
	public ClientConnectionHandler(final String address, int port) {

		this.serverConnection = new ServerConnection(address, port);
		this.parser = new Parser();

		this.listener = new ListenerThread(serverConnection, parser);

		this.senders = new SenderThreadPool(serverConnection, parser, listener, NUMBER_OF_SENDER_THREADS);

		this.listener.start();
	}

	/**
	 * This method will send the {@link String} to the connection specified in the
	 * constructor. The {@link Consumer} that is specified will be registered and
	 * called upon response from the server.
	 * 
	 * @param json
	 * @param responseAction
	 */
	public void sendJSON(final String json, final Consumer<Message> responseAction) {
		this.senders.send(json, responseAction);
	}
}