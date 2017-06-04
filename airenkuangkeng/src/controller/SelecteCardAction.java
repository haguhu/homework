package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import data.Card;
import data.SickCard;
import view.HandView;

public class SelecteCardAction extends MouseAdapter {
	private HandView hv;
	protected URL path = this.getClass().getClassLoader().getResource("/");

	public SelecteCardAction(HandView hv) {
		// TODO Auto-generated constructor stub
		this.hv = hv;
	}

	public void mouseClicked(java.awt.event.MouseEvent e) {
		super.mouseClicked(e);

		Player player = hv.getPlayer();
		if (player.getStatus().equals(Player.SICK)) {
			JOptionPane.showMessageDialog(null, "You are sick!");
			return;
		}

		Card lastC = hv.getSelectedCard();
		int width = hv.getPlayer().getHandCards().get(0).getWidth();
		int x = e.getX() / width;
		if (x > hv.getPlayer().getHandCards().size() - 1) {
			return;
		}
		hv.setSelectedCard(hv.getPlayer().getHandCards().get(x));
		if (lastC != null) {
			lastC.setBorder(null);
		}
		if (hv.getSelectedCard() != null) {
			Card c = hv.getSelectedCard();
			c.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			System.out.println(hv.getSelectedCard().getClass().getSimpleName());
		}

		if (hv.getSelectedCard() instanceof SickCard && !hv.isPlay()) {
			SickMenu sickMenu = new SickMenu(hv);
			sickMenu.show(e.getComponent(), e.getX(), e.getY());
		}

		System.out.println(path);

	};

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
	}
}
