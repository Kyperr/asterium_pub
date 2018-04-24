package main;

import java.util.function.Consumer;

import actiondata.ActionData;
import message.Message;


public class ClientConnectionHandler {

	private static int PORT = 25632;
	private static int NUMBER_OF_SENDER_THREADS = 1;
	
	private ServerConnection serverConnection;
	private Parser parser;
	private ListenerThread listener;
	private SenderThreadPool senders;
	

	public ClientConnectionHandler() {
System.out.println("Constructing ClientConnectionHandler...");
		this.serverConnection = new ServerConnection("localhost", PORT);
		this.parser = new Parser();
System.out.println("Constructing ListenerThread...");
		this.listener = new ListenerThread(serverConnection, parser);
System.out.println("Constructing SenderThreadPool...");
		this.senders = new SenderThreadPool(serverConnection, parser, listener, NUMBER_OF_SENDER_THREADS);
System.out.println("Starting listener...");
		this.listener.start();
System.out.println("ClientConnectionHandler construction complete.");
	}
	
	
	public void sendJSON(final String json, final Consumer<Message> responseAction) {
		this.senders.send(json, responseAction);
	}
}