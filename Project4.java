package wildthingeod;

// Author: Christopher Pearce
// Program: Project4.java (Part of CMIS 242 Project 4)
// Date: 6 March 2020
// Purpose: GUI, handling inputs from GUI, and GUI functionality

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;


class Project4 extends JFrame implements ActionListener {

    private JTextField transNoText = new JTextField("");
    private JTextField addressText = new JTextField("");
    private JTextField bedroomsText = new JTextField("");
    private JTextField sqFootText = new JTextField("");
    private JTextField priceText = new JTextField("");

    private JButton process = new JButton("Process");
    private String[] processSelections = {"Insert", "Delete", "Find"};
    private JComboBox processDropDown = new JComboBox(processSelections);

    private JButton changeStatus = new JButton("Change Status");
    private JComboBox<Status> statusDropDown = new JComboBox<>(Status.values());

    private TreeMap<Integer, Property> propertyList = new TreeMap<Integer, Property>();

    public Project4() {
        //Frame
        super("Real Estate Database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 250));
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        validate();
        pack();

        //Panel
        JPanel basePanel = new JPanel();
        addPanelAndComponents(basePanel);
        add(basePanel);

    }

    private JPanel addPanelAndComponents(JPanel basePanel) {
        basePanel.setLayout(new GridLayout(7, 2, 5, 5));
        basePanel.setPreferredSize(new Dimension(300, 300));
        basePanel.setVisible(true);

        JLabel transNo = new JLabel("Transaction No:");
        transNo.setHorizontalAlignment(JLabel.LEFT);
        basePanel.add(transNo);

        basePanel.add(transNoText);

        JLabel address = new JLabel("Address:");
        address.setHorizontalAlignment(JLabel.LEFT);
        basePanel.add(address);

        basePanel.add(addressText);

        JLabel bedrooms = new JLabel("Bedrooms:");
        bedrooms.setHorizontalAlignment(JLabel.LEFT);
        basePanel.add(bedrooms);

        basePanel.add(bedroomsText);

        JLabel sqFoot = new JLabel("Square Footage:");
        sqFoot.setHorizontalAlignment(JLabel.LEFT);
        basePanel.add(sqFoot);

        basePanel.add(sqFootText);

        JLabel price = new JLabel("Price:");
        price.setHorizontalAlignment(JLabel.LEFT);
        basePanel.add(price);

        basePanel.add(priceText);

        basePanel.add(process);
        process.addActionListener(this);
        basePanel.add(processDropDown);
        basePanel.add(changeStatus);
        changeStatus.addActionListener(this);
        basePanel.add(statusDropDown);

        return basePanel;
    }


    @Override // Process button and Change Status button functionality
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == process) {
            checkForIntegers();
            if (processDropDown.getSelectedItem() == "Insert") {
                if (checkTransNumbFree() == true) {
                    insertProperty();
                    JOptionPane.showMessageDialog(rootPane, "** Property Inserted  ** \n" +
                            "Transaction number: " + Integer.parseInt(transNoText.getText()) + showPropertySpecs() +
                            " was successfully added to the database");
                }

            } else if (processDropDown.getSelectedItem() == "Delete") {
                if (checkTransNumbExist() == true) {
                    JOptionPane.showMessageDialog(rootPane, "** Property Deleted ** \n" +
                            "Transaction number: " + Integer.parseInt(transNoText.getText()) + showPropertySpecs() +
                            " was successfully deleted from the database");
                    propertyList.remove(Integer.parseInt(transNoText.getText()));
                }

            } else if (processDropDown.getSelectedItem() == "Find") {
                if (checkTransNumbExist() == true) {
                    JOptionPane.showMessageDialog(rootPane, "** Property Found ** \n" +
                            "Transaction number: " + Integer.parseInt(transNoText.getText()) + showPropertySpecs() +
                            " was found.");
                }
            }
        }

        if (e.getSource() == changeStatus) {

            if (statusDropDown.getSelectedItem() == Status.FOR_SALE) {
                shiftStatus(Status.FOR_SALE);
                JOptionPane.showMessageDialog(rootPane, "The Status for transaction number: " +
                        Integer.parseInt(transNoText.getText()) + showPropertySpecs() +
                        " was successfully changed to FOR_SALE");

            } else if (statusDropDown.getSelectedItem() == Status.UNDER_CONTRACT) {
                shiftStatus(Status.UNDER_CONTRACT);
                JOptionPane.showMessageDialog(rootPane, "The Status for transaction number: " +
                        Integer.parseInt(transNoText.getText()) + showPropertySpecs() +
                        " was successfully changed to UNDER_CONTRACT");

            } else if (statusDropDown.getSelectedItem() == Status.SOLD) {
                shiftStatus(Status.SOLD);
                JOptionPane.showMessageDialog(rootPane, "The Status for transaction number: " +
                        Integer.parseInt(transNoText.getText()) + showPropertySpecs() +
                        " was successfully changed to SOLD");
            }
        }
    }

        public boolean checkTransNumbFree () {//Checks that transaction number is not used already
            int tn = Integer.parseInt(transNoText.getText());
            if (propertyList.containsKey(tn)) {
                JOptionPane.showMessageDialog(rootPane, "Transaction number " +
                        Integer.parseInt(transNoText.getText()) + " has already been used.\n" +
                        "Please choose another.");
                return false;
            } else {

                return true;
            }
        }

        public void insertProperty () {
            //Get info for object
            String address = addressText.getText();
            int bedrooms = (Integer.parseInt(bedroomsText.getText()));
            int squareFootage = (Integer.parseInt(sqFootText.getText()));
            int price = (Integer.parseInt(priceText.getText()));
            Status status = (Status) (statusDropDown.getSelectedItem());
            //Construct property object
            Property addProperty = new Property(address, bedrooms, squareFootage, price, status);
            //Insert into the TreeMap
            propertyList.put((Integer.parseInt(transNoText.getText())), addProperty);
        }

        public boolean checkTransNumbExist () {
            int tn = Integer.parseInt(transNoText.getText());
            if (propertyList.containsKey(tn)) {
                return true;
            } else {

                JOptionPane.showMessageDialog(rootPane, "Transaction number: " +
                        Integer.parseInt(transNoText.getText()) + " is not in the data base.");
                return false;
            }
        }

        public void shiftStatus (Status s){//removes, calls changeState, and replaces Property objects when called
            Property propertyChangingState = propertyList.get(Integer.parseInt(transNoText.getText()));
            propertyChangingState.changeState(s);
            propertyList.replace((Integer.parseInt(transNoText.getText())), propertyChangingState);
        }

        public void checkForIntegers () {//Checks integer is the only input for JTextfields that can only use integers
            try {
                propertyList.get(Integer.parseInt(transNoText.getText()));
                propertyList.get(Integer.parseInt(bedroomsText.getText()));
                propertyList.get(Integer.parseInt(sqFootText.getText()));
                propertyList.get(Integer.parseInt(priceText.getText()));

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Please check that ALL entries in\n" +
                        "transaction number, bedrooms, square footage and price\n" +
                        "are whole numbers only and then reprocess your entry\n" +
                        "Thank you!");
            }
        }

        public String showPropertySpecs() {
            Property foundProperty = propertyList.get(Integer.parseInt(transNoText.getText()));
            String propertySpecs = foundProperty.toString();
            return propertySpecs;
        }

        public static void main (String[]args){
            new Project4();
        }
    }

