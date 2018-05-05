package gamelogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Studio Toozo
 */
public class GameState {

	/* Map of player auth token to character */
	private Map<String, Character> playerCharacterMap = new ConcurrentHashMap<String, Character>();

	private Map<Character, Boolean> playerReadyMap = new ConcurrentHashMap<Character, Boolean>();

	public GameState() {

	}

	public boolean allCharactersReady() {
		for (Boolean bool : playerReadyMap.values()) {
			if (!bool) {
				return false;
			}
		}
		return true;
	}
	
	public boolean toggleReady(final String authToken) {
		return !playerReadyMap.get(playerCharacterMap.get(authToken));
	}
}
