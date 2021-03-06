package data;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Miner extends RoleCard {

	private Image imgae = Toolkit.getDefaultToolkit().getImage("picture/role/MINER.jpg");
	private final String role = "Miner";

	public Image getImgae() {
		return imgae;
	}

	public String getRole() {
		return role;
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

	public void setImgae(Image imgae) {
		this.imgae = imgae;
	}

}
