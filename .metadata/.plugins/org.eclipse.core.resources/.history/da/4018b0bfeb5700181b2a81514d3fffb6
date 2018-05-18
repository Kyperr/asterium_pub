package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import message.Message;

public class ListenerThread extends Thread {
	public static final boolean VERBOSE = false;
	private boolean running;
	private Parser parser;
	private final Map<String, Consumer<Message>> requestCallbacks;
	private final Map<UUID, Consumer<Message>> responseCallbacks;
	private ExecutorService threadPool;
	
	/**
	 * Constructs a ListenerThread. Requires a connection and a parser. This also
	 * creates a reference to the connection's input stream.
	 * 
	 * @param connection
	 *            - {@link ServerConnection}
	 * @param parser
	 *            - {@link Parser}
	 */
	public ListenerThread(WebsocketClientEndpoint connection, Parser parser, 
		      			  ConcurrentHashMap<String, Consumer<Message>> requestCallbacks,
					      ConcurrentHashMap<UUID, Consumer<Message>> responseCallbacks,
					      ExecutorService threadPool) {
		this.parser = parser;
		this.responseCallbacks = responseCallbacks;
		this.requestCallbacks = requestCallbacks;
		this.threadPool = threadPool;
		this.running = false;
	}
	
	@Override
	/**
	 * Sets running and starts this thread.
	 */
	public void start() {
		this.running = true;
		super.start();
	}

	/**
	 * Sets running to false;
	 */
	public void stopListening() {
		this.running = false;
	}

	@Override
	/**
	 * This is the run method for this thread. This is the part that listens for
	 * input and publishes it.
	 */
	public void run() {
		String json;
		while (running) {
			try {
				// Read message from server
				json = this.br.readLine();
				
				if (VERBOSE) {
					System.out.println("ListenerThread received message: " + json);
				}
				
				Message message = this.parser.parse(json);
				
				if (message.isResponse()) {
					// Handle responses
					threadPool.execute(() -> {
						UUID id = message.getMessageID();
						Consumer<Message> callback = ListenerThread.this.responseCallbacks.remove(id);
						if (callback != null) {
							callback.accept(message);
						} else {
							System.err.println("Error: Response received, but callback not found!");
						}
					});
				} else {
					// Handle requests
					threadPool.execute(() -> {
						String actionName = message.getActionData().getName();
						Consumer<Message> callback = ListenerThread.this.requestCallbacks.remove(actionName);
						if (callback != null) {
							callback.accept(message);
						} else {
							System.err.println("Error: Request received, but callback not found!");
						}
					});
				}
			} catch (IOException e) {
				System.err.println("This breaks as part of a loop and just goes back to listening.");
				e.printStackTrace();
			}
		}
	}

}
