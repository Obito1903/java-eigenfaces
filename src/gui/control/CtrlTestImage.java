package gui.control;

import java.util.List;

import eigenfaces.Test;
import eigenfaces.image.ImageVector;
import eigenfaces.image.VectorWithDistance;
import gui.Gui;
import gui.utils.ImageAlbum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CtrlTestImage implements EventHandler<ActionEvent> {
    private Gui app;

    public CtrlTestImage(Gui app) {
        this.app = app;
    }

    @Override
    public void handle(ActionEvent arg0) {
        List<VectorWithDistance> imageList = Test.findBestMatches(this.app.getEgdb(),
                new ImageVector(this.app.getTestPicturePath()), 40);

        this.app.setResults(imageList);

        ImageAlbum resultAlbum = new ImageAlbum();

        for (int i = 0; i < imageList.size(); i++) {
            ImageVector imV = this.app.getEgdb().reconstruct(imageList.get(i), imageList.get(i).getName())
                    .centerReduce();
            resultAlbum.addPicture(imV);
        }
        this.app.setResultAlbum(resultAlbum);
        this.app.getEigenPane().getRightPane().reloadTopIcons(resultAlbum);
        this.app.getEigenPane().getRightPane().setPicture(resultAlbum.getCurrentPicture());

        this.app.getEigenPane().getRightPane().setDistanceWith(resultAlbum.getCurrentPicture());
    }
}
