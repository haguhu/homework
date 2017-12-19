package data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class RoadDeadend_2 extends RoadCard {

	private final int down = 0;

	private Image image = Toolkit.getDefaultToolkit().getImage("picture/road/deadend_2.jpg");
	private final int left = 1;
	private final int right = 1;
	private final int up = 0;

	public int getDown() {
		return down;
	}

	public Image getImage() {
		return image;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getUp() {
		return up;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Image image = this.image;
		if (funcCard != null) {
			image = funcCard.getImage();
		}
		setBackground(Color.WHITE);
		if (image != null) {
			int height = image.getHeight(this);
			int width = image.getWidth(this);

			if (height != -1 && height > getHeight())
				height = getHeight();

			if (width != -1 && width > getWidth())
				width = getWidth();

			int x = (int) (((double) (getWidth() - width)) / 2.0);
			int y = (int) (((double) (getHeight() - height)) / 2.0);
			g.drawImage(image, x, y, width, height, this);
		}
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
