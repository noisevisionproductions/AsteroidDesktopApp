package com.asteroids.Characters;

import com.asteroids.Scenes.GameStateScene;
import com.asteroids.Scenes.ScreenSize;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public abstract class Character {
    protected ImageView character;
    private Point2D movement;
    private boolean alive;

    private ScreenSize screenSize;
    private int width;
    private int height;

    public Character(ImageView ImageView, int x, int y, ScreenSize screenSize) {
        this.screenSize = screenSize;
        width = screenSize.getGameWidth();
        height = screenSize.getGameHeight();      

        this.character = new ImageView();
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.character.setFitWidth(width);
        this.character.setFitHeight(height);

        this.movement = new Point2D(0, 0);

        this.alive = true;
    }

    protected void setCharacter(ImageView imageViewOfShip) {
        this.character.setImage(imageViewOfShip.getImage());
    }

    public ImageView getCharacter() {
        // returns characters in the game that are polygons, for now ship and asteroids

        return this.character;
    }

    public void setMovement(Point2D move) {
        this.movement = move;
    }

    public Point2D getMovement() {
        return this.movement;
    }

    public void turnLeft() {
        // by calling this method it makes the character move to the left
        this.character.setRotate(this.character.getRotate() - 2);
    }

    public void turnRight() {
        // by calling this method it makes the character move to the right
        this.character.setRotate(this.character.getRotate() + 2);
    }

    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
    }

    public void isInBorder() {
        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + width);
        }

        if (this.character.getTranslateX() > width) {
            this.character.setTranslateX(this.character.getTranslateX() % width);
        }

        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + height);
        }

        if (this.character.getTranslateY() > height) {
            this.character.setTranslateY(this.character.getTranslateY() % height);
        }
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate() - 90));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate() - 90));

        changeX *= 0.03;
        changeY *= 0.03;

        this.movement = this.movement.add(changeX, changeY);
    }

    public void accelerateBackwards() {
        // this method allows the character to stop or to go backwards
        double changeX = Math.cos(Math.toRadians(this.character.getRotate() - 270));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate() - 270));

        // those values makes the acceleration slower
        changeX *= 0.01;
        changeY *= 0.01;

        this.movement = this.movement.add(changeX, changeY);
    }

    public boolean collide(Character otherCharacter) {
        Bounds bounds1 = this.getCharacter().localToParent(this.getCharacter().getBoundsInLocal());
        Bounds bounds2 = otherCharacter.getCharacter().localToParent(otherCharacter.getCharacter().getBoundsInLocal());
        return bounds1.intersects(bounds2);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
