package com.toozo.asteriumwebserver.gamelogic;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link Player} Clients are users interacting with the server from a mobile platform. 
 * Many may join one game.
 * 
 * @author Studio Toozo
 */
public class Player extends Client {
	// ===== CONSTANTS =====
	public static final Set<String> NAME_BLACKLIST;
	static {
		Set<String> substrings = new HashSet<String>();
		
		substrings.add("censor");
		
		NAME_BLACKLIST = Collections.unmodifiableSet(substrings);
	}
	// =====================
	
	
	
	/*
	 * The name associated with a Player
	 */
	private final String playerName;
	
	/**
	 * Creates and returns a {@link Player}.
	 * 
	 * @param session The {@link Session} associated with the {@link Player}
	 * @param playerName The name associated with the {@link Player}
	 */
	public Player(final String authToken, final String playerName) {
		super(authToken);
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public boolean nameValid() {
		String name = this.getPlayerName();
		
		// Check for any substrings in blacklist
		for (String substring : Player.NAME_BLACKLIST) {
			if (name.toLowerCase().contains(substring.toLowerCase())) {
				return false;
			}
		}
		
		// Check for non-letters
		for (char character : name.toCharArray()) {
			if (!Character.isLetter(character)) {
				return false;
			}
		}
		
		return true;
	}
}
