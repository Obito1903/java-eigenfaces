package eigenfaces;

import eigenfaces.image.ImageVector;
import eigenfaces.math.*;

/**
 * A class to store The weight of each face and it's name
 */
public class WeightMatrix extends Matrix {
    private String[] names;

    /**
     * Construct a weighted matrix with it's associated image names
     *
     * @param eMatrix Eigen vectors
     * @param images  Images
     */
    public WeightMatrix(Matrix eMatrix, ImageVector[] images) {
        super(PCA.gMatrix(eMatrix, new Matrix(images)));
        names = new String[this.getNbRow()];

        for (int i = 0; i < this.getNbRow(); i++) {
            names[i] = images[i].getName();
        }
    }

    public String getNameOf(int index) {
        return names[index];
    }

    public int getNbImages() {
        return names.length;
    }

    /* Affichage de la matrice */
    @Override
    public String toString() {
        String retour = "";
        for (int i = 0; i < this.getNbRow(); i++) {
            retour += names[i] + ": ";
            for (int j = 0; j < this.getNbColumn(); j++) {
                retour += String.format("%.2f | ", this.getXY(i, j));
            }
            retour += "\n";
        }
        return retour;
    }

}
