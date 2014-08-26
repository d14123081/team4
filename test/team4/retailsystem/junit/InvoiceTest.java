package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.PermanentDatabase;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;

public class InvoiceTest {

	@Test
	public void testCostCalculation() {
		String p1name = "product 1";
		double p1cost = 12.50;
		double p1markup = .2;
		int p1stockLevel = 20;
		
		Supplier supplier = new Supplier("supplier 1", "supplier@gmail.com", "supplier street", "489702987");
		
		String p2name = "product 2";
		double p2cost = 20.99; 
		double p2markup = .25;
		int p2stockLevel = 20;
		
		Product product1 = new Product(p1name, p1cost, p1markup, p1stockLevel, supplier);
		Product product2 = new Product(p2name, p2cost, p2markup, p2stockLevel, supplier);
		
		//Database db = Database.getInstance();
		PermanentDatabase db = PermanentDatabase.getInstance();
		db.addProduct(product1);
		db.addProduct(product2);
		
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		LineItem invoiceItemOne = new LineItem(1, 12);
		LineItem invoiceItemTwo = new LineItem(2, 5);
		lineItems.add(invoiceItemOne);
		lineItems.add(invoiceItemTwo);
		
		Customer customer = new Customer("cutomer", "89437628", "customer@gmail.com", "customer street");
		
		Invoice invoice = new Invoice(lineItems, customer);
		
		double targetCost = invoiceItemOne.getQuantity() * product1.getPrice() + invoiceItemTwo.getQuantity() * product2.getPrice();

		assertNotNull(invoice.getCost());
		assertEquals(targetCost, invoice.getCost(), 0.001);
		
		//cleanup
		db.deleteProduct(product1);
		db.deleteProduct(product2);
	}

}
