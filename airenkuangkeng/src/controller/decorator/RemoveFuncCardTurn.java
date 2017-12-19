package controller.decorator;

import data.Card;
import data.FuncCard;

public class RemoveFuncCardTurn extends Turn {

	public RemoveFuncCardTurn(Turn step, Card selectedCard, int x, int y) {
		super(step, selectedCard, x, y);
	}

	@Override
	public void undo() {
		Turn.getGameBoard().getBoardCards()[this.getX()][this.getY()].setFuncCard((FuncCard) this.getSelectedCard());
		Turn.getGameBoard().update();
		System.out.println("add funcCard!");
		super.undo();
	}

}
