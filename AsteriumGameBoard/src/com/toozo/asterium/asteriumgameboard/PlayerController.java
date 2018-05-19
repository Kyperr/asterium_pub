package com.toozo.asterium.asteriumgameboard;

import actiondata.SyncPlayerListRequestData.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller for the display of a player in the lobby.
 * @author Jenna
 *
 */
public class PlayerController {
	private PlayerData player;

    @FXML
    private Label nameLabel ;

    @FXML
    private Label readyLabel ;

    public void setPlayer(PlayerData player) {
        this.player = player;
        nameLabel.textProperty().unbind();
        if (player == null) {
            nameLabel.setText(null);
            readyLabel.setText(null);
        } else {
            nameLabel.setText(player.getName());
            readyLabel.setText(player.getName());;
        }
    }
}

