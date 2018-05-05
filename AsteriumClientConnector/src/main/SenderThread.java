package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

import message.Message;

/**
 * This is a thread used to send data. It's purpose is to handle connection
 * work. It will send the input and wait to handle the response.
 *
 */
public class SenderThread extends Thread implements Subscriber<Message> {
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
	/**
	 * Called by the publisher. You shouldn't be calling this. It reacts to the
	 * input as specified.
	 */
	public void onNext(Message item) {
		// Check if the published ActionData is the subscribed
		// data, and call response method if it is.
		if (VERBOSE) {
			System.out.println("SenderThread received publication. Checking for equality...");
		}
		
		if (item.getMessageID().equals(sentMessageID)) {
			if (VERBOSE) {
				System.out.println("SenderThread ActionData is equal. Idling and calling responseMethod...");
			}
			this.responseMethod.accept(item);
			this.isWaiting = false;
		}
	}

	/*
	 * Unused methods from Subscriber
	 */
	@Override
	/**
	 * Does nothing.
	 */
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Does nothing.
	 */
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Does nothing.
	 */
	public void onComplete() {
		// TODO Auto-generated method stub

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
