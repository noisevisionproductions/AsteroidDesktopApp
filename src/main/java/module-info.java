module com.asteroids {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.asteroids to javafx.fxml;
    exports com.asteroids;
    opens com.asteroids.Scenes to javafx.fxml;

    

}
