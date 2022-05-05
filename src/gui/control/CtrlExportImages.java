package gui.control;

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
    }

    @Override
    public void handle(ActionEvent event) {
        Compiler.saveImages(this.app.getEgdb(), path);
        ;
    }

}
