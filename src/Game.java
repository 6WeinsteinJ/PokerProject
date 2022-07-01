package src;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

//General class for keeping track of the game across hands
public class Game {
    static int dealerButtonPos = 1;
    static int handCounter = 0;
    private Hand hand;
    private GamePanel gp;
    private Player currentPlayer;
    private int betRoundCounter = 0;
    private int foldCounter;


    public Game(GamePanel gp) {
        this.gp = gp;
        gp.frameToFront();
        initController();
        startHand();
    }


    /**
     * Starts a new hand instance
      */
    private void getNewHand() {
        hand = new Hand();
    }

    /**
     * Initializes the values required at the start of a hand
     */
    private void startHand() {
//        resetGame();
        getNewHand();
        updatePlayers();
        setAllActive();

        handCounter++;
        foldCounter = 0;
        hand.setBigBlind(10);

        //Initializes the big and small blinds
        setPlayerBet(dealerButtonPos % Main.players.size(), hand.getBigBlind()/2);
        setPlayerBet((dealerButtonPos+1) % Main.players.size(), hand.getBigBlind());

        //Sets the current player to the UTG player (3 spots after the button)
        hand.setCurrentPlayer(Main.players.get((dealerButtonPos + 2) % Main.players.size()));
        currentPlayer = hand.getCurrentPlayer();
        gp.setPlayerNumber(currentPlayer.getSeatNum());
        gp.resetAllButtons();
        hand.setCurrentBetPivot(currentPlayer);
        updateSlider();
        updateButtons();

    }

    private void resetGame(){
        for(int i = 0; i < Main.players.size(); i++){
            for(int j = 0; j < 2; j++){
                gp.setLabelImage(i,j,null);
            }
        }
    }

    /**
     * Defines what will happen if the call button is pressed
     */
    private void callAction() {
        //If the current player has any current bet, reset it to before they bet before setting the new bet
        currentPlayer.setChipCount(currentPlayer.getChipCount() -
                (hand.getCurrentBetAmount() - currentPlayer.getCurrentBet()));
        hand.addPot(hand.getCurrentBetAmount() - currentPlayer.getCurrentBet());
        currentPlayer.setCurrentBet(hand.getCurrentBetAmount());

        gp.setBetLabel(currentPlayer.getSeatNum() - 1, currentPlayer.getCurrentBet());
        gp.setPot(hand.getPot());
        giveNextPlayerAction();
    }

    /**
     * Defines what happens when the player checks
     */
    private void checkAction() {
        gp.setBetLabel(currentPlayer.getSeatNum() -1);
        giveNextPlayerAction();
    }

    /**
     * Defines what happens when the player folds
     */
    private void foldAction() {
        Player tempPlayer = currentPlayer;
        currentPlayer.setActive(false);
        foldCounter++;

        //Hides the player cards
        gp.getPlayerLabel()[currentPlayer.getSeatNum()-1][0].setVisible(false);
        gp.getPlayerLabel()[currentPlayer.getSeatNum()-1][1].setVisible(false);
        if(foldCounter == Main.players.size() - 1){
            hand.setWinner(getNextPlayer());
            System.out.println("Player " + getNextPlayer().getSeatNum() + " is the winner!");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch(InterruptedException e) {
                System.out.println("got interrupted!");
            }
            startHand();
            return;
        }
        giveNextPlayerAction();
        //Fixes a bug where folding UTG would go to the flop and folding the button would reeaaally mess with things
        if(tempPlayer == hand.getCurrentBetPivot()){
            hand.setCurrentBetPivot(currentPlayer);
        }
    }

    /**
     * Defines what happens when the player bets/raises
     */
    private void betAction() {
        //If the player has a current bet, it makes sure they take it back before raising to the raise amount
        if(currentPlayer.getCurrentBet() > 0){
            currentPlayer.setChipCount(currentPlayer.getChipCount() + currentPlayer.getCurrentBet());
            hand.addPot(0 - currentPlayer.getCurrentBet());
        }

        setPlayerBet(currentPlayer.getSeatNum()-1, gp.getRaiseSlider().getValue());
        giveNextPlayerAction();
    }

    /**
     * Updates the buttons depending on certain circumstances
     */
    private void updateButtons() {

        //If there isn't a bet larger than the amount of chips the player has put out already
        if (hand.getCurrentBetAmount() == 0 || hand.getCurrentBetAmount() == currentPlayer.getCurrentBet()) {
            //Special logic for the big blind
            if (hand.getCurrentPlayer().getCurrentBet() > 0) {
                gp.setRaiseOrCheck();
            }
            gp.setCheckAction();
            gp.getRaiseSlider().setMaximum(currentPlayer.getChipCount() + currentPlayer.getCurrentBet());

            //If the current bet is more than the chips the current player has, set an all in layout
        } else if (hand.getCurrentBetAmount() >= currentPlayer.getChipCount() + currentPlayer.getCurrentBet()) {
            gp.setAllInAction();

        } else {
            gp.setBetAction(hand.getCurrentBetAmount(), currentPlayer);
            gp.getRaiseSlider().setMinimum(hand.getCurrentBetAmount() * 2);
            gp.getRaiseSlider().setMaximum(currentPlayer.getChipCount() + currentPlayer.getCurrentBet());
        }
    }

    /**
     * Adds the player cards to the player hand in the gui by finding the file correlated to the suit
     * and the card value of the deck stack pop
     */
    private void updatePlayers(){
        Image tempImage;
        ImageIcon tempIcon;
        BufferedImage image;
        Card tempC;
        for(int i = 0; i < Main.players.size(); i++){
            for(int j = 0; j < 2; j++) {
                tempC = Main.players.get(i).getCurrentHand().getCard(j);
                image = Card.getImage(tempC.getValue(), tempC.getSuit());
                tempImage = (image.getScaledInstance(65,90,Image.SCALE_DEFAULT));
                tempIcon = new ImageIcon(tempImage);
                gp.setLabelImage(i,j,tempIcon);
            }

        }
    }

