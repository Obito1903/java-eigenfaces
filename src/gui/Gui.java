package gui;

import java.util.List;

import eigenfaces.EigenFacesDB;
import eigenfaces.image.VectorWithDistance;
import gui.control.CtrlOpenScene;
import gui.utils.ImageAlbum;
import gui.utils.Picture;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {

    private List<VectorWithDistance> results;
    private EigenFacesDB egdb;

    /**
     * Album of eigenfaces images
     */
    private ImageAlbum eigenfaces;

    /**
     * Album of reference images
     */
    private ImageAlbum references;

    private ImageAlbum resultAlbum;

    private EigenPane eigenPane;
    private ConfigPane configPane;

    private Picture testImage;
    private String testImagePath;

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
        mainStage.setScene(this.configScene);
        mainStage.show();
    }

    public ImageAlbum getRefAlbum() {
        return this.references;
    }

    public ImageAlbum getEigenAlbum() {
        return this.eigenfaces;
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

    public Picture getTestPicture() {
        return this.testImage;
    }

    public void setTestPicture(Picture pic) {
        this.testImage = pic;
    }

    public String getTestPicturePath() {
        return this.testImagePath;
    }

    public void setTestPicturePath(String path) {
        this.testImagePath = path;
    }

    public List<VectorWithDistance> getTestResults() {
        return this.results;
    }

    public EigenFacesDB getEgdb() {
        return this.egdb;
    }

    public void setEgdb(EigenFacesDB egdb) {
        this.egdb = egdb;
    }

    public List<VectorWithDistance> getResults() {
        return this.results;
    }

    public void setResults(List<VectorWithDistance> results) {
        this.results = results;
    }

    public ImageAlbum getResultAlbum() {
        return this.resultAlbum;
    }

    public void setResultAlbum(ImageAlbum resultAlbum) {
        this.resultAlbum = resultAlbum;
    }

    // TEST
    public static void main(String[] args) {
        launch();
    }

	public static void mainLoop() {
		launch();
	}
}
