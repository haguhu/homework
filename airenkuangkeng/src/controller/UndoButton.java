package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import controller.decorator.Turn;
import view.HandView;

public class UndoButton extends JButton implements ActionListener {

	private HandView hv;

	public UndoButton(HandView hv) {
		this.hv = hv;
		setText("undo");
		setPreferredSize(new Dimension(100, 75));
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Turn.turn != null)
			if (hv.getPlayer().getUndoIndex() < 3) {
				Turn.turn.undo();
				hv.getPlayer().setUndoIndex(hv.getPlayer().getUndoIndex() + 1);
			} else {
				JOptionPane.showMessageDialog(null, "You can undo 3 times at most!");
			}
		else {
			JOptionPane.showMessageDialog(null, "ÒÑÎÞ²Ù×÷£¡");
		}
	}

}
