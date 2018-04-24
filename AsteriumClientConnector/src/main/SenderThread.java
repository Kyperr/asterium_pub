package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

import actiondata.ActionData;

public class SenderThread extends Thread implements Subscriber<ActionData> {
	private ActionData subscribedData;
	private Consumer<ActionData> responseMethod;
	private Parser parser;
	private PrintWriter output;
	
	public SenderThread(ServerConnection connection, Parser parser) {
		try {
			this.output = new PrintWriter(connection.getSocket().getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.parser = parser;
	}
	
	public void send(final String json, final Consumer<ActionData> action) {
		this.subscribedData = this.parser.parseToActionData(json);
		this.responseMethod = action;
	}
	
	@Override
	public void onNext(ActionData item) {
		// Check if the published ActionData is the subscribed 
		// data, and call response method if it is.
		if (item.equals(subscribedData)) {
			responseMethod.accept(item);
		}
	}
	
	// Unused subscriber methods
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
