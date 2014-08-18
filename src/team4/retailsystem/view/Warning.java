package team4.retailsystem.view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Warning extends JFrame{
	
	private JLabel warningLabel;
	private JButton okButton;

	public Warning() {
	//	this.setLayout(GridLayout);
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
	
	

}
