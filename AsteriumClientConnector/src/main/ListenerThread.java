package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.function.Consumer;

import actiondata.ActionData;

public class ListenerThread extends Thread implements Publisher {
	private boolean running;
	private InputStreamReader isr;
	private BufferedReader br;
	private Parser parser;

	public ListenerThread(ServerConnection connection, Parser parser) {
		this.parser = parser;
		this.running = true;
		try {
			this.isr = new InputStreamReader(connection.getSocket().getInputStream());
			this.br = new BufferedReader(isr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startListening() {
		this.running = true;
	}
	
	public void stopListening() {
		this.running = false;
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
					ActionData data = this.parser.parseToActionData(sb.toString());
					String name = data.getName();
					
					// End of message reached, clear string builder
					sb.setLength(0);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void subscribe(Subscriber subscriber) {
		// TODO Auto-generated method stub
		
	}
}
