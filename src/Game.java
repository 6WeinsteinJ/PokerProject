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


    public Game(GamePanel gp) {
        this.gp = gp;
        gp.frameToFront();
        initController();
        gameLoop();
    }


    private void getNewHand() {
        hand = new Hand();
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


    private void callAction() {
        System.out.println("You called");
        currentPlayer.setChipCount(currentPlayer.getChipCount() -
                (hand.getCurrentBetAmount() - currentPlayer.getCurrentBet()));
        giveNextPlayerAction();
    }

    private void checkAction() {
        System.out.println("You checked!");
        giveNextPlayerAction();
    }

    private void foldAction() {
        currentPlayer.setActive(false);
        System.out.println("You Folded!");
        giveNextPlayerAction();
    }

    private void betAction() {
        System.out.println("You bet!");
        if(currentPlayer.getCurrentBet() > 0){
            currentPlayer.setChipCount(currentPlayer.getChipCount() + currentPlayer.getCurrentBet());
        }
        currentPlayer.setCurrentBet(gp.getRaiseSlider().getValue());
        hand.setCurrentBetAmount(gp.getRaiseSlider().getValue());
        hand.setCurrentBetPivot(currentPlayer);
        giveNextPlayerAction();
    }

    private void updateButtons() {
        if (hand.getCurrentBetAmount() == 0) {
            gp.setCheckAction();
            gp.getRaiseSlider().setMaximum(currentPlayer.getChipCount());
        } else if (hand.getCurrentBetAmount() >= currentPlayer.getChipCount()) {
            gp.setAllInAction();
        } else {
            gp.setBetAction(hand.getCurrentBetAmount());
            gp.getRaiseSlider().setMinimum(hand.getCurrentBetAmount() * 2);
            gp.getRaiseSlider().setMaximum(currentPlayer.getChipCount());
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
            if(Main.players.get(    (i+currentPlayer.getSeatNum())  % (Main.players.size())  ).getActive()){
                nextPlayer = Main.players.get((i + currentPlayer.getSeatNum()) % (Main.players.size()));
                return nextPlayer;
            }
        }
        return currentPlayer;
    }

    private void giveNextPlayerAction(){
        Player nextPlayer = getNextPlayer();
//        if(currentPlayer == hand.getCurrentBetPivot()){
//            newBettingRound();
//        }
        if(currentPlayer != nextPlayer) {
            currentPlayer = getNextPlayer();
            hand.setCurrentPlayer(currentPlayer);
            System.out.println("NextPlayer seat num is " + currentPlayer.getSeatNum());
            gp.resetAllButtons();
            updateButtons();
            gp.setPlayerNumber(currentPlayer.getSeatNum());
        }
//        else if()
    }

    private void setAllActive(){
        for(Player i: Main.players){
            i.setActive(true);
        }
    }

    private void newBettingRound(){

    }

    public void gameLoop() {
        getNewHand();
        updatePlayers();
        setAllActive();
        hand.setBigBlind(10);
        Main.players.get((dealerButtonPos + 1) % Main.players.size()).setCurrentBet(hand.getBigBlind());
        Main.players.get((dealerButtonPos + 2) % Main.players.size()).setCurrentBet(hand.getBigBlind());
        hand.setCurrentBetAmount(hand.getBigBlind());
        hand.setCurrentPlayer(Main.players.get((dealerButtonPos + 2) % Main.players.size()));
        currentPlayer = hand.getCurrentPlayer();
        gp.setPlayerNumber(currentPlayer.getSeatNum());
        gp.resetAllButtons();
        hand.setCurrentBetPivot(currentPlayer);
        updateSlider();
        updateButtons();

    }
}