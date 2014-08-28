package team4.retailsystem.junit;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Test;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Delivery;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;
import team4.retailsystem.utils.EncryptionModule;

public class DatabaseTest {

	@Test
	public void testGetInstance() {
		assertNotNull(Database.getInstance());
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
		assertTrue(Database.getInstance().addCustomer(c));
		
		//test Create & Read
		Customer d = Database.getInstance().getCustomer(1);
		assertEquals(name, d.getName());
		assertEquals(telephoneNo, d.getTelephoneNumber());
		assertEquals(email, d.getEmail());
		assertEquals(address, d.getAddress());		
		
		//test Update
		d.setName(newName);
		d.setTelephoneNumber(newTelephoneNo);
		d.setEmail(newEmail);
		d.setAddress(newAddress);
		Database.getInstance().updateCustomer(d);
		
		Customer e = Database.getInstance().getCustomer(1);
		assertEquals(newName, e.getName());
		assertEquals(newTelephoneNo, e.getTelephoneNumber());
		assertEquals(newEmail, e.getEmail());
		assertEquals(newAddress, e.getAddress());		
		
		//test Delete
		assertTrue(Database.getInstance().deleteCustomer(e));
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
		Supplier s = new Supplier(name, address, email, telephoneNo);
		assertTrue(Database.getInstance().addSupplier(s));
		
		//test Create & Read
		Supplier d = Database.getInstance().getSupplier(1);
		assertEquals(name, d.getName());
		assertEquals(telephoneNo, d.getTelephoneNumber());
		assertEquals(email, d.getEmail());
		assertEquals(address, d.getAddress());		
		
		//test Update
		d.setName(newName);
		d.setTelephone(newTelephoneNo);
		d.setEmail(newEmail);
		d.setAddress(newAddress);
		Database.getInstance().updateSupplier(d);
		
		Supplier e = Database.getInstance().getSupplier(1);
		assertEquals(newName, e.getName());
		assertEquals(newTelephoneNo, e.getTelephoneNumber());
		assertEquals(newEmail, e.getEmail());
		assertEquals(newAddress, e.getAddress());		
		
		//test Delete
		assertTrue(Database.getInstance().deleteSupplier(e));
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
		String saddress = "54 Random Street\nDublin 4\nIreland";
		String semail = "amazing.carpets@gmail.com";
		String stelephoneNo = "0833903128";
		
		Supplier supplier = new Supplier(sname, saddress, semail, stelephoneNo);
		Supplier newSupplier = new Supplier(semail, saddress, semail, stelephoneNo);
		Database.getInstance().addSupplier(supplier);		
		Database.getInstance().addSupplier(newSupplier);		
		
		//test Create
		Product s = new Product(name, cost, markup, stockLevel, supplier);
		assertTrue(Database.getInstance().addProduct(s));
		
		//test Create & Read
		Product d = Database.getInstance().getProduct(1);
		assertEquals(name, d.getName());
		//assertEquals(cost, d.getCost(), .0001);
		assertEquals(markup, d.getMarkup(), .0001);
		assertEquals(stockLevel, d.getStockLevel());
		assertEquals(d.getSupplier().getName(), supplier.getName());
		
		//test Update
		d.setName(newName);
		d.setCost(newCost);
		d.setMarkup(newMarkup);
		d.setSupplier(newSupplier);
		d.setStockLevel(newStockLevel);
		Database.getInstance().updateProduct(d);
		
		Product e = Database.getInstance().getProduct(1);
		assertEquals(newName, e.getName());
		//assertEquals(newCost, e.getCost(), .0001);
		assertEquals(newMarkup, e.getMarkup(), .0001);
		assertEquals(newStockLevel, e.getStockLevel());
		assertEquals(e.getSupplier().getName(), newSupplier.getName());
		
		//test Delete
		assertTrue(Database.getInstance().deleteProduct(e));	
		
		//clean up
		Database.getInstance().deleteSupplier(supplier);
		Database.getInstance().deleteSupplier(newSupplier);		
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
		Database.getInstance().addUser(u);
		
		//test create & read
		User n = Database.getInstance().getUser(1);
		assertEquals(authLevel, n.getAuthorizationLevel());
		assertEquals(username, n.getUsername());
		assertEquals(passwordDigest, n.getPasswordDigest());
		assertEquals(salt, n.getSalt());
		
		//test upadte 
		n = new User(1, newAuthLevel, newUsername, newPasswordDigest, newSalt);
		Database.getInstance().updateUser(n);
		assertEquals(newAuthLevel, n.getAuthorizationLevel());
		assertEquals(newUsername, n.getUsername());
		assertEquals(newPasswordDigest, n.getPasswordDigest());
		assertEquals(newSalt, n.getSalt());
		
		//test delete
		assertTrue(Database.getInstance().deleteUser(n));		
	}

