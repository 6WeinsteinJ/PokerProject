package src;


import java.lang.reflect.Array;
import java.util.*;
import javafx.util.Pair;

public class HandStrength {
    private TreeMap<Integer, List<Card>> valueCardMap = new TreeMap<>();
    private TreeMap<Suit, List<Card>> suitCardMap = new TreeMap<>();
    private TreeMap<Integer, List<Integer>> duplicateValueMap = new TreeMap<>();
    private Pair<Rank,List<Card>> bestFive;



    public HandStrength(PlayerHand hand, List<Card> communityCards){
        List<Card> cardList = new ArrayList<>();
        List<Card> test = hand.getPlayerHand();
        cardList.addAll(test);
        cardList.addAll(communityCards);
        Collections.sort(cardList, new Card());

        putInMap(cardList);
        duplicateMap();
        bestFive = determineBestFive(cardList);

    }



    /**
     * Determines the best 5 cards and the rank of them
     * @param cards the playerHand that is inputted
     * @return the rank and the best 5 cards in a Pair
     */
    public Pair<Rank, List<Card>> determineBestFive(List<Card> cards){
        List<Card> hand;
        int handSize = cards.size();
        if(handSize > 5) {
            Pair<Rank, List<Card>> flushPair = determineFlush();
            if (flushPair != null) {
                if (flushPair.getKey() == Rank.STRAIGHT_FLUSH) {
                    return flushPair;
                }
            }

            hand = fourOfAKind();
            if (hand != null) {
                return new Pair<>(Rank.QUADS, hand);
            }
            hand = fullHouse();
            if (hand != null) {
                return new Pair<>(Rank.FULL_HOUSE, hand);
            }
            if (flushPair != null) {
                return flushPair;
            }
            ArrayList<Card> straightCards = new ArrayList<>();
            NavigableSet<Integer> valueSet = valueCardMap.navigableKeySet();
            Iterator<Integer> it = valueSet.iterator();
            while (it.hasNext()) {
                straightCards.add(valueCardMap.get(it.next()).get(0));
            }

            hand = determineStraight(straightCards);
            if (hand != null) {
                return new Pair<>(Rank.STRAIGHT, hand);
            }
            hand = threeOfAKind();
            if (hand != null) {
                return new Pair<>(Rank.TRIPS, hand);
            }
            hand = TwoPair();
            if (hand != null) {
                return new Pair<>(Rank.TWO_PAIR, hand);
            }
        }
        hand = OnePair();
        if(hand != null){
            return new Pair<>(Rank.ONE_PAIR, hand);
        }
        List<Integer> noValue = new ArrayList<>();
        hand = getKickers(noValue, 5);
        return new Pair<>(Rank.HIGH_CARD, hand);

    }

    /**
     * Puts all of the player's cards into a mapping
     * @param cards - the player's ArrayList of cards
     */

    private void putInMap(List<Card> cards){
        for(int i = 0; i < cards.size(); i++){
            Card card1 = cards.get(i);
            List<Card> cardsByValue = valueCardMap.get(card1.getValue());
            if (cardsByValue == null) {
                cardsByValue = new ArrayList<>();
                valueCardMap.put(card1.getValue(), cardsByValue);
            }

            cardsByValue.add(card1);

            List<Card> cardsBySuit = suitCardMap.get(card1.getSuit());
            if (cardsBySuit == null) {
                cardsBySuit = new ArrayList<>();
                suitCardMap.put(card1.getSuit(), cardsBySuit);
            }

            cardsBySuit.add(card1);


        }
    }

    /**
     * Initializes the duplicateValueMap which maps the number of instances of a value to the value
     */
    private void duplicateMap(){
        int duplicateNumber;
        int key = valueCardMap.lastKey();
        //Iterates through the valueCardMap
        for(int i = valueCardMap.size() - 1; i >= 0; i--){
            duplicateNumber = valueCardMap.get(key).size();
            List<Integer> duplicateList = duplicateValueMap.get(duplicateNumber);

            //Creates a list in the map if it doesn't exist
            if(duplicateList == null){
                duplicateList = new ArrayList<>();
                duplicateValueMap.put(duplicateNumber, duplicateList);
            }

            duplicateList.add(key);

            if(valueCardMap.lowerKey(key) != null){
                key = valueCardMap.lowerKey(key);
            }
        }
    }

    /**
     * Determines whether there is quads given the player hand
     * @return a List of the cards making up a four of a kind if it exists or null if it doesn't
     */
    private List<Card> fourOfAKind(){
        return duplicateHelper(4);
    }

    /**
     * Determines whether there is a full house in the list of cards
     * @return the highest full house (three of a kind + pair) if it exists. If it doesn't, return null
     */
    private List<Card> fullHouse(){
        List<Integer> tripsList = duplicateValueMap.get(3);
        List<Integer> pairList = duplicateValueMap.get(2);
        //Checks if there is a 3 of a kind
        if(tripsList != null) {

            //Checks if there is a pair other than the aforementioned three of a kind
            if(tripsList.size() > 1 || pairList != null) {

                List<Card> cardList = new ArrayList<>();

                //Adds the three of a kind to another list
                cardList.addAll(valueCardMap.get(tripsList.get(0)));
                int firstPair = -1;
                if (tripsList.size() > 1) {
                    firstPair = (tripsList.get(1));
                }

                if (pairList != null) {

                    if (pairList.size() > 0) {
                        if (pairList.get(0) > firstPair) {
                            firstPair = pairList.get(0);
                        }
                    }
                }
                cardList.add(valueCardMap.get(firstPair).get(0));
                cardList.add(valueCardMap.get(firstPair).get(1));
                return cardList;
            }

        }
        return null;
    }

