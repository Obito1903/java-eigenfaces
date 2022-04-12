import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import math.*;

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
     * Constructor
     *
     * @param averageFace Average face of the database
     * @param e           Eigenfaces
     * @param g           Weight matrix
     */
    public EigenFacesDB(Vector averageFace, Matrix e, WeightMatrix g) {
        this.averageFace = averageFace;
        this.e = e;
        this.g = g;
    }

    /**
     * Constructor from file
     *
     * @param file File to load from
     */
    public EigenFacesDB(String fileName) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            this.averageFace = (Vector) ois.readObject();
            this.e = (Matrix) ois.readObject();
            this.g = (WeightMatrix) ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            oos.close();
            System.out.println("Saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
