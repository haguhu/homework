package controller;

import java.util.ArrayList;
import java.util.List;

import data.Card;
import data.RoleCard;
import view.GameBoard;

public class Player {
	public static final String HEALTHY = "HEALTHY";
	public static final String SICK = "SICK";

	private int goldNumber;

	private List<Card> handCards = new ArrayList<Card>();

	private String name;

	private String role;

	private RoleCard roleCard;

	private String status;

	private int undoIndex = 0;

	public Player(String name, String role) {
		this.name = name;
		this.role = role;
		this.status = HEALTHY;
	}

	public void drawCard() {
		System.out.println(GameBoard.getCardList().size());
		if (GameBoard.getCardList().size() > 0) {
			handCards.add(GameBoard.getCardList().get(0));
			GameBoard.getCardList().remove(0);
		}
	}

	public void emptyHandCard() {
		handCards.clear();
	}

	public int getGoldNumber() {
		return goldNumber;
	}

	public List<Card> getHandCards() {
		return handCards;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return this.role;
	}

	public RoleCard getRoleCard() {
		return roleCard;
	}

	public String getStatus() {
		return status;
	}

	public int getUndoIndex() {
		return undoIndex;
	}

	public void playCard(Card card) {
		handCards.remove(card);
	}

	public void setGoldNumber(int goldNumber) {
		this.goldNumber = goldNumber;
	}

	public void setHandCards(List<Card> handCards) {
		this.handCards = handCards;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoleCard(RoleCard roleCard) {
		this.roleCard = roleCard;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUndoIndex(int undoIndex) {
		this.undoIndex = undoIndex;
	}

	public void sick() {
		this.setStatus(SICK);
	}

}
