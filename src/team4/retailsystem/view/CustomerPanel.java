package team4.retailsystem.view;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.User;

import java.awt.ComponentOrientation;

public class CustomerPanel extends JPanel {
	private JTextField nameTF;
	private JTextField eMailTF;
	private JTextField idTF;
	private JTextField telTF;
	private JTextArea infoTextArea;
	private JTextArea addressTF;
	private JLabel customerPanelLabel;
	private JPanel customerDetails;
	private JLabel customerNameLabel;
	private JLabel customerEmailLabel;
	private JLabel customerContactLabel;
	private JLabel customerIdLabel;
	private JLabel customerAddressLabel;
	private JButton editItemButton;
	private JButton removeItemButton;
	private JButton newCustomerButton;
	private JButton saveItemButton;
	private JButton cancelBtn;
	private JList<Object> customerList;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JPanel panel_3;
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	private Customer c;
	
    private Pattern emailPattern = Pattern
            .compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]"
                    + "{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|((["
                    + "a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    private Pattern telPattern = Pattern.compile("(\\+|\\d)\\d{6,15}");
	
	public CustomerPanel() {

		initialiseComponents();
		addListeners();
		constructView();

	}

	public void initialiseComponents() {

		panel = new JPanel();
		customerPanelLabel = new JLabel("Customer Panel");
		customerDetails = new JPanel();
		customerNameLabel = new JLabel("Customer name:");
		customerEmailLabel = new JLabel("Customer email:");
		customerContactLabel = new JLabel("Customer contact:");
		customerIdLabel = new JLabel("Customer id:");
		customerAddressLabel = new JLabel("Customer address:");

		nameTF = new JTextField();
		eMailTF = new JTextField();
		idTF = new JTextField();
		telTF = new JTextField();
		addressTF = new JTextArea();
		saveItemButton = new JButton("Save");
		newCustomerButton = new JButton("New Customer");
		editItemButton = new JButton("Edit Customer");
		removeItemButton = new JButton("Delete Customer");
		infoTextArea = new JTextArea();
		cancelBtn = new JButton("Cancel");
		scrollPane = new JScrollPane();
		panel_3 = new JPanel();
	
		customerList = new JList<Object>();
	}

    public boolean isEmailValid(String email) {
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public boolean isTelValid(String telephone) {
        Matcher telMatcher = telPattern.matcher(telephone);
        return telMatcher.matches();
    }

	public void addListeners() {

		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newCustomerMode();
			}
		});

		editItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please select a customer to edit");
				} 
				else {
					editCustomerMode();
				}
			}
		});

		removeItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please select a customer to remove");
				} else if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete this customer") == 0) {
					infoTextArea.setText(getNameTF()
							+ " has been removed from the database.");

					for (RetailViewListener r : listeners) {
						r.clickDeleteCustomer(Integer.parseInt(getIDTF()));

					}
					setToViewMode();

					if(customerList.getModel().getSize() > 0){
						customerList.setSelectedValue(customerList.getModel().getElementAt(0), true);	
					} else {
						clearTextFields();
					}
				}
			}

		});

		saveItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty() || getAddressTF().isEmpty()
						|| getEmailTF().isEmpty() || getTelTF().isEmpty()) {

					JOptionPane.showMessageDialog(null,
							"Please make sure all fields are filled in");
				} else if (!isEmailValid(getEmailTF())) {
					JOptionPane.showMessageDialog(null,
							"Invalid email.");
				} else if (!isTelValid(getTelTF())){
					JOptionPane.showMessageDialog(null,
							"Invalid telephone.");					
				} else {
					if (getIDTF().isEmpty()) {
						// inform RetailViewListeners of the event, pass the
						// information.
						for (RetailViewListener r : listeners) {
							r.clickSaveNewCustomer(getNameTF(), getAddressTF(),
									getEmailTF(), getTelTF());

						}

						// add to customer array/database when functionality
						// available
						infoTextArea.setText(getNameTF()
								+ "  is added to the database.");
						// getcustomerNameArrayList();
						setToViewMode();

					} else {
						for (RetailViewListener r : listeners) {
							r.clickUpdateCustomer(Integer.parseInt(getIDTF()),
									getNameTF(), getAddressTF(), getEmailTF(),
									getTelTF());

						}

						// add to customer array/database when functionality
						// available
						infoTextArea.setText(getNameTF()
								+ "'s details have been updated.");
						// getcustomerNameArrayList();
						//clearTextFields();
						setToViewMode();

					}
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				setToViewMode();
				
				if(customerList.getSelectedValue() != null){
					Customer c = (Customer)customerList.getSelectedValue();
					infoTextArea.setText("");
					nameTF.setText(c.getName());
					addressTF.setText(c.getAddress());
					eMailTF.setText(c.getEmail());
					idTF.setText("" + c.getID());
					telTF.setText(c.getTelephoneNumber());
				}
			}
		});
		
		customerList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(customerList.getSelectedValue() != null){
					c = (Customer) customerList.getSelectedValue();
					infoTextArea.setText("");
					nameTF.setText(c.getName());
					addressTF.setText(c.getAddress());
					eMailTF.setText(c.getEmail());
					idTF.setText("" + c.getID());
					telTF.setText(c.getTelephoneNumber());
				}
			}
			
		});

	}

	public void constructView() {

		setLayout(null);
		panel.setBounds(10, 11, 800, 62);
		add(panel);
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		newCustomerButton.setFont(new Font("Arial", Font.BOLD, 12));
		newCustomerButton.setBounds(10, 11, 125, 34);
		panel.add(newCustomerButton);

		editItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		editItemButton.setBounds(146, 11, 125, 34);
		panel.add(editItemButton);

		removeItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		removeItemButton.setBounds(281, 11, 133, 34);
		panel.add(removeItemButton);

		customerPanelLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		customerPanelLabel.setBounds(620, 13, 170, 29);
		panel.add(customerPanelLabel);

		customerDetails
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		customerDetails.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Customer details",
				TitledBorder.RIGHT, TitledBorder.TOP, null, null));
		customerDetails.setBounds(10, 84, 570, 372);
		add(customerDetails);
		customerDetails.setLayout(null);

		customerNameLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		customerNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		customerNameLabel.setBounds(33, 41, 148, 14);
		customerDetails.add(customerNameLabel);

		customerAddressLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		customerAddressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		customerAddressLabel.setBounds(33, 99, 148, 14);
		customerDetails.add(customerAddressLabel);

		nameTF.setBorder(BorderFactory.createTitledBorder(""));
		nameTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nameTF.setColumns(10);
		nameTF.setBounds(237, 35, 323, 27);
		customerDetails.add(nameTF);

		eMailTF.setBorder(BorderFactory.createTitledBorder(""));
		eMailTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		eMailTF.setColumns(10);
		eMailTF.setBounds(237, 180, 323, 27);
		customerDetails.add(eMailTF);

		idTF.setEditable(false);
		idTF.setBorder(BorderFactory.createTitledBorder(""));
		idTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idTF.setColumns(10);
		idTF.setBounds(237, 236, 323, 27);
		customerDetails.add(idTF);

		telTF.setBorder(BorderFactory.createTitledBorder(""));
		telTF.setFont(new Font("Arial", Font.PLAIN, 12));
		telTF.setColumns(10);
		telTF.setBounds(237, 295, 323, 27);
		customerDetails.add(telTF);

		customerEmailLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		customerEmailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		customerEmailLabel.setBounds(33, 186, 148, 14);
		customerDetails.add(customerEmailLabel);

		customerIdLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		customerIdLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		customerIdLabel.setBounds(33, 242, 148, 14);
		customerDetails.add(customerIdLabel);

		customerContactLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		customerContactLabel
				.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		customerContactLabel.setBounds(33, 301, 148, 14);
		customerDetails.add(customerContactLabel);

		addressTF.setBackground(SystemColor.control);
		addressTF.setDisabledTextColor(SystemColor.control);
		addressTF.setBorder(BorderFactory.createTitledBorder(""));
		addressTF.setBounds(237, 95, 323, 58);
		customerDetails.add(addressTF);

		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 465, 570, 124);
		add(panel_3);
		panel_3.setLayout(null);

		infoTextArea.setEditable(false);
		infoTextArea.setBackground(SystemColor.inactiveCaptionBorder);
		infoTextArea.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Event window",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		infoTextArea.setBounds(10, 11, 550, 46);
		panel_3.add(infoTextArea);
		infoTextArea.setColumns(10);

		saveItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		saveItemButton.setBounds(354, 68, 98, 34);
		panel_3.add(saveItemButton);
		cancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
		cancelBtn.setBounds(462, 68, 98, 34);
		panel_3.add(cancelBtn);

		scrollPane.setBorder(null);
		scrollPane.setViewportBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Customer list",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(590, 85, 220, 504);
		add(scrollPane);

		customerList.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setViewportView(customerList);

		setToViewMode();
	}

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

	public void updateCustomerList(ArrayList<Customer> customers) {
		Customer currentlySelected = null;
		int previousCount = customerList.getModel().getSize();
		
		if(previousCount > 0){
			currentlySelected = (Customer)customerList.getSelectedValue();
		}

		customerList.setListData(customers.toArray());
		int newCount = customerList.getModel().getSize();
		
		if( customerList.getModel().getSize() > 0 ){
			if(currentlySelected!=null && (newCount == previousCount)){
				customerList.setSelectedValue(currentlySelected, true);
			}
			else if( (newCount > previousCount) && (previousCount>0)){
				customerList.setSelectedValue(customerList.getModel().getElementAt(newCount-1), true);
			}
			else{
				customerList.setSelectedValue(customerList.getModel().getElementAt(0), true);	
			}
		}
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	// default panel view
	public void setToViewMode() {

		addressTF.setBackground(SystemColor.control);
		customerList.setEnabled(true);

		nameTF.setEditable(false);
		addressTF.setEditable(false);
		idTF.setEditable(false);
		eMailTF.setEditable(false);
		telTF.setEditable(false);

		newCustomerButton.setEnabled(true);
		removeItemButton.setEnabled(true);
		editItemButton.setEnabled(true);
		saveItemButton.setVisible(false);
		cancelBtn.setVisible(false);
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
		addressTF.setBackground(Color.white);

		nameTF.setEditable(false);
		idTF.setEditable(false);
		eMailTF.setEditable(true);
		addressTF.setEditable(true);
		telTF.setEditable(true);

		// disable/enable appropriate buttons
		removeItemButton.setEnabled(false);
		newCustomerButton.setEnabled(false);
		saveItemButton.setVisible(true);
		cancelBtn.setVisible(true);
	}

	// panel view new customer
	public void newCustomerMode() {

		customerList.setEnabled(false);
		addressTF.setBackground(SystemColor.white);

		nameTF.setEditable(true);
		idTF.setEditable(false);
		eMailTF.setEditable(true);
		nameTF.setEditable(true);
		addressTF.setEditable(true);
		telTF.setEditable(true);

		editItemButton.setEnabled(false);
		removeItemButton.setEnabled(false);
		saveItemButton.setVisible(true);
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

	// A method that clears temp fields on logout.
	public void logout() {
		setToViewMode();
		
		if(customerList.getModel().getSize() > 0){
			System.out.println("here, customer");
			customerList.setSelectedValue(customerList.getModel().getElementAt(0), true);	
		} else {
			String empty = "";
			setNameTF(empty);
			setAddressTF(empty);
			seteMailTF(empty);
			setIDTF(empty);
			telTF.setText(empty);
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

	public void seteMailTF(String newEmail) {
		eMailTF.setText(newEmail);
	}

	// auto generated iD can only be added after the submit-button is pressed???
	public void setIDTF(String newID) {
		idTF.setText(newID);
	}

}

