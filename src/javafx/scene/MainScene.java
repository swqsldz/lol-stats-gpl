package javafx.scene;

import javafx.DragStageEventHandler;
import javafx.Main;
import javafx.button.Button;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import database.Database;
import javafx.SceneType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import log.ds.Summoner;
import system.Sys;

public class MainScene {
	
	private static final String BACKGROUND_IMG_PATH = "src/resources/background_main.jpg";
	private static final String SUMMONER_ICONS_PATH = "src/resources/summonerIcons/";
	
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
		root.getChildren().add(getSummonerInfo());
		root.getChildren().add(getGeneralStats());
		
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
		
		Button closeB = Button.getCloseButton();
		closeB.setLayoutX(22);
		
		
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
		
		Button tipsB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_tips_normal.png", "button_tips_hover.png", "button_tips_pressed.png");
		tipsB.setLayoutX(88);
		
		Button preferencesB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_preferences_normal.png", "button_preferences_hover.png", "button_preferences_pressed.png");
		preferencesB.setLayoutX(176);
		
		Button backB = new Button(new EventHandler() {
			public void handle(Event event) {
				Main.changeScene(SceneType.LOGIN);
			}
		}, "button_back_normal.png", "button_back_hover.png", "button_back_pressed.png");
		backB.setLayoutX(264);
		
		topButtons.getChildren().add(statsB);
		topButtons.getChildren().add(tipsB);
		topButtons.getChildren().add(preferencesB);
		topButtons.getChildren().add(backB);
		topButtons.setLayoutX(386);
		topButtons.setLayoutY(64);
		
		return topButtons;
	}
	
	private static Node getSummonerInfo() {
		Group summonerInfo = new Group();
		
		String loggedSummonerName = Sys.getInstance().getLoggedSummonerName();
		Summoner summoner = Database.getInstance().getSummoners().get(loggedSummonerName);
		Image summonerIconImg = new Image(SUMMONER_ICONS_PATH + "si" + summoner.getSummonerIcon() + ".jpg");
		ImageView summonerIcon = new ImageView(summonerIconImg);
		summonerIcon.setFitHeight(61);
		summonerIcon.setFitWidth(61);
		
		Text summonerName = new Text(summoner.getSummonerName());
		summonerName.setFill(new Color(0.9, 0.9, 1.0, 1.0));
		summonerName.setFont(new Font(20));
		summonerName.setLayoutX(75);
		summonerName.setLayoutY(20);
		
		summonerInfo.getChildren().add(summonerIcon);
		summonerInfo.getChildren().add(summonerName);
		summonerInfo.setLayoutX(32);
		summonerInfo.setLayoutY(47);
		
		return summonerInfo;
	}
	
	private static Node getGeneralStats() {
		Group generalStats = new Group();
		
		Button normalB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_normal_normal.png", "button_normal_hover.png", "button_normal_pressed.png");
		
		Button premade3x3B = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_premade3x3_normal.png", "button_premade3x3_hover.png", "button_premade3x3_pressed.png");
		premade3x3B.setLayoutX(87);
		
		Button premade5x5B = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_premade5x5_normal.png", "button_premade5x5_hover.png", "button_premade5x5_pressed.png");
		premade5x5B.setLayoutX(174);
		
		Button soloB = new Button(new EventHandler() {
			public void handle(Event event) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		}, "button_solo_normal.png", "button_solo_hover.png", "button_solo_pressed.png");
		soloB.setLayoutX(261);
		
		generalStats.getChildren().add(normalB);
		generalStats.getChildren().add(premade3x3B);
		generalStats.getChildren().add(premade5x5B);
		generalStats.getChildren().add(soloB);
		generalStats.setLayoutX(45);
		generalStats.setLayoutY(180);
		
		return generalStats;
	}
}
