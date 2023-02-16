package com.example.javafxgame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    public class Controlador {
        @FXML
        private Rectangle cabeza;
        @FXML
        private Rectangle cuerpo1;
        @FXML
        private Rectangle cuerpo2;
        private List<Rectangle> snake = new ArrayList<>();

        public void start(){
            snake.add(cabeza);
            snake.add(cuerpo1);
            snake.add(cuerpo2);

        }


    }

}