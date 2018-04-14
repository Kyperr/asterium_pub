package sockettools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import sessionmanagement.SessionManager;
import sessionmanagement.SessionManager.Session;

public class ConnectionHandler extends Thread {

	/* The connection */
	private final Session session;

	/* Are we running or not? */
	private boolean run = false;
	
	public ConnectionHandler(Socket socket) {
		this.session = SessionManager.getInstance().createSession(socket);
	}
	
	public void run() {
		// We're running
		run = true;
		
		// New input stream
		try (InputStreamReader isr = new InputStreamReader(this.session.getSocket().getInputStream());
				BufferedReader br = new BufferedReader(isr);) {
			
			while(run) {
				// Get the input
				StringBuilder builder = new StringBuilder();
				String line = "";
				
				// Get every friggin line
				while((line = br.readLine()) != null) {
					builder.append(line);
				}
				
				// Call the parser, give them the string/input
				// ^^ TODO ^^
			}
		} catch (IOException e) {
			e.printStackTrace();
			// TODO change this to actually handle
		}
	}
}
