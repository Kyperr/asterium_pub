package actions;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.json.JSONObject;

import actiondata.ActionData;
import actiondata.CreateGameActionData;
import sessionmanagement.SessionManager.Session;

public abstract class Action implements Runnable {

	public static final String JOIN_AS_PLAYER = "join_as_player";
	public static final String JOIN_AS_PLAYER_RESPONSE = "join_as_player_response";

	public static final String CREATE_GAME = "create_game";
	public static final String CREATE_GAME_RESPONSE = "create_game_response";

	//
	private final static Map<Class<? extends ActionData>, 
							 BiFunction<Session, ActionData, Action>> ACTION_LOOKUP = new HashMap<Class<? extends ActionData>, 
							 																	  BiFunction<Session, ActionData, Action>>() {
		{
			ACTION_LOOKUP.put(CreateGameActionData.class, CreateGameAction::fromActionData);
		}
	};
	
	public static Action getActionFor(Session sender, ActionData actionData) {
		try {
			return ACTION_LOOKUP.get(actionData.getClass()).apply(sender, actionData);	
		} catch(ClassCastException e) {
			return new SendErrorAction(sender, actionData.getName(), SendErrorAction.INCORRECT_ACTION_MAPPING);
		}
	}

	// public final static Map<String, Function<? extends ActionData> >

	private final String name;
	private final Session callingSession;

	public Action(String name, Session callingSession) {
		this.name = name;
		this.callingSession = callingSession;
	}

	@Override
	public void run() {
		doAction();

	}
	
	public String getName() {
		return this.name;
	}

	protected Session getCallingSession() {
		return this.callingSession;
	}

	protected abstract void doAction();

}
