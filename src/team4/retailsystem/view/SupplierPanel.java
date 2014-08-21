package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.Supplier;

@SuppressWarnings("serial")
public class SupplierPanel extends JPanel implements ActionListener,
        ListSelectionListener {

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
    private JPanel buttonPanel;
    private JScrollPane supplierPanel;
    private JPanel addSupplierPanel;
    private JButton addSupplierButton;
    private JButton editSupplierButton;
    private JButton removeSupplierButton;
    private JButton finishButton;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel telephoneLabel;
    private JTextPane nameField;
    private JTextPane addressField;
    private JTextField emailField;
    private JTextField telephoneField;
    private JScrollPane scrollPane;
    private JList<Object> supplierList;
    private ArrayList<String> supplierArrayList;
    private int index;
    private Pattern emailPattern = Pattern
            .compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]"
                    + "{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|((["
                    + "a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    private Pattern telPattern = Pattern.compile("\\d{7,10}"); // checking 8
                                                            // digital number
                                                            // only
    private boolean isNewOrder = false;
    private boolean isEditOrder = false;
    private boolean isSelected = false;

    private ArrayList<Supplier> suppliers;

    public SupplierPanel(ArrayList<Supplier> suppliers) {
    	this.suppliers = suppliers;
    	
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        buttonPanel = new JPanel();
        gbc.ipady = 30;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        addPanel(buttonPanel, gbl, gbc);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        addSupplierButton = new JButton("Add");
        addSupplierButton.addActionListener(this);
        buttonPanel.add(addSupplierButton);

        editSupplierButton = new JButton("Edit");
        editSupplierButton.addActionListener(this);
        buttonPanel.add(editSupplierButton);

        removeSupplierButton = new JButton("Remove");
        removeSupplierButton.addActionListener(this);
        buttonPanel.add(removeSupplierButton);

        addSupplierPanel = new JPanel();
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;
        addPanelName("Add & Edit Supplier", addSupplierPanel);
        addPanel(addSupplierPanel, gbl, gbc);
        addSupplierPanel.setLayout(new GridLayout(9, 0));

        nameLabel = new JLabel("Name");
        addSupplierPanel.add(nameLabel);

        nameField = new JTextPane();
        scrollPane = new JScrollPane(nameField);
        addSupplierPanel.add(scrollPane);

        addressLabel = new JLabel("Address");
        addSupplierPanel.add(addressLabel);

        addressField = new JTextPane();
        scrollPane = new JScrollPane(addressField);
        addSupplierPanel.add(scrollPane);

        emailLabel = new JLabel("Email");
        addSupplierPanel.add(emailLabel);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 0));
        emailField = new JTextField();
        panel.add(emailField);
        addSupplierPanel.add(panel);

        telephoneLabel = new JLabel("Telephone");
        addSupplierPanel.add(telephoneLabel);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2, 0));
        telephoneField = new JTextField();
        panel1.add(telephoneField);
        addSupplierPanel.add(panel1);

        setEditableForField(false);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1, 2));
        addSupplierPanel.add(panel2);
        finishButton = new JButton("SUBMIT");
        finishButton.addActionListener(this);
        panel2.add(finishButton);

        getSupplierArrayList();

        supplierList = new JList<Object>();
        //supplierList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        supplierList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        supplierList.setVisibleRowCount(getHeight());
        supplierList.setOpaque(true);
        supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierList.getSelectionModel().addListSelectionListener(this);
        supplierPanel = new JScrollPane(supplierList);
        gbc.gridx = 1;
        addScrollPane(supplierPanel, "Suppliers", gbl, gbc);

    }
    
    public void updateSupplierList(ArrayList<Supplier> suppliers){
    	supplierList.setListData(suppliers.toArray());
	}

    public void addListener(RetailViewListener r){
		listeners.add(r);
	}
    
    public void addPanel(JPanel panel, GridBagLayout gbl, GridBagConstraints gbc) {
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }

    public void addScrollPane(JScrollPane panel, String panelName,
            GridBagLayout gbl, GridBagConstraints gbc) {
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }

    public void addPanelName(String panelName, JPanel panel) {
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource().equals(addSupplierButton)) {
            nameField.setText(null);
            addressField.setText(null);
            emailField.setText(null);
            telephoneField.setText(null);
            isNewOrder = true;
            setEditableForField(true);
        }

        else if (arg0.getSource().equals(editSupplierButton)) {
            if (isSelected == true) {
                isEditOrder = true;
                setEditableForField(true);
                Supplier supplier = suppliers.get(index);
                nameField.setText(supplier.getName());
                addressField.setText(supplier.getAddress());
                emailField.setText(supplier.getEmail());
                telephoneField.setText(supplier.getTelephoneNumber());
            } else
                warmingMsg("Please select Supplier to Edit");
        }

        else if (arg0.getSource().equals(finishButton)) {
            if (isNewOrder == true) {
                if (isCorrectDetail() == true) {
                	
                    //inform listeners of event
                	for(RetailViewListener r:listeners){
                		r.clickAddSupplier(nameField.getText(), addressField.getText(),
                							emailField.getText(), telephoneField.getText());
                	}
                	
                	setInitialConditionForButtons();
                    setEditableForField(false);
                }
            } else if (isEditOrder == true) {
                if (isCorrectDetail() == true) {
                    Supplier supplier =
                            Database.getInstance().getSuppliers().get(index);
                    editSupplier(supplier, nameField.getText(),
                            addressField.getText(), emailField.getText(),
                            telephoneField.getText());
                    
                    //inform listeners of event
                    Supplier s = (Supplier)supplierList.getSelectedValue();
                	for(RetailViewListener r:listeners){
                		r.clickUpdateSupplier(s.getID(),nameField.getText(), addressField.getText(),
                							emailField.getText(), telephoneField.getText());
                	}
                    
                    setInitialConditionForButtons();
                    setEditableForField(false);
                }
            } else
                errorMsg("Please choose Add, Edit or Remove");

        }

        else if (arg0.getSource().equals(removeSupplierButton)) {
            if(isSelected == true){             
                Supplier supplier = Database.getInstance().getSuppliers().get(index);
                
                //inform listeners of event
                Supplier s = (Supplier)supplierList.getSelectedValue();
            	for(RetailViewListener r:listeners){
            		r.clickDeleteSupplier(s.getID());
            	}
                
                setInitialConditionForButtons();
                setEditableForField(false);
            }
            else
                warmingMsg("Please select Supplier to remove");
            
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        index = supplierList.getSelectedIndex();
        isSelected = true;
    }

    public boolean isCorrectDetail() {
        boolean condition = false;
        if (nameField.getText().equals(null) || nameField.getText().equals("")) {
            warmingMsg("Please enter Supplier name");
        } else if (addressField.getText().equals(null)
                || addressField.getText().equals("")) {
            warmingMsg("Please enter Supplier address");
        } else if (isEmailValid(emailField.getText()) == false) {
            warmingMsg("invalid email");
        } else if (isTelValid(telephoneField.getText()) == false) {
            warmingMsg("invalid telephone");
        } else
            condition = true;
        return condition;
    }

    public boolean isEmailValid(String email) {
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public boolean isTelValid(String telephone) {
        Matcher telMatcher = telPattern.matcher(telephone);
        return telMatcher.matches();
    }

    public void errorMsg(String msg) {
        JOptionPane.showMessageDialog(new JFrame(), msg, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void warmingMsg(String msg) {
        JOptionPane.showMessageDialog(new JFrame(), msg, "Warming",
                JOptionPane.WARNING_MESSAGE);
    }

    public void setInitialConditionForButtons() {
        isNewOrder = false;
        isEditOrder = false;
        isSelected = false;
    }

    public void setEditableForField(boolean condition) {
        nameField.setEditable(condition);
        addressField.setEditable(condition);
        emailField.setEditable(condition);
        telephoneField.setEditable(condition);
    }

    public void editSupplier(Supplier supplier, String name, String address,
            String email, String telephone) {
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setEmail(email);
        supplier.setTelephone(telephone);
    }

    public void getSupplierArrayList() {
        suppliers = Database.getInstance().getSuppliers();
        supplierArrayList = new ArrayList<>();
        for (Supplier s : suppliers) {
            supplierArrayList.add(s.getName() + "   \n");
        }
    }

    public void addSupplierToDB() {
        //Database.getInstance().addSupplier(
                //new Supplier(nameField.getText(), emailField.getText(),
                       // addressField.getText(), telephoneField.getText()));
    }
}
