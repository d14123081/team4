package team4.retailsystem.view;



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.omg.CORBA.SetOverrideType;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;


public class CustomerPanel extends JPanel{
	
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
	private static final String NAME_FIELD = "name";
	private static final String NAME_COMBO = "combo";
	
	private JPanel namePanel;
	//private JPanel nameFieldPanel;
	//private JPanel nameComboPanel;
	
	JComboBox customerComboBox;
	
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
	private JButton submit, back, remove, edit;

	/* how to implement the ID generator?
	 * is the ID handled by the data base as an int or a string?
	 * 
	*/

	public CustomerPanel() {
		
		this.setSize(200, 400);
		 this.setVisible(true);
		 this.setLayout(new GridLayout(0,2,10, 30));
		 
	//	 this.setLayout(this, EAST);
		
		 namePanel = new JPanel();
		 namePanel.setLayout(new CardLayout());
		 customerComboBox = new JComboBox();
		 customerComboBox.addActionListener(new ComboBoxListener());
		 
		 nameLabel = new JLabel("Name:");
		 nameLabel.setHorizontalAlignment(PROPERTIES);
		 addressLabel = new JLabel("Address:");
		 addressLabel.setHorizontalAlignment(PROPERTIES);
		 
		 eMailLabel = new JLabel("Email:");
		 eMailLabel.setHorizontalAlignment(PROPERTIES);

		 idLabel = new JLabel("ID:");
		 idLabel.setHorizontalAlignment(PROPERTIES);

		 telLabel = new JLabel("Telephone Number:");
		 telLabel.setHorizontalAlignment(PROPERTIES);

		 
		 nameTF = new JTextField(8);
		 setNameTF("'Search here'");
		 addressTF = new JTextField(8);
		 eMailTF = new JTextField(8);
		 idTF = new JTextField(8);
		 telTF = new JTextField(8);
		 
		 //nameTF.addActionListener(new TextFieldListener()); 

		 submit = new JButton("Create new Customer");
		 submit.addActionListener(new CRUDListener());
		 
		 remove = new JButton("Remove Customer");
		 remove.addActionListener(new CRUDListener());
		 

		 edit = new JButton("Edit Customer");
		 edit.addActionListener(new CRUDListener());
		 
		 back = new JButton("Clear fields");
		 back.addActionListener(new CRUDListener());
		 
		 //adding to components to panel
		 namePanel.add(customerComboBox,NAME_COMBO);
		 namePanel.add(nameTF,NAME_FIELD);
		 
		 this.add(nameLabel);
		 this.add(namePanel);
		 this.add(addressLabel);
		 this.add(addressTF);
		 this.add(eMailLabel);
		 this.add(eMailTF);
		 this.add(idLabel);
		 this.add(idTF);
		 this.add(telLabel);
		 this.add(telTF);
		 this.add(submit);
		 this.add(remove);
		 this.add(edit);
		 this.add(back);

		 idTF.setEditable(false);
		 eMailTF.setEditable(false);
		 addressTF.setEditable(false);
		 telTF.setEditable(false);
		 
	}
	
	public void addListener(RetailViewListener r){
		listeners.add(r);
	}
	
	// **********getters and setters***************
	
	public String getNameTF(){
		return nameTF.getText();
		}
	
	public String getAddressTF(){
		return addressTF.getText();
		}
	
	public String getEmailTF(){
		return eMailTF.getText();
	}
	
	public String getIDTF(){
		return idTF.getText();
	}
	
