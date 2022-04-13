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

	/**
	 * Center every images in the DB
	 *
	 * @param images     The images to center
	 * @param meanVector The mean of all the images to center
	 * @return A new Array with the centered images
	 */
	private static ImageVector[] centerImages(ImageVector[] images, Vector meanVector) {
		ImageVector[] centeredImages = new ImageVector[images.length];
		for (int i = 0; i < images.length; i++) {
			centeredImages[i] = images[i].center(meanVector);
		}
		return (centeredImages);
	}

	/**
	 * Apply the eigenFace algorithm to the bank of images and store it
	 *
	 * @param dbPath The path to the reference images for the algo
	 * @param k      The number of eigenFaces to keep
	 * @return A Database with all the necesary informations to then serch a face
	 *         inside it
	 */
	public static EigenFacesDB compileDB(final String dbPath, final int k, boolean debug) {
		System.out.println("Reading reference database at " + dbPath);
		ImageVector[] images = readImages(dbPath);

		// Compute the mean image
		System.out.println("Generating average face.");
		Vector averageFace = PCA.averageFace(images);

		// Center the images
		System.out.println("Centering images.");
		ImageVector[] centeredImages = centerImages(images, averageFace);
		System.out.println("Images Centered.");
		// ImageVector[] centeredImages = images;

		// Compute the eigenfaces
		System.out.println("Generating eigenface matrix.");
		Matrix e = PCA.eMatrix(centeredImages, k);
		System.out.println("Eigenface matrix generated.");

		if (debug) {

			// Create eigenfaces images folder
			String directory = new File(dbPath).getAbsolutePath();
			directory += "/../";

			// Save the average face
			((ImageVector) averageFace).centerReduce().saveToFile(directory + "averageFace.png");
			System.out.println("Average face generated and saved : " + directory + "averageFace.png");

			directory += "eigens/";

			if (!(new File(directory).isDirectory())) {
				new File(directory).mkdir();
			}

			// Print eigenfaces
			for (int i = 0; i < e.getNbColumn(); i++) {
				ImageVector eig = new ImageVector(e.getColumn(i).getElements(),
						images[0].getHeight(), images[0].getWidth(), "EIGEN_" + i + ".png");
				eig.centerReduce().saveToFile(directory + eig.getFileName());

			}
			System.out.println("Eigenfaces generated and saved : " + directory);
		}



		// Compute the weight matrix
		System.out.println("Generating weight matrix.");
		WeightMatrix g = new WeightMatrix(e, centeredImages);
		System.out.println("G dimensions: " + g.getNbRow() + "x" + g.getNbColumn());
		System.out.println("E dimensions: " + e.getNbRow() + "x" + e.getNbColumn());
		return new EigenFacesDB(averageFace, e, g);
	}

	// TODO
	public static void verifyValidity() {
	}

	public static void main(String[] args) {
		EigenFacesDB db = compileDB("img/reference", 3, true);
		db.saveToFile("test.egdb");
	}
}
