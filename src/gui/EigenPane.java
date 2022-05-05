package gui;

import gui.components.ResultPane;
import gui.components.TestFacePane;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class EigenPane extends HBox {
    private TestFacePane leftPane;
    private BorderPane centerPane;
    private ResultPane righPane;

    public EigenPane() {
        super();
        this.getStylesheets().add(this.getClass().getResource("resources/style.css").toExternalForm());
        this.getStyleClass().add("recognitionTest");

        this.leftPane = new TestFacePane();
        this.centerPane = this.createCenterPane();
        this.righPane = new ResultPane();

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

    private BorderPane createCenterPane() {
        BorderPane center = new BorderPane();
        Button testBtn = new Button("Test against DB");

        center.setCenter(testBtn);
        return center;
    }
}
