package gui.control;

import java.io.File;

import gui.Gui;
import gui.utils.Picture;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CtrlSelectTest implements EventHandler<ActionEvent> {

    private FileChooser fileChooser;
    private Gui app;

    public CtrlSelectTest(Gui app) {
        this.app = app;
        this.fileChooser = new FileChooser();
        this.fileChooser.setInitialFileName("test.jpg");
        this.fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Test image", "*.jpg"));
    }

    @Override
    public void handle(ActionEvent event) {
        File dirPath = this.fileChooser.showOpenDialog(this.app.getMainStage());
        try {
            Picture img = new Picture("file://" + dirPath.getPath().toString());
            this.app.setTestPicturePath(dirPath.getPath().toString());
            this.app.setTestPicture(img);
            this.app.getEigenPane().getLeftPane().setPicture(img);

        } catch (Exception e) {
            System.err.println(e);
            Alert error = new Alert(AlertType.ERROR, e.toString());
            error.setHeight(300);
            error.setWidth(533);
            error.showAndWait();
            error.show();
        }

    }
}
