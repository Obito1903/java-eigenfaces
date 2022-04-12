package math;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Writer{

	/**
	 * Write the e and g Matrix into a .txt file
	 *
	 * @param eM Matrix e
	 * @param gM Matrix g
	 */

	public static void matrixWriter(Matrix eM, Matrix gM) {

		try {
			FileOutputStream f = new FileOutputStream(new File("egMatrix.txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(eM);
			o.writeObject(gM);

			//Closing object and file 
			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}

	}

}