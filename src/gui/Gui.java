package gui;

import gui.utils.ImageAlbum;
import javafx.application.Application;
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

    @Override
    public void start(Stage mainStage) throws Exception {
        // TODO Auto-generated method stub
        mainStage.setTitle("Facial recognition");

        ConfigScene configScene = new ConfigScene(1280, 720);

        mainStage.setScene(configScene);
        mainStage.show();
    }

    // TEST
    public static void main(String[] args) {
        launch();
    }
}
