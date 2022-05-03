import image.ImageSizeMismatchException;
import image.ImageVector;
import math.KValueOutOfBoundsException;

import java.util.List;

import org.apache.commons.cli.*;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
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

import java.io.File;
import java.io.IOException;

public class Main extends Application {

	/*Class attributes of the different sources in the scene*/
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

	@Override
	public void start(Stage primaryStage) {
		/*Title of the scene*/
		primaryStage.setTitle("Facial Recognition");

		/*Scene 1 layout, panes & labels*/
		BorderPane recognitionTest = new BorderPane();
		BorderPane leftTest = new BorderPane();
		BorderPane rightTest = new BorderPane();
		Scene scene1 = new Scene(recognitionTest, 1080, 720);
		scene1.getStylesheets().add("../css/recognition.css");
		Label dbStatusLabel = new Label("No database loaded");

		/*Scene 2 panes & layout*/
		VBox configEGDB = new VBox();
		Scene scene2 = new Scene(configEGDB, 1080, 720);
		scene2.getStylesheets().add("../css/configEGDB.css");

	/*Scene1*/
		/*Left window*/
		ImageView testImgDisplay = new ImageView(/*img url selected*/);
		HBox hb_match = new HBox();
		hb_match.setPadding(new Insets(50,300,100,300));
		
		Button btn_imgTest = new Button("TEST");
		btn_imgTest.setPrefSize(150,100);
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
		hb_config.setPadding(new Insets(15,200,15,200));

		Button btn_configEGDB = new Button("Databases\n(Compile/Load/View)");
		btn_configEGDB.setPrefSize(200, 35);
		btn_configEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(scene2);
			}
		});

		Button btn_imgFile = new Button("Image to test"); 
		createTestFileChooser();
		btn_imgFile.setPrefSize(200,35);
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
		//TODO displayEGDB
		ImageView matchedImgDisplay = new ImageView(/*selected img from album*/);
		//TODO distance
		
		//rightTest.setTop(displayEGDB); ---> TD3 JavaFx
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
		VBox vb_eigenfaces = new VBox();
		vb_eigenfaces.getStyleClass().add("vb_eigenfaces");
		/*Table of all of the eigenfaces from the loaded database - ImageView*/
		//TODO
		
		/*EGDB compilation menu*/
		HBox hb_compileEGDB = new HBox();
		hb_compileEGDB.getStyleClass().add("hb_compEGDB");
		hb_compileEGDB.setSpacing(120);
		
		Button btn_compRef = new Button("Select reference folder");
		createRefDirChooser();
		btn_compRef.getStyleClass().add("btn_comp");
		btn_compRef.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select image references folder*/
				try {
					refDir = refDirChooser.showDialog(primaryStage).getAbsolutePath();
				} catch (NullPointerException e) {
					//No directory was selected: do nothing
				}
			}
		});

		Label compileStatusLabel = new Label("");

		//TODO create a slider for value of k, make it in function of number of files in refDir

		Button btn_compile = new Button("Compile");
		btn_compile.getStyleClass().add("btn_comp");
		btn_compile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Feet compilation*/ //Mateo says: what
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


		hb_compileEGDB.getChildren().addAll(btn_compRef, btn_compile, btn_imgOutput, btn_dbOutput, compileStatusLabel);

		configEGDB.getChildren().addAll(hb_back, hb_loadEGDB, vb_eigenfaces, hb_compileEGDB);


		primaryStage.setScene(scene1);
		primaryStage.show();
	}
}	
