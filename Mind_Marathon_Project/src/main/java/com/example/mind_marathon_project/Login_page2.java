package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
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
import java.time.LocalDateTime;

public class Login_page2 extends Application {

    public void start(Stage stage3) throws Exception {
        CustomTitleBar customTitleBar = new CustomTitleBar(stage3);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ffffff;");
        root.setTop(customTitleBar);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #fffcf6;" +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #ff7bac; " +
                "-fx-padding: 20px;");
        vbox.setMaxWidth(600);
        vbox.setMaxHeight(550);

        // Back Button
        Button backButton = new Button();
        ImageView arrowImageView;
        try {
            arrowImageView = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/arrow.png").toExternalForm()));
        } catch (NullPointerException e) {
            throw e;
        }
        arrowImageView.setFitHeight(65);
        arrowImageView.setFitWidth(70);
        backButton.setGraphic(arrowImageView);
        addButtonEffects(backButton, "/com/example/mind_marathon_project/click_sound.mp3");
        backButton.setAlignment(Pos.TOP_LEFT);
        backButton.setStyle("-fx-background-color: #fffcf6;-fx-border-color: #FFFCF6FF");
        backButton.setOnAction(e -> {
            try {
                new Welcome_page().start(new Stage());
                stage3.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Login Image Header
        ImageView login;
        try {
            login = new ImageView(new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/logi.png")));
        } catch (NullPointerException e) {
            throw new RuntimeException("Image not found: /com/example/mind_marathon_project/logi.png", e);
        }
        login.setFitWidth(170);
        login.setFitHeight(90);

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.TOP_LEFT);
        headerBox.setSpacing(100);
        headerBox.setPadding(new Insets(0, 0, 10, 0));
        headerBox.getChildren().addAll(backButton, login);

        // Form Container
        StackPane formContainer = new StackPane();
        formContainer.setAlignment(Pos.CENTER);

        ImageView login_image;
        try {
            login_image = new ImageView(new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/log_pic.png")));
        } catch (NullPointerException e) {
            throw e;
        }
        login_image.setFitWidth(350);
        login_image.setFitHeight(350);

        VBox formFields = new VBox(40);
        formFields.setAlignment(Pos.CENTER);
        formFields.setPadding(new Insets(10));

        // Name Field
        TextField nameField = new TextField();
        nameField.setMaxWidth(150);
        nameField.setMinHeight(40);
        nameField.setPromptText("\tEnter your name");
        nameField.setStyle("-fx-background-color:  #fffcf6;-fx-background-radius: 20px;-fx-border-width: 2px;" +
                "-fx-border-radius: 20px;-fx-prompt-text-fill: #675c6e;-fx-border-color: #b78fd6;" +
                "-fx-text-fill: #3c6ca8;-fx-font-weight: bold;-fx-font-family: Calibri;" +
                "-fx-padding: 5px;-fx-font-size:14px; ");

        // Password Field
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(150);
        passwordField.setMinHeight(40);
        passwordField.setPromptText("  Enter your password");
        passwordField.setStyle("-fx-background-color: #fffcf6;-fx-prompt-text-fill: #675c6e;" +
                "-fx-border-color: #b78fd6; " +
                "-fx-border-radius: 20px;-fx-border-width: 2px;" +
                "-fx-background-radius: 20px; " +
                "-fx-padding: 5px; " +
                "-fx-font-size: 14px;-fx-text-fill: #3c6ca8;-fx-font-weight: bold;-fx-font-family: Calibri;");

        formFields.getChildren().addAll(nameField, passwordField);
        formContainer.getChildren().addAll(login_image, formFields);

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #ffeb79;-fx-border-color:#ff7bac;" +
                "-fx-border-width: 2px;-fx-border-radius: 20px;-fx-text-fill: #3c6ca8;" +
                "-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;" +
                "-fx-font-size: 18px;-fx-padding: 10 20");
        try {
            addButtonEffects(loginButton, "/com/example/mind_marathon_project/main_button.mp3");
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }

        loginButton.setOnAction(e -> {
            String name = nameField.getText();
            String password = passwordField.getText();

            // Validation
            if (name.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please fill in both username and password!");
                return;
            }

            // Authenticate
            if (authenticate(name, password)) {
                showAlert("Success", "Login successful! Welcome back, " + name + "!");
                try {
                    new Menu_page().start(new Stage());
                    stage3.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showAlert("Error", "Invalid username or password!");
                nameField.clear();
                passwordField.clear();
                nameField.setPromptText("Try again");
                passwordField.setPromptText("Try again");
            }
        });

        // Forgot Password Link
        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.setStyle("-fx-text-fill: #3c6ca8; -fx-font-size: 14px; -fx-font-weight: bold;");
        forgotPasswordLink.setOnAction(e -> {
            try {
                new ForgotPasswordPage().start(new Stage());
                stage3.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Create New Account Link
        Hyperlink createAccountLink = new Hyperlink("Don't have an account? Sign Up");
        createAccountLink.setStyle("-fx-text-fill: #3c6ca8; -fx-font-size: 14px; -fx-font-weight: bold;");
        createAccountLink.setOnAction(e -> {
            try {
                new Login_page().start(new Stage());
                stage3.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Links Container
        VBox linksBox = new VBox(5);
        linksBox.setAlignment(Pos.CENTER);
        linksBox.getChildren().addAll(forgotPasswordLink, createAccountLink);

        vbox.setSpacing(10);
        vbox.getChildren().addAll(headerBox, formContainer, loginButton, linksBox);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);
        stage3.initStyle(StageStyle.UNDECORATED);
        stage3.setMaximized(true);
        stage3.setScene(scene);
        stage3.show();
    }

    boolean authenticate(String name, String password) {
        File file = new File("user_data.txt");

        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String storedName = null;
            String storedPassword = null;
            String storedPin = null;
            int storedAge = 0;
            int storedCoins = 10;
            int storedLives = 3;
            int storedTotalScore = 0;
            int storedHints = 0;
            java.time.LocalDateTime storedLastLifeRegen = java.time.LocalDateTime.now();
            String storedProfileImage = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name:")) {
                    storedName = line.substring(5).trim();
                } else if (line.startsWith("Password:")) {
                    storedPassword = line.substring(9).trim();
                } else if (line.startsWith("PIN:")) {
                    storedPin = line.substring(4).trim();
                } else if (line.startsWith("Age:")) {
                    try {
                        storedAge = Integer.parseInt(line.substring(4).trim());
                    } catch (NumberFormatException e) {
                        storedAge = 0;
                    }
                } else if (line.startsWith("Coins:")) {
                    try {
                        storedCoins = Integer.parseInt(line.substring(6).trim());
                    } catch (NumberFormatException e) {
                        storedCoins = 10;
                    }
                } else if (line.startsWith("Lives:")) {
                    try {
                        storedLives = Integer.parseInt(line.substring(6).trim());
                    } catch (NumberFormatException e) {
                        storedLives = 3;
                    }
                } else if (line.startsWith("TotalScore:")) {
                    try {
                        storedTotalScore = Integer.parseInt(line.substring(11).trim());
                    } catch (NumberFormatException e) {
                        storedTotalScore = 0;
                    }
                } else if (line.startsWith("Hints:")) {
                    try {
                        storedHints = Integer.parseInt(line.substring(6).trim());
                    } catch (NumberFormatException e) {
                        storedHints = 0;
                    }
                } else if (line.startsWith("LastLifeRegen:")) {
                    try {
                        storedLastLifeRegen = java.time.LocalDateTime.parse(line.substring(14).trim());
                    } catch (Exception e) {
                        storedLastLifeRegen = java.time.LocalDateTime.now();
                    }
                } else if (line.startsWith("ProfileImage:")) {
                    storedProfileImage = line.substring(13).trim();
                    if (storedProfileImage.isEmpty()) {
                        storedProfileImage = null;
                    }
                } else if (line.equals("---")) {
                    // Check if credentials match
                    if (storedName != null && storedName.equals(name) &&
                            storedPassword != null && storedPassword.equals(password)) {

                        // Create player object and store in session
                        Player player = new Player(storedName, storedAge, storedPassword,
                                storedPin != null ? storedPin : "",
                                storedCoins, storedLives, storedTotalScore,
                                storedHints, storedLastLifeRegen, storedProfileImage);
                        UserSession.getInstance().setCurrentPlayer(player);
                        return true;
                    }

                    // Reset for next user
                    storedName = null;
                    storedPassword = null;
                    storedPin = null;
                    storedAge = 0;
                    storedCoins = 10;
                    storedLives = 3;
                    storedTotalScore = 0;
                    storedHints = 0;
                    storedLastLifeRegen = java.time.LocalDateTime.now();
                    storedProfileImage = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
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

    public static void main(String[] args) {
        launch();
    }
}