package com.asteroids.Scenes;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ScreenSize {
    private Rectangle2D screenBounds;
    private double screenWidth;
    private double screenHeight;
    private Integer gameWidth;
    private Integer gameHeight;

    public ScreenSize() {
        this.screenBounds = Screen.getPrimary().getVisualBounds();
        this.screenWidth = this.screenBounds.getWidth();
        this.screenHeight = this.screenBounds.getHeight();
        this.gameWidth = (int) screenWidth;
        this.gameHeight = (int) screenHeight;
    }

    public Integer getGameWidth() {
        return gameWidth;
    }

    public Integer getGameHeight() {
        return gameHeight;
    }
}
