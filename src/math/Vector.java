package math;

public class Vector {
	protected double[] elements;

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
		if (v1.getLength() == v2.getLength()) {
			return true;
		}
		return false;
	}

	public static double distance(Vector v1, Vector v2) throws DimensionMismatchException{
		double distance = 0;
		try {
			if (equalLength(v1, v2)) {
				for (int i = 0; i < v1.getLength(); i++) {
					distance +=  Math.pow((v1.getElements()[i] - v2.getElements()[i]), 2);
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

	public double norm(Vector v) {
		double res = 0;
		for (int i = 0; i < v.getLength(); i++) {
			res += Math.pow(v.getElements()[i], 2);
		}
		res = Math.sqrt(res);
		return res;
	}
}
