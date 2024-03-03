package com.asteroids.Characters;



import com.asteroids.Scenes.GameStateScene;
import com.asteroids.Scenes.ScreenSize;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProjectLines extends Character {

    private Group root;

    private Image imageOfProject;
    private static ImageView imageViewOfProject;

    public ProjectLines(Group root, ImageView imageViewOfShip, int x, int y, ScreenSize screenSize) {
        super(imageViewOfProject, x, y, screenSize.getGameWidth(), screenSize.getGameHeight());
        this.root = root;
    }

    public void changeLook() {
        this.imageOfProject = new Image("file:///C:/Users/noise/Desktop/projects/asteroidsapplication/src/main/java/com/asteroids/images/bullet3.png");
        ImageView imageViewOfShip = new ImageView(this.imageOfProject);
        imageViewOfShip.setRotate(90);
        setCharacter(imageViewOfShip);
    }

    @Override
    public void isInBorder() {
        double x = this.getCharacter().getTranslateX();
        double y = this.getCharacter().getTranslateY();

        if (x < 0 || x > GameStateScene.WIDTH || y < 0 || y > GameStateScene.HEIGHT) {
            root.getChildren().remove(this.getCharacter());
        }
    }

}
