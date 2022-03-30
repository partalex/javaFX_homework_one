package partalex.homework_one;

import partalex.homework_one.objects.*;
import partalex.homework_one.options.Fence_Choose;
import partalex.homework_one.objects.Fence;
import partalex.homework_one.options.Terrain_Choose;
import partalex.homework_one.widgets.ProgressBar;
import partalex.homework_one.widgets.Status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<MouseEvent> {
    private static final double WINDOW_WIDTH = 600.0D;
    private static final double WINDOW_HEIGHT = 800.0D;
    private static final double PLAYER_WIDTH = 20.0D;
    private static final double PLAYER_HEIGHT = 80.0D;
    private static final double PLAYER_MAX_ANGLE_OFFSET = 60.0D;
    private static final double PLAYER_MIN_ANGLE_OFFSET = -60.0D;
    private static final double MS_IN_S = 1000.0D;
    private static final double NS_IN_S = 1.0E9D;
    private static final double MAXIMUM_HOLD_IN_S = 3.0D;
    private static final double MAXIMUM_BALL_SPEED = 1500.0D;
    private static final double BALL_RADIUS = 5.0D;
    private static final double BALL_DAMP_FACTOR = 0.995D;
    private static final double MIN_BALL_SPEED = 1.0D;
    private static final double HOLE_RADIUS = 15.0D;
    private static final double PROGRESS_BAR_WIDTH = 12.0D;
    private static final double OBSTACLE_WIDTH = 120.0D;
    private static final double OBSTACLE_HEIGHT = 16.0D;
    private static final double FENCE_WIDTH = 18.0D;
    private static final double SPEED_MODIFIER_WIDTH = 30.0D;
    private static final double MUD_DAMP_FACTOR = 0.9D;
    private static final double ICE_DAMP_FACTOR = 1.1D;
    private static final double MAXIMUM_HOLE_SPEED = 400.0D;
    private static final int NUMBER_OF_LIVES = 5;
    private static final int HOLE_POINTS_MAXIMUM = 20;
    private static final int HOLE_POINTS_MEDIUM = 10;
    private static final int HOLE_POINTS_MINIMUM = 5;
    private Group root;
    private Player player;
    private Ball ball;
    private long time;
    private Hole[] holes;
    private ProgressBar progressBar;
    private Obstacle[] obstacles;
    private SpeedModifier[] mudPuddles;
    private SpeedModifier[] icePatches;
    private Status status;
    private Main.State state;
    private Fence fence;

    private void addSpeedModifiers() {
        Translate mudPuddle0Position = new Translate(105.0D, 105.0D);
        SpeedModifier mudPuddle0 = SpeedModifier.mud(30.0D, 30.0D, mudPuddle0Position);
        this.root.getChildren().addAll(new Node[]{mudPuddle0});
        Translate icePatch0Position = new Translate(465.0D, 105.0D);
        SpeedModifier icePatch0 = SpeedModifier.ice(30.0D, 30.0D, icePatch0Position);
        this.root.getChildren().addAll(new Node[]{icePatch0});
        Translate mudPuddle1Position = new Translate(465.0D, 545.0D);
        SpeedModifier mudPuddle1 = SpeedModifier.mud(30.0D, 30.0D, mudPuddle1Position);
        this.root.getChildren().addAll(new Node[]{mudPuddle1});
        Translate icePatch1Position = new Translate(105.0D, 545.0D);
        SpeedModifier icePatch1 = SpeedModifier.ice(30.0D, 30.0D, icePatch1Position);
        this.root.getChildren().addAll(new Node[]{icePatch1});
        this.mudPuddles = new SpeedModifier[]{mudPuddle0, mudPuddle1};
        this.icePatches = new SpeedModifier[]{icePatch0, icePatch1};
    }

    private void addObstacles() {
        Translate obstacle0Position = new Translate(140.0D, 400.0D);
        Obstacle obstacle0 = new Obstacle(120.0D, 16.0D, obstacle0Position);
        this.root.getChildren().addAll(new Node[]{obstacle0});
        Translate obstacle1Position = new Translate(340.0D, 400.0D);
        Obstacle obstacle1 = new Obstacle(120.0D, 16.0D, obstacle1Position);
        this.root.getChildren().addAll(new Node[]{obstacle1});
        Translate obstacle2Position = new Translate(292.0D, 136.0D);
        Obstacle obstacle2 = new Obstacle(16.0D, 120.0D, obstacle2Position);
        this.root.getChildren().addAll(new Node[]{obstacle2});
        this.obstacles = new Obstacle[]{obstacle0, obstacle1, obstacle2};
    }

    private void addHoles() {
        Translate hole0Position = new Translate(300.0D, 80.0D);
        Hole hole0 = new Hole(15.0D, hole0Position, Color.DARKGOLDENROD, 20);
        this.root.getChildren().addAll(new Node[]{hole0});
        Translate hole1Position = new Translate(300.0D, 320.0D);
        Hole hole1 = new Hole(15.0D, hole1Position, Color.LIGHTGREEN, 5);
        this.root.getChildren().addAll(new Node[]{hole1});
        Translate hole2Position = new Translate(200.0D, 200.0D);
        Hole hole2 = new Hole(15.0D, hole2Position, Color.YELLOW, 10);
        this.root.getChildren().addAll(new Node[]{hole2});
        Translate hole3Position = new Translate(400.0D, 200.0D);
        Hole hole3 = new Hole(15.0D, hole3Position, Color.YELLOW, 10);
        this.root.getChildren().addAll(new Node[]{hole3});
        this.holes = new Hole[]{hole0, hole1, hole2, hole3};
    }

    public void start(Stage stage) throws IOException {
        this.state = Main.State.IDLE;
        this.root = new Group();
        Image grassImage = new Image(Main.class.getClassLoader().getResourceAsStream(Terrain_Choose.SPACE.toString()));
        ImagePattern grass = new ImagePattern(grassImage);
        Scene scene = new Scene(this.root, 600.0D, 800.0D, grass);
        Image fenceImage = new Image(Main.class.getClassLoader().getResourceAsStream(Fence_Choose.FENCE_3.toString()));
        ImagePattern fenceImagePattern = new ImagePattern(fenceImage);
        this.fence = new Fence(600.0D, 800.0D, 18.0D, fenceImagePattern);
        this.root.getChildren().addAll(new Node[]{this.fence});
        Translate playerPosition = new Translate(290.0D, 720.0D);
        this.player = new Player(20.0D, 80.0D, playerPosition);
        this.root.getChildren().addAll(new Node[]{this.player});
        this.progressBar = new ProgressBar(12.0D, 800.0D);
        this.root.getChildren().addAll(new Node[]{this.progressBar});
        this.addHoles();
        this.addObstacles();
        this.addSpeedModifiers();
        this.status = new Status(5, 5.0D, 600.0D);
        this.root.getChildren().addAll(new Node[]{this.status});
        scene.addEventHandler(MouseEvent.MOUSE_MOVED, (mouseEvent) -> {
            this.player.handleMouseMoved(mouseEvent, -60.0D, 60.0D);
        });
        scene.addEventHandler(MouseEvent.ANY, this);
        Timer timer = new Timer((deltaNanoseconds) -> {
            double deltaSeconds = (double) deltaNanoseconds / 1.0E9D;
            if (this.state == Main.State.BALL_SHOT) {
                boolean collidedWithObstacle = Arrays.stream(this.obstacles).anyMatch((obstacle) -> {
                    return this.ball.handleCollision(obstacle.getBoundsInParent());
                });
                double dampFactor = 0.995D;
                boolean inMud = Arrays.stream(this.mudPuddles).anyMatch((mudPuddle) -> {
                    return mudPuddle.handleCollision(this.ball);
                });
                boolean isOverIce = Arrays.stream(this.icePatches).anyMatch((icePatch) -> {
                    return icePatch.handleCollision(this.ball);
                });
                if (inMud) {
                    dampFactor = 0.9D;
                } else if (isOverIce) {
                    dampFactor = 1.1D;
                }

                boolean stopped = this.ball.update(deltaSeconds, 18.0D, 582.0D, 18.0D, 782.0D, dampFactor, 1.0D);
                Optional<Hole> optionalHole = Arrays.stream(this.holes).filter((hole) -> {
                    return hole.handleCollision(this.ball);
                }).findFirst();
                if (stopped) {
                    this.root.getChildren().remove(this.ball);
                    this.ball = null;
                    this.state = Main.State.IDLE;
                }

                if (optionalHole.isPresent() && this.ball.getSpeedMagnitude() <= 400.0D) {
                    this.ball.initializeFalling((event) -> {
                        this.status.addPoints(((Hole) optionalHole.get()).getPoints());
                        this.root.getChildren().remove(this.ball);
                        this.state = Main.State.IDLE;
                    });
                    this.state = Main.State.FALLING;
                }
            } else if (this.state == Main.State.PREPARATION) {
                this.progressBar.elapsed(deltaNanoseconds, 3.0E9D);
            }

        });
        timer.start();
        scene.setCursor(Cursor.NONE);
        stage.setTitle("Golfer");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//		launch(new String[0]);
        launch();
    }

    public void handle(MouseEvent mouseEvent) {
        if (this.status.hasLives()) {
            if (this.state == Main.State.IDLE && mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED) && mouseEvent.isPrimaryButtonDown()) {
                this.time = System.currentTimeMillis();
                this.progressBar.onClick();
                this.state = Main.State.PREPARATION;
            } else if (this.state == Main.State.PREPARATION && mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                double value = (double) (System.currentTimeMillis() - this.time) / 1000.0D;
                double deltaSeconds = Utilities.clamp(value, 0.0D, 3.0D);
                double ballSpeedFactor = deltaSeconds / 3.0D * 1500.0D;
                Translate ballPosition = this.player.getBallPosition();
                Point2D ballSpeed = this.player.getSpeed().multiply(ballSpeedFactor);
                this.ball = new Ball(5.0D, ballPosition, ballSpeed);
                this.root.getChildren().addAll(new Node[]{this.ball});
                this.progressBar.onRelease();
                this.status.removeLife();
                this.state = Main.State.BALL_SHOT;
            }
        }

    }

    private static enum State {
        IDLE,
        PREPARATION,
        BALL_SHOT,
        FALLING;

        private State() {
        }
    }
}
