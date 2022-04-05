package math;

public class Matrix {
	/**
	 * The elements will be accessed with row first then column
	 * elements[row][column]
	 */
	private double[][] elements;
	private int nbRow;
	private int nbColumn;

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

	public Matrix multiply(Matrix m) {
		return null;

	}

	public Vector multiply(Vector v) {
		return null;

	}

	public Matrix subtract(Matrix m) {
		return null;

	}

	public Matrix transpose() {
		return null;
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
}
