package gui;

import eigenfaces.EigenFacesDB;
import gui.utils.ImageAlbum;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.DirectoryChooser;

import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class ConfigPane extends VBox {

    FileChooser egdbFileChooser = new FileChooser();

    private ImageAlbum defaultDB;	//Temporary

    private ArrayList<Button> displayRef = new ArrayList<Button>();

    DirectoryChooser outputDirChooser = new DirectoryChooser();
	FileChooser outputFileChooser = new FileChooser();
    DirectoryChooser refDirChooser = new DirectoryChooser();

    private ImageAlbum references;

    EigenFacesDB db = null;
    String refDir = null;

    /**/
    private void createOutputDirChooser() {
		outputDirChooser.setTitle("Select directory to export to");
	}

	private void createOutputFileChooser() {
		egdbFileChooser.setTitle("Select file to export to");
		egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Eigenface Databases (.egdb)", "*.egdb"));
	}

    private void createEgdbFileChooser() {
		egdbFileChooser.setTitle("Select Eigenface database file");
		egdbFileChooser.getExtensionFilters().addAll(new ExtensionFilter("Eigenface Databases (.egdb)", "*.egdb"));
	}

    public HBox createLoadEgdb(Label dbStatusLabel) {
		HBox hb_loadEGDB = new HBox();
		hb_loadEGDB.getStyleClass().add("hb_loadEGDB");
		Button btn_loadEGDB = new Button("Load eigenfaces database");
		btn_loadEGDB.getStyleClass().add("btn1");
		createEgdbFileChooser();
		hb_loadEGDB.getChildren().add(btn_loadEGDB);
		return hb_loadEGDB;
	}

    public Button createCompileButton(Label compileStatusLabel, Label dbStatusLabel) {
		Button btn_compile = new Button("Compile");
		btn_compile.getStyleClass().add("btn2");
		return btn_compile;
	}

    public Button createImgOutputButton() {
		Button btn_imgOutput = new Button("Export images");
		createOutputDirChooser();
		btn_imgOutput.getStyleClass().add("btn2");
		return btn_imgOutput;
	}

	public Button createDbOutputButton() {
		Button btn_dbOutput = new Button("Save as EGDB");
		createOutputFileChooser();
		btn_dbOutput.getStyleClass().add("btn2");
		return btn_dbOutput;
	}

    private void createRefDirChooser() {
		refDirChooser.setTitle("Select the reference image directory");
	}

    public Button createRefDirButton() {
		Button btn_compRef = new Button("Select reference \n image directory");
		createRefDirChooser();
		btn_compRef.getStyleClass().add("btn2");
		return btn_compRef;
	}

    public VBox createKValue() {
		Label label = new Label("K value");
		label.getStyleClass().add("label_kvalue");
		label.setTextFill(Color.WHITE);
		TextField tf = new TextField();
		tf.setText("1");
		tf.setPrefSize(30,20);

		VBox vb = new VBox();
		vb.getChildren().addAll(label, tf);
		return vb;
	}

    public FlowPane createCompilationMenu(Label dbStatusLabel) {
		
		Button btn_compRef = createRefDirButton();
		Label compileStatusLabel = new Label("");
		//TODO Make the slider in function of number of files in refDir
		VBox vb_kValue = createKValue(/*nbrOfFiles*/);
		Button btn_compile = createCompileButton(compileStatusLabel,dbStatusLabel); 
		Button btn_imgOutput = createImgOutputButton();
		Button btn_dbOutput = createDbOutputButton();
		
		FlowPane fp_compileEGDB = new FlowPane();
		fp_compileEGDB.setHgap(75);
		fp_compileEGDB.setAlignment(Pos.CENTER);
		fp_compileEGDB.getChildren().addAll(btn_compRef,vb_kValue,btn_compile,btn_imgOutput,btn_dbOutput,compileStatusLabel);
		
		return fp_compileEGDB;
	}

    public FlowPane createEgdbDisplay() {
		FlowPane display = new FlowPane(10,10);
		//display.
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
		gp_eigenfaces.setPrefSize(400,400);
		//Background bg = new Background();
		/*Table of all of the eigenfaces from the loaded database - ImageView*/
		//TODO

		return gp_eigenfaces;
	} 


    public void setFirstLine(){
        /*Horizontal Box for the top borderpane*/
        HBox hb_top = new HBox();

        /*Button to switch scene with Hbox to fit the button*/
        HBox hb_back = new HBox();
        hb_back.setSpacing(25);
        hb_back.getStyleClass().add("hb_back");
        Button btn_back = new Button("Back");

        /*Title of the window*/
        Label title = new Label("EIGENFACES DATABASE CONFIGURATION");
		title.getStyleClass().add("title");

        /*Adding child to the hbox*/
        hb_top.getChildren().addAll(btn_back, title);
		hb_top.setAlignment(Pos.CENTER);

        this.getChildren().add(hb_top);
    }

    public void setSecondLine(Label dbStatusLabel){
        /*EGDB loading button*/
        HBox hb_loadEGDB = createLoadEgdb(dbStatusLabel);
		hb_loadEGDB.setAlignment(Pos.CENTER);

        this.getChildren().add(hb_loadEGDB);
    }

    public void setThirdLine(){
        /*EGDB table*/
		GridPane gp_eigenfaces = createEigenfacesTable();
        this.getChildren().add(gp_eigenfaces);
    }

    public void setFourthLine(Label dbStatusLabel){
        FlowPane fp_compileEGDB = createCompilationMenu(dbStatusLabel);
		fp_compileEGDB.setAlignment(Pos.BASELINE_CENTER);
		fp_compileEGDB.getStyleClass().add("fp_compileEGDB");
		this.getChildren().add(fp_compileEGDB);
    }

    public ConfigPane(){
        super();
		this.getStylesheets().add("../css/configEGDB.css");

        /*Label used to show potential error on EigenPane*/
        Label dbStatusLabel = new Label("No database loaded");
		dbStatusLabel.getStyleClass().add("dbStatusLabel");

        setFirstLine();

        setSecondLine(dbStatusLabel);

        setThirdLine();

        setFourthLine(dbStatusLabel);
    }
}
