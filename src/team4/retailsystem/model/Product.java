package team4.retailsystem.model;

public class Product {

	private double costs;
	private Date date;
	private int deliveryID;
	private int ID;
	private LineItem lineItems;
	private Supplier supplier;
	
	public Product(double costs, Date date, int deliveryID, int ID, LineItem LineItems, Supplier supplier) {
		this.costs = costs;
		this.date = date;
		this.deliveryID = deliveryID;
		this.ID = ID;
		this.lineItems = lineItems;
		this.supplier = supplier;

	}
	
	
	//********getters and setters*********
	
	public double getCosts(){
		return costs;
	}
	
	public void setCosts(double costs){
		this.costs = costs;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDeliveryID() {
		return deliveryID;
	}

	public void setDeliveryID(int deliveryID) {
		this.deliveryID = deliveryID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public LineItem getLineItems() {
		return lineItems;
	}

	public void setLineItems(LineItem lineItems) {
		this.lineItems = lineItems;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
