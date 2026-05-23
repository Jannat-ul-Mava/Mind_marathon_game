package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class game_history extends Application {

    private Player currentPlayer;
    private List<GameRecord> gameHistory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage HistoryStage) {
        // Get current user from session
        currentPlayer = UserSession.getInstance().getCurrentPlayer();

        if (currentPlayer == null) {
            showAlert("Error", "No user logged in!");
            try {
                new Login_page2().start(new Stage());
                HistoryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        // Load game history for current player
        gameHistory = loadGameHistory();

        CustomTitleBar customTitleBar = new CustomTitleBar(HistoryStage);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fffcf6;");
        root.setTop(customTitleBar);

        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #fffcf6; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #333883;-fx-border-width: 5px;" +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(650);
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
        backButton.setStyle("-fx-background-color: #fffcf6; -fx-border-color: #fffcf6;");
        backButton.setOnAction(e -> {
            try {
                new Achievements_page().start(new Stage());
                HistoryStage.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        ImageView historyTitle;
        try {
            historyTitle = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/history_button.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        historyTitle.setFitHeight(150);
        historyTitle.setFitWidth(350);
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.TOP_CENTER);
        titleBar.getChildren().add(historyTitle);

        ImageView coin = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(30);
        coin.setFitWidth(35);
        Label coinValue = new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: rgba(12,11,11,0.98);");
        HBox coinBox = new HBox();
        coinBox.getChildren().addAll(coin, coinValue);
        coinBox.setAlignment(Pos.TOP_RIGHT);

        ImageView heart = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toExternalForm()));
        heart.setFitHeight(25);
        heart.setFitWidth(25);
        Label heartValue = new Label(String.valueOf(currentPlayer.getLives()));
        heartValue.setAlignment(Pos.CENTER);
        heartValue.setStyle("-fx-text-fill: #1e1919;");
        HBox heartBox = new HBox();
        heartBox.setSpacing(4);
        heartBox.getChildren().addAll(heart, heartValue);
        heartBox.setAlignment(Pos.TOP_RIGHT);

        HBox sideBox = new HBox();
        sideBox.setAlignment(Pos.TOP_RIGHT);
        sideBox.setSpacing(10);
        sideBox.getChildren().addAll(coinBox, heartBox);

        HBox headerBox = new HBox(backButton, titleBar, sideBox);
        headerBox.setAlignment(Pos.TOP_CENTER);
        headerBox.setSpacing(30);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        // Create the history table
        VBox historyTable = createHistoryTable();

        cardPane.getChildren().addAll(headerBox, historyTable);
        root.setCenter(cardPane);

        Scene scene = new Scene(root, 800, 600);
        HistoryStage.initStyle(StageStyle.UNDECORATED);
        HistoryStage.setScene(scene);
        HistoryStage.setMaximized(true);
        HistoryStage.show();
    }

    private VBox createHistoryTable() {
        VBox tableContainer = new VBox(10);
        tableContainer.setAlignment(Pos.CENTER);
        tableContainer.setPadding(new Insets(10));

        // Header
        HBox headerRow = new HBox();
        headerRow.setAlignment(Pos.CENTER);
        headerRow.setSpacing(10);
        headerRow.setStyle("-fx-background-color: linear-gradient(to bottom, #66ccff, #0033cc);" +
                "-fx-padding: 10; -fx-background-radius: 10;");

        Label modeHeader = new Label("Mode");
        modeHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        modeHeader.setPrefWidth(100);
        modeHeader.setAlignment(Pos.CENTER);

        Label categoryHeader = new Label("Category");
        categoryHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        categoryHeader.setPrefWidth(120);
        categoryHeader.setAlignment(Pos.CENTER);

        Label dateHeader = new Label("Date");
        dateHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        dateHeader.setPrefWidth(150);
        dateHeader.setAlignment(Pos.CENTER);

        Label statusHeader = new Label("Status");
        statusHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        statusHeader.setPrefWidth(100);
        statusHeader.setAlignment(Pos.CENTER);

        Label scoreHeader = new Label("Score");
        scoreHeader.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
        scoreHeader.setPrefWidth(80);
        scoreHeader.setAlignment(Pos.CENTER);

        headerRow.getChildren().addAll(modeHeader, categoryHeader, dateHeader, statusHeader, scoreHeader);

        // Data rows in a scrollable container
        VBox dataRows = new VBox(5);
        dataRows.setAlignment(Pos.CENTER);

        if (gameHistory.isEmpty()) {
            Label noDataLabel = new Label("No game history yet!\nPlay some games to see your history here.");
            noDataLabel.setStyle("-fx-text-fill: #3c6ca8; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 30;");
            noDataLabel.setAlignment(Pos.CENTER);
            dataRows.getChildren().add(noDataLabel);
        } else {
            for (GameRecord record : gameHistory) {
                HBox row = createHistoryRow(record);
                dataRows.getChildren().add(row);
            }
        }

        ScrollPane scrollPane = new ScrollPane(dataRows);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        tableContainer.getChildren().addAll(headerRow, scrollPane);

        return tableContainer;
    }

    private HBox createHistoryRow(GameRecord record) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10);
        row.setStyle("-fx-background-color: #ecf1ef; -fx-padding: 8; -fx-background-radius: 5;");

        Label modeLabel = new Label(record.getMode());
        modeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1b548d;");
        modeLabel.setPrefWidth(100);
        modeLabel.setAlignment(Pos.CENTER);

        Label categoryLabel = new Label(record.getCategory());
        categoryLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1b548d;");
        categoryLabel.setPrefWidth(120);
        categoryLabel.setAlignment(Pos.CENTER);

        Label dateLabel = new Label(record.getDate());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1b548d;");
        dateLabel.setPrefWidth(150);
        dateLabel.setAlignment(Pos.CENTER);

        Label statusLabel = new Label(record.getStatus());
        String statusColor = record.getStatus().equalsIgnoreCase("Pass") ? "#2ecc71" : "#e74c3c";
        statusLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + statusColor + ";");
        statusLabel.setPrefWidth(100);
        statusLabel.setAlignment(Pos.CENTER);

        Label scoreLabel = new Label(String.valueOf(record.getScore()));
        scoreLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1b548d; -fx-font-weight: bold;");
        scoreLabel.setPrefWidth(80);
        scoreLabel.setAlignment(Pos.CENTER);

        row.getChildren().addAll(modeLabel, categoryLabel, dateLabel, statusLabel, scoreLabel);

        return row;
    }

    private List<GameRecord> loadGameHistory() {
        List<GameRecord> history = new ArrayList<>();
        File file = new File(currentPlayer.getName() + "_history.txt");

        if (!file.exists()) {
            return history;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                GameRecord record = GameRecord.fromFileFormat(line);
                if (record != null) {
                    history.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return history;
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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