	@Test
	public void testCRUDInvoice() {
		Database db = Database.getInstance();
		Date date = new Date();
		double cost = 54.0;
		int productID = 1;
		int quantity = 2;
		LineItem one = new LineItem(productID, quantity);
		LineItem two = new LineItem(quantity, productID);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		lineItems.add(one);
		lineItems.add(two);
		
		Supplier supplier1 = new Supplier("Supplier One", "supplier 1 road", "supplier1@email.com", "11111111");
		Supplier supplier2 = new Supplier("Supplier Two", "supplier 2 road", "supplier2@email.com", "22222222");
		db.addSupplier(supplier1);
		db.addSupplier(supplier2);
		
		Product product1 = new Product("product 1", 12, .5, 15, supplier1);
		Product product2 = new Product("product 2", 12, .5, 15, supplier2);
		db.addProduct(product1);
		db.addProduct(product2);

		String customerName = "Amazing Carpets";
		String customerTel = "0833903128";
		String customerEmail = "amazing.carpets@gmail.com";
		String customerAddress = "54 Random Street\nDublin 4\nIreland";
		Customer customer = new Customer(customerName, customerTel, customerEmail, customerAddress);
		db.addCustomer(customer);	
		
		//acquire the id for customer c
		customer = Database.getInstance().getCustomer(1);
		
		String newCustomerName = "Average Carpets";
		String newCustomerTel = "123445678";
		String newCustomerEmail = "average.carpets@outlook.com";
		String newCustomerAddress = "81 Less Random street";
		Customer newCustomer = new Customer(newCustomerName, newCustomerTel, newCustomerEmail, newCustomerAddress);
		db.addCustomer(newCustomer);
		
		//test Create
		Invoice invoice = new Invoice(lineItems, customer);
		assertTrue(db.addInvoice(invoice));
		date = invoice.getDate();
		//cost = invoice.getCost();
		
		//test Create & Read
		invoice = db.getInvoice(1);
		//assertEquals(cost, invoice.getCost(), .0001);
		assertEquals(customer.getName(), invoice.getCustomer().getName());
		assertEquals(date.getTime(), invoice.getDate().getTime());
		
		ArrayList<LineItem> invoiceListItems = invoice.getLineItems();
		assertEquals(one.getProductID(), invoiceListItems.get(0).getProductID());
		assertEquals(two.getProductID(), invoiceListItems.get(1).getProductID());
		assertEquals(one.getQuantity(), invoiceListItems.get(0).getQuantity());
		assertEquals(two.getQuantity(), invoiceListItems.get(1).getQuantity());
		assertEquals(invoice.getID(), invoiceListItems.get(0).getOrderID());
		assertEquals(invoice.getID(), invoiceListItems.get(1).getOrderID());
		
		//test Update		
		Date newDate = new Date();		
		newCustomer = db.getCustomer(2);
		invoice.setCustomer(newCustomer);
		invoice.setDate(newDate);

		invoiceListItems.get(0).setProductID(quantity);
		invoiceListItems.get(0).setQuantity(productID);
		invoiceListItems.get(1).setProductID(productID);
		invoiceListItems.get(1).setQuantity(quantity);	
		invoice.setLineItems(invoiceListItems);

		db.updateInvoice(invoice);
		invoice = db.getInvoice(1);
		//assertEquals(cost, invoice.getCost(), .0001);
		assertEquals(newCustomer.getName(), invoice.getCustomer().getName());
		assertEquals(newDate.getTime(), invoice.getDate().getTime());
		
		invoiceListItems = invoice.getLineItems();
		assertEquals(quantity, invoiceListItems.get(0).getProductID());
		assertEquals(productID, invoiceListItems.get(1).getProductID());
		assertEquals(productID, invoiceListItems.get(0).getQuantity());
		assertEquals(quantity, invoiceListItems.get(1).getQuantity());
		assertEquals(invoice.getID(), invoiceListItems.get(0).getOrderID());
		assertEquals(invoice.getID(), invoiceListItems.get(1).getOrderID());
		
		//test Delete
		assertTrue(Database.getInstance().deleteInvoice(invoice));
		
		//cleanup
		db.deleteCustomer(customer);
		db.deleteCustomer(newCustomer);
		db.deleteProduct(product1);
		db.deleteProduct(product2);
		db.deleteSupplier(supplier1);
		db.deleteSupplier(supplier2);		
	}

