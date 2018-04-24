package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection {

	private final static String CREATE_TEST = "{“request”:{“action_name”:“create_game”,“create_game”:{}}}";
	
	Socket connection;
	
	public ServerConnection(String address, int port) {
		try {
			Socket connection = new Socket(address, port);

			connection.getOutputStream().write(CREATE_TEST.getBytes());

			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			
			StringBuilder builder = new StringBuilder();
			String line = "";

			// Get every line
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			
			System.out.println(builder.toString());
			
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
