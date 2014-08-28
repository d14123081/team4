package team4.retailsystem.model;

public class LineItem {

	private int productID;
	private int quantity;
	private int orderID;	//foreign key for the db
	private int id;
	
	
	public LineItem(int productID, int quantity) {
		this.productID = productID;
		this.quantity = quantity;
		this.id = -1;
	}

	public LineItem(int productID, int quantity, int id, int orderID) {
		this.productID = productID;
		this.quantity = quantity;
		this.orderID = orderID;
		this.id = id;
	}

	// *******getters and setters***********

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getID(){
		return id;
	}
	
	public int getOrderID(){
		return orderID;
	}
}
