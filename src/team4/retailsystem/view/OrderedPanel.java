package team4.retailsystem.view;

import java.awt.Color;
import java.awt.Dimension;
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
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;

public class OrderedPanel extends JPanel implements ListSelectionListener {

    private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
    private JTable ordersTable;
    private DefaultTableModel model;
    private DefaultTableCellRenderer centreRenderer;
    private JScrollPane orderScrollPanel;
    private ArrayList<Object> orderedArrayList = new ArrayList<>();
    private Object[][] orderList = null;
    private String[] columnNames = {"Order ID", "Ordered Date","Delivery Date"};
    private static int orderID;
    private ArrayList<Order> ordered = new ArrayList<>(); 
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private CalenderPanel calender;
    private int addDelivery;

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
        // TODO Auto-generated method stub
        if(addDelivery == 2)
            addDeliveryday();
        this.orderID = Integer.parseInt(model.getValueAt(ordersTable.getSelectedRow(),0).toString());
        
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
        orderedArrayList.clear();
        for(Order order : ordered){
            //if(!order.getOrderDate().equals(null)){
                Object[] item = {""+order.getID(),sdf.format(order.getOrderDate()),""};
                model.addRow(item);
            //} 
        }
    }
    
    public void addDeliveryday(){
        calender = new CalenderPanel();
        Object[] t = {calender};
        JOptionPane o = new JOptionPane(t,JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_OPTION);
        JDialog dialog = o.createDialog("Delivery day");
        dialog.setSize(new Dimension(400, 350));
        dialog.setVisible(true);
        if(o.getValue().equals(0)){
            model.setValueAt(calender.getDate(), ordersTable.getSelectedRow(), 2);
        }
    }
    
    public void addListener(RetailViewListener r) {
        listeners.add(r);
    }
    
}
