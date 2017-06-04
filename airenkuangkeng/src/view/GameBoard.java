package view;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;

import controller.SelectPositionAction;
import controller.decorator.Turn;
import data.AllCards;
import data.Card;
import data.EmptyCard;
import data.EndCard;
import data.StartCard;

public class GameBoard extends JPanel {

	private static List<Card> cardList;
	// private List<Card> boradCards = new ArrayList<Card>();
	private static List<EndCard> endCards;

	public static List<Card> getCardList() {
		return cardList;
	}

	public static List<EndCard> getEndCards() {
		return endCards;
	}

	public static void setCardList(List<Card> cardList) {
		GameBoard.cardList = cardList;
	}

	private Card[][] boardCards = new Card[5][9];

	private HandView hv;

	private StartCard startCard = new StartCard();

	public GameBoard(HandView hv) {
		this.hv = hv;

		this.setLayout(new GridLayout(5, 9));
		this.addMouseListener(new SelectPositionAction(this, hv));
	}

	public Card[][] getBoardCards() {
		return boardCards;
	}

	public StartCard getStartCard() {
		return startCard;
	}

	public void init() {
		cardList = AllCards.getCardList();
		endCards = AllCards.getEndCardList();

		Turn.setGameBoard(this);
		Turn.setHv(hv);
		Turn.turn = null;
		this.removeAll();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 9; j++) {
				if (i == 0 && j == 8) {
					boardCards[i][j] = endCards.get(0);
					this.add(endCards.get(0));
				} else if (i == 2 && j == 0) {
					StartCard sc = new StartCard();
					boardCards[i][j] = sc;
					this.add(sc);
				} else if (i == 2 && j == 8) {
					boardCards[i][j] = endCards.get(1);
					this.add(endCards.get(1));
				} else if (i == 4 && j == 8) {
					boardCards[i][j] = endCards.get(2);
					this.add(endCards.get(2));
				} else {
					EmptyCard ec = new EmptyCard();
					boardCards[i][j] = ec;
					this.add(ec);
				}
			}
		}

	}

	public void setBoardCards(Card[][] boardCards) {
		this.boardCards = boardCards;
	}

	public void setEndCards(List<EndCard> endCards) {
		this.endCards = endCards;
	}

	public void setStartCard(StartCard startCard) {
		this.startCard = startCard;
	}

	public void update() {
		removeAll();
		for (EndCard ec : endCards) {
			if (ec.isFound()) {
				ec.showImage();
			} else
				ec.showBack();
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 9; j++) {
				this.add(boardCards[i][j]);
			}
		}
		updateUI();
	}

}
