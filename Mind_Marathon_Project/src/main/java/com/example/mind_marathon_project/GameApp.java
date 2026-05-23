package com.example.mind_marathon_project;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
//import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameApp extends Application {
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showStartPage();
    }
    private void showStartPage() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fdeaea;");

    }
}
