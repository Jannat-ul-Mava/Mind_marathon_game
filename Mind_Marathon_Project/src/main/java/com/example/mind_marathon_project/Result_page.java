package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Result_page extends Application {
    private int score;
    private String difficulty;
    private String category;
    private String status;
    private Player currentPlayer;

    // Default constructor
    public Result_page() {
        this.score = 0;
        this.difficulty = "Easy";
        this.category = "General";
        this.status = "Pass";
    }

    // Constructor with parameters
    public Result_page(int score, String difficulty, String category, String status) {
        this.score = score;
        this.difficulty = difficulty;
        this.category = category;
        this.status = status;
    }

    public void start(Stage stage11) throws Exception {
        currentPlayer = UserSession.getInstance().getCurrentPlayer();
        if (currentPlayer == null) {
            try {
                new Login_page2().start(new Stage());
                stage11.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        CustomTitleBar customTitleBar = new CustomTitleBar(stage11);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f1f5f6;");
        root.setTop(customTitleBar);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #5eb090;" +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #3c6ca8; " +
                "-fx-padding: 20px;");
        vbox.setMaxWidth(600);
        vbox.setMaxHeight(500);

        ImageView login;
        try {
            login = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/score_pic.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        login.setFitWidth(190);
        login.setFitHeight(60);

        Button backButton = new Button();
        ImageView arrowImageView;
        try {
            arrowImageView = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/menu.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        arrowImageView.setFitHeight(50);
        arrowImageView.setFitWidth(50);
        backButton.setGraphic(arrowImageView);
        addButtonEffects(backButton, "/com/example/mind_marathon_project/click_sound.mp3");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        backButton.setOnAction(e -> {
            try {
                new Menu_page().start(new Stage());
                stage11.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

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

        ImageView hintImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/hint_button.png").toExternalForm()));
        hintImage.setFitHeight(40);
        hintImage.setFitWidth(40);
        Label hintCountLabel = new Label(String.valueOf(currentPlayer.getHints()));
        hintCountLabel.setAlignment(Pos.CENTER);
        hintCountLabel.setStyle("-fx-text-fill: #fffdfd;");
        HBox hintBox = new HBox();
        hintBox.setSpacing(4);
        hintBox.getChildren().addAll(hintImage, hintCountLabel);
        hintBox.setAlignment(Pos.TOP_RIGHT);

        HBox sideBox = new HBox();
        sideBox.setAlignment(Pos.CENTER_RIGHT);
        sideBox.setSpacing(12);
        sideBox.getChildren().addAll(coinBox, heartBox, hintBox);

        HBox headerBox = new HBox(100, backButton, login, sideBox);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        StackPane base = new StackPane();
        base.setAlignment(Pos.CENTER);

        HBox starsBox = new HBox(10);
        starsBox.setAlignment(Pos.CENTER);
        starsBox.setPadding(new Insets(20, 0, 10, 0));
        addStars(starsBox, score);

        ImageView picture;
        try {
            picture = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/result_background.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        picture.setFitWidth(450);
        picture.setFitHeight(410);

        HBox formFields = new HBox(picture);
        formFields.setAlignment(Pos.CENTER);
        formFields.setPadding(new Insets(10));

        ImageView trophyImage = new ImageView();
        setTrophyImage(trophyImage, score);
        trophyImage.setFitHeight(100);
        trophyImage.setFitWidth(300);

        Text scoreText = new Text(score + " / 5");
        scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        scoreText.setStyle("-fx-fill: #003366;");
        StackPane formContainer = new StackPane(trophyImage, scoreText);
        formContainer.setAlignment(Pos.CENTER);

        ImageView coinImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coinImage.setFitWidth(30);
        coinImage.setFitHeight(30);

        Text coinValue1 = new Text("+ " + calculateCoins(score) + " Coins Earned!");
        coinValue1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        coinValue1.setStyle("-fx-fill: #f6b93b;");

        HBox coinBox1 = new HBox(5, coinImage, coinValue1);
        coinBox1.setAlignment(Pos.CENTER);

        // Display difficulty, category, and status
        Label difficultyLabel = new Label("Difficulty: " + difficulty);
        difficultyLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1b548d;");

        Label categoryLabel = new Label("Category: " + category);
        categoryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1b548d;");

        Label statusLabel = new Label("Status: " + status);
        String statusColor = status.equals("Pass") ? "#2ecc71" : "#e74c3c";
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + statusColor + ";");

        VBox infoBox = new VBox(5, difficultyLabel, categoryLabel, statusLabel);
        infoBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(5, starsBox, formContainer, coinBox1, infoBox);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));

        Button okButton = new Button("\t  Exit\t\t");
        okButton.setStyle("-fx-background-color: #fdf58e;-fx-border-color:#f59eb7;-fx-border-width: 2px;-fx-border-radius: 20px;-fx-text-fill: #3c6ca8;-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-padding: 10 20");
        try {
            addButtonEffects(okButton, "/com/example/mind_marathon_project/main_button.mp3");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        okButton.setOnAction(e -> {
            try {
                new exit_page().start(new Stage());
                stage11.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        HBox buttonBox = new HBox(okButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 20, 0));
        base.getChildren().addAll(formFields, centerBox);

        vbox.getChildren().addAll(headerBox, base, buttonBox);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 650);
        stage11.initStyle(StageStyle.UNDECORATED);
        stage11.setMaximized(true);
        stage11.setScene(scene);
        stage11.show();
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

    private void addStars(HBox starsBox, int score) {
        starsBox.getChildren().clear();
        int starCount = 0;

        if (score >= 4) {
            starCount = 3;
        } else if (score >= 3) {
            starCount = 2;
        } else if (score >= 1) {
            starCount = 1;
        }

        for (int i = 0; i < starCount; i++) {
            ImageView star = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/star.png").toExternalForm()));
            star.setFitWidth(50);
            star.setFitHeight(50);
            starsBox.getChildren().add(star);
        }
    }

    private void setTrophyImage(ImageView trophyImage, int score) {
        trophyImage.setImage(new Image(getClass().getResource("/com/example/mind_marathon_project/trophy.png").toExternalForm()));
    }

    private int calculateCoins(int score) {
        return score * 2; // 2 coins per correct answer
    }

    public static void main(String[] args) {
        launch();
    }
}