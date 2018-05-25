package com.toozo.asterium.controllers;

import javax.resource.spi.IllegalStateException;

import com.toozo.asterium.util.GameResources;
import com.toozo.asterium.util.NodeNavigator;

public interface AsteriumController {

	
	public void initialize(GameResources gameResources, NodeNavigator nodeNavigator) throws IllegalStateException;
	
}
