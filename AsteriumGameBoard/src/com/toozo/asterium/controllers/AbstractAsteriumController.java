package com.toozo.asterium.controllers;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

public abstract class AbstractAsteriumController {

	private boolean isInitialized = false;

	private GameResources gameResources;

	private NodeNavigator nodeNavigator;

	public final void initialize(GameResources gameResources, NodeNavigator nodeNavigator) throws IllegalStateException {
		if (isInitialized) {
			throw new IllegalStateException("Initialization is being called on an already initialized AsteriumController.");
		} else {
			this.gameResources = gameResources;
			this.nodeNavigator = nodeNavigator;
			this.isInitialized = true;
		}
		setup();
	}
	
	protected abstract void setup();
	
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
