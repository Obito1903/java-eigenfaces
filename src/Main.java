import org.apache.commons.cli.*;

public class Main {
	public static void main(String[] args) {

		Options options = new Options();

		Option compile = Option.builder("c")
				.desc("Compile database from images folder")
				.longOpt("compile")
				.hasArgs()
				.argName("image folder path> <output file path")
				.build();

		Option test = Option.builder("t")
				.desc("Test an image against database")
				.longOpt("test")
				.hasArgs()
				.argName("image path> <compiled database file path")
				.build();

		Option k = Option.builder("k")
				.desc("k value, optional")
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
			} else if (cmd.hasOption("t")) {
				String[] argsTest = cmd.getOptionValues("t");
				//if (argsTest.length == 2 && Compiler.verifyValidity(argsTest[1])) {
				//	  Test.test(argsTest[0], argsTest[1]);
				//}
			} else {
				throw new ParseException("No command specified");
			}
		} catch (ParseException e) {
			HelpFormatter helper = new HelpFormatter();
			helper.printHelp("java -jar <programme>", options);
		}




		//Tester
		/* Argument formats
		 * -t [/path/to/testimg]
		 */

		//call Compiler.verifyValidity() to verify if DB is good (up to date and output files not tampered with)
		//then call Test.test(imagePath)
		
	}
}
