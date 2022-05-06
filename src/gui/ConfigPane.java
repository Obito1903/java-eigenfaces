package gui;

import gui.components.ConfigAlbumsPane;
import gui.components.ConfigMenuPane;
import gui.control.CtrlOpenEgdb;
import gui.control.CtrlOpenScene;
import gui.utils.ImageAlbum;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ConfigPane extends BorderPane {

	// FileChooser egdbFileChooser = new FileChooser();

	private Gui app;

	private ImageAlbum defaultDB; // Temporary
	private ImageAlbum references;

	private HBox titleBox;
	private HBox loadBtnBox;
	private ConfigAlbumsPane imageContainer;
	private ConfigMenuPane actionMenu;
	private Label compileStatus;

	private Button backBtn;
	private Button loadBtn;

	private Label errorLabel;

	private ArrayList<Button> displayRef = new ArrayList<Button>();

	// DirectoryChooser outputDirChooser = new DirectoryChooser();
	// FileChooser outputFileChooser = new FileChooser();
	// DirectoryChooser refDirChooser = new DirectoryChooser();

	// EigenFacesDB db = null;
	// String refDir = null;

	public ConfigPane(Gui app) {
		super();
		this.app = app;
		// this.getStylesheets().add("../css/configEGDB.css");
		this.getStylesheets().add(this.getClass().getResource("resources/configEGDB.css").toExternalForm());

		/* Label used to show potential error on EigenPane */
		this.errorLabel = new Label("No database loaded");
		this.errorLabel.getStyleClass().add("dbStatusLabel");

		this.titleBox = createTitleBox();

		this.loadBtnBox = createLoadBtnBox();
		this.compileStatus = new Label("");

		VBox topPane = new VBox(this.titleBox, this.loadBtnBox);

		this.imageContainer = new ConfigAlbumsPane(app);

		this.actionMenu = new ConfigMenuPane(this.app);
		this.backBtn = createBackButton();

		VBox bottom = new VBox(this.compileStatus, this.actionMenu, this.backBtn);
		bottom.setAlignment(Pos.CENTER);

		this.setTop(topPane);
		this.setCenter(this.imageContainer);
		this.setBottom(bottom);

		// this.getChildren().addAll(this.titleBox, this.loadBtnBox,
		// this.imageContainer, this.actionMenu);
	}

	public HBox getTitleBox() {
		return this.titleBox;
	}

	public Button getBackButton() {
		return this.backBtn;
	}

	public ConfigAlbumsPane getImageList() {
		return this.imageContainer;
	}

	public ConfigMenuPane getConfigMenuPane() {
		return this.actionMenu;
	}

	public int getKValue() {
		return this.actionMenu.getKValue();
	}

	public Label getStatusLabel() {
		return this.compileStatus;
	}

	public void setCompileStatus(String value) {
		this.compileStatus.setText(value);
	}

	public Gui getApp() {
		return this.app;
	}

	/**/
	// private void createOutputDirChooser() {
	// outputDirChooser.setTitle("Select directory to export to");
	// }

	// private void createOutputFileChooser() {
	// egdbFileChooser.setTitle("Select file to export to");
	// egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Eigenface
	// Databases (.egdb)", "*.egdb"));
	// }

	// private void createEgdbFileChooser() {
	// egdbFileChooser.setTitle("Select Eigenface database file");
	// egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Eigenface
	// Databases (.egdb)", "*.egdb"));
	// }

	public HBox createLoadEgdb() {
		HBox hb_loadEGDB = new HBox();
		hb_loadEGDB.getStyleClass().add("hb_loadEGDB");
		Button btn_loadEGDB = new Button("Load eigenfaces database");
		btn_loadEGDB.getStyleClass().add("btn1");
		this.loadBtn = btn_loadEGDB;
		// createEgdbFileChooser();
		hb_loadEGDB.getChildren().add(btn_loadEGDB);
		return hb_loadEGDB;
	}

	public FlowPane createEgdbDisplay() {
		FlowPane display = new FlowPane(10, 10);
		// display.
		display.setAlignment(Pos.CENTER);
		if (references != null) {
			for (int i = 0; i < references.getSize(); i++) {
				Button btn = new Button();
				btn.setGraphic(new ImageView(references.getPicture(i).getIcon()));
				btn.setPadding(Insets.EMPTY);
				displayRef.add(btn);
				display.getChildren().add(btn);
			}
		} else {
			for (int i = 0; i < defaultDB.getSize(); i++) {
				Button btn = new Button();
				btn.setGraphic(new ImageView(defaultDB.getPicture(i).getIcon()));
				btn.setPadding(Insets.EMPTY);
				if (i == 0) {
					btn.setStyle("-fx-border-color:blue; -fx-border-width:4px;");
				}
				displayRef.add(btn);
				display.getChildren().add(btn);
			}
		}
		return display;
	}

	public GridPane createEigenfacesTable() {
		GridPane gp_eigenfaces = new GridPane();
		gp_eigenfaces.getStyleClass().add("eigenfacesTable");
		gp_eigenfaces.setPrefSize(400, 400);
		// Background bg = new Background();
		/* Table of all of the eigenfaces from the loaded database - ImageView */
		// TODO

		return gp_eigenfaces;
	}

	public Button createBackButton() {
		Button btn_back = new Button("Go to test Menu");
		return btn_back;
	}

	public HBox createTitleBox() {
		/* Horizontal Box for the top borderpane */
		HBox hb_top = new HBox();

		/* Title of the window */
		Label title = new Label("EIGENFACES DATABASE CONFIGURATION");
		title.getStyleClass().add("title");

		/* Adding child to the hbox */
		hb_top.getChildren().add(title);
		hb_top.setAlignment(Pos.CENTER);

		// this.getChildren().add(hb_top);
		return hb_top;
	}

	public HBox createLoadBtnBox() {
		/* EGDB loading button */
		HBox hb_loadEGDB = createLoadEgdb();
		hb_loadEGDB.setAlignment(Pos.CENTER);

		return hb_loadEGDB;
	}

	public GridPane createImagesContainer() {
		/* EGDB table */
		GridPane gp_eigenfaces = createEigenfacesTable();
		return gp_eigenfaces;
	}

	public void setupCtrl() {
		CtrlOpenScene CtrlOpenConfig = new CtrlOpenScene(this.app.getMainStage(),
				this.getBackButton(),
				this.app.getEigenScene());
		this.getBackButton().setOnAction(CtrlOpenConfig);
		this.actionMenu.setupCtrl();

		CtrlOpenEgdb ctrlOpenEgdb = new CtrlOpenEgdb(this.app);
		this.loadBtn.setOnAction(ctrlOpenEgdb);
	}
}
