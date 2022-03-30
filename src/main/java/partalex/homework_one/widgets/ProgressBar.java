package partalex.homework_one.widgets;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import partalex.homework_one.Utilities;

public class ProgressBar extends Group {
    private Rectangle progressBar;
    private Scale scale;
    private ProgressBar.State state;
    private double time;

    public ProgressBar(double width, double height) {
        this.progressBar = new Rectangle(width, height, Color.RED);
        this.state = ProgressBar.State.IDLE;
        this.scale = new Scale(1.0D, 0.0D);
        this.progressBar.getTransforms().addAll(new Transform[]{new Translate(0.0D, height), this.scale, new Translate(0.0D, -height)});
    }

    public void onClick() {
        super.getChildren().addAll(new Node[]{this.progressBar});
        this.scale.setY(0.0D);
        this.state = ProgressBar.State.CLICKED;
        this.time = 0.0D;
    }

    public void elapsed(long dns, double max) {
        if (this.state == ProgressBar.State.CLICKED) {
            this.time += (double)dns;
            double value = Utilities.clamp(this.time / max, 0.0D, 1.0D);
            this.scale.setY(value);
        }

    }

    public void onRelease() {
        super.getChildren().remove(this.progressBar);
        this.scale.setY(0.0D);
        this.state = ProgressBar.State.IDLE;
        this.time = 0.0D;
    }

    private static enum State {
        IDLE,
        CLICKED;

        private State() {
        }
    }
}
