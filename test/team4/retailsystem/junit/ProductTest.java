package team4.retailsystem.junit;

import static org.junit.Assert.*;


import org.junit.Test;

import team4.retailsystem.model.Product;

public class ProductTest {
	Product p = new Product(null, 0, 0, 0, null);


	@Test
	public void gettersTest() {
		p.setCost(23.0);
		p.setMarkup(0.3);
		p.setName("Boris");
		p.setStockLevel(22);
	
		
		assertEquals(0.0, p.getMarkup(), 1);
		assertEquals(p.getName(), "Boris");
		assertEquals(23.0, p.getPrice(), 1);
		assertEquals(23.0, p.getCost(), 1);
		assertEquals(22, p.getStockLevel(), 0);
	}
	

}
