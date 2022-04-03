package math;

public class Vector {
	private double[] elements;

	public Vector(Vector vector) {
		this.elements = vector.getElements().clone();
	}

	public Vector(double[] elements) {
		this.elements = elements.clone();
	}

	public int getLength() {
		return (this.elements.length);
	}

	public double[] getElements() {
		return (this.elements);
	}

	public static boolean equalLength(Vector v1, Vector v2) {
		return true;
	}

	public static double distance(Vector v1, Vector v2) {
		return 0.0;
	}

	public void multiply(double x) {
		for (int i = 0; i < elements.length; i++)
			elements[i] *= x;
	}

	public void add(Vector v) {
		for (int i = 0; i < elements.length; i++)
			elements[i] += v.getElements()[i];
	}

	public void subtract(Vector v) {
		for (int i = 0; i < elements.length; i++)
			elements[i] -= v.getElements()[i];
	}

	public double norm() {
		return 0.0;
	}
}
