package math;

public class Vector {
	private double[] elements;

	public Vector(double[] elements) {
	}

	public int getLength() {
		return(this.elements.length);
	}

	public double[] getElements() {
		return(this.elements);
	}

	public static boolean equalLength(Vector v1, Vector v2) {
	}

	public static double distance(Vector v1, Vector v2) {
	}

	public void multiply(double x) {
		for (i = 0; i < elements.length; i++)
			elements[i] *= x;
	}

	public void add(Vector v) {
		for (i = 0; i < elements.length; i++)
			elements[i] += v.getElements()[i];
	}

	public void subtract(Vector v) {
		for (i = 0; i < elements.length; i++)
			elements[i] -= v.getElements()[i];
	}

	public double norm() {
	}
}
