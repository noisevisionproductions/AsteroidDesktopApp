package com.asteroids.Scenes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.asteroids.Characters.SharedAtomicInteger;
import com.asteroids.Characters.Ship;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SceneManager {
    @FXML
    private Pane pane;
    @FXML
    private AnchorPane anchorPaneOfImages;
    @FXML
    private Button choosePreviousImage;
    @FXML
    private Button chooseNextImage;
    @FXML
    public static ImageView chosenShip;
    @FXML
    private ImageView buttonImage;

    private AtomicInteger currentImageIndex;
    private ImageView[] shipImages;

    private GameStateScene gameStateScene;
    private Ship ship;

    private ScreenSize screenSize;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public SceneManager(Stage stage) throws IOException {
        this.stage = stage;
    }

    public SceneManager() {
        this.currentImageIndex = SharedAtomicInteger.getCurrentImageIndex();
        this.screenSize = new ScreenSize();
    }

    public Ship setShip(ImageView chosenShip) {
        this.ship = new Ship(chosenShip, 50, 50, screenSize);
        return this.ship;
    }

    @FXML
    public void initialize() throws IOException {
        setLookOfShipButtons();

        setShip(chosenShip);
        shipImages = ship.getImages();

        int currentAtomicIndex = currentImageIndex.get();

        List<Boolean> visibilityStates = new ArrayList<>(Collections.nCopies(shipImages.length, false));
        for (int i = 0; i < shipImages.length; i++) {
            chosenShip = shipImages[i];

            chosenShip.setFitWidth(150);
            chosenShip.setFitHeight(150);
            chosenShip.setY(60);
            chosenShip.setX(70);
            chosenShip.setVisible(visibilityStates.get(i));

            anchorPaneOfImages.getChildren().add(chosenShip);
            shipImages[currentAtomicIndex].setVisible(true);
        }
        handleShipButtons();
    }

    public void setLookOfShipButtons() {
        choosePreviousImage.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        Image newImage = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/arowButtonLeftNew.png");
        buttonImage = new ImageView(newImage);
        choosePreviousImage.setGraphic(buttonImage);

        chooseNextImage.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        Image newImage2 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/arrowButtonRR.png");
        ImageView buttonImage2 = new ImageView(newImage2);
        chooseNextImage.setGraphic(buttonImage2);
    }

    public void handleShipButtons() {
        chooseNextImage.setOnAction(event -> {
            int currentIndex = currentImageIndex.get();
            shipImages[currentIndex].setVisible(false);
            int nextIndex = (currentIndex + 1) % shipImages.length;
            currentImageIndex.set(nextIndex);
            shipImages[nextIndex].setVisible(true);
        });

        choosePreviousImage.setOnAction(event -> {
            int currentIndex = currentImageIndex.get();
            shipImages[currentIndex].setVisible(false);
            int previousIndex = (currentIndex - 1 + shipImages.length) % shipImages.length;
            currentImageIndex.set(previousIndex);
            shipImages[previousIndex].setVisible(true);
        });
    }

    public void switchToGameState(ActionEvent event) throws IOException {
        gameStateScene = new GameStateScene();
        root = FXMLLoader.load(getClass().getResource("/com/asteroids/fxml/gamestate.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(gameStateScene.getScene());
        stage.show();
        gameStateScene.setDifficulty();
        root.addEventFilter(KeyEvent.KEY_PRESSED, eventt -> {
            if (eventt.getCode() == KeyCode.ESCAPE) {
                try {
                    switchToMainMenu(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/asteroids/fxml/mainmenu.fxml"));
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void quitGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/asteroids/fxml/mainmenu.fxml"));
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.close();
    }

    public void switchToOptionsMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/asteroids/fxml/optionsmenu.fxml"));
        stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        root.addEventFilter(KeyEvent.KEY_PRESSED, eventt -> {
            if (eventt.getCode() == KeyCode.ESCAPE) {
                try {
                    root = FXMLLoader.load(getClass().getResource("/com/asteroids/fxml/mainmenu.fxml"));
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
