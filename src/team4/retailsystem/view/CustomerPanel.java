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

import java.awt.Font;
import java.util.ArrayList;

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
	private JButton editItemButton;
	private JButton removeItemButton;
	private JButton newCustomerButton;
	private JButton saveItemButton;
	private JButton cancelBtn;
	private JList<Object> customerList;



	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();

	

	public CustomerPanel() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 800, 62);
		add(panel);
		panel.setLayout(null);
		
		// new customer button
		 newCustomerButton = new JButton("New Customer");
		 newCustomerButton.setFont(new Font("Arial", Font.BOLD, 12));
		 newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newCustomerMode();
			}
		});
		 newCustomerButton.setBounds(10, 11, 119, 34);
		panel.add(newCustomerButton);
		
		// edit customer button
		editItemButton = new JButton("Edit Customer");
		editItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getNameTF().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please select a customer to edit");
						}
				else{
					editCustomerMode();
				}
			}
		});
		editItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		editItemButton.setBounds(140, 11, 119, 34);
		panel.add(editItemButton);
		
		
		
		
		//remove button
		 removeItemButton = new JButton("Delete Customer");
		 removeItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			
		});
		removeItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		removeItemButton.setBounds(269, 11, 125, 34);
		panel.add(removeItemButton);
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Customer Panel");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(620, 13, 170, 29);
		panel.add(lblNewLabel_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel_1.setBorder(new TitledBorder(null, "Customer details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 84, 570, 372);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Customer name:");
		lblNewLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(115, 41, 112, 14);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Customer address:");
		lblNewLabel_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(115, 99, 112, 14);
		panel_1.add(lblNewLabel_1);
		
		nameTF = new JTextField();
		nameTF.setBorder(BorderFactory.createTitledBorder(""));
		nameTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nameTF.setColumns(10);
		nameTF.setBounds(237, 35, 323, 27);
		panel_1.add(nameTF);
		
		eMailTF = new JTextField();
		eMailTF.setBorder(BorderFactory.createTitledBorder(""));
		eMailTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		eMailTF.setColumns(10);
		eMailTF.setBounds(237, 180, 323, 27);
		panel_1.add(eMailTF);
		
		idTF = new JTextField();
		idTF.setEditable(false);
		idTF.setBorder(BorderFactory.createTitledBorder(""));
		idTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idTF.setColumns(10);
		idTF.setBounds(237, 236, 323, 27);
		panel_1.add(idTF);
		
		telTF = new JTextField();
		telTF.setBorder(BorderFactory.createTitledBorder(""));
		telTF.setFont(new Font("Arial", Font.PLAIN, 12));
		telTF.setColumns(10);
		telTF.setBounds(237, 295, 323, 27);
		panel_1.add(telTF);
		
		JLabel label = new JLabel("Customer email:");
		label.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(115, 186, 112, 14);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("Customer ID:");
		label_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(115, 242, 112, 14);
		panel_1.add(label_1);
		
		JLabel label_2 = new JLabel("Customer contact:");
		label_2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setBounds(115, 301, 112, 14);
		panel_1.add(label_2);
		
		addressTF = new JTextArea();
		addressTF.setBackground(SystemColor.control);
		addressTF.setDisabledTextColor(SystemColor.control);
		addressTF.setBorder(BorderFactory.createTitledBorder(""));
		addressTF.setBounds(237, 95, 323, 58);
		panel_1.add(addressTF);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 465, 570, 124);
		add(panel_3);
		panel_3.setLayout(null);
		
		infoTextArea = new JTextArea();
		
		infoTextArea.setEditable(false);
		infoTextArea.setBackground(SystemColor.inactiveCaptionBorder);
		infoTextArea.setBorder(new TitledBorder(null, "Event information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		infoTextArea.setBounds(10, 11, 550, 46);
		panel_3.add(infoTextArea);
		infoTextArea.setColumns(10);
		
		saveItemButton = new JButton("Save");
		saveItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		saveItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					infoTextArea.setText("New customer added to database");
					//getcustomerNameArrayList();
					clearTextFields();
					setToViewMode();

				} else {
					JOptionPane.showMessageDialog(null,
							"Please provide a valid email address");

				}

			
			}
		});
		saveItemButton.setBounds(354, 68, 98, 34);
		panel_3.add(saveItemButton);
		
		cancelBtn = new JButton("Cancel");
		cancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setToViewMode();
			}
		});
		cancelBtn.setBounds(462, 68, 98, 34);
		panel_3.add(cancelBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setViewportBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Customer list", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(590, 85, 220, 504);
		add(scrollPane);
		
		customerList = new JList();
		customerList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		customerList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Customer c = (Customer)customerList.getSelectedValue();

				nameTF.setText(c.getName());
				addressTF.setText(c.getAddress());
				eMailTF.setText(c.getEmail());
				idTF.setText("" + c.getID());
				telTF.setText(c.getTelephoneNumber());
			}
		});
		customerList.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setViewportView(customerList);

		setToViewMode();
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

	public void updateCustomerList(ArrayList<Customer> customers) {
		//System.out.println("Customers size: "+customers.size());
		customerList.setListData(customers.toArray());
		
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	// default panel view
	public void setToViewMode() {

		clearTextFields();
		infoTextArea.setText("Choose a customer to view details");

		addressTF.setBackground(SystemColor.control);

		customerList.setEnabled(true);

		nameTF.setEditable(false);
		idTF.setEditable(false);
		eMailTF.setEditable(false);
		telTF.setEditable(false);

		newCustomerButton.setEnabled(true);
		removeItemButton.setEnabled(true);
		editItemButton.setEnabled(true);
		saveItemButton.setVisible(false);
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
		addressTF.setBackground(Color.white);

		// set customer details in corresponding textfields
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
		//customerNameArrayList.clear();
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
		saveItemButton.setVisible(false);
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
		seteMailTF(empty);
		setIDTF(empty);
		telTF.setText(empty);
	}


}
