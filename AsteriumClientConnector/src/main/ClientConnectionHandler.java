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

public class ClientConnectionHandler {
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private final Map<UUID, Consumer<Message>> callbacks;
	private ListenerThread listener;
	private Parser parser;
	private PrintWriter output;
	private ServerConnection connection;
	
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
	
	public void sequentialSend(final String json) {
		ClientConnectionHandler.this.output.println(json);
		ClientConnectionHandler.this.output.flush();
	}
	
	public void concurrentSend(final String json, final Consumer<Message> responseAction) {
		threadPool.execute(() -> {
			// Put the responseAction in callbacks, keyed off of the message ID.
			System.out.println(ClientConnectionHandler.this.parser.toString());
			ClientConnectionHandler.this.callbacks.put(ClientConnectionHandler.this.parser.getMessageID(json), responseAction);
			
			// Send json to server
			sequentialSend(json);
		});
	}
}