	@Test
	public void testCRUDOrder() {
		double cost = 200;
		double newCost = 400;
		int deliveryID = 5;
		int productID = 1;
		int quantity = 2;
		LineItem one = new LineItem(productID, quantity);
		LineItem two = new LineItem(productID, productID);
		ArrayList<LineItem> items = new ArrayList<LineItem>();
		items.add(one);
		items.add(two);
		
		Supplier supplier1 = new Supplier("Blackthorne", "city street", "blackthorne@gmail.com", "07528934");
		Product product1 = new Product("name", cost, 1, 1, supplier1);
		Order order = new Order(cost, supplier1, deliveryID, items);
		Date deliveryDate = order.getOrderDate();
		
		Database db = Database.getInstance();
		db.addSupplier(supplier1);
		db.addProduct(product1);
		
		//Test Create
		assertTrue(db.addOrder(order));
		
		//Test Create && Read
		order = db.getOrder(1);
		//assertEquals(cost, order.getCost(), 0.001);
		assertEquals(deliveryDate.getTime(), order.getOrderDate().getTime());
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
		order.setOrderDate(new Date());
		deliveryDate = order.getOrderDate();
		order.setLineItems(items);
		db.updateOrder(order);
		
		db.getOrder(1);
		//assertEquals(newCost, order.getCost(), 0.001);
		assertEquals(deliveryDate.getTime(), order.getOrderDate().getTime());
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
		db.deleteProduct(product1);
	}

	@Test
	public void testCRUDDelivery() {
		int orderID = 2;
		
		String name = "Amazing Carpets";
		String telephoneNo = "0833903128";
		String email = "amazing.carpets@gmail.com";
		String address = "54 Random Street\nDublin 4\nIreland";
		Supplier supplier1 = new Supplier(name, address, email, telephoneNo);	
		Database.getInstance().addSupplier(supplier1);
		
		String newName = "Average Carpets";	
		String newTelephoneNo = "123445678";
		String newEmail = "average.carpets@outlook.com";
		String newAddress = "81 Less Random street";		
		Supplier supplier2 = new Supplier(newName, newAddress, newEmail, newTelephoneNo);	
		Database.getInstance().addSupplier(supplier2);	

		//test Create
		Delivery delivery = new Delivery(supplier1, orderID);
		Date date = delivery.getDate();
		Database.getInstance().addDelivery(delivery);
		
		//test Create & Read
		delivery = Database.getInstance().getDelivery(1);
		assertEquals(orderID, delivery.getOrderID());
		assertEquals(date, delivery.getDate());
		assertEquals(supplier1.getName(), delivery.getSupplier().getName());
		
		//test Update
		orderID = 505;
		delivery.setOrderID(orderID);
		delivery.setSupplier(supplier2);
		delivery.setDate(new Date());
		date = delivery.getDate();

		Database.getInstance().updateDelivery(delivery);		
		assertEquals(orderID, delivery.getOrderID());
		assertEquals(date, delivery.getDate());
		assertEquals(supplier2.getName(), delivery.getSupplier().getName());
		
		//test Delete
		assertTrue(Database.getInstance().deleteDelivery(delivery));
		
		//cleanup
		Database.getInstance().deleteSupplier(supplier1);
		Database.getInstance().deleteSupplier(supplier2);
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
		
		Database.getInstance().addUser(admin);
		Database.getInstance().addUser(nonAdmin);
		
		admin = Database.getInstance().authorizeUser(adminUsername, adminPassword);
		nonAdmin = Database.getInstance().authorizeUser(nonAdminUsername, nonAdminPassword);
		notInSys = Database.getInstance().authorizeUser(notInSysUsername, notInSysPassword);
		
		assertEquals(User.ADMINISTRATOR, admin.getAuthorizationLevel());
		assertEquals(User.NORMAL_USER, nonAdmin.getAuthorizationLevel());
		assertEquals(User.NO_AUTHORIZATION, notInSys.getAuthorizationLevel());
		assertEquals(User.NO_AUTHORIZATION, Database.getInstance().authorizeUser(adminWrongUsername, adminPassword).getAuthorizationLevel());			
		assertEquals(User.NO_AUTHORIZATION, Database.getInstance().authorizeUser(adminUsername, adminWrongPassword).getAuthorizationLevel());			
		assertEquals(User.NO_AUTHORIZATION, Database.getInstance().authorizeUser(adminWrongUsername, adminWrongPassword).getAuthorizationLevel());	
		
		//clean up
		Database.getInstance().deleteUser(admin);
		Database.getInstance().deleteUser(nonAdmin);
	}
	
