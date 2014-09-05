package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import team4.retailsystem.model.Delivery;
import team4.retailsystem.model.Supplier;

public class DeliveryTest {

	@Test
	public void testConstructorSG() {
		String supplierData = "sampleData";
		String newSupplierData = "newSupplierData";
		int orderID = 5;
		int newOrderID = 16;
		Date date = new Date();
		Supplier supplier1 = new Supplier(supplierData, supplierData, supplierData, supplierData);
		Supplier supplier2 = new Supplier(newSupplierData, newSupplierData, newSupplierData, newSupplierData);
		Delivery d = new Delivery(supplier1, orderID, date, 20);

		assertEquals(date, d.getDate());
		assertEquals(20, d.getDeliveryID());
		assertEquals(orderID, d.getOrderID());
		assertEquals(supplier1.getName(), d.getSupplier().getName());

		Date date2 = new Date();
		d.setDate(date2);
		d.setOrderID(newOrderID);
		d.setSupplier(supplier2);

		assertEquals(date2, d.getDate());
		assertEquals(20, d.getDeliveryID());
		assertEquals(newOrderID, d.getOrderID());
		assertEquals(supplier2.getName(), d.getSupplier().getName());
	}
}
