package gui.components;

import gui.utils.Picture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TestFacePane extends BorderPane {

    private HBox topButtons;
    private ImageView imgView;

    public TestFacePane() {
        super();
        // Debug
        // this.setStyle("-fx-background-color: #FF0000;");

        this.topButtons = createTopButtons();
        this.imgView = createImageView();

        this.setTop(this.topButtons);
        this.setCenter(this.imgView);
    }

    private ImageView createImageView() {
        ImageView imgView = new ImageView();
        // Debug
        imgView.fitHeightProperty().bind(this.heightProperty().divide(2));
        imgView.setPreserveRatio(true);
        // this.imgView.fitWidthProperty().bind(this.widthProperty().divide(2));
        return imgView;
    }

    private HBox createTopButtons() {
        HBox topButtons = new HBox();

        topButtons.setPrefHeight(100);
        topButtons.setAlignment(Pos.CENTER);
        topButtons.setSpacing(50);

        Button btn_configEGDB = new Button("Databases\n(Compile/Load/View)");
        btn_configEGDB.getStyleClass().add("btn_configEGDB");

        Button btn_imgFile = new Button("Select test image");
        btn_imgFile.getStyleClass().add("btn_imgFile");
        topButtons.getChildren().addAll(btn_configEGDB, btn_imgFile);
        return topButtons;
    }

    public void setPicture(Picture pic) {
        this.imgView.setImage(pic.getImage());
    }
}
