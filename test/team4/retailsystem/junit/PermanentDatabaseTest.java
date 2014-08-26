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
		d.setName(newName);
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
		PermanentDatabase db = PermanentDatabase.getInstance();
		Date date = new Date();
		double cost = 54.0;
		int productID = 1;
		int quantity = 2;
		LineItem one = new LineItem(productID, quantity);
		LineItem two = new LineItem(quantity, productID);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		lineItems.add(one);
		lineItems.add(two);
		
		Supplier supplier1 = new Supplier("Supplier One", "supplier1@email.com", "supplier 1 road", "11111111");
		Supplier supplier2 = new Supplier("Supplier Two", "supplier2@email.com", "supplier 2 road", "22222222");
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
		customer = PermanentDatabase.getInstance().getCustomer(1);
		
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
		cost = invoice.getCost();
		
		//test Create & Read
		invoice = db.getInvoice(1);
		assertEquals(cost, invoice.getCost(), .0001);
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
		assertEquals(cost, invoice.getCost(), .0001);
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
		assertTrue(PermanentDatabase.getInstance().deleteInvoice(invoice));
		
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
		
		PermanentDatabase db = PermanentDatabase.getInstance();
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

		Supplier one = new Supplier(name1, telNo1, email1, addr1);
		Supplier two = new Supplier(name2, telNo2, email2, addr2);
		Supplier three = new Supplier(name3, telNo3, email3, addr3);

		PermanentDatabase db = PermanentDatabase.getInstance();
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

		Supplier supplier = new Supplier(name1, email1, addr1, telNo1);
		
		Product one = new Product(name1, 1, 1, 1, supplier);
		Product two = new Product(name2, 1, 1, 1, supplier);
		Product three = new Product(name3, 1, 1, 1, supplier);

		PermanentDatabase db = PermanentDatabase.getInstance();
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

		PermanentDatabase db = PermanentDatabase.getInstance();
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
		
		PermanentDatabase db = PermanentDatabase.getInstance();
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
	
	@Test
	public void testGetOrders(){
		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		
		Supplier supplier1 =  new Supplier(name1,name1,name1,name1);
		Product product1 = new Product(name1, 1, 1, 1, supplier1);
		Product product2 = new Product(name2, 2, 2, 2, supplier1);
		Product product3 = new Product(name3, 3, 3, 3, supplier1);
		
		PermanentDatabase db = PermanentDatabase.getInstance();
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

		PermanentDatabase db = PermanentDatabase.getInstance();
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
}
