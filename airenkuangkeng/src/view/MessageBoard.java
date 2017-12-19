package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageBoard extends JPanel {

	private JLabel roleLabel;
	private int round = 1;
	private JLabel roundLabel;

	public MessageBoard() {
		roundLabel = new JLabel(round + "/3");
		roleLabel = new JLabel();
		this.add(roundLabel);
		this.add(roleLabel);
	}

	public int getRound() {
		return round;
	}

	public void setRole(String role) {
		roleLabel.setText(role);
	}

	public void setRound(int round) {
		this.round = round;
	}

}
