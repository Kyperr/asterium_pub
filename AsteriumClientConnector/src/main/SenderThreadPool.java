package main;

import java.util.LinkedList;
import java.util.Queue;

public class SenderThreadPool {
	Queue<SenderThread> pool;
	
	public SenderThreadPool(ServerConnection connection, Parser parser, ListenerThread listenerThread,
							int numberOfThreads) {
		this.pool = new LinkedList<SenderThread>();
		
		// Populate pool
		for (int i = 0; i < numberOfThreads; i++) {
			SenderThread newThread = new SenderThread(connection, parser);
			listenerThread.subscribe(newThread);
			pool.add(newThread);
		}
	}
	

}
