package main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import message.Message;

/**
 * ClientConnectionHandler which handles connecting to the server, 
 * and allows sending and receiving responses from the server.
 * 
 * @author Greg Schmitt
 */
@ClientEndpoint
public class ClientConnectionHandler {
	private static ExecutorService threadPool = Executors.newCachedThreadPool();
	private final ConcurrentHashMap<String, Consumer<Message>> requestCallbacks;
	private final ConcurrentHashMap<UUID, Consumer<Message>> responseCallbacks;
	private Parser parser;
	private Session userSession = null;
	public static final boolean VERBOSE = true;
	
	/**
	 * Creates a new ClientConnectionHandler and connects it to the server at address:port.
	 * 
	 * @param address The address of the server.
	 * @param port The port of the server on which to open a socket.
	 */
	public ClientConnectionHandler(final String uri) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, new URI(uri));
		} catch (DeploymentException e) {
			e.printStackTrace();
			System.err.println("ERROR: Deployment exception. ClientConnectionHandler will not work properly.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("ERROR: IOException. ClientConnectionHandler will not work properly.");
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.err.println("ERROR: URI has a syntax problem. ClientConnectionHandler will not work properly.");
		}

		// Instantiate instance variables
		this.parser = new Parser();
		this.requestCallbacks = new ConcurrentHashMap<String, Consumer<Message>>();
		this.responseCallbacks = new ConcurrentHashMap<UUID, Consumer<Message>>();
	}
	
	/**
	 * Registers a callback function which should be called when a {@link Request} with
	 * actionName.
	 * 
	 * @param actionName The name of the {@link Request} action.
	 * @param callback The {@link Consumer} which should be called when a 
	 * 				   request with actionName is received.
	 */
	public void registerRequestCallback(String actionName, Consumer<Message> callback) {
		this.requestCallbacks.put(actionName, callback);
	}

	/**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }
    
	/**
	 * Sends a JSON message to the server, ignoring responses.
	 * 
	 * @param json A JSON message to send to the server.
	 */
	public void send(final String json) {
		synchronized(this.userSession) {
			try {
				this.userSession.getBasicRemote().sendText(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Sends a JSON message to the server and calls responseAction 
	 * when receiving a response to the message.
	 * 
	 * @param json A JSON message to send to the server.
	 * @param responseAction The code which should be called when a response is received.
	 */
	public void send(final String json, final Consumer<Message> responseAction) {
		threadPool.execute(() -> {
			// Put the responseAction in callbacks, keyed off of the message ID.
			System.out.println(ClientConnectionHandler.this.parser.toString());
			ClientConnectionHandler.this.responseCallbacks.put(ClientConnectionHandler.this.parser.getMessageID(json), responseAction);
			
			// Send json to server
			this.send(json);
		});
	}
    
    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String string) {
    	if (VERBOSE) {
			System.out.println("ListenerThread received message: " + string);
		}
		
		Message message = this.parser.parse(string);
		
		if (message.isResponse()) {
			// Handle responses
			threadPool.execute(() -> {
				UUID id = message.getMessageID();
				Consumer<Message> callback = this.responseCallbacks.remove(id);
				if (callback != null) {
					callback.accept(message);
				} else {
					System.err.println("Error: Response received, but callback not found for: " + message.getActionData().getName());
				}
			});
		} else {
			// Handle requests
			// TODO Change else block to message.isRequest(), and have Message not necessarily be a request or response.
			threadPool.execute(() -> {
				String actionName = message.getActionData().getName();
				Consumer<Message> callback = this.requestCallbacks.get(actionName);
				if (callback != null) {
					callback.accept(message);
				} else {
					System.err.println("Error: Request received, but callback not found for: " + message.getActionData().getName());
				}
			});
		}
    }
	
    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }
}
