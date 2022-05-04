import image.ImageSizeMismatchException;
import image.ImageVector;
import math.KValueOutOfBoundsException;

import abstraction.ImageDatabase;
import abstraction.Picture;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.cli.*;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

	/*Class attributes of the different sources in the scene*/
	private ImageDatabase eigenfaces;
	private ImageDatabase references;
	private ArrayList<Button> displayRef;
	
	FileChooser egdbFileChooser = new FileChooser();
	DirectoryChooser refDirChooser = new DirectoryChooser();
	DirectoryChooser outputDirChooser = new DirectoryChooser();
	FileChooser outputFileChooser = new FileChooser();

	FileChooser testFileChooser = new FileChooser();

	String refDir = null;
	EigenFacesDB db = null;
	String testImg = null;

	
	
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
				/*Select image references folder*/
				try {
					refDir = refDirChooser.showDialog(primaryStage).getAbsolutePath();
					references = new ImageDatabase(refDir);
					createEgdbDisplay();
				} catch (NullPointerException e) {
					//No directory was selected: do nothing
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
					db = Compiler.compileDB(refDir, 0/*Get k*/, compileStatusLabel, primaryStage);
					dbStatusLabel.setText("Database loaded (" + db.g.getNbRow() + " images)");
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
				/*Select Output folder*/
				try {
					Compiler.saveImages(db, outputDirChooser.showDialog(primaryStage).getAbsolutePath());
					//Maybe also do reconstitutions?
					new Alert(AlertType.INFORMATION, "Average face and eigenfaces saved.").showAndWait();
				} catch (NullPointerException e) {
					//No directory was selected: do nothing
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
				/*Select Output folder*/
				try {
					if (db == null)
						new Alert(AlertType.INFORMATION, "No database is loaded.").showAndWait();
					else {
						db.saveToFile(outputFileChooser.showSaveDialog(primaryStage).getAbsolutePath());
						new Alert(AlertType.INFORMATION, "Database exported successfully.").showAndWait();
					}
				} catch (NullPointerException e) {
					//No directory was selected: do nothing
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
		//TODO create a slider for value of k, make it in function of number of files in refDir
		Button btn_compile = createCompileButton(primaryStage, compileStatusLabel, dbStatusLabel); 
		Button btn_imgOutput = createImgOutputButton(primaryStage);	
		Button btn_dbOutput = createDbOutputButton(primaryStage);

		hb_compileEGDB.getChildren().addAll(btn_compRef, btn_compile, btn_imgOutput, btn_dbOutput, compileStatusLabel);
		return hb_compileEGDB;
	}

	public VBox createEigenfacesTable() {
		VBox vb_eigenfaces = new VBox();	
		vb_eigenfaces.getStyleClass().add("vb_eigenfaces");
		/*Table of all of the eigenfaces from the loaded database - ImageView*/
		//TODO
		return vb_eigenfaces;
	}

	public FlowPane createEgdbDisplay() {
		FlowPane display = new FlowPane(10,10);
		display.setAlignment(Pos.CENTER);
		if (references != null) {
			for(int i = 0; i < references.getSize(); i++) {
				Button btn = new Button();
				btn.setGraphic(new ImageView(references.getPicture(i).getIcon()));
				btn.setPadding(Insets.EMPTY);
				displayRef.add(btn);
				display.getChildren().add(btn);
			}
		}
		return display;
	}

	@Override
	public void start(Stage primaryStage) {
		/*Title of the scene*/
		primaryStage.setTitle("Facial Recognition");
		//primaryStage.setFullScreen(true); //Benjamin say : C'est horrrible le fullscreen

		/*Scene 1 layout, panes & labels*/
		BorderPane recognitionTest = new BorderPane();
		BorderPane leftTest = new BorderPane();
		BorderPane rightTest = new BorderPane();
		
		Scene scene1 = new Scene(recognitionTest, 1280, 720);
		scene1.getStylesheets().add("../css/recognition.css");
		recognitionTest.getStyleClass().add("recognitionTest");
		
		Label dbStatusLabel = new Label("No database loaded");
		dbStatusLabel.getStyleClass().add("dbStatusLabel");

		/*Scene 2 panes & layout*/
		VBox configEGDB = new VBox();
		Scene scene2 = new Scene(configEGDB, 1280, 720);
		scene2.getStylesheets().add("../css/configEGDB.css");

	/*Scene1*/
		/*Left window*/
		ImageView testImgDisplay = new ImageView(/*img url selected*/);
		
		HBox hb_match = new HBox();
		hb_match.getStyleClass().add("hb_match");
		
		Button btn_imgTest = new Button("Test");
		btn_imgTest.getStyleClass().add("btn_imgTest");
		btn_imgTest.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*scene change to configEGDB*/
				try {
					if (db == null)
						new Alert(AlertType.INFORMATION, "No database is loaded.").showAndWait();
					else if (testImg == null)
						new Alert(AlertType.INFORMATION, "No test image is selected.").showAndWait();
					else
						Test.findBestMatch(db, new ImageVector(testImg), false);
					//TODO change up Test class to get all images with a distance below 40
				} catch (Exception e) {
					Alert error = new Alert(AlertType.ERROR, e.toString());
					error.setHeight(300);
					error.setWidth(533);
					error.showAndWait();
				}
			}
		});
		
		hb_match.getChildren().add(btn_imgTest);

		HBox hb_config = new HBox();
		hb_config.getStyleClass().add("hb_config");
		hb_config.setSpacing(50);

		Button btn_configEGDB = new Button("Databases\n(Compile/Load/View)");
		btn_configEGDB.getStyleClass().add("btn_configEGDB");
		btn_configEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(scene2);
			}
		});

		Button btn_imgFile = new Button("Image to test"); 
		createTestFileChooser();
		btn_imgFile.getStyleClass().add("btn_imgFile");
		btn_imgFile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*filechooser - image the user wants to test*/
				File f = testFileChooser.showOpenDialog(primaryStage);
				testImg = f.getAbsolutePath();
				testImgDisplay.setImage(new Image(f.toURI().toString()));
			}
		});

		hb_config.getChildren().addAll(dbStatusLabel, btn_configEGDB, btn_imgFile);

		leftTest.setTop(hb_config);
		leftTest.setCenter(testImgDisplay);
		leftTest.setBottom(hb_match);

		/*Right*/
		FlowPane egdbDisplay = createEgdbDisplay();
		
		ImageView matchedImgDisplay = new ImageView(/*selected img from album*/);

		matchedImgDisplay.getStyleClass().add("matchedImgDisplay");
		//TODO distance
		
		//rightTest.setTop(egdbDisplay); ---> TD3 JavaFx
		rightTest.setCenter(matchedImgDisplay);
		//rightTest.setBottom(distance);

		recognitionTest.setRight(rightTest);
		recognitionTest.setLeft(leftTest);
		
	/*Scene2*/
		/*Return button*/	
		HBox hb_back = new HBox();
		hb_back.getStyleClass().add("hb_back");
		Button btn_back = new Button("Back");
		btn_back.getStyleClass().add("btn_back");
		btn_back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(scene1);
				//(perhaps use windows instead of scene changes - to be discussed)
			}
		});
		hb_back.getChildren().add(btn_back);
		
		/*EGDB loading button*/
		HBox hb_loadEGDB = new HBox();
		hb_loadEGDB.getStyleClass().add("hb_loadEGDB");
		Button btn_loadEGDB = new Button("Load eigenfaces database");
		btn_loadEGDB.getStyleClass().add("btn_loadEGDB");
		createEgdbFileChooser();
		btn_loadEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select EGDB folder*/
				try {
					db = new EigenFacesDB(egdbFileChooser.showOpenDialog(primaryStage).getAbsolutePath());
					dbStatusLabel.setText("Database loaded (" + db.g.getNbRow() + " images)");
				} catch (NullPointerException e) {
					//Do nothing
				} catch (Exception e) {
					Alert error = new Alert(AlertType.ERROR, e.toString());
					error.setHeight(300);
					error.setWidth(533);
					error.showAndWait();
				}
			}
		});
		hb_loadEGDB.getChildren().add(btn_loadEGDB);
		
		/*EGDB table*/
		VBox vb_eigenfaces = createEigenfacesTable();
		HBox hb_compileEGDB = createCompilationMenu(primaryStage, dbStatusLabel);
		configEGDB.getChildren().addAll(hb_back, hb_loadEGDB, vb_eigenfaces, hb_compileEGDB);


		primaryStage.setScene(scene1);
		primaryStage.show();
	}
}	
