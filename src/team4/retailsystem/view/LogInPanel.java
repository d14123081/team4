package team4.retailsystem.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.User;

public class LogInPanel extends JPanel implements ActionListener {
    
	ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
    private JLabel wellcomeLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JTextField passField;
    private JTextField invalidMsg;
    private JButton logInButton;

    public LogInPanel() {
        this.setLayout(null);
        
        wellcomeLabel = new JLabel("Welcome to Retail System");
        wellcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 19));
        wellcomeLabel.setBounds(276, 60, 281, 37);
        this.add(wellcomeLabel);
        
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        usernameLabel.setBounds(294, 151, 76, 14);
        this.add(usernameLabel);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        usernameField.setBounds(294, 176, 210, 30);
        this.add(usernameField);
        usernameField.setColumns(24);
        
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        passwordLabel.setBounds(294, 217, 70, 14);
        this.add(passwordLabel);
        
        passField = new JPasswordField();
        passField.setColumns(24);
        passField.setFont(new Font("Tahoma", Font.PLAIN, 15));
        passField.setBounds(294, 242, 210, 30);
        this.add(passField);
        
        logInButton = new JButton("Log In");
        logInButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        logInButton.setBounds(294, 283, 89, 30);
        logInButton.addActionListener(this);
        this.add(logInButton);
                
        //TODO: Remove this eventually...I'm sick of typing it in.
        usernameField.setText("admin");
        passField.setText("admin");
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        for(RetailViewListener r:listeners){
        	r.doLogin(usernameField.getText(), passField.getText());
        }
        
        usernameField.setText("");
        passField.setText("");
    }
    
    public void addListener(RetailViewListener r){
    	listeners.add(r);
    }

}
