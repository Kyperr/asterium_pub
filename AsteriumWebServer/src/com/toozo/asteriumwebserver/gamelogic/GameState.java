package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.toozo.asteriumwebserver.gamelogic.Character;

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
	
	/**
	 * Gets a {@link Collection} of the {@link Character}s in the game.
	 * WARNING: Modifications to this Collection will affect the GameState's list of Characters.
	 * 
	 * @return Collection<Character> containing the game's Characters.
	 */
	public Collection<Character> getCharacters() {
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
	
	public Character getCharacter(final String auth) {
		return playerCharacterMap.get(auth);
	}
	
	/**
	 * Add a {@link Player} to the {@link GameState} when they join the {@link Game}.
	 * 
	 * @param playerAuth The player's auth token
	 * @param character The new character
	 */
	public void addPlayer(final String playerAuth) {
		Character character = new Character();
		
		this.playerCharacterMap.put(playerAuth, character);
		this.playerReadyMap.put(character, false);
	}
	
	public Collection<Character> getCharacters() {
		return this.playerCharacterMap.values();
	}
}
