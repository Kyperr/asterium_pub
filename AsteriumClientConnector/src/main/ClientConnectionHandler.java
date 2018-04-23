package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class ClientConnectionHandler {

	private static int PORT = 25632;
	
	private ServerConnection serverConnection;
	private Parser parser;
	
	
	private ListenerThread listener;
	private SenderThreadPool senders;
	

	public ClientConnectionHandler() {
		serverConnection = new ServerConnection("localhost", PORT);
		parser = new Parser();
		listener = new ListenerThread(serverConnection, parser);
		senders = new SenderThreadPool(serverConnection, parser);
		listener.subscribe(senders);
		listener.start();
	}
	
	
	public void sendJSON(final String json, final Consumer<Number> responseAction) {
		//PrintWriter output = new PrintWriter(serverConnection.);
		Number errorCode = 0;// = parser.parse(response);
		responseAction.accept(errorCode);
	}
}