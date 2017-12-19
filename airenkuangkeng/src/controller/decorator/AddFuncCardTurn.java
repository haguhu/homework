package controller.decorator;

import data.Card;

public class AddFuncCardTurn extends Turn {

	public AddFuncCardTurn(Turn step, Card selectedCard, int x, int y) {
		super(step, selectedCard, x, y);
	}

	@Override
	public void undo() {
		Turn.getGameBoard().getBoardCards()[this.getX()][this.getY()].setFuncCard(null);
		Turn.getGameBoard().update();
		System.out.println("remove funcCard!");
		super.undo();
	}

}
