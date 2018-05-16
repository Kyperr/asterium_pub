package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.toozo.asteriumwebserver.gamelogic.PlayerCharacter;

/**
 * 
 * @author Studio Toozo
 */
public class GameState {

	/* Map of player auth token to character */
	private Map<String, PlayerCharacter> playerCharacterMap = new ConcurrentHashMap<String, PlayerCharacter>();

	private Map<PlayerCharacter, Boolean> playerReadyMap = new ConcurrentHashMap<PlayerCharacter, Boolean>();

	public GameState() {

	}
	
	/**
	 * Gets a {@link Collection} of the {@link PlayerCharacter}s in the game.
	 * WARNING: Modifications to this Collection will affect the GameState's list of Characters.
	 * 
	 * @return Collection<Character> containing the game's Characters.
	 */
	public Collection<PlayerCharacter> getCharacters() {
		return this.playerCharacterMap.values();
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
	
	public PlayerCharacter getCharacter(final String auth) {
		return playerCharacterMap.get(auth);
	}
	
	/**
	 * Add a {@link Player} to the {@link GameState} when they join the {@link Game}.
	 * 
	 * @param playerAuth The player's auth token
	 * @param character The new character
	 */
	public void addPlayer(final String playerAuth) {
		PlayerCharacter character = new PlayerCharacter();
		
		this.playerCharacterMap.put(playerAuth, character);
		this.playerReadyMap.put(character, false);
	}
}
