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

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.event.*;

import javafx.geometry.Orientation;
import javafx.geometry.Insets;

import java.io.IOException;

public class Main extends Application {

	/*Class attributes of the different sources in the scene*/
	FileChooser egdbFileChooser = new FileChooser();
	FileChooser outputFileChooser = new FileChooser();

	public void createEgdbFileChooser(){
		egdbFileChooser.setTitle("Open egdb file");
		egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Egdb Files", "*.egdb"));
	}

	public void createOutputFileChooser(){
		egdbFileChooser.setTitle("Choose the final location");
		//egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Egdb Files", "*.egdb"));
	}

	@Override
	public void start(Stage primaryStage) {

		/*Title of the scene*/
		primaryStage.setTitle("Facial Recognition");
	/*Scene1*/
		BorderPane recognitionTest = new BorderPane();
	/*Scene2*/
		VBox configEGDB = new VBox();
	/*Return button*/	
		HBox hb_back = new HBox();
		hb_back.setPadding(new Insets(15,0,15,25));
		Button btn_back = new Button("Back");
		btn_back.setPrefSize(70,60);
		btn_back.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Swap back to the 'test' scene*/
				//TODO (perhaps use windows instead of scene changes - to be discussed)
			}
		});
		hb_back.getChildren().add(btn_back);
	/*EGDB loading button*/
		HBox hb_loadEGDB = new HBox();
		hb_loadEGDB.setPadding(new Insets(20,0,20,410));
		Button btn_loadEGDB = new Button("Load eigenfaces database");
		btn_loadEGDB.setPrefSize(250,30);
		btn_loadEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select EGDB folder*/
				//TODO
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
		hb_compileEGDB.setPadding(new Insets(15,50,15,320));
		hb_compileEGDB.setSpacing(20);
		hb_compileEGDB.setStyle("-fx-background-color: #336699;");
		Button btn_compRef = new Button("Select reference folder");
		btn_compRef.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select image references folder*/
				//TODO
			}
		});
		Button btn_compOutput = new Button("Select Output folder");
		btn_compOutput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select Output folder*/
				//TODO
			}
		});
		Button btn_compile = new Button("Compile");
		btn_compile.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Feet compilation*/
				//TODO (not selectable unless folders chosen)
			}
		});
		hb_compileEGDB.getChildren().addAll(btn_compRef, btn_compOutput, btn_compile);

		configEGDB.getChildren().addAll(hb_back, hb_loadEGDB, vb_eigenfaces, hb_compileEGDB);

		Scene scene1 = new Scene(recognitionTest, 1080, 720);
		Scene scene2 = new Scene(configEGDB, 1080, 720);

		primaryStage.setScene(scene2);
		primaryStage.show();
	}
}	
