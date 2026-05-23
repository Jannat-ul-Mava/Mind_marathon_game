package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Interest_page extends Application {
    private Player currentPlayer;
    @Override
    public void start(Stage stage4) throws Exception {
        currentPlayer = UserSession.getInstance().getCurrentPlayer();
        if (currentPlayer == null) {
            try {
                new Login_page2().start(new Stage());
                stage4.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        currentPlayer.regenerateLives();
        CustomTitleBar customTitleBar = new CustomTitleBar(stage4);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTop(customTitleBar);

        VBox cardPane = new VBox(20);
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #1b548d; " +
                "-fx-background-radius: 20px;-fx-border-width: 3;" +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #ff7bac; " +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(600);
        cardPane.setMaxHeight(500);
        Button backButton = new Button();
        ImageView arrowImageView;
        try {
            arrowImageView = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/arrow.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        arrowImageView.setFitHeight(45);
        arrowImageView.setFitWidth(45);
        backButton.setGraphic(arrowImageView);
        addButtonEffects(backButton, "/com/example/mind_marathon_project/click_sound.mp3");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setStyle("-fx-background-color: #1b548d; -fx-border-color: #1b548d;");
        backButton.setOnAction(e->{
            try {
                new Menu_page().start(new Stage());
                stage4.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });
        ImageView coin=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(30);
        coin.setFitWidth(35);
        Label coinValue=new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: #fffdfd;");
        HBox coinBox=new HBox();
        coinBox.setSpacing(4);
        coinBox.getChildren().addAll(coin,coinValue);
        coinBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView heart=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toExternalForm()));
        heart.setFitHeight(25);
        heart.setFitWidth(25);
        Label heartValue=new Label(String.valueOf(currentPlayer.getLives()));
        heartValue.setAlignment(Pos.CENTER);
        heartValue.setStyle("-fx-text-fill: #fffdfd;");
        HBox heartBox=new HBox();
        heartBox.setSpacing(4);
        heartBox.getChildren().addAll(heart,heartValue);
        heartBox.setAlignment(Pos.CENTER_RIGHT);

        HBox sideBox=new HBox();
        sideBox.setAlignment(
                Pos.CENTER_RIGHT
        );
        sideBox.setSpacing(15);
        sideBox.getChildren().addAll(coinBox,heartBox);


        HBox headerBox = new HBox(400,backButton,sideBox);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        ImageView imagelogo;
        try{
            imagelogo=new ImageView(new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/iterest_tag.png")));
        }
        catch(NullPointerException e){
            throw new RuntimeException(e);
        }
        imagelogo.setFitHeight(110);
        imagelogo.setFitWidth(450);

        ToggleGroup toggleGroup = new ToggleGroup();

        ToggleButton scienceBtn = createToggleButton("Science",toggleGroup);
        ToggleButton artBtn = createToggleButton("Art",toggleGroup);
        ToggleButton geographyBtn = createToggleButton("Geography",toggleGroup);
        ToggleButton historyBtn = createToggleButton("History",toggleGroup);
        ToggleButton puzzleBtn = createToggleButton("Economy",toggleGroup);
        ToggleButton sportsBtn = createToggleButton("Sports",toggleGroup);

        scienceBtn.setToggleGroup(toggleGroup);
        artBtn.setToggleGroup(toggleGroup);
        geographyBtn.setToggleGroup(toggleGroup);
        historyBtn.setToggleGroup(toggleGroup);
        puzzleBtn.setToggleGroup(toggleGroup);
        sportsBtn.setToggleGroup(toggleGroup);
        // HBox to contain two rows of buttons
        HBox row1 = new HBox(10, scienceBtn, artBtn);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(10, geographyBtn, historyBtn);
        row2.setAlignment(Pos.CENTER);
        HBox row3 = new HBox(10, puzzleBtn, sportsBtn);
        row3.setAlignment(Pos.CENTER);

        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: #fdf58e;-fx-border-color:#f59eb7;-fx-border-width: 2px;-fx-border-radius: 20px;-fx-text-fill: #1b548d;-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-padding: 10 20");
        try {
            addButtonEffects(okButton, "/com/example/mind_marathon_project/main_button.mp3");
        }
        catch (NullPointerException e) {
            throw new RuntimeException( e);
        }
        // Replace your okButton.setOnAction code with this:
        okButton.setOnAction(e -> {
            ToggleButton selected = (ToggleButton) toggleGroup.getSelectedToggle();
            if (selected != null) {
                String category = selected.getText().toLowerCase();
                String fileName = getCategoryFileName(category);
                String resourcePath = "/com/example/mcqs/" + fileName + ".txt";

                try {
                    // Verify the resource exists
                    if (getClass().getResource(resourcePath) != null) {
                        gameLevel game = new gameLevel(resourcePath, category);
                        game.start(new Stage());
                        stage4.close();
                    } else {
                        System.err.println("Resource not found: " + resourcePath);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Please select a category");
            }
        });


        VBox vbox = new VBox(20,imagelogo, row1, row2, row3, okButton);
        vbox.setStyle("-fx-alignment: center; -fx-padding: 10; -fx-background-color: #1b548d;");

        VBox lastbox=new VBox(headerBox,vbox);
        cardPane.getChildren().addAll(lastbox);

        root.setCenter(cardPane);
       /*  okButton.setOnAction(e->{
           if(scienceBtn.isSelected()){
                try {
                    new Science_page().start(new Stage());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                stage4.close();
            } else if (artBtn.isSelected()) {
                new Art_page().start(new Stage());
                stage4.close();

            } else if (geographyBtn.isSelected()) {
                new Geography().start(new Stage());
                stage4.close();
            } else if (historyBtn.isSelected()) {
                new History_page().start(new Stage());
                stage4.close();


            } else if (puzzleBtn.isSelected()) {
                new Puzzle_page().start(new Stage());
                stage4.close();

            } else if (sportsBtn.isSelected()) {
                new Sports_page().start(new Stage());
                stage4.close();

            } else{
                try {
                    new Menu_page().start(new Stage());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });*/

        Scene scene = new Scene(root,800,600);
        stage4.initStyle(StageStyle.UNDECORATED);
        stage4.setMaximized(true);
        stage4.setScene(scene);
        stage4.show();

    }

    // Add this helper method to handle file name mapping
    private String getCategoryFileName(String buttonText) {
        switch (buttonText) {
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
                return buttonText;
        }
    }
    private ToggleButton createToggleButton(String text, ToggleGroup toggleGroup) {
        ToggleButton toggleButton = new ToggleButton(text);
        toggleButton.setAlignment(Pos.CENTER);
        toggleButton.setPrefWidth(150);
        toggleButton.setPrefHeight(40);
        toggleButton.setStyle("-fx-background-color: #fffcf6; -fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-text-fill: #1b548d; -fx-padding:15 25 15 25;");

        toggleButton.setToggleGroup(toggleGroup);
        // Toggle behavior to show a yellow glow when selected
        toggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (oldToggle != null) {
                ((ToggleButton) oldToggle).setStyle("-fx-background-color: #fffcf6; -fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-text-fill: #1b548d; -fx-padding:15 25 15 25;");
            }
            if (newToggle != null) {
                ((ToggleButton) newToggle).setStyle("-fx-background-color: #ffffa5;-fx-background-radius: 20px; -fx-text-fill: #3d3939; -fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-padding: 15 25 15 25;");
            }
        });

        return toggleButton;
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
    public static void main(String[] args) {
        launch(args);
    }
}
