package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class ForgotPasswordPage extends Application {

    @Override
    public void start(Stage stage) {
        CustomTitleBar customTitleBar = new CustomTitleBar(stage);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTop(customTitleBar);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #fffcf6;" +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #ff7bac; " +
                "-fx-padding: 30px;");
        vbox.setMaxWidth(500);
        vbox.setMaxHeight(450);

        // Back Button
        Button backButton = new Button();
        ImageView arrowImageView=null;
        try {
            arrowImageView = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/arrow.png").toExternalForm()));
        } catch (NullPointerException e) {
            // If image not found, create a text-based back button
            backButton.setText("← Back");
            backButton.setStyle("-fx-background-color: #fffcf6;-fx-border-color: #FFFCF6FF;" +
                    "-fx-text-fill: #3c6ca8;-fx-font-weight: bold;-fx-font-size: 16px;");
        }

        if (arrowImageView != null) {
            arrowImageView.setFitHeight(50);
            arrowImageView.setFitWidth(50);
            backButton.setGraphic(arrowImageView);
        }

        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setStyle("-fx-background-color: #fffcf6;-fx-border-color: #FFFCF6FF");
        backButton.setOnAction(e -> {
            try {
                new Login_page2().start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Title
        Label titleLabel = new Label("Reset Password");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; " +
                "-fx-text-fill: #3c6ca8; -fx-font-family: Calibri;");

        // Subtitle
        Label subtitleLabel = new Label("Enter your username and recovery PIN");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #675c6e; " +
                "-fx-font-family: Calibri;");

        // Username Field
        TextField nameField = new TextField();
        nameField.setMaxWidth(300);
        nameField.setMinHeight(40);
        nameField.setPromptText("Username");
        nameField.setStyle("-fx-background-color: #fffcf6;" +
                "-fx-border-color: #b78fd6;" +
                "-fx-border-radius: 20px;" +
                "-fx-border-width: 2px;" +
                "-fx-background-radius: 20px;" +
                "-fx-text-fill: #3c6ca8;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Calibri;" +
                "-fx-padding: 5px;" +
                "-fx-font-size: 14px;" +
                "-fx-prompt-text-fill: #675c6e;");

        // PIN Field
        TextField pinField = new TextField();
        pinField.setMaxWidth(300);
        pinField.setMinHeight(40);
        pinField.setPromptText("4-digit Recovery PIN");
        pinField.setStyle("-fx-background-color: #fffcf6;" +
                "-fx-border-color: #b78fd6;" +
                "-fx-border-radius: 20px;" +
                "-fx-border-width: 2px;" +
                "-fx-background-radius: 20px;" +
                "-fx-text-fill: #3c6ca8;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Calibri;" +
                "-fx-padding: 5px;" +
                "-fx-font-size: 14px;" +
                "-fx-prompt-text-fill: #675c6e;");

        // New Password Field
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setMaxWidth(300);
        newPasswordField.setMinHeight(40);
        newPasswordField.setPromptText("New Password");
        newPasswordField.setStyle("-fx-background-color: #fffcf6;" +
                "-fx-border-color: #b78fd6;" +
                "-fx-border-radius: 20px;" +
                "-fx-border-width: 2px;" +
                "-fx-background-radius: 20px;" +
                "-fx-text-fill: #3c6ca8;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Calibri;" +
                "-fx-padding: 5px;" +
                "-fx-font-size: 14px;" +
                "-fx-prompt-text-fill: #675c6e;");

        // Confirm Password Field
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setMinHeight(40);
        confirmPasswordField.setPromptText("Confirm New Password");
        confirmPasswordField.setStyle("-fx-background-color: #fffcf6;" +
                "-fx-border-color: #b78fd6;" +
                "-fx-border-radius: 20px;" +
                "-fx-border-width: 2px;" +
                "-fx-background-radius: 20px;" +
                "-fx-text-fill: #3c6ca8;" +
                "-fx-font-weight: bold;" +
                "-fx-font-family: Calibri;" +
                "-fx-padding: 5px;" +
                "-fx-font-size: 14px;" +
                "-fx-prompt-text-fill: #675c6e;");

        // Reset Button
        Button resetButton = new Button("Reset Password");
        resetButton.setStyle("-fx-background-color: #ffeb79;" +
                "-fx-border-color: #ff7bac;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 20px;" +
                "-fx-text-fill: #3c6ca8;" +
                "-fx-background-radius: 20px;" +
                "-fx-font-family: Calibri;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 16px;" +
                "-fx-padding: 10 30;");
        addButtonEffects(resetButton);

        resetButton.setOnAction(e -> {
            String username = nameField.getText();
            String pin = pinField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Validation
            if (username.isEmpty() || pin.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("Error", "All fields are required!");
                return;
            }

            if (!pin.matches("\\d{4}")) {
                showAlert("Error", "PIN must be exactly 4 digits!");
                return;
            }

            if (newPassword.length() < 4) {
                showAlert("Error", "Password must be at least 4 characters long!");
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                showAlert("Error", "Passwords do not match!");
                confirmPasswordField.clear();
                return;
            }

            // Reset Password
            if (resetPassword(username, pin, newPassword)) {
                showAlert("Success", "Password reset successfully!\nYou can now login with your new password.");
                try {
                    new Login_page2().start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showAlert("Error", "Invalid username or PIN!\nPlease check your credentials and try again.");
                nameField.clear();
                pinField.clear();
                newPasswordField.clear();
                confirmPasswordField.clear();
            }
        });

        // Back to Login Link
        Hyperlink backToLoginLink = new Hyperlink("Back to Login");
        backToLoginLink.setStyle("-fx-text-fill: #3c6ca8; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-underline: true;");
        backToLoginLink.setOnAction(e -> {
            try {
                new Login_page2().start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Create New Account Link
        Hyperlink createAccountLink = new Hyperlink("Don't have an account? Sign Up");
        createAccountLink.setStyle("-fx-text-fill: #3c6ca8; -fx-font-size: 14px; " +
                "-fx-font-weight: bold; -fx-underline: true;");
        createAccountLink.setOnAction(e -> {
            try {
                new Login_page().start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add all components
        HBox headerBox = new HBox(backButton);
        headerBox.setAlignment(Pos.TOP_LEFT);

        VBox linksBox = new VBox(5);
        linksBox.setAlignment(Pos.CENTER);
        linksBox.getChildren().addAll(backToLoginLink, createAccountLink);

        vbox.getChildren().addAll(
                headerBox,
                titleLabel,
                subtitleLabel,
                nameField,
                pinField,
                newPasswordField,
                confirmPasswordField,
                resetButton,
                linksBox
        );

        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    boolean resetPassword(String username, String pin, String newPassword) {
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
                        String passwordLine = currentUser.get(1);
                        String pinLine = currentUser.get(2);

                        // Check if this is the user to update
                        if (nameLine.equals("Name:" + username) && pinLine.equals("PIN:" + pin)) {
                            // Write updated user data
                            writer.write(nameLine + "\n");
                            writer.write("Password:" + newPassword + "\n");
                            writer.write(pinLine + "\n");

                            // Write remaining lines (like Age if present)
                            for (int i = 3; i < currentUser.size(); i++) {
                                writer.write(currentUser.get(i) + "\n");
                            }

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

        // Style the alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #fffcf6;");

        alert.showAndWait();
    }

    private void addButtonEffects(Button button) {
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

    public static void main(String[] args) {
        launch();
    }
}