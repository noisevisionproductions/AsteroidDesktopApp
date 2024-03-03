package com.asteroids.Scenes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;

public class ButtonsState  {
    private final ObjectProperty<Button> state = new SimpleObjectProperty<>();

    public ObjectProperty<Button> stateProperty() {
        return state;
    }

    public Button getState() {
        return state.get();
    
    }
    public void setState (Button button){
        state.set(button);
    }
}
