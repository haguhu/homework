package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.DisCardButton;
import controller.Game;
import controller.NextTermButton;
import controller.Player;
import controller.SelecteCardAction;
import controller.UndoButton;
import data.Card;

public class HandView extends JPanel {

	private JPanel buttonJPanel;

	private JPanel cardJPanel;

	private DisCardButton disCardButton;

	private boolean isPlay;
	private NextTermButton nextTermButton;
	private Player player;
	private JLabel playerGoldNumber;
	private JPanel playerInfoJPanel;
	private JLabel playerName;
	private JLabel playerRole;
	private List<Player> players;
	private int playersIndex;
	private JLabel playerStatus;
	private Card selectedCard;
	private UndoButton undoButton;
	public HandView() {
		this.players = Game.genPlayers();

		this.setLayout(new BorderLayout());

		playerInfoJPanel = new JPanel();
		playerInfoJPanel.setPreferredSize(new Dimension(100, 150));
		playerInfoJPanel.setLayout(new GridLayout(4, 1, 10, 10));
		playerName = new JLabel();
		playerStatus = new JLabel();
		playerRole = new JLabel();
		playerGoldNumber = new JLabel();

		playerInfoJPanel.add(playerName);
		playerInfoJPanel.add(playerStatus);
		playerInfoJPanel.add(playerRole);
		playerInfoJPanel.add(playerGoldNumber);

		cardJPanel = new JPanel();
		cardJPanel.setPreferredSize(new Dimension(800, 150));
		cardJPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonJPanel = new JPanel(new GridLayout(2, 2, 5, 10));
		buttonJPanel.setPreferredSize(new Dimension(160, 150));
		nextTermButton = new NextTermButton(this);
		disCardButton = new DisCardButton(this);
		undoButton = new UndoButton(this);

		buttonJPanel.add(nextTermButton);
		buttonJPanel.add(disCardButton);
		buttonJPanel.add(undoButton);

		this.add(cardJPanel, BorderLayout.WEST);
		this.add(playerInfoJPanel, BorderLayout.CENTER);
		this.add(buttonJPanel, BorderLayout.EAST);

		this.setPreferredSize(new Dimension(1024, 150));
		this.setVisible(true);
		this.addMouseListener(new SelecteCardAction(this));
	}

	public Player getPlayer() {
		return player;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public int getPlayersIndex() {
		return playersIndex;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public void init() {
		for (int j = 0; j < players.size(); j++) {
			players.get(j).emptyHandCard();
			players.get(j).setUndoIndex(0);
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < players.size(); j++)
				players.get(j).drawCard();
		}
		this.setPlayer(players.get(0));
		isPlay = false;
	}

	public boolean isPlay() {
		return isPlay;
	}

	public void nextTerm() {
		if (player.getStatus().equals(Player.HEALTHY) || (player.getStatus().equals(Player.SICK) && this.isPlay())) {
			player.drawCard();
		} else if (!this.isPlay()) {
			player.setStatus(Player.HEALTHY);
		}
		if (++playersIndex > players.size() - 1) {
			playersIndex = 0;
		}
		setPlayer(players.get(playersIndex));
		setSelectedCard(null);
		isPlay = false;
		if (player.getUndoIndex() != 0) {
			undoButton.setEnabled(false);
		} else
			undoButton.setEnabled(true);

	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}

	public void playCard() {
		player.playCard(getSelectedCard());
		isPlay = true;
		update();
	}

	public void setPlayer(Player player) {
		this.player = player;
		update();
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setPlayersIndex(int playersIndex) {
		this.playersIndex = playersIndex;
	}

	public void setSelectedCard(Card seletedCard) {
		this.selectedCard = seletedCard;
	}

	public void update() {
		cardJPanel.removeAll();
		for (int i = 0; i < player.getHandCards().size(); i++) {
			cardJPanel.add(player.getHandCards().get(i));
		}
		updatePlayerInfo();
		updateUI();
	}

	public void updatePlayerInfo() {
		playerName.setText(this.player.getName());
		playerStatus.setText(this.player.getStatus());
		playerRole.setText(this.player.getRole());
		playerGoldNumber.setText("Gold: " + this.player.getGoldNumber());
		if (this.player.getStatus().equals(Player.HEALTHY)) {
			playerStatus.setForeground(Color.GREEN);
		} else
			playerStatus.setForeground(Color.red);
	}

}
