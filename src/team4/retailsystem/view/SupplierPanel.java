package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import team4.retailsystem.model.Supplier;

public class SupplierPanel extends JPanel implements ActionListener, ListSelectionListener{

    private JPanel buttonPanel;
    private JScrollPane supplierPanel;
    private JPanel addSupplierPanel;
    private JButton addSupplierButton;
    private JButton editSupplierButton;
    private JButton removeSupplierButton;
    private JButton finishButton;
    private JButton cancelButton;
    private JButton finishEdit;
    private JLabel nameLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel telephoneLabel;
    private JTextPane nameField;
    private JTextPane addressField;
    private JTextField emailField;
    private JTextField telephoneField;
    private JScrollPane scrollPane;
    private JList supplierList;
    private ArrayList<String> supplierArrayList;
    private Supplier supplier;
    private int index;
    

    public SupplierPanel() {
        // TODO Auto-generated constructor stub
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
        addSupplierButton = new JButton("New Supplier");
        addSupplierButton.addActionListener(this);
        buttonPanel.add(addSupplierButton);
               
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
        panel.setLayout(new GridLayout(2,0));
        emailField = new JTextField();
        panel.add(emailField);
        addSupplierPanel.add(panel);
        
        telephoneLabel = new JLabel("Telephone");
        addSupplierPanel.add(telephoneLabel);
        
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2,0));
        telephoneField = new JTextField();
        panel1.add(telephoneField);
        addSupplierPanel.add(panel1);
        
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(1,2));
        addSupplierPanel.add(panel2);
        finishButton = new JButton("ADD SUPPLIER");
        finishButton.addActionListener(this);
        panel2.add(finishButton);
        
        finishEdit = new JButton("EDIT SUPPLIER");
        finishEdit.addActionListener(this);
        panel2.add(finishEdit);
        
        supplierArrayList = new ArrayList<>();
        supplierArrayList.add("test");
        supplierArrayList.add("test1");
        supplierList = new JList(supplierArrayList.toArray());
        supplierList.setBounds(0, 0, 10, 10);
        supplierList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        supplierList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        supplierList.setVisibleRowCount(getHeight());
        supplierList.setOpaque(true);
        supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierList.getSelectionModel().addListSelectionListener(this);
        supplierPanel = new JScrollPane(supplierList);
        gbc.gridx = 1;
        addScrollPane(supplierPanel, "Suppliers", gbl, gbc);
        
        
        
        
    }

    public void addPanel(JPanel panel, GridBagLayout gbl,GridBagConstraints gbc){
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }
    
    public void addScrollPane(JScrollPane panel, String panelName, GridBagLayout gbl,GridBagConstraints gbc){
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }
    
    public void addPanelName(String panelName, JPanel panel){
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource().equals(addSupplierButton)){
            nameField.setText(null);
            addressField.setText(null);
            emailField.setText(null);
            telephoneField.setText(null);
        }
        
        else if(arg0.getSource().equals(finishButton)){
            //testing if it works or not
            new Supplier(nameField.getText(), emailField.getText(), addressField.getText(), telephoneField.getText());
            supplierArrayList.add(nameField.getText());
            supplierList.setBorder(BorderFactory.createLineBorder(Color.black));
            supplierList.setListData(supplierArrayList.toArray());
        }
        
        else if(arg0.getSource().equals(editSupplierButton)){
            //need db or arraylist from supplier to test
            /*supplier = null;
            for(Supplier s : suppliers){
                supplier = s;
                if(supplier.equals(supplierArrayList.get(supplierList.getSelectedIndex()))){
                    nameField.setText(supplier.getName());
                    addressField.setText(supplier.getAddress());
                    emailField.setText(supplier.getEmail());
                    telephoneField.setText(supplier.getTelephoneNumber());
                    break;
                }
            }*/
      
        }
        
        else if(arg0.getSource().equals(finishEdit)){
            //String name = supplier.getName();
            supplierArrayList.remove(index);
            supplierArrayList.add(index, nameField.getText());
            supplierList.setListData(supplierArrayList.toArray());
            //setAddress(addressField.getText());
            //setEmail(emailField.getText());
            
        }
        
        else if(arg0.getSource().equals(removeSupplierButton)){
            supplierList.remove(supplierList.getSelectedIndex());
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        // TODO Auto-generated method stub
        String text = supplierList.getSelectedValue().toString();
        nameField.setText(text);
        index = supplierList.getSelectedIndex();
        String test;
    }
}
