package team4.retailsystem.view;

import java.util.ArrayList;

import javax.swing.JPanel;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Product;

public class PredictionPanel extends JPanel{

    private ArrayList<Order> orders = Database.getInstance().getOrders();
    private ArrayList<Invoice> invoices = Database.getInstance().getInvoices();
    private ArrayList<Product> products = Database.getInstance().getProducts();
    
    public PredictionPanel() {
        // TODO Auto-generated constructor stub
    }

}
