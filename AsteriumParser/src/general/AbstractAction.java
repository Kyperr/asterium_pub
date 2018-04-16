package general;

import java.util.Map;

public abstract class AbstractAction implements Runnable{

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
