package partalex.homework_one.objects;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import partalex.homework_one.Utilities;

public class SpeedModifier extends Rectangle {
    public static SpeedModifier ice(double width, double height, Translate position) {
        return new SpeedModifier(width, height, Color.LIGHTBLUE, position);
    }

    public static SpeedModifier mud(double width, double height, Translate position) {
        return new SpeedModifier(width, height, Color.BROWN, position);
    }

    public SpeedModifier(double width, double height, Color color, Translate position) {
        super(width, height, color);
        super.getTransforms().addAll(new Transform[]{position});
    }

    public boolean handleCollision(Ball ball) {
        Bounds myBounds = super.getBoundsInParent();
        double minX = myBounds.getMinX();
        double maxX = myBounds.getMaxX();
        double minY = myBounds.getMinY();
        double maxY = myBounds.getMaxY();
        Bounds ballBounds = ball.getBoundsInParent();
        double ballX = ballBounds.getCenterX();
        double ballY = ballBounds.getCenterY();
        double radius = ball.getRadius();
        double closestX = Utilities.clamp(ballX, minX, maxX);
        double closestY = Utilities.clamp(ballY, minY, maxY);
        double distanceX = ballX - closestX;
        double distanceY = ballY - closestY;
        double distanceSquared = distanceX * distanceX + distanceY * distanceY;
        boolean collisionDetected = distanceSquared <= radius * radius;
        return collisionDetected;
    }
}
