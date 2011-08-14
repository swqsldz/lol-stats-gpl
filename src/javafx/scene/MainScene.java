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
	
	private final static String SEPARATOR = System.getProperty("file.separator");
	private static final String BACKGROUND_IMG_PATH = "src" + SEPARATOR + "resources" + SEPARATOR + "background_main.jpg";
	private static final String SUMMONER_ICONS_PATH = "src" + SEPARATOR + "resources" + SEPARATOR + "summonerIcons" + SEPARATOR;
	
	private static Image backgroundImg;
	
	private static Button statsB;
	private static Button tipsB;
	private static Button preferencesB;
	private static Button backB;
	private static Button normalB;
	private static Button premade3x3B;
	private static Button premade5x5B;
	private static Button soloB;
	
	public static Scene getMainScene() {
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
	
	private static Image getBackgroundImg() {
        if (backgroundImg == null)
            backgroundImg = new Image(BACKGROUND_IMG_PATH);
        return backgroundImg;
    }
	
	private static Button getStatsButton() {
		if (statsB == null)
			statsB = new Button(new EventHandler() {
				public void handle(Event event) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			}, "button_stats_normal.png", "button_stats_hover.png", "button_stats_pressed.png");
		return statsB;
	}
	
	private static Button getTipsButton() {
		if (tipsB == null) {
			tipsB = new Button(new EventHandler() {
				public void handle(Event event) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			}, "button_tips_normal.png", "button_tips_hover.png", "button_tips_pressed.png");
			tipsB.setLayoutX(88);
		}
		return tipsB;
	}
	
	private static Button getPreferencesButton() {
		if (preferencesB == null) {
			preferencesB = new Button(new EventHandler() {
				public void handle(Event event) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			}, "button_preferences_normal.png", "button_preferences_hover.png", "button_preferences_pressed.png");
			preferencesB.setLayoutX(176);
		}
		return preferencesB;
	}
		
	private static Button getBackButton() {
		if (backB == null) {
			backB = new Button(new EventHandler() {
				public void handle(Event event) {
					Main.changeScene(SceneType.LOGIN);
				}
			}, "button_back_normal.png", "button_back_hover.png", "button_back_pressed.png");
			backB.setLayoutX(264);
		}
		return backB;
	}
	
	private static Button getNormalButton() {
		if (normalB == null)
			normalB = new Button(new EventHandler() {
				public void handle(Event event) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			}, "button_normal_normal.png", "button_normal_hover.png", "button_normal_pressed.png");
		return normalB;
	}
	
	private static Button getPremade3x3Button() {
		if (premade3x3B == null) {
			premade3x3B = new Button(new EventHandler() {
				public void handle(Event event) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			}, "button_premade3x3_normal.png", "button_premade3x3_hover.png", "button_premade3x3_pressed.png");
			premade3x3B.setLayoutX(87);
		}
		return premade3x3B;
	}
	
	private static Button getPremade5x5Button() {
		if (premade5x5B == null) {
			premade5x5B = new Button(new EventHandler() {
				public void handle(Event event) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			}, "button_premade5x5_normal.png", "button_premade5x5_hover.png", "button_premade5x5_pressed.png");
			premade5x5B.setLayoutX(174);
		}
		return premade5x5B;
	}
		
	private static Button getSoloButton() {
		if (soloB == null) {
			soloB = new Button(new EventHandler() {
				public void handle(Event event) {
					throw new UnsupportedOperationException("Not supported yet.");
				}
			}, "button_solo_normal.png", "button_solo_hover.png", "button_solo_pressed.png");
			soloB.setLayoutX(261);
		}
		return soloB;
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
		
		topButtons.getChildren().add(getStatsButton());
		topButtons.getChildren().add(getTipsButton());
		topButtons.getChildren().add(getPreferencesButton());
		topButtons.getChildren().add(getBackButton());
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
		
		Text summonerLevel = new Text("Level " + summoner.getLevel());
		summonerLevel.setFill(new Color(0.9, 0.9, 1.0, 1.0));
		summonerLevel.setFont(new Font(20));
		summonerLevel.setLayoutX(75);
		summonerLevel.setLayoutY(53);
		
		summonerInfo.getChildren().add(summonerIcon);
		summonerInfo.getChildren().add(summonerName);
		summonerInfo.getChildren().add(summonerLevel);
		summonerInfo.setLayoutX(32);
		summonerInfo.setLayoutY(47);
		
		return summonerInfo;
	}
	
	private static Node getGeneralStats() {
		Group generalStats = new Group();
		
		generalStats.getChildren().add(getNormalButton());
		generalStats.getChildren().add(getPremade3x3Button());
		generalStats.getChildren().add(getPremade5x5Button());
		generalStats.getChildren().add(getSoloButton());
		generalStats.setLayoutX(45);
		generalStats.setLayoutY(180);
		
		return generalStats;
	}
}
