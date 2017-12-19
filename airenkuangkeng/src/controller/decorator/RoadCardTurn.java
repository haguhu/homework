package controller.decorator;

import data.Card;
import data.EmptyCard;

public class RoadCardTurn extends Turn {

	public RoadCardTurn(Turn step, Card selectedCard, int x, int y) {
		super(step, selectedCard, x, y);
	}

	@Override
	public void undo() {
		System.out.println("remove roadCard!");
		Turn.getGameBoard().getBoardCards()[this.getX()][this.getY()] = new EmptyCard();
		Turn.getGameBoard().update();
		super.undo();
	}

}
