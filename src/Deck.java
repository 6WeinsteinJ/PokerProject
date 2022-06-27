package src;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

//Extends makes the class Deck into an ArrayList (?)
public class Deck extends Stack<Card> {



    static Card C2 = new Card(Suit.CLUBS,2);
    static Card C3 = new Card(Suit.CLUBS,3);
    static Card C4 = new Card(Suit.CLUBS,4);
    static Card C5 = new Card(Suit.CLUBS,5);
    static Card C6 = new Card(Suit.CLUBS,6);
    static Card C7 = new Card(Suit.CLUBS,7);
    static Card C8 = new Card(Suit.CLUBS,8);
    static Card C9 = new Card(Suit.CLUBS,9);
    static Card C10 = new Card(Suit.CLUBS,10);
    static Card C11 = new Card(Suit.CLUBS,11);
    static Card C12 = new Card(Suit.CLUBS,12);
    static Card C13 = new Card(Suit.CLUBS,13);
    static Card C14 = new Card(Suit.CLUBS,14);

    static Card D2 = new Card(Suit.DIAMONDS,2);
    static Card D3 = new Card(Suit.DIAMONDS,3);
    static Card D4 = new Card(Suit.DIAMONDS,4);
    static Card D5 = new Card(Suit.DIAMONDS,5);
    static Card D6 = new Card(Suit.DIAMONDS,6);
    static Card D7 = new Card(Suit.DIAMONDS,7);
    static Card D8 = new Card(Suit.DIAMONDS,8);
    static Card D9 = new Card(Suit.DIAMONDS,9);
    static Card D10 = new Card(Suit.DIAMONDS,10);
    static Card D11 = new Card(Suit.DIAMONDS,11);
    static Card D12 = new Card(Suit.DIAMONDS,12);
    static Card D13 = new Card(Suit.DIAMONDS,13);
    static Card D14 = new Card(Suit.DIAMONDS,14);

    static Card H2 = new Card(Suit.HEARTS,2);
    static Card H3 = new Card(Suit.HEARTS,3);
    static Card H4 = new Card(Suit.HEARTS,4);
    static Card H5 = new Card(Suit.HEARTS,5);
    static Card H6 = new Card(Suit.HEARTS,6);
    static Card H7 = new Card(Suit.HEARTS,7);
    static Card H8 = new Card(Suit.HEARTS,8);
    static Card H9 = new Card(Suit.HEARTS,9);
    static Card H10 = new Card(Suit.HEARTS,10);
    static Card H11 = new Card(Suit.HEARTS,11);
    static Card H12 = new Card(Suit.HEARTS,12);
    static Card H13 = new Card(Suit.HEARTS,13);
    static Card H14 = new Card(Suit.HEARTS,14);

    static Card S2 = new Card(Suit.SPADES,2);
    static Card S3 = new Card(Suit.SPADES,3);
    static Card S4 = new Card(Suit.SPADES,4);
    static Card S5 = new Card(Suit.SPADES,5);
    static Card S6 = new Card(Suit.SPADES,6);
    static Card S7 = new Card(Suit.SPADES,7);
    static Card S8 = new Card(Suit.SPADES,8);
    static Card S9 = new Card(Suit.SPADES,9);
    static Card S10 = new Card(Suit.SPADES,10);
    static Card S11 = new Card(Suit.SPADES,11);
    static Card S12 = new Card(Suit.SPADES,12);
    static Card S13 = new Card(Suit.SPADES,13);
    static Card S14 = new Card(Suit.SPADES,14);



    private Deck() {

        addAll(Arrays.asList(C14,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,
                D14,D2,D3,D4,D5,D6,D7,D8,D9,D10,D11,D12,D13,
                H14,H2,H3,H4,H5,H6,H7,H8,H9,H10,H11,H12,H13,
                S14,S2,S3,S4,S5,S6,S7,S8,S9,S10,S11,S12,S13));
    }

    public static Deck getNewDeck() {
        Deck deck = new Deck();
        deck.shuffle();
        return deck;
    }

    private void shuffle() {
        Collections.shuffle(this);
    }

    private void getCardImage(){
        try{
            BufferedImage AceC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/ace_of_clubs.png"));
            BufferedImage AceD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/ace_of_diamonds.png"));
            BufferedImage AceH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/ace_of_hearts.png"));
            BufferedImage AceS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/ace_of_spades.png"));

            BufferedImage twoC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/2_of_clubs.png"));
            BufferedImage twoD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/2_of_diamonds.png"));
            BufferedImage twoH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/2_of_hearts.png"));
            BufferedImage twoS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/2_of_spades.png"));

            BufferedImage threeC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/3_of_clubs.png"));
            BufferedImage fourC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/4_of_clubs.png"));
            BufferedImage fiveC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/5_of_clubs.png"));
            BufferedImage sixC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/6_of_clubs.png"));
            BufferedImage sevenC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/7_of_clubs.png"));
            BufferedImage eightC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/8_of_clubs.png"));
            BufferedImage nineC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/9_of_clubs.png"));
            BufferedImage tenC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/10_of_clubs.png"));
            BufferedImage jackC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/jack_of_clubs2.png"));
            BufferedImage queenC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/queen_of_clubs2.png"));
            BufferedImage kingC = ImageIO.read(getClass().getResourceAsStream("/resources/cards/king_of_clubs2.png"));

            BufferedImage threeD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/3_of_diamonds.png"));
            BufferedImage fourD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/4_of_diamonds.png"));
            BufferedImage fiveD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/5_of_diamonds.png"));
            BufferedImage sixD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/6_of_diamonds.png"));
            BufferedImage sevenD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/7_of_diamonds.png"));
            BufferedImage eightD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/8_of_diamonds.png"));
            BufferedImage nineD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/9_of_diamonds.png"));
            BufferedImage tenD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/10_of_diamonds.png"));
            BufferedImage jackD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/jack_of_diamonds2.png"));
            BufferedImage queenD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/queen_of_diamonds2.png"));
            BufferedImage kingD = ImageIO.read(getClass().getResourceAsStream("/resources/cards/king_of_diamonds2.png"));

            BufferedImage threeH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/3_of_hearts.png"));
            BufferedImage fourH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/4_of_hearts.png"));
            BufferedImage fiveH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/5_of_hearts.png"));
            BufferedImage sixH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/6_of_hearts.png"));
            BufferedImage sevenH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/7_of_hearts.png"));
            BufferedImage eightH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/8_of_hearts.png"));
            BufferedImage nineH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/9_of_hearts.png"));
            BufferedImage tenH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/10_of_hearts.png"));
            BufferedImage jackH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/jack_of_hearts2.png"));
            BufferedImage queenH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/queen_of_hearts2.png"));
            BufferedImage kingH = ImageIO.read(getClass().getResourceAsStream("/resources/cards/king_of_hearts2.png"));


            BufferedImage threeS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/3_of_spades.png"));
            BufferedImage fourS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/4_of_spades.png"));
            BufferedImage fiveS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/5_of_spades.png"));
            BufferedImage sixS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/6_of_spades.png"));
            BufferedImage sevenS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/7_of_spades.png"));
            BufferedImage eightS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/8_of_spades.png"));
            BufferedImage nineS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/9_of_spades.png"));
            BufferedImage tenS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/10_of_spades.png"));
            BufferedImage jackS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/jack_of_spades2.png"));
            BufferedImage queenS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/queen_of_spades2.png"));
            BufferedImage kingS = ImageIO.read(getClass().getResourceAsStream("/resources/cards/king_of_spades2.png"));



        } catch(IOException e){
            e.printStackTrace();
        }

    }
}
