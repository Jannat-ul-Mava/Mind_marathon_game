package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Edit_page extends Application {

    private Player currentPlayer;

    @Override
    public void start(Stage stage9) {
        // Get current user from session
        currentPlayer = UserSession.getInstance().getCurrentPlayer();

        if (currentPlayer == null) {
            showAlert("Error", "No user logged in!");
            try {
                new Login_page2().start(new Stage());
                stage9.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        CustomTitleBar customTitleBar = new CustomTitleBar(stage9);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fffcf6;");
        root.setTop(customTitleBar);

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
        backButton.setAlignment(Pos.TOP_RIGHT);
        backButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // Display current user's coins and lives
        ImageView coin = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(30);
        coin.setFitWidth(35);
        Label coinValue = new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: #ffffff;");
        HBox coinBox = new HBox();
        coinBox.getChildren().addAll(coin, coinValue);
        coinBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView heart = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/life_button.png").toExternalForm()));
        heart.setFitHeight(25);
        heart.setFitWidth(25);
        Label heartValue = new Label(String.valueOf(currentPlayer.getLives()));
        heartValue.setAlignment(Pos.CENTER);
        heartValue.setStyle("-fx-text-fill: #fcfcfc;");
        HBox heartBox = new HBox();
        heartBox.setSpacing(4);
        heartBox.getChildren().addAll(heart, heartValue);
        heartBox.setAlignment(Pos.CENTER_RIGHT);

        HBox sideBox = new HBox();
        sideBox.setAlignment(Pos.CENTER_RIGHT);
        sideBox.setSpacing(10);
        sideBox.getChildren().addAll(coinBox, heartBox);

        HBox headerBox2 = new HBox(400, backButton, sideBox);
        headerBox2.setAlignment(Pos.CENTER_LEFT);
        headerBox2.setPadding(new Insets(0, 0, 10, 0));

        Label headerLabel = new Label("Edit Information");
        headerLabel.setStyle("-fx-background-radius:20px;-fx-background-size: 20px;-fx-background-color: #fff4f4;-fx-border-color: #06d2ec;-fx-border-radius: 20px;-fx-border-width: 2;-fx-font-size: 24px; -fx-text-fill: #1b548d;-fx-font-family:'Comic Sans MS' ;-fx-font-weight: bold;-fx-padding: 10,10,10,10;");
        headerLabel.setAlignment(Pos.TOP_CENTER);

        HBox headerBox1 = new HBox(headerLabel);
        headerBox1.setAlignment(Pos.TOP_CENTER);
        headerBox1.setSpacing(10);

        VBox headerBox = new VBox(headerBox2);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        headerBox.setPadding(new Insets(0, 0, 10, 0));

        // TextFields for input - Pre-filled with current user data
        VBox fields = new VBox();
        fields.setSpacing(30);
        fields.setAlignment(Pos.CENTER);

        TextField nameField = new TextField(currentPlayer.getName());
        nameField.setMaxWidth(200);
        nameField.setStyle("-fx-background-color: #fcfcfc;-fx-background-radius: 10px;-fx-border-width: 2;-fx-border-color: #06d2ec ;-fx-border-radius: 10px; -fx-text-fill: #4a4a4a;-fx-font-family:'Comic Sans MS';-fx-padding: 10,5,10,5 ;");

        TextField ageField = new TextField(String.valueOf(currentPlayer.getAge()));
        ageField.setMaxWidth(200);
        ageField.setStyle("-fx-background-color: #ffffff;-fx-background-radius: 10px;-fx-border-width: 2;-fx-border-color: #06d2ec ; -fx-border-radius: 10px; -fx-text-fill: #4a4a4a;-fx-font-family:'Comic Sans MS' ;-fx-padding: 10,5,10,5 ;");

        PasswordField passwordField = new PasswordField();
        passwordField.setText(currentPlayer.getPassword());
        passwordField.setMaxWidth(200);
        passwordField.setStyle("-fx-background-color: #fcfcfc;-fx-background-radius: 10px;-fx-border-width: 2;-fx-border-color: #06d2ec ; -fx-border-radius: 10px; -fx-text-fill: #4a4a4a;-fx-font-family:'Comic Sans MS' ;-fx-padding: 10,5,10,5 ;");

        fields.getChildren().addAll(nameField, ageField, passwordField);

        // OK Button
        Button okButton = new Button("  OK  ");
        okButton.setStyle("-fx-background-radius:20px;-fx-background-size: 100px;-fx-background-color: #ffe47a;-fx-border-color: #c0981b;-fx-border-radius: 20px;-fx-border-width: 2;-fx-font-size: 14px; -fx-text-fill: #3d3939;-fx-font-family:'Comic Sans MS' ;-fx-font-weight: bold;-fx-padding: 10,10,10,10;");

        try {
            addButtonEffects(okButton, "/com/example/mind_marathon_project/main_button.mp3");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }

        okButton.setOnAction(e -> {
            String newName = nameField.getText();
            String newPassword = passwordField.getText();
            String newAgeStr = ageField.getText();

            if (newName.isEmpty() || newPassword.isEmpty() || newAgeStr.isEmpty()) {
                showAlert("Error", "All fields are required!");
                return;
            }

            int newAge;
            try {
                newAge = Integer.parseInt(newAgeStr);
                if (newAge <= 0 || newAge > 150) {
                    showAlert("Error", "Please enter a valid age!");
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Age must be a number!");
                return;
            }

            if (newPassword.length() < 4) {
                showAlert("Error", "Password must be at least 4 characters long!");
                return;
            }

            // Update user data in file
            if (updateUserInFile(currentPlayer.getName(), newName, newAge, newPassword)) {
                // Update the player object and session
                currentPlayer.setName(newName);
                currentPlayer.setAge(newAge);
                currentPlayer.setPassword(newPassword);
                UserSession.getInstance().setCurrentPlayer(currentPlayer);

                showAlert("Success", "Your information has been updated successfully!");

                try {
                    new Menu_page().start(new Stage());
                    stage9.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showAlert("Error", "Failed to update information. Please try again.");
            }
        });

        VBox cardPane = new VBox(40, headerBox, headerBox1, fields, okButton);
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #1b548d; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #ff7bac; " +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(600);
        cardPane.setMaxHeight(500);

        root.setCenter(cardPane);

        backButton.setOnAction(e -> {
            try {
                new Menu_page().start(new Stage());
                stage9.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(root, 800, 600);
        stage9.initStyle(StageStyle.UNDECORATED);
        stage9.setMaximized(true);
        stage9.setScene(scene);
        stage9.show();
    }

    private boolean updateUserInFile(String oldName, String newName, int newAge, String newPassword) {
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

                        // Check if this is the user to update
                        if (nameLine.equals("Name:" + oldName)) {
                            // Write updated user data
                            writer.write("Name:" + newName + "\n");
                            writer.write("Password:" + newPassword + "\n");

                            // Keep PIN the same
                            for (String userLine : currentUser) {
                                if (userLine.startsWith("PIN:")) {
                                    writer.write(userLine + "\n");
                                    break;
                                }
                            }

                            writer.write("Age:" + newAge + "\n");

                            // Keep coins and lives the same
                            for (String userLine : currentUser) {
                                if (userLine.startsWith("Coins:") || userLine.startsWith("Lives:")) {
                                    writer.write(userLine + "\n");
                                }
                            }

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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

    private void goBackToMenu() {
        System.out.println("Back to Menu");
    }
}