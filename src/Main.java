import image.ImageSizeMismatchException;
import image.ImageVector;
import math.KValueOutOfBoundsException;

import org.apache.commons.cli.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.*;
import javafx.geometry.Orientation;

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

		/**/
		VBox topLeft = new VBox();
		BorderPane topRight = new BorderPane();
		BorderPane bottomLeft = new BorderPane();
		BorderPane bottomRight = new BorderPane();

		/*Test*/
		//topLeft.getChildren().add(new Label("1"));
		topRight.setCenter(new Label("2"));
        bottomLeft.setCenter(new Label("3"));
        bottomRight.setCenter(new Label("4"));

		/*Top Left*/
		createEgdbFileChooser();
		createOutputFileChooser();
		topLeft.getChildren().addAll(new Label("Test"), egdbFileChooser, outputFileChooser);

		/**/
        TilePane root = new TilePane(Orientation.HORIZONTAL);
		root.setPrefColumns(2);
		root.setPrefTileWidth(640);
		root.setPrefTileHeight(360);

		/**/
		root.getChildren().addAll(topLeft, topRight, bottomLeft, bottomRight);

		/**/
		Scene scene = new Scene(root, 1280, 720);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {

		launch(args);

		/*Options options = new Options();

		Option compile = Option.builder("c")
				.desc("Compile database from images folder\n")
				.longOpt("compile")
				.hasArgs()
				.argName("image folder path> <output file path")
				.build();

		Option test = Option.builder("t")
				.desc("Test an image against database\n")
				.longOpt("test")
				.hasArgs()
				.argName("image path> <compiled database file path")
				.build();

		Option k = Option.builder("k")
				.desc("k value, optional\n")
				.hasArg()
				.argName("value")
				.required(false)
				.build();

		Option debug = Option.builder("d")
				.desc("Debug mode, optional\n")
				.longOpt("debug")
				.hasArg()
				.argName("output folder")
				.required(false)
				.build();

		options.addOption(compile);
		options.addOption(k);
		options.addOption(test);
		options.addOption(debug);

		DefaultParser parser = new DefaultParser();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("c") && cmd.getOptionValues("c").length == 2) {
				String[] argsCompile = cmd.getOptionValues("c");
				EigenFacesDB db = Compiler.compileDB(argsCompile[0], cmd.hasOption("k") ? Integer.parseInt(cmd.getOptionValue("k")) : 0, cmd.hasOption("d") ? cmd.getOptionValue("d") : null);
				db.saveToFile(argsCompile[1]);


			} else if (cmd.hasOption("t") && cmd.getOptionValues("t").length == 2) {
				String[] argsTest = cmd.getOptionValues("t");

				EigenFacesDB db = new EigenFacesDB(argsTest[1]);

				System.out.println("Best match against database : " + Test.findBestMatch(db, new ImageVector(argsTest[0]), cmd.hasOption("d")));
				
			} else {
				throw new ParseException("No command specified");
			}
		} catch (ParseException e) {
			HelpFormatter helper = new HelpFormatter();
			helper.printHelp("java -jar <programme>", options);
		} catch (IOException e) {
			System.out.println("Error while reading file : " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Error while loading database file, trying to load class.");
		} catch (KValueOutOfBoundsException e) {
			System.out.println("Error while loading database file : " + e.getMessage());
		} catch (ImageSizeMismatchException e) {
			System.out.println("Error while loading image : " + e.getMessage());
		}*/
		
	}
}
