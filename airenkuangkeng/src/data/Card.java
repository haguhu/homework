package data;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JPanel;

public class Card extends JPanel implements Cloneable {

	protected FuncCard funcCard;
	protected Image image;

	public Card() {
		setPreferredSize(new Dimension(125, 150));
	}

	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	public FuncCard getFuncCard() {
		return funcCard;
	}

	public Image getImage() {
		return image;
	}

	public void setFuncCard(FuncCard funcCard) {
		this.funcCard = funcCard;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
