package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import view.GameBoard;
import view.HandView;

public class Game {

	private static Game game;
	public static boolean minerWin;

	public final static int playerNumber = 3;

	private static List<Player> players;

	public static boolean SaboteurWin;

	public static void dispatchGold() {
		String goldMsg = "playerName       new got gold number       total gold number\n";

		String winner = Game.minerWin ? "Miner" : "Saboteur";
		int ratio = Game.minerWin ? 10 : 30;
		for (Player player : getPlayers()) {
			if (player.getRole().equals(winner)) {
				int currentGoldNumber = player.getGoldNumber();
				int newGoldNumber = (int) Math.ceil(Math.random() * ratio);
				player.setGoldNumber(currentGoldNumber + newGoldNumber);
				goldMsg += player.getName() + "              " + newGoldNumber + "              "
						+ (currentGoldNumber + newGoldNumber) + "\n";
			}
		}
		JOptionPane.showMessageDialog(null, goldMsg, "gold number", JOptionPane.INFORMATION_MESSAGE);

	}

	public static List<Player> genPlayers() {
		players = new ArrayList<Player>();
		players.add(new Player("player1", "Miner"));
		players.add(new Player("player2", "Miner"));
		players.add(new Player("player3", "Saboteur"));
		return players;
	}
	public static Game getGame() {
		return game;
	}

	public static Game getGame(GameBoard gb, HandView hv) {
		if (game == null) {
			game = new Game(gb, hv);
		}
		return game;
	}
	public static List<Player> getPlayers() {
		return players;
	}

	public static boolean noHandCard() {
		for (Player player : getPlayers()) {
			if (player.getHandCards().size() > 0) {
				return false;
			}
		}
		return true;
	}

	private GameBoard gb;

	private HandView hv;

	private Game(GameBoard gb, HandView hv) {
		this.gb = gb;
		this.hv = hv;
	}

	public void init() {
		this.gb.init();
		this.hv.init();
		for (Player player : getPlayers()) {
			player.setStatus(Player.HEALTHY);
		}
	}
}
