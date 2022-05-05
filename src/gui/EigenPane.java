package gui;

import gui.components.TestFacePane;
import gui.utils.ImageAlbum;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class EigenPane extends HBox {
    private ImageAlbum refAlbum;

    private BorderPane leftPane;
    private BorderPane centerPane;
    private BorderPane righPane;

    public EigenPane() {
        super();
        this.getStylesheets().add(this.getClass().getResource("resources/style.css").toExternalForm());
        this.getStyleClass().add("recognitionTest");

        this.leftPane = new TestFacePane();
        this.getChildren().addAll(this.leftPane);
    }

}
