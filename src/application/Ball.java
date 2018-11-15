package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle{
	
	double radius;
	Color color;
	
	public Ball(double radius, Color color) {
		super(radius, color);
	}
}
