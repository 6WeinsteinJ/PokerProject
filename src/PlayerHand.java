package src;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerHand{


    private List<Card> playerHand = new ArrayList<>();
    private Rank rank;
    private List<Card> strongestFive;
    private HandStrength hand;
    Player player;




    public PlayerHand(Player player){
        this.player = player;
        player.setCurrentHand(this);
    }

    public void clearCards(){
        playerHand.clear();
    }
    public void addCard(Card card){
        playerHand.add(card);
    }

    public int getSize(){
        return playerHand.size();
    }

    public String toString(){
        StringBuffer buf = new StringBuffer();
        for(Card i: playerHand){
           buf.append(i);
           buf.append(" ");
        }
        return buf.toString();
    }
    public List<Card> getPlayerHand(){
        return playerHand;
    }

    public Card getCard(int i){
        return playerHand.get(i);
    }

    public Rank getRank(){
        return rank;
    }

    public List<Card> getStrongestFive(){
        return strongestFive;
    }

    public Player getPlayer(){
        return player;
    }

}
