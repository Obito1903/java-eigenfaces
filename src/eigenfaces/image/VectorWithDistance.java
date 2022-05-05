package eigenfaces.image;

import eigenfaces.math.Vector;

public class VectorWithDistance extends Vector implements Comparable<VectorWithDistance> {
	private double distance;
	private String name;

	public VectorWithDistance(Vector v, double distance, String name) {
		super(v);
		this.distance = distance;
		this.name = name;
	}

	public double getDistance() { return distance; }
	public String getName() { return name; }

	@Override
	public int compareTo(VectorWithDistance v) {
		if (this.distance > v.distance)
			return 1;
		else if (this.distance == v.distance)
			return 0;
		else
			return -1;
	}

}
