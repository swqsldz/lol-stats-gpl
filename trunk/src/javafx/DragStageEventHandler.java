package javafx;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DragStageEventHandler implements EventHandler {
	private double px;
	private double py;
	private Stage stage;

	public DragStageEventHandler(Stage stage) {
		this.stage = stage;
	}

	public void handle(Event event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			px = ((MouseEvent)event).getX();
			py = ((MouseEvent)event).getY();
		}
		else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			stage.setX(stage.getX() + (((MouseEvent)event).getX() - px) / 2.0);
			stage.setY(stage.getY() + (((MouseEvent)event).getY() - py) / 2.0);
		}
	}
}
