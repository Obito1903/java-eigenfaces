package gui.control;

import java.io.File;

import eigenfaces.EigenFacesDB;
import eigenfaces.image.ImageVector;
import gui.Gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CtrlOpenEgdb implements EventHandler<ActionEvent> {

    private FileChooser fileChooser;
    private Gui app;

    public CtrlOpenEgdb(Gui app) {
        this.app = app;
        this.fileChooser = new FileChooser();
        this.fileChooser.setInitialFileName("database.egdb");
        this.fileChooser.setSelectedExtensionFilter(new ExtensionFilter("EigenFaces Database", "*.egdb"));
    }

    @Override
    public void handle(ActionEvent event) {
        File dirPath = this.fileChooser.showOpenDialog(this.app.getMainStage());
        try {
            EigenFacesDB egdb = new EigenFacesDB(dirPath.toString());
            this.app.setEgdb(egdb);

            this.app.getEigenAlbum().reset();
            ImageVector[] eigImgList = this.app.getEgdb().eigenFacesToImageVectors();
            for (int i = 0; i < eigImgList.length; i++) {
                this.app.getEigenAlbum().addPicture(eigImgList[i]);
            }

            this.app.getRefAlbum().reset();
            for (int i = 0; i < this.app.getEgdb().getG().getNbColumn(); i++) {
                ImageVector image = this.app.getEgdb().reconstruct(i).centerReduce();

                this.app.getRefAlbum().addPicture(image);
            }

        } catch (Exception e) {
            Alert error = new Alert(AlertType.ERROR, e.toString());
            error.setHeight(300);
            error.setWidth(533);
            error.showAndWait();
            error.show();
        }

    }

}
