package javafx.scene;

import javafx.DragStageEventHandler;
import javafx.Hyperlink;
import javafx.Main;
import javafx.SceneType;
import javafx.button.Button;
import javafx.dialog.ErrorDialog;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import system.Sys;
import options.Options;

public class LoginScene {
	
	private final static String BACKGROUND_IMG_PATH = "src/resources/background_login.jpg";
	
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
        root.getChildren().add(getLoginMenu());
		
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
    
    private static Node getLoginMenu() {
        Group loginMenu = new Group();
        
        TextBox usernameTB = new TextBox();
		boolean rememberUsername = Options.getInstance().getRememberUsername();
		if (rememberUsername)
			usernameTB.setText(Options.getInstance().getLastLoggedUsername());
        usernameTB.setColumns(36);
        
        CheckBox rememberChB = new CheckBox();
		rememberChB.setSelected(rememberUsername);
		rememberChB.setOnMouseClicked(new EventHandler(){
			public void handle(Event event) {
				Options.getInstance().setRememberUsername(((CheckBox)event.getSource()).isSelected());
			}
		});
        rememberChB.setLayoutX(0);
        rememberChB.setLayoutY(40);
        
        Text rememberText = new Text("Remember username");
        rememberText.setFill(new Color(0.9, 0.9, 1.0, 1.0));
        rememberText.setLayoutX(20);
        rememberText.setLayoutY(51);
        
        Button loginB = getLoginButton(usernameTB);
        loginB.setLayoutX(156);
        loginB.setLayoutY(35);
        
        Text visitText = new Text("Visit our webpage for more information about");
        visitText.setFill(new Color(0.9, 0.9, 1.0, 1.0));
        visitText.setLayoutX(10);
        visitText.setLayoutY(110);
        
        Text visitHL = Hyperlink.createHyperlink("League of Legends Stats", "www.leagueoflegends.com");
        visitHL.setFont(new Font(16));
        visitHL.setFill(new Color(0.2, 0.5, 1.0, 1.0));
        visitHL.setLayoutX(40);
        visitHL.setLayoutY(135);
        
        loginMenu.getChildren().add(usernameTB);
        loginMenu.getChildren().add(rememberChB);
        loginMenu.getChildren().add(rememberText);
        loginMenu.getChildren().add(loginB);
        loginMenu.getChildren().add(visitText);
        loginMenu.getChildren().add(visitHL);
        loginMenu.setLayoutX(689);
        loginMenu.setLayoutY(257);
        return loginMenu;
    }
	
	private static Button getLoginButton(final TextBox usernameTB) {
		return new Button(new EventHandler() {
			public void handle(Event event) {
				Sys sys = Sys.getInstance();
				if (!sys.login(usernameTB.getRawText())) {
					ErrorDialog.showErrorDialog("The username you entered is incorrect.");
				}
				else {
					if (!Options.getInstance().getRememberUsername())
						usernameTB.setText("");
					Main.changeScene(SceneType.MAIN);
				}
			}
		}, "button_login_normal.png", "button_login_hover.png", "button_login_pressed.png");
	}
}
