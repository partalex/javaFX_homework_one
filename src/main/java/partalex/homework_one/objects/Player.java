package partalex.homework_one.objects;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import partalex.homework_one.Utilities;

public class Player extends Group {
	private double width;
	private double height;
	private Translate position;
	private Rotate rotate;
	private double baseRadius;

	public Player(double width, double height, Translate position) {
		this.width = width;
		this.height = height;
		this.position = position;
		this.baseRadius = width / 2.0D;
		Circle base = new Circle(this.baseRadius, Color.ORANGE);
		base.getTransforms().add(new Translate(width / 2.0D, height - this.baseRadius));
		Path cannon = new Path(new PathElement[]{new MoveTo(width / 4.0D, 0.0D), new LineTo(0.0D, height - this.baseRadius), new HLineTo(width), new LineTo(width * 3.0D / 4.0D, 0.0D), new ClosePath()});
		cannon.setFill(Color.LIGHTBLUE);
		super.getChildren().addAll(new Node[]{cannon, base});
		this.rotate = new Rotate();
		super.getTransforms().addAll(new Transform[]{position, new Translate(width / 2.0D, height - this.baseRadius), this.rotate, new Translate(-width / 2.0D, -(height - this.baseRadius))});
	}

	public void handleMouseMoved(MouseEvent mouseEvent, double minAngleOffset, double maxAngleOffset) {
		Bounds bounds = super.getBoundsInParent();
		double startX = bounds.getCenterX();
		double startY = bounds.getMaxY();
		double endX = mouseEvent.getX();
		double endY = mouseEvent.getY();
		Point2D direction = (new Point2D(endX - startX, endY - startY)).normalize();
		Point2D startPosition = new Point2D(0.0D, -1.0D);
		double angle = (double)(endX > startX ? 1 : -1) * direction.angle(startPosition);
		this.rotate.setAngle(Utilities.clamp(angle, minAngleOffset, maxAngleOffset));
	}

	public Translate getBallPosition() {
		double startX = this.position.getX() + this.width / 2.0D;
		double startY = this.position.getY() + this.height - this.baseRadius;
		double x = startX + Math.sin(Math.toRadians(this.rotate.getAngle())) * this.height;
		double y = startY - Math.cos(Math.toRadians(this.rotate.getAngle())) * this.height;
		Translate result = new Translate(x, y);
		return result;
	}

	public Point2D getSpeed() {
		double startX = this.position.getX() + this.width / 2.0D;
		double startY = this.position.getY() + this.height - this.baseRadius;
		double endX = startX + Math.sin(Math.toRadians(this.rotate.getAngle())) * this.height;
		double endY = startY - Math.cos(Math.toRadians(this.rotate.getAngle())) * this.height;
		Point2D result = new Point2D(endX - startX, endY - startY);
		return result.normalize();
	}
}
