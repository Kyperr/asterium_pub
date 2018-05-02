package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This is an object used to reference connection specific information.
 * @author Daniel
 *
 */
public class ServerConnection {
	
	private Socket socket;
	
	/**
	 * Creates a socket with the given address and port.
	 * 
	 * @param address - {@link String}
	 * @param port - {@link Integer}
	 */
	public ServerConnection(String address, int port) {
		try {
			this.socket = new Socket(address, port);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the socket creates by this object.
	 * @return
	 */
	public Socket getSocket() {
		return this.socket;
	}

}
