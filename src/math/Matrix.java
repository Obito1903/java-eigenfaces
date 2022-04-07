package math;

import java.util.Random;

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

	//////////////////////////////////////////////////////////////////////////////
	
	/*CACA DE BAGUETTETRASH*/
	
	/*tempo peut etre - initialisé matrice null*/
	public Matrix(int nbRow, int nbColumn) {
		this.nbRow = nbRow;
		this.nbColumn = nbColumn;
		this.elements = new double[nbRow][nbColumn];
	}
	
	//Rempli la matrice avec des valeurs entre 0 et 5;
	public void fill() {
		Random rand = new Random();
		for(int i=0; i<nbRow; i++) {
			for(int j=0; j<nbColumn; j++) {
				this.setXY(i,j,rand.nextInt(5));
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////

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

	/////////////////////////////////////////////////////////////////////////////////////////////
	
	/*PIPI DE BAGUETTETRASH*/

	public Matrix multiply(Matrix m) {
		if (this.nbColumn == m.nbRow) {
			
			/*Initialising a matrix*/
			Matrix product = new Matrix(this.nbRow,m.getNbColumn());
			double somme;
			/*Fill in the vectorial product of the two matrixes*/
			for (int row = 0; row < this.nbRow; row++) {
				for (int column = 0; column < m.getNbColumn(); column++) {
					somme = 0.0;
					for (int index = 0; index<this.nbColumn; index++) {
						somme += this.getXY(row,index)*m.getXY(index,column);
					}
					product.setXY(row,column,somme);	
				}
			}
			return product;
		} else {
			return null;
		}/* else {
			throw new MatrixesDimensionIncompatibilityException("An operation (multiplication) was attempted between 2 matrices but it was not possible due to dimensional incompatibility (the length of the first matrix was different from the height of the second one).");
			return null;
		}*/
	}


	///////////////////////////////////////////////////////////////////////////////////////////////


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
	

	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/*ZIZI DE BAGUETTETRASH*/
	
	//Affichage de matrice
	@Override
	public String toString() {
		String retour = "";
		for(int i=0; i<nbRow; i++) {
			for(int j=0; j<nbColumn; j++) {
				retour += elements[i][j]+ "  ";
			}
			retour += "\n";
		}
		return retour;
	}

	/*verification*/
	public static void main(String[] args) {
		Matrix caca = new Matrix(2,4);
		Matrix pipi = new Matrix(4,3);
		//Matrix zizi = new Matrix(3,3);
		
		/*donner valeur aux matrices*/
		caca.fill();
		System.out.println(caca);
		pipi.fill();
		System.out.println(pipi);


		System.out.println(caca.multiply(pipi));
		//System.out.println(caca.multiply(zizi));
	}

}
