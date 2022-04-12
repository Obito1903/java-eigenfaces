package math;

import image.*;

import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

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
	 * @param aMatrix The matrix (Vector array) we want to get the eigenvectors from
	 * @param k       How many eigenvectors we want to keep
	 * @return The eigenvectors matrix, along with the eigenvalues at the last row
	 */

	public static Matrix eMatrix(Vector[] a, int k) {
		// Convert matrix A to format accepted by the SVD library
		double[][] m = new double[a.length][];
		for (int i = 0; i < m.length; i++)
			m[i] = a[i].getElements();

		// Let the SVD do its job
		SingularValueDecomposition svd = new SingularValueDecomposition(MatrixUtils.createRealMatrix(m));

		// Convert it back to our format
		double[][] eVec = svd.getV().getData();
		double[] eVal = svd.getSingularValues();

		// TODO take the k first eigenvectors and adapt the eigenvalues

		k = Math.min(k, eVal.length);
		if (k <= 0)
			throw new KValueOutOfBoundsException("k must be positive or > 0");

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

	public static void main(String[] args) {

		double[][] stuff = {
				{ 1, 0, 1, 1 },
				{ 0, 1, 0, 1 },
				{ 0, 0, 1, 1 },
				{ 0, 1, 0, 1 },
				{ 1, 0, 1, 0 },
				{ 0, 1, 0, 1 },
				{ 0, 0, 1, 1 },
				{ 0, 1, 0, 1 },
				{ 1, 0, 1, 1 }
		};
		double[][] stuff2 = {
				{ 1, 0, 0, 0, 1, 0, 0, 0, 1 },
				{ 0, 1, 0, 1, 0, 1, 0, 1, 0 },
				{ 1, 0, 1, 0, 1, 0, 1, 0, 1 },
				{ 1, 1, 1, 1, 0, 1, 1, 1, 1 }
		};
		double[][] stuff3 = {
				{ 3, 1, 2, 1, 2, 1, 2, 1, 3 },
				{ 1, 2, 1, 2, 0, 2, 1, 2, 1 },
				{ 2, 1, 2, 1, 1, 1, 2, 1, 2 },
				{ 1, 2, 1, 2, 0, 2, 1, 2, 1 },
				{ 2, 0, 1, 0, 2, 0, 1, 0, 2 },
				{ 1, 2, 1, 2, 0, 2, 1, 2, 1 },
				{ 2, 1, 2, 1, 1, 1, 2, 1, 2 },
				{ 1, 2, 1, 2, 0, 2, 1, 2, 1 },
				{ 3, 1, 2, 1, 2, 1, 2, 1, 3 }
		};
		double[][] stuff4 = {
				{ 3, 0, 3, 2 },
				{ 0, 4, 0, 4 },
				{ 3, 0, 5, 4 },
				{ 2, 4, 4, 8 }
		};

		SingularValueDecomposition svd = new SingularValueDecomposition(MatrixUtils.createRealMatrix(stuff2));
		System.out.println(svd.getVT());
	}
}
