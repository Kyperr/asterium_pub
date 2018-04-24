package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import message.Message;

public class ListenerThread extends Thread implements Publisher<Message> {
	private boolean running;
	private InputStreamReader isr;
	private BufferedReader br;
	private Parser parser;
	private List<Subscriber<? super Message>> subscribers;

	public ListenerThread(ServerConnection connection, Parser parser) {
System.out.println("Constructing ListenerThread (not listening yet)...");
		this.parser = parser;
		this.running = false;
		this.subscribers = new LinkedList<Subscriber<? super Message>>();
		try {
			this.isr = new InputStreamReader(connection.getSocket().getInputStream());
			this.br = new BufferedReader(isr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void start() {
		this.running = true;
		super.start();
		System.out.println("ListenerThread listening...");
	}
	
	public void stopListening() {
		this.running = false;
		System.out.println("ListenerThread stopped listening...");
	}
	
	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
		String line;
		while (running) {
			try {
				line = this.br.readLine();
				
				if (line != null) {
					sb.append(line);
				} else {
					// End of message reached. Parse contents of string builder.
					Message data = this.parser.parse(sb.toString());
					publish(data);
					
					// End of message reached, clear string builder
					sb.setLength(0);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void publish(Message data) {
		for (Subscriber<? super Message> s : this.subscribers) {
			s.onNext(data);
		}
	}
	
	@Override
	public void subscribe(Subscriber<? super Message> subscriber) {
		this.subscribers.add(subscriber);
	}
}
