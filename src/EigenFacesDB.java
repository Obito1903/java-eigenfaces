import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import math.*;

/**
 * A class to store the Eigen Face Database and restore it from disk
 */
public class EigenFacesDB implements Serializable {
    /**
     * Avergae face of the database
     */
    protected Vector averageFace;

    /**
     * Eigenfaces
     */
    protected Matrix e;

    /**
     * Wheight matrix
     */
    protected WeightMatrix g;

	/**
	 * Image width
	 */
	protected Integer width;

	/**
	 * Image height
	 */
	protected Integer height;

    /**
     * Constructor
     *
     * @param averageFace Average face of the database
     * @param e           Eigenfaces
     * @param g           Weight matrix
	 * @param w           Image width
	 * @param h           Image height
     */
    public EigenFacesDB(Vector averageFace, Matrix e, WeightMatrix g, int w, int h) {
        this.averageFace = averageFace;
        this.e = e;
        this.g = g;
		this.width = w;
		this.height = h;
    }

    /**
     * Constructor from file
     *
     * @param file File to load from
     */
    public EigenFacesDB(String fileName) throws IOException, ClassNotFoundException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            this.averageFace = (Vector) ois.readObject();
            this.e = (Matrix) ois.readObject();
            this.g = (WeightMatrix) ois.readObject();
			this.width = (Integer) ois.readObject();
			this.height = (Integer) ois.readObject();
            ois.close();
        } catch (IOException e) {
            throw new IOException("Database");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }

    /**
     * Saves the database to a file
     *
     * @param fileName File to save to
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(this.averageFace);
            oos.writeObject(this.e);
            oos.writeObject(this.g);
			oos.writeObject(this.width);
			oos.writeObject(this.height);
            oos.close();
            System.out.println("Saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
