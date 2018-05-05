package main;

import java.io.IOException;

import sockettools.ServerSocketListener;

/**
 * Starts the server.
 * 
 * @author Studio Toozo
 */
public class Main {
	public static final boolean VERBOSE = true;
	// HARD-CODED, this should be dynamic.
	private static final int PORT = 25632;

	/**
	 * Starts listening on PORT.
	 * @param args command-line arguments.
	 */
	public static void main(String[] args) {
		try {
			// Create ServerSocketListener on PORT.
			ServerSocketListener listener = new ServerSocketListener(PORT);
			
			if (VERBOSE) {
				System.out.println("Server listening...");
			}
			
			// Start listening.
			listener.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
