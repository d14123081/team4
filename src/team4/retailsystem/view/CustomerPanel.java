package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Customer;
import team4.retailsystem.model.User;
import team4.retailsystem.view.CustomerPanel.CRUDListener;

public class CustomerPanel extends JPanel {

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();

	private JPanel buttonPanel1, CustomerDisplayPanel;
	private JScrollPane orderListPanel;
	private JPanel combinePanel;
	private JPanel orderPanel;
	private JPanel functionalityPanel;
	private JScrollPane customerPanel;
	private JScrollPane productPanel;
	private JButton newCustomerButton, testBtn, spaceBtn;
	private JButton saveBtn, cancelBtn;
	private JButton editItemButton;
	private JButton removeItemButton;
	private JLabel infoLabel;

	// *****************************

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
	private JList<Object> customerList;
	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<String> customerNameArrayList = new ArrayList<>();
	private Customer customer;
	private int index;

	public CustomerPanel() {

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		// **********************************************

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
		addressTF = new JTextField(8);
		eMailTF = new JTextField(8);
		idTF = new JTextField(8);
		telTF = new JTextField(8);

		// ********************************

		buttonPanel1 = new JPanel();
		gbc.ipady = 30;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		addPanel(buttonPanel1, gbl, gbc);

		CustomerDisplayPanel = new JPanel();
		CustomerDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		CustomerDisplayPanel.setBorder(BorderFactory.createTitledBorder("Customer info"));
		CustomerDisplayPanel.setLayout(new GridLayout(0, 2, 10, 34));
		CustomerDisplayPanel.add(nameLabel);
		CustomerDisplayPanel.add(nameTF);
		CustomerDisplayPanel.add(addressLabel);
		CustomerDisplayPanel.add(addressTF);
		CustomerDisplayPanel.add(eMailLabel);
		CustomerDisplayPanel.add(eMailTF);
		CustomerDisplayPanel.add(idLabel);
		CustomerDisplayPanel.add(idTF);
		CustomerDisplayPanel.add(telLabel);

		CustomerDisplayPanel.add(telTF);

		buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.X_AXIS));

		spaceBtn = new JButton("     ");
		spaceBtn.setEnabled(false);
		spaceBtn.setBorder(null);
		buttonPanel1.add(spaceBtn);

		newCustomerButton = new JButton("New Customer");
		buttonPanel1.add(newCustomerButton, gbc);
		newCustomerButton.addActionListener(new CRUDListener());

		spaceBtn = new JButton("    ");
		spaceBtn.setEnabled(false);
		spaceBtn.setBorder(null);
		buttonPanel1.add(spaceBtn);

		editItemButton = new JButton("Edit Customer");
		buttonPanel1.add(editItemButton);
		editItemButton.addActionListener(new CRUDListener());

		spaceBtn = new JButton("    ");
		spaceBtn.setEnabled(false);
		spaceBtn.setBorder(null);
		buttonPanel1.add(spaceBtn);

		removeItemButton = new JButton("Remove Customer");
		buttonPanel1.add(removeItemButton);
		removeItemButton.addActionListener(new CRUDListener());

		spaceBtn = new JButton("    ");
		spaceBtn.setEnabled(false);
		spaceBtn.setBorder(null);
		buttonPanel1.add(spaceBtn);

		orderPanel = new JPanel();
		gbc.weightx = 20.9;
		gbc.weighty = 2.0;
		gbc.gridy = 1;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.gridwidth = 1;
		addPanel(orderPanel, gbl, gbc);

		GridBagLayout gbl1 = new GridBagLayout();
		orderPanel.setLayout(gbl1);
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.fill = GridBagConstraints.BOTH;

		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weighty = 6.0;
		gbc1.weightx = 1;
		gbc1.gridwidth = 1;
		addPanel(CustomerDisplayPanel, gbl1, gbc1);
		// addScrollPane(orderListPanel, gbl1, gbc1);*/
		orderPanel.add(CustomerDisplayPanel);

		functionalityPanel = new JPanel();
		gbc1.gridy = 1;
		gbc1.weighty = 1.0;
		addPanel(functionalityPanel, gbl1, gbc1);
		orderPanel.add(functionalityPanel);
		functionalityPanel.setLayout(new BoxLayout(functionalityPanel,
				BoxLayout.X_AXIS));

		infoLabel = new JLabel("Info");
		infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		saveBtn = new JButton("Save");
		cancelBtn = new JButton("Cancel");
		saveBtn.addActionListener(new FunctionalityListener());
		cancelBtn.addActionListener(new FunctionalityListener());

		spaceBtn = new JButton("    ");
		spaceBtn.setEnabled(false);
		spaceBtn.setBorder(null);
		functionalityPanel.add(spaceBtn);

		functionalityPanel.add(saveBtn);

		spaceBtn = new JButton("    ");
		spaceBtn.setEnabled(false);
		spaceBtn.setBorder(null);
		functionalityPanel.add(spaceBtn);

		functionalityPanel.add(cancelBtn);

		combinePanel = new JPanel();
		gbc.weightx = 1.8;
		gbc.weighty = 2.0;
		gbc.gridx = 1;
		gbc.gridy = 1;
		addPanel(combinePanel, gbl, gbc);

		combinePanel.setLayout(new GridLayout(2, 0));

		customerList = new JList(customerNameArrayList.toArray());
		getcustomerNameArrayList();
		customerList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		customerList.setFont(new Font("Areal", Font.PLAIN, 16));
		customerList.setVisibleRowCount(getHeight());
		customerList.setOpaque(true);
		customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerList.getSelectionModel().addListSelectionListener(
				new CustomerSelectionListener());
		customerPanel = new JScrollPane(customerList);
		addPanelName("Customers", customerPanel);
		combinePanel.add(customerPanel);
		
		this.setVisible(true);
		setToViewMode();
	}

	// ***********************

	class CRUDListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("New Customer")) {
				newCustomerMode();
			}

			// when edit button pressed
			if (e.getActionCommand().equals("Edit Customer")) {
				if(getNameTF().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please select a customer to edit");
						}
				else{
					editCustomerMode();
				}

			}

			// when remove button pressed
			if (e.getActionCommand().equals("Remove Customer")) {
				if(getNameTF().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please select a customer to remove");
						}
				else if (JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this customer") == 0 ) {
						for (RetailViewListener r : listeners) {
							r.clickDeleteCustomer(Integer.parseInt(getIDTF()));
							
						}
						setToViewMode();
				}
			}
			getcustomerNameArrayList();

			//updatecustomerList(customers);
		}
		
	}

	class CustomerSelectionListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent l) {
			//
			index = customerList.getSelectedIndex();
			customer = Database.getInstance().getCustomers().get(index);

			for (Customer c : customers) {
				if (c.equals(customer)) {

					nameTF.setText(c.getName());
					addressTF.setText(c.getAddress());
					eMailTF.setText(c.getEmail());
					idTF.setText("" + c.getID());
					telTF.setText(c.getTelephoneNumber());

				}
			}

		}
	}

	class FunctionalityListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Save")) {
				if (getNameTF().isEmpty() || getAddressTF().isEmpty()
						|| getEmailTF().isEmpty() || getTelTF().isEmpty()) {

					JOptionPane.showMessageDialog(null,
							"Please make sure all fields are filled in");
				}

				// checks for invalid Email construction
				else if (getEmailTF().contains("@")
						&& getEmailTF().contains(".")) {

					
					// inform RetailViewListeners of the event, pass the
					// information.
					for (RetailViewListener r : listeners) {
						r.clickSaveNewCustomer(getNameTF(), getAddressTF(),
								getEmailTF(), getTelTF());
					}

					// add to customer array/database when functionality
					// available
					System.out.println("Customer added to database");
					getcustomerNameArrayList();
					clearTextFields();
					setToViewMode();

				} else {
					JOptionPane.showMessageDialog(null,
							"Please provide a valid email address");

				}

			}
			if (e.getActionCommand().equals("Update")) {
				if (getAddressTF().isEmpty() || getEmailTF().isEmpty()
						|| getTelTF().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please make sure all fields are filled in");
				}

				else if (getEmailTF().contains("@")
						&& getEmailTF().contains(".")) {

					// inform RetailViewListeners of the event, pass the
					// information.
					for (RetailViewListener r : listeners) {
						r.clickUpdateCustomer(Integer.parseInt(getIDTF()),
								getNameTF(), getAddressTF(), getEmailTF(),
								getTelTF());
					}

					// add to customer array/database when functionality
					// available
					System.out.println("Customer updated");
					setToViewMode();

				} else {
					JOptionPane.showMessageDialog(null,
							"Please provide a valid email address");
				}

			}
			// when cancel button pressed
			if (e.getActionCommand().equals("Cancel")) {
				setToViewMode();

			}
		}
	}

	// **********getters and setters***************

	public String getNameTF() {
		return nameTF.getText();
	}

	public String getAddressTF() {
		return addressTF.getText();
	}

	public String getEmailTF() {
		return eMailTF.getText();
	}

	public String getIDTF() {
		return idTF.getText();
	}

	public String getTelTF() {
		return telTF.getText();
	}

	public void setNameTF(String newName) {
		nameTF.setText(newName);
	}

	public void setAddressTF(String newAddress) {
		addressTF.setText(newAddress);
	}

	public void setEmailTF(String newEmail) {
		eMailTF.setText(newEmail);
	}

	// auto generated iD can only be added after the submit-button is pressed???
	public void setIDTF(String newID) {
		idTF.setText(newID);
	}

	// *************************************

	public void addPanel(JPanel panel, GridBagLayout gbl, GridBagConstraints gbc) {
		gbl.setConstraints(panel, gbc);
		add(panel);
	}

	public void addScrollPane(JScrollPane panel, GridBagLayout gbl,
			GridBagConstraints gbc) {
		gbl.setConstraints(panel, gbc);
		add(panel);
	}

	public void addPanelName(String panelName, JScrollPane panel) {
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setBorder(BorderFactory.createTitledBorder(panelName));
	}

	public void getcustomerNameArrayList() {
		customers = Database.getInstance().getCustomers();
		customerNameArrayList.clear();
		for (Customer s : customers) {
			customerNameArrayList.add(s.getName() + "   ");
		}
		customerList.setListData(customerNameArrayList.toArray());
	}

	public void updateCustomerList(ArrayList<Customer> customers) {
		customerList.setListData(customers.toArray());
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	// default panel view
	public void setToViewMode() {

		clearTextFields();
		customerList.setEnabled(true);

		nameTF.setEditable(false);
		idTF.setEditable(false);
		eMailTF.setEditable(false);
		addressTF.setEditable(false);
		telTF.setEditable(false);

		newCustomerButton.setEnabled(true);
		removeItemButton.setEnabled(true);
		editItemButton.setEnabled(true);
		saveBtn.setVisible(false);
		saveBtn.setText("Save");
		cancelBtn.setVisible(false);
		
		
		//getcustomerNameArrayList();
	}

	public void clearTextFields() {
		idTF.setText("");
		eMailTF.setText("");
		nameTF.setText("");
		addressTF.setText("");
		telTF.setText("");

	}

	// panel view edit customer
	public void editCustomerMode() {

		// disable selection list
		customerList.setEnabled(false);

		// set customer details in corresponding textfields
		nameTF.setEditable(false);
		idTF.setEditable(false);
		eMailTF.setEditable(true);
		addressTF.setEditable(true);
		telTF.setEditable(true);

		// disable/enable appropriate buttons
		removeItemButton.setEnabled(false);
		newCustomerButton.setEnabled(false);
		saveBtn.setText("Update");
		saveBtn.setVisible(true);
		cancelBtn.setVisible(true);

	}

	// panel view new customer
	public void newCustomerMode() {
		//customerNameArrayList.clear();
		customerList.setEnabled(false);

		nameTF.setEditable(true);
		idTF.setEditable(false);
		eMailTF.setEditable(true);
		nameTF.setEditable(true);
		addressTF.setEditable(true);
		telTF.setEditable(true);

		editItemButton.setEnabled(false);
		removeItemButton.setEnabled(false);
		saveBtn.setVisible(true);
		cancelBtn.setVisible(true);

		clearTextFields();
	}

	public void updateUser(User user) {
		//
		if (user.getAuthorizationLevel() == 1) {
			newCustomerButton.setVisible(false);
			removeItemButton.setVisible(false);
			editItemButton.setVisible(false);
		} else {
			newCustomerButton.setVisible(true);
			removeItemButton.setVisible(true);
			editItemButton.setVisible(true);
		}
	}

	

	/**
	 * A method that clears temp fields on logout.
	 */
	public void logout() {
		String empty = "";
		setNameTF(empty);
		setAddressTF(empty);
		setEmailTF(empty);
		setIDTF(empty);
		telTF.setText(empty);
	}

}
