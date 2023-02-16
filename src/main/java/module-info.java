module com.example.javafxgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.javafxgame to javafx.fxml;
    exports com.example.javafxgame;
}