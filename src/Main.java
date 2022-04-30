import image.ImageSizeMismatchException;
import image.ImageVector;
import math.KValueOutOfBoundsException;

import java.util.List;

import org.apache.commons.cli.*;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
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
		
		BorderPane windowEGDB = new BorderPane();
		
		VBox vb_loadEGDB = new VBox();
		/*VBox formatting*/
		//TODO
		Button btn_loadEGDB = new Button("Load eigenfaces database");
		btn_loadEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select EGDB folder*/
				//TODO
			}
		});
		vb.loadEGDB.getChildren().add(btn_loadEGDB);

		VBox vb_eigenfaces = new VBox();
		/*Table of all of the eigenfaces from the loaded database - ImageView*/
		//TODO

		HBox hb_compileEGDB = new HBox();
		hb_compileEGDB.setPadding(new Insets(15,50,15,50));
		hb_compileEGDB.setSpacing(20);
		hb_compileEGDB.setStyle("-fx-background-color: #336699;"):
		Button btn_compEGDB = new Button("Select EGDB file");
		btn_compEGDB.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*Select EGDB file via filechooser*/
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
				//TODO
			}
		});
		hb_compileEGDB.getChildren().addAll(btn_compEGDB, btn_compOutput, btn_compile);

		windowEGDB.setTop(vb_loadEGDB);
		windowEGDB.setCenter(vb_eigenfaces);
		windowEGDB.setBottom(hb_compileEGDB);

		Scene scene1 = new Scene(windowTest);
		Scene scene2 = new Scene(windowEGDB);

		primaryStage.setScene(scene2, 1280, 720);
		primaryStage.show();
	}
}	
