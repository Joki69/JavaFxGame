package com.example.javafxgame;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloController {
        private int speed = 5;
        private   int score=-1;
        private  int foodcolor = 0;
        private  int width = 20;
        private  int height = 20;
        private int foodX = 0;
        private  int foodY = 0;
        private  int cornersize = 25;
        private  List<Corner> snake = new ArrayList<>();
        private  Dir direction = Dir.left;
        private  boolean gameOver = false;
        private  Random rand = new Random();



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFoodcolor() {
        return foodcolor;
    }

    public void setFoodcolor(int foodcolor) {
        this.foodcolor = foodcolor;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFoodX() {
        return foodX;
    }

    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }

    public int getCornersize() {
        return cornersize;
    }

    public void setCornersize(int cornersize) {
        this.cornersize = cornersize;
    }

    public List<Corner> getSnake() {
        return snake;
    }

    public void setSnake(List<Corner> snake) {
        this.snake = snake;
    }

    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // tick
        public void tick(GraphicsContext gc) {
            if (gameOver) {
                // Media deadSnake = new Media(new File("src/main/resources/Sounds/Pac-Man Death - Sound Effect (HD).mp3").toURI().toString());
                //  MediaPlayer mediaPlayerDeadSnake =new MediaPlayer(deadSnake);
                // mediaPlayerDeadSnake.play();
                // mediaPlayerDeadSnake.seek(Duration.ZERO);
                gc.setFont(new Font("1942 report", 50));
                gc.setFill(Color.RED);
                gc.fillText("GAME OVER", 100, 250);
                gc.fillText("Press Q to restart", 40, 300);

            } else{
                for (int i = snake.size() - 1; i >= 1; i--) {
                    snake.get(i).x = snake.get(i - 1).x;
                    snake.get(i).y = snake.get(i - 1).y;
                }

                switch (direction) {
                    case up:
                        snake.get(0).y--;
                        if (snake.get(0).y < 0) {
                            gameOver = true;
                        }
                        break;
                    case down:
                        snake.get(0).y++;
                        if (snake.get(0).y > height-1 ) {
                            gameOver = true;
                        }
                        break;
                    case left:
                        snake.get(0).x--;
                        if (snake.get(0).x < 0) {
                            gameOver = true;
                        }
                        break;
                    case right:
                        snake.get(0).x++;
                        if (snake.get(0).x > width-1) {
                            gameOver = true;
                        }
                        break;

                }


                // eat food
                if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
                   //String mediaApth="/home/dam2a/IdeaProjects/dam-m6-2022-main/code/uf1/Baniosmixtos/JavaFxGame/src/main/java/com/example/javafxgame/eating.mp3";
                  // Media eatFruitSound = new Media(getClass().getClassLoader().getResource(mediaApth).toString());
                    //  Media eatFruitSound = new Media(new File("/home/dam2a/IdeaProjects/dam-m6-2022-main/code/uf1/Baniosmixtos/JavaFxGame/src/main/java/com/example/javafxgame/maybe-next-time-huh.wav").toURI().toString());
                    // MediaPlayer mediaPlayerEatFruitSound = new MediaPlayer(eatFruitSound);
                    // mediaPlayerEatFruitSound.play();
                    //mediaPlayerEatFruitSound.seek(Duration.ZERO);
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

                // random fruit
                Image naranja = new Image(Main.class.getResource("Images/naranja-removebg-preview.png").toExternalForm());
                Image pera = new Image(Main.class.getResource("Images/pera-removebg-preview.png").toExternalForm());
                Image platano = new Image(Main.class.getResource("Images/platano-removebg-preview.png").toExternalForm());
                Image sandia = new Image(Main.class.getResource("Images/sandia-removebg-preview.png").toExternalForm());
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
    // eat food
    public void newFood() {
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

}