package com.toozo.asterium.asteriumgameboard;

import java.util.ArrayList;
import java.util.List;

import com.toozo.asterium.util.GameResources;

import actiondata.SyncPlayerListRequestData.PlayerData;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * Controller for the display of player components in the lobby.
 * @author Jenna
 *
 */
public class PlayerListController extends AbstractAsteriumController {

	List<PlayerListCell> playerCells = new ArrayList<PlayerListCell>();
	
	@FXML
	private VBox playerList;
	
	// Add player views to the vbox
	public void updatePlayers(List<PlayerData> playerData) {
		for (int i = 0; i < playerData.size(); i++) {
			if (i < playerCells.size()) {
				playerCells.get(i).updateItem(playerData.get(i));
			} else {
				PlayerListCell cell = new PlayerListCell(playerData.get(i));
				playerCells.add(cell);
				playerList.getChildren().add(cell.getPlayerView());
			}
		}
	}

	@Override
	protected void setup() {
	}
    
}

