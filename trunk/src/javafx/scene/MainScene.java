package javafx.scene;

import javafx.DragStageEventHandler;
import javafx.Main;
import javafx.button.Button;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainScene {
	
	private static final String BACKGROUND_IMG_PATH = "src/resources/background_main.jpg";
	
	private static Scene instance;
	
	private static Image backgroundImg;
	
	private static Scene getLoginScene() {
		Group root = new Group();
		Scene scene = new Scene(root, 1024, 783);
        DragStageEventHandler eventHandler = new DragStageEventHandler(Main.getStage());
        scene.setOnMousePressed(eventHandler);
        scene.setOnMouseDragged(eventHandler);
        root.getChildren().add(new ImageView(getBackgroundImg()));
		root.getChildren().add(getTopMenu());
		root.getChildren().add(getTopButtons());
		
		return scene;
	}
	
	public static Scene getInstance() {
		if (instance == null)
			instance = getLoginScene();
		return instance;
	}
	
	private static Image getBackgroundImg() {
        if (backgroundImg == null)
            backgroundImg = new Image(BACKGROUND_IMG_PATH);
        return backgroundImg;
    }
	
	private static Node getTopMenu() {
		Group topMenu = new Group();
		
		Button minimizeB = Button.getMinimizeButton();
		minimizeB.setLayoutX(0);
		minimizeB.setLayoutY(0);
		
		Button closeB = Button.getCloseButton();
		closeB.setLayoutX(22);
		closeB.setLayoutY(0);
		
		
		topMenu.getChildren().add(minimizeB);
        topMenu.getChildren().add(closeB);
        topMenu.setLayoutX(975);
        topMenu.setLayoutY(3);
		return topMenu;
	}
	
	private static Node getTopButtons() {
		Group topButtons = new Group();
		
		Button statsB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_stats_normal.png", "button_stats_hover.png", "button_stats_pressed.png");
		statsB.setLayoutX(0);
		statsB.setLayoutY(0);
		
		Button tipsB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_tips_normal.png", "button_tips_hover.png", "button_tips_pressed.png");
		tipsB.setLayoutX(88);
		tipsB.setLayoutY(0);
		
		Button preferencesB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_preferences_normal.png", "button_preferences_hover.png", "button_preferences_pressed.png");
		preferencesB.setLayoutX(176);
		preferencesB.setLayoutY(0);
		
		Button backB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_back_normal.png", "button_back_hover.png", "button_back_pressed.png");
		backB.setLayoutX(264);
		backB.setLayoutY(0);
		
		topButtons.getChildren().add(statsB);
		topButtons.getChildren().add(tipsB);
		topButtons.getChildren().add(preferencesB);
		topButtons.getChildren().add(backB);
		topButtons.setLayoutX(386);
		topButtons.setLayoutY(64);
		
		return topButtons;
	}
}
