package com.toozo.asterium.nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.toozo.asterium.util.GameResources;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MapPane extends Pane {
	
	private static final Double IMAGE_WIDTH = 800.0;
	
	private static final Double IMAGE_HEIGHT = 347.0;

	private Image image;
	
	private AnimationTimer backgroundAnim;
	
	private Canvas canvas;
	
	private List<Text> locationText;
	
	private Map<Integer, Function<Double, Double>> xTranslatorMap;
	
	private Map<Integer, Function<Double, Double>> yTranslatorMap;
	
	private GraphicsContext gc;
	
	private GameResources gameResources;
	
	public MapPane(GameResources gr) {
		super();
		gameResources = gr;
		this.image = new Image("/com/toozo/asterium/resources/shipNEW3.png", 1600, 900, true, true);
		canvas = new Canvas(getWidth(), getHeight());
		getChildren().add(canvas);
		widthProperty().addListener(e -> canvas.setWidth(getWidth()));
		heightProperty().addListener(e -> canvas.setHeight(getHeight()));
		init();
	}
	
	private void init() {
		
		locationText = new ArrayList<Text>();
		
//		mapXCoordinates();
//		mapYCoordinates();

		backgroundAnim = new AnimationTimer() {
            long lastUpdate = 0 ;
            
			
			@Override
			public void handle(long now) {//now is in nanos
				
				gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, getWidth(), getHeight());
		        gc.drawImage(image, 0, 0, getWidth(), getHeight());
//		        for(Integer position : gameResources.getLocationMap().keySet()) {
//					Double xLocation = xTranslatorMap.get(position).apply(getWidth());
//					Double yLocation = yTranslatorMap.get(position).apply(getHeight());
//					gc.setFill(Color.web("#4bf221"));
//					gc.fillText(gameResources.getLocationMap().get(position), 
//							xLocation, yLocation);
//				}
			}
		};

		backgroundAnim.start();		
	}
	
	public void endGame(String message) {
		backgroundAnim.stop();
		backgroundAnim = new AnimationTimer() {
            long lastUpdate = 0 ;
            
			
			@Override
			public void handle(long now) {//now is in nanos
				
				gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, getWidth(), getHeight());
		        gc.drawImage(image, 0, 0, getWidth(), getHeight());
		        gc.setFont(new Font("Arial", 80.0));
		        gc.setFill(Color.BLACK);
		        gc.fillText("GAME OVER", getWidth()/2, getHeight()/2);

			}
		};

		backgroundAnim.start();		
	}
	
//	private void mapXCoordinates() {
//		xTranslatorMap = new HashMap<Integer, Function<Double, Double>>();
//		// Control room
//		xTranslatorMap.put(1, width -> {return (15.0/320.0) * width;});//Valid
//		// 
//		xTranslatorMap.put(2, width -> {return (100.0/320.0) * width;});//Valid
//		xTranslatorMap.put(8, width -> {return (100.0/320.0) * width;});
//		xTranslatorMap.put(14, width -> {return (100.0/320.0) * width;});
//		xTranslatorMap.put(20, width -> {return (100.0/320.0) * width;});
//		
//		xTranslatorMap.put(3, width -> {return (136.0/320.0) * width;});
//		xTranslatorMap.put(9, width -> {return (136.0/320.0) * width;});
//		xTranslatorMap.put(15, width -> {return (136.0/320.0) * width;});
//		xTranslatorMap.put(21, width -> {return (136.0/320.0) * width;});
//		
//		xTranslatorMap.put(4, width -> {return (166.0/320.0) * width;});
//		xTranslatorMap.put(10, width -> {return (166.0/320.0) * width;});
//		xTranslatorMap.put(16, width -> {return (166.0/320.0) * width;});
//		xTranslatorMap.put(22, width -> {return (166.0/320.0) * width;});
//		
//		xTranslatorMap.put(5, width -> {return (188.0/320.0) * width;});
//		xTranslatorMap.put(11, width -> {return (188.0/320.0) * width;});
//		xTranslatorMap.put(17, width -> {return (188.0/320.0) * width;});
//		xTranslatorMap.put(23, width -> {return (188/320) * width;});
//		
//		xTranslatorMap.put(6, width -> {return (218.0/320.0) * width;});
//		xTranslatorMap.put(12, width -> {return (218.0/320.0) * width;});
//		xTranslatorMap.put(18, width -> {return (218.0/320.0) * width;});
//		xTranslatorMap.put(24, width -> {return (218.0/320.0) * width;});
//		
//		xTranslatorMap.put(7, width -> {return (240.0/320.0) * width;});
//		xTranslatorMap.put(13, width -> {return (240.0/320.0) * width;});
//		xTranslatorMap.put(19, width -> {return (240.0/320.0) * width;});
//		xTranslatorMap.put(25, width -> {return (240.0/320.0) * width;});
//		
//	}
//	
//	private void mapYCoordinates() {
//		yTranslatorMap = new HashMap<Integer, Function<Double, Double>>();
//		
//		yTranslatorMap.put(1, height -> {return 0.9 * height;});
//		
//		for (int i = 2; i < 8; ++i) {
//			yTranslatorMap.put(i, height -> {return (50.0/180.0) * height;});
//		}
//		
//		for (int i = 8; i < 14; i++) {
//			yTranslatorMap.put(1, height -> {return (64.0/180.0) * height;});
//		}
//		
//		for (int i = 14; i < 20; i++) {
//			yTranslatorMap.put(i, height -> {return (94.0/180.0) * height;});
//		}
//		
//		for (int i = 20; i < 26; i++) {
//			yTranslatorMap.put(i, height -> {return (116.0/180.0) * height;});
//		}
//		
//	}
}
