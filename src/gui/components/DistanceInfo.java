package gui.components;

import java.util.List;

import eigenfaces.image.VectorWithDistance;
import gui.Gui;
import gui.utils.Picture;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DistanceInfo extends VBox {

    private Gui app;

    private Label imageName;
    private Label distance;

    public DistanceInfo(Gui app) {
        super();
        this.app = app;
        this.setAlignment(Pos.CENTER);

        this.imageName = new Label("salut");
        this.distance = new Label("salut12");

        this.getChildren().addAll(this.imageName, this.distance);
    }

    public void setDistanceWith(Picture pic) {
        List<VectorWithDistance> results = this.app.getTestResults();

        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getName() == pic.getName()) {
                this.imageName.setText("Name : " + pic.getName());
                this.distance.setText("Distance : " + results.get(i).getDistance());
                break; // I know it's ugly
            }
        }
    }
}
