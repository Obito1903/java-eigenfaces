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
		for (String path : files)
			if (path.toLowerCase().endsWith(".jpg"))
				i++;
		ImageVector[] images = new ImageVector[i];
		i = 0; // Have to loop twice here, sadly
		for (String path : files) {
			if (path.toLowerCase().endsWith(".jpg")) {
				images[i] = new ImageVector(dbPath + "/" + path);
				if (images[i].getHeight() != images[0].getHeight() || images[i].getWidth() != images[0].getWidth())
					throw new ImageSizeMismatchException("Image dimensions mismatched: (" + images[0].getFileName()
							+ " and " + images[i].getFileName() + ")");
				i++;
			}
		}
		return (images);
	}

	private static ImageVector[] centerImages(ImageVector[] images, Vector meanVector) {
		ImageVector[] centeredImages = new ImageVector[images.length];
		for (int i = 0; i < images.length; i++) {
			centeredImages[i] = images[i].center(meanVector);
		}
		return (centeredImages);
	}

	public static EigenFacesDB compileDB(final String dbPath, final int k) {
		System.out.println("Reading reference database at " + dbPath);
		ImageVector[] images = readImages(dbPath);

		// Compute the mean image
		System.out.println("Generating average face.");
		Vector averageFace = PCA.averageFace(images);

		// Save the average face
		((ImageVector) averageFace).saveToFile("img/averageFace.png");
		System.out.println("Average face generated.");

		// Center the images
		System.out.println("Centering images.");
		ImageVector[] centeredImages = centerImages(images, averageFace);
		System.out.println("Images Centered.");

		// Compute the eigenfaces
		System.out.println("Generating eigenface matrix.");
		Matrix e = PCA.eMatrix(centeredImages, k);
		System.out.println("Eigenface matrix generated.");

		/*
		 * //Print eigenfaces
		 * for (int i = 0; i < 23; i++) {
		 * ImageVector eig = new ImageVector(e.getColumn(i).getElements(),
		 * images[0].getHeight(), images[0].getWidth(), "EIGEN_0.jpg");
		 * System.out.println(eig.centerReduce());
		 * }
		 */

		// Compute the weight matrix
		System.out.println("Generating weight matrix.");
		WeightMatrix g = new WeightMatrix(e, centeredImages);
		System.out.println("Dimensions: " + g.getNbRow() + "x" + g.getNbColumn());
		return new EigenFacesDB(averageFace, e, g);
		// TODO Still need to write the corresponding names of G matrix
		// Code below is tests to be removed
		// ImageVector test = new ImageVector("img/reference/Mateo_Mongour_1.jpg");
		// Vector stuff = e.transpose().multiply(test);
		// for (int i = 0; i < g.getNbRow(); i++)
		// System.out.println("Distance to " + images[i].getName() + ": " +
		// Vector.distance(stuff, g.getRow(i)));
	}

	public static void verifyValidity() {
	}

	public static Matrix[] readCompiledMatrices() {
		return null;
	}

	public static void main(String[] args) {
		EigenFacesDB db = compileDB("img/reference", 3);
		// System.out.println(db.g.toString());
		db.saveToFile("test.egdb");
	}
}
