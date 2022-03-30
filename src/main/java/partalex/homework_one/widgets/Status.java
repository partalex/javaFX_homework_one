package partalex.homework_one.widgets;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class Status extends Group {
    private Circle[] lives;
    private int numberOfLives;
    private int points;
    private Text text;

    public Status(int numberOfLives, double ballRadius, double windowWidth) {
        this.lives = new Circle[numberOfLives];

        for(int i = 0; i < this.lives.length; ++i) {
            this.lives[i] = new Circle(ballRadius, Color.RED);
            this.lives[i].getTransforms().addAll(new Transform[]{new Translate(windowWidth - (double)((i + 1) * 3) * ballRadius, 2.0D * ballRadius)});
            super.getChildren().addAll(new Node[]{this.lives[i]});
        }

        this.numberOfLives = numberOfLives;
        this.text = new Text(Integer.toString(this.points));
        this.text.setFont(Font.font(20.0D));
        this.text.getTransforms().addAll(new Transform[]{new Translate(10.0D, 15.0D)});
        super.getChildren().addAll(new Node[]{this.text});
    }

    public boolean hasLives() {
        return this.numberOfLives > 0;
    }

    public void removeLife() {
        --this.numberOfLives;
        this.getChildren().remove(this.lives[this.numberOfLives]);
    }

    public void addPoints(int points) {
        this.points += points;
        this.text.setText(Integer.toString(this.points));
    }
}
