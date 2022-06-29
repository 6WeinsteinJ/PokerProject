package src;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;

//General class for keeping track of the game across hands
public class Game {
    static int dealerButtonPos = 1;
    static int handCounter = 0;
    private Hand hand;
    private GamePanel gp;
    private Player currentPlayer;
    private int betRoundCounter = 0;


    public Game(GamePanel gp) {
        this.gp = gp;
        gp.frameToFront();
        initController();
        startHand();
    }


    private void getNewHand() {
        hand = new Hand();
    }


    public void startHand() {
        getNewHand();
        updatePlayers();
        setAllActive();
        hand.setBigBlind(10);

        //Initializes the big and small blinds
        setPlayerBet(dealerButtonPos % Main.players.size(), hand.getBigBlind()/2);
        setPlayerBet((dealerButtonPos+1) % Main.players.size(), hand.getBigBlind());


        hand.setCurrentPlayer(Main.players.get((dealerButtonPos + 2) % Main.players.size()));
        currentPlayer = hand.getCurrentPlayer();
        gp.setPlayerNumber(currentPlayer.getSeatNum());
        gp.resetAllButtons();
        hand.setCurrentBetPivot(currentPlayer);
        updateSlider();
        updateButtons();

    }




    private void callAction() {
        System.out.println("You called");
        currentPlayer.setChipCount(currentPlayer.getChipCount() -
                (hand.getCurrentBetAmount() - currentPlayer.getCurrentBet()));
        hand.addPot(hand.getCurrentBetAmount() - currentPlayer.getCurrentBet());
        currentPlayer.setCurrentBet(hand.getCurrentBetAmount());
        gp.setBetLabel(currentPlayer.getSeatNum() - 1, currentPlayer.getCurrentBet());
        gp.setPot(hand.getPot());
        giveNextPlayerAction();
    }

    private void checkAction() {
        System.out.println("You checked!");
        gp.setBetLabel(currentPlayer.getSeatNum() -1);
        giveNextPlayerAction();
    }

    private void foldAction() {
        currentPlayer.setActive(false);
        System.out.println("You Folded!");
        gp.getPlayerLabel()[currentPlayer.getSeatNum()-1][0].setVisible(false);
        gp.getPlayerLabel()[currentPlayer.getSeatNum()-1][1].setVisible(false);
        giveNextPlayerAction();
    }

    private void betAction() {
        System.out.println("You bet!");
        if(currentPlayer.getCurrentBet() > 0){
            currentPlayer.setChipCount(currentPlayer.getChipCount() + currentPlayer.getCurrentBet());
        }

        setPlayerBet(currentPlayer.getSeatNum()-1, gp.getRaiseSlider().getValue());
        giveNextPlayerAction();
    }

    private void updateButtons() {
        if (hand.getCurrentBetAmount() == 0 || hand.getCurrentBetAmount() == currentPlayer.getCurrentBet()) {
            //Special logic for the big blind
            if (hand.getCurrentPlayer().getCurrentBet() > 0) {
                gp.setRaiseOrCheck();
            }
            gp.setCheckAction();
            gp.getRaiseSlider().setMaximum(currentPlayer.getChipCount() + currentPlayer.getCurrentBet());
        } else if (hand.getCurrentBetAmount() >= currentPlayer.getChipCount() + currentPlayer.getCurrentBet()) {
            gp.setAllInAction();
        } else {
            gp.setBetAction(hand.getCurrentBetAmount(), currentPlayer);
            gp.getRaiseSlider().setMinimum(hand.getCurrentBetAmount() * 2);
            gp.getRaiseSlider().setMaximum(currentPlayer.getChipCount() + currentPlayer.getCurrentBet());
        }
        System.out.println("Buttons updated!");
    }

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
    private void updateTextBox(){
        gp.getRaiseSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int value = gp.getRaiseSlider().getValue();
                gp.getBetAmount().setText(String.valueOf(value));
            }
        });
    }

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

    private void giveNextPlayerAction(){
        Player nextPlayer = getNextPlayer();
       if(nextPlayer == hand.getCurrentBetPivot()){
           newBettingRound();
        }
        else if(currentPlayer != nextPlayer) {
            currentPlayer = getNextPlayer();
            hand.setCurrentPlayer(currentPlayer);
            System.out.println("NextPlayer seat num is " + currentPlayer.getSeatNum());
            gp.resetAllButtons();
            updateButtons();
            gp.setPlayerNumber(currentPlayer.getSeatNum());
           gp.setPot(hand.getPot());
       }
//        else if()
    }


    private void setAllActive(){
        for(Player i: Main.players){
            i.setActive(true);
        }
    }

    //TODO make this a switch statement
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
        hand.setCurrentBetPivot(currentPlayer);
        hand.setCurrentBetAmount(0);
        gp.getRaiseSlider().setMinimum(0);
        giveNextPlayerAction();

    }

    private void updateFlopCards(){
        Card tempC;
        Image image;
        Image tempImage;
        ImageIcon tempIcon;
        for(int i = 0; i < 3; i++){
            tempC = hand.getDeck().pop();
            image = Card.getImage(tempC.getValue(), tempC.getSuit());
            tempImage = (image.getScaledInstance(65,90,Image.SCALE_DEFAULT));
            tempIcon = new ImageIcon(tempImage);
            gp.setCommunityCardImage(i, tempIcon);
        }
    }

    private void updateTurnAndRiverCards(int num){
        Card tempC;
        Image image;
        Image tempImage;
        ImageIcon tempIcon;
        tempC = hand.getDeck().pop();
        image = Card.getImage(tempC.getValue(), tempC.getSuit());
        tempImage = (image.getScaledInstance(65,90,Image.SCALE_DEFAULT));
        tempIcon = new ImageIcon(tempImage);
        gp.setCommunityCardImage(num, tempIcon);
    }

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