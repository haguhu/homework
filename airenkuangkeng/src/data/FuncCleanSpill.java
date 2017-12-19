package data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class FuncCleanSpill extends FuncCard {

	private Image image = Toolkit.getDefaultToolkit().getImage("picture/func/CLEAN TOXIC SPILL.jpg");

	public Image getImage() {
		return image;
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

	public void setImage(Image image) {
		this.image = image;
	}

}
