package com.example.mind_marathon_project;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class Welcome_page extends Application {

    @Override
    public void start(Stage stage) throws IOException {


        CustomTitleBar customTitleBar = new CustomTitleBar(stage);


        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f1f5f6;");
        root.setTop(customTitleBar);


        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #fffcf6; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #3c6ca8; " +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(600);
        cardPane.setMaxHeight(500);


        ImageView welcomeTag = loadImage("/com/example/mind_marathon_project/Welcome_tag.png");
        welcomeTag.setFitWidth(300);
        welcomeTag.setFitHeight(150);


        ImageView logo = loadImage("/com/example/mind_marathon_project/logo.png");
        logo.setFitWidth(300);
        logo.setFitHeight(250);


        VBox buttons=new VBox();
        Button playButton = createPlayButton();
        Button exitButton = createExitButton();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        buttons.getChildren().addAll(playButton, exitButton);

        cardPane.getChildren().addAll(welcomeTag, logo,buttons);


        root.setCenter(cardPane);
        StackPane centerPane = new StackPane(cardPane);
        centerPane.setAlignment(Pos.CENTER);
        root.setCenter(centerPane);


        playButton.setOnAction(e -> {
            if (isUserSignedUp()) {
                try {
                    new Login_page2().start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    new Login_page().start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        exitButton.setOnAction(e->{
            if (exitButton.isHover()) {
                try {
                    new exit_page().start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            });


        Scene scene = new Scene(root, 800, 600);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    private ImageView loadImage(String path) {
        try {
            return new ImageView(new Image(getClass().getResourceAsStream(path)));
        } catch (NullPointerException e) {
            throw new RuntimeException("Image not found: " + path, e);
        }
    }

    private Button createPlayButton() {
        Button playButton = new Button("\tPLAY\t");
        playButton.setStyle("-fx-background-color: #3c6ca8;-fx-border-color:#b024cd;-fx-border-width: 2px;-fx-border-radius: 20px;-fx-text-fill: #ffffff;-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-padding: 10 20");
        try {
            addButtonEffects(playButton, "/com/example/mind_marathon_project/main_button.mp3");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return playButton;
    }
    private Button createExitButton() {
        Button exitButton = new Button("\tEXIT\t\t");
        exitButton.setStyle("-fx-background-color: #3c6ca8;-fx-border-color:#b024cd;-fx-border-width: 2px;-fx-border-radius: 20px;-fx-text-fill: #ffffff;-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-padding: 10 20");
        try {
            addButtonEffects(exitButton, "/com/example/mind_marathon_project/main_button.mp3");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        return exitButton;
    }

    private void addButtonEffects(Button button, String soundFile) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        button.setOnMouseEntered(e -> scaleTransition.playFromStart());
        button.setOnMouseExited(e -> {
            scaleTransition.stop();
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
    }

    private boolean isUserSignedUp() {
        File userFile = new File("user_data.txt");
        return userFile.exists();
    }

    public static void main(String[] args) {
        launch();
    }
}
