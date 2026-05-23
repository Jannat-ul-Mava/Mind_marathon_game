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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShopStop_page extends Application {

    private Player currentPlayer;
    private Label coinValue;
    private Label heartValue;
    private Label purchaseMessage;
    private int hintCount = 0; // Track hints purchased

    @Override
    public void start(Stage stage6) throws Exception {
        // Get current user from session
        currentPlayer = UserSession.getInstance().getCurrentPlayer();

        if (currentPlayer == null) {
            showAlert("Error", "No user logged in!");
            try {
                new Login_page2().start(new Stage());
                stage6.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        CustomTitleBar customTitleBar = new CustomTitleBar(stage6);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTop(customTitleBar);

        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #fffcf6; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #ff7bac; " +
                "-fx-padding: 0px;");
        cardPane.setMaxWidth(600);
        cardPane.setMaxHeight(590);

        Button backButton = new Button();

        ImageView arrowImageView = loadImage("/com/example/mind_marathon_project/arrow.png");
        arrowImageView.setFitHeight(50);
        arrowImageView.setFitWidth(50);
        backButton.setGraphic(arrowImageView);
        addButtonEffects(backButton, "/com/example/mind_marathon_project/click_sound.mp3");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setStyle("-fx-background-color: #fffcf6; -fx-border-color: #fffcf6;");

        HBox titleBar = new HBox();
        titleBar.setStyle("-fx-background-color: #f15363;-fx-background-radius: 20px;-fx-border-radius: 20px;-fx-border-color:#f15363;-fx-border-width: 2px;-fx-padding: 5,0,5,0");
        titleBar.setSpacing(10);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.setMaxWidth(200);
        titleBar.setMaxHeight(100);

        Label headerLabel = new Label("Shop Stop");
        headerLabel.setStyle("-fx-text-fill: #ffffff;-fx-font-weight: bold;-fx-font-family: 'Comic Sans MS';-fx-font-size: 24px");
        headerLabel.setAlignment(Pos.CENTER);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        ImageView money = loadImage("/com/example/mind_marathon_project/money.png");
        money.setFitWidth(30);
        money.setFitHeight(30);
        titleBar.getChildren().addAll(headerLabel, money);

        HBox headerBox = new HBox(backButton, titleBar);
        headerBox.setSpacing(140);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        // Create a Label for displaying purchase success message
        purchaseMessage = new Label();
        purchaseMessage.setStyle("-fx-text-fill: #3d3939; -fx-font-weight: bold;");
        purchaseMessage.setAlignment(Pos.CENTER);

        ImageView shop;
        try {
            shop = new ImageView(new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/shop_base.png")));
        } catch (NullPointerException e) {
            throw e;
        }
        shop.setFitHeight(480);
        shop.setFitWidth(400);

        HBox hbox1 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setSpacing(20);

        ImageView hintlogo = loadImage("/com/example/mind_marathon_project/hint_image.png");
        hintlogo.setFitHeight(90);
        hintlogo.setFitWidth(150);

        Button buyButtonhint = new Button("BUY");
        buyButtonhint.setStyle("-fx-background-color: #fffcf6;-fx-border-color:#b78fd6;-fx-border-width: 3px;-fx-border-radius: 18px;-fx-text-fill: #3d3939;-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-padding: 10 10");
        addButtonEffects(buyButtonhint, "/com/example/mind_marathon_project/main_button.mp3");
        buyButtonhint.setOnAction(e -> {
            purchaseHint();
        });

        hbox1.getChildren().addAll(hintlogo, buyButtonhint);

        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.setSpacing(20);

        ImageView lifelogo = loadImage("/com/example/mind_marathon_project/life_image.png");
        lifelogo.setFitHeight(80);
        lifelogo.setFitWidth(150);

        Button buyButtonlife = new Button("BUY");
        buyButtonlife.setStyle("-fx-background-color: #fffcf6;-fx-border-color:#b78fd6;-fx-border-width: 3px;-fx-border-radius: 18px;-fx-text-fill: #3d3939;-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;-fx-font-size: 18px;-fx-padding: 10 10");
        addButtonEffects(buyButtonlife, "/com/example/mind_marathon_project/main_button.mp3");
        buyButtonlife.setOnAction(e -> {
            purchaseLife();
        });

        hbox2.getChildren().addAll(lifelogo, buyButtonlife);

        // Display current player's coins and lives
        ImageView coin = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(40);
        coin.setFitWidth(45);
        coinValue = new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: #1a1717;");
        HBox coinBox = new HBox();
        coinBox.getChildren().addAll(coin, coinValue);
        coinBox.setAlignment(Pos.CENTER);

        ImageView heart = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toExternalForm()));
        heart.setFitHeight(35);
        heart.setFitWidth(35);
        heartValue = new Label(String.valueOf(currentPlayer.getLives()));
        heartValue.setAlignment(Pos.CENTER);
        heartValue.setStyle("-fx-text-fill: #1a1414;");
        HBox heartBox = new HBox();
        heartBox.setSpacing(4);
        heartBox.getChildren().addAll(heart, heartValue);
        heartBox.setAlignment(Pos.CENTER);

        HBox sideBox = new HBox();
        sideBox.setAlignment(Pos.CENTER);
        sideBox.setSpacing(80);
        sideBox.setTranslateY(35);
        sideBox.getChildren().addAll(coinBox, heartBox);

        HBox headerBox1 = new HBox(sideBox);
        headerBox1.setAlignment(Pos.BOTTOM_CENTER);
        headerBox1.setPadding(new Insets(0, 0, 0, 0));

        Button okbutton = new Button("  OK  ");
        okbutton.setStyle("-fx-background-radius:20px;-fx-background-size: 100px;-fx-background-color: #f15363;-fx-border-color: #f59eb7;-fx-border-radius: 20px;-fx-border-width: 2;-fx-font-size: 14px; -fx-text-fill: #ffffff;-fx-font-family:'Comic Sans MS' ;-fx-font-weight: bold;-fx-padding: 10,10,10,10;");
        try {
            addButtonEffects(okbutton, "/com/example/mind_marathon_project/main_button.mp3");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
        okbutton.setOnAction(e -> {
            try {
                new Menu_page().start(new Stage());
                stage6.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Region spacer3 = new Region();
        spacer3.setMaxSize(30, 50);
        VBox.setVgrow(spacer3, Priority.ALWAYS);
        Region spacer4 = new Region();
        spacer4.setMaxSize(0, 30);
        VBox.setVgrow(spacer4, Priority.ALWAYS);
        Region spacer5 = new Region();
        spacer5.setMaxSize(0, 60);
        VBox.setVgrow(spacer5, Priority.ALWAYS);

        VBox baseOfShop = new VBox();
        baseOfShop.setAlignment(Pos.CENTER);
        baseOfShop.getChildren().addAll(spacer3, headerBox1, spacer5, hbox1, hbox2, spacer4, okbutton);
        StackPane base = new StackPane();
        base.getChildren().addAll(shop, baseOfShop);

        VBox vbox = new VBox(1, headerBox, base, purchaseMessage);
        vbox.setStyle("-fx-alignment: center; -fx-padding: 1;");
        vbox.setAlignment(Pos.CENTER);

        cardPane.getChildren().addAll(vbox);

        root.setCenter(cardPane);

        backButton.setOnAction(e -> {
            try {
                new Menu_page().start(new Stage());
                stage6.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(root, 800, 600);
        stage6.initStyle(StageStyle.UNDECORATED);
        stage6.setMaximized(true);
        stage6.setScene(scene);
        stage6.show();
    }

    private void purchaseHint() {
        final int HINT_COST = 10;

        if (currentPlayer.getCoins() >= HINT_COST) {
            currentPlayer.subtractCoins(HINT_COST);
            currentPlayer.addHint();

            if (updatePlayerInFile()) {
                UserSession.getInstance().setCurrentPlayer(currentPlayer);
                coinValue.setText(String.valueOf(currentPlayer.getCoins()));
                purchaseMessage.setText("✓ Hint purchased! You now have " + currentPlayer.getHints() + " hint(s). " + HINT_COST + " coins deducted.");
                purchaseMessage.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-font-size: 16px;");
            } else {
                currentPlayer.addCoins(HINT_COST);
                currentPlayer.useHint();
                purchaseMessage.setText("✗ Purchase failed! Please try again.");
                purchaseMessage.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 16px;");
            }
        } else {
            purchaseMessage.setText("✗ Not enough coins! You need " + HINT_COST + " coins to buy a hint.");
            purchaseMessage.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 16px;");
        }
    }

    private void purchaseLife() {
        final int LIFE_COST = 10;

        if (currentPlayer.getCoins() >= LIFE_COST) {
            // Deduct coins and add life
            currentPlayer.subtractCoins(LIFE_COST);
            currentPlayer.addLife();

            // Update the file
            if (updatePlayerInFile()) {
                // Update session
                UserSession.getInstance().setCurrentPlayer(currentPlayer);

                // Update UI
                coinValue.setText(String.valueOf(currentPlayer.getCoins()));
                heartValue.setText(String.valueOf(currentPlayer.getLives()));
                purchaseMessage.setText("✓ Life purchased! You now have " + currentPlayer.getLives() + " lives. " + LIFE_COST + " coins deducted.");
                purchaseMessage.setStyle("-fx-text-fill: #2ecc71; -fx-font-weight: bold; -fx-font-size: 16px;");
            } else {
                // Revert if file update failed
                currentPlayer.addCoins(LIFE_COST);
                currentPlayer.loseLife();
                purchaseMessage.setText("✗ Purchase failed! Please try again.");
                purchaseMessage.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 16px;");
            }
        } else {
            purchaseMessage.setText("✗ Not enough coins! You need " + LIFE_COST + " coins to buy a life.");
            purchaseMessage.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 16px;");
        }
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
                    // Process complete user record
                    if (currentUser.size() >= 4) {
                        String nameLine = currentUser.get(0);

                        // Check if this is the current player
                        if (nameLine.equals("Name:" + currentPlayer.getName())) {
                            // Write updated user data
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
                            // Write user data as-is
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

        // Replace original file with updated file
        if (updated) {
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }
        } else {
            tempFile.delete();
        }

        return updated;
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to load images
    private ImageView loadImage(String path) {
        try {
            return new ImageView(new Image(getClass().getResourceAsStream(path)));
        } catch (NullPointerException e) {
            System.out.println("Image not found: " + path);
            return new ImageView();
        }
    }

    private void addButtonEffects(Button button, String soundFile) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        // Add hover effects
        button.setOnMouseEntered(e -> scaleTransition.playFromStart());
        button.setOnMouseExited(e -> {
            scaleTransition.stop();
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}