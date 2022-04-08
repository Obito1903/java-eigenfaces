public class Main {
	public static void main(String[] args) {
		//TODO argument parser
		//Compiler
		/* Argument formats
		 * -c [/path/to/referencedb]?						(path defaults to ./img/reference if unspecified)
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
