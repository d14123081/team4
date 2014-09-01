package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;

/**
 * jUnit tests for Invoice.java
 * 
 * @author szymon
 */
public class InvoiceTest {

	@Test
	public void testCostCalculation() {
		String tempData = "unimportant";
		double p1cost = 12.50;
		double p1markup = .2;
		int stockLevel = 20;

		double p2cost = 20.99;
		double p2markup = .25;

		Supplier supplier = new Supplier(tempData, tempData, tempData, tempData);
		Customer customer = new Customer(tempData, tempData, tempData, tempData);

		Product product1 = new Product(tempData, p1cost, p1markup, stockLevel,
				supplier);
		Product product2 = new Product(tempData, p2cost, p2markup, stockLevel,
				supplier);

		Database db = Database.getInstance("testSystem");
		db.addProduct(product1);
		db.addProduct(product2);

		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		LineItem invoiceItemOne = new LineItem(1, 12);
		LineItem invoiceItemTwo = new LineItem(2, 5);
		lineItems.add(invoiceItemOne);
		lineItems.add(invoiceItemTwo);
		Invoice invoice = new Invoice(lineItems, customer);

		double targetCost = invoiceItemOne.getQuantity() * product1.getPrice()
				+ invoiceItemTwo.getQuantity() * product2.getPrice();

		assertNotNull(invoice.getCost());
		assertEquals(targetCost, invoice.getCost(), 0.001);
	}

	@After
	public void cleanup() {
		Database db = Database.getInstance("testSystem");
		
		for (Product p : db.getProducts()) {
			db.deleteProduct(p);
		}
	}
}
