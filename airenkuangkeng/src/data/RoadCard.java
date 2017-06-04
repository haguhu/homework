package data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import view.HandView;

public class RoadCard extends Card {
	protected int down;
	protected HandView hv;

	protected Image image = Toolkit.getDefaultToolkit().getImage("picture/func/BACK.jpg");
	protected int left;
	protected int right;
	protected int up;

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

	public void setDown(int down) {
		this.down = down;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setUp(int up) {
		this.up = up;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "up:" + getUp() + "left:" + getLeft() + "down:" + getDown() + "right" + getRight();
	}

}
