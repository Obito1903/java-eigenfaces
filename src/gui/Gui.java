package gui;

import java.util.List;

import eigenfaces.image.VectorWithDistance;
import gui.control.CtrlOpenScene;
import gui.utils.ImageAlbum;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {
    /**
     * Album of eigenfaces images
     */
    private ImageAlbum eigenfaces;

    private List<VectorWithDistance> results;

    /**
     * Album of reference images
     */
    private ImageAlbum references;

    private EigenPane eigenPane;
    private ConfigPane configPane;

    private Scene configScene;
    private Scene eigenScene;

    private Stage mainStage;

    @Override
    public void start(Stage mainStage) throws Exception {
        // TODO
        this.references = new ImageAlbum();
        this.eigenfaces = new ImageAlbum();

        this.mainStage = mainStage;
        this.eigenPane = new EigenPane(this);
        this.eigenScene = new Scene(this.eigenPane, 1280, 720);

        this.configPane = new ConfigPane(this);
        this.configScene = new Scene(this.configPane, 1280, 720);

        // Test
        // this.eigenPane.getLeftPane().setPicture(this.references.getCurrentPicture());
        // this.eigenPane.getRightPane().setPicture(this.references.getCurrentPicture());
        // this.eigenPane.getRightPane().reloadTopIcons(this.references);

        this.configPane.getImageList().getRefListPane().reloadImageList(this.references);
        this.configPane.getImageList().getEigenListPane().reloadImageList(this.references);

        this.eigenPane.setupCtrl();
        this.configPane.setupCtrl();

        mainStage.setTitle("Facial Recognition");
        mainStage.setScene(this.eigenScene);
        mainStage.show();
    }

    public ImageAlbum getRefAlbum() {
        return this.references;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public EigenPane getEigenPane() {
        return eigenPane;
    }

    public ConfigPane getConfigPane() {
        return configPane;
    }

    public Scene getConfigScene() {
        return configScene;
    }

    public Scene getEigenScene() {
        return eigenScene;
    }

    public List<VectorWithDistance> getTestResults() {
        return this.results;
    }

    // TEST
    public static void main(String[] args) {
        launch();
    }
}
