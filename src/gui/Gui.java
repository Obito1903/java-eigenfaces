package gui;

import gui.utils.ImageAlbum;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {
    /**
     * Album of eigenfaces images
     */
    private ImageAlbum eigenfaces;

    /**
     * Album of reference images
     */
    private ImageAlbum references;

    private EigenPane eigenPane;

    @Override
    public void start(Stage mainStage) throws Exception {
        // TODO
        this.eigenPane = new EigenPane();

        mainStage.setTitle("Facial Recognition");
        mainStage.setScene(new Scene(this.eigenPane, 1280, 720));
        mainStage.show();
    }

    // TEST
    public static void main(String[] args) {
        launch();
    }
}
