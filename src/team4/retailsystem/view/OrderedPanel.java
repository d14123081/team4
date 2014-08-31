package team4.retailsystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;

public class OrderedPanel extends JPanel implements ListSelectionListener {

    private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
    private JTable ordersTable;
    private DefaultTableModel model;
    private DefaultTableCellRenderer centreRenderer;
    private JScrollPane orderScrollPanel;
    private ArrayList<Object> orderedArrayList = new ArrayList<>();
    private ArrayList<Delivery> deliveries = Database.getInstance().getDeliveries();
    private ArrayList<Order> orders = Database.getInstance().getOrders();
    private Object[][] orderList = null;
    private String[] columnNames = {"Order ID", "Ordered Date","Delivery Date"};
    private static int orderID;
    private ArrayList<Order> ordered = new ArrayList<>(); 
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private CalenderPanel calender;
    private int addDelivery;
    private JOptionPane o;

    public OrderedPanel(int addDelivery) {
        this.addDelivery = addDelivery;
        this.ordered = Database.getInstance().getOrders();
        model = new DefaultTableModel(orderList,columnNames);
        ordersTable = new JTable(model){
            boolean[] canEdit = new boolean[]{
                    false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
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
        addPanelName("Ordered List", orderScrollPanel);
        this.add(orderScrollPanel);
        
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        this.orderID = Integer.parseInt(model.getValueAt(ordersTable.getSelectedRow(),0).toString());
        if(addDelivery == 2){
            calender = new CalenderPanel();
            Object[] t = {calender};
            o = new JOptionPane(t,JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_OPTION);
            JDialog dialog = o.createDialog("Delivery day");
            dialog.setSize(new Dimension(400, 350));
            dialog.setVisible(true);
            if(o.getValue().equals(0)){
                model.setValueAt(calender.getDate(), ordersTable.getSelectedRow(), 2);
                if(model.getValueAt(ordersTable.getSelectedRow(), 2).equals("")){
                    addDeliveryDay();
                }
                else{
                    
                }
            }
            
        }        
    }

    public void addPanelName(String panelName, JScrollPane panel) {
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setBorder(BorderFactory.createTitledBorder(panelName));
    }
    
    public void setItemTableSize() {
        ordersTable.getColumnModel().getColumn(0).setMaxWidth(100);
        ordersTable.getColumnModel().getColumn(1).setMaxWidth(200);
        ordersTable.getColumnModel().getColumn(2).setMaxWidth(200);
    }
    
    public int getOrderID(){
        return this.orderID;
    }
    
    
    public void getOrderedArrayList(){
        //orderedArrayList.clear();
        
        for(Order order : ordered){
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
            if (order.getID() == orderID) {
                for (RetailViewListener r : listeners) {
                    try {
                        r.clickAddDelivery(order.getSupplier(), orderID,
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
            if(delivery.getOrderID() == orderID) {
                try {
                    delivery.setDate(sdf.parse(calender.getDate()));
                    for(RetailViewListener r : listeners){
                        r.clickUpdateDelivery(delivery);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void addListener(RetailViewListener r) {
        listeners.add(r);
    }

    public void updateOrderList(ArrayList<Order> orders) {
        // TODO Auto-generated method stub
        
    }
    
}
