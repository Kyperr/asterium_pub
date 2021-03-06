package com.toozo.asteriumwebserver.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.websocket.Session;
import javax.xml.ws.soap.AddressingFeature.Responses;

import com.toozo.asteriumwebserver.sessionmanager.SessionManager;

import actiondata.ActionData;
import actiondata.ErroredResponseData;
import message.Message;
import message.Request;
import message.Response;

/**
 * Runnable {@link Action} representing an action which should be performed by
 * the server.
 * 
 * @author Studio Toozo
 */
public abstract class Action implements Runnable {

	// ==== CONSTANTS ====
	public static final String JOIN_AS_PLAYER = "join_as_player";
	public static final String JOIN_AS_GAMEBOARD = "join_as_gameboard";
	public static final String CREATE_GAME = "create_game";
	public static final String TOGGLE_READY_UP = "toggle_ready_up";
	public static final String QUERY_IS_IN_GAME = "query_is_in_game";
	public static final String PLAYER_SYNC = "player_sync";
	public static final String ALLOCATE_STATS = "allocate_stats";
	public static final String TURN = "turn";
	public static final String ITEM_TURN = "item_turn";
	public static final String SET_READY_STATUS = "set_ready_status";
	public static final String SYNC_LOCATIONS = "sync_locations";
	public static final String SYNC_PLAYER_LIST = "sync_player_list";
	public static final String USE_ITEM = "use_item";
	public static final String DISCARD_ITEM = "discard_item";
	public static final String COMMUNAL_INVENTORY = "communal_inventory";
	public static final String LEAVE_GAME = "leave_game";

	// Also controls verbosity of children.
	public static final boolean VERBOSE = true;
	// ===================

	/**
	 * Static map from an {@link ActionData} subclass to the function which should
	 * be performed.
	 */
	private final static Map<String, Function<Message, Action>> ACTION_LOOKUP = new HashMap<String, Function<Message, Action>>() {
		/**
		 * Auto-generated unique identifier for ACTION_LOOKUP
		 */
		private static final long serialVersionUID = 7474026135071364749L;

		/*
		 * Static block in which ACTION_LOOKUP is populated. As new Actions are written,
		 * their corresponding functions should be added here.
		 */
		{			
			//default: take in action spit out action that does nothing
			//default: response from client , ^
			
			//need responses from clients to server
			
			//syncplayerclient request server->client NOT IN THIS MAP
			//client response->server "okay"
			//like this? 
			put(ActionData.SUCCESS_RESPONSE, ClientToServerResponseAction::fromMessage);
			put(ActionData.ERRORED_RESPONSE, SendErrorAction::fromMessage);
			
			put(ActionData.JOIN_AS_PLAYER, JoinAsPlayerAction::fromMessage);
			put(ActionData.JOIN_AS_GAMEBOARD, JoinAsGameBoardAction::fromMessage);
			put(ActionData.CREATE_GAME, CreateGameAction::fromMessage);
			
			put(ActionData.ALLOCATE_STATS, AllocateStatsAction::fromMessage);
			put(ActionData.TURN_ACTION, TurnAction::fromMessage);
			put(ActionData.ITEM_TURN_ACTION, ItemTurnAction::fromMessage);
			put(ActionData.USE_ITEM, UseItemAction::fromMessage);
			put(ActionData.COMMUNAL_INVENTORY, CommunalInventoryAction::fromMessage);
			put(ActionData.DISCARD_ITEM, DiscardItemAction::fromMessage);
			
			put(ActionData.QUERY_IS_IN_GAME, QueryIsInGameAction::fromMessage);
			put(ActionData.SET_READY_STATUS, SetReadyStatusAction::fromMessage);
			put(ActionData.TOGGLE_READY_UP, ToggleReadyUpAction::fromMessage);
			
			
			
			put(ActionData.LEAVE_GAME, LeaveGameAction::fromMessage);
		}
	};

	/**
	 * Static method to look up and call the function corresponding to a
	 * {@link Message}, then returns the results.
	 * 
	 * @param sender
	 *            The {@link Session} which is sending a message.
	 * @param message
	 *            The {@link Message} to find the appropriate {@link Action} for.
	 * @return The {@link Action} returned from the function corresponding to the
	 *         message.
	 */
	public static Action getActionFor(final Message message) {
		ActionData actionData = message.getActionData();
		try {

			// Look up the function that corresponds to actionData's class and call it.
			Function<Message, Action> action = ACTION_LOOKUP.get(actionData.getName());
			if(action != null) {
				return action.apply(message);				
			} else {
				throw new IllegalArgumentException("No action found for: " + actionData.getName());
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			return new SendErrorAction(actionData.getName(), message.getAuthToken(), SendErrorAction.INCORRECT_ACTION_MAPPING,
					message.getMessageID());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new SendErrorAction(actionData.getName(), message.getAuthToken(), SendErrorAction.INCORRECT_ACTION_MAPPING,
					message.getMessageID());			
		}
	}

	// The name of the Action (e.g. "create_game")
	private final String name;

	// The message ID to respond to
	private final UUID messageID;

	private final String authToken;

	/**
	 * Abstract constructor to be called by subclasses of {@link Action}.
	 * 
	 * @param name
	 *            the name of the {@link Action} (e.g. "create_game")
	 * @param callingSession
	 *            the {@link Session} which is using this {@link Action}.
	 * @param messageID
	 *            the identifier for a {@link Message} for {@link Responses} to
	 *            respond to the correct {@link Request}.
	 */
	protected Action(final String name, final String authToken, final UUID messageID) {
		this.name = name;
		this.authToken = authToken;
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
	 * 
	 * @return a String that is the name of this {@link Action}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the authToken related to this {@link Action}.
	 * 
	 * @return the authToken related to this {@link Action}.
	 */
	protected String getCallingAuthToken() {
		return this.authToken;
	}

	/**
	 * Get the message id related to this {@link Action}.
	 * 
	 * @return a {@link UUID} that is the {@link Message} identifier
	 */
	protected UUID getMessageID() {
		return this.messageID;
	}

	/**
	 * Abstract method which should perform the operations related to this
	 * {@link Action}.
	 */
	protected abstract void doAction();

	/**
	 * Utility function for use with subclasses to send errors.
	 */
	protected void sendError(Integer errorCode) {
		ErroredResponseData ead = new ErroredResponseData(this.getName());
		Message message = new Response(ead, errorCode, this.getMessageID(), "");

		// Try to send the error response
		try {
			Session session = SessionManager.getInstance().getSession(getCallingAuthToken());
			synchronized(session) {
				session.getBasicRemote()
						.sendText(message.jsonify().toString());	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
