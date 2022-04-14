package math;

import image.*;

import org.apache.commons.math3.linear.SingularValueDecomposition;

import org.apache.commons.math3.linear.MatrixUtils;

public class PCA {

	/**
	 * Returns the average vector from a matrix
	 *
	 * @param faces Array of ImageVectors to calculate the average
	 * @return The average vector as an ImageVector
	 */
	public static ImageVector averageFace(ImageVector[] faces) {
		int w = faces[0].getWidth();
		int h = faces[0].getHeight();
		double[] elements = new double[w * h];
		for (int i = 0; i < w * h; i++) {
			double sum = 0;
			for (int j = 0; j < faces.length; j++)
				sum += faces[j].getElements()[i];
			elements[i] = (double) sum / (double) (faces.length);
		}
		return (new ImageVector(elements, h, w, "AVERAGE_FACE.jpg"));
	}

	/**
	 * Calculates the eigenmatrix of the input matrix
	 *
	 * @param a	      The original images matrix
	 * @param k       How many eigenvectors we want to keep
	 * @param debug   Whether or not to display information lost
	 * @return The eigenvectors matrix
	 */
	public static Matrix eMatrix(Vector[] a, int k, boolean debug) {
		// Convert matrix A to format accepted by the SVD library
		double[][] m = new double[a.length][];
		for (int i = 0; i < m.length; i++)
			m[i] = a[i].getElements();

		// Let the SVD do its job
		SingularValueDecomposition svd = new SingularValueDecomposition(MatrixUtils.createRealMatrix(m));

		// Convert it back to our format
		double[][] eVec = svd.getV().getData();
		double[] eVal = svd.getSingularValues();

		// Check the validity of k
		k = Math.min(k, eVal.length);

		if (k == 0) {
			k = eVal.length;
		}

		if (k < 0)
			throw new KValueOutOfBoundsException("k must be positive");

		Vector[] res = new Vector[k];

		for (int i = 0; i < k; i++) {
			double[] vector = new double[eVec.length];
			for (int j = 0; j < vector.length; j++)
				vector[j] = eVec[j][i];
			// vector[vector.length-1] = eVal[i];
			Vector vec = new Vector(vector);
			// Normalize the vector
			vec.multiply(1 / vec.norm());
			res[i] = vec;
		}

		//If debug flag is on, show amount of information lost
		if (debug && k != eVal.length) {
			double kEvSum = 0;
			for (int i = 0; i < k; i++)
				kEvSum += eVal[i];
			double evSum = kEvSum;
			for (int i = k; i < eVal.length; i++)
				evSum += eVal[i];
			System.out.println(k + " eigenvectors conserved, for a " + (100*(1-(kEvSum/evSum))) + "% information loss.");
		}

		return new Matrix(res);
	}

	/**
	 * Creates the weight matrix of a matrix and its associated eigenvector matrix
	 *
	 * @param eMatrix The eigenvector matrix
	 * @param aMatrix The original matrix
	 * @return The weight matrix
	 */
	public static Matrix gMatrix(Matrix eMatrix, Matrix aMatrix) {
		return eMatrix.transpose().multiply(aMatrix);
	}

}
