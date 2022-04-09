package image;

import math.*;
import java.awt.image.BufferedImage;
import java.awt.color.ColorSpace;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageVector extends Vector {
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
			//System.out.println(raster.getNumBands());

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
		this.fileName = imagePath;
		this.name = imagePath;
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
				int index = j*this.getHeight() + i;
				res += "\033[38;2;"+(int)(elements[index]*25500)+";"+(int)(elements[index]*25500)+";"+(int)(elements[index]*25500)+"m██";
			}
			res += ("\n");
		}
		return(res);
	}

	public static void main(String[] args) {
		System.out.println("testImgVector");
		ImageVector imgV = imageToVector("img/reference/Samuel_Rodrigues_2.jpg");
		System.out.println(imgV);
	}
}
