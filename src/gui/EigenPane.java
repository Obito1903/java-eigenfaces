package gui;

import gui.components.ResultPane;
import gui.components.TestFacePane;
import gui.control.CtrlTestImage;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class EigenPane extends HBox {
    private Gui app;

    private TestFacePane leftPane;
    private BorderPane centerPane;
    private ResultPane righPane;

    private Button testBtn;

    public EigenPane(Gui app) {
        super();
        this.app = app;
        this.getStylesheets().add(this.getClass().getResource("resources/style.css").toExternalForm());
        this.getStyleClass().add("recognitionTest");

        this.leftPane = new TestFacePane(this.app);
        this.centerPane = this.createCenterPane();
        this.righPane = new ResultPane(this.app);

        HBox.setHgrow(this.leftPane, Priority.ALWAYS);
        HBox.setHgrow(this.righPane, Priority.ALWAYS);

        this.getChildren().addAll(this.leftPane, this.centerPane, this.righPane);
    }

    public TestFacePane getLeftPane() {
        return leftPane;
    }

    public BorderPane getCenterPane() {
        return centerPane;
    }

    public ResultPane getRightPane() {
        return righPane;
    }

    public Gui getApp() {
        return this.app;
    }

    private BorderPane createCenterPane() {
        BorderPane center = new BorderPane();
        Button testBtn = new Button("Test against DB");
        this.testBtn = testBtn;
        center.setCenter(testBtn);
        return center;
    }

    public void setupCtrl() {
        this.leftPane.setupCtrl();

        CtrlTestImage ctrlTestImage = new CtrlTestImage(this.app);
        this.testBtn.setOnAction(ctrlTestImage);
    }
}
