package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import view.HandView;

public class DisCardButton extends JButton implements ActionListener {

	private HandView hv;

	public DisCardButton(HandView hv) {
		// TODO Auto-generated constructor stub
		this.hv = hv;
		setText("discard");
		setPreferredSize(new Dimension(100, 75));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (hv.getSelectedCard() == null) {
			JOptionPane.showMessageDialog(null, "choose a card");
			return;
		}
		if (hv.isPlay()) {
			JOptionPane.showMessageDialog(null, "you have played");
			return;
		}
		hv.playCard();

		if (Game.noHandCard() && !Game.minerWin) {
			Game.SaboteurWin = true;
			JOptionPane.showMessageDialog(null, "Saboteur Win!");
			Game.dispatchGold();
			Game.SaboteurWin = false;
			Game.getGame().init();
		}
	}

}
