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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class info_page extends Application {
    private Player currentPlayer;
    @Override
    public void start(Stage stage8) {
        currentPlayer = UserSession.getInstance().getCurrentPlayer();

        if (currentPlayer == null) {
            try {
                new Login_page2().start(new Stage());
                stage8.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        CustomTitleBar customTitleBar = new CustomTitleBar(stage8);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTop(customTitleBar);

        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #c3e1ff; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #1b548d; " +
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
        arrowImageView.setFitHeight(40);
        arrowImageView.setFitWidth(40);
        backButton.setGraphic(arrowImageView);
        addButtonEffects(backButton, "/com/example/mind_marathon_project/click_sound.mp3");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        backButton.setOnAction(e -> goBackToMenu());
        ImageView coin=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(30);
        coin.setFitWidth(35);
        Label coinValue=new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: #131111;");
        HBox coinBox=new HBox();
        coinBox.getChildren().addAll(coin,coinValue);
        coinBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView heart=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toExternalForm()));
        heart.setFitHeight(25);
        heart.setFitWidth(25);
        Label heartValue=new Label(String.valueOf(currentPlayer.getLives()));
        heartValue.setAlignment(Pos.CENTER);
        heartValue.setStyle("-fx-text-fill: #1a1919;");
        HBox heartBox=new HBox();
        heartBox.setSpacing(4);
        heartBox.getChildren().addAll(heart,heartValue);
        heartBox.setAlignment(Pos.CENTER_RIGHT);

        HBox sideBox=new HBox();
        sideBox.setAlignment(
                Pos.CENTER_RIGHT
        );
        sideBox.setSpacing(10);
        sideBox.getChildren().addAll(coinBox,heartBox);

        HBox headerBox = new HBox(400,backButton,sideBox);
        headerBox.setAlignment(Pos.CENTER_LEFT);// Add some spacing after the label for better layout
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        ImageView title;
        try{
            title=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/info_bank.png").toExternalForm()));
        }
        catch(NullPointerException e){
            throw e;
        }
        title.setFitHeight(450);
        title.setFitWidth(350);

        cardPane.setSpacing(5);
         VBox bottomPage=new VBox();
        bottomPage.setAlignment(Pos.CENTER);
        bottomPage.getChildren().addAll(title);
        cardPane.getChildren().addAll(headerBox,bottomPage);
        root.setCenter(cardPane);
        backButton.setOnAction(e ->{
            try {
                new Menu_page().start(new Stage());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            stage8.close();
        });
        Scene scene = new Scene(root, 800, 600);
        stage8.initStyle(StageStyle.UNDECORATED);
        stage8.centerOnScreen();
        stage8.setMaximized(true);
        stage8.setScene(scene);
        stage8.show();

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
    private void goBackToMenu() {
        System.out.println("Back to Menu");
    }
}
