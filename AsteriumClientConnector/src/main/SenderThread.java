package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

import actiondata.ActionData;
import message.Message;

public class SenderThread extends Thread implements Subscriber<Message> {
	private Message subscribedData;
	private Consumer<Message> responseMethod;
	private Parser parser;
	private PrintWriter output;
	private boolean isWaiting;

	public SenderThread(ServerConnection connection, Parser parser) {
		System.out.println("Constructing SenderThread...");
		try {
			this.output = new PrintWriter(connection.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.parser = parser;
		this.isWaiting = false;
	}

	public void send(final String json, final Consumer<Message> action) {
		System.out.println("SenderThread is sending...");
		this.subscribedData = this.parser.parse(json);
		this.responseMethod = action;
		this.isWaiting = true;
		this.output.println(json);
		this.output.flush();
		System.out.println("SenderThread sent.");
	}

	public boolean isWaiting() {
		return this.isWaiting;
	}

	@Override
	public void onNext(Message item) {
		// Check if the published ActionData is the subscribed
		// data, and call response method if it is.
		System.out.println("SenderThread received publication. Checking for equality...");
		if (item.equals(subscribedData)) {
			System.out.println("SenderThread ActionData is equal. Idling and calling responseMethod...");
			this.responseMethod.accept(item);
			this.isWaiting = false;
		}
	}

	/*
	 * Unused methods from Subscriber
	 */
	@Override
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Throwable throwable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub

	}
}
