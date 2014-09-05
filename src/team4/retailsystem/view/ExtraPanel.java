package team4.retailsystem.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ExtraPanel extends JPanel implements ActionListener{

    private JTextField displayField;
    private JButton nineButton;
    private JButton eightButton;
    private JButton sevenButton;
    private JButton sixButton;
    private JButton fiveButton;
    private JButton fourButton;
    private JButton threeButton;
    private JButton twoButton;
    private JButton oneButton;
    private JButton zeroButton;
    public JButton enterButton;
    private JButton deleteButton;
    private String text = "";
    
    public ExtraPanel() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        
        displayField = new JTextField("0");
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        gbc.ipady = 10;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 3;
        this.add(displayField,gbc);
        
        sevenButton = new JButton("7");
        sevenButton.addActionListener(this);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        this.add(sevenButton,gbc);
        
        eightButton = new JButton("8");
        eightButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        this.add(eightButton,gbc);
        
        nineButton = new JButton("9");
        nineButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        this.add(nineButton,gbc);
        
        fourButton = new JButton("4");
        fourButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        this.add(fourButton,gbc);
        
        fiveButton = new JButton("5");
        fiveButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        this.add(fiveButton,gbc);
        
        sixButton = new JButton("6");
        sixButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        this.add(sixButton,gbc);
        
        oneButton = new JButton("1");
        oneButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        this.add(oneButton,gbc);
        
        twoButton = new JButton("2");
        twoButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        this.add(twoButton,gbc);
        
        threeButton = new JButton("3");
        threeButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        this.add(threeButton,gbc);
        
        zeroButton = new JButton("0");
        zeroButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        this.add(zeroButton,gbc);
        
        deleteButton = new JButton("Del");
        deleteButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        this.add(deleteButton,gbc);
        
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource().equals(zeroButton)){
            if(text.equals(null) || text.equals("0") || text.equals("")){
                System.out.println(text);
                text = "0";
            }
            else 
                text += 0;
        }
        else if(arg0.getSource().equals(oneButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 1;
            else
                text = "1";
        }
        else if(arg0.getSource().equals(twoButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 2;
            else
                text = "2";
        }
        else if(arg0.getSource().equals(threeButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 3;
            else
                text = "3";
        }
        else if(arg0.getSource().equals(fourButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 4;
            else
                text = "4";
        }
        else if(arg0.getSource().equals(fiveButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 5;
            else
                text = "5";
        }
        else if(arg0.getSource().equals(sixButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 6;
            else
                text = "6";
        }
        else if(arg0.getSource().equals(sevenButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 7;
            else
                text = "7";
        }
        else if(arg0.getSource().equals(eightButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 8;
            else
                text = "8";
        }
        else if(arg0.getSource().equals(nineButton)){
            if(!(text.equals(null) || text.equals("0") || text.equals("")))
                text += 9;
            else
                text = "9";
        }
        else if(arg0.getSource().equals(deleteButton)){
            if(!(text.equals(null) || text.equals("")) && text.length() > 1){
                System.out.println(text.length());
                text = text.substring(0,text.length()-1 );
            }
            else
                text = "0";
        }
        displayField.setText(text);
    }
    
    public int getValue(){
        int value = Integer.parseInt(this.displayField.getText());
        if(value == 0){
            value = 1;
        }
        return value;
    }

}
