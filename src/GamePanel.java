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

        for(int i = 0; i < playerLabel.length; i++){
            for(int j = 0; j < playerLabel[0].length; j++){
                playerLabel[i][j] = new JLabel();
            }
        }


        buttonPanel.setBackground(Color.WHITE);
        raisePanel.setBackground(Color.WHITE);
        raisePanel.setBounds(775, 670, 400, 75);
        buttonPanel.setBounds(475, 600, 300, 150);
        raisePanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setLayout(new GridLayout(2, 2));

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

        playerPanel[0] = new JPanel();
        playerPanel[0].setBounds(715,25,130,100);
        playerPanel[0].setLayout(new GridLayout());
        frame.add(playerPanel[0]);

        playerPanel[1] = new JPanel();
        playerPanel[1].setBounds(875,100,130,100);
        playerPanel[1].setLayout(new GridLayout());
        frame.add(playerPanel[1]);

        playerPanel[2] = new JPanel();
        playerPanel[2].setBounds(940,275,130,100);
        playerPanel[2].setLayout(new GridLayout());
        frame.add(playerPanel[2]);

        playerPanel[3] = new JPanel();
        playerPanel[3].setBounds(830,425,130,100);
        playerPanel[3].setLayout(new GridLayout());
        frame.add(playerPanel[3]);

        playerPanel[4] = new JPanel();
        playerPanel[4].setBounds(580,445,130,100);
        playerPanel[4].setLayout(new GridLayout());
        frame.add(playerPanel[4]);

        playerPanel[5] = new JPanel();
        playerPanel[5].setBounds(340,425,130,100);
        playerPanel[5].setLayout(new GridLayout());
        frame.add(playerPanel[5]);

        playerPanel[6] = new JPanel();
        playerPanel[6].setBounds(210,275,130,100);
        playerPanel[6].setLayout(new GridLayout());
        frame.add(playerPanel[6]);

        playerPanel[7] = new JPanel();
        playerPanel[7].setBounds(230,110,130,100);
        playerPanel[7].setLayout(new GridLayout());
        frame.add(playerPanel[7]);

        playerPanel[8] = new JPanel();
        playerPanel[8].setBounds(400,25,130,100);
        playerPanel[8].setLayout(new GridLayout());
        frame.add(playerPanel[8]);

        callButton.setFocusable(false);
        checkButton.setFocusable(false);
        foldButton.setFocusable(false);
        betButton.setFocusable(false);



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
        frame.add(buttonPanel);
        frame.add(raisePanel);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Poker");
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void frameToFront(){
        frame.setAlwaysOnTop(true);
        frame.toFront();
        frame.requestFocus();
        frame.repaint();
        frame.setAlwaysOnTop(false);
    }

    public void resetAllButtons(){
        callButton.setEnabled(false);
        betButton.setEnabled(false);
        checkButton.setEnabled(false);
    }


    public void setCheckAction(){
        checkButton.setEnabled(true);
        betButton.setText("Bet");
        betButton.setEnabled(true);
    }

    public void setBetAction(int betAmount){
        betButton.setText("Raise");
        callButton.setText("Call " + betAmount);
        betButton.setEnabled(true);
        callButton.setEnabled(true);
    }

    public void setAllInAction(){
        callButton.setText("Call All In");
        callButton.setEnabled(true);
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

    public JPanel[] getPlayerPanel(){
        return playerPanel;
    }

    public void setLabelImage(int i,int j, ImageIcon image){
        playerLabel[i][j].setIcon(image);
        playerPanel[i].add(playerLabel[i][j]);
    }
}