	@Test
	public void testGetCustomers(){
		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		String telNo1 = "telNo 1";
		String telNo2 = "telNo 2";
		String telNo3 = "telNo 3";
		String email1 = "email 1";
		String email2 = "email 2";
		String email3 = "email 3";
		String addr1 = "addr 1";
		String addr2 = "addr 2";
		String addr3 = "addr 3";
		
		Customer one = new Customer(name1, telNo1, email1, addr1);
		Customer two = new Customer(name2, telNo2, email2, addr2);
		Customer three = new Customer(name3, telNo3, email3, addr3);
		
		Database db = Database.getInstance();
		db.addCustomer(one);
		db.addCustomer(two);
		db.addCustomer(three);
		
		ArrayList<Customer> customers = db.getCustomers();
		assertEquals(name1, customers.get(0).getName());
		assertEquals(name2, customers.get(1).getName());
		assertEquals(name3, customers.get(2).getName());
		
		//cleanup
		db.deleteCustomer(one);
		db.deleteCustomer(two);
		db.deleteCustomer(three);
	}
	
	@Test
	public void testGetSuppliers() {

		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		String telNo1 = "telNo 1";
		String telNo2 = "telNo 2";
		String telNo3 = "telNo 3";
		String email1 = "email 1";
		String email2 = "email 2";
		String email3 = "email 3";
		String addr1 = "addr 1";
		String addr2 = "addr 2";
		String addr3 = "addr 3";

		Supplier one = new Supplier(name1, addr1, email1, telNo1);
		Supplier two = new Supplier(name2, addr2, email2, telNo2);
		Supplier three = new Supplier(name3, addr3, email3, telNo3);

		Database db = Database.getInstance();
		db.addSupplier(one);
		db.addSupplier(two);
		db.addSupplier(three);

		ArrayList<Supplier> suppliers = db.getSuppliers();
		assertEquals(name1, suppliers.get(0).getName());
		assertEquals(name2, suppliers.get(1).getName());
		assertEquals(name3, suppliers.get(2).getName());

		// cleanup
		db.deleteSupplier(one);
		db.deleteSupplier(two);
		db.deleteSupplier(three);
	}
	
