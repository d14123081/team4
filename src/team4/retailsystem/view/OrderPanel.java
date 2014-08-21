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
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;

@SuppressWarnings("serial")
public class OrderPanel extends JPanel implements ListSelectionListener, ActionListener {

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
	private JPanel buttonPanel1;
    private JScrollPane orderListPanel;
    private JPanel combinePanel;
    private JPanel orderPanel;
    private JPanel totalPricePanel;
    private JScrollPane supplierPanel;
    private JScrollPane productPanel;
    private JButton newOrderButton;
    private JButton makeOrderButton;
    private JButton editItemButton;
    private JButton removeItemButton;
    private JLabel totalLabel;
    private JTextField totalField;
    
    private JList<Object> supplierList;
    private JList<Object> productList;
    
    private JTable itemTable;
    private DefaultTableModel model;
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<String> supplierArrayList = new ArrayList<>();
    private ArrayList<String> productArrayList = new ArrayList<>();
    private ArrayList<LineItem> itemsArrayList = new ArrayList<>();
    private Object[][] itemsList = new ArrayList[0][0];;
    private String[] columnNames = {"Item name", "Quantity", "Price per item"};
    
    private boolean isNewOrder = false;
    private boolean isEditOrder = false;
    private boolean isSelected = false;
    
    private double total = 0;
    private double cost = 0;

    private int index;
    
