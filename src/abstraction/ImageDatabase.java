package abstraction;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class ImageDatabase extends Observable {

	public static final Integer CHANGING_CURRENT_IMG = new Integer(0); // PAC
	public static final Integer CHANGING_SIZE = new Integer(1); // PAC
	public static final Integer NEW_IMG = new Integer(2); // PAC

	private ArrayList<Picture> imageSet;
	private int currentIndex; // index de l'image courante

	/* Constructor */
	public ImageDatabase(String directory) {
		this.imageSet = new ArrayList<Picture>();
		File dirImages = new File(directory);
		for (String string : dirImages.list()) {
			this.addPicture("file:" + directory + "/" + string);
		}
		this.currentIndex = 0;
	}

	public int getSize() {
		return this.imageSet.size();
	}

	public void addPicture(String fullpathname) {
		this.imageSet.add(new Picture(fullpathname));
		this.setChanged(); // PAC
		this.notifyObservers(NEW_IMG); // PAC
	}

	public Picture getPicture(int index) {
		if ((index < this.imageSet.size()) && (index >= 0)) {
			return this.imageSet.get(index);
		} else {
			return null;
		}
	}

	public int getCurrentIndex() {
		return this.currentIndex;
	}

	public void setCurrentIndex(int current) {
		if ((current < this.imageSet.size()) && (current >= 0)) {
			this.currentIndex = current;
			this.setChanged(); // PAC
			this.notifyObservers(CHANGING_CURRENT_IMG); // PAC
		}
	}

	public Picture getCurrentPicture() {
		return this.imageSet.get(currentIndex);
	}
}
