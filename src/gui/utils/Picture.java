package gui.utils;

import java.io.File;

import eigenfaces.image.ImageVector;
import javafx.scene.image.Image;

public class Picture {

	private final float ICON_SIZE = 80.0f;

	private String filename;
	private Image img;
	private int widthInit;
	private int heightInit;
	private String name;

	public Picture(ImageVector imageVectors) {
		this.filename = imageVectors.getFileName();
		this.widthInit = imageVectors.getWidth();
		this.heightInit = imageVectors.getHeight();
		this.name = imageVectors.getName();
		this.img = imageVectors.getFXImage();
	}

	public Picture(String filename) {
		this.filename = filename;
		this.img = new Image(filename);
		this.widthInit = this.getWidth();
		this.heightInit = this.getHeight();
		this.name = (new File(filename)).getName();
		this.name = this.name.substring(0, this.name.length() - 4);
	}

	public Image getImage() {
		return this.img;
	}

	public String getFilePath() {
		return this.filename;
	}

	public int getWidth() {
		return (int) this.img.getWidth();
	}

	public int getHeight() {
		return (int) this.img.getHeight();
	}

	public String getName() {
		return this.name;
	}

	public Image getIcon() {
		float ratio = Math.max(this.widthInit, this.heightInit);
		int width = (int) (ICON_SIZE * (float) this.widthInit / ratio);
		int height = (int) (ICON_SIZE * (float) this.heightInit / ratio);
		return new Image(this.filename, width, height, false, false);
	}
}
