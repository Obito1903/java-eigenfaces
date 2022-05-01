import image.ImageSizeMismatchException;
import image.ImageVector;
import math.KValueOutOfBoundsException;

import java.util.List;

import org.apache.commons.cli.*;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
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

	FileChooser testFileChooser = new FileChooser();

	String refDir;
	EigenFacesDB db;
	String testImg;

	private void createEgdbFileChooser() {
		egdbFileChooser.setTitle("Select Eigenface database file");
		egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Eigenface Databases (.egdb)", "*.egdb"));
	}

	private void createRefDirChooser() {
		refDirChooser.setTitle("Select the reference image directory");
	}

	private void createOutputFileChooser() {
		outputDirChooser.setTitle("Select the final location");
	}

	private void createTestFileChooser() {
		testFileChooser.setTitle("Select image to test");
		testFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg"));
	}

	@Override
	public void start(Stage primaryStage) {
		/*Title of the scene*/
		primaryStage.setTitle("Facial Recognition");

		/*Scene 1 panes & layout*/
		BorderPane recognitionTest = new BorderPane();
		BorderPane leftTest = new BorderPane();
		BorderPane rightTest = new BorderPane();
		Scene scene1 = new Scene(recognitionTest, 1080, 720);

		/*Scene 2 panes & layout*/
		VBox configEGDB = new VBox();
		Scene scene2 = new Scene(configEGDB, 1080, 720);

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
			}
		});
		
		hb_match.getChildren().add(btn_imgTest);

		HBox hb_config = new HBox();
		hb_config.setSpacing(50);
		hb_config.setPadding(new Insets(15,200,15,200));

		Button btn_configEGDB = new Button("EGDB settings");
		btn_configEGDB.setPrefSize(200, 35);
		btn_configEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(scene2);
			}
		});

		Button btn_imgFile = new Button("Image file"); 
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

		hb_config.getChildren().addAll(btn_configEGDB, btn_imgFile);

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
		hb_back.setPadding(new Insets(15,0,15,25));
		Button btn_back = new Button("Back");
		btn_back.setPrefSize(70,60);
		btn_back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				primaryStage.setScene(scene1);
				//(perhaps use windows instead of scene changes - to be discussed)
			}
		});
		hb_back.getChildren().add(btn_back);
		/*EGDB loading button*/
		HBox hb_loadEGDB = new HBox();
		hb_loadEGDB.setPadding(new Insets(20,0,20,410));
		Button btn_loadEGDB = new Button("Load eigenfaces database");
		btn_loadEGDB.setPrefSize(250,30);
		createEgdbFileChooser();
		btn_loadEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select EGDB folder*/
				try {
					db = new EigenFacesDB(egdbFileChooser.showOpenDialog(primaryStage).getAbsolutePath());
				} catch (NullPointerException e) {
					//Do nothing
				} catch (Exception e) {
					System.err.println(e);
					//Probably make an alert box?
				}
			}
		});
		hb_loadEGDB.getChildren().add(btn_loadEGDB);
		/*EGDB table*/
		VBox vb_eigenfaces = new VBox();
		vb_eigenfaces.setPadding(new Insets(50,200,500,200));
		/*Table of all of the eigenfaces from the loaded database - ImageView*/
		//TODO
		
		/*EGDB compilation menu*/
		HBox hb_compileEGDB = new HBox();
		hb_compileEGDB.setPadding(new Insets(15,150,15,150));
		hb_compileEGDB.setSpacing(120);
		hb_compileEGDB.setStyle("-fx-background-color: #336699;");
		
		Button btn_compRef = new Button("Select reference folder");
		createRefDirChooser();
		btn_compRef.setPrefSize(220,50);
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

		Button btn_compOutput = new Button("Select Output folder");
		btn_compOutput.setPrefSize(220,50);
		btn_compOutput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select Output folder*/
				//TODO
			}
		});

		//TODO create a slider for value of k, make it in function of number of files in refDir

		Button btn_compile = new Button("Compile");
		btn_compile.setPrefSize(220,50);
		btn_compile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Feet compilation*/ //Mateo says: what
				db = Compiler.compileDB(refDir, 0, null);
				db.saveToFile("db.egdb");
				//TODO (not selectable unless folders chosen) //Mateo says: just try catch lol
			}
		});
		hb_compileEGDB.getChildren().addAll(btn_compRef, btn_compOutput, btn_compile);

		configEGDB.getChildren().addAll(hb_back, hb_loadEGDB, vb_eigenfaces, hb_compileEGDB);


		primaryStage.setScene(scene1);
		primaryStage.show();
	}
}	
