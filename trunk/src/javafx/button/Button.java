package javafx.button;

import javafx.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import system.Sys;

public class Button extends ImageView {
    
    private static final String IMAGES_PATH = "src/resources/buttons/";
    
    private Image normalImg;
    private Image hoverImg;
    private Image pressedImg;

    public Button(EventHandler clickEvent, String normalImgPath, String hoverImgPath, String pressedImgPath) {	
        normalImg = new Image(IMAGES_PATH + normalImgPath);
        hoverImg = new Image(IMAGES_PATH + hoverImgPath);
        pressedImg = new Image(IMAGES_PATH + pressedImgPath);
        
        setImage(normalImg);
        
        setOnMouseEntered(new EventHandler() {
			@Override
            public void handle(Event event) {
                ((ImageView)((MouseEvent)event).getSource()).setImage(hoverImg);
            }
        });
        setOnMouseExited(new EventHandler() {
			@Override
            public void handle(Event event) {
                ((ImageView)((MouseEvent)event).getSource()).setImage(normalImg);
            }
        });
        setOnMouseReleased(new EventHandler() {
			@Override
            public void handle(Event event) {
                if (((ImageView)((MouseEvent)event).getSource()).isHover())
                    ((ImageView)((MouseEvent)event).getSource()).setImage(hoverImg);
                else
                    ((ImageView)((MouseEvent)event).getSource()).setImage(normalImg);
            }
        });
        setOnMousePressed(new EventHandler() {
			@Override
            public void handle(Event event) {
                ((ImageView)((MouseEvent)event).getSource()).setImage(pressedImg);
            }
        });
        
		setOnMouseClicked(clickEvent);
    }
	
	public static Button getMinimizeButton() {
		return new Button(new EventHandler() {
			public void handle(Event event) {
				Main.getStage().setIconified(true);
			}
		}, "minimize_button_normal.png", "minimize_button_hover.png", "minimize_button_hover.png");
	}
	
	public static Button getCloseButton() {
		return new Button(new EventHandler() {
			public void handle(Event event) {
				Sys sys = Sys.getInstance();
				sys.saveFiles();
				System.exit(0);
			}
        }, "close_button_normal.png", "close_button_hover.png", "close_button_hover.png");
	}

}
