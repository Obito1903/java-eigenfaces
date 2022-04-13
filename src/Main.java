import image.ImageSizeMismatchException;
import image.ImageVector;
import math.KValueOutOfBoundsException;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
	public static void main(String[] args) {

		Options options = new Options();

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
				EigenFacesDB db = Compiler.compileDB(argsCompile[0], cmd.hasOption("k") ? Integer.parseInt(cmd.getOptionValue("k")) : 0, cmd.hasOption("d"));
				db.saveToFile(argsCompile[1]);


			} else if (cmd.hasOption("t") && cmd.getOptionValues("t").length == 2) {
				String[] argsTest = cmd.getOptionValues("t");

				EigenFacesDB db = new EigenFacesDB(argsTest[1]);
				// TODO : test image against database
				// if (argsTest.length == 2 && Compiler.verifyValidity(argsTest[1])) {
				//	   Test.test(argsTest[0], argsTest[1]);
				// }

				System.out.println("Best match against database : " + Test.findBestMatch(db, new ImageVector(argsTest[0])));
				
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
		}
		
	}
}
