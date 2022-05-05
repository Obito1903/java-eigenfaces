package gui;

import gui.utils.ImageAlbum;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class EigenScene extends Scene {
    private ImageAlbum refAlbum;

    private BorderPane leftPane;
    private BorderPane centerPane;
    private BorderPane righPane;

    public EigenScene(double width, double height) {
        super(new HBox(), width, height);
        this.getRoot().getStylesheets().add(this.getClass().getResource("resources/style.css").toExternalForm());
        this.getRoot().getStyleClass().add("recognitionTest");

    }
}
