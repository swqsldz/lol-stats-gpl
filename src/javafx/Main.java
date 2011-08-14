package javafx;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.LoginScene;
import javafx.scene.MainScene;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.Sys;

public class Main extends Application {

	private static Stage stage;
    
    public static void main(String[] args) {
		Application.launch(Main.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) {
		// Se inicializa el sistema
		try {
			Sys.getInstance().updateGames();
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		stage = primaryStage;
		
        stage.setTitle("LoL Tips");
        stage.initStyle(StageStyle.UNDECORATED);
		
        stage.setScene(LoginScene.getLoginScene());
        stage.setVisible(true);
    }
	
	// For testing purposes - DELETE AFTER USE OR WE'LL BE DESTROYED (it hurts)
	public static void changeScene(SceneType newSceneType) {
		Scene newScene = null;
		
		switch(newSceneType) {
			case LOGIN: 
				newScene = LoginScene.getLoginScene();
				break;
			case MAIN:
				newScene = MainScene.getMainScene();
				break;
		}
		
		
		stage.setScene(newScene);
	}

	public static Stage getStage() {return stage;}
}
