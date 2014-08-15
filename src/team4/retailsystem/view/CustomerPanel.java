package team4.retailsystem.view;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CustomerPanel extends JPanel{
	
	private JLabel nameLabel;
	private JLabel addressLabel;
	private JLabel eMailLabel;
	private JLabel idLabel;
	private JLabel telLabel;
	private JTextField nameTF;
	private JTextField addressTF;
	private JTextField eMailTF;
	private JTextField idTF;
	private JTextField telTF;
	private JButton submit;

	/* how to implement the ID generator?
	 * is the ID handled by the data base as an int or a string?
	 * 
	*/

	public CustomerPanel() {
		
		this.setSize(200, 400);
		 this.setVisible(true);
		 this.setLayout(new GridLayout(0,2));
		 
	//	 this.setLayout(this, EAST);
		
		 nameLabel = new JLabel("Name:");
		 addressLabel = new JLabel("Address:");
		 eMailLabel = new JLabel("Email:");
		 idLabel = new JLabel("ID:");
		 telLabel = new JLabel("Telephone Number:");
		 
		 nameTF = new JTextField(8);
		 addressTF = new JTextField(8);
		 eMailTF = new JTextField(8);
		 idTF = new JTextField(8);
		 telTF = new JTextField(8);
		 
		 submit = new JButton("Add Customer");
		 
		 //adding to components to panel
		 this.add(nameLabel);
		 this.add(nameTF);
		 this.add(addressLabel);
		 this.add(addressTF);
		 this.add(eMailLabel);
		 this.add(eMailTF);
		 this.add(idLabel);
		 this.add(idTF);
		 this.add(telLabel);
		 this.add(telTF);
		 this.add(submit);

		 idTF.setEditable(false);
		 
		 
	}
	
	
	// getters and setters
	
	public String getNameTF(){
		return nameTF.getText();
		}
	
	public String getAddressTF(){
		return addressTF.getText();
		}
	
	public String getEmailTF(){
		return eMailTF.getText();
	}
	
	public String getID(){
		return idTF.getText();
	}
	
	public String getTel(){
		return telTF.getText();
	}


	public void setNameTF(String newName) {
		this.nameTF.setText(newName);
	}

	public void setAddressTF(String newAddress) {
		this.addressTF.setText(newAddress);
	}
	
	public void setEmailTF(String newEmail) {
		this.eMailTF.setText(newEmail);
	}
	
	// auto generated iD can only be added after the submit-button is pressed???
	public void setIDTF(String newID) {
		this.idTF.setText(newID);
	}
	
	//Listeners(still figuring out)

 public void addCustomerListener(ActionListener listenForAddButton){
         
		 submit.addActionListener(listenForAddButton);
	
	
}
 //change to upload
 
	
}
