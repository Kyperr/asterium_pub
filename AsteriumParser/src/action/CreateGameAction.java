package action;

import java.util.Map;

import org.json.JSONObject;

public class CreateGameAction extends AbstractAction {
	
	public CreateGameAction(Map<String, Object> args) {
		super(AbstractAction.CREATE_GAME, args);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static CreateGameAction parseArgs(JSONObject jsonObj) {
		return null;
	}
	
}
