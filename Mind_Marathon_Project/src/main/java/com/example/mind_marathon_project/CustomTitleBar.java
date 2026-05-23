package com.example.mind_marathon_project;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class CustomTitleBar extends HBox{
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomTitleBar(Stage stage) {
        // Set style and alignment for title bar
        this.setStyle("-fx-background-color: #1b548d;-fx-padding:5,5,5,5,5;");// Custom color
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);

        // image tag
        ImageView imageView;
        try {
            imageView = new ImageView(new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/title_bar.png")));
        } catch (NullPointerException e) {
            throw new RuntimeException("Image not found: /com/example/mind_marathon_project/title_bar.png", e);
        }
        try {
            Image icon = new Image(getClass().getResourceAsStream("/com/example/mind_marathon_project/title_bar.png"));
            stage.getIcons().add(icon);
        } catch (NullPointerException e) {
            System.out.println("Game icon not found: " + e.getMessage());
        }
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        Circle clip = new Circle(20.5, 19,17);
        clip.setStyle("-fx-padding: 20 20 20 20 20 20");
        imageView.setClip(clip);

        // Title label
        Label title1 = new Label("Mind");
        title1.setStyle("-fx-text-fill: #ffffff;"+"-fx-font-family:'Comic Sans MS';"+"-fx-font-weight: bold;");
        Label title2 = new Label("Marathon");
        title2.setStyle("-fx-text-fill: #f1f5f6;"+"-fx-font-family:'Comic Sans MS';"+"-fx-font-weight: bold;");

        // Minimize and close buttons
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button minimizeButton = new Button("–");
        Button closeButton = new Button("X");
        Button maximizebutton = new Button("⬜");
        minimizeButton.setStyle(" -fx-background-color:#ffffff;-fx-text-fill: #675c6e; -fx-cursor: hand ;");
        maximizebutton.setStyle("-fx-background-color:#ffffff;-fx-text-fill: #675c6e;-fx-cursor: hand ;");
        closeButton.setStyle(" -fx-background-color:#ffffff;-fx-text-fill: #675c6e;-fx-cursor:  hand;");
        // Close button action
        closeButton.setOnAction(e -> stage.close());
        boolean[] isMaximized = {false}; // Start with the assumption that the window is not maximized
        maximizebutton.setOnAction(e -> {
            if (!isMaximized[0]) {
                stage.setWidth(800);
                stage.setHeight(600);
                stage.centerOnScreen();
                stage.setFullScreen(false);
                isMaximized[0] = true;

            } else {
                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");
                isMaximized[0] = false;

            }
        });

        // Minimize button action
        minimizeButton.setOnAction(e -> stage.setIconified(true));

        // Add elements to the custom title bar
        this.getChildren().addAll(imageView,title1,title2, spacer,minimizeButton, maximizebutton,closeButton);

        // Make the title bar draggable
        this.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
