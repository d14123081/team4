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
		// TODO: Ugly hack, shouldn't call database like this here. Should maybe
		// change LineItem to have costPerUnit as a property
    	double tempCost=0.0;
    	Database db = Database.getInstance();
    	for(LineItem item:items){
    		tempCost+= item.getQuantity()* db.getProductById(item.getProductID()).getCost();
    	}
    	return tempCost;
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

}
