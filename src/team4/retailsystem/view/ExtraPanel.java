package team4.retailsystem.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtraPanel extends JFrame implements ActionListener{

    private JPanel panel;
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
    private JButton enterButton;
    private JButton deleteButton;
    private String text;
    
    public ExtraPanel() {
        this.setSize(300,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        panel.setLayout(gbl);
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
        panel.add(displayField,gbc);
        
        sevenButton = new JButton("7");
        sevenButton.addActionListener(this);
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridwidth = 1;
        panel.add(sevenButton,gbc);
        
        eightButton = new JButton("8");
        eightButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(eightButton,gbc);
        
        nineButton = new JButton("9");
        nineButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        panel.add(nineButton,gbc);
        
        fourButton = new JButton("4");
        fourButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(fourButton,gbc);
        
        fiveButton = new JButton("5");
        fiveButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(fiveButton,gbc);
        
        sixButton = new JButton("6");
        sixButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        panel.add(sixButton,gbc);
        
        oneButton = new JButton("1");
        oneButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(oneButton,gbc);
        
        twoButton = new JButton("2");
        twoButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(twoButton,gbc);
        
        threeButton = new JButton("3");
        threeButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        panel.add(threeButton,gbc);
        
        zeroButton = new JButton("0");
        zeroButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(zeroButton,gbc);
        
        deleteButton = new JButton("Del");
        deleteButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(deleteButton,gbc);
        
        enterButton = new JButton("Enter");
        enterButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        panel.add(enterButton,gbc);
        
        this.add(panel);
        this.setLocation((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 -100, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 -100);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource().equals(zeroButton)){
            if(text != null){
                text += 0;
            }
        }
        else if(arg0.getSource().equals(oneButton)){
            if(text != null)
                text += 1;
            else
                text = "1";
        }
        else if(arg0.getSource().equals(twoButton)){
            if(text != null)
                text += 2;
            else
                text = "2";
        }
        else if(arg0.getSource().equals(threeButton)){
            if(text != null)
                text += 3;
            else
                text = "3";
        }
        else if(arg0.getSource().equals(fourButton)){
            if(text != null)
                text += 4;
            else
                text = "4";
        }
        else if(arg0.getSource().equals(fiveButton)){
            if(text != null)
                text += 5;
            else
                text = "5";
        }
        else if(arg0.getSource().equals(sixButton)){
            if(text != null)
                text += 6;
            else
                text = "6";
        }
        else if(arg0.getSource().equals(sevenButton)){
            if(text != null)
                text += 7;
            else
                text = "7";
        }
        else if(arg0.getSource().equals(eightButton)){
            if(text != null)
                text += 8;
            else
                text = "8";
        }
        else if(arg0.getSource().equals(nineButton)){
            if(text != null)
                text += 9;
            else
                text = "9";
        }
        else if(arg0.getSource().equals(deleteButton)){
            if(text != null){
                text = text.replace(text.substring(text.length()-1 ), "");
            }
        }
        else if(arg0.getSource().equals(enterButton)){
            if(text == null){
                text = "1";
            }
         
            dispose();
        }
        displayField.setText(text);
    }
    
    public String getValue(){
        return this.displayField.getText();
    }

}
