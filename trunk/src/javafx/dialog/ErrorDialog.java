package javafx.dialog;

import javafx.button.Button;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Añadir, si es posible, que se cierre el menu pulsando ENTER
public class ErrorDialog {
	
	private static Image backgroundErrorImg;
	
	public static void showErrorDialog(String errorMsg) {
		final Stage dialog = new Stage();
		dialog.initStyle(StageStyle.TRANSPARENT);
		
		ImageView backImg = new ImageView(getBackgroundErrorImg());
		
		Text errorText = new Text("Error");
		errorText.setFont(new Font(16));
        errorText.setFill(new Color(0.9, 0.9, 1.0, 1.0));
		errorText.setLayoutX(25);
		errorText.setLayoutY(28);
		
        Text msgText = new Text(errorMsg);
		msgText.setWrappingWidth(220);
		msgText.setTextAlignment(TextAlignment.CENTER);
		msgText.setFont(new Font(16));
        msgText.setFill(new Color(0.9, 0.9, 1.0, 1.0));
		msgText.setLayoutX(50);
		msgText.setLayoutY(70);
		
		Button acceptButton = new Button(new EventHandler() {
                    public void handle(Event event) {
                            dialog.close();
                    }
		}, "button_accept_normal.png", "button_accept_hover.png", "button_accept_pressed.png");
		acceptButton.setLayoutX(115);
		acceptButton.setLayoutY(140);

        Group root = new Group();
		root.getChildren().add(backImg);
		root.getChildren().add(errorText);
		root.getChildren().add(msgText);
		root.getChildren().add(acceptButton);
		
        Scene dialogScene = new Scene(root);
        dialog.setScene(dialogScene);
        dialog.setVisible(true);
	}
	
	public static Image getBackgroundErrorImg() {
        if (backgroundErrorImg == null)
            backgroundErrorImg = new Image("src/resources/error_menu.png");
        return backgroundErrorImg;
    }
}
