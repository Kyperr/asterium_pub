package actions;

import java.util.Optional;

import actiondata.JoinAsPlayerRequestData.PlayerData;
import gamelogic.Game;
import sessionmanagement.SessionManager.Session;

/**
 * Action which allows a player to indicate that their character is ready,
 * and they are ready for the game to start. 
 * 
 * @author Jenna Hand
 */
public class ReadyUpAction extends RequestAction {

	
	
	public ReadyUpAction(String name, Session callingSession) {
		super(name, callingSession);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doAction() {
		// TODO Auto-generated method stub
		
	}


}
