package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import message.Message;

/**
 * This is an object that, as a client, listens for input from the server. As
 * input comes from the server, it will publish the input to it's subscribers.
 */
public class ListenerThread extends Thread implements Publisher<Message> {
	public static final boolean VERBOSE = false;
	private boolean running;
	private InputStreamReader isr;
	private BufferedReader br;
	private Parser parser;
	private List<Subscriber<? super Message>> subscribers;

	/**
	 * Constructs a ListenerThread. Requires a connection and a parser. This also
	 * creates a reference to the connection's input stream.
	 * 
	 * @param connection
	 *            - {@link ServerConnection}
	 * @param parser
	 *            - {@link Parser}
	 */
	public ListenerThread(ServerConnection connection, Parser parser) {

		this.parser = parser;
		this.running = false;
		this.subscribers = new LinkedList<Subscriber<? super Message>>();
		try {
			this.isr = new InputStreamReader(connection.getSocket().getInputStream());
			this.br = new BufferedReader(isr);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	/**
	 * Sets running and starts this thread.
	 */
	public void start() {
		this.running = true;
		super.start();
	}

	/**
	 * Sets running to false;
	 */
	public void stopListening() {
		this.running = false;
	}

	@Override
	/**
	 * This is the run method for this thread. This is the part that listens for
	 * input and publishes it.
	 */
	public void run() {
		String json;
		while (running) {
			try {
				json = this.br.readLine();
				
				if (VERBOSE) {
					System.out.println(json);
				}
				
				Message data = this.parser.parse(json);
				
				publish(data);
				
			} catch (IOException e) {
				System.err.println("This breaks as part of a loop and just goes back to listening.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Publishes input to subscriber.
	 * @param data
	 */
	private void publish(Message data) {
		for (Subscriber<? super Message> s : this.subscribers) {
			if (VERBOSE) {
				System.out.println("THREAD POOL:");
				System.out.println(s.toString());
			}
			s.onNext(data);
		}
	}

	@Override
	/**
	 * Adds as a subscriber to be send publications.
	 */
	public void subscribe(Subscriber<? super Message> subscriber) {
		this.subscribers.add(subscriber);
	}
}
