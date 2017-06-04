package controller.decorator;

import data.Card;
import view.GameBoard;
import view.HandView;

public class Turn {
	private static GameBoard gameBoard;
	private static HandView hv;
	public static Turn turn = null;

	public static GameBoard getGameBoard() {
		return gameBoard;
	}

	public static HandView getHv() {
		return hv;
	}

	public static void setGameBoard(GameBoard gameBoard) {
		Turn.gameBoard = gameBoard;
	}

	public static void setHv(HandView hv) {
		Turn.hv = hv;
	}

	private Card selectedCard;

	private Turn step;

	private int x, y;

	public Turn(Turn step, Card selectedCard, int x, int y) {
		this.step = step;
		this.selectedCard = selectedCard;
		this.x = x;
		this.y = y;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}

	public Turn getStep() {
		return step;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setSelectedCard(Card selectedCard) {
		this.selectedCard = selectedCard;
	}

	public void setStep(Turn step) {
		this.step = step;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void undo() {
		Turn.turn = Turn.turn.getStep();
		System.out.println("undo");
	}

}
