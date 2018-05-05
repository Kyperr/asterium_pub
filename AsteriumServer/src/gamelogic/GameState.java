package gamelogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

	/**
	 * 
	 * @author Jenna, Danie
	 */
	public class GameState {
		
		private Map<Player, Character> playerCharacterMap = new ConcurrentHashMap<Player, Character>();
		
		private Map<Character, Boolean> playerReadyMap = new ConcurrentHashMap<Character, Boolean>();
		
		public GameState() {
			
		}
		
		public boolean allCharactersReady() {
			for (Boolean bool : playerReadyMap.values()) {
				if(!bool) {
					return false;
				}
			}
			return true;
		}
	}
