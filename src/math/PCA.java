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
		double[] elements = new double[w*h];
		for (int i = 0; i < w*h; i++) {
			double sum = 0;
			for (int j = 0; j < faces.length; j++)
				sum += faces[j].getElements()[i];
			elements[i] = (double)sum / (double)(faces.length);
		}
		return(new ImageVector(elements, h, w, "AVERAGE_FACE.jpg"));
	}

	/**
	 * Calculates the eigenmatrix of the input matrix
	 *
	 * @param aMatrix the matrix (Vector array) we want to get the eigenvectors from
	 * @return The eigenvectors matrix, along with the eigenvalues at the last row
	 */

	public static Matrix eMatrix(Vector[] a) {
		//Convert matrix A to format accepted by the SVD library
		double[][] m = new double[a.length][];
		for (int i = 0; i < m.length; i++)
			m[i] = a[i].getElements();

		//Let the SVD do its job
		SingularValueDecomposition svd = new SingularValueDecomposition(MatrixUtils.createRealMatrix(m));

		//Convert it back to our format
		double[][] eVec = svd.getV().getData();
		double[] eVal = svd.getSingularValues();
		Vector[] res = new Vector[eVal.length];
		for (int i = 0; i < res.length; i++) {
			double[] vector = new double[eVec.length+1];
			for (int j = 0; j < vector.length-1; j++)
				vector[j] = eVec[j][i];
			vector[vector.length-1] = eVal[i];
			res[i] = new Vector(vector);
		}

		return new Matrix(res);
	}

	public static Matrix gMatrix(Matrix eMatrix, Matrix aMatrix) {
		return null;
	}

	public static void main(String[] args) {
		
		double[][] stuff = {
			{1, 0, 1, 1},
			{0, 1, 0, 1},
			{0, 0, 1, 1},
			{0, 1, 0, 1},
			{1, 0, 1, 0},
			{0, 1, 0, 1},
			{0, 0, 1, 1},
			{0, 1, 0, 1},
			{1, 0, 1, 1}
		};
		double[][] stuff2 = {
			{1, 0, 0, 0, 1, 0, 0, 0, 1},
			{0, 1, 0, 1, 0, 1, 0, 1, 0},
			{1, 0, 1, 0, 1, 0, 1, 0, 1},
			{1, 1, 1, 1, 0, 1, 1, 1, 1}
		};
		double[][] stuff3 = {
			{3, 1, 2, 1, 2, 1, 2, 1, 3},
			{1, 2, 1, 2, 0, 2, 1, 2, 1},
			{2, 1, 2, 1, 1, 1, 2, 1, 2},
			{1, 2, 1, 2, 0, 2, 1, 2, 1},
			{2, 0, 1, 0, 2, 0, 1, 0, 2},
			{1, 2, 1, 2, 0, 2, 1, 2, 1},
			{2, 1, 2, 1, 1, 1, 2, 1, 2},
			{1, 2, 1, 2, 0, 2, 1, 2, 1},
			{3, 1, 2, 1, 2, 1, 2, 1, 3}
		};
		double[][] stuff4 = {
			{3, 0, 3, 2},
			{0, 4, 0, 4},
			{3, 0, 5, 4},
			{2, 4, 4, 8}
		};
		
		SingularValueDecomposition svd = new SingularValueDecomposition(MatrixUtils.createRealMatrix(stuff2));
		System.out.println(svd.getVT());
	}
}
