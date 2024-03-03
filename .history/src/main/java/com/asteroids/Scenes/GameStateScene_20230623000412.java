package com.asteroids.Scenes;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.asteroids.Characters.Asteroid;
import com.asteroids.Characters.DifficultyLevel;
import com.asteroids.Characters.ProjectLines;
import com.asteroids.Characters.Ship;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameStateScene {
   /*  public static int WIDTH = 1280;
    public static int HEIGHT = 720; */

    private ScreenSize screenSize;

    @FXML
    private AnchorPane test;

    @FXML
    private ImageView chosenShipByUser;
    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    @FXML
    private Label difficultyLabel;
    @FXML
    private Text gamePausedText;
    @FXML
    private Text endScore;
    @FXML
    private Text gameOverText;
    @FXML
    private Button continueButton;
    @FXML
    private Button restartButton;
    @FXML
    private Button mainMenuButton;

    private Ship ship;
    private Asteroid asteroid;
    private Parent rootgame;
    private Label pointsStyle;
    private Group root;
    private Scene gameScene;
    private SceneManager sceneManager;

    private AtomicInteger score;
    private AnimationTimer animationTimer;
    private ImageView imageView;

    private long shotTime = 0;
    private boolean canShoot = true;
    private boolean easyDisabled = true;
    private boolean mediumDisabled = true;
    private boolean hardDisabled = true;

    public static List<ProjectLines> listOfShots = new ArrayList<>();
    private Map<KeyCode, Boolean> keyPressed;
    private List<Asteroid> asteroidList;

    public GameStateScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/asteroids/fxml/gamestate.fxml"));
        loader.setController(this);
        this.rootgame = loader.load();

        this.screenSize = new ScreenSize();

        this.imageView = new ImageView();

        Stage stage = new Stage();
        this.sceneManager = new SceneManager(stage);
        this.root = new Group();
        this.score = new AtomicInteger();
        this.gameScene = new Scene(this.root, this.screenSize.getGameWidth(), screenSize.getGameHeight());
        //this.gameScene = new Scene(this.root, WIDTH, HEIGHT);

        this.pointsStyle = new Label("Points: 0");
        pointsStyle.setPrefSize(120, 20);
        pointsStyle.setAlignment(Pos.CENTER);
        pointsStyle.setPadding(new Insets(5));
        pointsStyle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        pointsStyle.setStyle("-fx-background-color: #fff000;");

        this.asteroidList = new ArrayList<>();

        // creates a map so it will tell if certain key on the keyboard was pressed and
        // released or not
        this.keyPressed = new HashMap<>();
        gameScene.setOnKeyPressed(event -> {
            this.keyPressed.put(event.getCode(), Boolean.TRUE);
        });

        gameScene.setOnKeyReleased(event -> {
            this.keyPressed.put(event.getCode(), Boolean.FALSE);
        });

        this.ship = createShip();
        this.root.getChildren().addAll(rootgame, ship.getCharacter(), pointsStyle);
    }

    public Scene getScene() {
        return this.gameScene;
    }

    public int getScore() {
        return this.score.get();
    }

    public Ship createShip() {


        this.ship = new Ship(imageView, screenSize.getGameHeight()/2, screenSize.getGameWidth()/2,screenSize);

        //this.ship = new Ship(imageView, WIDTH / 2, HEIGHT / 2, 50, 51);
        this.ship.changeLook();
        return this.ship;
    }

    public Asteroid createAsteroid() {
        Random random = new Random();
        asteroid = null;
        try {
            asteroid = new Asteroid(imageView, random.nextInt(1280), random.nextInt(720), 50, 50);
            asteroid.changeLook();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return asteroid;
    }

    public void play() {

        animationTimer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                handleInput();
                if (now - shotTime > 300_000_000) {
                    canShoot = true;
                }
                if (Math.random() < 0.02) {
                    createAsteroid();
                    if (asteroid != null && !asteroid.collide(ship)) {

                        root.getChildren().add(asteroid.getCharacter());
                        asteroidList.add(asteroid);

                        if (easyDisabled == false) {
                            asteroid.setDifficulty(DifficultyLevel.EASY);
                        }
                        if (mediumDisabled == false) {
                            asteroid.setDifficulty(DifficultyLevel.MEDIUM);
                        }

                        if (hardDisabled == false) {
                            asteroid.setDifficulty(DifficultyLevel.HARD);
                        }
                    }
                }

                ship.move();
                ship.isInBorder();

                asteroidList.forEach(asteroid -> asteroid.move());
                asteroidList.forEach(asteroid -> asteroid.isInBorder());
                listOfShots.forEach(projectlines -> projectlines.move());
                listOfShots.forEach(projectlines -> projectlines.isInBorder());

                listOfShots.forEach(projectline -> {
                    asteroidList.forEach(asteroid -> {
                        if (projectline.collide(asteroid)) {
                            projectline.setAlive(false);
                            asteroid.setAlive(false);
                        }
                    });
                    if (!projectline.isAlive()) {
                        pointsStyle.setText("Points: " + score.incrementAndGet());
                    }
                });

                listOfShots.stream()
                        .filter(projectline -> !projectline.isAlive())
                        .forEach(projectline -> root.getChildren().remove(projectline.getCharacter()));
                listOfShots.removeAll(listOfShots.stream()
                        .filter(projectlines -> !projectlines.isAlive())
                        .collect(Collectors.toList()));

                asteroidList.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .forEach(asteroid -> root.getChildren().remove(asteroid.getCharacter()));
                asteroidList.removeAll(asteroidList.stream()
                        .filter(asteroid -> !asteroid.isAlive())
                        .collect(Collectors.toList()));

                asteroidList.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        setGameOver();
                        stopAnimation();
                    }
                });
            }
        };
        animationTimer.start();
    }

    public void startAnimation() {
        play();
    }

    public void stopAnimation() {
        animationTimer.stop();
    }

    public void setDifficulty() throws IOException {

        ship.getCharacter().setVisible(false);
        pointsStyle.setVisible(false);

        easyButton.setOnAction(event -> {
            easyDisabled = false;
            mediumDisabled = true;
            hardDisabled = true;

            setUnpause();
        });

        mediumButton.setOnAction(event -> {
            if (easyDisabled == true) {
                easyDisabled = true;
                mediumDisabled = false;
                hardDisabled = true;
                setUnpause();
            }
        });

        hardButton.setOnAction(event -> {
            if (mediumDisabled == true && easyDisabled == true) {
                easyDisabled = true;
                mediumDisabled = true;
                hardDisabled = false;
                setUnpause();
            }
        });
    }

    public void handleGameStateButtons() {
        restartButton.setOnAction(event -> {
            try {
                sceneManager.switchToGameState(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainMenuButton.setOnAction(event -> {
            try {
                sceneManager.switchToMainMenu(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        continueButton.setOnAction(event -> {
            setUnpause();
        });
    }

    @FXML
    public void setGameOver() {
        handleGameStateButtons();
        this.endScore.setText("Your score: " + getScore());
        this.endScore.setVisible(true);

        pointsStyle.setVisible(false);

        gameOverText.setVisible(true);
        gameOverText.setDisable(false);
        root.getChildren().addAll(gameOverText, endScore, restartButton, mainMenuButton);
        gameOverText.toFront();
        restartButton.toFront();
        mainMenuButton.toFront();
        endScore.toFront();

        restartButton.setVisible(true);
        restartButton.setDisable(false);

        mainMenuButton.setVisible(true);
        mainMenuButton.setDisable(false);

    }

    public void setPause() {

        stopAnimation();
        handleGameStateButtons();
        this.endScore.setText("Your score: " + getScore());
        this.endScore.setVisible(true);
        pointsStyle.setVisible(false);

        this.gamePausedText.setVisible(true);

        root.getChildren().addAll(endScore, restartButton, mainMenuButton, gamePausedText, continueButton);
        gamePausedText.toFront();
        continueButton.toFront();
        restartButton.toFront();
        mainMenuButton.toFront();
        endScore.toFront();
        restartButton.setVisible(true);
        restartButton.setDisable(false);

        continueButton.setVisible(true);
        continueButton.setDisable(false);

        mainMenuButton.setVisible(true);
        mainMenuButton.setDisable(false);

    }

    public void setUnpause() {

        startAnimation();

        ship.getCharacter().setVisible(true);
        pointsStyle.setVisible(true);

        this.endScore.setVisible(false);
        pointsStyle.setVisible(true);

        this.gamePausedText.setVisible(false);

        root.getChildren().removeAll(endScore, restartButton, mainMenuButton, gamePausedText, continueButton);
        gamePausedText.toBack();
        continueButton.toBack();
        restartButton.toBack();
        mainMenuButton.toBack();
        endScore.toBack();
        restartButton.setVisible(false);
        restartButton.setDisable(true);

        continueButton.setVisible(false);
        continueButton.setDisable(true);

        mainMenuButton.setVisible(false);
        mainMenuButton.setDisable(true);

        easyButton.setVisible(false);
        easyButton.setDisable(true);

        mediumButton.setVisible(false);
        mediumButton.setDisable(true);

        hardButton.setVisible(false);
        hardButton.setDisable(true);

        difficultyLabel.setVisible(false);

    }

    public void handleInput() {
        if (keyPressed.getOrDefault(KeyCode.ESCAPE, false)) {
            setPause();
        }

        if (keyPressed.getOrDefault(KeyCode.LEFT, false)) {
            ship.turnLeft();
        }

        if (keyPressed.getOrDefault(KeyCode.RIGHT, false)) {
            ship.turnRight();
        }

        if (keyPressed.getOrDefault(KeyCode.UP, false)) {
            ship.getMovement().normalize().multiply(-3);
            ship.accelerate();
        }

        if (keyPressed.getOrDefault(KeyCode.DOWN, false)) {
            ship.accelerateBackwards();
        }

        if (keyPressed.getOrDefault(KeyCode.SPACE, false) && canShoot) {

            ProjectLines projectlines = new ProjectLines(root, imageView,
                    (int) ship.getCharacter().getTranslateX() + 20,
                    (int) ship.getCharacter().getTranslateY() + 5,screenSize);
            projectlines.changeLook();

            projectlines.getCharacter().setRotate(ship.getCharacter().getRotate());

            listOfShots.add(projectlines);

            projectlines.accelerate();

            projectlines.setMovement(projectlines.getMovement().normalize().multiply(3));
            root.getChildren().add(projectlines.getCharacter());

            shotTime = System.nanoTime();

            canShoot = false;
        }
    }

}