	public String getTelTF(){
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
	
	
	private class ComboBoxListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Customer customer = ((Customer)customerComboBox.getSelectedItem());
			addressTF.setText(customer.getAddress());
			eMailTF.setText(customer.getEmail());
			idTF.setText(""+customer.getID());
			telTF.setText(customer.getTelephoneNumber());
		}
		
	}
	
	// *********listeners and methods************

	public class TextFieldListener implements ActionListener{
	    public void actionPerformed(ActionEvent e){
	    	
	    	ArrayList<Customer> customers = Database.getInstance().getCustomers();
	    	for(Customer customer : customers){
	    		if(customer.getName().equals(nameTF.getText())){
	    			addressTF.setText(customer.getAddress());
	    			eMailTF.setText(customer.getEmail());
	    			idTF.setText(""+customer.getID());
	    			telTF.setText(customer.getTelephoneNumber());
	    		}
	    	}
	    	System.out.println("put customer information in textfields");
	    	
	    }
	    
	}
	
		class CRUDListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getActionCommand().equals("Clear fields")){
				clearTextFields();
			}
			if(e.getActionCommand().equals("Create new Customer"))
				newCustomerMode();
			 
			 // save new customer if conditions are met
	    	if(e.getActionCommand().equals("Save new Customer")){
	    		if(getNameTF().isEmpty()||getAddressTF().isEmpty()
	    	    	||getEmailTF().isEmpty()||getTelTF().isEmpty()){
	    	    		
	    				// Checks for blank fields
	    	    		Warning w = new Warning();
	    	    		w.blankWarning();
	    	    		}
	    	    	
	    		   	// checks for invalid Email construction
	    	    	else if(getEmailTF().contains("@")&& getEmailTF().contains(".")){
	    	    		
	    	    		/*Customer customer = new Customer(getNameTF(), getTelTF(), getAddressTF(), getEmailTF() );
	    	    		Database db = Database.getInstance();
	    	    		db.addCustomer(customer);*/
	    	    		
	    	    		//inform RetailViewListeners of the event, pass the information.
					 	for(RetailViewListener r:listeners){
					 		r.clickSaveNewCustomer(getNameTF(), getAddressTF(), getEmailTF(), getTelTF());
					 	}
	    	    		
	    	    		//add to customer array/database when functionality available
	    	    		System.out.println("Customer added to database");
	    	    		clearTextFields();
	    	    		setToViewMode();

		    	    		
		    	    	}
		    	    	else{
		    	    		
		    	    		Warning w = new Warning();
		    	    		w.EmailWarning();
		    	      	    		
	    	  } 

	    }
			// when back button pressed
			if(e.getActionCommand().equals("back")){
				setToViewMode();
			}
			
			// when remove button pressed
			if(e.getActionCommand().equals("Remove Customer")){
				
				//inform listeners of the delete customer event
				for(RetailViewListener r:listeners){
			 		r.clickDeleteCustomer(Integer.parseInt(getIDTF()));
			 	}
				
				//Warning w = new Warning();
				//w.RemoveWarning();
				
				
			}
			
			//when edit button pressed
			if(e.getActionCommand().equals("Edit Customer")){
				 editCustomerMode();
				
			}
			
				if(e.getActionCommand().equals("Save Changes")){
					 if(getNameTF().isEmpty()||getAddressTF().isEmpty()
			    		       	||getEmailTF().isEmpty()||getTelTF().isEmpty()){
			    	    		
			    	    		Warning w = new Warning();
			    	    		w.blankWarning();
			    	    	}
					
				
					 	else if(getEmailTF().contains("@")&& getEmailTF().contains(".")){
					 
					 	//update database, return to view mode
					 		
					 	//inform RetailViewListeners of the event, pass the information.
					 	for(RetailViewListener r:listeners){
					 		r.clickUpdateCustomer(Integer.parseInt(getIDTF()), getNameTF(), getAddressTF(), getEmailTF(), getTelTF());
					 	}
					 		
	    	    		System.out.println("customer details saved to data base");
	    	    		setToViewMode();
					 
						}
	    	    		else{
	    	    			Warning w = new Warning();
		    	    		w.EmailWarning();
	    	    		}
			}	    	
    	  }
		}	
	
	
	//default panel view 
	public void setToViewMode(){
		
		//switch the text field for the combobox
 		((CardLayout)(namePanel.getLayout())).show(namePanel, NAME_COMBO);
 		if(customerComboBox.getItemCount()>0){
			customerComboBox.setSelectedIndex(customerComboBox.getItemCount()-1);
		}
		
		idTF.setEditable(false);
		 eMailTF.setEditable(false);
		 addressTF.setEditable(false);
		 telTF.setEditable(false);
		 submit.setEnabled(true);
		 remove.setEnabled(true);
		 edit.setEnabled(true);
		 edit.setText("Edit Customer");
 		 submit.setText("Create new Customer");
 		 back.setText("Clear fields");
 		 setNameTF("'Search here'");
	}
 		 
 		
 	public void clearTextFields(){	 
 		 idTF.setText("");
 		 eMailTF.setText("");
 		 nameTF.setText("");
 		 addressTF.setText("");
 		 telTF.setText("");
 	
		 
	}
 	
 	//panel view edit customer
 	public void editCustomerMode(){
 		
 		//switch the text field for the combobox
 		((CardLayout)(namePanel.getLayout())).show(namePanel, NAME_COMBO);
 		/*if(customerComboBox.getItemCount()>0){
			customerComboBox.setSelectedIndex(0);
		}*/
 		
 		idTF.setEditable(false);
		 eMailTF.setEditable(true);
		 addressTF.setEditable(true);
		 telTF.setEditable(true);
		 
		 submit.setEnabled(false);
		 remove.setEnabled(false);
		 edit.setText("Save Changes");
		 back.setText("back");
 		
 	}
 	
 	//panel view new customer 
 	public void newCustomerMode(){
 		
 		//switch the combobox for the text field
 		((CardLayout)(namePanel.getLayout())).show(namePanel, NAME_FIELD);
 		
 		 idTF.setEditable(false);
		 eMailTF.setEditable(true);
		 nameTF.setEditable(true);
		 addressTF.setEditable(true);
		 telTF.setEditable(true);
		 
		 submit.setText("Save new Customer");
		 remove.setEnabled(false);
		 edit.setEnabled(false);
		 back.setText("back");
 		
		 clearTextFields();
 	}
 	
 	public void updateCustomerList(ArrayList<Customer> customers){
		customerComboBox.setModel(new DefaultComboBoxModel(customers.toArray()));
		if(customerComboBox.getItemCount()>0){
			customerComboBox.setSelectedIndex(customerComboBox.getItemCount()-1);
		}
	}
 	
 	/**
 	 * A method that clears temp fields on logout.
 	 */
 	public void logout(){
 		String empty = "";
 		setNameTF(empty);
 		setAddressTF(empty);
 		setEmailTF(empty);
 		setIDTF(empty);
 		telTF.setText(empty);
 	}
}
