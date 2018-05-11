package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import message.Message;

/**
 * ClientConnectionHandler which handles connecting to the server, 
 * and allows sending and receiving responses from the server.
 * 
 * @author Greg Schmitt
 */
public class ClientConnectionHandler {
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private final Map<UUID, Consumer<Message>> callbacks;
	private ListenerThread listener;
	private Parser parser;
	private PrintWriter output;
	private ServerConnection connection;
	
	/**
	 * Creates a new ClientConnectionHandler and connects it to the server at address:port.
	 * 
	 * @param address The address of the server.
	 * @param port The port of the server on which to open a socket.
	 */
	public ClientConnectionHandler(final String address, final int port) {
		// Establish connection with server
		this.connection = new ServerConnection(address, port);
		
		// Establish socket with server
		try {
			this.output = new PrintWriter(connection.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Instantiate instance variables
		this.parser = new Parser();
		this.callbacks = new HashMap<UUID, Consumer<Message>>();
		this.listener = new ListenerThread(this.connection, this.parser, this.callbacks, ClientConnectionHandler.threadPool);
		this.listener.start();
	}
	
	/**
	 * Sends a JSON message to the server.
	 * 
	 * @param json A JSON message to send to the server.
	 */
	public void send(final String json) {
		ClientConnectionHandler.this.output.println(json);
		ClientConnectionHandler.this.output.flush();
	}
	
	/**
	 * Sends a JSON message to the server and calls responseAction 
	 * when receiving a response to the message.
	 * 
	 * @param json A JSON message to send to the server.
	 * @param responseAction The code which should be called when a response is received.
	 */
	public void send(final String json, final Consumer<Message> responseAction) {
		threadPool.execute(() -> {
			// Put the responseAction in callbacks, keyed off of the message ID.
			System.out.println(ClientConnectionHandler.this.parser.toString());
			ClientConnectionHandler.this.callbacks.put(ClientConnectionHandler.this.parser.getMessageID(json), responseAction);
			
			// Send json to server
			send(json);
		});
	}
}
