package partalex.homework_one.objects;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Hole extends Circle {
	private int points;

	public Hole(double radius, Translate position, Color color, int points) {
		super(radius);
		this.points = points;
		Stop[] stops = new Stop[]{new Stop(0.0D, Color.BLACK), new Stop(1.0D, color)};
		RadialGradient radialGradient = new RadialGradient(0.0D, 0.0D, 0.5D, 0.5D, 0.5D, true, CycleMethod.NO_CYCLE, stops);
		super.setFill(radialGradient);
		super.getTransforms().addAll(new Transform[]{position});
	}

	public boolean handleCollision(Circle ball) {
		Bounds ballBounds = ball.getBoundsInParent();
		double ballX = ballBounds.getCenterX();
		double ballY = ballBounds.getCenterY();
		Bounds holeBounds = super.getBoundsInParent();
		double holeX = holeBounds.getCenterX();
		double holeY = holeBounds.getCenterY();
		double holeRadius = super.getRadius();
		double distanceX = holeX - ballX;
		double distanceY = holeY - ballY;
		double distanceSquared = distanceX * distanceX + distanceY * distanceY;
		boolean result = distanceSquared < holeRadius * holeRadius;
		return result;
	}

	public int getPoints() {
		return this.points;
	}
}
