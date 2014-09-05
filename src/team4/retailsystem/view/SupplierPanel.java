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
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

import java.awt.ComponentOrientation;

public class SupplierPanel extends JPanel {
	private JTextField nameTF;
	private JTextField eMailTF;
	private JTextField idTF;
	private JTextField telTF;
	private JTextArea infoTextArea;
	private JTextArea addressTF;
	private JLabel supplierPanelLabel;
	private JPanel supplierDetails;
	private JLabel supplierNameLabel;
	private JLabel supplierEmailLabel;
	private JLabel supplierContactLabel;
	private JLabel supplierIdLabel;
	private JLabel supplierAddressLabel;
	private JButton editItemButton;
	private JButton removeItemButton;
	private JButton newCustomerButton;
	private JButton saveItemButton;
	private JButton cancelBtn;
	private JList<Object> supplierList;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JPanel panel_3;
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	private Supplier s;
	
    private Pattern emailPattern = Pattern
            .compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]"
                    + "{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|((["
                    + "a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    private Pattern telPattern = Pattern.compile("(\\+|\\d)\\d{6,15}");
	
	public SupplierPanel() {

		initialiseComponents();
		addListeners();
		constructView();

	}

	public void initialiseComponents() {

		panel = new JPanel();
		supplierPanelLabel = new JLabel("Suppliers");
		supplierDetails = new JPanel();
		supplierNameLabel = new JLabel("Name:");
		supplierEmailLabel = new JLabel("Email address:");
		supplierContactLabel = new JLabel("Telephone:");
		supplierIdLabel = new JLabel("Unique ID:");
		supplierAddressLabel = new JLabel("Address:");

		nameTF = new JTextField();
		eMailTF = new JTextField();
		idTF = new JTextField();
		telTF = new JTextField();
		addressTF = new JTextArea();
		saveItemButton = new JButton("Save");
		newCustomerButton = new JButton("New");
		editItemButton = new JButton("Edit");
		removeItemButton = new JButton("Delete");
		infoTextArea = new JTextArea();
		cancelBtn = new JButton("Cancel");
		scrollPane = new JScrollPane();
		panel_3 = new JPanel();
	
		supplierList = new JList<Object>();
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
				newSupplierMode();
			}
		});

		editItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please select a supplier to edit");
				} 
				else {
					editSupplierMode();
				}
			}
		});

		removeItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please select a supplier to remove");
				} else if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete this supplier") == 0) {
					infoTextArea.setText(getNameTF()
							+ " has been removed from the database.");

					for (RetailViewListener r : listeners) {
						r.clickDeleteSupplier(Integer.parseInt(getIDTF()));

					}
					setToViewMode();

					if(supplierList.getModel().getSize() > 0){
						supplierList.setSelectedValue(supplierList.getModel().getElementAt(0), true);	
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
							r.clickAddSupplier(getNameTF(), getAddressTF(),
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
							r.clickUpdateSupplier(Integer.parseInt(getIDTF()),
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
				
				if(supplierList.getSelectedValue() != null){
					Supplier s = (Supplier)supplierList.getSelectedValue();
					infoTextArea.setText("");
					nameTF.setText(s.getName());
					addressTF.setText(s.getAddress());
					eMailTF.setText(s.getEmail());
					idTF.setText("" + s.getID());
					telTF.setText(s.getTelephoneNumber());
				}
			}
		});
		
		supplierList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(supplierList.getSelectedValue() != null){
					s = (Supplier) supplierList.getSelectedValue();
					infoTextArea.setText("");
					nameTF.setText(s.getName());
					addressTF.setText(s.getAddress());
					eMailTF.setText(s.getEmail());
					idTF.setText("" + s.getID());
					telTF.setText(s.getTelephoneNumber());
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

		supplierPanelLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		supplierPanelLabel.setBounds(620, 13, 170, 29);
		panel.add(supplierPanelLabel);

		supplierDetails
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		supplierDetails.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Supplier details",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		supplierDetails.setBounds(10, 84, 570, 372);
		add(supplierDetails);
		supplierDetails.setLayout(null);

		supplierNameLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		supplierNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		supplierNameLabel.setBounds(33, 41, 148, 14);
		supplierDetails.add(supplierNameLabel);

		supplierAddressLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		supplierAddressLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		supplierAddressLabel.setBounds(33, 99, 148, 14);
		supplierDetails.add(supplierAddressLabel);

		nameTF.setBorder(BorderFactory.createTitledBorder(""));
		nameTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nameTF.setColumns(10);
		nameTF.setBounds(237, 35, 323, 27);
		supplierDetails.add(nameTF);

		eMailTF.setBorder(BorderFactory.createTitledBorder(""));
		eMailTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		eMailTF.setColumns(10);
		eMailTF.setBounds(237, 180, 323, 27);
		supplierDetails.add(eMailTF);

		idTF.setEditable(false);
		idTF.setBorder(BorderFactory.createTitledBorder(""));
		idTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idTF.setColumns(10);
		idTF.setBounds(237, 236, 323, 27);
		supplierDetails.add(idTF);

		telTF.setBorder(BorderFactory.createTitledBorder(""));
		telTF.setFont(new Font("Arial", Font.PLAIN, 12));
		telTF.setColumns(10);
		telTF.setBounds(237, 295, 323, 27);
		supplierDetails.add(telTF);

		supplierEmailLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		supplierEmailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		supplierEmailLabel.setBounds(33, 186, 148, 14);
		supplierDetails.add(supplierEmailLabel);

		supplierIdLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		supplierIdLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		supplierIdLabel.setBounds(33, 242, 148, 14);
		supplierDetails.add(supplierIdLabel);

		supplierContactLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		supplierContactLabel
				.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		supplierContactLabel.setBounds(33, 301, 148, 14);
		supplierDetails.add(supplierContactLabel);

		addressTF.setBackground(SystemColor.control);
		addressTF.setDisabledTextColor(SystemColor.control);
		addressTF.setBorder(BorderFactory.createTitledBorder(""));
		addressTF.setBounds(237, 95, 323, 58);
		supplierDetails.add(addressTF);

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
				.getBorder("TitledBorder.border"), "Supplier list",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scrollPane.setBounds(590, 85, 220, 504);
		add(scrollPane);

		supplierList.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setViewportView(supplierList);

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

	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		
		Supplier currentlySelected = null;
		int previousCount = supplierList.getModel().getSize();
		
		if(previousCount > 0){
			currentlySelected = (Supplier)supplierList.getSelectedValue();
		}

		supplierList.setListData(suppliers.toArray());
		int newCount = supplierList.getModel().getSize();
		
		if( supplierList.getModel().getSize() > 0 ){
			if(currentlySelected!=null && (newCount == previousCount)){
				supplierList.setSelectedValue(currentlySelected, true);
			}
			else if( (newCount > previousCount) && (previousCount>0)){
				supplierList.setSelectedValue(supplierList.getModel().getElementAt(newCount-1), true);
			}
			else{
				supplierList.setSelectedValue(supplierList.getModel().getElementAt(0), true);
			}
		}
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	// default panel view
	public void setToViewMode() {

		addressTF.setBackground(SystemColor.control);
		supplierList.setEnabled(true);

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
	public void editSupplierMode() {

		// disable selection list
		supplierList.setEnabled(false);
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
	public void newSupplierMode() {

		supplierList.setEnabled(false);
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
		
		if(supplierList.getModel().getSize() > 0){
			supplierList.clearSelection();
			supplierList.setSelectedValue(supplierList.getModel().getElementAt(0), true);		
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

