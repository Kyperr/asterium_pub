package gamelogic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class GameManager {

	private static GameManager gameManager;
	
	private Map<String, Game> gameMap = new ConcurrentHashMap<String, Game>();
	
	public static synchronized GameManager getInstance() {
		if (gameManager == null) {
			gameManager = new GameManager();
		} 
		return gameManager;
	}
	
	private GameManager() {
		// Empty on purpose
	}
	
	public void registerGame(Game game) {
		gameMap.put(game.getLobbyID(), game);
	}

	public boolean isLobbyIDUsed(String lobbyID) {
		return gameMap.containsKey(lobbyID);
	}
	
	public Game getGame(String lobbyID) {
		return gameMap.get(lobbyID);
	}
}
