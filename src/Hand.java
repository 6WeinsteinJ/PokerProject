package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Hand {
    private LinkedList<Player> activePlayers;
    private Deck deck;
    private List<Card> communityCards = new ArrayList<>();
    private int mainPot = 0;
    private int currentBetAmount = 0;
    private Player currentPlayer;
    private int currentBlind;
    private int bigBlind;
    private Player currentBetPivot;


    public Hand(){
        dealNewHand(Main.players);
    }


    private void dealNewHand(List<Player> players){
        deck = Deck.getNewDeck();
        for(int i = 0; i < players.size()*2; i++){
            players.get(i % players.size()).getCurrentHand().addCard(deck.pop());
        }
    }




    public List<PlayerHand> determineWinningHands(List<PlayerHand> playerList){
        PlayerHand bestPlayer = playerList.get(0);
        List<PlayerHand> winners = new ArrayList<>();
        winners.add(bestPlayer);
        Rank bestRank = bestPlayer.getRank();
        List<Card> bestFive = bestPlayer.getStrongestFive();
        PlayerHand current;
        //hIndex is the hand Index
        //Goes through the list of hands to check if they're better than the current best
        for(int hIndex = 1; hIndex < playerList.size(); hIndex++){
            current = playerList.get(hIndex);
            if(current.getRank().ordinal() > bestRank.ordinal()){
                winners.clear();
                winners.add(current);
                bestRank = current.getRank();
                bestFive = current.getStrongestFive();

            } else if (current.getRank().ordinal() == bestRank.ordinal()) {
                //Iterates through all 5 of the cards
                for(int cIndex = 0; cIndex < 5; cIndex++){
                    if(current.getStrongestFive().get(cIndex).getValue() > bestFive.get(cIndex).getValue()){
                        winners.clear();
                        bestFive = current.getStrongestFive();
                        winners.add(current);
                        break;
                    }
                    //This is the case of a tie hand
                    if(cIndex == 4){
                        winners.add(current);
                    }
                }
            }
        }
        return winners;
    }



    public Deck getDeck(){
        return deck;
    }

    public void setCurrentBetAmount(int currentBetAmount){
        this.currentBetAmount = currentBetAmount;
    }

    public int getCurrentBetAmount(){
        return currentBetAmount;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public void setBigBlind(int bigBlind){
        this.bigBlind = bigBlind;
    }

    public int getBigBlind(){
        return bigBlind;
    }

    public void setCurrentBetPivot(Player currentBetPivot){
        this.currentBetPivot = currentBetPivot;
    }

    public Player getCurrentBetPivot(){
        return currentBetPivot;
    }

    public void addPot(int pot){
        mainPot += pot;
    }

    public void setPot(int pot){
        mainPot = pot;
    }

    public int getPot(){
        return mainPot;
    }
}
