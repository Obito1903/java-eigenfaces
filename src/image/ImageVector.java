package image;

import math.*;
import java.awt.image.BufferedImage;
import java.awt.color.ColorSpace;
import java.awt.Color;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class ImageVector extends Vector implements Serializable {
	private final String fileName;
	private final String name;
	private final int height;
	private final int width;

	/**
	 * Convert a image file into a Image vector where column are lined up one after
	 * the other
	 *
	 * @param imagePath Path to the image to convert
	 * @return an image vector
	 */
	private static ImageVector imageToVector(String imagePath) {
		try {
			// Read the image and place it in memory
			BufferedImage imageBase = ImageIO.read(new File(imagePath));

			// Convert colorspace to grayscale
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			BufferedImage image = op.filter(imageBase, null);

			// Extract pixels data from the buffered image
			Raster raster = image.getRaster();
			// System.out.println(raster.getNumBands());

			// Create the vector which will contain the pixels data
			double[] pixelVector = new double[image.getWidth() * image.getHeight()];

			// Fill the vector
			for (int x = 0; x < raster.getWidth(); x++) {
				for (int y = 0; y < raster.getHeight(); y++) {

					//
					pixelVector[y + (x * raster.getHeight())] = raster.getSampleDouble(x, y, 0) / 255;
				}
			}

			return new ImageVector(pixelVector, raster.getHeight(), raster.getWidth(), imagePath);

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Internal constructor to create a ImageVector from raw data
	 *
	 * @param vector    The vector of pixels
	 * @param height    Height of the original image
	 * @param width     Width of the original image
	 * @param imagePath Path of the original image
	 */
	public ImageVector(double[] vector, int height, int width, String imagePath) {
		super(vector);
		Path path = Paths.get(imagePath);
		this.fileName = path.getFileName().toString();
		this.name = path.getFileName().toString();
		this.height = height;
		this.width = width;
	}

	/**
	 * Contructor by full copy
	 *
	 * @param imgV
	 */
	public ImageVector(ImageVector imgV) {
		super(imgV);
		this.fileName = imgV.getFileName();
		this.name = imgV.getName();
		this.height = imgV.getHeight();
		this.width = imgV.getWidth();
	}

	/**
	 * Constructor for ImageVactor using the path of the image to use
	 *
	 * @param imagePath Path to the og image
	 */
	public ImageVector(String imagePath) {
		this(imageToVector(imagePath));
	}

	public String getFileName() {
		return (fileName);
	}

	public String getName() {
		return (name);
	}

	public int getHeight() {
		return (height);
	}

	public int getWidth() {
		return (width);
	}

	/**
	 * Returns the image vector as a string printable in the terminal
	 */
	@Override
	public String toString() {
		String res = this.getName() + "'s face:\n";
		double[] elements = this.getElements();
		for (int i = 0; i < this.getHeight(); i++) {
			for (int j = 0; j < this.getWidth(); j++) {
				int index = j * this.getHeight() + i;
				res += "\033[38;2;" + (int) (elements[index] * 255) + ";" + (int) (elements[index] * 255) + ";"
						+ (int) (elements[index] * 255) + "m██";
			}
			res += ("\n");
		}
		return (res);
	}

	/**
	 * Centers and reduces the vector
	 * Transforms the vector so that its values' range goes from [min, max] to [0,
	 * 1]
	 *
	 * @return A new imageVector that is centered and reduced
	 */

	public ImageVector centerReduce() {
		double min = this.elements[0];
		double max = this.elements[0];
		double[] values = new double[width * height];

		// Find the min and the max values of the vector
		for (int i = 1; i < values.length; i++) {
			if (this.elements[i] < min)
				min = this.elements[i];
			else if (this.elements[i] > max)
				max = this.elements[i];
		}

		// For every value v of the original vector, apply (v-min)/(max-min) and store
		// it in the new vector
		for (int i = 0; i < values.length; i++)
			values[i] = (this.elements[i] - min) / (max - min);

		return (new ImageVector(values, height, width, fileName));
	}

	public ImageVector center(Vector meanVector) {
		double[] values = new double[this.width * this.height];

		// For every value v of the original vector, apply (v-mean) and store it in the
		// new vector
		for (int i = 0; i < values.length; i++)
			values[i] = this.elements[i] - meanVector.getElement(i);

		return (new ImageVector(values, this.height, this.width, this.fileName));
	}

	public void saveToFile(String path) {
		try {
			BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					float gColor = (float) this.elements[j + (i * height)];
					Color pixel = new Color(gColor, gColor, gColor);
					image.setRGB(i, j, pixel.getRGB());
				}
			}
			ImageIO.write(image, "png", new File(path));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		System.out.println("testImgVector");
		ImageVector imgV = imageToVector("img/reference/Samuel_Rodrigues_2.jpg");
		imgV.saveToFile("img/Samuel_Rodrigues_2_reduced.png");
		System.out.println(imgV);
	}
}
