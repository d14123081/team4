package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Supplier;

public class OrderPanel extends JPanel implements ListSelectionListener, ActionListener {

    private JPanel buttonPanel1;
    private JScrollPane orderListPanel;
    private JPanel combinePanel;
    private JScrollPane supplierPanel;
    private JScrollPane productPanel;
    private JButton newOrderButton;
    private JButton viewOrderedButton;
    private JButton makeOrderButton;
    private JButton editItemButton;
    private JButton deleteItemButton;
    private JButton cancelButton;
    private JList itemList;
    private JList supplierList;
    private JList productList;
    private ArrayList<String> supplierArrayList;
    private ArrayList<String> productArrayList;
    private Database db;

    
    public OrderPanel() {
        // TODO Auto-generated constructor stub
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        
        buttonPanel1 = new JPanel();
        gbc.ipady = 30;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        addPanel(buttonPanel1, gbl, gbc);
        ArrayList<Supplier> as  = db.getSuppliers();
        for(Supplier s : as){
            
        }
        buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.X_AXIS));
        newOrderButton = new JButton("New Order"); 
        buttonPanel1.add(newOrderButton,gbc);
        newOrderButton.addActionListener(this);
        
        makeOrderButton = new JButton("Order");
        buttonPanel1.add(makeOrderButton,gbc);
        makeOrderButton.addActionListener(this);
        
        viewOrderedButton = new JButton("View Ordered");
        buttonPanel1.add(viewOrderedButton,gbc);
        viewOrderedButton.addActionListener(this);
        
        
        orderListPanel = new JScrollPane();
        gbc.weightx = 1.8;
        gbc.weighty = 2.0;
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;
        addPanelName("Order Items List", orderListPanel);
        addScrollPane(orderListPanel, gbl, gbc);
        
        combinePanel = new JPanel();
        gbc.weightx = 1.2;
        gbc.weighty = 2.0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        addPanel(combinePanel, gbl ,gbc);
        
        combinePanel.setLayout(new GridLayout(2, 0));
        supplierArrayList = new ArrayList<String>();
        supplierList = new JList(supplierArrayList.toArray());
        supplierPanel = new JScrollPane(supplierList);
        addPanelName("Suppliers", supplierPanel);
        combinePanel.add(supplierPanel);
        supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierList.getSelectionModel().addListSelectionListener(this);
        
        productArrayList = new ArrayList<String>();
        productList = new JList(productArrayList.toArray());
        productPanel = new JScrollPane(productList);
        addPanelName("Products", productPanel);
        combinePanel.add(productPanel);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productList.getSelectionModel().addListSelectionListener(this);
        
        
        this.setVisible(true);
    }

    public void addPanel(JPanel panel, GridBagLayout gbl,GridBagConstraints gbc){
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }
    
    public void addScrollPane(JScrollPane panel, GridBagLayout gbl,GridBagConstraints gbc){
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }
    public void addPanelName(String panelName, JScrollPane panel){
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }

    //can use SQL instead of arraylist
    public void addSupplierToArrayList(){
        //db.getInstance().getSuppliers()
    }
    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource().equals(newOrderButton)){
            itemList.removeAll();
            
        }
        
        else if(arg0.getSource().equals(viewOrderedButton)){
            
        }
        
        else if(arg0.getSource().equals(editItemButton)){
            
        }
        
        else if(arg0.getSource().equals(deleteItemButton)){
            
        }
        
        else if(arg0.getSource().equals(makeOrderButton)){
            
        }
        
        else if(arg0.getSource().equals(cancelButton)){
            
        }
        
    }

}
