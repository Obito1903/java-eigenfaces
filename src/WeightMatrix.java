import math.*;
import image.ImageVector;

public class WeightMatrix extends Matrix {
    private String[] names;

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
