package com.toozo.asteriumwebserver.gamelogic;


/**
 * {@link Client} class which allows the server to keep track of {@link Client} that are part of a {@link Game}.  
 * 
 * @author Studio Toozo
 */
public class Client {

	/*
	 * The authToken for this client.
	 */
	private String authToken;

	/**
	 * Creates and returns a {@link Client} object. Assigns the given authToken to the client.
	 * @param authToken The authToken to assign to the {@link Client}.
	 */
	protected Client(String authToken) {
		this.authToken = authToken;
	}
	
	/**
	 * @return The {@link Client}'s current authToken.
	 */
	public String getAuthToken() {
		return authToken;
	}

}