	@Test
	public void testGetProducts(){

		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		String telNo1 = "telNo 1";
		String email1 = "email 1";
		String addr1 = "addr 1";

		Supplier supplier = new Supplier(name1, addr1, email1, telNo1);
		
		Product one = new Product(name1, 1, 1, 1, supplier);
		Product two = new Product(name2, 1, 1, 1, supplier);
		Product three = new Product(name3, 1, 1, 1, supplier);

		Database db = Database.getInstance();
		db.addProduct(one);
		db.addProduct(two);
		db.addProduct(three);

		ArrayList<Product> products = db.getProducts();
		assertEquals(name1, products.get(0).getName());
		assertEquals(name2, products.get(1).getName());
		assertEquals(name3, products.get(2).getName());

		// cleanup
		db.deleteProduct(one);
		db.deleteProduct(two);
		db.deleteProduct(three);
	}
	
	@Test
	public void testGetUsers(){

		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		String email1 = "email 1";
		
		User one = new User(1, name1, email1, email1);
		User two = new User(1, name2, email1, email1);
		User three = new User(1, name3, email1, email1);

		Database db = Database.getInstance();
		db.addUser(one);
		db.addUser(two);
		db.addUser(three);

		ArrayList<User> users = db.getUsers();
		assertEquals(name1, users.get(0).getUsername());
		assertEquals(name2, users.get(1).getUsername());
		assertEquals(name3, users.get(2).getUsername());

		// cleanup
		db.deleteUser(one);
		db.deleteUser(two);
		db.deleteUser(three);		
	}
	
	@Test
	public void testGetInvoices(){
		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		String email1 = "email 1";
		
		Supplier supplier1 =  new Supplier(name1,name1,name1,name1);
		Product product1 = new Product(name1, 1, 1, 1, supplier1);
		Product product2 = new Product(name2, 2, 2, 2, supplier1);
		Product product3 = new Product(name3, 3, 3, 3, supplier1);
		
		Database db = Database.getInstance();
		db.addProduct(product1);
		db.addProduct(product2);
		db.addProduct(product3);
		
		Customer customer = new Customer(email1, email1, email1, email1);
		LineItem lineItem1 = new LineItem(1, 1);
		LineItem lineItem2 = new LineItem(2, 2);
		LineItem lineItem3 = new LineItem(3, 3);		
		
		ArrayList<LineItem> items1 = new ArrayList<LineItem>();
		items1.add(lineItem1);
		items1.add(lineItem2);
		ArrayList<LineItem> items2 = new ArrayList<LineItem>();
		items2.add(lineItem2);
		items2.add(lineItem3);
		ArrayList<LineItem> items3 = new ArrayList<LineItem>();
		items3.add(lineItem3);
		items3.add(lineItem1);
		
		Invoice one = new Invoice(items1, customer);
		Invoice two = new Invoice(items2, customer);
		Invoice three = new Invoice(items3, customer);
		
		db.addInvoice(one);
		db.addInvoice(two);
		db.addInvoice(three);

		ArrayList<Invoice> invoices = db.getInvoices();
		assertEquals(1, invoices.get(0).getLineItems().get(0).getProductID());
		assertEquals(2, invoices.get(0).getLineItems().get(1).getProductID());
		assertEquals(2, invoices.get(1).getLineItems().get(0).getProductID());
		assertEquals(3, invoices.get(1).getLineItems().get(1).getProductID());
		assertEquals(3, invoices.get(2).getLineItems().get(0).getProductID());
		assertEquals(1, invoices.get(2).getLineItems().get(1).getProductID());
		
		// cleanup
		db.deleteInvoice(invoices.get(0));
		db.deleteInvoice(invoices.get(1));
		db.deleteInvoice(invoices.get(2));
		
		ArrayList<Product> products = db.getProducts();
		db.deleteProduct(products.get(0));
		db.deleteProduct(products.get(1));
		db.deleteProduct(products.get(2));
	}
	
