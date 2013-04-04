package blackjack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import prog05.ArrayStack;
import prog05.StackInt;

public class Main {
	static Scanner in;
	static ArrayList<Card> startDeck = new ArrayList<Card>();
	static StackInt<Card> deck = new ArrayStack<Card>();

	public static void main(String[] args) {
		InitializeDeck();
		shuffle(startDeck);
		Hand player1 = new Hand(deck.pop(), deck.pop());
		System.out.println(player1);
		//while (!deck.empty()) {
			//System.out.println(deck.pop().cardValue);
		
		printDeck();
		System.out.println(find(startDeck, 49));

	}

	private static void InitializeDeck() {
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 15; j++)
				startDeck.add(new Card(i, j));
		}
	}

	private static void printDeck() {
		for (int i = 0; i < 52; i++) {
			System.out.println(startDeck.get(i).cardValue);
		}
	}

	private static void shuffle(ArrayList<Card> startDeck) {
		Random random = new Random();
		int shuffleIndex;

		int i = 0;
		while (i < 52) {
			shuffleIndex = random.nextInt(52);
			if (find(startDeck, shuffleIndex) > 0) {
				deck.push(startDeck.get(shuffleIndex));
				i++;
			}
		}
	}

	public static int find(ArrayList<Card> deck, int value) {
		int middle, first, last;
		first = 0;
		last = deck.size() - 1;

		while (first < last) {
			middle = (first + last) / 2;
			if (deck.get(middle).cardValue == value) {
				return middle;
			}
			if (deck.get(middle).cardValue > value) {
				last = middle - 1;
			}
			if (deck.get(middle).cardValue < value) {
				first = middle + 1;
			}
		}
		if (deck.get(first).cardValue != value)
			return -1;
		return first;

	}
}
