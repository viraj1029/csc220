package blackjack;

import java.util.ArrayList;

public class Hand {
	ArrayList<Card> cards = new ArrayList<Card>();
	int value;

	public Hand(Card card1, Card card2) {
		cards.add(card1);
		cards.add(card2);
		value = card1.playValue + card2.playValue;
	}

	private void addCard(Card card) {
		cards.add(card);
		value += card.playValue;

	}

	private int getValue() {
		return this.value;
	}

	public String toString() {
		String ret = ("Hand consists of: ");
		for (int i = 0; i < cards.size(); i++)
			{ret += (cards.get(i));
			if (i < cards.size()-1)
				ret += (", ");
			}
		ret += " Hand Value is: " + value;
		return ret;
	}
}
