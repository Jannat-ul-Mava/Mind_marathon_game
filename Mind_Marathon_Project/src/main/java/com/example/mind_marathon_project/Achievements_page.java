package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

public class Achievements_page extends Application {

    private Player currentPlayer;
    private int score = 0;
    private ProgressBar progressBar;
    private Label scoreLabel;
    private ImageView medal100, medal500, medal1000;

    private boolean isMedal100Locked = true;
    private boolean isMedal500Locked = true;
    private boolean isMedal1000Locked = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage7) throws Exception {
        // Get current user from session
        currentPlayer = UserSession.getInstance().getCurrentPlayer();

        if (currentPlayer == null) {
            showAlert("Error", "No user logged in!");
            try {
                new Login_page2().start(new Stage());
                stage7.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        // Load player's total score
        score = currentPlayer.getTotalScore();

        CustomTitleBar customTitleBar = new CustomTitleBar(stage7);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fffcf6;");
        root.setTop(customTitleBar);

        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #1b548d; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #ff7bac; " +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(600);
        cardPane.setMaxHeight(550);

        Button backButton = new Button();
        ImageView arrowImageView;
        try {
            arrowImageView = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/arrow.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        arrowImageView.setFitHeight(40);
        arrowImageView.setFitWidth(40);
        backButton.setGraphic(arrowImageView);
        addButtonEffects(backButton, "/com/example/mind_marathon_project/click_sound.mp3");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setStyle("-fx-background-color: #1b548d; -fx-border-color: #1b548d;");

        ImageView coin = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(30);
        coin.setFitWidth(35);
        Label coinValue = new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: #fffdfd;");
        HBox coinBox = new HBox();
        coinBox.getChildren().addAll(coin, coinValue);
        coinBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView heart = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toExternalForm()));
        heart.setFitHeight(25);
        heart.setFitWidth(25);
        Label heartValue = new Label(String.valueOf(currentPlayer.getLives()));
        heartValue.setAlignment(Pos.CENTER);
        heartValue.setStyle("-fx-text-fill: #fffdfd;");
        HBox heartBox = new HBox();
        heartBox.setSpacing(4);
        heartBox.getChildren().addAll(heart, heartValue);
        heartBox.setAlignment(Pos.CENTER_RIGHT);

        HBox sideBox = new HBox();
        sideBox.setAlignment(Pos.CENTER_RIGHT);
        sideBox.setSpacing(10);
        sideBox.getChildren().addAll(coinBox, heartBox);

        HBox headerBox = new HBox(400, backButton, sideBox);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 20px;-fx-border-radius: 20px;-fx-border-color:#ff7bac;-fx-border-width: 2px;-fx-padding: 5,0,5,0");
        titleBar.setSpacing(10);
        titleBar.setMaxWidth(300);
        titleBar.setMaxHeight(90);
        Label headerLabel = new Label("Achievements");
        headerLabel.setStyle("-fx-text-fill: #1b548d;-fx-font-weight: bold;-fx-font-family: 'Comic Sans MS';-fx-font-size: 24px");
        headerLabel.setAlignment(Pos.CENTER);

        ImageView star;
        try {
            star = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/star.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        star.setFitWidth(50);
        star.setFitHeight(50);
        titleBar.getChildren().addAll(headerLabel, star);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        spacer.setPrefHeight(50);
        Region spacer2 = new Region();
        VBox.setVgrow(spacer2, Priority.ALWAYS);
        spacer2.setPrefHeight(30);
        Region spacer3 = new Region();
        VBox.setVgrow(spacer3, Priority.ALWAYS);
        spacer3.setPrefHeight(10);

        ImageView medal;
        try {
            medal = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/medal_pic.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        medal.setFitHeight(40);
        medal.setFitWidth(40);

        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(300);
        progressBar.setStyle("-fx-accent: #ffdd51; " +
                "-fx-control-inner-background: #ffffff; " +
                "-fx-background-radius: 20; " +
                "-fx-padding: 5; " +
                "-fx-border-radius: 20; " +
                "-fx-border-color: #ffde59;");

        scoreLabel = new Label("Score: " + score);
        scoreLabel.setFont(new Font("Comic Sans MS", 20));
        scoreLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold");

        try {
            medal100 = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/lock.png").toExternalForm()));
            medal500 = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/lock.png").toExternalForm()));
            medal1000 = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/lock.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        medal100.setFitWidth(80);
        medal100.setFitHeight(80);
        medal500.setFitWidth(80);
        medal500.setFitHeight(82);
        medal1000.setFitWidth(82);
        medal1000.setFitHeight(84);

        Label medalLabel100 = new Label("Problem-Solver");
        Label medalLabel500 = new Label("Skilled Analyst");
        Label medalLabel1000 = new Label("Sage of Wisdom");
        medalLabel100.setStyle("-fx-text-fill: #f1f5f6;-fx-font-weight: bold;-fx-font-size: 9px;");
        medalLabel500.setStyle("-fx-text-fill: #f1f5f6;-fx-font-weight: bold;-fx-font-size: 9px");
        medalLabel1000.setStyle("-fx-text-fill: #f1f5f6;-fx-font-weight: bold;-fx-font-size: 9px");

        VBox medalBox100 = new VBox(medal100, medalLabel100);
        VBox medalBox500 = new VBox(medal500, medalLabel500);
        VBox medalBox1000 = new VBox(medal1000, medalLabel1000);

        medalBox100.setAlignment(Pos.CENTER);
        medalBox500.setAlignment(Pos.CENTER);
        medalBox1000.setAlignment(Pos.CENTER);

        medalBox100.setSpacing(10);
        medalBox500.setSpacing(10);
        medalBox1000.setSpacing(10);

        HBox medalRow = new HBox(20, medalBox100, medalBox500, medalBox1000);
        medalRow.setAlignment(Pos.CENTER);
        medalRow.setSpacing(50);

        Button Historybutton = new Button("  History  ");
        Historybutton.setStyle("-fx-background-radius:20px;-fx-background-size: 100px;-fx-background-color: #ffe47a;-fx-border-color: #c0981b;-fx-border-radius: 20px;-fx-border-width: 2;-fx-font-size: 14px; -fx-text-fill: #3d3939;-fx-font-family:'Comic Sans MS' ;-fx-font-weight: bold;-fx-padding: 10,10,10,10;");
        try {
            addButtonEffects(Historybutton, "/com/example/mind_marathon_project/main_button.mp3");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        Historybutton.setOnAction(e -> {
            try {
                new game_history().start(new Stage());
                stage7.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        cardPane.setAlignment(Pos.CENTER);
        cardPane.getChildren().addAll(headerBox, titleBar, spacer, medal, progressBar, scoreLabel, spacer2, medalRow, spacer3, Historybutton);

        root.setCenter(cardPane);

        backButton.setOnAction(e -> {
            try {
                new Menu_page().start(new Stage());
                stage7.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(root, 800, 600);
        stage7.initStyle(StageStyle.UNDECORATED);
        stage7.setScene(scene);
        stage7.setMaximized(true);
        stage7.show();

        // Initialize progress and medals based on current score
        updateAchievements();
    }

    private void updateAchievements() {
        // Ensure score doesn't exceed 1000
        if (score > 1000) {
            score = 1000;
        }

        // Update progress bar
        double progress = (double) score / 1000;
        progressBar.setProgress(progress);
        animateProgressBar(progress);

        scoreLabel.setText("Score: " + score);

        // Unlock medals based on the score
        if (score >= 100) {
            medal100.setImage(new Image(getClass().getResource("/com/example/mind_marathon_project/100_score.png").toExternalForm()));
            isMedal100Locked = false;
        }
        if (score >= 500) {
            medal500.setImage(new Image(getClass().getResource("/com/example/mind_marathon_project/500_score.png").toExternalForm()));
            isMedal500Locked = false;
        }
        if (score >= 1000) {
            medal1000.setImage(new Image(getClass().getResource("/com/example/mind_marathon_project/1000_score.png").toExternalForm()));
            isMedal1000Locked = false;
        }
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

    private void animateProgressBar(double targetProgress) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(500), new KeyValue(progressBar.progressProperty(), targetProgress))
        );
        timeline.play();
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}