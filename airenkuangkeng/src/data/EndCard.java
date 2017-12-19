package data;

import java.awt.Image;
import java.awt.Toolkit;

public class EndCard extends Card {

	protected Image backImage = Toolkit.getDefaultToolkit().getImage("picture/end/BACK.jpg");
	protected boolean found;
	protected boolean isShow;

	public Image getImage() {
		return image;
	}

	public boolean isFound() {
		return found;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void showBack() {
		isShow = false;
	}

	public void showImage() {
		isShow = true;
	}

}
