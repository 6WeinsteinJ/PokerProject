package src;


import java.awt.*;
import java.util.Comparator;

public class Card implements Comparator<Card> {
	private Suit suit;
	private int value;
	private Image image;

	public Card(){

    }
	/**
	 * @param suit
	 * @param power
	 */
	public Card(Suit suit, int power) {

		this.suit = suit;
		this.value = power;
	}

	public int getValue(){
		return value;
	}

	public Suit getSuit(){
		return suit;
	}

	public String toString(){
		//As long as the card value is less than 10, print the value + suit
		if(value < 11) {
			return String.valueOf(value) + suit;
		} else{
			//Figures out which face card the card is to name it appropriately
			String faceCard ="";
			switch(value){
				case 11:
					faceCard = "J";
					break;
				case 12:
					faceCard = "Q";
					break;
				case 13:
					faceCard = "K";
					break;
				case 14:
					faceCard = "A";
					break;
			}
			return faceCard + suit;
		}
	}

	//Compares the values of the cards
	public int compare(Card a, Card b){
		return this.getValue() - b.getValue();
	}
}
