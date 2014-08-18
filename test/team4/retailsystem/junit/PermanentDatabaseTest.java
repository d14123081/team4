package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.PermanentDatabase;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

public class PermanentDatabaseTest {

	@Test
	public void testGetInstance() {
		assertNotNull(PermanentDatabase.getInstance());
	}
	
	@Test
	public void testCRUDCustomer() {
		String name = "John Doe";
		String newName = "Allan Martins";
		String telephoneNo = "0833903128";
		String newTelephoneNo = "123445678";
		String email = "john.doe@gmail.com";
		String newEmail = "allan.martins@outlook.com";
		String address = "54 Random Street\nDublin 4\nIreland";
		String newAddress = "81 Less Random street";
		
		//test Create
		Customer c = new Customer(name, telephoneNo, email, address);
		assertTrue(PermanentDatabase.getInstance().addCustomer(c));
		
		//test Create & Read
		Customer d = PermanentDatabase.getInstance().getCustomer(1);
		assertEquals(name, d.getName());
		assertEquals(telephoneNo, d.getTelephoneNumber());
		assertEquals(email, d.getEmail());
		assertEquals(address, d.getAddress());		
		
		//test Update
		d.setName(newName);
		d.setTelephoneNumber(newTelephoneNo);
		d.setEmail(newEmail);
		d.setAddress(newAddress);
		PermanentDatabase.getInstance().updateCustomer(d);
		
		Customer e = PermanentDatabase.getInstance().getCustomer(1);
		assertEquals(newName, e.getName());
		assertEquals(newTelephoneNo, e.getTelephoneNumber());
		assertEquals(newEmail, e.getEmail());
		assertEquals(newAddress, e.getAddress());		
		
		//test Delete
		assertTrue(PermanentDatabase.getInstance().deleteCustomer(e));
	}

	@Test
	public void testCRUDSupplier() {
		String name = "Amazing Carpets";
		String newName = "Average Carpets";
		String telephoneNo = "0833903128";
		String newTelephoneNo = "123445678";
		String email = "amazing.carpets@gmail.com";
		String newEmail = "average.carpets@outlook.com";
		String address = "54 Random Street\nDublin 4\nIreland";
		String newAddress = "81 Less Random street";
		
		//test Create
		Supplier s = new Supplier(name, email, address, telephoneNo);
		assertTrue(PermanentDatabase.getInstance().addSupplier(s));
		
		//test Create & Read
		Supplier d = PermanentDatabase.getInstance().getSupplier(1);
		assertEquals(name, d.getName());
		assertEquals(telephoneNo, d.getTelephoneNumber());
		assertEquals(email, d.getEmail());
		assertEquals(address, d.getAddress());		
		
		//test Update
		d.setSupplier(newName);
		d.setTelephone(newTelephoneNo);
		d.setEmail(newEmail);
		d.setAddress(newAddress);
		PermanentDatabase.getInstance().updateSupplier(d);
		
		Supplier e = PermanentDatabase.getInstance().getSupplier(1);
		assertEquals(newName, e.getName());
		assertEquals(newTelephoneNo, e.getTelephoneNumber());
		assertEquals(newEmail, e.getEmail());
		assertEquals(newAddress, e.getAddress());		
		
		//test Delete
		assertTrue(PermanentDatabase.getInstance().deleteSupplier(e));
	}

	@Test
	public void testCRUDProduct() {
		String name = "A book";
		String newName = "New book";
		double cost = 11.50;
		double newCost = 10.10;
		double markup = 0.1;
		double newMarkup = 0.5;
		int stockLevel = 50;
		int newStockLevel = 10;
		
		String sname = "Amazing Carpets";
		String stelephoneNo = "0833903128";
		String semail = "amazing.carpets@gmail.com";
		String saddress = "54 Random Street\nDublin 4\nIreland";
		
		Supplier supplier = new Supplier(sname, stelephoneNo, semail, saddress);
		Supplier newSupplier = new Supplier(semail, stelephoneNo, semail, saddress);
		PermanentDatabase.getInstance().addSupplier(supplier);		
		PermanentDatabase.getInstance().addSupplier(newSupplier);		
		
		//test Create
		Product s = new Product(name, cost, markup, stockLevel, supplier);
		assertTrue(PermanentDatabase.getInstance().addProduct(s));
		
		//test Create & Read
		Product d = PermanentDatabase.getInstance().getProduct(1);
		assertEquals(name, d.getName());
		assertEquals(cost, d.getCost(), .0001);
		assertEquals(markup, d.getMarkup(), .0001);
		assertEquals(stockLevel, d.getStockLevel());
		assertEquals(d.getSupplier().getName(), supplier.getName());
		
		//test Update
		d.setName(newName);
		d.setCost(newCost);
		d.setMarkup(newMarkup);
		d.setSupplier(newSupplier);
		d.setStockLevel(newStockLevel);
		PermanentDatabase.getInstance().updateProduct(d);
		
		Product e = PermanentDatabase.getInstance().getProduct(1);
		assertEquals(newName, e.getName());
		assertEquals(newCost, e.getCost(), .0001);
		assertEquals(newMarkup, e.getMarkup(), .0001);
		assertEquals(newStockLevel, e.getStockLevel());
		assertEquals(e.getSupplier().getName(), newSupplier.getName());
		
		//test Delete
		assertTrue(PermanentDatabase.getInstance().deleteProduct(e));	
		
		//clean up
		PermanentDatabase.getInstance().deleteSupplier(supplier);
		PermanentDatabase.getInstance().deleteSupplier(newSupplier);		
	}

	@Test
	public void testCRUDUser() {
		int authLevel = 1;
		String username = "ames";
		String passwordDigest = "tesla core";
		String salt = "salt";
		int newAuthLevel = 0;
		String newUsername = "bob";
		String newPasswordDigest = "hellobob";
		String newSalt = "newsalt";
		
		//test create
		User u = new User(authLevel, username, passwordDigest, salt);
		PermanentDatabase.getInstance().addUser(u);
		
		//test create & read
		User n = PermanentDatabase.getInstance().getUser(1);
		assertEquals(authLevel, n.getAuthorizationLevel());
		assertEquals(username, n.getUsername());
		assertEquals(passwordDigest, n.getPasswordDigest());
		assertEquals(salt, n.getSalt());
		System.out.println(n.getSalt());
		
		//test upadte 
		n = new User(1, newAuthLevel, newUsername, newPasswordDigest, newSalt);
		PermanentDatabase.getInstance().updateUser(n);
		assertEquals(newAuthLevel, n.getAuthorizationLevel());
		assertEquals(newUsername, n.getUsername());
		assertEquals(newPasswordDigest, n.getPasswordDigest());
		assertEquals(newSalt, n.getSalt());
		
		//test delete
		assertTrue(PermanentDatabase.getInstance().deleteUser(n));		
	}

	@Test
	public void testCRUDInvoice() {
		//int aID = 
		//LineItem a = new LineItem(1, 6000);
		
		//ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		//lineItems.add();
	}

	@Test
	public void testCRUDOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testCRUDDelivery() {
		fail("Not yet implemented");		
	}

	@Test
	public void testCRUDOrderItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testCRUDInvoiceItem() {
		fail("Not yet implemented");
	}

	@Test
	public void testAuthorizeUser() {
		fail("Not yet implemented");
	}
}
