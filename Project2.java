/**
 * Developer: Richard Brown
 * Completion Date: February 09, 2020
 * This is the driver class which creates a Graphical User Interface to capture automobile data.  That data is used
 * to compute the sales tax based on which discount category the automobile belongs to. Sales tax is displayed on the
 * GUI after each capture.
 * There is also an option to display a report of all the vehicles calculated, which prints to the computer console.
 * version 16.345.21
 */
package Project2Package;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class Project2
{
    private static boolean isHybrid = false;
    private static boolean isElectric = false;
    private static boolean isOtherAutomobile = false;
    private static ArrayList<Automotive> automotiveArrayList = new ArrayList<>();

    public static void main(String[] args)
    {
        // base JFrame that all panels are packed into
        JFrame frame = new JFrame("Automotive Sales Tax Calculator");
        frame.getContentPane().setPreferredSize(new Dimension(500, 245));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /** top panel **/
        JPanel northPanel = new JPanel(new GridLayout(2, 2));
        northPanel.setBackground(Color.GRAY);
        JLabel makeAndModelLabel = new JLabel("Make and Model");
        northPanel.add(makeAndModelLabel);
        JTextField makeAndModelInput = new JTextField();
        northPanel.add(makeAndModelInput);
        JLabel purchasePrice = new JLabel("Sales Price");
        northPanel.add(purchasePrice);
        JTextField purchasePriceInput = new JTextField();
        northPanel.add(purchasePriceInput);
        frame.add(northPanel, BorderLayout.NORTH);
        /** center panel **/
        JPanel centerPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        centerPanel.setBackground(Color.GRAY);
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Automobile Type");
        centerPanel.setBorder(title);
        JTextField weightInPoundsInput = new JTextField();
        JTextField milesPerGallonInput = new JTextField();
        JRadioButton hybridAutomobileRadioButton = new JRadioButton("Hybrid");
        centerPanel.add(hybridAutomobileRadioButton, BorderLayout.NORTH);
        // makes weight field not visible (which is not required for hybrid type)
        hybridAutomobileRadioButton.addActionListener(e -> {
            isHybrid = true;
            if (isHybrid) {
                weightInPoundsInput.setVisible(false);
                milesPerGallonInput.setVisible(true);
                weightInPoundsInput.setText(null);
            }
        });
        JLabel milesPerGallon = new JLabel("Miles per Gallon");
        centerPanel.add(milesPerGallon);
        centerPanel.add(milesPerGallonInput);
        JRadioButton electricAutomobileRadioButton = new JRadioButton("Electric");
        centerPanel.add(electricAutomobileRadioButton, BorderLayout.CENTER);
        // makes MPG field not visible (which is not required for electric type)
        electricAutomobileRadioButton.addActionListener(e -> {
            isElectric = true;
            if (isElectric) {
                weightInPoundsInput.setVisible(true);
                milesPerGallonInput.setVisible(false);
                milesPerGallonInput.setText(null);
            }
        });
        JLabel weightInPounds = new JLabel(" Weight in Pounds");
        centerPanel.add(weightInPounds);
        centerPanel.add(weightInPoundsInput);
        JRadioButton otherAutomobileRadioButton = new JRadioButton("Other");
        centerPanel.add(otherAutomobileRadioButton, BorderLayout.SOUTH);
        // makes weight and MPG fields not visible (neither are required for non-hybrid or non-electric type)
        otherAutomobileRadioButton.addActionListener(e -> {
            isOtherAutomobile = true;
            if (isOtherAutomobile) {
                weightInPoundsInput.setVisible(false);
                weightInPoundsInput.setText(null);
                milesPerGallonInput.setVisible(false);
                milesPerGallonInput.setText(null);
            }
        });
        ButtonGroup radioButtonsGroup = new ButtonGroup();
        radioButtonsGroup.add(hybridAutomobileRadioButton);
        radioButtonsGroup.add(electricAutomobileRadioButton);
        radioButtonsGroup.add(otherAutomobileRadioButton);
        frame.add(centerPanel, BorderLayout.CENTER);
        /** bottom panel **/
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
        bottomPanel.setBackground(Color.GRAY);
        JButton computeSalesTax = new JButton("Compute Sales Tax");
        bottomPanel.add(computeSalesTax);
        JTextField calculatedSalesTaxDisplayed = new JTextField();
        calculatedSalesTaxDisplayed.setEditable(false);
        // the dreaded compute button. each radio button selection requires different computations.
        computeSalesTax.addActionListener(e -> {

                if (isHybrid) { // hybrid object created, displayed to JTextField, and put into ArrayList
                    Hybrid hybridAutomobileObj = new Hybrid(makeAndModelLabel.getText(), Integer.parseInt(purchasePriceInput.getText()), Integer.parseInt(milesPerGallonInput.getText()));
                    calculatedSalesTaxDisplayed.setText(String.format("%.2f", hybridAutomobileObj.salesTax()));
                    if(automotiveArrayList.size() > 4) {JOptionPane.showMessageDialog(null, "That's a lot of vehicles\nI can't handle that many");} else automotiveArrayList.add(hybridAutomobileObj);
                }else if (isElectric) { // electric object created, displayed to JTextField, and put into ArrayList
                    Electric electricAutomobileObj = new Electric(makeAndModelInput.getText(), Integer.parseInt(purchasePriceInput.getText()), Integer.parseInt(weightInPoundsInput.getText()));
                    calculatedSalesTaxDisplayed.setText(String.format("%.2f", electricAutomobileObj.salesTax()));
                    if(automotiveArrayList.size() > 4) {JOptionPane.showMessageDialog(null, "That's a lot of vehicles\nI can't handle that many");} else automotiveArrayList.add(electricAutomobileObj);
                }else if (isOtherAutomobile) { // other object created, displayed to JTextField, and put into ArrayList
                    Automotive otherAutomobileObj = new Automotive(makeAndModelInput.getText(), Integer.parseInt(purchasePriceInput.getText()));
                    calculatedSalesTaxDisplayed.setText(String.format("%.2f", otherAutomobileObj.salesTax()));
                    if(automotiveArrayList.size() > 4){JOptionPane.showMessageDialog(null, "That's a lot of vehicles\nI can't handle that many");} else automotiveArrayList.add(otherAutomobileObj);
                }else {JOptionPane.showMessageDialog(null, "This program requires that you\nenter a valid vehicle type");}
        });

        bottomPanel.add(calculatedSalesTaxDisplayed);
        // CLEAR ALL FIELDS AND RESET ALL BUTTONS
        JButton clearFields = new JButton("Clear Fields");
        clearFields.addActionListener(e -> {
            radioButtonsGroup.clearSelection();
            makeAndModelInput.setText(null);
            purchasePriceInput.setText(null);
            milesPerGallonInput.setText(null);
            weightInPoundsInput.setText(null);
            milesPerGallonInput.setVisible(true);
            weightInPoundsInput.setVisible(true);
            calculatedSalesTaxDisplayed.setText(null);
            computeSalesTax.resetKeyboardActions();
        });
        bottomPanel.add(clearFields);
        // DISPLAY TO COMPUTER CONSOLE
        JButton displayReport = new JButton("Display Report");
        displayReport.addActionListener(e -> { // displays ArrayList to the computer console
            for (int i = 0; i < automotiveArrayList.size(); i++) {
                System.out.println("\nYour Vehicle Report:\n" + automotiveArrayList.get(i));
            }});
        bottomPanel.add(displayReport);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

    } // END MAIN

} // END Project Class


