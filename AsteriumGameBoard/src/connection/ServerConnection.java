package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Manages the GameBoard's connection to the server.
 */
public class ServerConnection {

	// Temporary JSON string
	private final static String CREATE_TEST = "{“request”:{“action_name”:“create_game”,“create_game”:{}}}";

	Socket connection;

	public ServerConnection(String address, int port) {
		try {

			while (true) {
				Socket connection = new Socket(address, port);

				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				BufferedReader br = new BufferedReader(isr);

				OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
				
				PrintWriter writerToClient = new PrintWriter(connection.getOutputStream());
                writerToClient.println(CREATE_TEST);
                writerToClient.flush();
				
				//connection.getOutputStream().write(CREATE_TEST.getBytes());
				osw.write(CREATE_TEST);
				
				osw.flush();
				
				System.out.println("Message Sent...");

				StringBuilder builder = new StringBuilder();
				String line = "";

				// Get every line
				while ((line = br.readLine()) != null) {
					builder.append(line);
				}

				System.out.println(builder.toString());

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
