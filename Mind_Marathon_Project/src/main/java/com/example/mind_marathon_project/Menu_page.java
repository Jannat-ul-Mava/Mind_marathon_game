package com.example.mind_marathon_project;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu_page extends Application {

    private ImageView profileImageView;
    private List<Image> preloadedImages;
    private static Image selectedProfileImage;
    private Player currentPlayer;
    private Label hintCountLabel;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage5) throws Exception {
        currentPlayer = UserSession.getInstance().getCurrentPlayer();
        if (currentPlayer == null) {
            try {
                new Login_page2().start(new Stage());
                stage5.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        currentPlayer.regenerateLives();
        updatePlayerInFile(); // Save the updated lives to file
        UserSession.getInstance().setCurrentPlayer(currentPlayer);
        preloadedImages = loadPreloadedImages();
        CustomTitleBar customTitleBar = new CustomTitleBar(stage5);
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fffcf6;");
        root.setTop(customTitleBar);

        VBox cardPane = new VBox();
        cardPane.setAlignment(Pos.CENTER);
        cardPane.setStyle("-fx-background-color: #e0ecff; " +
                "-fx-background-radius: 20px; " +
                "-fx-border-radius: 20px; " +
                "-fx-border-color: #365aa8; " +
                "-fx-padding: 20px;");
        cardPane.setMaxWidth(650);
        cardPane.setMaxHeight(500);

        ImageView coin=new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/coins_button.png").toExternalForm()));
        coin.setFitHeight(30);
        coin.setFitWidth(35);
        Label coinValue=new Label(String.valueOf(currentPlayer.getCoins()));
        coinValue.setAlignment(Pos.CENTER);
        coinValue.setStyle("-fx-text-fill: black;");
        HBox coinBox=new HBox();
        coinBox.getChildren().addAll(coin,coinValue);
        coinBox.setAlignment(Pos.TOP_RIGHT);

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
        ImageView hintImage = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/hint_button.png").toExternalForm()));
        hintImage.setFitHeight(35);
        hintImage.setFitWidth(35);
        hintCountLabel = new Label(String.valueOf(currentPlayer.getHints()));
        hintCountLabel.setAlignment(Pos.CENTER);
        hintCountLabel.setStyle("-fx-text-fill: black;");
        HBox hintBox = new HBox();
        hintBox.setSpacing(4);
        hintBox.getChildren().addAll(hintImage, hintCountLabel);
        hintBox.setAlignment(Pos.TOP_RIGHT);

        VBox sideBox=new VBox();
        sideBox.setAlignment(Pos.TOP_RIGHT);
        sideBox.setSpacing(10);
        sideBox.getChildren().addAll(coinBox,heartBox,hintBox);

        // Border circle for profile image
        Circle borderCircle = new Circle(75);
        borderCircle.setStyle("-fx-fill: rgba(255,228,122,0.71); -fx-stroke: #5eb090; -fx-stroke-width: 7;");

        // Profile image circle - FIXED: Always set size and clipping
        profileImageView = new ImageView();
        profileImageView.setFitWidth(150); // Set fixed size
        profileImageView.setFitHeight(150);
        profileImageView.setPreserveRatio(false);
        profileImageView.setClip(new Circle(75, 75, 75)); // Circle clipping

        if (selectedProfileImage != null) {
            profileImageView.setImage(selectedProfileImage);
        }

        StackPane profileImageStack = new StackPane();
        profileImageStack.getChildren().addAll(borderCircle, profileImageView);
        profileImageStack.setAlignment(Pos.CENTER);

        // "Choose Image" button
        Button chooseImageButton = new Button("Choose image");
        chooseImageButton.setStyle("-fx-background-color: #fdf58e; -fx-font-size: 14px; -fx-border-color: #ffc107;-fx-padding: 3px;");
        addButtonEffects(chooseImageButton,"/com/example/mind_marathon_project/click_sound.mp3");
        chooseImageButton.setOnAction(e -> openImageSelectionDialog(stage5));

        // Name and Age labels
        Label nameLabel = new Label(String.valueOf(currentPlayer.getName()));
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000;");

        Label ageLabel = new Label(String.valueOf(currentPlayer.getAge()));
        ageLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #000;");

        HBox labels = new HBox(20,nameLabel, ageLabel);
        labels.setAlignment(Pos.CENTER);
        VBox profileSection = new VBox(10, profileImageStack, labels);
        profileSection.setAlignment(Pos.CENTER);
        HBox ImageStack=new HBox();
        ImageStack.setSpacing(40);
        ImageStack.getChildren().addAll(profileSection,chooseImageButton);
        ImageStack.setAlignment(Pos.CENTER);
        if (currentPlayer.getProfileImagePath() != null && !currentPlayer.getProfileImagePath().isEmpty()) {
            try {
                if (currentPlayer.getProfileImagePath().startsWith("/")) {
                    // Resource path
                    Image savedImage = new Image(getClass().getResource(currentPlayer.getProfileImagePath()).toExternalForm());
                    profileImageView.setImage(savedImage);
                    selectedProfileImage = savedImage;
                } else {
                    // File path
                    File imageFile = new File(currentPlayer.getProfileImagePath());
                    if (imageFile.exists()) {
                        Image savedImage = new Image(new FileInputStream(imageFile));
                        profileImageView.setImage(savedImage);
                        selectedProfileImage = savedImage;
                    }
                }
            } catch (Exception e) {
                System.err.println("Error loading profile image: " + e.getMessage());
            }
        }
        // Top-left and top-right buttons
        Button Button1 = new Button();
        ImageView imageView1 ;
        try {
            imageView1 = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/award_png.png").toExternalForm()));
        }catch(NullPointerException e){
            throw e;
        }
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        Button1.setGraphic(imageView1);
        Button1.setStyle("-fx-background-color: #3c6ca8;-fx-border-color: #ffe47a;-fx-border-width: 3px;-fx-background-radius: 10px;-fx-border-radius:7px;-fx-padding:4;-fx-background-size: 2");

        Button Button2 = new Button();
        ImageView imageView ;
        try {
            imageView = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/edit_png.png").toExternalForm()));
        }catch(NullPointerException e){
            throw e;
        }
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        Button2.setGraphic(imageView);
        Button2.setStyle("-fx-background-color: #3c6ca8;-fx-border-color: #ffe47a;-fx-border-width: 3px;-fx-background-radius: 10px;-fx-border-radius:7px;-fx-padding:4;-fx-background-size: 2");

        addButtonEffects(Button2,"/com/example/mind_marathon_project/click_sound.mp3");
        addButtonEffects(Button1,"/com/example/mind_marathon_project/click_sound.mp3");

        // Bottom-left and bottom-right buttons
        Button Button3 = new Button();
        ImageView imageView3 ;
        try {
            imageView3 = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/info_png.png").toExternalForm()));
        }catch(NullPointerException e){
            throw e;
        }
        imageView3.setFitWidth(30);
        imageView3.setFitHeight(30);
        Button3.setGraphic(imageView3);
        Button3.setStyle("-fx-background-color:#3c6ca8;-fx-border-color: #ffe47a;-fx-border-width: 3px;-fx-background-radius: 10px;-fx-border-radius:7px;-fx-padding:4;-fx-background-size: 2");

        Button Button4 = new Button();
        ImageView imageView4 ;
        try {
            imageView4 = new ImageView(new Image(getClass().getResource("/com/example/mind_marathon_project/shop_png.png").toExternalForm()));
        }catch(NullPointerException e){
            throw e;
        }
        imageView4.setFitWidth(30);
        imageView4.setFitHeight(30);
        Button4.setGraphic(imageView4);
        Button4.setStyle("-fx-background-color: #3c6ca8;-fx-border-color: #ffe47a;-fx-border-width: 3px;-fx-background-radius: 10px;-fx-border-radius:7px;-fx-padding:4;-fx-background-size: 2");

        VBox sideButtons = new VBox(10, Button1, Button2, Button3, Button4);
        sideButtons.setAlignment(Pos.TOP_RIGHT);
        addButtonEffects(Button3,"/com/example/mind_marathon_project/shop_png.png");
        addButtonEffects(Button4,"/com/example/mind_marathon_project/info_png.png");

        // Play and Exit buttons
        Button playButton = new Button("\t    Play\t\t  ");
        playButton.setStyle("-fx-background-color: #ffe47a; -fx-background-radius: 10px;-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #000;-fx-padding: 10;");
        playButton.setOnAction(e -> System.out.println("Play  clicked"));
        addButtonEffects(playButton,"/com/example/mind_marathon_project/click_sound.mp3");
        Button exitButton = new Button("  \t     Exit       \t ");
        exitButton.setStyle("-fx-background-color: #ffe47a;  -fx-background-radius: 10px;-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #000;-fx-padding: 10;");
        addButtonEffects(exitButton,"/com/example/mind_marathon_project/click_sound.mp3");

        VBox bottomBox = new VBox(playButton, exitButton);
        bottomBox.setStyle("-fx-background-color: #3c6ca8;-fx-background-radius: 20px;-fx-border-color: #439576;-fx-border-radius: 20px;-fx-border-width: 2;-fx-padding: 20,20,20,20");
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setMaxWidth(350);
        bottomBox.setSpacing(10);

        HBox centerBox = new HBox(10, sideButtons);
        centerBox.setSpacing(250);
        centerBox.setAlignment(Pos.CENTER_LEFT);

        HBox rightBox = new HBox(10,centerBox,ImageStack);
        rightBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setSpacing(130);
        HBox leftBox = new HBox(10,rightBox,sideBox);
        leftBox.setAlignment(Pos.TOP_RIGHT);
        leftBox.setSpacing(80);

        cardPane.getChildren().addAll(leftBox, bottomBox);

        root.setCenter(cardPane);

        playButton.setOnAction(e->{
            if(playButton.isHover()){
                try {
                    if (currentPlayer.getLives() <= 0) {
                        showNoLivesAlert(stage5);
                        return;
                    }
                    else{
                    new Interest_page().start(new Stage());
                    stage5.close();}
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        exitButton.setOnAction(e->{
            if (exitButton.isHover()) {
                try {
                    new exit_page().start(new Stage());
                    stage5.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        Button1.setOnAction(e->{
            if(Button1.isHover()){}
            try {
                new Achievements_page().start(new Stage());
                stage5.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button2.setOnAction(e->{
            if(Button2.isHover()){}
            try {
                new Edit_page().start(new Stage());
                stage5.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button3.setOnAction(e->{
            if(Button3.isHover()){}
            try {
                new info_page().start(new Stage());
                stage5.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Button4.setOnAction(e->{
            if(Button4.isHover()){}
            try {
                new ShopStop_page().start(new Stage());
                stage5.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(root, 800, 600);
        stage5.initStyle(StageStyle.UNDECORATED);
        stage5.setMaximized(true);
        stage5.setScene(scene);
        stage5.show();
    }

    private void chooseImageFromFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Image profileImage = new Image(new FileInputStream(file));
                selectedProfileImage = profileImage;
                profileImageView.setImage(profileImage);

                // Save image path to player
                currentPlayer.setProfileImagePath(file.getAbsolutePath());
                updatePlayerInFile();
                UserSession.getInstance().setCurrentPlayer(currentPlayer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void showNoLivesAlert(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Lives");
        alert.setHeaderText("You don't have any lives!");

        long minutesUntilNext = currentPlayer.getMinutesUntilNextLife();
        alert.setContentText("You need at least 1 life to play.\n" +
                "Next life in: " + minutesUntilNext + " minutes\n" +
                "Or buy a life from the Shop!");

        alert.showAndWait();

        try {
            new Menu_page().start(new Stage());
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openImageSelectionDialog(Stage stage) {
        VBox dialogContent = new VBox(10);
        dialogContent.setPadding(new Insets(20));
        dialogContent.setStyle("-fx-background-color: #1b548d; -fx-border-color: #5eb090; -fx-border-width: 2;");
        dialogContent.setAlignment(Pos.BASELINE_CENTER);

        Label instructions = new Label("Select an avatar:");
        instructions.setStyle("-fx-font-size: 16px;-fx-text-fill: #ffffff; -fx-font-weight: bold;");

        HBox preloadedImagesBox = new HBox(10);
        preloadedImagesBox.setAlignment(Pos.CENTER);
        for (Image image : preloadedImages) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            StackPane imageContainer = new StackPane(imageView);
            imageContainer.setStyle("-fx-padding: 5; -fx-border-radius: 10; -fx-background-radius: 10;-fx-background-color: #fff4f4;-fx-border-color:#439576 ");

            imageView.setOnMouseClicked(e -> {
                selectedProfileImage = image;
                profileImageView.setImage(image);
                animateImageSelection(imageContainer);
                imageContainer.setStyle("-fx-background-color: #fff99f; -fx-border-radius: 10; -fx-background-radius: 10;");

                // Save avatar selection to player (save resource path)
                int imageIndex = preloadedImages.indexOf(image) + 1;
                currentPlayer.setProfileImagePath("/com/example/mind_marathon_project/avatar" + imageIndex + ".png");
                updatePlayerInFile();
                UserSession.getInstance().setCurrentPlayer(currentPlayer);
            });

            preloadedImagesBox.getChildren().add(imageContainer);
        }

        Button chooseFromFileButton = new Button("Choose from computer");
        chooseFromFileButton.setStyle("-fx-background-color: #ffde59; -fx-font-size: 14px;");
        chooseFromFileButton.setOnAction(e -> chooseImageFromFile(stage));
        addButtonEffects(chooseFromFileButton,"/com/example/mind_marathon_project/click_sound.mp3");
        dialogContent.getChildren().addAll(instructions, preloadedImagesBox, chooseFromFileButton);

        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setScene(new Scene(dialogContent));
        dialog.show();
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

    private void animateImageSelection(StackPane imageContainer) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), imageContainer);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        imageContainer.setStyle("-fx-border-color: #3c6ca8; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
    }

    private List<Image> loadPreloadedImages() {
        List<Image> images = new ArrayList<>();
        try {
            images.add(new Image(getClass().getResource("/com/example/mind_marathon_project/avatar1.png").toExternalForm()));
            images.add(new Image(getClass().getResource("/com/example/mind_marathon_project/avatar2.png").toExternalForm()));
            images.add(new Image(getClass().getResource("/com/example/mind_marathon_project/avatar3.png").toExternalForm()));
            images.add(new Image(getClass().getResource("/com/example/mind_marathon_project/avatar4.png").toExternalForm()));
            images.add(new Image(getClass().getResource("/com/example/mind_marathon_project/avatar5.png").toExternalForm()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
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
}