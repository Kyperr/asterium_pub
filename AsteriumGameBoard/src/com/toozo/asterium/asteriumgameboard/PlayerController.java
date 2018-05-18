package com.toozo.asterium.asteriumgameboard;

import com.toozo.asterium.model.Player;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlayerController {
	private Player player;

    @FXML
    private Label nameLabel ;

    @FXML
    private Label readyLabel ;

    public void setPlayer(Player player) {
        this.player = player;
        nameLabel.textProperty().unbind();
        if (player == null) {
            nameLabel.setText(null);
            readyLabel.setText(null);
        } else {
            nameLabel.textProperty().bind(player.nameProperty());;
            readyLabel.textProperty().bind(player.readyProperty().asString());
        }
    }
}

