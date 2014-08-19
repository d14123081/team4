package team4.retailsystem.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Warning extends JFrame{
	
	private JLabel warningLabel;
	private JButton okButton, cancelButton;

	public Warning() {
	this.setLocation(530, 250);
	// comment test
		
	}
	
	public void blankWarning(){
		this.setSize(300, 120);
		this.setResizable(false);
		this.setVisible(true);
		warningLabel = new JLabel("     Please make sure all the fields are filled in");
		okButton = new JButton("OK");
		this.add(warningLabel);
	//	this.add(okButton);
	
	}
	
	
	
	public void EmailWarning(){
		this.setSize(300, 120);
		this.setResizable(false);

		this.setVisible(true);
		warningLabel = new JLabel("        Please provide a valid email address");
		okButton = new JButton("OK");
		this.add(warningLabel);
	//	this.add(okButton);
	}
	
	public void RemoveWarning(){
		this.setLayout(new FlowLayout());
		this.setSize(300, 120);
		this.setResizable(false);
		okButton = new JButton("Delete");
		cancelButton = new JButton("Cancel");


		this.setVisible(true);
		warningLabel = new JLabel("Are you sure you want to remove a customer");
		this.add(warningLabel);
		this.add(okButton);
		this.add(cancelButton);
		okButton.addActionListener(new BtnListener());
	}
	
	class BtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("Delete")){
				// remove customer from database
				System.out.println("Customer removed from database");
			}
			else if(e.getActionCommand().equals("Cancel")){
				// close window on cancel
				
			}
		}
		
	//	CustomerPanel.this.clearTextFields();

	}

}
