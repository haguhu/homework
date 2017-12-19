package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class AllCards {
	private static Hashtable<String, Card> cardMap = new Hashtable<String, Card>();

	static {
		loadCard();

	}

	public static Card getCard(String cardId) {
		Card cachedCard = cardMap.get(cardId);
		return (Card) cachedCard.clone();
	}

	public static List<Card> getCardList() {
		// TODO Auto-generated method stub
		List<Card> cardList = new ArrayList<Card>();
		for (int i = 0; i < 4; i++) {
			 cardList.add(getCard("RoadA"));
			 cardList.add(getCard("RoadB"));
			 cardList.add(getCard("RoadC"));
			 cardList.add(getCard("RoadDeadend_1"));
			 cardList.add(getCard("RoadDeadend_2"));
			 cardList.add(getCard("RoadDeadend_3"));
			 cardList.add(getCard("RoadE"));
			 cardList.add(getCard("RoadF"));
			 cardList.add(getCard("RoadG"));
			cardList.add(getCard("RoadH"));
		}
		for (int i = 0; i < 4; i++) {
			 cardList.add(getCard("FuncCleanOrRepaire"));
			 cardList.add(getCard("FuncSpill"));
			 cardList.add(getCard("FuncRockFall"));
			 cardList.add(getCard("FuncRepaireRockFall"));
			 cardList.add(getCard("FuncMap"));
			 cardList.add(getCard("FuncCleanSpill"));
			 cardList.add(getCard("SickCard"));
		}
		Collections.shuffle(cardList);
		return cardList;
	}

	public static List<EndCard> getEndCardList() {

		List<EndCard> endCards = new ArrayList<EndCard>();
		endCards.add(new Stone());
		endCards.add(new Stone());
		endCards.add(new Gold());
		Collections.shuffle(endCards);
		return endCards;
	}

	public static void loadCard() {
		Card card;
		card = new RoadA();
		cardMap.put("RoadA", card);
		card = new RoadB();
		cardMap.put("RoadB", card);
		card = new RoadC();
		cardMap.put("RoadC", card);
		card = new RoadDeadend_1();
		cardMap.put("RoadDeadend_1", card);
		card = new RoadDeadend_2();
		cardMap.put("RoadDeadend_2", card);
		card = new RoadDeadend_3();
		cardMap.put("RoadDeadend_3", card);
		card = new RoadE();
		cardMap.put("RoadE", card);
		card = new RoadF();
		cardMap.put("RoadF", card);
		card = new RoadG();
		cardMap.put("RoadG", card);
		card = new RoadH();
		cardMap.put("RoadH", card);

		card = new FuncCleanOrRepaire();
		cardMap.put("FuncCleanOrRepaire", card);
		card = new FuncSpill();
		cardMap.put("FuncSpill", card);
		card = new FuncRockFall();
		cardMap.put("FuncRockFall", card);
		card = new FuncRepaireRockFall();
		cardMap.put("FuncRepaireRockFall", card);
		card = new FuncMap();
		cardMap.put("FuncMap", card);
		card = new FuncCleanSpill();
		cardMap.put("FuncCleanSpill", card);
		card = new SickCard();
		cardMap.put("SickCard", card);

		card = new Stone();
		cardMap.put("Stone", card);
		card = new Gold();
		cardMap.put("Gold", card);
	}

}
