package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Delivery;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.PermanentDatabase;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;
import team4.retailsystem.utils.EncryptionModule;

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
		Date d = new Date();
		double cost = 54.0;
		int productID = 1;
		int quantity = 2;
		LineItem one = new LineItem(productID, quantity);
		LineItem two = new LineItem(quantity, productID);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		lineItems.add(one);
		lineItems.add(two);
		
		Product p1 = new Product("p1", 12, .5, 15, new Supplier("name", "email", "address", "telephone"));
		Product p2 = new Product("p2", 12, .5, 15, new Supplier("name", "email", "address", "telephone"));
		PermanentDatabase.getInstance().addProduct(p1);
		PermanentDatabase.getInstance().addProduct(p2);
		
		String name = "Amazing Carpets";
		String newName = "Average Carpets";
		String telephoneNo = "0833903128";
		String newTelephoneNo = "123445678";
		String email = "amazing.carpets@gmail.com";
		String newEmail = "average.carpets@outlook.com";
		String address = "54 Random Street\nDublin 4\nIreland";
		String newAddress = "81 Less Random street";
		
		//acquire the id for customer c
		Customer c = new Customer(name, telephoneNo, email, address);
		PermanentDatabase.getInstance().addCustomer(c);
		c = PermanentDatabase.getInstance().getCustomer(1);
		
		//test Create
		Invoice invoice = new Invoice(lineItems, c, 1, d, 54.0);
		PermanentDatabase.getInstance().addInvoice(invoice);
		System.out.println(invoice);
		
		//test Create & Read
		Invoice read = PermanentDatabase.getInstance().getInvoice(1);
		System.out.println(read);
		assertEquals(cost, read.getCost(), .0001);
		assertEquals(c.getName(), read.getCustomer().getName());
		assertEquals(d.getTime(), read.getDate().getTime());
		ArrayList<LineItem> readLI = read.getLineItems();
		assertEquals(one.getProductID(), readLI.get(0).getProductID());
		assertEquals(two.getProductID(), readLI.get(1).getProductID());
		assertEquals(one.getQuantity(), readLI.get(0).getQuantity());
		assertEquals(two.getQuantity(), readLI.get(1).getQuantity());
		assertEquals(read.getID(), readLI.get(0).getOrderID());
		assertEquals(read.getID(), readLI.get(1).getOrderID());
		
		//test Update
		Date newDate = new Date();
		Customer e = new Customer(newName, newTelephoneNo, newEmail, newAddress);
		PermanentDatabase.getInstance().addCustomer(e);
		e = PermanentDatabase.getInstance().getCustomer(2);
		read.setCustomer(e);
		read.setDate(newDate);

		ArrayList<LineItem> newLineItems = new ArrayList<LineItem>();
		newLineItems = new ArrayList<LineItem>();
		LineItem newLineItem1 = read.getLineItems().get(0);
		LineItem newLineItem2 = read.getLineItems().get(1);
		newLineItem1.setProductID(quantity);
		newLineItem1.setQuantity(productID);
		newLineItem2.setProductID(productID);
		newLineItem2.setQuantity(quantity);		
		newLineItems.add(newLineItem1);
		newLineItems.add(newLineItem2);
		read.setLineItems(newLineItems);

		PermanentDatabase.getInstance().updateInvoice(read);
		Invoice f = PermanentDatabase.getInstance().getInvoice(1);
		assertEquals(cost, f.getCost(), .0001);
		assertEquals(e.getName(), read.getCustomer().getName());
		assertEquals(newDate.getTime(), f.getDate().getTime());
		
		ArrayList<LineItem> newReadLI = f.getLineItems();
		assertEquals(newLineItems.get(0).getProductID(), newReadLI.get(0).getProductID());
		assertEquals(newLineItems.get(1).getProductID(), newReadLI.get(1).getProductID());
		assertEquals(newLineItems.get(0).getQuantity(), newReadLI.get(0).getQuantity());
		assertEquals(newLineItems.get(1).getQuantity(), newReadLI.get(1).getQuantity());
		assertEquals(newLineItems.get(0).getOrderID(), newReadLI.get(0).getOrderID());
		assertEquals(newLineItems.get(1).getOrderID(), newReadLI.get(1).getOrderID());
		
		//test Delete
		assertTrue(PermanentDatabase.getInstance().deleteInvoice(f));
		
		//cleanup
		PermanentDatabase.getInstance().deleteCustomer(c);
		PermanentDatabase.getInstance().deleteCustomer(e);
		PermanentDatabase.getInstance().deleteProduct(p1);
		PermanentDatabase.getInstance().deleteProduct(p2);
	}

	@Test
	public void testCRUDOrder() {
		double cost = 200;
		double newCost = 400;
		int deliveryID = 5;
		int productID = 1;
		int quantity = 2;
		LineItem one = new LineItem(productID, quantity);
		LineItem two = new LineItem(quantity, productID);
		ArrayList<LineItem> items = new ArrayList<LineItem>();
		items.add(one);
		items.add(two);
		
		Supplier supplier1 = new Supplier("Blackthorne", "blackthorne@gmail.com", "city street", "07528934");
		Order order = new Order(cost, supplier1, deliveryID, items);
		Date deliveryDate = order.getDeliveryDate();
		
		PermanentDatabase db = PermanentDatabase.getInstance();
		db.addSupplier(supplier1);
		
		//Test Create
		assertTrue(db.addOrder(order));
		
		//Test Create && Read
		order = db.getOrder(1);
		assertEquals(cost, order.getCost(), 0.001);
		assertEquals(deliveryDate.getTime(), order.getDeliveryDate().getTime());
		assertEquals(supplier1.getName(), order.getSupplier().getName());
		assertEquals(one.getQuantity(), order.getLineItems().get(0).getQuantity());
		assertEquals(one.getProductID(), order.getLineItems().get(0).getProductID());
		assertEquals(order.getID(), order.getLineItems().get(0).getOrderID());
		assertEquals(two.getQuantity(), order.getLineItems().get(1).getQuantity());
		assertEquals(two.getProductID(), order.getLineItems().get(1).getProductID());
		assertEquals(order.getID(), order.getLineItems().get(1).getOrderID());
		
		//Test Update
		items = order.getLineItems();
		items.get(0).setProductID(quantity);
		items.get(0).setQuantity(productID);
		items.get(1).setProductID(productID);
		items.get(1).setQuantity(quantity);
		order.setCost(newCost);
		order.setDeliveryDate(new Date());
		deliveryDate = order.getDeliveryDate();
		order.setLineItems(items);
		db.updateOrder(order);
		
		db.getOrder(1);
		assertEquals(newCost, order.getCost(), 0.001);
		assertEquals(deliveryDate.getTime(), order.getDeliveryDate().getTime());
		assertEquals(supplier1.getName(), order.getSupplier().getName());
		assertEquals(items.get(0).getQuantity(), order.getLineItems().get(0).getQuantity());
		assertEquals(items.get(0).getProductID(), order.getLineItems().get(0).getProductID());
		assertEquals(order.getID(), order.getLineItems().get(0).getOrderID());
		assertEquals(items.get(1).getQuantity(), order.getLineItems().get(1).getQuantity());
		assertEquals(items.get(1).getProductID(), order.getLineItems().get(1).getProductID());
		assertEquals(order.getID(), order.getLineItems().get(1).getOrderID());		
		
		//Test Delete
		assertTrue(db.deleteOrder(order));

		//cleanup
		db.deleteSupplier(supplier1);
	}

	@Test
	public void testCRUDDelivery() {
		int orderID = 2;
		
		String name = "Amazing Carpets";
		String telephoneNo = "0833903128";
		String email = "amazing.carpets@gmail.com";
		String address = "54 Random Street\nDublin 4\nIreland";
		Supplier supplier1 = new Supplier(name, email, address, telephoneNo);	
		PermanentDatabase.getInstance().addSupplier(supplier1);
		
		String newName = "Average Carpets";	
		String newTelephoneNo = "123445678";
		String newEmail = "average.carpets@outlook.com";
		String newAddress = "81 Less Random street";		
		Supplier supplier2 = new Supplier(newName, newEmail, newAddress, newTelephoneNo);	
		PermanentDatabase.getInstance().addSupplier(supplier2);	

		//test Create
		Delivery delivery = new Delivery(supplier1, orderID);
		Date date = delivery.getDate();
		PermanentDatabase.getInstance().addDelivery(delivery);
		
		//test Create & Read
		delivery = PermanentDatabase.getInstance().getDelivery(1);
		assertEquals(orderID, delivery.getOrderID());
		assertEquals(date, delivery.getDate());
		assertEquals(supplier1.getName(), delivery.getSupplier().getName());
		
		//test Update
		orderID = 505;
		delivery.setOrderID(orderID);
		delivery.setSupplier(supplier2);
		delivery.setDate(new Date());
		date = delivery.getDate();

		PermanentDatabase.getInstance().updateDelivery(delivery);		
		assertEquals(orderID, delivery.getOrderID());
		assertEquals(date, delivery.getDate());
		assertEquals(supplier2.getName(), delivery.getSupplier().getName());
		
		//test Delete
		assertTrue(PermanentDatabase.getInstance().deleteDelivery(delivery));
		
		//cleanup
		PermanentDatabase.getInstance().deleteSupplier(supplier1);
		PermanentDatabase.getInstance().deleteSupplier(supplier2);
	}

	@Test
	public void testAuthorizeUser() {
		EncryptionModule md = null;
		try {
			md = new EncryptionModule();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail("Could not initiate the encryption module.");
		}
		
		String adminUsername = "tester";
		String adminWrongUsername = "teste";
		String adminPassword = "kitten";
		String adminWrongPassword = "superposition";
		String adminSalt = md.getRandomSalt();
		User admin = new User(User.ADMINISTRATOR, adminUsername, md.encrypt(adminPassword, adminSalt), adminSalt);
		
		String nonAdminUsername = "bb84";
		String nonAdminPassword = adminWrongPassword;
		String nonAdminSalt = md.getRandomSalt();
		User nonAdmin = new User(User.NORMAL_USER, nonAdminUsername, md.encrypt(nonAdminPassword, nonAdminSalt), nonAdminSalt);
		
		String notInSysUsername = "alice";
		String notInSysPassword = "wrong_basis";
		User notInSys;
		
		PermanentDatabase.getInstance().addUser(admin);
		PermanentDatabase.getInstance().addUser(nonAdmin);
		
		admin = PermanentDatabase.getInstance().authorizeUser(adminUsername, adminPassword);
		nonAdmin = PermanentDatabase.getInstance().authorizeUser(nonAdminUsername, nonAdminPassword);
		notInSys = PermanentDatabase.getInstance().authorizeUser(notInSysUsername, notInSysPassword);
		
		assertEquals(User.ADMINISTRATOR, admin.getAuthorizationLevel());
		assertEquals(User.NORMAL_USER, nonAdmin.getAuthorizationLevel());
		assertEquals(User.NO_AUTHORIZATION, notInSys.getAuthorizationLevel());
		assertEquals(User.NO_AUTHORIZATION, PermanentDatabase.getInstance().authorizeUser(adminWrongUsername, adminPassword).getAuthorizationLevel());			
		assertEquals(User.NO_AUTHORIZATION, PermanentDatabase.getInstance().authorizeUser(adminUsername, adminWrongPassword).getAuthorizationLevel());			
		assertEquals(User.NO_AUTHORIZATION, PermanentDatabase.getInstance().authorizeUser(adminWrongUsername, adminWrongPassword).getAuthorizationLevel());	
		
		//clean up
		PermanentDatabase.getInstance().deleteUser(admin);
		PermanentDatabase.getInstance().deleteUser(nonAdmin);
	}
}
