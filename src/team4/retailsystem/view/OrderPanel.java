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
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;

public class OrderPanel extends JPanel implements ListSelectionListener,
        ActionListener {

    private ArrayList<RetailViewListener> listeners =
            new ArrayList<RetailViewListener>();

    private JPanel buttonPanel1;
    private JScrollPane orderListPanel;
    private JPanel combinePanel;
    private JPanel orderPanel;
    private JPanel totalPricePanel;
    private JScrollPane supplierPanel;
    private JScrollPane productPanel;
    private JButton newOrderButton;
    private JButton viewOrderedButton;
    private JButton makeOrderButton;
    private JButton editItemButton;
    private JButton removeItemButton;
    private JLabel totalLabel;
    private JTextField totalField;
    private JButton enter;

    private JList<Object> supplierList;
    private JList<Object> productList;

    private JTable itemTable;
    private DefaultTableModel model;
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<String> supplierArrayList = new ArrayList<>();
    private ArrayList<String> productArrayList = new ArrayList<>();
    private ArrayList<LineItem> itemsArrayList = new ArrayList<>();
    private Object[][] itemsList = null;
    private String[] columnNames =
            { "Item name", "Quantity", "Price per item" };
    private JTextField quantity;
    private Supplier supplier;
    private boolean isNewOrder = false;
    private boolean isSelected = false;
    private ExtraPanel p;

    private double total = 0;
    private double cost = 0;
    private int index;


    public OrderPanel() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        quantity = new JTextField();
        quantity.addActionListener(this);
        enter = new JButton();
        enter.addActionListener(this);
        buttonPanel1 = new JPanel();
        gbc.ipady = 30;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        addPanel(buttonPanel1, gbl, gbc);

        buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.X_AXIS));
        newOrderButton = new JButton("New Order");
        buttonPanel1.add(newOrderButton, gbc);
        newOrderButton.addActionListener(this);

        editItemButton = new JButton("Edit");
        editItemButton.addActionListener(this);
        buttonPanel1.add(editItemButton);

        removeItemButton = new JButton("Remove");
        removeItemButton.addActionListener(this);
        buttonPanel1.add(removeItemButton);

        viewOrderedButton = new JButton("Orders");
        buttonPanel1.add(viewOrderedButton, gbc);
        viewOrderedButton.addActionListener(this);

        orderPanel = new JPanel();
        gbc.weightx = 1.2;
        gbc.weighty = 2.0;
        gbc.gridy = 1;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.gridwidth = 1;
        addPanel(orderPanel, gbl, gbc);

        GridBagLayout gbl1 = new GridBagLayout();
        orderPanel.setLayout(gbl1);
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.fill = GridBagConstraints.BOTH;

        model = new DefaultTableModel(itemsList, columnNames);
        itemTable = new JTable(model);
        setItemTableSize();
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

        totalField = new JTextField("0.0", 10);
        totalField.setHorizontalAlignment(JTextField.RIGHT);
        totalField.setBorder(null);
        totalField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        totalField.setEditable(false);
        totalPricePanel.add(totalField, BorderLayout.LINE_END);

        combinePanel = new JPanel();
        gbc.weightx = 1.8;
        gbc.weighty = 2.0;
        gbc.gridx = 1;
        gbc.gridy = 1;
        addPanel(combinePanel, gbl, gbc);

        combinePanel.setLayout(new GridLayout(2, 0));
        supplierList = new JList<Object>(supplierArrayList.toArray());
        getSupplierArrayList();
        supplierList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        supplierList.setFont(new Font("Tahoma", Font.PLAIN, 18));
        supplierList.setVisibleRowCount(getHeight());
        supplierList.setOpaque(true);
        supplierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        supplierList.getSelectionModel().addListSelectionListener(this);
        supplierPanel = new JScrollPane(supplierList);
        addPanelName("Suppliers", supplierPanel);
        combinePanel.add(supplierPanel);

        productList = new JList<Object>(productArrayList.toArray());
        getProductArrayList();
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

    public void addPanel(JPanel panel, GridBagLayout gbl, GridBagConstraints gbc) {
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }

    public void addScrollPane(JScrollPane panel, GridBagLayout gbl,
            GridBagConstraints gbc) {
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }

    public void addPanelName(String panelName, JScrollPane panel) {
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        if (supplierList.getValueIsAdjusting() == true) {
            productArrayList.clear();
            index = supplierList.getSelectedIndex();
            supplier =
                    Database.getInstance().getSuppliers().get(index);
            productArrayList.clear();
            for (Product p : products) {
                if (p.getSupplier().equals(supplier)) {
                    productArrayList.add(p.getName() + "   ");
                }
            }
            productList.setEnabled(true);
            productList.setListData(productArrayList.toArray());
            
        } else if (productList.getValueIsAdjusting() == true) {
            for (Product product : products) {
                if (productList.getSelectedValue().toString()
                        .equals(product.getName() + "   ")) {
                    cost = product.getCost();
                    Object[] item =
                            { productList.getSelectedValue().toString(), 1,
                                    cost };
                    itemsArrayList.add(new LineItem(product.getID(), 1));
                    total = total + cost;
                    totalField.setText(toString().valueOf(total));
                    model.addRow(item);
                    model.fireTableDataChanged();
                    supplierList.setEnabled(false);
                    break;
                }
            }

        } else if (itemTable.getSelectionModel().getValueIsAdjusting() == true) {
            isSelected = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource().equals(newOrderButton)) {
            initialCondition();
            clearItemList();
            setItemTableSize();
            isNewOrder = true;
            supplierList.setEnabled(true);
            //productList.setEnabled(true);
            getSupplierArrayList();
            getProductArrayList();
        }
        
        else if (arg0.getSource().equals(viewOrderedButton)) {

        }
        
        else if (arg0.getSource().equals(editItemButton)) {
            if (isSelected == true) {
                editQuantity();
                isSelected = false;
            } else
                warmingMsg("Please select item to edit");
            model.fireTableDataChanged();
        }

        else if (arg0.getSource().equals(removeItemButton)) {
            if (isSelected == true) {
                itemsArrayList.remove(itemTable.getSelectedRow());
                model.removeRow(itemTable.getSelectedRow());
                model.fireTableDataChanged();
                isSelected = false;
                
            } else
                warmingMsg("Please select item to edit");
        }

        else if (arg0.getSource().equals(makeOrderButton)) {
            if (isNewOrder == true) {
                //Database.getInstance().addOrder(new Order(total, itemsArrayList));
                initialCondition();
                getSupplierArrayList();
                getProductArrayList();
                supplierList.setEnabled(false);
                productList.setEnabled(false);
                clearItemList();
            }
            else
                warmingMsg("Please select New Order");
        }

    }

    public void getSupplierArrayList() {
        suppliers = Database.getInstance().getSuppliers();
        supplierArrayList.clear();
        for (Supplier s : suppliers) {
            supplierArrayList.add(s.getName() + "   ");
        }
        supplierList.setListData(supplierArrayList.toArray());
    }

    public void getProductArrayList() {
        products = Database.getInstance().getProducts();
        productArrayList.clear();
        for (Product p : products) {
            productArrayList.add(p.getName() + "   ");
        }
        productList.setListData(productArrayList.toArray());
    }

    public void warmingMsg(String msg) {
        JOptionPane.showMessageDialog(new JFrame(), msg, "Warming",
                JOptionPane.WARNING_MESSAGE);
    }

    public void initialCondition() {
        isNewOrder = false;
        isSelected = false;
        total = 0;
    }

    public void setItemTableSize() {
        itemTable.getColumnModel().getColumn(1).setMaxWidth(60);
        itemTable.getColumnModel().getColumn(2).setMinWidth(100);
        itemTable.getColumnModel().getColumn(2).setMaxWidth(100);
    }

    private void clearItemList() {
        itemsList = null;
        model.setDataVector(itemsList, columnNames);
        model.fireTableDataChanged();
    }

    private void updateQuantity(int quantity){
        total =
                total
                        + Double.parseDouble(model.getValueAt(
                                itemTable.getSelectedRow(), 2)
                                .toString())
                        * (quantity - 1);
        itemsArrayList.get(itemTable.getSelectedRow()).setQuantity(quantity);
        model.setValueAt(quantity, itemTable.getSelectedRow(), 1);
        totalField.setText(toString().valueOf(total));
    }
    
    public void updateProductList(ArrayList<Product> products){
    	productList.setListData(products.toArray());
    }
    
    public void updateSupplierList(ArrayList<Supplier> supliers){
    	supplierList.setListData(suppliers.toArray());
    }
    
    public void addListener(RetailViewListener listener) {
        // TODO Auto-generated method stub

    }
    
    public void editQuantity(){
        p = new ExtraPanel();
        Object[] t = {p};
        JOptionPane o = new JOptionPane(t,JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_OPTION);
        JDialog dialog = o.createDialog(null, "Width 100");
        dialog.setVisible(true);
        if(o.getValue().equals(0)){
            updateQuantity(p.getValue());
        }
    }
}
