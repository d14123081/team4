package team4.retailsystem.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
    private JPanel buttonPanel2;
    private JScrollPane orderListPanel;
    private JPanel combinePanel;
    private JPanel orderPanel;
    private JPanel totalPricePanel;
    private JScrollPane supplierPanel;
    private JScrollPane productPanel;
    private JButton newOrderButton;
    private JButton OrderedButton;
    private JButton makeOrderButton;
    private JButton editItemButton;
    private JButton removeItemButton;
    private JLabel totalLabel;
    private JTextField totalField;

    private JList<Object> supplierList;
    private JList<Object> productList;

    private JTable itemTable;
    private DefaultTableModel model;
    private DecimalFormat df = new DecimalFormat("0.00");
    private ArrayList<Supplier> suppliers = Database.getInstance()
            .getSuppliers();
    private ArrayList<Product> products = Database.getInstance().getProducts();
    private ArrayList<Order> orders = Database.getInstance().getOrders();
    private ArrayList<String> supplierArrayList = new ArrayList<>();
    private ArrayList<String> productArrayList = new ArrayList<>();
    private ArrayList<LineItem> itemsArrayList = new ArrayList<>();
    private Object[][] itemsList = null;
    private String[] columnNames =
            { "Item name", "Quantity", "Price per item" };
    private Supplier supplier;
    private boolean isSelected = false;
    private ExtraPanel p;

    private double total = 0;
    private double cost = 0;
    private int index;


    private OrderedPanel ordered = new OrderedPanel();
    private CalenderPanel calender;

    @SuppressWarnings("serial")
    public OrderPanel() {
        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        buttonPanel1 = new JPanel();
        buttonPanel1
                .setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
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

        OrderedButton = new JButton("Orders");
        buttonPanel1.add(OrderedButton, gbc);
        OrderedButton.addActionListener(this);

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

        model = new DefaultTableModel(itemsList, columnNames) {

            boolean[] canEdit = new boolean[] { false, false, false };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        itemTable = new JTable(model);
        setItemTableSize();
        itemTable.setRowHeight(30);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.getSelectionModel().addListSelectionListener(this);
        itemTable.getTableHeader().setReorderingAllowed(false);
        orderListPanel = new JScrollPane(itemTable);

        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.weighty = 8.0;
        gbc1.weightx = 1;
        gbc1.gridwidth = 1;
        addPanelName("Order Items List", orderListPanel);
        addScrollPane(orderListPanel, gbl1, gbc1);
        orderPanel.add(orderListPanel);

        buttonPanel2 = new JPanel();
        buttonPanel2.setLayout(new GridLayout(2,0));
        
        gbc1.ipady = 10;
        gbc1.gridy = 1;
        gbc1.weighty = 1.0;
        addPanel(buttonPanel2, gbl1, gbc1);
        orderPanel.add(buttonPanel2);
        totalPricePanel = new JPanel();
        buttonPanel2.add(totalPricePanel);
        totalPricePanel.setLayout(new BorderLayout());

        totalLabel = new JLabel("Total Price: ");
        totalLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        totalPricePanel.add(totalLabel, BorderLayout.LINE_START);

        totalField = new JTextField("0.00", 10);
        totalField.setHorizontalAlignment(JTextField.RIGHT);
        totalField.setBorder(null);
        totalField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        totalField.setEditable(false);
        totalPricePanel.add(totalField, BorderLayout.LINE_END);

        JPanel button = new JPanel();
        button.setLayout(new GridLayout(1,3));
        makeOrderButton = new JButton("Submit");
        makeOrderButton.addActionListener(this);
        button.add(makeOrderButton);
        
        editItemButton = new JButton("Edit");
        editItemButton.addActionListener(this);
        button.add(editItemButton);

        removeItemButton = new JButton("Remove");
        removeItemButton.addActionListener(this);
        button.add(removeItemButton);
        setButton(false);
        buttonPanel2.add(button);
        
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

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        if (supplierList.getValueIsAdjusting() == true) {
            productArrayList.clear();
            index = supplierList.getSelectedIndex();
            supplier = Database.getInstance().getSuppliers().get(index);
            for (Product p : products) {
                if (p.getSupplier().getID() == supplier.getID()) {
                    productArrayList.add(p.getName() + "   ");
                }
            }
            productList.setEnabled(true);
            productList.setListData(productArrayList.toArray());

        } else if (productList.getValueIsAdjusting() == true) {
            for (Product product : products) {
                if(checkProductArrayList(itemsArrayList) == false){
                    if (productList.getSelectedValue().toString()
                            .equals(product.getName() + "   ")) {
                        cost = product.getCost();
                        Object[] item =
                                { productList.getSelectedValue().toString(), 1,
                                        cost };
                        itemsArrayList.add(new LineItem(product.getID(), 1));
                        total = total + cost;
                        totalField.setText(df.format(total));
                        model.addRow(item);
                        model.fireTableDataChanged();
                        supplierList.setEnabled(false);
                        break;
                    }
                }
                
            }
        } else if (itemTable.getSelectionModel().getValueIsAdjusting() == true) {
            isSelected = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource().equals(newOrderButton)) {
            itemsArrayList.clear();
            initialCondition();
            clearItemList();
            setItemTableSize();
            productList.setEnabled(false);
            supplierList.setEnabled(true);
            getSupplierArrayList();
            setButton(true);
        }

        else if (arg0.getSource().equals(OrderedButton)) {
            supplierList.setEnabled(false);
            productList.setEnabled(false);
            setButton(false);
            ordersList();
        }


        else if (arg0.getSource().equals(editItemButton)) {
            if (isSelected == true) {
                editQuantity();
                itemTable.clearSelection();
                model.fireTableDataChanged();
                isSelected = false;
            } else
                warmingMsg("Please select item to edit");
        }

        else if (arg0.getSource().equals(removeItemButton)) {
            if (isSelected == true) {
                total =
                        total
                                - Double.parseDouble(model.getValueAt(
                                        itemTable.getSelectedRow(), 1)
                                        .toString())
                                * Double.parseDouble(model.getValueAt(
                                        itemTable.getSelectedRow(), 2)
                                        .toString());
                totalField.setText(df.format(total));
                itemsArrayList.remove(itemTable.getSelectedRow());
                model.removeRow(itemTable.getSelectedRow());
                model.fireTableDataChanged();
                isSelected = false;

            } else
                warmingMsg("Please select item to edit");
        }

        else if (arg0.getSource().equals(makeOrderButton)) {
            if (model.getRowCount() > 0) {
                for (RetailViewListener r : listeners) {
                    System.out.print(1);
                    r.clickCreateOrder(total, itemsArrayList, supplier, 1);   
                }
                initialCondition();
                totalField.setText("0.00");
                supplierList.setEnabled(false);
                productList.setEnabled(false);
                itemsArrayList.clear();
                setButton(false);
                clearItemList();
            } else
                warmingMsg("Please add item to make a new order");
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
    
    public boolean checkProductArrayList(ArrayList<LineItem> itemsArrayList){
        boolean isSame = false;
        for(LineItem items : itemsArrayList){
            Product product = Database.getInstance().getProduct(items.getProductID());
            if(productList.getSelectedValue().toString().equals(product.getName()+ "   ")){
                isSame = true;
                break;
            }
        }
        return isSame;
    }

    public void warmingMsg(String msg) {
        JOptionPane.showMessageDialog(new JFrame(), msg, "Warming",
                JOptionPane.WARNING_MESSAGE);
    }

    public void initialCondition() {
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
        totalField.setText("0.00");
    }

    private void updateQuantity(int quantity) {
        total +=
                (Double.parseDouble(model.getValueAt(
                        itemTable.getSelectedRow(), 2).toString()) * (quantity - 1));
        itemsArrayList.get(itemTable.getSelectedRow()).setQuantity(quantity);
        model.setValueAt(quantity, itemTable.getSelectedRow(), 1);
        totalField.setText(df.format(total));
    }

    public void editQuantity() {
        p = new ExtraPanel();
        Object[] t = { p };
        JOptionPane o =
                new JOptionPane(t, JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.OK_OPTION);
        JDialog dialog = o.createDialog("Edit Quantity");
        dialog.setVisible(true);
        if (o.getValue().equals(0)) {
            updateQuantity(p.getValue());
        }
    }

    public void ordersList() {
        clearItemList();
        Object[] t = { ordered, calender };
        JOptionPane o =
                new JOptionPane(t, JOptionPane.PLAIN_MESSAGE,
                        JOptionPane.OK_OPTION);
        JDialog d = o.createDialog(null, "Orders List");
        d.setSize(480, 550);
        d.setVisible(true);
        if (o.getValue().equals(0)) {
            if(ordered.getOrderID() > 0){
                clearItemList();
                total = 0;
                orders = Database.getInstance().getOrders();
                for (Order order : orders) {
                    if (order.getID() == ordered.getOrderID()) {
                        itemsArrayList = order.getLineItems();
                        for (LineItem items : order.getLineItems()) {
                            for (Product product : products) {
                                if (product.getID() == items.getProductID()) {
                                    Object[] item =
                                            { product.getName(),
                                                    items.getQuantity(),
                                                    product.getCost() };
                                    total += product.getCost()
                                             * items.getQuantity();
                                    model.addRow(item);
                                    model.fireTableDataChanged();
                                }
                            }
                        }
                        totalField.setText(df.format(total));
                    }
                }
            }
        }
    }

    /**
     * A method that clears temp fields on logout.
     */
    public void logout() {
        initialCondition();
        clearItemList();
        productList.clearSelection();
        supplierList.clearSelection();
        supplierList.setEnabled(false);
        productList.setEnabled(false);
    }

    public void setButton(boolean isVisible){
        makeOrderButton.setVisible(isVisible);
        editItemButton.setVisible(isVisible);
        removeItemButton.setVisible(isVisible);
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
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }
    
    public void updateOrderList(ArrayList<Order> orders) {
        ordered.updateOrderList(orders);
    }

    public void updateProductList(ArrayList<Product> products) {
        getProductArrayList();
        productList.setListData(products.toArray());
        productList.validate();
    }

    public void updateSupplierList(ArrayList<Supplier> supliers) {
        supplierList.setListData(suppliers.toArray());
    }

    public void addListener(RetailViewListener r) {
        listeners.add(r);
        ordered.addListener(r);
    }
}
