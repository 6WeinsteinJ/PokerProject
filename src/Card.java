package src;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Card implements Comparator<Card> {
	private Suit suit;
	private int value;
	private Image image;
    private static final Map<String, BufferedImage> imageCache = new HashMap<>();


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

	public void setImage(BufferedImage image){
	    this.image = image;
    }


    public static BufferedImage getImage(int value, Suit suit) {
        String k = value + suit.toString();
        return imageCache.computeIfAbsent(k, e -> loadImage(k));
    }

    private static BufferedImage loadImage(String key) {
        try {
            return ImageIO.read(Card.class.getResource("resources/cards/" + key + ".png"));
        } catch (IOException e) {
            System.err.println("Missing card image: " + key + ".png");
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            // Return a blank image instead.
            return new BufferedImage(100, 200, BufferedImage.TYPE_INT_RGB);
        }
    }

}
