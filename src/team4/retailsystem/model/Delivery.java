package team4.retailsystem.model;
import java.util.Calendar;

public class Delivery {
	static int numberOfDeliveries = 0;

	private Calendar deliveryDate;
	private int deliveryID;
	private int orderID;
	private Supplier supplier;

	public Delivery(Supplier supplier, int orderID) {
		this.deliveryID = (numberOfDeliveries++) + 1;
		this.supplier = supplier;
		this.orderID = orderID;
		deliveryDate = Calendar.getInstance();
	}

	public Calendar getDate() {
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
}
