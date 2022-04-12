import image.ImageVector;
import math.*;

public class Test {

	/**
	 * Calculate the distances between the test image and each images in the db
	 *
	 * @param db   The database to use
	 * @param test The test image
	 * @return An array of distances (doubles)
	 */
	public static double[] calculateDistances(EigenFacesDB db, ImageVector test) {
		// Compute the projection of the test image
		Vector testProjection = db.e.transpose().multiply(test);

		// Compute the distance to each image
		double[] distances = new double[db.g.getNbImages()];
		for (int i = 0; i < db.g.getNbImages(); i++) {
			distances[i] = Vector.distance(testProjection, db.g.getRow(i));
		}

		return distances;

	}

	/**
	 * Find the closest image to the test image
	 *
	 * @param db   The database to use
	 * @param test The test image
	 * @return The name of the closest image
	 */
	public static String findBestMatch(EigenFacesDB db, ImageVector test) {

		double[] distances = calculateDistances(db, test);

		// Find the best match
		double bestDistance = Double.MAX_VALUE;
		String bestMatch = "";
		for (int i = 0; i < db.g.getNbImages(); i++) {
			if (distances[i] < bestDistance) {
				bestDistance = distances[i];
				bestMatch = db.g.getNameOf(i);
			}
		}

		return bestMatch;
	}

	public static void main(String[] args) {
		EigenFacesDB db = new EigenFacesDB("test.egdb");
		// EigenFacesDB db = Compiler.compileDB("img/reference", 3);
		ImageVector test = new ImageVector("img/test/Vincent_3.jpg");
		double[] distances = calculateDistances(db, test);
		for (int i = 0; i < distances.length; i++) {
			System.out.println(db.g.getNameOf(i) + ": " + distances[i]);
		}
		System.out.println("Best match : " + findBestMatch(db, test));
	}
}
