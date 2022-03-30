package partalex.homework_one.objects;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Fence extends Group {
    private Rectangle left;
    private Rectangle right;
    private Rectangle top;
    private Rectangle bottom;

    public Fence(double width, double height, double fenceWidth, ImagePattern texture) {
        this.left = new Rectangle(fenceWidth, height, texture);
        this.right = new Rectangle(fenceWidth, height, texture);
        this.right.getTransforms().addAll(new Transform[]{new Translate(width - fenceWidth, 0.0D)});
        this.top = new Rectangle(width - 2.0D * fenceWidth, fenceWidth, texture);
        this.top.getTransforms().addAll(new Transform[]{new Translate(fenceWidth, 0.0D)});
        this.bottom = new Rectangle(width - 2.0D * fenceWidth, fenceWidth, texture);
        this.bottom.getTransforms().addAll(new Transform[]{new Translate(fenceWidth, height - fenceWidth)});
        super.getChildren().addAll(new Node[]{this.left, this.right, this.top, this.bottom});
    }

    public boolean handleCollision(Ball ball) {
        boolean collidedWithLeft = ball.handleCollision(this.left.getBoundsInParent());
        boolean collidedWithRight = ball.handleCollision(this.right.getBoundsInParent());
        boolean collidedWithTop = ball.handleCollision(this.top.getBoundsInParent());
        boolean collidedWithBottom = ball.handleCollision(this.bottom.getBoundsInParent());
        boolean result = collidedWithLeft || collidedWithRight || collidedWithTop || collidedWithBottom;
        return result;
    }
}
