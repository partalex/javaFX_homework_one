package partalex.homework_one.objects;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import partalex.homework_one.Utilities;

public class Ball extends Circle {
	private Translate position;
	private Point2D speed;
	private Scale scale;
	private Ball.State state;

	public Ball(double radius, Translate position, Point2D speed) {
		super(radius, Color.RED);
		this.position = position;
		this.speed = speed;
		this.scale = new Scale(1.0D, 1.0D);
		this.state = Ball.State.MOVING;
		super.getTransforms().addAll(new Transform[]{this.position, this.scale});
	}

	public boolean update(double ds, double left, double right, double top, double bottom, double dampFactor, double minBallSpeed) {
		boolean result = false;
		if (this.state == Ball.State.MOVING) {
			double newX = this.position.getX() + this.speed.getX() * ds;
			double newY = this.position.getY() + this.speed.getY() * ds;
			double radius = super.getRadius();
			double minX = left + radius;
			double maxX = right - radius;
			double minY = top + radius;
			double maxY = bottom - radius;
			this.position.setX(Utilities.clamp(newX, minX, maxX));
			this.position.setY(Utilities.clamp(newY, minY, maxY));
			if (newX < minX || newX > maxX) {
				this.speed = new Point2D(-this.speed.getX(), this.speed.getY());
			}

			if (newY < minY || newY > maxY) {
				this.speed = new Point2D(this.speed.getX(), -this.speed.getY());
			}

			this.speed = this.speed.multiply(dampFactor);
			double ballSpeed = this.speed.magnitude();
			if (ballSpeed < minBallSpeed) {
				result = true;
			}
		}

		return result;
	}

	public boolean handleCollision(Bounds bounds) {
		Bounds myBounds = this.getBoundsInParent();
		double centerX = myBounds.getCenterX();
		double centerY = myBounds.getCenterY();
		double radius = this.getRadius();
		double minX = bounds.getMinX();
		double maxX = bounds.getMaxX();
		double minY = bounds.getMinY();
		double maxY = bounds.getMaxY();
		double closestX = Utilities.clamp(centerX, minX, maxX);
		double closestY = Utilities.clamp(centerY, minY, maxY);
		double distanceX = centerX - closestX;
		double distanceY = centerY - closestY;
		double distanceSquared = distanceX * distanceX + distanceY * distanceY;
		boolean collisionDetected = distanceSquared <= radius * radius;
		if (collisionDetected) {
			boolean goingUp = this.speed.getY() < 0.0D;
			boolean goingDown = this.speed.getY() > 0.0D;
			boolean goingRight = this.speed.getX() > 0.0D;
			boolean goingLeft = this.speed.getX() < 0.0D;
			double epsilon = 3.0D;
			boolean isMinY = Utilities.areEqual(closestY, minY, 3.0D);
			boolean isMaxY = Utilities.areEqual(closestY, maxY, 3.0D);
			boolean isMinX = Utilities.areEqual(closestX, minX, 3.0D);
			boolean isMaxX = Utilities.areEqual(closestX, maxX, 3.0D);
			boolean invertY = isMinY && goingDown || isMaxY && goingUp;
			boolean invertX = isMinX && goingRight || isMaxX && goingLeft;
			if (invertY) {
				this.speed = new Point2D(this.speed.getX(), -this.speed.getY());
			} else if (invertX) {
				this.speed = new Point2D(-this.speed.getX(), this.speed.getY());
			}
		}

		return collisionDetected;
	}

	public void initializeFalling(EventHandler<ActionEvent> onFinishHandler) {
		this.state = Ball.State.FALLING;
		Timeline animation = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(0.0D), new KeyValue[]{new KeyValue(this.scale.xProperty(), 1, Interpolator.LINEAR)}), new KeyFrame(Duration.seconds(0.0D), new KeyValue[]{new KeyValue(this.scale.yProperty(), 1, Interpolator.LINEAR)}), new KeyFrame(Duration.seconds(2.0D), new KeyValue[]{new KeyValue(this.scale.xProperty(), 0, Interpolator.LINEAR)}), new KeyFrame(Duration.seconds(2.0D), new KeyValue[]{new KeyValue(this.scale.yProperty(), 0, Interpolator.LINEAR)})});
		animation.setOnFinished(onFinishHandler);
		animation.play();
	}

	public double getSpeedMagnitude() {
		return this.speed.magnitude();
	}

	private static enum State {
		MOVING,
		FALLING;

		private State() {
		}
	}
}
