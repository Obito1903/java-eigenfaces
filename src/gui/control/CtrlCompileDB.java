package gui.control;

import java.util.Observable;
import java.util.Observer;

import eigenfaces.Compiler;
import eigenfaces.EigenFacesDB;
import eigenfaces.image.ImageVector;
import gui.Gui;
import gui.utils.ImageAlbum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

@SuppressWarnings("deprecation")
public class CtrlCompileDB implements EventHandler<ActionEvent>, Observer {

    private ImageAlbum eigenAlbum;
    private Gui app;

    public CtrlCompileDB(Gui app, ImageAlbum eigenAlbum) {
        this.app = app;
        this.eigenAlbum = eigenAlbum;
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        this.app.getConfigPane().getImageList().getEigenListPane().reloadImageList(this.eigenAlbum);
    }

    @Override
    public void handle(ActionEvent arg0) {
        System.out.println("slaut");
        if (this.app.getRefAlbum().getDir() != "") {
            this.app.setEgdb(
                    Compiler.compileDB(this.app.getRefAlbum().getDir(), this.app.getConfigPane().getKValue(),
                            this.app.getConfigPane().getStatusLabel()));
            ImageVector[] eigImgList = this.app.getEgdb().eigenFacesToImageVectors();
            for (int i = 0; i < eigImgList.length; i++) {
                this.app.getEigenAlbum().addPicture(eigImgList[i]);
            }
        }

    }
}
