package sockettools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sessionmanagement.SessionManager;
import sessionmanagement.SessionManager.Session;

public class ConnectionHandler extends Thread {
	
	private static final int SOCKET_HANDLER_TIMEOUT = 60000;
	
	private static ExecutorService threadPoolExec = Executors.newCachedThreadPool();

	/* The Session for this ConnectionHandler thread. Has the associated socket
	 * and authentication token.
	 */
	private final Session session;

	/* Run condition: true while thread should be alive, false when it should be killed. */
	private boolean run = false;
	
	public ConnectionHandler(Socket socket) {
		this.session = SessionManager.getInstance().createSession(socket);
	}
	
	public void run() {
		// We're running
		run = true;
		
		/* Using this ConnectionHandler thread's associated socket, set the timeout 
		 * and create a new input stream. Read input from the stream, and call the
		 * parser to execute requests and responses.
		 */
		try {	this.session.getSocket().setSoTimeout(SOCKET_HANDLER_TIMEOUT);
				InputStreamReader isr = new InputStreamReader(this.session.getSocket().getInputStream());
				BufferedReader br = new BufferedReader(isr); 
			
				while(run) {
					// Get the input
					StringBuilder builder = new StringBuilder();
					String line = "";
					
					// Get every line
					while((line = br.readLine()) != null) {
						builder.append(line);
					}
					
					// Call the parser, give them the string/input
					// ^^ TODO ^^
					
					// Pass a runnable to the ExecutorService
					Runnable runnable = new Runnable() {
						@Override
						public void run() {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							System.out.println("sleepy");
							
						}
					};
					threadPoolExec.execute(runnable);
			}
				
		/* If there has been a timeout, the user has been disconnected. Kill this thread. */
		} catch (SocketTimeoutException e) {
			run = false;
			interrupt();
			
		} catch (IOException e) {
			e.printStackTrace();
			// TODO change this to actually handle
		}  
	}
}
