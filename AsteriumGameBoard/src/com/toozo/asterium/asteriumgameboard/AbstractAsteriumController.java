package com.toozo.asterium.asteriumgameboard;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

public class AbstractAsteriumController {

	private boolean isInitialized = false;

	private GameResources gameResources;

	private NodeNavigator nodeNavigator;

	public final void initialize(GameResources gameResources, NodeNavigator nodeNavigatir) throws IllegalStateException {
		if (isInitialized) {
			throw new IllegalStateException("Initialization is being called on an already initialized AsteriumController.");
		} else {
			this.gameResources = gameResources;
			this.nodeNavigator = nodeNavigatir;
			this.isInitialized = true;
		}
	}
	
	protected GameResources getGameResources() throws IllegalStateException {
		if(isInitialized) {
			return this.gameResources;
		} else {
			throw new IllegalStateException("Trying to call getGameResources() when this Controller has not been initialized yet.");
		}
	}
	
	protected NodeNavigator getNodeNavigator() throws IllegalStateException {
		if(isInitialized) {
		return this.nodeNavigator;
		} else {
			throw new IllegalStateException("Trying to call getNodeNavigator() when this Controller has not been initialized yet.");			
		}
	}

}
