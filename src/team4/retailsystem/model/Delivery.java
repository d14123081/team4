package team4.retailsystem.model;

import java.util.Date;

public class Delivery {
	static int numberOfDeliveries = 0;

	private Date deliveryDate;
	private int deliveryID;
	private int orderID;
	private Supplier supplier;

	public Delivery(Supplier supplier, int orderID) {
		this.deliveryID = ++numberOfDeliveries;
		this.supplier = supplier;
		this.orderID = orderID;
		deliveryDate = new Date();
	}

	public Date getDate() {
		return deliveryDate;
	}

	public int getDeliveryID() {
		return deliveryID;
	}

	public int getOrderID() {
		return orderID;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setDeliveryID(int deliveryID) {
		this.deliveryID = deliveryID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
