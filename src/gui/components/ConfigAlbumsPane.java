package gui.components;

import gui.Gui;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ConfigAlbumsPane extends HBox {

    private PictureListPane refList;
    private PictureListPane eigenList;

    public ConfigAlbumsPane(Gui app) {
        super();

        this.setAlignment(Pos.CENTER);

        this.refList = new PictureListPane("Reference images");
        this.eigenList = new PictureListPane("Eigen Faces");

        this.getChildren().addAll(this.refList, this.eigenList);
    }

    public PictureListPane getRefListPane() {
        return this.refList;
    }

    public PictureListPane getEigenListPane() {
        return this.eigenList;
    }

}
