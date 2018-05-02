package view;

import actiondata.CreateGameRequestData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.ClientConnectionHandler;
import message.Request;
 
public class GameBoardApp extends Application {
	
	static Text text = new Text();
	
	boolean run = true;
	
	
	
	public static void main(String[] args) {

		ClientConnectionHandler ccHandler = new ClientConnectionHandler("localhost", 25632);
		
		CreateGameRequestData cgaData = new CreateGameRequestData();
		
		Request request = new Request(cgaData);
		
		String msg = request.jsonify().toString();
		
		ccHandler.sendJSON(msg, (message) -> text.setText(message.jsonify().toString()));
		
		
		 launch(args);
		
	}
	
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Asterium GameBoard");
        StackPane root = new StackPane();
        root.getChildren().add(text);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        
    }
}

