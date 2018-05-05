package actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import actiondata.ActionData;
import actiondata.CreateGameRequestData;
import actiondata.ErroredResponseData;
import actiondata.JoinAsPlayerRequestData;
import message.Message;
import message.Response;
import sessionmanagement.SessionManager.Session;

/**
 * Runnable {@link Action} representing an action which should be performed by the server.
 * 
 * @author Studio Toozo
 */
public abstract class Action implements Runnable {

	// ==== CONSTANTS ====
	public static final String JOIN_AS_PLAYER = "join_as_player";
	public static final String JOIN_AS_PLAYER_RESPONSE = "join_as_player_response";

	public static final String JOIN_AS_GAMEBOARD = "join_as_gameboard";
	public static final String JOIN_AS_GAMEBOARD_RESPONSE = "join_as_gameboard_response";

	public static final String CREATE_GAME = "create_game";
	public static final String CREATE_GAME_RESPONSE = "create_game_response";
	
	public static final boolean VERBOSE = false;
	// ===================
	
	/**
	 * Static map from an {@link ActionData} subclass to the function which should be performed.
	 */
	private final static Map<Class<? extends ActionData>, BiFunction<Session, Message, Action>> ACTION_LOOKUP = new HashMap<Class<? extends ActionData>, BiFunction<Session, Message, Action>>() {
		/**
		 * Auto-generated unique identifier for ACTION_LOOKUP
		 */
		private static final long serialVersionUID = 7474026135071364749L;

		/*
		 * Static block in which ACTION_LOOKUP is populated.
		 * As new Actions are written, their corresponding functions should be added here.
		 */
		{
			put(JoinAsPlayerRequestData.class, JoinAsPlayerAction::fromMessage);
			put(CreateGameRequestData.class, CreateGameAction::fromMessage);
		}
	};

	/**
	 * Static method to look up and call the function corresponding to a {@link Message}, 
	 * then returns the results.
	 * 
	 * @param sender The {@link Session} which is sending a message.
	 * @param message	The {@link Message} to find the appropriate {@link Action} for.
	 * @return The {@link Action} returned from the function corresponding to the message.
	 */
	public static Action getActionFor(final Session sender, final Message message) {
		ActionData actionData = message.getActionData();
		try {
			if (VERBOSE) {
				System.out.println("class: " + actionData.getClass());
				for (Class<? extends ActionData> c : ACTION_LOOKUP.keySet()) {
					System.out.println("class2: " + c.getName());
				}
			}
			
			// Look up the function that corresponds to actionData's class and call it.
			return ACTION_LOOKUP.get(actionData.getClass()).apply(sender, message);
		} catch (ClassCastException e) {
			return new SendErrorAction(actionData.getName(), sender, SendErrorAction.INCORRECT_ACTION_MAPPING, message.getMessageID());
		}
	}

	// The name of the Action (e.g. "create_game")
	private final String name;
	
	// The message ID to respond to
	private final UUID messageID;
	
	// The session which is using this Action.
	// Uses include tracking which game needs to be modified by Action's call.
	private final Session callingSession;

	/**
	 * Abstract constructor to be called by subclasses of {@link Action}.
	 * 
	 * @param name the name of the {@link Action} (e.g. "create_game")
	 * @param callingSession the {@link Session} which is using this {@link Action}.
	 * @param messageID	the identifier for a {@link Message} for {@link Responses} to respond to the correct {@link Request}.
	 */
	protected Action(final String name, final Session callingSession, final UUID messageID) {
		this.name = name;
		this.callingSession = callingSession;
		this.messageID = messageID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		doAction();
	}

	/**
	 * Get the name of this {@link Action}.
	 * @return a String that is the name of this {@link Action}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the Session related to this {@link Action}.
	 * @return the Session related to this {@link Action}.
	 */
	protected Session getCallingSession() {
		return this.callingSession;
	}
	
	/**
	 * Get the message id related to this {@link Action}.
	 * @return a {@link UUID} that is the {@link Message} identifier
	 */
	protected UUID getMessageID() {
		return this.messageID;
	}

	/**
	 * Abstract method which should perform the operations related to this {@link Action}.
	 */
	protected abstract void doAction();
	
	/**
	 * Utility function for use with subclasses to send errors.
	 */
	protected void sendError(Integer errorCode) {
		ErroredResponseData ead = new ErroredResponseData(this.getName());
		Message message = new Response(ead, errorCode, this.getMessageID());
		
		// Try to send the error response
		try {
			this.getCallingSession().sendMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
