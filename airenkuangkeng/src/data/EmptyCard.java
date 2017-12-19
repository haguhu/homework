package data;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class EmptyCard extends Card {

	private Card card;

	private float[] dash1 = { 5.0f };

	// ÑÕÉ«
	private Color mfColor = new Color(255, 0, 0);
	private Rectangle2D mfRect = new Rectangle2D.Float();

	private BasicStroke s = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

	public EmptyCard() {
		super();
	}

	public Card getCard() {
		return card;
	}

	public void paint(Graphics g) {

		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		// ÉèÖÃ±ß¿òÑÕÉ«
		g2d.setColor(mfColor);
		// ÉèÖÃ±ß¿ò·¶Î§
		mfRect.setRect(0, 0, getWidth() - 1, getHeight() - 1);
		// ÉèÖÃ±ß¿òÀàÐÍ
		g2d.setStroke(s);

		g2d.draw(mfRect);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if (card != null)
			image = card.getImage();
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

	public void setCard(Card card) {
		this.card = card;
		paintComponent(getGraphics());
	}

}
