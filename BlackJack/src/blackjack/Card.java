package blackjack;

public class Card {
	String suite;
	int value;
	boolean visible;;
	int cardValue;
	int playValue;

	public Card(int suite, int value) {
		switch (suite) {
		case 0:
			this.suite = "Diamonds";
			break;
		case 1:
			this.suite = "Hearts";
			break;
		case 2:
			this.suite = "Clubs";
			break;
		case 3:
			this.suite = "Spades";
			break;
		default:
			this.suite = "default";
			break;
		}
		if (value > 10)
			playValue = value - (value-10);
		else
		playValue = value;
		
		this.value = value;
		visible = true;
		//calculate the value of the card from 0-51
		//useful for shuffling with random values
		cardValue = suite*13+value-2;
	}
	private boolean isVisible()
	{
		return visible;
	}

	public Card() {
		this.suite = "";
		this.value = 0;
		this.visible = true;
		cardValue = 0;

	}

	public  String toString() {
		String ret = " of " + suite;
		if (value < 11)
			ret = value + ret;
		else
			switch (value) {
			case 11:
				ret = "Jack" + ret;
				break;
			case 12:
				ret = "Queen" + ret;
				break;
			case 13:
				ret = "King" + ret;
				break;
			case 14:
				ret = "Ace" + ret;
				break;
			default:
				ret = "INVALID";
			}
		return ret;
	}
}
