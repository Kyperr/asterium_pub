package connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection {

	Socket connection;
	
	public ServerConnection(String address, int port) {
		try {
			Socket connection = new Socket(address, port);
			while(true) {
				
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
