package com.toozo.asterium.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class MapPane extends Pane {
	
	private final Double IMAGE_WIDTH = getWidth() * 1.5; 
	private final Double IMAGE_HEIGHT = getHeight() * 1.5;
	
	private final Double BACKGROUND_X = -getWidth()*.3;
	private final Double BACKGROUND_Y = -getHeight()*.12;

	private Image image;
	
	private AnimationTimer backgroundAnim;
	
	private Canvas canvas;
	
	private List<Text> locationText;
	
	private Map<Integer, String> locationMap;
	
	private Map<Integer, Double> xCoordinateMap;
	
	private Map<Integer, Double> yCoordinateMap;
	
	private GraphicsContext gc;
	
	public MapPane(Image image, Map<Integer, String> locationMap) {
		super();
		mapXCoordinates();
		mapYCoordinates();
		this.image = image;
		this.locationMap = locationMap;
		canvas = new Canvas(getWidth(), getHeight());
		getChildren().add(canvas);
		widthProperty().addListener(e -> canvas.setWidth(getWidth()));
		heightProperty().addListener(e -> canvas.setHeight(getHeight()));
		init();
	}
	
	private void init() {
		
		locationText = new ArrayList<Text>();

		backgroundAnim = new AnimationTimer() {
            long lastUpdate = 0 ;
			
			@Override
			public void handle(long now) {//now is in nanos

				gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, getWidth(), getHeight());
		        gc.drawImage(image, -getWidth()*.3, -getHeight()*.15, getWidth() * 1.5, getHeight() * 1.5);
		        
		        for(Integer position : locationMap.keySet()) {
					Double xLocation = xCoordinateMap.get(position);
					Double yLocation = yCoordinateMap.get(position);
					gc.fillText(locationMap.get(position), xLocation, yLocation);
				}
			}
		};

		backgroundAnim.start();		
	}
	
	
	private void mapXCoordinates() {
		xCoordinateMap = new HashMap<Integer, Double>();
		// Control room
		xCoordinateMap.put(1, 52/320 * IMAGE_WIDTH + BACKGROUND_X);
		// 
		xCoordinateMap.put(2, (114/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(3, (136/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(4, (166/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(5, (188/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(6, (218/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(7, (240/320) * IMAGE_WIDTH + BACKGROUND_X);
		
		xCoordinateMap.put(8, (114/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(9, (136/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(10, (166/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(11, (188/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(12, (218/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(13, (240/320) * IMAGE_WIDTH + BACKGROUND_X);
		
		xCoordinateMap.put(14, (114/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(15, (136/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(16, (166/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(17, (188/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(18, (218/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(19, (240/320) * IMAGE_WIDTH + BACKGROUND_X);
		
		xCoordinateMap.put(20, (114/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(21, (136/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(22, (166/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(23, (188/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(24, (218/320) * IMAGE_WIDTH + BACKGROUND_X);
		xCoordinateMap.put(25, (240/320) * IMAGE_WIDTH + BACKGROUND_X);
	}
	
	private void mapYCoordinates() {
		yCoordinateMap = new HashMap<Integer, Double>();
		
		yCoordinateMap.put(1, (64/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(2, (42/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(3, (42/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(4, (42/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(5, (42/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(6, (42/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(7, (42/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		
		yCoordinateMap.put(8, (64/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(9, (64/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(10, (64/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(11, (64/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(12, (64/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(13, (64/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		
		yCoordinateMap.put(14, (94/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(15, (94/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(16, (94/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(17, (94/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(18, (94/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(19, (94/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		
		
		yCoordinateMap.put(20, (116/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(21, (116/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(22, (116/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(23, (116/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(24, (116/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		yCoordinateMap.put(25, (116/180) * IMAGE_HEIGHT + BACKGROUND_Y);
		
	}
}
