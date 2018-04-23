package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import actiondata.ActionData;

public class SenderThreadPool {
	private PrintWriter pw;
	private static ExecutorService senderThreads = Executors.newCachedThreadPool();
	
	public SenderThreadPool(ServerConnection connection, Parser parser, ListenerThread listenerThread) {
		try {
			this.pw = new PrintWriter(connection.getSocket().getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(final String json, final Consumer<ActionData> action) {
		
	}
}
