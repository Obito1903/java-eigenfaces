import org.apache.commons.cli.*;

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

		options.addOption(compile);
		options.addOption(k);
		options.addOption(test);

		DefaultParser parser = new DefaultParser();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("c")) {
				String[] argsCompile = cmd.getOptionValues("c");
				Compiler.compileDB(argsCompile[0], cmd.hasOption("k") ? Integer.parseInt(cmd.getOptionValue("k")) : 0);

				// TODO : add option to save the database to a file
			} else if (cmd.hasOption("t")) {
				String[] argsTest = cmd.getOptionValues("t");

				// TODO : read database file
				// TODO : test image against database
				// if (argsTest.length == 2 && Compiler.verifyValidity(argsTest[1])) {
				//	   Test.test(argsTest[0], argsTest[1]);
				// }
			} else {
				throw new ParseException("No command specified");
			}
		} catch (ParseException e) {
			HelpFormatter helper = new HelpFormatter();
			helper.printHelp("java -jar <programme>", options);
		}
		// TODO : catch everything else
		
	}
}
