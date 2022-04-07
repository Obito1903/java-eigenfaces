import java.io.File;
import java.io.IOException;

import math.*;
import image.*;

public class Compiler {

	/**
	 * Reads all images in the training database and makes a matrix out of it
	 *
	 * @param dbPath Path to the directory with the images
	 * @return The matrix with all images in a row as ImageVectors
	 */
	public static Matrix readImages(String dbPath) throws IOException {
		File dbDir = new File(dbPath);
		String[] files = dbDir.list();
		int i = 0;
		for (String path: files)
			if (path.toLowerCase().endsWith(".jpg"))
				i++;
		Vector[] images = new Vector[i];
		i = 0; //Have to loop twice here, sadly
		for (String path: files) {
			//TODO check if all images are same size
			if (path.toLowerCase().endsWith(".jpg")) {
				images[i] = new ImageVector(path);
				i++;
			}
		}
		return(new Matrix(images));
	}

	public static void compileDB() {
	}

	public static void verifyValidity() {
	}

	public static Matrix[] readCompiledMatrixes() {
		return null;
	}
}
