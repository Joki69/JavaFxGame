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
    // variable
    static int speed = 5;

    static int score=-1;
    static int foodcolor = 0;
    static int width = 20;
    static int height = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornersize = 25;
    static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static boolean gameOver = false;
    static Random rand = new Random();


    public enum Dir {
        left, right, up, down
    }

    public static class Corner {
        int x;
        int y;

        public Corner(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    public void start(Stage primaryStage) {
        try {
            newFood();

            VBox root = new VBox();
            Canvas c = new Canvas(width * cornersize, height * cornersize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }

            }.start();
            Media cumbion = new Media(new File("C:\\Users\\Joki\\IdeaProjects\\JavaFxGame\\src\\main\\resources\\Sounds\\Rainbow cockroach spinning to Daft Punk Around the World for 10 minutes.mp3").toURI().toString());
            MediaPlayer mediaPlayerCumbion =new MediaPlayer(cumbion);
            Scene scene = new Scene(root, width * cornersize, height * cornersize);

            // control
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W || key.getCode() == KeyCode.UP) {
                    if (direction != Dir.down) {
                        direction = Dir.up;
                    }
                }
                if (key.getCode() == KeyCode.A || key.getCode() == KeyCode.LEFT) {
                    if (direction != Dir.right) {
                        direction = Dir.left;
                    }
                }
                if (key.getCode() == KeyCode.S || key.getCode() == KeyCode.DOWN) {
                    if (direction != Dir.up) {
                        direction = Dir.down;
                    }
                }
                if (key.getCode() == KeyCode.D || key.getCode() == KeyCode.RIGHT) {
                    if (direction != Dir.left) {
                        direction = Dir.right;
                    }
                }
                if (key.getCode()==KeyCode.Q && gameOver){
                    gameOver=false;
                    speed=5;
                    score=-1;
                    newFood();
                    direction=Dir.left;
                    snake.clear();
                    snake.add(new Corner(width / 2, height / 2));
                    snake.add(new Corner(width / 2, height / 2));
                    snake.add(new Corner(width / 2, height / 2));
                    mediaPlayerCumbion.seek(Duration.ZERO
                    );
                }
            });

            // add start snake parts
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            snake.add(new Corner(width / 2, height / 2));
            mediaPlayerCumbion.play();
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // tick
    public static void tick(GraphicsContext gc) {
        if (gameOver) {
            Media deadSnake = new Media(new File("src/main/resources/Sounds/Pac-Man Death - Sound Effect (HD).mp3").toURI().toString());
            MediaPlayer mediaPlayerDeadSnake =new MediaPlayer(deadSnake);
            mediaPlayerDeadSnake.play();
            mediaPlayerDeadSnake.seek(Duration.ZERO);
            gc.setFont(new Font("1942 report", 50));
            gc.setFill(Color.RED);
            gc.fillText("GAME OVER", 100, 250);
            gc.fillText("Press Q to restart", 60, 300);

        } else{
            for (int i = snake.size() - 1; i >= 1; i--) {
                snake.get(i).x = snake.get(i - 1).x;
                snake.get(i).y = snake.get(i - 1).y;
            }

        switch (direction) {
            case up:
                snake.get(0).y--;
                if (snake.get(0).y < 1) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).y++;
                if (snake.get(0).y > height-2 ) {
                    gameOver = true;
                }
                break;
            case left:
                snake.get(0).x--;
                if (snake.get(0).x < 1) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).x++;
                if (snake.get(0).x > width-2) {
                    gameOver = true;
                }
                break;

        }


        // eat food
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            Media eatFruitSound = new Media(new File("src/main/resources/Sounds/Pac-Man eating fruit sound effects Pac-Man sound effect.mp3").toURI().toString());
            MediaPlayer mediaPlayerEatFruitSound = new MediaPlayer(eatFruitSound);
            mediaPlayerEatFruitSound.play();
            mediaPlayerEatFruitSound.seek(Duration.ZERO);
            snake.add(new Corner(-1, -1));
            newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
            }
        }

        // fill
        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cornersize, height * cornersize);

        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Dialog", 30));
        gc.fillText("Score: " + score, 10, 30);

        // random foodcolor
            Image naranja = new Image("C:\\Users\\Joki\\IdeaProjects\\JavaFxGame\\src\\main\\resources\\Images\\naranja-removebg-preview.png");
            Image pera = new Image("C:\\Users\\Joki\\IdeaProjects\\JavaFxGame\\src\\main\\resources\\Images\\pera-removebg-preview.png");
            Image platano = new Image("C:\\Users\\Joki\\IdeaProjects\\JavaFxGame\\src\\main\\resources\\Images\\platano-removebg-preview.png");
            Image sandia = new Image("C:\\Users\\Joki\\IdeaProjects\\JavaFxGame\\src\\main\\resources\\Images\\sandia-removebg-preview.png");
            switch (foodcolor) {
            case 0:
                ImageView naranjaView = new ImageView(naranja);
               gc.drawImage(naranja,foodX * cornersize, foodY * cornersize, cornersize, cornersize);
                break;
            case 1:
                ImageView peraView = new ImageView(pera);
                gc.drawImage(pera,foodX * cornersize, foodY * cornersize, cornersize, cornersize);
                break;
            case 2:
                ImageView platanoView = new ImageView(platano);
                gc.drawImage(platano,foodX * cornersize, foodY * cornersize, cornersize, cornersize);
                break;
            case 3:
                ImageView sandiaView = new ImageView(sandia);
                gc.drawImage(sandia,foodX * cornersize, foodY * cornersize, cornersize, cornersize);
                break;
        }






        // snake
        for (Corner c : snake) {
            gc.setFill(Color.BEIGE);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 1, cornersize - 1);
            gc.setFill(Color.GOLD);
            gc.fillRect(c.x * cornersize, c.y * cornersize, cornersize - 2, cornersize - 2);

        }

    }

}

    // food
    public static void newFood() {
        start: while (true) {
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);

            for (Corner c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    continue start;
                }
            }
            foodcolor = rand.nextInt(4);
            speed++;
            score++;
            break;

        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}