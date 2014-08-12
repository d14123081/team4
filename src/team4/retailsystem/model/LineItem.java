package team4.retailsystem.model;

public class LineItem {
	private int productID;
	private int quantity;

	public LineItem(int productID, int quantity) {
		this.productID = productID;
		this.quantity = quantity;
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
}
