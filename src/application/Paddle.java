package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
	
	int length;
	int width;
	Color color;
	
	public Paddle(int length, int width, Color color) {
		super(length, width, color);
	}
}
