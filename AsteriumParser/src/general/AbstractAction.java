package general;

import java.util.Map;

public abstract class AbstractAction implements Runnable{
	
	/* Constants for action names */
	public static final String JOIN_AS_PLAYER = "join_as_player";
	public static final String CREATE_GAME = "create_game";
	
	/* Constants for all messages */
	public static final String ACTION_NAME = "action_name";
	public static final String REQUEST = "request";
	public static final String RESPONSE = "response";

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
