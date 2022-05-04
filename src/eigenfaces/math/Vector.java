package eigenfaces.math;

import java.io.Serializable;

public class Vector implements Serializable {

	/**
	 * Values inside the vector
	 */
	protected double[] elements;

	/**
	 * Construct a new vector from an already existing one (full copy by
	 * constructor)
	 *
	 * @param vector
	 */
	public Vector(Vector vector) {
		this.elements = vector.getElements().clone();
	}

	/**
	 * Construct the vector from an array of elements
	 *
	 * @param elements
	 */
	public Vector(double[] elements) {
		this.elements = elements.clone();
	}

	public int getLength() {
		return (this.elements.length);
	}

	public double getElement(int index) {
		return (this.elements[index]);
	}

	public double[] getElements() {
		return (this.elements);
	}

	/**
	 * Check of two vector are of equel length
	 *
	 * @param v1
	 * @param v2
	 * @return True if it's the case, false otherwise
	 */
	public static boolean equalLength(Vector v1, Vector v2) {
		if (v1.getLength() == v2.getLength()) {
			return true;
		}
		return false;
	}

	/**
	 * Calculate the root mean square (distance quadratique) between two vector
	 *
	 * @param v1
	 * @param v2
	 * @return The root mean square of the two vector
	 * @throws DimensionMismatchException When the vectors are not the same size
	 */
	public static double distance(Vector v1, Vector v2) throws DimensionMismatchException {
		double distance = 0;
		try {
			if (equalLength(v1, v2)) {
				for (int i = 0; i < v1.getLength(); i++) {
					distance += Math.pow((v1.getElements()[i] - v2.getElements()[i]), 2);
				}
				distance = Math.sqrt(distance);

			} else {
				throw new DimensionMismatchException("Vector dimensions not equal");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return distance;
	}

	/**
	 * Multiply the vector by a double
	 *
	 * @param x
	 */
	public void multiply(double x) {
		for (int i = 0; i < elements.length; i++)
			elements[i] *= x;
	}

	/**
	 * Add a double
	 *
	 * @param v
	 */
	public void add(Vector v) {
		for (int i = 0; i < elements.length; i++)
			elements[i] += v.getElements()[i];
	}

	/**
	 * Substract a double
	 *
	 * @param v
	 */
	public void subtract(Vector v) {
		for (int i = 0; i < elements.length; i++)
			elements[i] -= v.getElements()[i];
	}

	/**
	 * Calculate the norm of the vector
	 *
	 * @return the norm
	 */
	public double norm() {
		double res = 0;
		for (int i = 0; i < this.getLength(); i++) {
			res += Math.pow(this.getElements()[i], 2);
		}
		res = Math.sqrt(res);
		return res;
	}
}
