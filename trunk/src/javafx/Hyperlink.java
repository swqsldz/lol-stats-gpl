package javafx;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.text.Text;

public class Hyperlink {
	public static Text createHyperlink(String text, final String uri, final Scene scene) {
		Text hyperlink = new Text(text);

        hyperlink.setOnMouseEntered(new EventHandler() {
            public void handle(Event event) {
                scene.setCursor(Cursor.HAND);
            }
        });
        hyperlink.setOnMouseExited(new EventHandler() {
            public void handle(Event event) {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
        hyperlink.setOnMouseClicked(new EventHandler() {
            public void handle(Event event) {
                try {
                    java.awt.Desktop.getDesktop().browse(new URI(uri));
                } catch (URISyntaxException ex) { /*TODO*/ }
				  catch (IOException ex) { /*TODO*/ }
            }
        });
		
		return hyperlink;
	}
}
