package math;

import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;
import org.ejml.simple.SimpleEVD;

public class PCA {


	public static Matrix eMatrix(Matrix aMatrix) {
		//Assuming number of pixels > number of images
		//multiply the transpose by the normal matrix
		//in order to have a smaller dimension result
		Matrix covMatrix = aMatrix.transpose().multiply(aMatrix);

		//Convert covariance matrix to format accepted by the SVD library
		double[][] cm = new double[covMatrix.getNbRow()][];
		for (int i = 0; i < cm.length; i++)
			cm[i] = covMatrix.getRow(i).getElements();

		//Let the SVD do its job
		SingularValueDecomposition svd = new SingularValueDecomposition(MatrixUtils.createRealMatrix(cm));


		return null;
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
		//EigenDecomposition svd = new EigenDecomposition(MatrixUtils.createRealMatrix(stuff4));
		SimpleMatrix sm = new SimpleMatrix(stuff4);
		SimpleEVD svd = sm.eig();
		System.out.println(svd.getEigenvalues());
		//for (int i = 0; i < svd.getNumberOfEigenvalues(); i++)
			//System.out.println(svd.getEigenVector(i));
		System.out.println(svd.getEVD());
	}
}
