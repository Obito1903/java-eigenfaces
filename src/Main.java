import eigenfaces.Compiler;
import eigenfaces.EigenFacesDB;
import eigenfaces.image.ImageSizeMismatchException;
import eigenfaces.image.ImageVector;
import eigenfaces.math.KValueOutOfBoundsException;
import gui.utils.ImageAlbum;
import gui.utils.Picture;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.cli.*;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.DirectoryChooser;

import javafx.event.*;

import javafx.geometry.Orientation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

	/* Class attributes of the different sources in the scene */
	private ImageAlbum eigenfaces;
	private ImageAlbum references;
	private ImageAlbum defaultDB;
	private ArrayList<Button> displayRef;
	private ImageView imgTest;
	private ImageView imgRef;

	FileChooser egdbFileChooser = new FileChooser();
	DirectoryChooser refDirChooser = new DirectoryChooser();
	DirectoryChooser outputDirChooser = new DirectoryChooser();
	FileChooser outputFileChooser = new FileChooser();

	FileChooser testFileChooser = new FileChooser();

	String refDir = null;
	EigenFacesDB db = null;
	String testImg = null;

	Picture defaultImg = new Picture("file:gaspard_le_fantome.jpg");

	private void createEgdbFileChooser() {
		egdbFileChooser.setTitle("Select Eigenface database file");
		egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Eigenface Databases (.egdb)", "*.egdb"));
	}

	private void createRefDirChooser() {
		refDirChooser.setTitle("Select the reference image directory");
	}

	private void createOutputDirChooser() {
		outputDirChooser.setTitle("Select directory to export to");
	}

	private void createOutputFileChooser() {
		egdbFileChooser.setTitle("Select file to export to");
		egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Eigenface Databases (.egdb)", "*.egdb"));
	}

	private void createTestFileChooser() {
		testFileChooser.setTitle("Select image to test");
		testFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
	}

	public Button createRefDirButton(Stage primaryStage) {
		Button btn_compRef = new Button("Select reference image directory");
		createRefDirChooser();
		btn_compRef.getStyleClass().add("btn_comp");
		btn_compRef.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/* Select image references folder */
				try {
					refDir = refDirChooser.showDialog(primaryStage).getAbsolutePath();
					references = new ImageAlbum(refDir);
					createEgdbDisplay();
				} catch (NullPointerException e) {
					// No directory was selected: do nothing
				}
			}
		});
		return btn_compRef;
	}

	public Button createCompileButton(Stage primaryStage, Label compileStatusLabel, Label dbStatusLabel) {
		Button btn_compile = new Button("Compile");
		btn_compile.getStyleClass().add("btn_comp");
		btn_compile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				try {
					db = Compiler.compileDB(refDir, 0/* Get k */, compileStatusLabel, primaryStage);
					dbStatusLabel.setText("Database loaded (" + db.getG().getNbRow() + " images)");
				} catch (NullPointerException e) {
					new Alert(AlertType.INFORMATION, "No reference image directory is selected.").showAndWait();
				} catch (Exception e) {
					Alert error = new Alert(AlertType.ERROR, e.toString());
					error.setHeight(300);
					error.setWidth(533);
					error.showAndWait();
				}
			}
		});
		return btn_compile;
	}

	public Button createImgOutputButton(Stage primaryStage) {
		Button btn_imgOutput = new Button("Export images");
		createOutputDirChooser();
		btn_imgOutput.getStyleClass().add("btn_comp");
		btn_imgOutput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/* Select Output folder */
				try {
					Compiler.saveImages(db, outputDirChooser.showDialog(primaryStage).getAbsolutePath());
					// Maybe also do reconstitutions?
					new Alert(AlertType.INFORMATION, "Average face and eigenfaces saved.").showAndWait();
				} catch (NullPointerException e) {
					// No directory was selected: do nothing
				}
			}
		});
		return btn_imgOutput;
	}

	public Button createDbOutputButton(Stage primaryStage) {
		Button btn_dbOutput = new Button("Save as EGDB");
		createOutputFileChooser();
		btn_dbOutput.getStyleClass().add("btn_comp");
		btn_dbOutput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/* Select Output folder */
				try {
					if (db == null)
						new Alert(AlertType.INFORMATION, "No database is loaded.").showAndWait();
					else {
						db.saveToFile(outputFileChooser.showSaveDialog(primaryStage).getAbsolutePath());
						new Alert(AlertType.INFORMATION, "Database exported successfully.").showAndWait();
					}
				} catch (NullPointerException e) {
					// No directory was selected: do nothing
				}
			}
		});
		return btn_dbOutput;
	}

	public HBox createCompilationMenu(Stage primaryStage, Label dbStatusLabel) {
		HBox hb_compileEGDB = new HBox();
		hb_compileEGDB.getStyleClass().add("hb_compEGDB");
		hb_compileEGDB.setSpacing(120);

		Button btn_compRef = createRefDirButton(primaryStage);
		Label compileStatusLabel = new Label("");
		// TODO create a slider for value of k, make it in function of number of files
		// in refDir
		Button btn_compile = createCompileButton(primaryStage, compileStatusLabel, dbStatusLabel);
		Button btn_imgOutput = createImgOutputButton(primaryStage);
		Button btn_dbOutput = createDbOutputButton(primaryStage);

		hb_compileEGDB.getChildren().addAll(btn_compRef, btn_compile, btn_imgOutput, btn_dbOutput, compileStatusLabel);
		return hb_compileEGDB;
	}

	public VBox createEigenfacesTable() {
		VBox vb_eigenfaces = new VBox();
		vb_eigenfaces.getStyleClass().add("vb_eigenfaces");
		/* Table of all of the eigenfaces from the loaded database - ImageView */
		// TODO
		return vb_eigenfaces;
	}

	public FlowPane createEgdbDisplay() {
		FlowPane display = new FlowPane(10, 10);
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

	public FlowPane createImgDisplay() {
		FlowPane paneDisplay = new FlowPane(10, 10);
		paneDisplay.setAlignment(Pos.BASELINE_LEFT);
		paneDisplay.setPrefSize(177, 250);
		// TODO
		// if references != null then display current image in 'references'
		// ImageDatabase object
		// control + observer
		paneDisplay.getChildren().add(imgRef);
		return paneDisplay;
	}

	@Override
	public void start(Stage primaryStage) {
		/* Title of the scene */
		primaryStage.setTitle("Facial Recognition");
		// primaryStage.setFullScreen(true);
		// Benjamin say : C'est horrrible le fullscreen
		// Opinion rejected
		// I don't care
		defaultDB = new ImageAlbum();
		imgRef = new ImageView(defaultDB.getCurrentPicture().getImage());
		displayRef = new ArrayList<Button>();

		/* Scene 1 layout, panes & labels */
		HBox recognitionTest = new HBox();
		BorderPane leftSide = new BorderPane();
		BorderPane center = new BorderPane();
		BorderPane rightSide = new BorderPane();

		Scene scene1 = new Scene(recognitionTest, 1280, 720);
		scene1.getStylesheets().add("../css/style.css");
		recognitionTest.getStyleClass().add("recognitionTest");
		leftSide.getStyleClass().add("leftSide");
		center.getStyleClass().add("center");
		rightSide.getStyleClass().add("rightSide");

		recognitionTest.setAlignment(Pos.CENTER);

		/* Scene 2 panes & layout */
		VBox configEGDB = new VBox();
		Scene scene2 = new Scene(configEGDB, 1280, 720);
		scene2.getStylesheets().add("../css/style.css");

		/* Scene1 */

		/* Left Side */

		/* Center Image view of test image */
		ImageView testImgDisplay = new ImageView(/* img url selected */);
		testImgDisplay.getStyleClass().add("testImgDisplay");
		// testImgDisplay.fitWidthProperty().bind(leftTest.widthProperty());

		/* Horizontal top box for each config button */
		HBox hb_top = new HBox();
		hb_top.getStyleClass().add("hb_top");
		hb_top.setSpacing(10);

		/* Replace on top if not working */
		Label dbStatusLabel = new Label("No database loaded");
		dbStatusLabel.getStyleClass().add("dbStatusLabel");

		/* Button to choose the egdb file */
		Button btn_configEGDB = new Button("Databases\n(Compile/Load/View)");
		btn_configEGDB.getStyleClass().add("btn_configEGDB");
		btn_configEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(scene2);
			}
		});

		/* Button to choose the test image */
		Button btn_imgFile = new Button("Select test image");
		createTestFileChooser();
		btn_imgFile.getStyleClass().add("btn_imgFile");
		btn_imgFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/* filechooser - image the user wants to test */
				File f = testFileChooser.showOpenDialog(primaryStage);
				testImg = f.getAbsolutePath();
				testImgDisplay.setImage(new Image(f.toURI().toString()));
			}
		});

		hb_top.getChildren().addAll(dbStatusLabel, btn_configEGDB, btn_imgFile); // Adding each button to the top hbox

		leftSide.setTop(hb_top);
		leftSide.setCenter(testImgDisplay);

		/* Center */

		/* Test button */
		Button btn_imgTest = new Button("Test");
		btn_imgTest.getStyleClass().add("btn_imgTest");
		btn_imgTest.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/* scene change to configEGDB */
				try {
					if (db == null)
						new Alert(AlertType.INFORMATION, "No database is loaded.").showAndWait();
					else if (testImg == null)
						new Alert(AlertType.INFORMATION, "No test image is selected.").showAndWait();
					else
						Test.findBestMatch(db, new ImageVector(testImg), false);
					// TODO change up Test class to get all images with a distance below 40
				} catch (Exception e) {
					Alert error = new Alert(AlertType.ERROR, e.toString());
					error.setHeight(300);
					error.setWidth(533);
					error.showAndWait();
				}
			}
		});

		center.setCenter(btn_imgTest);

		/* Right side */

		/* Top flow pane use to preview the better matches */
		FlowPane egdbDisplay = createEgdbDisplay();
		egdbDisplay.getStyleClass().add("egdbDisplay");

		/* Center Image view of the matched image */
		ImageView matchedImgDisplay = new ImageView(/* img url selected */);
		matchedImgDisplay.getStyleClass().add("matchedImgDisplay");

		/* Bottom label to display the distance of the best match */
		// TODO distance

		rightSide.setTop(egdbDisplay);
		rightSide.setCenter(matchedImgDisplay);
		// rightSide.setBottom(distance);

		/* Adding the two side part to the main scene */
		recognitionTest.getChildren().addAll(leftSide, center, rightSide);

		/* Scene2 */
		/* Return button */
		HBox hb_back = new HBox();
		hb_back.getStyleClass().add("hb_back");
		Button btn_back = new Button("Back");
		btn_back.getStyleClass().add("btn_back");
		btn_back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(scene1);
				// (perhaps use windows instead of scene changes - to be discussed)
			}
		});
		hb_back.getChildren().add(btn_back);

		/* EGDB loading button */
		HBox hb_loadEGDB = new HBox();
		hb_loadEGDB.getStyleClass().add("hb_loadEGDB");
		Button btn_loadEGDB = new Button("Load eigenfaces database");
		btn_loadEGDB.getStyleClass().add("btn_loadEGDB");
		createEgdbFileChooser();
		btn_loadEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/* Select EGDB folder */
				try {
					db = new EigenFacesDB(egdbFileChooser.showOpenDialog(primaryStage).getAbsolutePath());
					dbStatusLabel.setText("Database loaded (" + db.getG().getNbRow() + " images)"); // Add k value
					// (characteristics)
				} catch (NullPointerException e) {
					// Do nothing
				} catch (Exception e) {
					Alert error = new Alert(AlertType.ERROR, e.toString());
					error.setHeight(300);
					error.setWidth(533);
					error.showAndWait();
				}
			}
		});
		hb_loadEGDB.getChildren().add(btn_loadEGDB);

		/* EGDB table */
		VBox vb_eigenfaces = createEigenfacesTable();
		HBox hb_compileEGDB = createCompilationMenu(primaryStage, dbStatusLabel);
		configEGDB.getChildren().addAll(hb_back, hb_loadEGDB, vb_eigenfaces, hb_compileEGDB);

		primaryStage.setScene(scene2);
		primaryStage.show();
	}
}
