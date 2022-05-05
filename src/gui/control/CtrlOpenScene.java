package gui.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CtrlOpenScene implements EventHandler<ActionEvent> {

    private Stage mainStage;
    private Button button;
    private Scene targetScene;

    public CtrlOpenScene(Stage mainStage, Button btn, Scene targetScene) {
        this.mainStage = mainStage;
        this.button = btn;
        this.targetScene = targetScene;
    }

    @Override
    public void handle(ActionEvent event) {
        this.mainStage.setScene(this.targetScene);
    }
}
