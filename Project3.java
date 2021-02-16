package wildthingeod;

// Author: Christopher Pearce
// Program: Project3.java (Part of CMIS 242 Project 3
// Date: 23 February 2020
// Purpose: GUI development and GUI functionality


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Project3 extends JFrame implements ActionListener{

    //LeftPanel comboboxes, textfields, and labels
    JLabel shapeTypeLabel = new JLabel("Shape Type");
    String[] shapeTypeList = { "", "Rectangle", "Oval" };
    JComboBox shapeTypeDropDown= new JComboBox(shapeTypeList);

    JLabel fillTypeLabel = new JLabel("Fill Type");
    String[] fillTypeList = {"", "Hollow", "Solid" };
    JComboBox fillTypeDropDown= new JComboBox(fillTypeList);

    JLabel colorLabel = new JLabel("Color");
    static String[] colorList = {"", "BLACK", "RED", "ORANGE", "YELLOW", "GREEN", "BLUE", "MAGENTA" };
    static JComboBox colorDropDown = new JComboBox(colorList);

    JLabel widthLabel = new JLabel("Width");
    JTextField widthText = new JTextField();

    JLabel heightLabel = new JLabel("Height");
    JTextField  heightText = new JTextField ();

    JLabel xCoordinateLabel = new JLabel("x coordinate");
    JTextField xCoordinateText = new JTextField();

    JLabel yCoordinateLabel = new JLabel("y coordinate");
    JTextField yCoordinateText = new JTextField();

    JButton drawPilgrim = new JButton("Draw");

   // Drawing drawing = new Drawing();
    Drawing rightPane = new Drawing();

    public Project3(){
        super("Geometric Drawing");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(550, 300));
        setLocationRelativeTo(null);
        setResizable(true);
        validate();
        pack();

        JPanel basePane = new JPanel();
        JPanel leftAndRightPane = new JPanel();
        JPanel leftPane = new JPanel();

        JPanel bottomPane = new JPanel();


        add(basePane);
        basePane.setLayout(new BoxLayout(basePane,BoxLayout.PAGE_AXIS));


        basePane.setPreferredSize(new Dimension(600, 325));

        basePane.add(leftAndRightPane);
        basePane.add(bottomPane);

        leftAndRightPane.add(leftPane);
        leftAndRightPane.add(rightPane);

        constructRightPanel(rightPane);
        constructBottomPanel(bottomPane);
        constructLeftPanel(leftPane);

        setVisible(true);
        componentRepaint();
    }
private JPanel constructLeftPanel(JPanel leftPane) {
    leftPane.setLayout(new GridLayout(7, 2, 5, 5));
    leftPane.setPreferredSize(new Dimension(250, 200));

    shapeTypeLabel.setHorizontalAlignment(JLabel.LEFT);
    leftPane.add(shapeTypeLabel);

    leftPane.add(shapeTypeDropDown);

    fillTypeLabel.setHorizontalAlignment(JLabel.LEFT);
    leftPane.add(fillTypeLabel);

    leftPane.add(fillTypeDropDown);

    colorLabel.setHorizontalAlignment(JLabel.LEFT);
    leftPane.add(colorLabel);

    leftPane.add(colorDropDown);

    widthLabel.setHorizontalAlignment(JLabel.LEFT);
    leftPane.add(widthLabel);

    leftPane.add(widthText);

    heightLabel.setHorizontalAlignment(JLabel.LEFT);
    leftPane.add(heightLabel);

    leftPane.add(heightText);

    xCoordinateLabel.setHorizontalAlignment(JLabel.LEFT);
    leftPane.add(xCoordinateLabel);

    leftPane.add(xCoordinateText);

    yCoordinateLabel.setHorizontalAlignment(JLabel.LEFT);
    leftPane.add(yCoordinateLabel);

    leftPane.add(yCoordinateText);
    leftPane.setVisible(true);

    return leftPane;
}

   private JPanel constructRightPanel(JPanel rightPane) {
       rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
       rightPane.setPreferredSize(new Dimension(225, 225));
       TitledBorder title = BorderFactory.createTitledBorder("Shape Drawing");
       rightPane.setBorder(title);
       setVisible(true);

       return rightPane;
   }

    private JPanel constructBottomPanel(JPanel bottomPane){
        bottomPane.setLayout(new FlowLayout());
        drawPilgrim.addActionListener(this);
        bottomPane.add(drawPilgrim);

        return bottomPane;
    }

    public void componentRepaint(){
        //shapeTypeLabel.repaint();
        shapeTypeLabel.repaint();
        shapeTypeDropDown.repaint();
        fillTypeLabel.repaint();
        fillTypeDropDown.repaint();
        colorLabel.repaint();
        colorDropDown.repaint();
        widthLabel.repaint();
        widthText.repaint();
        heightLabel.repaint();
        heightText.repaint();
        xCoordinateLabel.repaint();
        xCoordinateText.repaint();
        yCoordinateLabel.repaint();
        yCoordinateText.repaint();
        drawPilgrim.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object shape = shapeTypeDropDown.getSelectedItem();

        if (shape == "Rectangle") {

            Rectangular rectangleInput = new Rectangular(new Rectangle(Integer.parseInt(xCoordinateText.getText()),
                    Integer.parseInt(yCoordinateText.getText()), Integer.parseInt(widthText.getText()), Integer.parseInt(heightText.getText())),
                    Color.getColor(String.valueOf(colorDropDown.getSelectedItem())), String.valueOf(fillTypeDropDown.getSelectedItem()));
            try {

                rightPane.drawShape(rectangleInput);


            } catch (OutsideBounds ex) {
                JOptionPane.showMessageDialog(rootPane, "Error this shape will not fit in the shape area");

            }

        } else if (shape == "Oval") {

            Oval ovalInput = new Oval(new Rectangle(Integer.parseInt(xCoordinateText.getText()), Integer.parseInt(yCoordinateText.getText()),
                    Integer.parseInt(widthText.getText()), Integer.parseInt(heightText.getText())),
                    Color.getColor(String.valueOf(colorDropDown.getSelectedItem())), String.valueOf(fillTypeDropDown.getSelectedItem()));
            try {

                rightPane.drawShape(ovalInput);

            } catch (OutsideBounds ex) {
                JOptionPane.showMessageDialog(rootPane, "Error this shape will not fit in the shape area");
            }
        }
    }

    public static void main(String[] args) {

        new Project3();
    }


}
