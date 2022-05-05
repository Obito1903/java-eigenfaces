package gui.control;

import java.nio.file.Path;
import java.util.Observable;
import java.util.Observer;

import gui.Gui;
import gui.utils.ImageAlbum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class CtrlSelectRefDir implements EventHandler<ActionEvent>, Observer {

    private DirectoryChooser dirChooser;

    private ImageAlbum refAlbum;
    private Gui app;

    public CtrlSelectRefDir(Gui app, ImageAlbum refAlbum) {
        this.refAlbum = refAlbum;
        this.app = app;
        this.dirChooser = new DirectoryChooser();
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        this.app.getConfigPane().getImageList().getRefListPane().reloadImageList(this.refAlbum);
        this.app.getConfigPane().getConfigMenuPane().setMaxKValue(this.app.getRefAlbum().getSize());
    }

    @Override
    public void handle(ActionEvent event) {
        Path dirPath = this.dirChooser.showDialog(this.app.getMainStage()).toPath();

        this.refAlbum.loadDirectory(dirPath.toString());
    }
}
