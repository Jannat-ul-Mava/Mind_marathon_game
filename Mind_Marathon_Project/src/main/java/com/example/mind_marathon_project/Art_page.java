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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Art_page extends Application {

    private Label questionLabel;
    private List<Button> answerButtons;
    private Label scoreLabel;
    private Label timerLabel;
    private Label lifeLabel;
    private ImageView hintButton;
    private Label hintLabel;
    private Label questionNumberLabel;
    private int currentScore = 0;
    private int currentLife = 5;
    private int questionIndex = 0;
    private long remainingTime = 30000; // 30 seconds in milliseconds

    private List<Question> questions = new ArrayList<>();
    private AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) {
        CustomTitleBar customTitleBar = new CustomTitleBar(primaryStage);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTop(customTitleBar);

        questions.add(new Question("Which country has kangaroo as national animal?", "Australia", "UK", "USA", "UAE", "Kangaroos are native to Australia."));
        questions.add(new Question("What is the largest mammal on Earth?", "Blue Whale", "Elephant", "Giraffe", "Lion", "The blue whale is the largest animal ever known to have lived on Earth."));
        questions.add(new Question("What is the chemical symbol for gold?", "Au", "Ag", "Fe", "Cu", "The chemical symbol for gold is Au."));
        questions.add(new Question("Which planet is known as the Red Planet?", "Mars", "Venus", "Jupiter", "Saturn", "Mars is often called the 'Red Planet' because of its reddish appearance."));
        questions.add(new Question("Who painted the Mona Lisa?", "Leonardo da Vinci", "Michelangelo", "Raphael", "Donatello", "Leonardo da Vinci painted the Mona Lisa."));

        Collections.shuffle(questions);
        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #439576; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #1b548d; " +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(700);
        cardPane.setMaxHeight(500);

        // Top bar
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        spacer1.setMaxSize(150,150);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        spacer2.setMaxSize(150,150);

        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #ffe47a;-fx-padding: 20,30,20,30");
        topBar.setAlignment(Pos.CENTER);

        ImageView coinImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toString()));
        coinImage.setFitWidth(30);
        coinImage.setFitHeight(30);
        scoreLabel = new Label("  0");
        scoreLabel.setStyle("-fx-font-size: 18px;");

        ImageView timerImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/clock_button.png").toString()));
        timerImage.setFitWidth(30);
        timerImage.setFitHeight(30);
        timerLabel = new Label("  30");
        timerLabel.setStyle("-fx-font-size: 18px;");

        ImageView lifeImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toString()));
        lifeImage.setFitWidth(30);
        lifeImage.setFitHeight(30);
        lifeLabel = new Label("  5");
        lifeLabel.setStyle("-fx-font-size: 18px;");

        topBar.getChildren().addAll(coinImage, scoreLabel, spacer1,timerImage, timerLabel,spacer2, lifeImage, lifeLabel);


        // Center content
        VBox center = new VBox(20);
        center.setAlignment(Pos.CENTER);

        questionLabel = new Label(questions.get(questionIndex).getQuestion());
        questionLabel.setMaxWidth(600);
        questionLabel.setMaxHeight(400);
        questionLabel.setStyle("-fx-font-size: 18px;-fx-background-color: #ecf1ef;-fx-border-color: #1b548d;-fx-border-width:3;-fx-border-radius: 20px;-fx-background-radius: 20px;-fx-padding: 20;-fx-text-alignment: center");
        center.getChildren().add(questionLabel);

        answerButtons = new ArrayList<>();
        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(20);
        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(20);

        Button button1 = new Button();
        button1.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d; -fx-background-radius: 20px;-fx-padding: 10px 20px;");
        button1.setOnAction(event -> checkAnswer(button1));
        addButtonEffects(button1,"/com/example/mind_marathon_project/click_sound.mp3");
        button1.setPrefSize(150,50);
        answerButtons.add(button1);

        Button button2 = new Button();
        button2.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d;-fx-background-radius: 20px; -fx-padding: 10px 20px;");
        button2.setOnAction(event -> checkAnswer(button2));
        button2.setPrefSize(150,50);
        addButtonEffects(button2,"/com/example/mind_marathon_project/click_sound.mp3");
        answerButtons.add(button2);
        row1.getChildren().addAll(button1,button2);

        Button button3 = new Button();
        button3.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d; -fx-background-radius: 20px;-fx-padding: 10px 20px;");
        button3.setOnAction(event -> checkAnswer(button3));
        addButtonEffects(button3,"/com/example/mind_marathon_project/click_sound.mp3");
        button3.setPrefSize(150,50);
        answerButtons.add(button3);

        Button button4 = new Button();
        button4.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b548d; -fx-background-radius: 20px;-fx-padding: 10px 20px;");
        button4.setOnAction(event -> checkAnswer(button4));
        addButtonEffects(button4,"/com/example/mind_marathon_project/click_sound.mp3");
        button4.setPrefSize(150,50);

        answerButtons.add(button4);
        row2.getChildren().addAll(button3,button4);
        for (int i = 0; i < 4; i++) {
            Button button = answerButtons.get(i);
            button.setStyle("-fx-background-color: #f1f5f6;-fx-background-radius: 20px;-fx-border-color: #1b548d;-fx-border-width:3;-fx-border-radius: 10px; -fx-padding: 10px 20px;-fx-text-fill: black" );
            button.setText(questions.get(questionIndex).getOptions().get(i));
        }
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPrefHeight(200);
        //  layout.setPrefWidth(200);
        layout.setSpacing(15);
        layout.getChildren().addAll(row1,row2);

        // Bottom bar
        HBox bottomBar = new HBox(20);
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setStyle("-fx-background-color: #ffe47a;-fx-padding: 10,30,10,30");
        // topBar.setSpacing(80);
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
     //   backButton.setOnAction(e -> goBackToMenu());

        VBox headerBox = new VBox(backButton);
        headerBox.setAlignment(Pos.BOTTOM_RIGHT);// Add some spacing after the label for better layout
        headerBox.setPadding(new Insets(0, 0, 10, 0));


        hintButton = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/hint_button.png").toExternalForm()));
        Button hint=new Button();
        hintButton.setFitWidth(30);
        hint.setStyle("-fx-background-color: #1b548d;-fx-border-color: #ffe47a;-fx-border-radius: 20px;-fx-background-radius: 20px;");
        hintButton.setFitHeight(30);
        hint.setGraphic(hintButton);
        addButtonEffects(hint, "/com/example/mind_marathon_project/click_sound.mp3");
        hint.setOnMouseClicked(event -> showHint());

        questionNumberLabel = new Label("< 1 /" + questions.size() + " >");
        questionNumberLabel.setStyle("-fx-font-size: 18px;-fx-font-weight: bold;-fx-font-family: 'Comic Sans MS'");
        bottomBar.getChildren().addAll(headerBox,spacer, questionNumberLabel,spacer4);
        root.setBottom(bottomBar);

        VBox all_labels=new VBox(10);
        all_labels.setAlignment(Pos.CENTER);
        all_labels.getChildren().addAll(topBar,cardPane,bottomBar);

        // Hint label
        hintLabel = new Label();
        hintLabel.setStyle("-fx-background-color: #ffffa5; -fx-text-fill: black; -fx-padding: 5px; -fx-border-color: black;");
        hintLabel.setVisible(false);
        center.getChildren().add(hintLabel);
        cardPane.getChildren().addAll(center,layout,hint);
        root.setCenter(all_labels);
        // Timer
        startTimer();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void startTimer() {
        timer = new AnimationTimer() {
            private long lastUpdate = System.nanoTime();

            @Override
            public void handle(long now) {
                long elapsedTime = now - lastUpdate;
                lastUpdate = now;
                remainingTime -= elapsedTime / 1_000_000; // Convert to milliseconds

                if (remainingTime <= 0) {
                    this.stop();
                    handleTimeUp();
                }

                timerLabel.setText(String.valueOf(remainingTime / 1000));
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

        // Play sound effect
        // AudioClip clickSound = new AudioClip(getClass().getResource(soundFile).toExternalForm());

        // Add hover effects
        button.setOnMouseEntered(e -> scaleTransition.playFromStart());
        button.setOnMouseExited(e -> {
            scaleTransition.stop();
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });

        // Play sound on click
        //    button.setOnMouseClicked(e -> clickSound.play());
    }
    private void checkAnswer(Button button) {
        if (button.getText().equals(questions.get(questionIndex).getCorrectAnswer())) {
            button.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            currentScore += 1;
            scoreLabel.setText(String.valueOf(currentScore));
        } else {
            button.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            currentLife--;
            for (Button cbutton : answerButtons) {
                if (cbutton.getText().equals(questions.get(questionIndex).getCorrectAnswer())) {
                    cbutton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    break;
                }
            }       lifeLabel.setText(String.valueOf(currentLife));
            if (currentLife == 0) {
                //add game over or result page
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
                1000
        );
    }

    private void nextQuestion() {
        if (questionIndex < questions.size() - 1) {
            questionIndex++;
            questionLabel.setText(questions.get(questionIndex).getQuestion());
            for (int i = 0; i < 4; i++) {
                Button button = answerButtons.get(i);
                button.setText(questions.get(questionIndex).getOptions().get(i));
                button.setDisable(false);
                button.setStyle("-fx-background-color: #f1f5f6;-fx-background-radius: 20px;-fx-border-color: #1b548d;-fx-border-width:3;-fx-border-radius: 10px; -fx-padding: 10px 20px;-fx-text-fill: black");
            }
            questionNumberLabel.setText("< " + (questionIndex + 1) + "/" + questions.size() + " >");
            remainingTime = 30000;
            timerLabel.setText(String.valueOf(remainingTime / 1000));
        } else {
            System.out.println("No more questions");
            //add game over or result page
        }
    }

    private void handleTimeUp() {
        System.out.println("Time's up!");
        //add game over or result page
    }


    private void showHint() {
        if (currentLife > 0) {
            currentLife--;
            lifeLabel.setText(String.valueOf(currentLife));
            hintLabel.setText(questions.get(questionIndex).getHint());
            hintLabel.setVisible(true);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> hintLabel.setVisible(false));
                        }
                    },
                    3000
            );
        }
    }

    private static class Question {
        private final String question;
        private final String correctAnswer;
        private final List<String> options;
        private final String hint;

        public Question(String question, String correctAnswer, String option1, String option2, String option3, String hint) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.options = new ArrayList<>();
            this.options.add(option1);
            this.options.add(option2);
            this.options.add(option3);
            this.options.add(correctAnswer);
            Collections.shuffle(this.options);
            this.hint = hint;
        }

        public String getQuestion() {
            return question;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public List<String> getOptions() {
            return options;
        }

        public String getHint() {
            return hint;
        }
    }
}
