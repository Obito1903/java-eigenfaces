package gui.control;

import java.io.File;

import gui.Gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class CtrlSaveEgdb implements EventHandler<ActionEvent> {

    private FileChooser fileChooser;

    private Gui app;

    public CtrlSaveEgdb(Gui app) {
        this.app = app;
        this.fileChooser = new FileChooser();
        this.fileChooser.setInitialFileName("database.egdb");
        this.fileChooser.setSelectedExtensionFilter(new ExtensionFilter("EigenFaces Database", "*.egdb"));
    }

    @Override
    public void handle(ActionEvent event) {
        File dirPath = this.fileChooser.showSaveDialog(this.app.getMainStage());

        this.app.getEgdb().saveToFile(dirPath.toString());

    }
}
