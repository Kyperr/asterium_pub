package com.toozo.asteriumwebserver.websocket;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONException;

import com.toozo.asteriumwebserver.actions.Action;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import main.Parser;
import message.Message;

@ServerEndpoint("/Game")
public class WebSocketServer {
	public static final boolean VERBOSE = false;
	public static final int TIMEOUT_MS = 10000;

	private static ExecutorService threadPoolExec = Executors.newCachedThreadPool();

	@OnOpen
	public void onOpen(Session session) {
		if (VERBOSE) { 
			System.out.println("Websocket opened!");
		}
	}

	@OnClose
	public void onClose(Session session) {
		if (VERBOSE) {
			System.out.println("Websocket closed.");
		}
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		Message parsedMessage = new Parser().parse(message);
		try {

			if (parsedMessage.getAuthToken().isEmpty()) {
				String authToken = SessionManager.getInstance().registerNewSession(session);
				parsedMessage.setAuthToken(authToken);
			}

			SessionManager.getInstance().remapSession(parsedMessage.getAuthToken(), session);

			Action action = Action.getActionFor(parsedMessage);

			threadPoolExec.execute(action);
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				synchronized (session) {
					session.getBasicRemote().sendText("Error: Malformed JSON.");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@OnError
	public void onError(Throwable e) {
		e.printStackTrace();
	}

}
