package src;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.NumberFormat;

public class GamePanel {

    private JFrame frame;
    private JButton callButton, checkButton, foldButton, betButton;
    private JFormattedTextField betAmount;
    private JSlider raiseSlider;
    private JLabel playerNumber;
    private JPanel[] playerPanel = new JPanel[9];
    private JLabel[][] playerLabel = new JLabel[9][2];
    private JPanel[] betPanel = new JPanel[9];
    private JLabel[] betLabel = new JLabel[9];
    private JLabel[] communityCardsLabel = new JLabel[5];
    private JPanel communityCardPanel = new JPanel();
    private JPanel potPanel;
    private JLabel potLabel;


    public GamePanel() {

        frame = new JFrame();
        JPanel buttonPanel = new JPanel();
        JPanel raisePanel = new JPanel();
        JPanel panel = new JPanel();
        raiseSlider = new JSlider();
        betButton = new JButton("Bet");
        checkButton = new JButton("Check");
        foldButton = new JButton("Fold");
        callButton = new JButton("Call");
        playerNumber = new JLabel();
        potPanel = new JPanel();
        potLabel = new JLabel();
        for(int i = 0; i < 5; i++){
            communityCardsLabel[i] = new JLabel();
        }


        buttonPanel.setBackground(Color.WHITE);
        raisePanel.setBackground(Color.WHITE);
        panel.setBackground(Color.lightGray);
        raisePanel.setBounds(775, 670, 400, 75);
        buttonPanel.setBounds(475, 600, 300, 150);
        potPanel.setBounds(570,175,60,40);
        potPanel.setBackground(Color.lightGray);
        raisePanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setLayout(new GridLayout(2, 2));
        communityCardPanel.setLayout(new FlowLayout());
        communityCardPanel.setOpaque(false);

        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMaximum(65535);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        betAmount = new JFormattedTextField(formatter);


        try {
            BufferedImage myPicture = ImageIO.read(getClass().getResourceAsStream("./resources/table.jpeg"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            panel.add(picLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }


        initPlayerAndBetPanel();

        playerPanel[0].setBounds(715,25,130,92);
        playerPanel[1].setBounds(910,100,130,92);
        playerPanel[2].setBounds(940,275,130,92);
        playerPanel[3].setBounds(830,425,130,92);
        playerPanel[4].setBounds(550,445,130,92);
        playerPanel[5].setBounds(290,445,130,92);
        playerPanel[6].setBounds(130,275,130,92);
        playerPanel[7].setBounds(150,110,130,92);
        playerPanel[8].setBounds(400,25,130,92);
        betPanel[0].setBounds(725,130,60,30);
        betPanel[1].setBounds(825,180,60,30);
        betPanel[2].setBounds(850,275,60,30);
        betPanel[3].setBounds(760,375,60,30);
        betPanel[4].setBounds(580,375,60,30);
        betPanel[5].setBounds(380,375,60,30);
        betPanel[6].setBounds(285,275,60,30);
        betPanel[7].setBounds(300,180,60,30);
        betPanel[8].setBounds(425,130,60,30);
        communityCardPanel.setBounds(430,225,350,100);


        callButton.setFocusable(false);
        checkButton.setFocusable(false);
        foldButton.setFocusable(false);
        betButton.setFocusable(false);

        for(int i = 0; i < Main.players.size(); i++){
            betLabel[i] = new JLabel();
            betLabel[i].setVisible(false);
            playerPanel[i].setOpaque(false);
            frame.add(playerPanel[i]);
            frame.add(betPanel[i]);
        }

        raiseSlider.setMajorTickSpacing(100);
        raiseSlider.setMinorTickSpacing(100);
        raiseSlider.setPaintTicks(true);
        raiseSlider.setPaintLabels(true);
        raiseSlider.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0));


        buttonPanel.add(callButton);
        buttonPanel.add(checkButton);
        buttonPanel.add(foldButton);
        buttonPanel.add(betButton);
        raisePanel.add(raiseSlider);
        raisePanel.add(betAmount);
        buttonPanel.add(playerNumber);
        potPanel.add(potLabel);

        frame.add(potPanel);
        frame.add(buttonPanel);
        frame.add(communityCardPanel);
        frame.add(raisePanel);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Poker");
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void initPlayerAndBetPanel(){
        for(int i = 0; i < 9; i++){
            playerPanel[i] = new JPanel();
            playerPanel[i].setLayout(new GridLayout());
            betPanel[i] = new JPanel();
            betPanel[i].setBackground(new Color(179, 174, 54));
            betPanel[i].setVisible(false);
            betPanel[i].setLayout(new GridBagLayout());
        }
    }

    public void frameToFront(){
        frame.setAlwaysOnTop(true);
        frame.toFront();
        frame.requestFocus();
        frame.repaint();
        frame.setAlwaysOnTop(false);
    }

    public void resetAllButtons(){
        callButton.setVisible(false);
        betButton.setVisible(false);
        checkButton.setVisible(false);
    }


    public void setCheckAction(){
        checkButton.setVisible(true);
        betButton.setText("Bet");
        betButton.setVisible(true);
    }

    public void setBetAction(int betAmount, Player currentPlayer){
        betButton.setText("Raise");
        callButton.setText("Call " + (betAmount - currentPlayer.getCurrentBet()));
        betButton.setVisible(true);
        callButton.setVisible(true);
    }

    public void setAllInAction(){
        callButton.setText("Call All In");
        callButton.setVisible(true);
    }

    public void setRaiseOrCheck(){
        betButton.setText("Raise");
        checkButton.setEnabled(true);
        betButton.setEnabled(true);
    }

    public void setCallButton(JButton callButton){
        this.callButton = callButton;
    }

    public JButton getCallButton(){
        return callButton;
    }

    public void setCheckButton(JButton checkButton){
        this.checkButton = checkButton;
    }

    public JButton getCheckButton(){
        return checkButton;
    }


    public void setFoldButton(JButton checkButton){
        this.foldButton = checkButton;
    }

    public JButton getFoldButton(){
        return foldButton;
    }

    public void setBetButton(JButton checkButton){
        this.betButton = checkButton;
    }

    public JButton getBetButton(){
        return betButton;
    }

    public void setRaiseSlider(JSlider raiseSlider){
        this.raiseSlider = raiseSlider;
    }

    public JSlider getRaiseSlider(){
        return raiseSlider;
    }

    public void setBetAmount(JFormattedTextField betAmount){
        this.betAmount = betAmount;
    }

    public JFormattedTextField getBetAmount(){
        return betAmount;
    }

    public void setPlayerNumber(int playerNumber){
        this.playerNumber.setText("Player " + playerNumber);
    }

    public void setPot(int pot){
        potLabel.setText("Pot: " + String.valueOf(pot));
    }

    public void setBetLabel(int playerNum, int bet){
        betLabel[playerNum].setText(String.valueOf(bet));
        betLabel[playerNum].setVisible(true);
        betPanel[playerNum].setVisible(true);
        betPanel[playerNum].add(betLabel[playerNum]);
    }

    public void setBetLabel(int playerNum){
        betLabel[playerNum].setText("check");
        betLabel[playerNum].setVisible(true);
        betPanel[playerNum].setVisible(true);
        betPanel[playerNum].add(betLabel[playerNum]);
    }

    public JLabel[][] getPlayerLabel(){
        return playerLabel;
    }

    public JPanel[] getBetPanel(){
        return betPanel;
    }

    public void setLabelImage(int i,int j, ImageIcon image){
        playerLabel[i][j] = new JLabel();
        playerLabel[i][j].setIcon(image);
        playerLabel[i][j].setVisible(true);
        playerPanel[i].add(playerLabel[i][j]);
    }

    public void setCommunityCardImage(int i, ImageIcon image){
        communityCardsLabel[i].setIcon(image);
        communityCardPanel.add(communityCardsLabel[i]);
    }

    public void hideAllLabels(){
        for(int i =0; i < Main.players.size(); i++){
            betLabel[i].setVisible(false);
        }
    }
}
