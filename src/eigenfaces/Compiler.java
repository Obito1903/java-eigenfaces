package eigenfaces;

import java.io.File;

import eigenfaces.image.*;
import eigenfaces.math.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
	public static EigenFacesDB compileDB(final String dbPath, final int k, Label status) {
		status.setText("Reading reference database at " + dbPath);
		System.out.println("Reading reference database at " + dbPath);
		ImageVector[] images = readImages(dbPath);

		// Compute the mean image
		status.setText("Generating average face.");
		System.out.println("Generating average face.");
		Vector averageFace = PCA.averageFace(images);

		// Center the images
		status.setText("Centering images.");
		System.out.println("Centering images.");

		ImageVector[] centeredImages = centerImages(images, averageFace);
		status.setText("Images Centered.");
		System.out.println("Images Centered.");

		// ImageVector[] centeredImages = images;

		// Compute the eigenfaces
		status.setText("Generating eigenface matrix.");
		System.out.println("Generating eigenface matrix.");

		Matrix e = PCA.eMatrix(centeredImages, k, false);
		status.setText("Eigenface matrix generated.");
		System.out.println("Eigenface matrix generated.");

		// Compute the weight matrix
		status.setText("Generating weight matrix.");
		System.out.println("Generating weight matrix.");

		WeightMatrix g = new WeightMatrix(e, centeredImages);
		// Is it really worthwhile to show those?
		// status.setText("G dimensions: " + g.getNbRow() + "x" + g.getNbColumn());
		// status.setText("E dimensions: " + e.getNbRow() + "x" + e.getNbColumn());
		status.setText("Database compiled successfully");
		System.out.println("Database compiled successfully");

		return new EigenFacesDB(averageFace, e, g, images[0].getWidth(), images[0].getHeight());
	}

	public static void saveImages(EigenFacesDB db, String path) {
		// Create eigenfaces images folder
		String directory = new File(path).getAbsolutePath() + "/";

		if (!(new File(directory).isDirectory())) {
			new File(directory).mkdir();
		}

		// Save the average face
		((ImageVector) (db.averageFace)).centerReduce().saveToFile(directory + "averageFace.png");

		// Print eigenfaces
		for (int i = 0; i < db.e.getNbColumn(); i++) {
			ImageVector eig = new ImageVector(db.e.getColumn(i).getElements(),
					db.height, db.width, "EIGEN_" + i + ".png");
			eig.centerReduce().saveToFile(directory + eig.getFileName());

		}
	}
}
