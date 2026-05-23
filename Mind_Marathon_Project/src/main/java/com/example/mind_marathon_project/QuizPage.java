package com.example.mind_marathon_project;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizPage extends Application {
    private Player currentPlayer;
    private Label questionLabel;
    private List<Button> answerButtons;
    private Label scoreLabel;
    private Label timerLabel;
    private Label lifeLabel;
    private Label hintCountLabel; // Added hint label
    private ImageView hintButton;
    private Label hintLabel;
    private Label questionNumberLabel;
    private int questionsAnswered = 0;
    private int correctAnswers = 0;
    private int questionIndex = 0;
    private long remainingTime = 30000;

    private List<Question> questions;
    private String difficulty;
    private String category;
    private AnimationTimer timer;

    public QuizPage() {
        this.questions = new ArrayList<>();
        this.difficulty = "Easy";
        this.category = "General";
    }

    public QuizPage(List<Question> questions, String difficulty, String category) {
        this.questions = questions;
        this.difficulty = difficulty;
        this.category = category;
    }

    @Override
    public void start(Stage primaryStage) {
        currentPlayer = UserSession.getInstance().getCurrentPlayer();
        if (currentPlayer == null) {
            try {
                new Login_page2().start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        currentPlayer.regenerateLives();

        if (questions != null && !questions.isEmpty()) {
            Collections.shuffle(questions);
        } else {
            System.err.println("No questions loaded!");
            return;
        }

        CustomTitleBar customTitleBar = new CustomTitleBar(primaryStage);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTop(customTitleBar);

        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #439576; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #1b548d; " +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(700);
        cardPane.setMaxHeight(500);

        // Top bar with coins, timer, lives, and hints
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        spacer1.setMaxSize(100, 150);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        spacer2.setMaxSize(100, 150);

        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #ffe47a;-fx-padding: 20,30,20,30");
        topBar.setAlignment(Pos.CENTER);

        // Coins
        ImageView coinImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toString()));
        coinImage.setFitWidth(30);
        coinImage.setFitHeight(30);
        scoreLabel = new Label("  " + currentPlayer.getCoins());
        scoreLabel.setStyle("-fx-font-size: 18px;");

        // Timer
        ImageView timerImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/clock_button.png").toString()));
        timerImage.setFitWidth(30);
        timerImage.setFitHeight(30);
        timerLabel = new Label("  30");
        timerLabel.setStyle("-fx-font-size: 18px;");

        // Lives
        ImageView lifeImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toString()));
        lifeImage.setFitWidth(30);
        lifeImage.setFitHeight(30);
        lifeLabel = new Label("  " + currentPlayer.getLives());
        lifeLabel.setStyle("-fx-font-size: 18px;");

        // Hints - ADDED
        ImageView hintImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/hint_button.png").toString()));
        hintImage.setFitWidth(30);
        hintImage.setFitHeight(30);
        hintCountLabel = new Label("  " + currentPlayer.getHints());
        hintCountLabel.setStyle("-fx-font-size: 18px;");

        topBar.getChildren().addAll(coinImage, scoreLabel, spacer1, timerImage, timerLabel, spacer2, lifeImage, lifeLabel, hintImage, hintCountLabel);

        // Center content
        VBox center = new VBox(20);
        center.setAlignment(Pos.CENTER);

        questionLabel = new Label(questions.get(questionIndex).getQuestionText());
        questionLabel.setWrapText(true);
        questionLabel.setMaxWidth(600);
        questionLabel.setMaxHeight(400);
        questionLabel.setStyle("-fx-font-size: 18px;-fx-background-color: #ecf1ef;-fx-border-color: #1b548d;-fx-border-width:3;-fx-border-radius: 20px;-fx-background-radius: 20px;-fx-padding: 20;-fx-alignment: center");
        center.getChildren().add(questionLabel);

        answerButtons = new ArrayList<>();
        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(20);
        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(20);

        Button button1 = new Button();
        button1.setWrapText(true);
        button1.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d; -fx-background-radius: 20px;-fx-padding: 10px 20px;");
        button1.setOnAction(event -> checkAnswer(button1));
        addButtonEffects(button1, "/com/example/mind_marathon_project/click_sound.mp3");
        button1.setPrefSize(150, 50);
        answerButtons.add(button1);

        Button button2 = new Button();
        button2.setWrapText(true);
        button2.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d;-fx-background-radius: 20px; -fx-padding: 10px 20px;");
        button2.setOnAction(event -> checkAnswer(button2));
        button2.setPrefSize(150, 50);
        addButtonEffects(button2, "/com/example/mind_marathon_project/click_sound.mp3");
        answerButtons.add(button2);
        row1.getChildren().addAll(button1, button2);

        Button button3 = new Button();
        button3.setWrapText(true);
        button3.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d; -fx-background-radius: 20px;-fx-padding: 10px 20px;");
        button3.setOnAction(event -> checkAnswer(button3));
        addButtonEffects(button3, "/com/example/mind_marathon_project/click_sound.mp3");
        button3.setPrefSize(150, 50);
        answerButtons.add(button3);

        Button button4 = new Button();
        button4.setWrapText(true);
        button4.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d; -fx-background-radius: 20px;-fx-padding: 10px 20px;");
        button4.setOnAction(event -> checkAnswer(button4));
        addButtonEffects(button4, "/com/example/mind_marathon_project/click_sound.mp3");
        button4.setPrefSize(150, 50);

        answerButtons.add(button4);
        row2.getChildren().addAll(button3, button4);

        List<String> currentOptions = getCurrentOptions();
        for (int i = 0; i < 4 && i < currentOptions.size(); i++) {
            Button button = answerButtons.get(i);
            button.setStyle("-fx-background-color: #f1f5f6;-fx-background-radius: 20px;-fx-border-color: #1b548d;-fx-border-width:3;-fx-border-radius: 10px; -fx-padding: 10px 20px;-fx-text-fill: black");
            button.setText(currentOptions.get(i));
        }

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(200);
        layout.setSpacing(15);
        layout.getChildren().addAll(row1, row2);

        // Bottom bar
        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setStyle("-fx-background-color: #ffe47a;-fx-padding: 10,30,10,30");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Region spacer4 = new Region();
        HBox.setHgrow(spacer4, Priority.ALWAYS);

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
        backButton.setStyle("-fx-background-color: #ffe47a;");
        addButtonEffects(backButton, "/com/example/mind_marathon_project/click_sound.mp3");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setOnAction(e -> {
            timer.stop();
            try {
                gameLevel game = new gameLevel(
                        "/com/example/mcqs/" + getCategoryFileName(category) + ".txt",
                        category
                );
                game.start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox headerBox = new VBox(backButton);
        headerBox.setAlignment(Pos.BOTTOM_RIGHT);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        hintButton = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/hint_button.png").toExternalForm()));
        Button hint = new Button();
        hintButton.setFitWidth(30);
        hint.setStyle("-fx-background-color: #1b548d;-fx-border-color: #ffe47a;-fx-border-radius: 20px;-fx-background-radius: 20px;");
        hintButton.setFitHeight(30);
        hint.setGraphic(hintButton);
        addButtonEffects(hint, "/com/example/mind_marathon_project/click_sound.mp3");
        hint.setOnMouseClicked(event -> showHint());

        questionNumberLabel = new Label("< 1 /" + questions.size() + " >");
        questionNumberLabel.setStyle("-fx-font-size: 18px;-fx-font-weight: bold;-fx-font-family: 'Comic Sans MS'");
        bottomBar.getChildren().addAll(headerBox, spacer, questionNumberLabel, spacer4);
        root.setBottom(bottomBar);

        VBox all_labels = new VBox(10);
        all_labels.setAlignment(Pos.CENTER);
        all_labels.getChildren().addAll(topBar, cardPane, bottomBar);

        hintLabel = new Label();
        hintLabel.setWrapText(true);
        hintLabel.setMaxWidth(550);
        hintLabel.setStyle("-fx-background-color: #ffffa5; -fx-text-fill: black; -fx-padding: 10px; -fx-border-color: black;-fx-border-radius: 10px;-fx-background-radius: 10px;");
        hintLabel.setVisible(false);
        center.getChildren().add(hintLabel);
        cardPane.getChildren().addAll(center, layout, hint);
        root.setCenter(all_labels);

        startTimer();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private boolean updatePlayerInFile() {
        File inputFile = new File("user_data.txt");
        File tempFile = new File("temp_user_data.txt");

        if (!inputFile.exists()) {
            return false;
        }

        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            List<String> currentUser = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                currentUser.add(line);

                if (line.equals("---")) {
                    if (currentUser.size() >= 4) {
                        String nameLine = currentUser.get(0);

                        if (nameLine.equals("Name:" + currentPlayer.getName())) {
                            writer.write("Name:" + currentPlayer.getName() + "\n");
                            writer.write("Password:" + currentPlayer.getPassword() + "\n");
                            writer.write("PIN:" + currentPlayer.getPin() + "\n");
                            writer.write("Age:" + currentPlayer.getAge() + "\n");
                            writer.write("Coins:" + currentPlayer.getCoins() + "\n");
                            writer.write("Lives:" + currentPlayer.getLives() + "\n");
                            writer.write("TotalScore:" + currentPlayer.getTotalScore() + "\n");
                            writer.write("Hints:" + currentPlayer.getHints() + "\n");
                            writer.write("LastLifeRegen:" + currentPlayer.getLastLifeRegenTime().toString() + "\n");
                            writer.write("ProfileImage:" + (currentPlayer.getProfileImagePath() != null ? currentPlayer.getProfileImagePath() : "") + "\n");
                            writer.write("---\n");
                            updated = true;
                        } else {
                            for (String userLine : currentUser) {
                                writer.write(userLine + "\n");
                            }
                        }
                    }
                    currentUser.clear();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (updated) {
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } else {
            tempFile.delete();
        }

        return updated;
    }

    private void showHint() {
        if (currentPlayer.getHints() > 0) {
            currentPlayer.useHint();
            updatePlayerInFile();
            UserSession.getInstance().setCurrentPlayer(currentPlayer);
            hintCountLabel.setText("  " + currentPlayer.getHints()); // Update UI

            hintLabel.setText(questions.get(questionIndex).getHint());
            hintLabel.setVisible(true);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> hintLabel.setVisible(false));
                        }
                    },
                    4000
            );
        } else {
            hintLabel.setText("No hints available! Buy hints from the Shop.");
            hintLabel.setVisible(true);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> hintLabel.setVisible(false));
                        }
                    },
                    2000
            );
        }
    }

    private String getCategoryFileName(String category) {
        switch (category.toLowerCase()) {
            case "sports":
                return "sport&GK";
            case "science":
                return "science";
            case "art":
                return "art";
            case "geography":
                return "geography";
            case "history":
                return "history";
            case "puzzle":
                return "puzzle";
            default:
                return category.toLowerCase();
        }
    }

    private List<String> getCurrentOptions() {
        Question currentQuestion = questions.get(questionIndex);
        List<String> options = new ArrayList<>();
        options.add(currentQuestion.getOption1());
        options.add(currentQuestion.getOption2());
        options.add(currentQuestion.getOption3());
        options.add(currentQuestion.getOption4());
        Collections.shuffle(options);
        return options;
    }

    private void startTimer() {
        timer = new AnimationTimer() {
            private long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {
                long elapsedTime = now - lastUpdate;
                lastUpdate = now;
                remainingTime -= elapsedTime / 1_000_000;

                if (remainingTime <= 0) {
                    this.stop();
                    handleTimeUp();
                }

                timerLabel.setText("  " + (remainingTime / 1000));
            }
        };
        timer.start();
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

    private void checkAnswer(Button button) {
        String selectedAnswer = button.getText();
        String correctAnswer = questions.get(questionIndex).getCorrectAnswer();

        questionsAnswered++;

        if (selectedAnswer.equals(correctAnswer)) {
            button.setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-background-radius: 20px;-fx-border-radius: 10px;");

            // Award 2 coins per correct answer
            currentPlayer.addCoins(2);
            correctAnswers++;
            scoreLabel.setText("  " + currentPlayer.getCoins());
        } else {
            button.setStyle("-fx-background-color: red; -fx-text-fill: white;-fx-background-radius: 20px;-fx-border-radius: 10px;");
            currentPlayer.loseLife();

            for (Button cbutton : answerButtons) {
                if (cbutton.getText().equals(correctAnswer)) {
                    cbutton.setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-background-radius: 20px;-fx-border-radius: 10px;");
                    break;
                }
            }

            lifeLabel.setText("  " + currentPlayer.getLives());

            if (currentPlayer.getLives() == 0) {
                timer.stop();
                handleGameOver();
                return;
            }
        }

        answerButtons.forEach(btn -> btn.setDisable(true));

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> nextQuestion());
                    }
                },
                1500
        );
    }

    private void nextQuestion() {
        if (questionIndex < questions.size() - 1) {
            questionIndex++;
            questionLabel.setText(questions.get(questionIndex).getQuestionText());

            List<String> currentOptions = getCurrentOptions();
            for (int i = 0; i < 4 && i < currentOptions.size(); i++) {
                Button button = answerButtons.get(i);
                button.setText(currentOptions.get(i));
                button.setDisable(false);
                button.setStyle("-fx-background-color: #f1f5f6;-fx-background-radius: 20px;-fx-border-color: #1b548d;-fx-border-width:3;-fx-border-radius: 10px; -fx-padding: 10px 20px;-fx-text-fill: black");
            }

            questionNumberLabel.setText("< " + (questionIndex + 1) + "/" + questions.size() + " >");
            remainingTime = 30000;
            timerLabel.setText("  30");
        } else {
            timer.stop();
            handleQuizComplete();
        }
    }

    private void handleTimeUp() {
        currentPlayer.loseLife();
        lifeLabel.setText("  " + currentPlayer.getLives());
        questionsAnswered++;

        if (currentPlayer.getLives() == 0) {
            handleGameOver();
        } else {
            String correctAnswer = questions.get(questionIndex).getCorrectAnswer();
            for (Button button : answerButtons) {
                if (button.getText().equals(correctAnswer)) {
                    button.setStyle("-fx-background-color: green; -fx-text-fill: white;-fx-background-radius: 20px;");
                }
            }

            answerButtons.forEach(btn -> btn.setDisable(true));

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> nextQuestion());
                        }
                    },
                    1500
            );
        }
    }

    private void handleGameOver() {
        // Calculate score for achievements (10 points per correct answer)
        int achievementScore = correctAnswers * 10;
        currentPlayer.addScore(achievementScore);

        // Update player file
        updatePlayerInFile();
        UserSession.getInstance().setCurrentPlayer(currentPlayer);

        // Save game record
        saveGameRecord("Fail", correctAnswers);

        Platform.runLater(() -> {
            try {
                Result_page resultPage = new Result_page(correctAnswers, difficulty, category, "Fail");
                resultPage.start(new Stage());
                Stage currentStage = (Stage) questionLabel.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleQuizComplete() {
        // Calculate score for achievements (10 points per correct answer)
        int achievementScore = correctAnswers * 10;
        currentPlayer.addScore(achievementScore);

        // Update player file
        updatePlayerInFile();
        UserSession.getInstance().setCurrentPlayer(currentPlayer);

        // Save game record
        saveGameRecord("Pass", correctAnswers);

        Platform.runLater(() -> {
            try {
                Result_page resultPage = new Result_page(correctAnswers, difficulty, category, "Pass");
                resultPage.start(new Stage());
                Stage currentStage = (Stage) questionLabel.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void saveGameRecord(String status, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentPlayer.getName() + "_history.txt", true))) {
            GameRecord record = new GameRecord(difficulty, category, status, score);
            writer.write(record.toFileFormat() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}