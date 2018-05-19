package com.toozo.asterium.asteriumgameboard;

import java.io.IOException;
import java.io.UncheckedIOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

public class LocationListCell extends ListCell<String> {
	private final Pane locationView ;
    private final LocationController locationController ;

    public LocationListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("location.fxml"));
            locationView = loader.load();
            locationController = loader.getController();
            setGraphic(locationView);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        locationController.setLocation(item);
    }
}