    /**
     * Determines whether there is three of a kind given the player hand
     * @return a List of the cards making up a three of a kind if it exists or null if it doesn't
     */
    private List<Card> threeOfAKind(){
        return duplicateHelper(3);
    }

    /**
     * Determines the highest two pair poker hand
     * @return The highest two pairs if there is a two pair, and null if there isn't
     */
    private List<Card> TwoPair(){
        List<Integer> pairList = duplicateValueMap.get(2);
        if(pairList != null) {
            if (pairList.size() >= 2) {
                List<Card> cardList = new ArrayList<>();
                int pairNum = 0;
                List<Integer> valuesToIgnore = new ArrayList<>();
                while (pairNum <= 1) {
                    valuesToIgnore.add(pairList.get(pairNum));
                    cardList.addAll(valueCardMap.get(pairList.get(pairNum)));
                    pairNum++;
                }
                cardList.addAll(getKickers(valuesToIgnore,1));
                return cardList;
            }
        }
        return null;
    }

    /**
     * Determines the highest pair poker hand
     * @return A pair if one exists, and null if there isn't
     */
    private List<Card> OnePair(){
        return duplicateHelper(2);
    }

    /**
     * A helper function for the duplicate-based poker hand checks
     * @param duplicateNum the number of duplicates a value has
     * @return The best 5 cards in terms of pairs/three of a kind
     */
    private List<Card> duplicateHelper(int duplicateNum){

        //Checks to make sure the number of duplicates specified (duplicateNum) exists in the duplicateValueMap
        if(duplicateValueMap.get(duplicateNum) != null) {


            int value = duplicateValueMap.get(duplicateNum).get(0);
            List<Card> cardList = new ArrayList<>();
            cardList.addAll(valueCardMap.get(value));
            List<Integer> valuesToIgnore = new ArrayList<>();
            valuesToIgnore.add(value);
            cardList.addAll(getKickers(valuesToIgnore, 5 - duplicateNum));
            return cardList;
        }
        return null;
    }


    /**
     * Determines the best non-paired cards to go with a poker hand
     * @param valuesToIgnore the values to ignore when searching for kickers, which are the pair values
     * @param numberOfKickers the number of kickers the function will generate
     * @return a list with all the kickers
     */

    private List<Card> getKickers(List<Integer> valuesToIgnore, int numberOfKickers){
        int search = valueCardMap.lastKey();
        List<Card> kickerList = new ArrayList<>();
        for(int i = 0; i < numberOfKickers; i++){
            if(valuesToIgnore.contains(search) == false){
                kickerList.add(valueCardMap.get(search).get(0));

            }else{
                i--;
            }
            search = valueCardMap.lowerKey(search);
        }
        return kickerList;
    }


    /**
     *
     * @param sortedCards cards sorted in ascending order
     * @return list of cards for the straight, or null
     */
    private List<Card> determineStraight(List<Card> sortedCards) {
        if (sortedCards.size() < 5) {
            return null;
        }

        int counter = 1;
        int size = sortedCards.size();
        int nextValue = sortedCards.get(size -1).getValue();
        for (int i = size - 2; i>= 0; i--) {
            Card card = sortedCards.get(i);
            if (nextValue - card.getValue() == 1) {
                counter++;
            } else {
                counter = 1;
            }

            if (counter == 5) {
                // we have a straight
                return new ArrayList<>(sortedCards.subList(i, i+5));
            }

            nextValue = card.getValue();
        }

        //special case with ace to 5 (the wheel)
        if(counter == 4 && sortedCards.get(0).getValue() == 2 && sortedCards.get(size-1).getValue() == 14){
            ArrayList<Card> aceStraight = new ArrayList<>();
            aceStraight.add(sortedCards.get(size - 1));
            aceStraight.addAll(sortedCards.subList(0, 4));

            return aceStraight;
        }

        return null;
    }



    /**
     * Determines whether there is a flush in the playerHand
     * @return A pair of Card Rank and List of the of the best 5 cards
     */
    private Pair<Rank, List<Card>> determineFlush(){
        List<Card> cardList = new ArrayList<>();
        Suit suit = suitCardMap.firstKey();

        //Iterates through the map of the suits
        for(int i = 0; i < suitCardMap.size(); i++){
            //If there are 5 or more of one suit in the map
            if(suitCardMap.get(suit).size() >= 5 ){
                cardList = suitCardMap.get(suit);


                for(int j = cardList.size() - 1; j >= 0; j--){
                    List<Card> straight = determineStraight(cardList);

                    //Special Case with a straight flush
                    if(straight != null){
                        return new Pair<>(Rank.STRAIGHT_FLUSH,straight);
                    }


                    ArrayList<Card> subList = new ArrayList<>(cardList.subList(cardList.size()-5, cardList.size()));
                    Collections.reverse(subList);
                    return new Pair<>(Rank.FLUSH, subList);
                }

                //If it finds 5 or more of one suit, it exits to save time
                break;
            }

            if(suitCardMap.higherKey(suit) != null){
                suit = suitCardMap.higherKey(suit);
            }
        }
        return null;

    }

    /**
     * @return a string of the rank
     */
    public Rank getRank(){
        return bestFive.getKey();
    }

    /**
     * @return a string of the best 5 cards
     */
    public List<Card> getBestFive(){
        return bestFive.getValue();
    }


}

