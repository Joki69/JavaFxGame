package com.example.javafxgame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Main extends Application {
    HelloController controller = new HelloController();
    public static void main(String[] args) {
    launch(args);
}
    public void start(Stage primaryStage) {
        try {
            controller.newFood();

            VBox root = new VBox();
            Canvas c = new Canvas(controller.getWidth() * controller.getCornersize(), controller.getHeight() * controller.getCornersize());
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        controller.tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / controller.getSpeed()) {
                        lastTick = now;
                        controller.tick(gc);
                    }
                }

            }.start();
            //   Media cumbion = new Media(new File("src/main/resources/Sounds/Rainbow cockroach spinning to Daft Punk Around the World for 10 minutes.mp3").toURI().toString());
            //     MediaPlayer mediaPlayerCumbion =new MediaPlayer(cumbion);
            Scene scene = new Scene(root, controller.getWidth() * controller.getCornersize(), controller.getHeight() * controller.getCornersize());

            // control
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) {
                    if (controller.getDirection() != Dir.down) {
                        controller.setDirection(Dir.up);
                    }
                }
                if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) {
                    if (controller.getDirection() != Dir.right) {
                        controller.setDirection(Dir.left);
                    }
                }
                if (key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) {
                    if (controller.getDirection() != Dir.up) {
                        controller.setDirection(Dir.down);
                    }
                }
                if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) {
                    if (controller.getDirection() != Dir.left) {
                        controller.setDirection(Dir.right);
                    }
                }
                if (key.getCode() == KeyCode.Q && controller.isGameOver()) {
                    controller.setGameOver(false);
                    controller.setSpeed(5);
                    controller.setScore(-1);
                    controller.newFood();
                    controller.setDirection(Dir.left);
                    controller.getSnake().clear();
                    controller.getSnake().add(new Corner(controller.getWidth() / 2, controller.getHeight() / 2));
                    controller.getSnake().add(new Corner(controller.getWidth() / 2, controller.getHeight() / 2));
                    controller.getSnake().add(new Corner(controller.getWidth() / 2, controller.getHeight() / 2));
                    //  mediaPlayerCumbion.seek(Duration.ZERO);
                }
            });
            // add start snake parts
            controller.getSnake().add(new Corner(controller.getWidth() / 2, controller.getHeight() / 2));
            controller.getSnake().add(new Corner(controller.getWidth() / 2, controller.getHeight() / 2));
            controller.getSnake().add(new Corner(controller.getWidth() / 2, controller.getHeight() / 2));
            // mediaPlayerCumbion.play();
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}