package com.toozo.asteriumwebserver.gamelogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;
import com.toozo.asteriumwebserver.gamelogic.items.AbstractItem;

/**
 * @author Studio Toozo
 */
public class GameState {
	// ===== FIELDS =====
	/* Map of player auth token to character */
	private Game game;
	private Map<String, PlayerCharacter> playerCharacterMap;
	private Map<String, Boolean> playerReadyMap;
	private Collection<VictoryCondition> victoryConditions;
	private Inventory communalInventory;
	// ==================
	
	// ===== CONSTRUCTORS =====
	public GameState(Game game) {
		this.game = game;
		this.playerCharacterMap = new ConcurrentHashMap<String, PlayerCharacter>();
		this.playerReadyMap = new ConcurrentHashMap<String, Boolean>();
		this.victoryConditions = new ArrayList<VictoryCondition>();
		this.communalInventory = new Inventory();
	}
	// ========================
	
	// ===== GETTERS =====
	public PlayerCharacter getCharacter(final String auth) {
		return playerCharacterMap.get(auth);
	}
	
	/**
	 * @return a {@link Collection} of references to all complete 
	 * 		   {@link VictoryCondition}s in this GameState.
	 */
	public Collection<VictoryCondition> getCompleteVictoryConditions() {
		Collection<VictoryCondition> result = new ArrayList<VictoryCondition>();
		
		// Add all complete victory conditions
		for (VictoryCondition vc : this.victoryConditions) {
			if (vc.isComplete(this)) {
				result.add(vc);
			}
		}
		
		return result;
	}
	
	/**
	 * @return a {@link Collection} of references to this GameState's {@link VictoryCondition}s.
	 */
	public Collection<VictoryCondition> getVictoryConditions() {
		return this.victoryConditions;
	}
	
	/**
	 * Gets a {@link Collection} of the {@link PlayerCharacter}s in the game.
	 * WARNING: Modifications to this Collection will affect 
	 * 			the GameState's map of {@link PlayerCharacter}s.
	 * 
	 * @return Collection<Character> containing the game's Characters.
	 */
	public Collection<PlayerCharacter> getCharacters() {
		return this.playerCharacterMap.values();
	}
	
	// ===================
	
	// ===== METHODS =====
	public boolean allCharactersReady() {
		for (Boolean bool : playerReadyMap.values()) {
			if (!bool) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Toggles whether the {@link PlayerCharacter} belonging
	 * to the {@link Player} with authToken is ready or not.
	 * 
	 * @param authToken The auth token of the {@link Player}
	 */
	public synchronized boolean toggleReady(final String authToken) {
			Player player = this.game.getPlayer(authToken);
			boolean isReady = !playerReadyMap.get(authToken);
			this.playerReadyMap.put(authToken, isReady);
			game.executePhase();
			return isReady;
	}
	
	/**
	 * Add a {@link Player} to the {@link GameState} when they join the {@link Game}.
	 * 
	 * @param playerAuth The player's auth token
	 * @param character The new character
	 */
	public void addPlayer(final String playerAuth) {
		Player player = this.game.getPlayer(playerAuth);
		PlayerCharacter character = new PlayerCharacter();
		
		this.playerCharacterMap.put(playerAuth, character);
		this.playerReadyMap.put(playerAuth, false);
	}
	
	/**
	 * Add an item to the communal inventory.
	 * 
	 * @param item the {@link AbstractItem} which should be added to the communal {@link Inventory}.
	 */
	public void addCommunalItem(final AbstractItem item) {
		this.communalInventory.add(item);
	}
	// ===================

	public void setAllCharactersNotReady() {
		for(String auth : playerReadyMap.keySet()) {
			playerReadyMap.put(auth, false);
		}
	}
}
