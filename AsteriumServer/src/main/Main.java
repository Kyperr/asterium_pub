package main;

import java.io.IOException;

import sockettools.ServerSocketListener;

public class Main {
	
	private static int PORT = 25632;

	public static void main(String[] args) {
		try {
			ServerSocketListener listener = new ServerSocketListener(PORT);
			listener.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
