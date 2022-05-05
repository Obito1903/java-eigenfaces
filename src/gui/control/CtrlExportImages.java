package gui.control;

import java.nio.file.Path;

import eigenfaces.Compiler;
import gui.Gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;

public class CtrlExportImages implements EventHandler<ActionEvent> {

    private DirectoryChooser dirChooser;

    private Gui app;

    public CtrlExportImages(Gui app) {
        this.app = app;
        this.dirChooser = new DirectoryChooser();
    }

    @Override
    public void handle(ActionEvent event) {
        Path dirPath = this.dirChooser.showDialog(this.app.getMainStage()).toPath();

        Compiler.saveImages(this.app.getEgdb(), dirPath.toString());

    }

}