    public OrderPanel() {
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

        buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.X_AXIS));
        newOrderButton = new JButton("New Order"); 
        buttonPanel1.add(newOrderButton,gbc);
        newOrderButton.addActionListener(this);
        
        editItemButton = new JButton("Edit");
        editItemButton.addActionListener(this);
        buttonPanel1.add(editItemButton);
        
        removeItemButton = new JButton("Remove");
        removeItemButton.addActionListener(this);
        buttonPanel1.add(removeItemButton);
                      
        orderPanel = new JPanel();
        gbc.weightx = 1.6;
        gbc.weighty = 2.0;
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;
        addPanel(orderPanel, gbl, gbc);
        
        GridBagLayout gbl1 = new GridBagLayout();
        orderPanel.setLayout(gbl1);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;
        
        model = new DefaultTableModel(itemsList,columnNames);
        itemTable = new JTable(model);
        setItemTableSize();
        itemTable.setEnabled(false);
        itemTable.setRowHeight(30);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.getSelectionModel().addListSelectionListener(this);
        orderListPanel = new JScrollPane(itemTable);
        
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.weighty = 6.0;
        gbc1.weightx = 1;
        gbc1.gridwidth = 1;
        addPanelName("Order Items List", orderListPanel);
        addScrollPane(orderListPanel, gbl1, gbc1);
        orderPanel.add(orderListPanel);
        
        totalPricePanel = new JPanel();
        gbc1.gridy = 1;
        gbc1.weighty = 1.0;
        addPanel(totalPricePanel, gbl1, gbc1);
        orderPanel.add(totalPricePanel);
        totalPricePanel.setLayout(new BorderLayout(20, 20));
        
        makeOrderButton = new JButton("Submit");
        makeOrderButton.addActionListener(this);
        totalPricePanel.add(makeOrderButton, BorderLayout.LINE_START);
        
        totalLabel = new JLabel("Total Price: ");
        totalLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        totalPricePanel.add(totalLabel, BorderLayout.CENTER);
        
        totalField = new JTextField("0.0",10);
        totalField.setHorizontalAlignment(JTextField.RIGHT);
        totalField.setBorder(null);
        totalField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        totalField.setEditable(false);
        totalPricePanel.add(totalField, BorderLayout.LINE_END);
        
        combinePanel = new JPanel();
        gbc.weightx = 1.4;
        gbc.weighty = 2.0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        addPanel(combinePanel, gbl ,gbc);
        
        combinePanel.setLayout(new GridLayout(2, 0));
        getSupplierArrayList();
        supplierList = new JList<Object>(supplierArrayList.toArray());
        supplierList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        supplierList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        supplierList.setVisibleRowCount(getHeight());
        supplierList.setOpaque(true);
        supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierList.getSelectionModel().addListSelectionListener(this);
        supplierPanel = new JScrollPane(supplierList);
        addPanelName("Suppliers", supplierPanel);
        combinePanel.add(supplierPanel);

        
        getProductArrayList();
        productList = new JList<Object>(productArrayList.toArray());
        productList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        productList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        productList.setVisibleRowCount(getHeight());
        productList.setOpaque(true);
        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productList.getSelectionModel().addListSelectionListener(this);
        productPanel = new JScrollPane(productList);
        addPanelName("Products", productPanel);
        combinePanel.add(productPanel);

        supplierList.setEnabled(false);
        productList.setEnabled(false);
        
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
        if(isNewOrder == true){
            if(supplierList.getValueIsAdjusting()){
                productArrayList.clear();
                index = supplierList.getSelectedIndex();
                Supplier supplier = Database.getInstance().getSuppliers().get(index);
                productArrayList = new ArrayList<>();
                for(Product p : products){
                    if(p.getSupplier().equals(supplier)){
                        productArrayList.add(p.getName() + "   ");
                    }
                }
                productList.setListData(productArrayList.toArray());
            }
            
            else if(productList.getValueIsAdjusting()){
                for(Product product : products){
                    if(productList.getSelectedValue().toString().equals(product.getName()+"   ")){
                        cost = product.getCost();
                        Object[] item = {productList.getSelectedValue().toString(), 1 , cost};
                        itemsArrayList.add(new LineItem(product.getID(), 1));
                        total = total + cost;
                        totalField.setText(toString().valueOf(total));
                        model.addRow(item);
                        break;
                    }
                }
               
            }
        }
        else if(isEditOrder == true){
           ExtraPanel p = new ExtraPanel();
           String t = p.ExtraEditPanel();
           warmingMsg(t);
        }
               
        else if(itemTable.getSelectionModel().getValueIsAdjusting()){
            isSelected = true;  
            warmingMsg("hehr");
        }
        
        else
            warmingMsg("Please Select New Order to adding items");
            
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource().equals(newOrderButton)){
            initialCondition();
            model.setDataVector(null, columnNames);
            setItemTableSize();
            isNewOrder = true;
            supplierList.setEnabled(true);
            productList.setEnabled(true);
            itemTable.setEnabled(true);
        }
                
        else if(arg0.getSource().equals(editItemButton)){
            if(isSelected == true){
                isEditOrder = true;
            }
            else
                warmingMsg("Please select item to edit");
        }
        
        else if(arg0.getSource().equals(removeItemButton)){
            
        }
        
        else if(arg0.getSource().equals(makeOrderButton)){
            if(isNewOrder == true && isEditOrder == false){
                //Database.getInstance().addOrder(new Order(total, supplier, deliveryID, itemsArrayList));
                initialCondition();
                getSupplierArrayList();
                getProductArrayList();
                supplierList.setEnabled(false);
                productList.setEnabled(false);
                itemTable.setEnabled(false);
            }
            
            else if(isEditOrder == true){
                
            }
            else
                warmingMsg("Please select New Order, Edit or Remove button");
        }
        
        
    }

    public void getSupplierArrayList() {
        suppliers = Database.getInstance().getSuppliers();
        supplierArrayList.clear();
        for (Supplier s : suppliers) {
            supplierArrayList.add(s.getName() + "   ");
        }
    }
    
    public void getProductArrayList() {
        products = Database.getInstance().getProducts();
        productArrayList.clear();
        for (Product p : products) {
            productArrayList.add(p.getName() + "   ");
        }
    }
    
    public void getItemArrayList(){
        
    }
    
    public void warmingMsg(String msg) {
        JOptionPane.showMessageDialog(new JFrame(), msg, "Warming",
                JOptionPane.WARNING_MESSAGE);
    }

    public void initialCondition(){
        isNewOrder = false;
        isEditOrder = false;
        isSelected = false;
    }
    
    public void setItemTableSize(){
        itemTable.getColumnModel().getColumn(1).setMaxWidth(60);
        itemTable.getColumnModel().getColumn(2).setMinWidth(100);
        itemTable.getColumnModel().getColumn(2).setMaxWidth(100);
    }
    
    public void updateProductList(ArrayList<Product> products){
    	//TODO: This is how/where the controller will tell you the products in the model have changed
    }
    
    public void updateSupplierList(ArrayList<Supplier> suppliers){
    	supplierList.setListData(suppliers.toArray());
    }
    
    public void updateOrderList(ArrayList<Order> orders){
    	//TODO: This is how/where the controller will tell you the orders in the model have changed
    }
    
    public void addListener(RetailViewListener r){
    	listeners.add(r);
    }
}
