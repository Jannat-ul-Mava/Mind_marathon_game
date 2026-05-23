package com.example.mind_marathon_project;

import java.util.Collections;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class gameLevel extends Application {
    private String questionFilePath;
    private String category;
    private List<Question> easyQuestions;
    private List<Question> mediumQuestions;
    private List<Question> hardQuestions;

    // Default constructor
    public gameLevel() {
        this.questionFilePath = null;
        this.category = null;
        this.easyQuestions = new ArrayList<>();
        this.mediumQuestions = new ArrayList<>();
        this.hardQuestions = new ArrayList<>();
    }

    // Constructor with parameters
    public gameLevel(String resourcePath, String category) {
        this.questionFilePath = resourcePath;
        this.category = category;
        this.easyQuestions = new ArrayList<>();
        this.mediumQuestions = new ArrayList<>();
        this.hardQuestions = new ArrayList<>();
        loadQuestions();
    }
    private Player currentPlayer;
    @Override
    public void start(Stage stage10) throws Exception {
        currentPlayer = UserSession.getInstance().getCurrentPlayer();
        if (currentPlayer == null) {
            try {
                new Login_page2().start(new Stage());
                stage10.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (questionFilePath != null && easyQuestions.isEmpty() && mediumQuestions.isEmpty() && hardQuestions.isEmpty()) {
            loadQuestions();
        }
        CustomTitleBar customTitleBar = new CustomTitleBar(stage10);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fffcf6;");
        root.setTop(customTitleBar);
        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #fcfcfc; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #252d62; " +
                "-fx-padding: 40px;");
        cardPane.setMaxWidth(850);
        cardPane.setMaxHeight(550);

        // Background image - make it non-interactive
        ImageView imageView;
        try{
            imageView= new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/mode_base.png").toExternalForm()));
        }catch (NullPointerException e){
            throw e;
        }
        imageView.setFitWidth(750);
        imageView.setFitHeight(500);
        imageView.setMouseTransparent(true); // Make image not block clicks

        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setMouseTransparent(true); // Make container also not block clicks

        // Back button
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
        backButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        backButton.setOnAction(e->{
            System.out.println("Back button clicked!"); // Debug
            try {
                new Interest_page().start(new Stage());
                stage10.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox headerBox = new VBox(backButton);
        headerBox.setAlignment(Pos.TOP_LEFT);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        // Coins
        ImageView coin=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(30);
        coin.setFitWidth(35);
        Label coinValue=new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: black;");
        HBox coinBox=new HBox();
        coinBox.getChildren().addAll(coin,coinValue);
        coinBox.setAlignment(Pos.TOP_RIGHT);

        // Lives
        ImageView heart=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toExternalForm()));
        heart.setFitHeight(25);
        heart.setFitWidth(25);
        Label heartValue=new Label(String.valueOf(currentPlayer.getLives()));
        heartValue.setAlignment(Pos.CENTER);
        heartValue.setStyle("-fx-text-fill: black;");
        HBox heartBox=new HBox();
        heartBox.setSpacing(4);
        heartBox.getChildren().addAll(heart,heartValue);
        heartBox.setAlignment(Pos.TOP_RIGHT);

        HBox sideBox=new HBox();
        sideBox.setAlignment(Pos.TOP_RIGHT);
        sideBox.setSpacing(10);
        sideBox.setPadding(new Insets(10, 10, 0, 0));
        sideBox.getChildren().addAll(coinBox,heartBox);

        HBox topBox=new HBox();
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setSpacing(450);
        topBox.setTranslateY(60);
        topBox.getChildren().addAll(headerBox,sideBox);

        // Easy Button
        Button easyButton = new Button();
        ImageView easy;
        try{
            easy=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/easy.png").toExternalForm()));
        }
        catch (NullPointerException e){
            throw e;
        }
        easy.setFitHeight(200);
        easy.setFitWidth(205);
        easyButton.setStyle("-fx-background-color: transparent;-fx-border-color:transparent;");
        easyButton.setGraphic(easy);
        addButtonEffects(easyButton, "/com/example/mind_marathon_project/main_button.mp3");
        easyButton.setOnAction(e -> {
            System.out.println("Easy button clicked!"); // Debug
            try {
                if (easyQuestions == null || easyQuestions.isEmpty()) {
                    System.err.println("No easy questions available!");
                    return;
                }
                List<Question> selectedQuestions = getRandomQuestions(easyQuestions, 5);
                QuizPage quizPage = new QuizPage(selectedQuestions, "Easy", category);
                quizPage.start(new Stage());
                stage10.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Medium Button
        Button mediumButton = new Button();
        ImageView medium;
        try{
            medium=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/medium.png").toExternalForm()));
        }
        catch (NullPointerException e){
            throw e;
        }
        medium.setFitHeight(200);
        medium.setFitWidth(205);
        mediumButton.setStyle("-fx-background-color: transparent;-fx-border-color:transparent;");
        mediumButton.setGraphic(medium);
        addButtonEffects(mediumButton, "/com/example/mind_marathon_project/main_button.mp3");
        mediumButton.setOnAction(e -> {
            System.out.println("Medium button clicked!"); // Debug
            try {
                if (mediumQuestions == null || mediumQuestions.isEmpty()) {
                    System.err.println("No medium questions available!");
                    return;
                }
                List<Question> selectedQuestions = getRandomQuestions(mediumQuestions, 5);
                QuizPage quizPage = new QuizPage(selectedQuestions, "Medium", category);
                quizPage.start(new Stage());
                stage10.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Epic Button
        Button epicButton = new Button();
        ImageView epic;
        try{
            epic=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/epic.png").toExternalForm()));
        }
        catch (NullPointerException e){
            throw e;
        }
        epic.setFitHeight(200);
        epic.setFitWidth(205);
        epicButton.setStyle("-fx-background-color: transparent; -fx-border-color:transparent;");
        epicButton.setGraphic(epic);
        addButtonEffects(epicButton, "/com/example/mind_marathon_project/main_button.mp3");
        epicButton.setOnAction(e -> {
            System.out.println("Epic button clicked!"); // Debug
            try {
                if (hardQuestions == null || hardQuestions.isEmpty()) {
                    System.err.println("No hard questions available!");
                    return;
                }
                List<Question> selectedQuestions = getRandomQuestions(hardQuestions, 5);
                QuizPage quizPage = new QuizPage(selectedQuestions, "Hard", category);
                quizPage.start(new Stage());
                stage10.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(easyButton, mediumButton, epicButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setTranslateY(20); // Adjust position if needed

        VBox cellBox = new VBox(20);
        cellBox.setSpacing(60);
        cellBox.getChildren().addAll(topBox, buttonBox);
        // Create the complete layout with proper layering
        // Layer 1: Background image (bottom, non-interactive)
        // Layer 2: Interactive buttons (top, clickable)
        StackPane backgroundPane = new StackPane();
        backgroundPane.getChildren().addAll(
                imageContainer,  // Background - Layer 1 (mouse transparent)
                cellBox
        );
        backgroundPane.setAlignment(Pos.CENTER);

        cardPane.getChildren().add(backgroundPane);
        root.setCenter(cardPane);

        Scene scene = new Scene(root, 900, 600);
        stage10.initStyle(StageStyle.UNDECORATED);
        stage10.setMaximized(true);
        stage10.setScene(scene);
        stage10.show();
    }

    private List<Question> getRandomQuestions(List<Question> allQuestions, int count) {
        List<Question> shuffled = new ArrayList<>(allQuestions);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }

    private void loadQuestions() {
        easyQuestions = new ArrayList<>();
        mediumQuestions = new ArrayList<>();
        hardQuestions = new ArrayList<>();

        if (questionFilePath == null) return;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getResourceAsStream(questionFilePath)))) {

            String line;
            String currentDifficulty = "";

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.equals("Easy") || line.equals("Medium") || line.equals("Hard")) {
                    currentDifficulty = line;
                    continue;
                }

                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("(") && line.endsWith(")")) {
                    Question question = parseQuestion(line, currentDifficulty);
                    if (question != null) {
                        switch (currentDifficulty) {
                            case "Easy":
                                easyQuestions.add(question);
                                break;
                            case "Medium":
                                mediumQuestions.add(question);
                                break;
                            case "Hard":
                                hardQuestions.add(question);
                                break;
                        }
                    }
                }
            }

            System.out.println("Loaded " + easyQuestions.size() + " easy questions");
            System.out.println("Loaded " + mediumQuestions.size() + " medium questions");
            System.out.println("Loaded " + hardQuestions.size() + " hard questions");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading questions from: " + questionFilePath);
        }
    }

    private Question parseQuestion(String line, String difficulty) {
        try {
            line = line.substring(1, line.length() - 1);
            List<String> parts = splitRespectingQuotes(line);

            if (parts.size() >= 7) {
                String questionText = parts.get(0);
                String option1 = parts.get(1);
                String option2 = parts.get(2);
                String option3 = parts.get(3);
                String option4 = parts.get(4);
                String hint = parts.get(5);
                String correctAnswer = parts.get(6);

                return new Question(questionText, option1, option2, option3,
                        option4, hint, correctAnswer, difficulty);
            }
        } catch (Exception e) {
            System.err.println("Error parsing question: " + line);
            e.printStackTrace();
        }
        return null;
    }

    private List<String> splitRespectingQuotes(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                if (i + 1 < line.length() && line.charAt(i + 1) == ' ') {
                    parts.add(current.toString().trim());
                    current = new StringBuilder();
                    i++;
                } else {
                    current.append(c);
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            parts.add(current.toString().trim());
        }

        return parts;
    }

    private void addButtonEffects(Button button, String soundFile) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
        scaleUp.setFromX(1.0);
        scaleUp.setFromY(1.0);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
        scaleDown.setFromX(1.1);
        scaleDown.setFromY(1.1);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        button.setOnMouseEntered(e -> {
            scaleDown.stop();
            scaleUp.playFromStart();
        });

        button.setOnMouseExited(e -> {
            scaleUp.stop();
            scaleDown.playFromStart();
        });
    }
}