package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import data.EndCard;
import view.GameBoard;
import view.HandView;

public class NextTermButton extends JButton implements ActionListener {

	private HandView hv;

	public NextTermButton(HandView hv) {
		// TODO Auto-generated constructor stub
		this.hv = hv;
		setText("next");
		setPreferredSize(new Dimension(100, 75));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (hv.getPlayer().getStatus().equals(Player.HEALTHY)) {

			if (!hv.isPlay()) {
				JOptionPane.showMessageDialog(null, "you haven't played or discard yet ");
				return;
			}

			for (EndCard ec : GameBoard.getEndCards()) {
				if (ec.isFound()) {
					ec.showImage();
				} else
					ec.showBack();
				ec.repaint();
			}
			hv.getSelectedCard().setBorder(null);
		}
		hv.nextTerm();

	}

}
