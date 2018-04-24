package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

import actiondata.ActionData;

public class SenderThreadPool implements Subscriber<ActionData> {
	private PrintWriter pw;
	private ActionData sentData;
	private static ExecutorService senderThreads = Executors.newCachedThreadPool();
	
	public SenderThreadPool(ServerConnection connection, Parser parser) {
		try {
			this.pw = new PrintWriter(connection.getSocket().getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(final String json, final Consumer<ActionData> action) {
		
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNext(ActionData item) {
		//TODO For each thread... 
		//TODO Check if ActionData is the same as the actionData this thread sent
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
