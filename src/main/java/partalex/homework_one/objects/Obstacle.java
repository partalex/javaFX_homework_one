package partalex.homework_one.objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Obstacle extends Rectangle {
    public Obstacle(double width, double height, Translate position) {
        super(width, height, Color.GRAY);
        super.getTransforms().addAll(new Transform[]{position});
    }
}