	@Deprecated
	@Test
	public void testGetOrders(){
		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		
		Supplier supplier1 =  new Supplier(name1,name1,name1,name1);
		Product product1 = new Product(name1, 1, 1, 1, supplier1);
		Product product2 = new Product(name2, 2, 2, 2, supplier1);
		Product product3 = new Product(name3, 3, 3, 3, supplier1);
		
		Database db = Database.getInstance();
		db.addProduct(product1);
		db.addProduct(product2);
		db.addProduct(product3);
		
		LineItem lineItem1 = new LineItem(1, 1);
		LineItem lineItem2 = new LineItem(2, 2);
		LineItem lineItem3 = new LineItem(3, 3);
		
		ArrayList<LineItem> items1 = new ArrayList<LineItem>();
		items1.add(lineItem1);
		items1.add(lineItem2);
		ArrayList<LineItem> items2 = new ArrayList<LineItem>();
		items2.add(lineItem2);
		items2.add(lineItem3);
		ArrayList<LineItem> items3 = new ArrayList<LineItem>();
		items3.add(lineItem3);
		items3.add(lineItem1);
		
		Order one = new Order(1, supplier1, 1, items1);
		Order two = new Order(1, supplier1, 1, items2);
		Order three = new Order(1, supplier1, 1, items3);
		
		db.addOrder(one);
		db.addOrder(two);
		db.addOrder(three);

		ArrayList<Order> orders = db.getOrders();
		assertEquals(1, orders.get(0).getLineItems().get(0).getProductID());
		assertEquals(2, orders.get(0).getLineItems().get(1).getProductID());
		assertEquals(2, orders.get(1).getLineItems().get(0).getProductID());
		assertEquals(3, orders.get(1).getLineItems().get(1).getProductID());
		assertEquals(3, orders.get(2).getLineItems().get(0).getProductID());
		assertEquals(1, orders.get(2).getLineItems().get(1).getProductID());
		
		// cleanup
		db.deleteOrder(orders.get(0));
		db.deleteOrder(orders.get(1));
		db.deleteOrder(orders.get(2));
		
		ArrayList<Product> products = db.getProducts();
		db.deleteProduct(products.get(0));
		db.deleteProduct(products.get(1));
		db.deleteProduct(products.get(2));	
	}
	
	@Test
	public void testGetDeliveries(){
		String name1 = "name 1";
		
		Supplier supplier1 = new Supplier(name1, name1, name1, name1);
		
		Delivery one = new Delivery(supplier1, 1);
		Delivery two = new Delivery(supplier1, 2);
		Delivery three = new Delivery(supplier1, 3);

		Database db = Database.getInstance();
		db.addDelivery(one);
		db.addDelivery(two);
		db.addDelivery(three);

		ArrayList<Delivery> deliveries = db.getDeliveries();
		assertEquals(1, deliveries.get(0).getOrderID());
		assertEquals(2, deliveries.get(1).getOrderID());
		assertEquals(3, deliveries.get(2).getOrderID());

		// cleanup
		db.deleteDelivery(deliveries.get(0));
		db.deleteDelivery(deliveries.get(1));
		db.deleteDelivery(deliveries.get(2));	
	}
	
	@After
	public void cleanup(){
		for(Customer c : Database.getInstance().getCustomers()){
			Database.getInstance().deleteCustomer(c);
		}
		for(Product p : Database.getInstance().getProducts()){
			Database.getInstance().deleteProduct(p);
		}
		for(Supplier s : Database.getInstance().getSuppliers()){
			Database.getInstance().deleteSupplier(s);
		}
		for(User u : Database.getInstance().getUsers()){
			Database.getInstance().deleteUser(u);
		}
		for(Invoice i : Database.getInstance().getInvoices()){
			Database.getInstance().deleteInvoice(i);
		}
		for(Delivery d : Database.getInstance().getDeliveries()){
			Database.getInstance().deleteDelivery(d);
		}
		for(Order o : Database.getInstance().getOrders()){
			Database.getInstance().deleteOrder(o);
		}
		for(LineItem li : Database.getInstance().getOrderItems()){
			Database.getInstance().deleteOrderItem(li);
		}
		for(LineItem li : Database.getInstance().getInvoiceItems()){
			Database.getInstance().deleteInvoiceItem(li);
		}
	}
	@Test
	public void testGetOrdersBetween(){
		String temp = "temp";
		Date now = new Date();
		Date now500 = new Date(now.getTime()+500);
		Date inBetween = new Date(now.getTime()+200);
		Date nowMinus1 = new Date(now.getTime()-1);
		Date now500Plus1 = new Date(now500.getTime()+1);
		Supplier supplier = new Supplier(temp,temp,temp,temp);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		Order tooOld = new Order(1, supplier, 1, lineItems, 1, nowMinus1);
		Order justOldEnough = new Order(1, supplier, 1, lineItems, 1, now);
		Order justRight = new Order(1, supplier, 1, lineItems, 1, inBetween);
		Order justNewEnough = new Order(1, supplier, 1, lineItems, 1, now500);
		Order tooNew = new Order(1, supplier, 1, lineItems, 1, now500Plus1);	
		
		Database db = Database.getInstance();
		db.addOrder(justRight);
		db.addOrder(justOldEnough);
		db.addOrder(tooOld);
		db.addOrder(tooNew);
		db.addOrder(justNewEnough);
		
		ArrayList<Order> validOrders = db.getOrdersBetween(now, now500);
		assertEquals(1, validOrders.get(0).getID());
		assertEquals(2, validOrders.get(1).getID());
		assertEquals(5, validOrders.get(2).getID());
		
		//cleanup
		for(Order o: db.getOrders()){
			db.deleteOrder(o);
		}
	}
	