    /**
     * updates the slider whenever the bet textbox is changed
     */
    private void updateSlider() {

        Runnable changeSlider = new Runnable() {
            @Override
            public void run() {
                try {
                    gp.getRaiseSlider().setValue(Integer.parseInt(gp.getBetAmount().getText()));
                } catch (NumberFormatException nbx) {
                }
            }
        };
        SwingUtilities.invokeLater(changeSlider);
    }

    /**
     * Updates the bet amount textbox based on if the raise slider changes
     */
    private void updateTextBox(){
        gp.getRaiseSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = gp.getRaiseSlider().getValue();
                gp.getBetAmount().setText(String.valueOf(value));
            }
        });
    }

    /**
     * Finds the next valid player that is active
     * @return the next active player
     */
    private Player getNextPlayer(){
        Player nextPlayer;
        for(int i = 0; i < Main.players.size() - 1; i++){
            if(Main.players.get((i+currentPlayer.getSeatNum())  % (Main.players.size())  ).getActive()){
                nextPlayer = Main.players.get((i + currentPlayer.getSeatNum()) % (Main.players.size()));
                return nextPlayer;
            }
        }
        return currentPlayer;
    }


    /**
     * Defines the values needed for when there is a new betting round
      */
    private void newBettingRound(){
        betRoundCounter++;
        gp.hideAllLabels();
        switch(betRoundCounter){
            case 1:
                updateFlopCards();
                break;
            case 2:
                updateTurnAndRiverCards(3);
                break;
            case 3:
                updateTurnAndRiverCards(4);
                break;
            default:
                break;
        }
        for(int i = 0; i < Main.players.size(); i++){
            Main.players.get(i).setCurrentBet(0);
            gp.getBetPanel()[i].setVisible(false);
        }
        gp.setPot(hand.getPot());
        hand.setCurrentPlayer(Main.players.get(dealerButtonPos - 1));
        currentPlayer = hand.getCurrentPlayer();
        hand.setCurrentBetPivot(currentPlayer);
        hand.setCurrentBetAmount(0);
        gp.getRaiseSlider().setMinimum(0);
        giveNextPlayerAction();
        hand.setCurrentBetPivot(currentPlayer);
    }

    /**
     * Updates the buttons for the next player once the current player has made an action
     */
    private void giveNextPlayerAction(){
        Player nextPlayer = getNextPlayer();
       if(nextPlayer == hand.getCurrentBetPivot()){
           newBettingRound();
        }
        else if(currentPlayer != nextPlayer) {
            currentPlayer = getNextPlayer();
            hand.setCurrentPlayer(currentPlayer);
            gp.resetAllButtons();
            updateButtons();
            gp.setPlayerNumber(currentPlayer.getSeatNum());
           gp.setPot(hand.getPot());
       }
       System.out.println("Current bet pivot is " + hand.getCurrentBetPivot().getSeatNum());
//        else if()
    }

    /**
     * Sets all of the players to be active for when a new hand is started
     */
    private void setAllActive(){
        for(Player i: Main.players){
            i.setActive(true);
        }
    }

    /**
     * Tells the GUI to draw the flop cards
     */
    private void updateFlopCards(){
        Card tempC;
        Image image;
        Image tempImage;
        ImageIcon tempIcon;
        for(int i = 0; i < 3; i++){
            tempC = hand.getDeck().pop();
            hand.addCommunityCards(tempC);
            image = Card.getImage(tempC.getValue(), tempC.getSuit());
            tempImage = (image.getScaledInstance(65,90,Image.SCALE_DEFAULT));
            tempIcon = new ImageIcon(tempImage);
            gp.setCommunityCardImage(i, tempIcon);
        }
    }

    /**
     * Draws cards for the turn or the river
     * @param num - either 3 (the turn) or 4 (the flop)
     */
    private void updateTurnAndRiverCards(int num){
        Card tempC;
        Image image;
        Image tempImage;
        ImageIcon tempIcon;
        tempC = hand.getDeck().pop();
        hand.addCommunityCards(tempC);
        image = Card.getImage(tempC.getValue(), tempC.getSuit());
        tempImage = (image.getScaledInstance(65,90,Image.SCALE_DEFAULT));
        tempIcon = new ImageIcon(tempImage);
        gp.setCommunityCardImage(num, tempIcon);
    }

    /**
     * Updates the GUI for the player's bets when they do bet
     * @param playerSeat - the seat of the current player
     * @param bet - the amount the player bet/called/raised
     */
    public void setPlayerBet(int playerSeat, int bet){
        Player tempPlayer = Main.players.get(playerSeat);
        tempPlayer.setCurrentBet(bet);
        tempPlayer.setChipCount(tempPlayer.getChipCount()-bet);
        gp.setBetLabel(playerSeat,bet);
        hand.setCurrentBetAmount(bet);
        hand.setCurrentBetPivot(currentPlayer);
        hand.addPot(bet);
        gp.setPot(hand.getPot());
    }

    /**
     * Adds all of the actionlisteners to the buttons, textbox, and slider
     */
    private void initController() {
        gp.getCallButton().addActionListener(e -> callAction());

        gp.getCheckButton().addActionListener(e -> checkAction());

        gp.getFoldButton().addActionListener(e -> foldAction());

        gp.getBetButton().addActionListener(e -> betAction());

        gp.getRaiseSlider().addChangeListener(e -> updateTextBox());

        gp.getBetAmount().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSlider();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSlider();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSlider();
            }
        });
    }
}