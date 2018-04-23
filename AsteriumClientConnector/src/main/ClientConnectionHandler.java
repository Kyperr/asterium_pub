package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class ClientConnectionHandler {

	private static int PORT = 25632;
	
	private ServerConnection serverConnection;
	private Parser parser;
	
	
	private Thread listener;
	

	public ClientConnectionHandler() {
		serverConnection = new ServerConnection("localhost", PORT);
		parser = new Parser();
		listener = new ListenerThread(serverConnection, parser);
		listener.start();
	}
	
	
	public void sendJSON(final String json, final Consumer<Number> responseAction) {
		//PrintWriter output = new PrintWriter(serverConnection.);
		Number errorCode = 0;// = parser.parse(response);
		responseAction.accept(errorCode);
	}
}