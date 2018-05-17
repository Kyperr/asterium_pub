package com.toozo.asterium.asteriumgameboard;

import java.io.IOException;
import java.io.UncheckedIOException;

import com.toozo.asterium.model.Player;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

public class PlayerListCell extends ListCell<Player> {

    private final Pane playerView ;
    private final PlayerController playerController ;

    public PlayerListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("player.fxml"));
            playerView = loader.load();
            playerController = loader.getController();
            setGraphic(playerView);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }

    @Override
    protected void updateItem(Player item, boolean empty) {
        super.updateItem(item, empty);
        playerController.setPlayer(item);
    }
    
    
}