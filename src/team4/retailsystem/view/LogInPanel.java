package team4.retailsystem.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    
    private JLabel wellcomeLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JTextField passField;
    private JTextField invalidMsg;
    private JButton logInButton;

    public LogInPanel() {
        // TODO Auto-generated constructor stub
        this.setLayout(new GridLayout(20, 1));
        
        this.setMaximumSize(new Dimension(400, 400));
        
        JPanel emptyPanel = new JPanel();                
        this.add(emptyPanel);
        
        JPanel panel = new JPanel();
        this.add(panel);
        wellcomeLabel = new JLabel("Wellcome to rental system");
        panel.add(wellcomeLabel);

        this.add(emptyPanel);
        
        usernameLabel = new JLabel("Username ");
        this.add(usernameLabel);

        JPanel panel1 = new JPanel();
        this.add(panel1);
        panel1.setLayout(new BorderLayout());
        usernameField = new JTextField(20);
        usernameField.setBorder(null);
        panel1.add(usernameField, BorderLayout.LINE_START);
                
        passwordLabel = new JLabel("Password ");
        this.add(passwordLabel);
        
        JPanel panel2 = new JPanel();
        this.add(panel2);
        panel2.setLayout(new BorderLayout());
        passField = new JPasswordField(20);
        passField.setBorder(null);
        panel2.add(passField, BorderLayout.LINE_START);

        this.add(emptyPanel);
        
        JPanel panel3 = new JPanel();
        this.add(panel3);
        panel3.setLayout(new BorderLayout());
        logInButton = new JButton("Log In");
        panel3.add(logInButton,BorderLayout.LINE_START);
        logInButton.addActionListener(this);

        JPanel panel4 = new JPanel();
        this.add(panel4);
        panel4.setLayout(new BorderLayout());
        invalidMsg = new JTextField("Invalid Password or Username");
        invalidMsg.setForeground(Color.RED);
        invalidMsg.setBorder(null);
        invalidMsg.setEditable(false);
        invalidMsg.setVisible(false);
        panel4.add(invalidMsg,BorderLayout.LINE_START);
        
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        User user = Database.getInstance().authorizeUser(usernameField.getText(), passField.getText());
        System.out.print(user.getAuthorizationLevel());
        switch(user.getAuthorizationLevel()){
        case(-1):
            invalidMsg.setVisible(true);
            break;
        case(1):
            break;
        case(2):
            break;
        }
        this.updateUI();
        usernameField.setText(null);
        passField.setText(null);
    }

}
