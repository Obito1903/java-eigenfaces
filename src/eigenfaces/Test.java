package eigenfaces;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import eigenfaces.image.ImageVector;
import eigenfaces.image.VectorWithDistance;
import eigenfaces.math.*;

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
		Vector testProjection = db.getE().transpose().multiply(test);

		// Compute the distance to each image
		double[] distances = new double[db.g.getNbImages()];
		for (int i = 0; i < db.getG().getNbImages(); i++) {
			distances[i] = Vector.distance(testProjection, db.getG().getRow(i));

		}

		return distances;

	}

	/**
	 * Find the closest image to the test image
	 *
	 * @param db   The database to use
	 * @param test The test image
	 * @return The list of all matched images sorted in decreasing distance
	 */
	public static List<VectorWithDistance> findBestMatches(EigenFacesDB db, ImageVector test, double threshold) {
		// Subtract the average Face from the test image
		test.subtract(db.averageFace);

		double[] distances = calculateDistances(db, test);

		// Find the best match
		List<VectorWithDistance> matches = new ArrayList();
		for (int i = 0; i < db.getG().getNbImages(); i++) {
			if (distances[i] <= threshold) {
				matches.add(new VectorWithDistance(db.getG().getRow(i), distances[i], db.getG().getNameOf(i)));
			}
		}

		Collections.sort(matches, Collections.reverseOrder());

		return matches;
	}
}
