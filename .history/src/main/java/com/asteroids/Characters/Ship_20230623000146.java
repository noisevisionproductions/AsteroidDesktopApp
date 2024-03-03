package com.asteroids.Characters;

import java.util.concurrent.atomic.AtomicInteger;

import com.asteroids.Scenes.ScreenSize;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship extends Character {
    private ImageView image;
    private ImageView[] imagesOfShip;

    public Ship(ImageView imageViewOfShip, int x, int y, ScreenSize screenSize) {
        super(imageViewOfShip, x, y, screenSize);
    }

    public void changeLook() {
        getImages();
        AtomicInteger currentImageIndex = SharedAtomicInteger.getCurrentImageIndex();

        int index = currentImageIndex.get();
        image = new ImageView();
        image = imagesOfShip[index];

        setCharacter(image);
    }

    public ImageView[] getImages() {
        Image image1 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/ships/ship1.png");
        Image image2 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/ships/ship2.png");
        Image image3 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/ships/ship3.png");
        Image image4 = new Image(
                "file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/ships/ship4.png");

        imagesOfShip = new ImageView[] {
                new ImageView(image1),
                new ImageView(image2),
                new ImageView(image3),
                new ImageView(image4),
        };
        return this.imagesOfShip;
    }

    @Override
    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.getCharacter().getRotate() - 90));
        double changeY = Math.sin(Math.toRadians(this.getCharacter().getRotate() - 90));

        changeX *= 0.012;
        changeY *= 0.012;
        Point2D newMovement = this.getMovement().add(changeX, changeY);
        double maxSpeed = 2.0;
        if (newMovement.magnitude() <= maxSpeed) {
            this.setMovement(newMovement);
        } else {
            this.setMovement(newMovement.normalize().multiply(maxSpeed));
        }
    }
}
