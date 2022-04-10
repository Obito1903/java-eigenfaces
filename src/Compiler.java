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
	public static ImageVector[] readImages(String dbPath) throws ImageSizeMismatchException {
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
				images[i] = new ImageVector(dbPath + "/" + path);
				if (images[i].getHeight() != images[0].getHeight() || images[i].getWidth() != images[0].getWidth())
					throw new ImageSizeMismatchException("Image dimensions mismatched: (" + images[0].getFileName() + " and " + images[i].getFileName() + ")");
				i++;
			}
		}
		return(images);
	}

	public static void compileDB(final String dbPath, final int k) {
		System.out.println("Reading reference database at " + dbPath);
		ImageVector[] images = readImages(dbPath);

		System.out.println("Generating average face.");
		Vector averageFace = PCA.averageFace(images);
		//System.out.println(averageFace);
		//TODO generate average face as an image file
		System.out.println("Average face generated.");
		
		System.out.println("Generating eigenface matrix.");
		Matrix e = PCA.eMatrix(images, k);
		System.out.println("Eigenface matrix generated.");

		/*
		//Print eigenfaces
		for (int i = 0; i < 23; i++) {
			ImageVector eig = new ImageVector(e.getColumn(i).getElements(), images[0].getHeight(), images[0].getWidth(), "EIGEN_0.jpg");
			System.out.println(eig.centerReduce());
		}
		*/

		System.out.println("Generating weight matrix.");
		Matrix g = PCA.gMatrix(e, new Matrix(images));
		System.out.println("Dimensions: " + g.getNbRow() + "x" + g.getNbColumn());
		//TODO write the E and G matrix in a (JSON?) file, along with corresponding names
	}

	public static void verifyValidity() {
	}

	public static Matrix[] readCompiledMatrices() {
		return null;
	}

	public static void main(String[] args) {
		compileDB("img/reference", 0);
	}
}