	@Test
	public void testGetInvoicesBetween(){
		String temp = "temp";
		Date now = new Date();
		Date now500 = new Date(now.getTime()+500);
		Date inBetween = new Date(now.getTime()+200);
		Date nowMinus1 = new Date(now.getTime()-1);
		Date now500Plus1 = new Date(now500.getTime()+1);
		Customer customer = new Customer(temp,temp,temp,temp);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		Invoice tooOld = new Invoice(lineItems, customer, 1, nowMinus1);
		Invoice justOldEnough = new Invoice(lineItems, customer, 1, now);
		Invoice justRight = new Invoice(lineItems, customer, 1, inBetween);
		Invoice justNewEnough = new Invoice(lineItems, customer, 1, now500);
		Invoice tooNew = new Invoice(lineItems, customer, 1, now500Plus1);
		
		Database db = Database.getInstance();
		db.addInvoice(justRight);
		db.addInvoice(justOldEnough);
		db.addInvoice(tooOld);
		db.addInvoice(tooNew);
		db.addInvoice(justNewEnough);
		
		ArrayList<Invoice> validInvoices = db.getInvoicesBetween(now, now500);
		assertEquals(1, validInvoices.get(0).getID());
		assertEquals(2, validInvoices.get(1).getID());
		assertEquals(5, validInvoices.get(2).getID());
		
		//cleanup
		for(Invoice i: db.getInvoices()){
			db.deleteInvoice(i);
		}
	}
	
	@Test
	public void testGetDeliveriesBetween(){
		String temp = "temp";
		Date now = new Date();
		Date now500 = new Date(now.getTime()+500);
		Date inBetween = new Date(now.getTime()+200);
		Date nowMinus1 = new Date(now.getTime()-1);
		Date now500Plus1 = new Date(now500.getTime()+1);
		Supplier supplier = new Supplier(temp,temp,temp,temp);
		Delivery tooOld = new Delivery(supplier, 1, nowMinus1, 1);
		Delivery justOldEnough = new Delivery(supplier, 2, now, 1);
		Delivery justRight = new Delivery(supplier, 3, inBetween, 1);
		Delivery justNewEnough = new Delivery(supplier, 4, now500, 1);
		Delivery tooNew = new Delivery(supplier, 5, now500Plus1, 1);
		
		Database db = Database.getInstance();
		db.addDelivery(justRight);
		db.addDelivery(justOldEnough);
		db.addDelivery(tooOld);
		db.addDelivery(tooNew);
		db.addDelivery(justNewEnough);
		
		ArrayList<Delivery> validDeliveries = db.getDeliveriesBetween(now, now500);
		assertEquals(3, validDeliveries.get(0).getOrderID());
		assertEquals(2, validDeliveries.get(1).getOrderID());
		assertEquals(4, validDeliveries.get(2).getOrderID());
		
		//cleanup
		for(Delivery d: db.getDeliveries()){
			db.deleteDelivery(d);
		}
	}
}
