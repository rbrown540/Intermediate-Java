/**
 * This program is designed to provide a user with the ability to log into a program.
 * Authentication is compared against hard coded credentials.
 * Developer: Richard Brown
 * Date: 21 January 2021
 * version 1.1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class HW1 extends javax.swing.JFrame {
    // PANELS
    private JPanel authenticationPanel = new JPanel();
    private JPanel validatePanel = new JPanel();
    // LABELS
    private JLabel userNameLabel = new JLabel("User Name");
    private JLabel passWordLabel = new JLabel("Password");
    // TEXT FIELDS
    private JTextField userNameTextField = new JTextField();
    private JTextField passWordTextField = new JTextField();
    // BUTTONS
    private JButton authenticateButton = new JButton("authenticate");
    private JButton resetButton = new JButton("reset");
    // USERNAME & PASSWORD

    // login counter
    private byte loginCounter = 0;

    /**
     *
     */
    public HW1() {
        landingComponents();
    }
    // all JPanels with action listeners
    private void landingComponents() {
        /**
         * creates frame used for initial authentication purpose
         */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(350, 150));
        setLocationRelativeTo(null);
        setResizable(false);

        setVisible(true);
        pack();
        createAuthenticationPanelNorth(authenticationPanel);
        add(authenticationPanel, BorderLayout.NORTH);
        createAuthenticationPanelSouth(validatePanel);
        add(validatePanel, BorderLayout.SOUTH);
        authenticateButton.addActionListener(this::attemptAuthenticate);
        resetButton.addActionListener(this::resetFields);


    }
    // top JPanel with JTextfields
    private void createAuthenticationPanelNorth(JPanel authPanel) {
        authPanel.setLayout(new GridLayout(2, 3, 10, 10));
//        authPanel.setBackground(new java.awt.Color(0, 0, 0));
        authPanel.setPreferredSize(new Dimension(350, 90));
        authPanel.setVisible(true);
        authPanel.add(userNameLabel);
        authPanel.add(userNameTextField);
        authPanel.add(passWordLabel);
        authPanel.add(passWordTextField);
    }
    // bottom JPanel with JButtons
    private void createAuthenticationPanelSouth(JPanel validatePanel) {
        validatePanel.setLayout(new FlowLayout());
        validatePanel.setPreferredSize(new Dimension(350, 40));
        validatePanel.setVisible(true);
        validatePanel.add(authenticateButton);
        validatePanel.add(resetButton);


    }
    // authenticates user based on hard coded credentials and the user input
    private boolean authenticateUser() {
        /**
         */
        String uName = userNameTextField.getText();
        String pWord = passWordTextField.getText();


        if (uName.equals("Richard-the-Great") && pWord.equals("P455_W0RD!!")) {
            loginCounter = 0;
            return true;
        } else {
            /**
             * login count set to a maximum of three attempts
             */
            loginCounter++;
            if (loginCounter > 3) {
                auditLog(uName, false);
                JOptionPane.showMessageDialog(rootPane, "What do you think you're doing, that's to many attempts");
                System.exit(1);
            }
            return false;
        }
    }
    //action event for authenticate button
    private void attemptAuthenticate (ActionEvent e){
        String uName = userNameTextField.getText();
        if (authenticateUser()) {
            auditLog(uName, true);
            JOptionPane.showMessageDialog(rootPane, "THAT IS CORRECT!");
            System.exit(0);
        } else {
            auditLog(uName, false);
            JOptionPane.showMessageDialog(rootPane, "YOU ARE WRONG!");
        }
    }
    // action event for reset button
    private void resetFields(ActionEvent e){
            userNameTextField.setText("");
            passWordTextField.setText("");
        }
    // LOG
    public String auditLog(String userNameIn, boolean attempt){
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        try {
            FileWriter writer = new FileWriter("Log.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("Username = " + userNameIn + " Attempt T/F = " + attempt + " Date = " + date + " Time = " + time);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (attempt){
            return "Congrats, your correct info was captured";
        } else {
            return "Congrats, your incorrect info was captured";
        }

    }
    // MAIN
    public static void main (String[]args){
        new HW1();
    }

}

