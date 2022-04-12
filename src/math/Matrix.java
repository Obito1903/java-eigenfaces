package math;

import java.util.Random;
import java.io.Serializable;

public class Matrix implements Serializable {
	/**
	 * The elements will be accessed with row first then column
	 * elements[row][column]
	 */
	private double[][] elements;

	/**
	 * The number of rows
	 */
	private int nbRow;

	/**
	 * The number of columns
	 */
	private int nbColumn;

	/**
	 * Construct the matrix with the given Array of vector
	 *
	 * @param vectors
	 */
	public Matrix(Vector[] vectors) {
		// TODO lenght checks
		this.nbColumn = vectors.length;
		if (this.nbColumn != 0) {
			this.nbRow = vectors[0].getLength();
		} else {
			this.nbRow = 0;
		}

		// Each column of the matrix will be a ImageVector
		this.elements = new double[this.nbRow][this.nbColumn];

		// For each column of the matrix we fill each of the column cell with the
		// corresponding cell in the corresponding vector
		for (int column = 0; column < this.nbColumn; column++) {
			double[] vector = vectors[column].getElements();
			for (int row = 0; row < vector.length; row++) {
				this.elements[row][column] = vector[row];
			}
		}
	}

	/**
	 * Initiate empty matrix from dimensions taken through parameters
	 *
	 * @param nbRow
	 * @param nbColumn
	 */
	public Matrix(int nbRow, int nbColumn) {
		this.nbRow = nbRow;
		this.nbColumn = nbColumn;
		this.elements = new double[nbRow][nbColumn];
	}

	/**
	 * Constructor by copy
	 *
	 * @param matrix the Matrix to copy
	 */
	public Matrix(Matrix matrix) {
		this(matrixToVectors(matrix));
	}

	/* TEMPORARY */
	// Rempli la matrice avec des valeurs entre 0 et 5;
	public void fill() {
		Random rand = new Random();
		for (int i = 0; i < nbRow; i++) {
			for (int j = 0; j < nbColumn; j++) {
				this.setXY(i, j, rand.nextInt(5));
			}
		}
	}

	/**
	 * Convert the matrix to a Array of vector
	 *
	 * @param matrix The matrix to convert
	 * @return The array of vectors
	 */
	public static Vector[] matrixToVectors(Matrix matrix) {
		Vector[] vector = new Vector[matrix.getNbRow()];
		for (int row = 0; row < matrix.getNbRow(); row++) {
			vector[row] = new Vector(matrix.getRow(row));
		}
		return vector;
	}

	public Vector getColumn(int index) {
		double[] vector = new double[this.nbRow];
		for (int j = 0; j < this.nbRow; j++) {
			vector[j] = this.elements[j][index];
		}
		return new Vector(vector);
	}

	public Vector getRow(int index) {
		double[] vector = new double[this.nbColumn];
		for (int j = 0; j < this.nbColumn; j++) {
			vector[j] = this.elements[index][j];
		}
		return new Vector(vector);
	}

	public Vector[] getElements() {
		return matrixToVectors(this);
	}

	// Never used
	// private double[][] getElementsAsArray() {
	// return elements;
	// }

	/**
	 * Set the element at the given coodinate in the matrix
	 *
	 * @param column
	 * @param row
	 * @param value
	 */
	private void setXY(int column, int row, double value) {
		this.elements[column][row] = value;
	}

	/**
	 * Get the element at the given coodinate in the matrix
	 *
	 * @param column
	 * @param row
	 * @return
	 */
	public double getXY(int column, int row) {
		return this.elements[column][row];
	}

	/**
	 * Multiply the matrix by another matrix
	 *
	 * @param m The matrix to pultiply with
	 * @return A new matrix wich is the product of the current one by m
	 * @throws DimensionMismatchException When the two matrix have incompatible
	 *                                    dimensions
	 */
	public Matrix multiply(Matrix m) throws DimensionMismatchException {
		/* Initialising a matrix */
		Matrix product = new Matrix(this.nbRow, m.getNbColumn());
		double sum;
		/* Fill in the vectorial product of the two matrixes */
		for (int row = 0; row < this.nbRow; row++) {
			for (int column = 0; column < m.getNbColumn(); column++) {
				sum = 0.0;
				for (int index = 0; index < this.nbColumn; index++) {
					sum += this.getXY(row, index) * m.getXY(index, column);
				}
				product.setXY(row, column, sum);
			}
		}
		return product;
	}

	/**
	 * Multiply the matrix be a vector
	 *
	 * @param v The vecotr to multiply with
	 * @return The result of the product as a Vector
	 */
	public Vector multiply(Vector v) {
		if (this.nbColumn != v.getLength())
			throw new DimensionMismatchException("Incompatible dimensions between matrix and vector.");
		double[] elements = v.getElements();
		double[] product = new double[this.nbRow];
		for (int i = 0; i < this.nbRow; i++) {
			product[i] = 0;
			for (int j = 0; j < this.nbColumn; j++)
				product[i] += this.elements[i][j] * elements[j];
		}
		return (new Vector(product));
	}

	/**
	 * Make the difference between the current matrix and m
	 *
	 * @param m the matrix to substract to the current one
	 * @return A new matrix which is the difference between the two
	 */
	public Matrix subtract(Matrix m) {

		Matrix matrix = new Matrix(this);

		for (int column = 0; column < this.nbColumn; column++) {
			for (int row = 0; row < this.nbRow; row++) {
				matrix.setXY(row, column, this.elements[row][column] - matrix.getXY(row, column));
			}
		}
		return matrix;

	}

	/**
	 * Transpose the matrix
	 * 
	 * @return the Transposed matrix
	 */
	public Matrix transpose() {
		Vector[] res = new Vector[this.nbRow];
		for (int i = 0; i < res.length; i++)
			res[i] = this.getRow(i);
		return new Matrix(res);
	}

	public int getLength() {
		return this.nbColumn * this.nbRow;
	}

	public int getNbRow() {
		return this.nbRow;
	}

	public int getNbColumn() {
		return this.nbColumn;
	}

	/* Affichage de la matrice */
	@Override
	public String toString() {
		String retour = "";
		for (int i = 0; i < nbRow; i++) {
			for (int j = 0; j < nbColumn; j++) {
				retour += String.format("%.3f | ", elements[i][j]);
			}
			retour += "\n";
		}
		return retour;
	}

	// Verification de la méthode
	public static void main(String[] args) {
		Matrix matA = new Matrix(2, 4);
		Matrix matB = new Matrix(4, 3);
		Matrix matC = new Matrix(3, 3);

		/* donner valeur aux matrices */
		matA.fill();
		System.out.println(matA);
		matB.fill();
		System.out.println(matB);

		/*
		 * try {
		 *
		 * //if (this.nbColumn == m.nbRow) {
		 * System.out.println(matA.multiply(matB));
		 * System.out.println(matA.multiply(matC));
		 * } catch (DimensionMismatchException e) {
		 * System.out.
		 * println("An operation (multiplication) was attempted between 2 matrices but it was not possible due to dimensional incompatibility (the length of the first matrix was different from the height of the second one)."
		 * );
		 * }
		 */
		double[] v1 = { 1, 2 };
		double[] v2 = { 3, 4 };
		Vector[] vmat = { new Vector(v1), new Vector(v2) };
		Matrix mat = new Matrix(vmat);
		System.out.println(mat);
		System.out.println(mat.transpose());
	}

}
