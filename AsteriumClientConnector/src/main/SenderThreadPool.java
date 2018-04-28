package main;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import message.Message;

/**
 * Creates a pool of threads for {@link SenderThread}.
 * 
 * @author Daniel
 *
 */
public class SenderThreadPool {
	private Queue<SenderThread> idlePool;
	private Queue<SenderThread> busyPool;
	private Parser parser;
	private ListenerThread listener;
	private ServerConnection connection;
	private int numberOfThreads;

	/**
	 * Creates a pool of threads with the number of threads specified in the
	 * constructor.
	 * 
	 * @param connection
	 *            - {@link ServerConnection}
	 * @param parser
	 *            - {@link Parser}
	 * @param listenerThread
	 *            - {@link ListenerThread}
	 * @param numberOfThreads
	 *            - {@link Integer}
	 */
	public SenderThreadPool(ServerConnection connection, Parser parser, ListenerThread listenerThread,
			int numberOfThreads) {
		this.idlePool = new LinkedList<SenderThread>();
		this.busyPool = new LinkedList<SenderThread>();

		this.parser = parser;
		this.listener = listenerThread;
		this.connection = connection;

		// Populate pool
		for (int i = 0; i < numberOfThreads; i++) {
			SenderThread newThread = new SenderThread(connection, parser);
			this.listener.subscribe(newThread);
			this.idlePool.add(newThread);
		}
		this.numberOfThreads = numberOfThreads;
	}

	/**
	 * Passes the input to a thread's {@link SenderThread} send method. If there are
	 * no threads available, it will double the number of threads.
	 * 
	 * @param json - {@link String}
	 * @param action - {@link Consumer}
	 */
	public void send(final String json, final Consumer<Message> action) {
		SenderThread thread;
		if (!idlePool.isEmpty()) {
			thread = this.idlePool.remove();
			this.busyPool.add(thread);
			thread.send(json, action);
		} else {
			// Repopulate pool
			int busyPoolSize = this.busyPool.size();

			// Go through busy pool and look for any idle threads, adding them to idle pool.
			// Note: This should be replaced with a Publisher-Subscriber pattern, where
			// a thread will notify the thread pool that it is done, when done.
			for (int i = 0; i < busyPoolSize; i++) {
				thread = this.busyPool.remove();
				if (!thread.isWaiting()) {
					this.idlePool.add(thread);
				} else {
					this.busyPool.add(thread);
				}
			}

			// If idlePool still empty, double number of threads
			if (this.idlePool.size() == 0) {
				// Double size of pool
				for (int i = 0; i < this.numberOfThreads; i++) {
					SenderThread newThread = new SenderThread(connection, parser);
					this.listener.subscribe(newThread);
					this.idlePool.add(newThread);
				}
				this.numberOfThreads *= 2;
			}

			// Reinvoke method a la Translation Lookaside Buffer.
			this.send(json, action);
		}
	}
}
