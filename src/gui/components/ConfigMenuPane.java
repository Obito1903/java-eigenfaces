package gui.components;

import gui.Gui;
import gui.control.CtrlSelectRefDir;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@SuppressWarnings("deprecation")
public class ConfigMenuPane extends FlowPane {
    private Gui app;

    private Button refSelectBtn;
    private VBox kValueBox;
    private Label compileStatus;
    private Button compileBtn;
    private Button exportBtn;
    private Button saveEgdbBtn;

    public ConfigMenuPane(Gui app) {
        super();
        this.app = app;

        this.refSelectBtn = createRefSelectButton();
        this.compileStatus = new Label("");
        this.kValueBox = createKValue();
        this.compileBtn = createCompileButton();
        this.exportBtn = createImgExportButton();
        this.saveEgdbBtn = createSaveEgdbButton();

        this.setHgap(75);
        this.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.getStyleClass().add("fp_compileEGDB");

        this.getChildren().addAll(this.refSelectBtn, this.kValueBox, this.compileBtn, this.exportBtn, this.saveEgdbBtn);

    }

    public Button getRefSelecButton() {
        return this.refSelectBtn;
    }

    public Label getCompileSatusLabel() {
        return this.compileStatus;
    }

    public void setCompileStatus(String value) {
        this.compileStatus.setText(value);
    }

    public Button getCompileBtn() {
        return this.compileBtn;
    }

    public Button getExportButton() {
        return this.exportBtn;
    }

    public Button getSaveButton() {
        return this.saveEgdbBtn;
    }

    public int getKValue() {
        // TODO
        return 0;
    }

    public Gui getApp() {
        return this.app;
    }

    private VBox createKValue() {
        VBox vb = new VBox();
        Label label = new Label("K value");
        label.getStyleClass().add("label_kvalue");
        label.setTextFill(Color.WHITE);
        Spinner<Integer> s = new Spinner<Integer>();
        // TODO Link max with album size prop
        s.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, this.app.getRefAlbum().getSize(), 1));
        s.setPrefSize(200, 10);

        vb.getChildren().addAll(label, s);
        return vb;
    }

    private Button createRefSelectButton() {
        Button btn_compRef = new Button("Select reference \n image directory");

        // createRefDirChooser();

        btn_compRef.getStyleClass().add("btn2");
        this.refSelectBtn = btn_compRef;
        return btn_compRef;
    }

    private Button createImgExportButton() {
        Button btn_imgOutput = new Button("Export images");

        // createOutputDirChooser();

        btn_imgOutput.getStyleClass().add("btn2");
        this.exportBtn = btn_imgOutput;
        return btn_imgOutput;
    }

    private Button createCompileButton() {
        Button btn_compile = new Button("Compile");
        btn_compile.getStyleClass().add("btn2");
        this.compileBtn = btn_compile;
        return btn_compile;
    }

    private Button createSaveEgdbButton() {
        Button btn_dbOutput = new Button("Save as EGDB");

        // createOutputFileChooser();

        btn_dbOutput.getStyleClass().add("btn2");
        this.saveEgdbBtn = btn_dbOutput;
        return btn_dbOutput;
    }

    public void setupCtrl() {
        CtrlSelectRefDir ctrlSelectRefDir = new CtrlSelectRefDir(this.app, this.app.getRefAlbum(), this.refSelectBtn);
        this.app.getRefAlbum().addObserver(ctrlSelectRefDir);
        this.refSelectBtn.setOnAction(ctrlSelectRefDir);
    }
}
