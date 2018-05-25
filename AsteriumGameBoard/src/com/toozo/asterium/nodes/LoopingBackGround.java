package com.toozo.asterium.nodes;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class LoopingBackGround extends Pane {

	private final Canvas canvas;

	private Image backGroundImage;

	private AnimationTimer backgroundAnim;

	private Double translateX = 0.0;

	private Double panSpeed;
	
	public LoopingBackGround(Image backgroundImage, Double panSpeed) {
		super();
		this.backGroundImage = backgroundImage;
		this.panSpeed = panSpeed;
		canvas = new Canvas(getWidth(), getHeight());
		getChildren().add(canvas);
		widthProperty().addListener(e -> canvas.setWidth(getWidth()));
		heightProperty().addListener(e -> canvas.setHeight(getHeight()));
		init();
	}

	private void init() {

		backgroundAnim = new AnimationTimer() {
            long lastUpdate = 0 ;
			
			@Override
			public void handle(long now) {//now is in nanos
                if (lastUpdate == 0) {
                    lastUpdate = now ;
                    return ;
                }
                //Calculate difference in time so we can have regular updates.
                
                long deltaNan = (now - lastUpdate);
                
                double deltaSec = deltaNan / 1000000000.0;
                
                lastUpdate = now;

		        GraphicsContext gc = canvas.getGraphicsContext2D();

		        gc.clearRect(0, 0, getWidth(), getHeight());
		        
				//Speed, this should interp with time.
				translateX += panSpeed * deltaSec;

				//If one image.getWidth() distance off screen.
				if(translateX > backGroundImage.getWidth()) {
					translateX = -backGroundImage.getWidth();
				}
				
		        // Paint main instance of the image.:
		        gc.drawImage(backGroundImage, translateX, 0, backGroundImage.getWidth(), getHeight());
		        
		        //If less than a screen's width worth of image left, the next image.
		        if(translateX >= 0 ){
		        	double remaining = translateX - (backGroundImage.getWidth());
			        gc.drawImage(backGroundImage, remaining, 0, backGroundImage.getWidth(), getHeight());				
				} else if(translateX + backGroundImage.getWidth() < getWidth()) {
		        	double remaining = translateX + (backGroundImage.getWidth());
			        gc.drawImage(backGroundImage, remaining, 0, backGroundImage.getWidth(), getHeight());					
				}
		        
			}
		};

		backgroundAnim.start();
	}
}
