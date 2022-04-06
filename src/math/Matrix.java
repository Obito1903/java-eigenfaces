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

	public static Vector[] matrixToVector(Matrix matrix) {
		Vector[] vector = new Vector[matrix.getNbRow()];
		for (int row = 0; row < matrix.getNbRow(); row++) {
			vector[row] = new Vector(matrix.getRow(row));
		}
		return vector;
	}

	public Matrix(Matrix matrix) {
		this(matrixToVector(matrix));
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

	protected Vector[] getElements() {
		return matrixToVector(this);
	}

	protected double[][] getElementsAsArray() {
		return elements;
	}

	protected void setXY(int x, int y, double value) {
		this.elements[x][y] = value;
	}

	protected double getXY(int x, int y) {
		return this.elements[x][y];
	}

	public Matrix multiply(Matrix m) {
		return null;

	}

	public Vector multiply(Vector v) {
		return null;

	}

	public Matrix subtract(Matrix m) {

		Matrix matrix = new Matrix(this);

		for (int column = 0; column < this.nbColumn; column++) {
			for (int row = 0; row < this.nbRow; row++) {
				matrix.setXY(row, column, this.elements[row][column] - matrix.getXY(row, column));
			}
		}
		return matrix;

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
