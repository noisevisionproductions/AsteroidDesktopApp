package com.asteroids.Characters;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Asteroid extends Character {
    private final Random random;
    private double rotateMovement;
    private int accelerate;
    private final List<ImageView> imagesOfAsteroids;

    public Asteroid(ImageView imageViewOfAsteroid, int x, int y, int width, int height) throws MalformedURLException {
        super(imageViewOfAsteroid, x, y, width, height);

        this.imagesOfAsteroids = new ArrayList<>();
        this.random = new Random();
        this.accelerate = 0;
    }

    public void setDifficulty(DifficultyLevel difficultyLevel) {
        switch (difficultyLevel) {

            case EASY:
                setEasy();
                break;
            case MEDIUM:
                setMedium();
                break;
            case HARD:
                setHard();
                break;
            default:
                break;
        }
    }

    public void setEasy() {
        super.getCharacter().setRotate(random.nextInt());

        this.accelerate = 1 + random.nextInt(5);
        for (int i = 0; i < accelerate; i++) {
            accelerate();
        }
        this.rotateMovement = 0.5 - random.nextDouble();
    }

    public void setMedium() {
        super.getCharacter().setRotate(random.nextInt());

        accelerate = 1 + random.nextInt(20);
        for (int i = 0; i < accelerate; i++) {
            accelerate();
        }
        this.rotateMovement = 0.5 - random.nextDouble();
    }

    public void setHard() {
        super.getCharacter().setRotate(random.nextInt());

        accelerate = 1 + random.nextInt(150);
        for (int i = 0; i < accelerate; i++) {
            accelerate();
        }
        this.rotateMovement = 0.5 - random.nextDouble();
    }

    public void loadImages() {
        Image image1 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (1).png");
        Image image2 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (2).png");
        Image image3 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (3).png");
        Image image4 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (4).png");
        Image image5 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (5).png");
        Image image6 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (6).png");
        Image image7 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (7).png");
        Image image8 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/asteroids/r (8).png");

        ImageView imageView1 = new ImageView(image1);
        ImageView imageView2 = new ImageView(image2);
        ImageView imageView3 = new ImageView(image3);
        ImageView imageView4 = new ImageView(image4);
        ImageView imageView5 = new ImageView(image5);
        ImageView imageView6 = new ImageView(image6);
        ImageView imageView7 = new ImageView(image7);
        ImageView imageView8 = new ImageView(image8);

        this.imagesOfAsteroids.add(imageView1);
        this.imagesOfAsteroids.add(imageView2);
        this.imagesOfAsteroids.add(imageView3);
        this.imagesOfAsteroids.add(imageView4);
        this.imagesOfAsteroids.add(imageView5);
        this.imagesOfAsteroids.add(imageView6);
        this.imagesOfAsteroids.add(imageView7);
        this.imagesOfAsteroids.add(imageView8);

    }

    public void changeLook() {
        loadImages();
        Random random = new Random();
        int index = random.nextInt(imagesOfAsteroids.size());
        ImageView newImageView = imagesOfAsteroids.get(index);
        setCharacter(newImageView);
    }

    @Override
    public void move() {
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + this.rotateMovement);
    }

}
