package team4.retailsystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;

public class OrderedPanel extends JPanel implements ListSelectionListener {

    private JTable ordersTable;
    private DefaultTableModel model;
    private JScrollPane orderScrollPanel;
    private ArrayList<Object> orderedArrayList = new ArrayList<>();
    private Object[][] orderList = null;
    private String[] columnNames = {"Order ID", "Ordered Date","Delivery Date"};
    private int orderID;
    private ArrayList<Order> ordered = new ArrayList<>(); 
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public OrderedPanel() {
        this.ordered = Database.getInstance().getOrders();
        this.setRequestFocusEnabled(true);
        this.setSize(new Dimension(100, 100));
        model = new DefaultTableModel(orderList,columnNames);
        ordersTable = new JTable(model){
            Class[] columnTypes = new Class[] {
                    Integer.class, Calendar.class, Calendar.class
                };
            boolean[] canEdit = new boolean[]{
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
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
        // TODO Auto-generated method stub
        orderID = Integer.parseInt(model.getValueAt(ordersTable.getSelectedRow(),0).toString());
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
        return orderID;
    }
    
    public void getOrderedArrayList(){
        orderedArrayList.clear();
        for(Order order : ordered){
            //model.addRow(order.getID(),sdf.format(order.getDeliveryDate()), );
        }
    }
}
