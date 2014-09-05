package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import team4.retailsystem.model.Delivery;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Supplier;

public class LineItemTest {

	@Test
	public void testConstructorSG() {
		int productID1 = 5;
		int quantity1 = 6;
		int id1 = 7;
		int newOrderID1 = 23;

		int productID2 = 51;
		int quantity2 = 61;
		
		LineItem li = new LineItem(productID1, quantity1, id1, newOrderID1);

		assertEquals(productID1, li.getProductID());
		assertEquals(quantity1, li.getQuantity());
		assertEquals(id1, li.getID());
		assertEquals(newOrderID1, li.getOrderID());
		
		li.setProductID(productID2);
		li.setQuantity(quantity2);
		
		assertEquals(productID2, li.getProductID());
		assertEquals(quantity2, li.getQuantity());
		assertEquals(id1, li.getID());
		assertEquals(newOrderID1, li.getOrderID());
	}
}