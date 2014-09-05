package team4.retailsystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Delivery;
import team4.retailsystem.model.Order;

public class OrderedPanel extends JPanel implements ListSelectionListener, ActionListener {

    private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
    private JTable ordersTable;
    private DefaultTableModel model;
    private DefaultTableCellRenderer centreRenderer;
    private JScrollPane orderScrollPanel;
    private ArrayList<Delivery> deliveries = Database.getInstance().getDeliveries();
    private ArrayList<Order> orders = Database.getInstance().getOrders();
    private Object[][] orderList = null;
    private String[] columnNames = {"Order ID", "Ordered Date","Delivery Date"};
    private static int orderID;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private CalenderPanel calender;
    private JOptionPane o;
    private JPanel buttonPanel;
    private JButton removeOrder;
    private JButton addDelivery;
    private boolean isSelected = false;

    public OrderedPanel() {

        GridBagLayout gbl = new GridBagLayout();
        this.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.fill = GridBagConstraints.BOTH;
        
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        gbc.ipady = 30;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        addPanel(buttonPanel, gbl, gbc);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        removeOrder = new JButton("Remove Order");
        removeOrder.addActionListener(this);
        buttonPanel.add(removeOrder);
        addDelivery = new JButton("Add Delivery");
        addDelivery.addActionListener(this);
        buttonPanel.add(addDelivery);
        
        gbc.gridy = 1;
        gbc.gridheight = 8;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        model = new DefaultTableModel(orderList,columnNames);
        ordersTable = new JTable(model){
            boolean[] canEdit = new boolean[]{
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        ordersTable.getTableHeader().setReorderingAllowed(false);
        centreRenderer = new DefaultTableCellRenderer();
        centreRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        ordersTable.setRowSelectionAllowed(true);
        ordersTable.getColumnModel().getColumn(0).setCellRenderer(centreRenderer);
        ordersTable.getColumnModel().getColumn(1).setCellRenderer(centreRenderer);
        ordersTable.getColumnModel().getColumn(2).setCellRenderer(centreRenderer);
        getOrderedArrayList();
        setItemTableSize();
        ordersTable.setRowHeight(30);
        ordersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ordersTable.getSelectionModel().addListSelectionListener(this);
        orderScrollPanel = new JScrollPane(ordersTable);
        addScrollPane(orderScrollPanel, gbl, gbc);
        
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) { 
        isSelected = true;
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
    
    public void addPanel(JPanel panel, GridBagLayout gbl, GridBagConstraints gbc) {
        gbl.setConstraints(panel, gbc);
        this.add(panel);
    }
    
    public void setItemTableSize() {
        ordersTable.getColumnModel().getColumn(0).setMaxWidth(100);
        ordersTable.getColumnModel().getColumn(1).setMaxWidth(200);
        ordersTable.getColumnModel().getColumn(2).setMaxWidth(200);
    }
    
    public int getOrderID(){
        if(isSelected == false)
            orderID = 0;
        else
            orderID = Integer.parseInt(model.getValueAt(ordersTable.getSelectedRow(),0).toString());
        return orderID;
    }
    
    
    public void getOrderedArrayList(){ 
        orderList = null;
        model.setDataVector(orderList, columnNames);
        for(Order order : orders){
            String deliveryDate = "";
            for(Delivery delivery : deliveries){
                if(order.getID() == delivery.getOrderID()){
                    deliveryDate = sdf.format(delivery.getDate());
                    break;
                }
            }
            Object[] item = {""+order.getID(),sdf.format(order.getOrderDate()),deliveryDate};
            model.addRow(item); 
        }
    }
    
    public void addDeliveryDay() {
        for (Order order : orders) {
            if (order.getID() == Integer.parseInt(model.getValueAt(ordersTable.getSelectedRow(),0).toString())) {
                for (RetailViewListener r : listeners) {
                    try {
                        r.clickAddDelivery(order.getSupplier(), order.getID(),
                                sdf.parse(calender.getDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public void updateDeliveryDay(){
        for (Delivery delivery : deliveries) {
            if(delivery.getOrderID() == Integer.parseInt(model.getValueAt(ordersTable.getSelectedRow(),0).toString())) {
                try {
                    delivery.setDate(sdf.parse(calender.getDate()));
                    for(RetailViewListener r : listeners){
                        r.clickUpdateDelivery(delivery);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void addListener(RetailViewListener r) {
        listeners.add(r);
    }

    public
    void setOrderedType(int type){
        
    }
    public void updateOrderList(ArrayList<Order> orders) {
        this.orders = orders;
        getOrderedArrayList();
        model.fireTableDataChanged();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource().equals(addDelivery)){
            calender = new CalenderPanel();
            Object[] t = {calender};
            o = new JOptionPane(t,JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_OPTION);
            JDialog dialog = o.createDialog("Delivery day");
            dialog.setSize(new Dimension(400, 350));
            dialog.setVisible(true);
            if(o.getValue().equals(0)){
                
                if(model.getValueAt(ordersTable.getSelectedRow(), 2).equals("")){
                    addDeliveryDay();
                }
                else{
                    updateDeliveryDay();
                }
                model.setValueAt(calender.getDate(), ordersTable.getSelectedRow(), 2);    
            }
            
        }
        
        else if(arg0.getSource().equals(removeOrder)){
            for(RetailViewListener r : listeners){
                String d = model.getValueAt(ordersTable.getSelectedRow(),2).toString();
                r.clickDeleteOrder(Integer.parseInt(model.getValueAt(ordersTable.getSelectedRow(),0).toString()), d);
            }
            model.removeRow(ordersTable.getSelectedRow());
        }
        ordersTable.clearSelection();
        model.fireTableDataChanged();
        isSelected = false;
    }
    
}
