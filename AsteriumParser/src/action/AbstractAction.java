package action;

import java.util.Map;

public abstract class AbstractAction implements Runnable{
	
	/* Constants for action names */
	public static final String CREATE_GAME = "create_game";
	public static final String JOIN_AS_PLAYER = "join_as_player";
	public static final String JOIN_AS_GAME_BOARD = "join_as_game_board";
	public static final String START_GAME = "start_game";
	public static final String TURN_SEQUENCE = "turn_sequence";
	public static final String READY = "ready";
	public static final String END_GAME = "end_game";
	public static final String PLAYER_SYNC = "player_sync";
	
	
	private String name;
	private Map<String, Object> args;
	
	protected AbstractAction(String name, Map<String, Object> args) {
		setName(name);
		setArgs(args);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}
	
	public Object getArg(final String key) {
		return args.get(key);
	}
	
	@Override
	public abstract void run();
	
}
