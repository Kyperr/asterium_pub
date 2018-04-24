package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

import actiondata.ActionData;
import message.Message;

public class SenderThread extends Thread implements Subscriber<Message> {
	private ActionData subscribedData;
	private Consumer<Message> responseMethod;
	private Parser parser;
	private PrintWriter output;
	private boolean isWaiting;
	
	public SenderThread(ServerConnection connection, Parser parser) {
		try {
			this.output = new PrintWriter(connection.getSocket().getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.parser = parser;
		this.isWaiting = false;
	}
	
	public void send(final String json, final Consumer<Message> action) {
		this.subscribedData = this.parser.parseToActionData(json);
		this.responseMethod = action;
		this.isWaiting = true;
		this.output.write(json);
	}
	
	public boolean isWaiting() {
		return this.isWaiting;
	}
	
	@Override
	public void onNext(Message item) {
		// Check if the published ActionData is the subscribed 
		// data, and call response method if it is.
		if (item.equals(subscribedData)) {
			this.isWaiting = false;
			this.responseMethod.accept(item);
		}
	}
	
	/*
	 * Unused methods from unsubscriber
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
