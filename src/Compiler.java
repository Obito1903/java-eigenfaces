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
	public static Matrix readImages(String dbPath) throws IOException, ImageSizeMismatchException {
		File dbDir = new File(dbPath);
		String[] files = dbDir.list();
		int i = 0;
		for (String path: files)
			if (path.toLowerCase().endsWith(".jpg"))
				i++;
		ImageVector[] images = new ImageVector[i];
		i = 0; //Have to loop twice here, sadly
		for (String path: files) {
			if (path.toLowerCase().endsWith(".jpg")) {
				System.out.println(path);
				images[i] = new ImageVector(dbPath + "/" +path);
				if (images[i].getHeight() != images[0].getHeight() || images[i].getWidth() != images[0].getWidth())
					throw new ImageSizeMismatchException("Image dimensions mismatched: (" + images[0].getFileName() + " and " + images[i].getFileName() + ")");
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

	public static void main(String[] args) {
		try {
			Matrix m = readImages("img/reference");
		} catch (ImageSizeMismatchException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

	}
}
