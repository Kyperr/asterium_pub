package com.toozo.asterium.controllers;

import java.io.IOException;
import java.io.UncheckedIOException;

import actiondata.SyncPlayerListRequestData.PlayerData;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

public class PlayerListCell {

    private final Pane playerView ;
    private final PlayerController playerController ;

    public PlayerListCell(PlayerData data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/toozo/asterium/fxml/player.fxml"));
            playerView = loader.load();
            playerController = loader.getController();
            updateItem(data);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }

    protected void updateItem(PlayerData item) {
        playerController.setName(item.getName());
        playerController.setReadyStatus(item.getReadyStatus());
    }
    
    public Pane getPlayerView() {
    	return playerView;
    }
    
    
}