package gui.components;

import gui.utils.ImageAlbum;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class PictureListPane extends VBox {

    private FlowPane imageList;

    public PictureListPane(String name) {
        super();
        this.setAlignment(Pos.CENTER);

        this.imageList = createImageList();
        ScrollPane scrollPane = new ScrollPane(this.imageList);
        Label nameLabel = new Label(name);

        this.getChildren().addAll(nameLabel, scrollPane);
    }

    private FlowPane createImageList() {
        FlowPane imageList = new FlowPane();
        return imageList;
    }

    public void reloadImageList(ImageAlbum album) {
        this.imageList.getChildren().removeAll(this.imageList.getChildren());
        for (int i = 0; i < album.getSize(); i++) {
            ImageView img = new ImageView(album.getPicture(i).getImage());
            img.setFitHeight(80);
            img.setPreserveRatio(true);
            this.imageList.getChildren().add(img);
        }
    }
}
