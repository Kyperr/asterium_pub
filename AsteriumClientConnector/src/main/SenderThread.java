package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Consumer;

import message.Message;

/**
 * This is a thread used to send data. It's purpose is to handle connection
 * work. It will send the input and wait to handle the response.
 *
 */
public class SenderThread extends Thread {
	public static final boolean VERBOSE = false;
	
	private UUID sentMessageID;
	private Consumer<Message> responseMethod;
	private Parser parser;
	private PrintWriter output;
	private boolean isWaiting;

	/**
	 * Creates a SenderThread. Creates a reference to the connection's output
	 * stream.
	 * 
	 * @param connection
	 * @param parser
	 */
	public SenderThread(ServerConnection connection, Parser parser) {
		if (VERBOSE) {
			System.out.println("Constructing SenderThread...");
		}
		
		try {
			this.output = new PrintWriter(connection.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.parser = parser;
		this.isWaiting = false;
		
		if (VERBOSE) {
			System.out.println("SenderThread constructed.");
		}
	}

	/**
	 * Sends a message to the connection specified in the consumer.
	 * 
	 * @param json
	 * @param action
	 */
	public void send(final String json, final Consumer<Message> action) {
		if (VERBOSE) {
			System.out.println("SenderThread is sending...");
		}
		
		this.sentMessageID = parser.getMessageID(json);
		this.responseMethod = action;
		this.isWaiting = true;
		this.output.println(json);
		this.output.flush();
		
		if (VERBOSE) {
			System.out.println("SenderThread sent.");
		}
	}

	/**
	 * Returns whether or not this is waiting.
	 * 
	 * @return
	 */
	public boolean isWaiting() {
		return this.isWaiting;
	}

	
	@Override
	public String toString() {
		// Header
		StringBuilder sb = new StringBuilder("SenderThread {");
		
		// Content: sentMessageID
		sb.append("\n\tsentMessageID: ");
		sb.append(this.sentMessageID.toString());
		
		// Content: isWaiting
		sb.append("\n\tisWaiting? ");
		sb.append(String.valueOf(this.isWaiting));
		
		// Footer
		sb.append("\n}");
		return sb.toString();
	}
}
