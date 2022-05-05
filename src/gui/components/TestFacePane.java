package gui.components;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TestFacePane extends BorderPane {

    private HBox topButtons;

    public TestFacePane() {
        super();
        this.getStyleClass().add("hb_config");
        this.setStyle("-fx-background-color: #FF0000;");

        initTopButtons();

        this.setTop(this.topButtons);
    }

    private void initImageW() {
        // TODO
    }

    private void initTopButtons() {
        this.topButtons = new HBox();
        this.topButtons.setSpacing(50);

        Button btn_configEGDB = new Button("Databases\n(Compile/Load/View)");
        btn_configEGDB.getStyleClass().add("btn_configEGDB");

        Button btn_imgFile = new Button("Select test image");
        btn_imgFile.getStyleClass().add("btn_imgFile");
        this.topButtons.getChildren().addAll(btn_configEGDB, btn_imgFile);

    }
}
