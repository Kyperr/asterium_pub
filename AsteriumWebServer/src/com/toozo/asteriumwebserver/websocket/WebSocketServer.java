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
import com.toozo.asteriumwebserver.actions.SendErrorAction;
import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ErroredResponseData;
import main.Parser;
import message.Message;
import message.Response;

@ServerEndpoint("/Game")
public class WebSocketServer {

	private static ExecutorService threadPoolExec = Executors.newCachedThreadPool();
	
	@OnOpen
	public void onOpen(Session session){		
	}
	
	@OnClose
	public void onClose(Session session){
	}
	
	@OnMessage
	public void onMessage(Session session, String message){
		
		System.out.println(message);
		try {
		Message parsedMessage = new Parser().parse(message);

		if(parsedMessage.getAuthToken().isEmpty()) {
			String authToken = SessionManager.getInstance().registerNewSession(session);
			parsedMessage.setAuthToken(authToken);
		}
		
		Action action = Action.getActionFor(parsedMessage);

		threadPoolExec.execute(action);
		} catch(JSONException e) {
			e.printStackTrace();
			try {
				session.getBasicRemote().sendText("Error: Malformed JSON.");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}
	
}
