package main;

import java.util.function.Consumer;

import actiondata.ActionData;
import message.Message;


public class ClientConnectionHandler {

	private static int PORT = 25632;
	private static int NUMBER_OF_SENDER_THREADS = 1000;
	
	private ServerConnection serverConnection;
	private Parser parser;
	private ListenerThread listener;
	private SenderThreadPool senders;
	

	public ClientConnectionHandler() {
		this.serverConnection = new ServerConnection("localhost", PORT);
		this.parser = new Parser();
		this.listener = new ListenerThread(serverConnection, parser);
		this.senders = new SenderThreadPool(serverConnection, parser, listener, NUMBER_OF_SENDER_THREADS);
		this.listener.start();
	}
	
	
	public void sendJSON(final String json, final Consumer<Message> responseAction) {
		this.senders.send(json, responseAction);
	}
}