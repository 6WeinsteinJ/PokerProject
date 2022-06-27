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
import java.text.NumberFormat;

public class GamePanel {

    private JFrame frame;
    private JButton callButton, checkButton, foldButton, betButton;
    private JFormattedTextField betAmount;
    private JSlider raiseSlider;
    private JLabel playerNumber;
    private JPanel p1Panel, p2Panel, p3Panel, p4Panel, p5Panel, p6Panel, p7Panel, p8Panel, p9Panel;
    private JLabel player1Chips, player2Chips, player3Chips, player4Chips, player5Chips, player6Chips, player7Chips, player8Chips, player9Chips;



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




        p1Panel = new JPanel();
        p1Panel.setBackground(Color.red);
        p1Panel.setBounds(715,55,50,50);
        frame.add(p1Panel);

        p2Panel = new JPanel();
        p2Panel.setBackground(Color.MAGENTA);
        p2Panel.setBounds(875,100,50,50);
        frame.add(p2Panel);

        p3Panel = new JPanel();
        p3Panel.setBackground(Color.yellow);
        p3Panel.setBounds(940,275,50,50);
        frame.add(p3Panel);

        p4Panel = new JPanel();
        p4Panel.setBackground(Color.orange);
        p4Panel.setBounds(830,425,50,50);
        frame.add(p4Panel);

        p5Panel = new JPanel();
        p5Panel.setBackground(Color.black);
        p5Panel.setBounds(580,445,50,50);
        frame.add(p5Panel);

        p6Panel = new JPanel();
        p6Panel.setBackground(Color.orange);
        p6Panel.setBounds(340,425,50,50);
        frame.add(p6Panel);

        p7Panel = new JPanel();
        p7Panel.setBackground(Color.yellow);
        p7Panel.setBounds(210,275,50,50);
        frame.add(p7Panel);

        p8Panel = new JPanel();
        p8Panel.setBackground(Color.MAGENTA);
        p8Panel.setBounds(230,110,50,50);
        frame.add(p8Panel);

        p9Panel = new JPanel();
        p9Panel.setBackground(Color.red);
        p9Panel.setBounds(400,55,50,50);
        frame.add(p9Panel);


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

}
