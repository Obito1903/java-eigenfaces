package gui.components;

import gui.Gui;
import gui.utils.ImageAlbum;
import gui.utils.Picture;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ResultPane extends BorderPane {

    private Gui app;

    private ScrollPane topIcons;
    private HBox btnList;
    private ImageView imgView;
    private DistanceInfo distBox;

    public ResultPane(Gui app) {
        super();
        this.app = app;
        // Debug
        // this.setStyle("-fx-background-color: #00FF00;");

        this.imgView = createImageView();
        this.btnList = createBtnBox();
        this.topIcons = new ScrollPane(this.btnList);
        this.distBox = new DistanceInfo(this.app);

        this.setCenter(this.imgView);
        this.setTop(this.topIcons);
        this.setBottom(this.distBox);
    }

    private ImageView createImageView() {
        ImageView imgView = new ImageView();
        // Debug
        imgView.fitHeightProperty().bind(this.heightProperty().divide(2));
        imgView.setPreserveRatio(true);
        // this.imgView.fitWidthProperty().bind(this.widthProperty().divide(2));
        return imgView;
    }

    private HBox createBtnBox() {
        HBox topIcons = new HBox();

        topIcons.setPrefHeight(100);
        topIcons.setAlignment(Pos.CENTER);
        topIcons.setSpacing(10);

        return topIcons;
    }

    public void reloadTopIcons(ImageAlbum album) {
        this.btnList.getChildren().removeAll(this.btnList.getChildren());
        for (int i = 0; i < album.getSize(); i++) {
            Button btn = new Button();
            btn.setGraphic(new ImageView(album.getPicture(i).getIcon()));
            this.btnList.getChildren().add(btn);
        }
    }

    public void setPicture(Picture pic) {
        this.imgView.setImage(pic.getImage());
    }

}
