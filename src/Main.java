import org.apache.commons.cli.*;

public class Main {
	public static void main(String[] args) {

		Options options = new Options();

		Option compile = Option.builder("c")
				.desc("Compile database from images folder")
				.longOpt("compile")
				.hasArgs()
				.argName("image folder path> <output file path")
				.required()
				.build();

		Option test = Option.builder("t")
				.desc("Test an image against database")
				.longOpt("test")
				.hasArgs()
				.argName("image path> <compiled database file path")
				.required()
				.build();

		options.addOption(compile);
		options.addOption(test);

		DefaultParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			HelpFormatter helper = new HelpFormatter();
			helper.printHelp("java -jar <programme>", options);
		}

		//TODO argument parser
		//Compiler
		/* Argument formats
		 * -c [/path/to/referencedb]?						Compiles the database in the specified directory (path defaults to ./img/reference if unspecified)
		 * -c [/path/to/referencedb]? -k [value]			(k will need to be checked if it's higher than the number of images)
		 */

		//call Compiler.compileDB(dbPath, k)


		//Tester
		/* Argument formats
		 * -t [/path/to/testimg]
		 */

		//call Compiler.verifyValidity() to verify if DB is good (up to date and output files not tampered with)
		//then call Test.test(imagePath)
		
	}
}
