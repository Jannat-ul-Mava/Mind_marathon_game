package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class exit_page extends Application {
    @Override
    public void start(Stage stage10) throws Exception {

        CustomTitleBar customTitleBar = new CustomTitleBar(stage10);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fffcf6;");
        root.setTop(customTitleBar);
        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #f7f2e8; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #ff7bac; " +
                "-fx-padding: 40px;");
        cardPane.setMaxWidth(600);
        cardPane.setMaxHeight(500);

        HBox hBox = new HBox();
        Label text=new Label("Are you sure you \nwant to exit?");
        text.setStyle("-fx-text-fill: white;");
        hBox.setStyle("-fx-background-color: #3c6ca8;-fx-background-radius: 20px;-fx-border-radius: 20px;-fx-border-width:4;-fx-border-color: #ff7bac;-fx-padding: 20px;-fx-text-fill: white ;-fx-font-weight: bold;-fx-font-family: 'Comic Sans MS'");
        hBox.setMaxWidth(300);
        hBox.setMaxHeight(1000);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(text);


        Button yesButton = new Button("  Yes  ");
        yesButton.setStyle("-fx-background-radius:10px;-fx-background-size: 100px;-fx-background-color: #86cc38;-fx-border-color: #ff7bac;-fx-border-radius: 10px;-fx-border-width: 2;-fx-font-size: 14px; -fx-text-fill: #ffffff;-fx-font-family:'Comic Sans MS' ;-fx-font-weight: bold;-fx-padding:11");
        try {
            addButtonEffects(yesButton, "/com/example/mind_marathon_project/main_button.mp3");
        }
        catch (NullPointerException e) {
            throw new RuntimeException( e);
        }
        yesButton.setOnAction(e -> System.exit(0));

        Button noButton = new Button("  No  ");
        noButton.setStyle("-fx-background-radius:10px;-fx-background-size: 100px;-fx-background-color: #fa4848;-fx-border-color: #ff7bac;-fx-border-radius: 10px;-fx-border-width: 2;-fx-font-size: 14px; -fx-text-fill: #ffffff;-fx-font-family:'Comic Sans MS' ;-fx-font-weight: bold;-fx-padding: 11;");
        try {
            addButtonEffects(noButton, "/com/example/mind_marathon_project/main_button.mp3");
        }
        catch (NullPointerException e) {
            throw new RuntimeException( e);
        }


        HBox hBox1 = new HBox();
        hBox1.setSpacing(30);
        hBox1.setAlignment(Pos.CENTER);

        hBox1.getChildren().addAll(yesButton,noButton);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        vBox.getChildren().addAll(hBox,hBox1);
        cardPane.getChildren().addAll(vBox);
        root.setCenter(cardPane);

        Scene scene = new Scene(root, 800, 600);
        stage10.initStyle(StageStyle.UNDECORATED);
        stage10.setMaximized(true);
        stage10.setScene(scene);
        stage10.show();


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
}
