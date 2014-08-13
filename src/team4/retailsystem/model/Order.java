package team4.retailsystem.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    private static int orderNumber = 0;
    private Supplier supplier;
    private Date date;
    private double cost;
    private int deliveryID;
    private int ID;
    private ArrayList<LineItem> items;
    private SimpleDateFormat dateformat = new SimpleDateFormat("dd/M/yyyy");

    public Order(double cost, Supplier supplier, int deliveryID, ArrayList<LineItem> items) 
    {
        // TODO Auto-generated constructor stub
    	this.cost = cost;
        this.supplier = supplier;
        ID = ++orderNumber;
        this.date = new Date();
        this.deliveryID = deliveryID;
        this.items = items;
    }
    
    public ArrayList<LineItem> getLineItems()
    {
    	return items;
    }
    
    public void setLineItems(ArrayList<LineItem> items)
    {
    	this.items = items;
    }
    
    public int getID()
    {
    	return ID;
    }
    
    public double getCost()
    {
    	return cost;
    }
    
    public void setCost(double cost)
    {
    	this.cost = cost;
    }
    
    public int getDeliveryID()
    {
    	return deliveryID;
    }
    
    public void setDeliveryID(int deliveryID)
    {
    	this.deliveryID = deliveryID;
    }

    
    public Supplier getSupplier(){
        return this.supplier;
    }

    public Date getDeliveryDate() {
        return this.date;
    }

    public void setDeliveryDate(Date date) {
        this.date = date;
    }

    public String ViewCurrentOrderingForCustomer(Order order) {
        String text = null;
        if (order.isOrdered() == false) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nCustomer Name: "
                            + order.getCustomer().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\nEstimate delivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }

    public String ViewOrderedForCustomer(Order order) {
        String text = null;
        if (order.isOrdered() == true) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nCustomer Name: "
                            + order.getCustomer().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\ndelivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }
    
    public String ViewCurrentOrderingForAdmin(Order order) {
        String text = null;
        if (order.isOrdered() == false) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nSupplier Name: "
                            + order.getSupplier().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\nEstimate delivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }

    public String ViewOrderedForAdmin(Order order) {
        String text = null;
        if (order.isOrdered() == true) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nCustomer Name: "
                            + order.getSupplier().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\ndelivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }

}
