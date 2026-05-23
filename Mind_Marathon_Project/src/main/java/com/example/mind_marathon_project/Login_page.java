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

public class Login_page extends Application {

    @Override
    public void start(Stage stage2) {
        CustomTitleBar customTitleBar = new CustomTitleBar(stage2);
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
        vbox.setMaxHeight(500);

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
                stage2.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Login Image
        ImageView login;
        try {
            login = new ImageView(new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/login.png")));
        } catch (NullPointerException e) {
            throw new RuntimeException("Image not found: /com/example/mind_marathon_project/login.png", e);
        }
        login.setFitWidth(200);
        login.setFitHeight(90);

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.TOP_LEFT);
        headerBox.setSpacing(100);
        headerBox.setPadding(new Insets(0, 0, 10, 0));
        headerBox.getChildren().addAll(backButton, login);

        // Form Container
        StackPane formContainer = new StackPane();
        formContainer.setAlignment(Pos.CENTER);

        ImageView loginImage;
        try {
            loginImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/log_pic.png")));
        } catch (NullPointerException e) {
            throw new RuntimeException("Image not found: /com/example/mind_marathon_project/log_pic.png", e);
        }
        loginImage.setFitWidth(350);
        loginImage.setFitHeight(350);

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

        // Age Slider
        Slider ageSlider = new Slider();
        ageSlider.setMin(4);
        ageSlider.setMax(60);
        ageSlider.setPrefWidth(80);
        ageSlider.setStyle(
                "-fx-control-inner-background: #79ccab ;" +
                        "-fx-background-color: #1b548d;" +
                        "-fx-border-color: transparent;" +
                        "-fx-base: #b78fd6;"
        );

        Label ageLabel = new Label("0");
        ageLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 14px;-fx-font-weight: bold");
        ageSlider.valueProperty().addListener((obs, oldVal, newVal) -> ageLabel.setText(String.valueOf(newVal.intValue())));
        Label age1 = new Label("Age:");
        age1.setStyle("-fx-text-fill: #ffffff;-fx-font-size: 14px;-fx-font-weight: bold");
        HBox ageContainer = new HBox(10, age1, ageSlider, ageLabel);
        ageContainer.setAlignment(Pos.CENTER);

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

        // PIN Field
        TextField pinField = new TextField();
        pinField.setMaxWidth(150);
        pinField.setMinHeight(40);
        pinField.setPromptText("Enter 4-digit PIN");
        pinField.setStyle(
                "-fx-background-color: #fffcf6;" +
                        "-fx-border-color: #b78fd6;" +
                        "-fx-border-radius: 20px;" +
                        "-fx-border-width: 2px;" +
                        "-fx-background-radius: 20px;" +
                        "-fx-text-fill: #3c6ca8;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-family: Calibri;" +
                        "-fx-padding: 5px;" +
                        "-fx-font-size: 14px;" +
                        "-fx-prompt-text-fill: #675c6e;"
        );

        formFields.getChildren().addAll(nameField, ageContainer, passwordField, pinField);
        formContainer.getChildren().addAll(loginImage, formFields);

        // OK Button
        Button okButton = new Button("\t  OK\t\t");
        okButton.setStyle("-fx-background-color: #fff069;-fx-border-color:#f67fa3;" +
                "-fx-border-width: 2px;-fx-border-radius: 20px;-fx-text-fill: #3c6ca8;" +
                "-fx-background-radius: 20px;-fx-font-family: Calibri;-fx-font-weight:bold;" +
                "-fx-font-size: 18px;-fx-padding: 10 20");
        addButtonEffects(okButton, "/com/example/mind_marathon_project/main_button.mp3");

        okButton.setOnAction(e -> {
            String name = nameField.getText();
            String password = passwordField.getText();
            String pin = pinField.getText();
            String age = ageLabel.getText();

            // Validation
            if (name.isEmpty() || password.isEmpty() || pin.isEmpty()) {
                showAlert("Error", "All fields are required!");
                return;
            }

            if (age.equals("0")) {
                showAlert("Error", "Please select your age!");
                return;
            }

            if (!pin.matches("\\d{4}")) {
                showAlert("Error", "PIN must be exactly 4 digits");
                return;
            }

            if (password.length() < 4) {
                showAlert("Error", "Password must be at least 4 characters long");
                return;
            }

            // Check if username already exists
            if (usernameExists(name)) {
                showAlert("Error", "Username already exists! Please choose a different name.");
                return;
            }

            // Store user data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt", true))) {
                writer.write("Name:" + name + "\n");
                writer.write("Password:" + password + "\n");
                writer.write("PIN:" + pin + "\n");
                writer.write("Age:" + age + "\n");
                writer.write("Coins:" + 10 + "\n");  // Give 10 coins initially
                writer.write("Lives:" + 3 + "\n");
                writer.write("TotalScore:" + 0 + "\n");
                writer.write("---\n");
            } catch (IOException ex) {
                ex.printStackTrace();
                showAlert("Error", "Failed to save account. Please try again.");
                return;
            }

            showAlert("Success", "Account created successfully!");

            try {
                new Login_page2().start(new Stage());
                stage2.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vbox.setSpacing(10);
        vbox.getChildren().addAll(headerBox, formContainer, okButton);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 600);
        stage2.initStyle(StageStyle.UNDECORATED);
        stage2.setMaximized(true);
        stage2.setScene(scene);
        stage2.show();
    }

    private boolean usernameExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("Name:" + username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, so no users exist
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}