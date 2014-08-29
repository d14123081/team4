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
	private static final String CREATE_ERR = "Failed to create";
	private static final String READ_ERR = "Failed to read";
	private static final String UPDATE_ERR = "Failed to update";
	private static final String DELETE_ERR = "Failed to delete";

	@Test
	public void testGetInstance() {
		assertNotNull("Could not open/create the database",
				Database.getInstance("testSystem"));
	}

	@Test
	public void testCRUDCustomer() {
		// test data
		String name = "John Doe";
		String tel = "0833903128";
		String email = "john.doe@gmail.com";
		String addr = "54 Random Street\nDublin 4\nIreland";

		String newName = "Allan Martins";
		String newTel = "123445678";
		String newEmail = "allan.martins@outlook.com";
		String newAddr = "81 Less Random street";

		Database db = Database.getInstance("testSystem");
		Customer customer = new Customer(name, tel, email, addr);

		// test Create & Read
		assertTrue(CREATE_ERR, db.addCustomer(customer));

		customer = db.getCustomers().get(0);
		assertNotNull(READ_ERR, customer);
		assertEquals(READ_ERR, name, customer.getName());
		assertEquals(READ_ERR, tel, customer.getTelephoneNumber());
		assertEquals(READ_ERR, email, customer.getEmail());
		assertEquals(READ_ERR, addr, customer.getAddress());

		// test Update
		customer.setName(newName);
		customer.setTelephoneNumber(newTel);
		customer.setEmail(newEmail);
		customer.setAddress(newAddr);

		assertTrue(UPDATE_ERR, db.updateCustomer(customer));

		customer = db.getCustomers().get(0);
		assertNotNull(READ_ERR, customer);
		assertEquals(UPDATE_ERR, newName, customer.getName());
		assertEquals(UPDATE_ERR, newTel, customer.getTelephoneNumber());
		assertEquals(UPDATE_ERR, newEmail, customer.getEmail());
		assertEquals(UPDATE_ERR, newAddr, customer.getAddress());

		// test Delete
		assertTrue(DELETE_ERR, db.deleteCustomer(customer));
	}

	@Test
	public void testCRUDSupplier() {
		// test data
		String name = "Amazing Carpets";
		String tel = "0833903128";
		String email = "amazing.carpets@gmail.com";
		String addr = "54 Random Street\nDublin 4\nIreland";

		String newName = "Average Carpets";
		String newTel = "123445678";
		String newEmail = "average.carpets@outlook.com";
		String newAddress = "81 Less Random street";

		Database db = Database.getInstance("testSystem");
		Supplier supplier = new Supplier(name, addr, email, tel);

		// test Create & Read
		assertTrue(CREATE_ERR, db.addSupplier(supplier));

		supplier = db.getSuppliers().get(0);
		assertNotNull(READ_ERR, supplier);
		assertEquals(READ_ERR, name, supplier.getName());
		assertEquals(READ_ERR, tel, supplier.getTelephoneNumber());
		assertEquals(READ_ERR, email, supplier.getEmail());
		assertEquals(READ_ERR, addr, supplier.getAddress());

		// test Update
		supplier.setName(newName);
		supplier.setTelephone(newTel);
		supplier.setEmail(newEmail);
		supplier.setAddress(newAddress);

		assertTrue(UPDATE_ERR, db.updateSupplier(supplier));

		supplier = db.getSuppliers().get(0);
		assertNotNull(READ_ERR, supplier);
		assertEquals(UPDATE_ERR, newName, supplier.getName());
		assertEquals(UPDATE_ERR, newTel, supplier.getTelephoneNumber());
		assertEquals(UPDATE_ERR, newEmail, supplier.getEmail());
		assertEquals(UPDATE_ERR, newAddress, supplier.getAddress());

		// test Delete
		assertTrue(DELETE_ERR, db.deleteSupplier(supplier));
	}

	@Test
	public void testCRUDProduct() {
		// test data
		String name = "A book";
		double cost = 11.50;
		double markup = 0.1;
		int stockLvl = 50;

		String newName = "New book";
		double newCost = 20.99;
		double newMarkup = 50;
		int newStockLvl = 1000;

		String supplData = "Amazing Carpets";
		String newSupplData = "Average Carpets";

		Database db = Database.getInstance("testSystem");
		Supplier supplier = new Supplier(supplData, supplData, supplData,
				supplData);
		Supplier newSupplier = new Supplier(newSupplData, newSupplData,
				newSupplData, newSupplData);
		db.addSupplier(supplier);
		db.addSupplier(newSupplier);
		Product product = new Product(name, cost, markup, stockLvl, supplier);

		// test Create & Read
		assertTrue(CREATE_ERR, db.addProduct(product));

		product = db.getProducts().get(0);
		assertNotNull(READ_ERR, product);		
		assertEquals(READ_ERR, name, product.getName());
		assertEquals(READ_ERR, markup, product.getMarkup(), .0001);
		assertEquals(READ_ERR, stockLvl, product.getStockLevel());
		assertEquals(READ_ERR, supplier.getName(), product.getSupplier().getName());

		// test Update
		product.setName(newName);
		product.setCost(newCost);
		product.setMarkup(newMarkup);
		product.setSupplier(newSupplier);
		product.setStockLevel(newStockLvl);
		
		assertTrue(UPDATE_ERR, db.updateProduct(product));

		product = db.getProducts().get(0);
		assertNotNull(READ_ERR, product);
		assertEquals(UPDATE_ERR, newName, product.getName());
		assertEquals(UPDATE_ERR, newMarkup, product.getMarkup(), .0001);
		assertEquals(UPDATE_ERR, newStockLvl, product.getStockLevel());
		assertEquals(UPDATE_ERR, newSupplier.getName(), product.getSupplier().getName());

		// test Delete
		assertTrue(db.deleteProduct(product));
	}

	@Test
	public void testCRUDUser() {
		// test data
		int authLvl = User.ADMINISTRATOR;
		String username = "ames";
		String pswdDigest = "tesla core";
		String salt = "salt";
		
		int newAuthLvl = User.NORMAL_USER;
		String newUsername = "bob";
		String newPswdDigest = "hellobob";
		String newSalt = "newsalt";

		Database db = Database.getInstance("testSystem");
		User user = new User(authLvl, username, pswdDigest, salt);
		
		// test Create & Read
		assertTrue(CREATE_ERR, db.addUser(user));
		
		user = db.getUsers().get(0);
		assertNotNull(READ_ERR, user);		
		
		assertEquals(READ_ERR, authLvl, user.getAuthorizationLevel());
		assertEquals(READ_ERR, username, user.getUsername());
		assertEquals(READ_ERR, pswdDigest, user.getPasswordDigest());
		assertEquals(READ_ERR, salt, user.getSalt());

		// test Update
		user = new User(user.getID(), newAuthLvl, newUsername, newPswdDigest, newSalt);
		assertTrue(UPDATE_ERR, db.updateUser(user));
		assertEquals(UPDATE_ERR, newAuthLvl, user.getAuthorizationLevel());
		assertEquals(UPDATE_ERR, newUsername, user.getUsername());
		assertEquals(UPDATE_ERR, newPswdDigest, user.getPasswordDigest());
		assertEquals(UPDATE_ERR, newSalt, user.getSalt());

		// test Delete
		assertTrue(DELETE_ERR, db.deleteUser(user));
	}

	@Test
	public void testCRUDInvoice() {
		// test data
		Date date = new Date();
		int productID = 1;
		int quantity = 2;
		String suppData1 = "supplier1";
		String suppData2 = "supplier2";
		LineItem item1 = new LineItem(productID, quantity);
		LineItem item2 = new LineItem(quantity, productID);
		ArrayList<LineItem> invoiceItems = new ArrayList<LineItem>();
		invoiceItems.add(item1);
		invoiceItems.add(item2);

		Database db = Database.getInstance("testSystem");
		Supplier supplier1 = new Supplier(suppData1, suppData1, suppData1, suppData1);
		Supplier supplier2 = new Supplier(suppData2, suppData2,
				suppData2, suppData2);
		db.addSupplier(supplier1);
		db.addSupplier(supplier2);

		Product product1 = new Product("product 1", 12, .5, 15, supplier1);
		Product product2 = new Product("product 2", 12, .5, 15, supplier2);
		db.addProduct(product1);
		db.addProduct(product2);

		String custData1 = "Amazing Carpets";
		Customer customer1 = new Customer(custData1, custData1,
				custData1, custData1);
		db.addCustomer(customer1);

		String custData2 = "Average Carpets";
		Customer customer2 = new Customer(custData2, custData2,
				custData2, custData2);
		db.addCustomer(customer2);

		Invoice invoice = new Invoice(invoiceItems, customer1);
		date = invoice.getDate();
		
		// test Create & Read
		assertTrue(CREATE_ERR, db.addInvoice(invoice));		

		invoice = db.getInvoices().get(0);
		assertNotNull(READ_ERR, invoice);
		assertEquals(READ_ERR, customer1.getName(), invoice.getCustomer().getName());
		assertEquals(READ_ERR, date.getTime(), invoice.getDate().getTime());

		invoiceItems = invoice.getLineItems();
		assertNotNull(READ_ERR, invoiceItems);
		assertEquals(READ_ERR, item1.getProductID(), invoiceItems.get(0).getProductID());
		assertEquals(READ_ERR, item1.getQuantity(), invoiceItems.get(0).getQuantity());
		assertEquals(READ_ERR, item2.getProductID(), invoiceItems.get(1).getProductID());
		assertEquals(READ_ERR, item2.getQuantity(), invoiceItems.get(1).getQuantity());
		assertEquals(READ_ERR, invoice.getID(), invoiceItems.get(0).getOrderID());
		assertEquals(READ_ERR, invoice.getID(), invoiceItems.get(1).getOrderID());

		// test Update
		Date newDate = new Date();
		invoice.setCustomer(customer2);
		invoice.setDate(newDate);

		invoiceItems.get(0).setProductID(quantity);
		invoiceItems.get(0).setQuantity(productID);
		invoiceItems.get(1).setProductID(productID);
		invoiceItems.get(1).setQuantity(quantity);

		assertTrue(UPDATE_ERR, db.updateInvoice(invoice));
		
		invoice = db.getInvoices().get(0);
		assertNotNull(UPDATE_ERR, invoice);
		assertEquals(UPDATE_ERR, customer2.getName(), invoice.getCustomer().getName());
		assertEquals(UPDATE_ERR, newDate.getTime(), invoice.getDate().getTime());

		invoiceItems = invoice.getLineItems();
		assertEquals(UPDATE_ERR, quantity, invoiceItems.get(0).getProductID());
		assertEquals(UPDATE_ERR, productID, invoiceItems.get(0).getQuantity());
		assertEquals(UPDATE_ERR, productID, invoiceItems.get(1).getProductID());
		assertEquals(UPDATE_ERR, quantity, invoiceItems.get(1).getQuantity());
		assertEquals(UPDATE_ERR, invoice.getID(), invoiceItems.get(0).getOrderID());
		assertEquals(UPDATE_ERR, invoice.getID(), invoiceItems.get(1).getOrderID());

		// test Delete
		assertTrue(db.deleteInvoice(invoice));
	}

	@Test
	public void testCRUDOrder() {
		// test data
		double cost = 200;
		int deliveryID = 5;
		int productID = 1;
		int quantity = 2;
		LineItem item1 = new LineItem(productID, quantity);
		LineItem item2 = new LineItem(quantity, productID);
		ArrayList<LineItem> orderItems = new ArrayList<LineItem>();
		orderItems.add(item1);
		orderItems.add(item2);

		String suppData1 = "Blackthorne";
		
		Supplier supplier1 = new Supplier(suppData1, suppData1, suppData1, suppData1);
		Product product1 = new Product("name", cost, 1, 1, supplier1);
		Order order = new Order(cost, supplier1, deliveryID, orderItems);
		Date date = order.getOrderDate();

		Database db = Database.getInstance("testSystem");
		db.addSupplier(supplier1);
		db.addProduct(product1);

		// Test Create && Read
		assertTrue(CREATE_ERR, db.addOrder(order));

		order = db.getOrders().get(0);
		assertNotNull(READ_ERR, order);
		orderItems = order.getLineItems();
		assertNotNull(READ_ERR, orderItems);
		
		assertEquals(READ_ERR, date.getTime(), order.getOrderDate().getTime());
		assertEquals(READ_ERR, supplier1.getName(), order.getSupplier().getName());

		assertEquals(READ_ERR, item1.getProductID(), orderItems.get(0).getProductID());
		assertEquals(READ_ERR, item1.getQuantity(), orderItems.get(0).getQuantity());
		assertEquals(READ_ERR, order.getID(), orderItems.get(0).getOrderID());
		assertEquals(READ_ERR, item2.getProductID(), orderItems.get(1)
				.getProductID());
		assertEquals(READ_ERR, item2.getQuantity(), orderItems.get(1).getQuantity());
		assertEquals(READ_ERR, order.getID(), orderItems.get(1).getOrderID());

		// Test Update
		orderItems.get(0).setProductID(quantity);
		orderItems.get(0).setQuantity(productID);
		orderItems.get(1).setProductID(productID);
		orderItems.get(1).setQuantity(quantity);
		order.setOrderDate(new Date());
		date = order.getOrderDate();
		order.setLineItems(orderItems);
		
		assertTrue(UPDATE_ERR, db.updateOrder(order));
		
		order = db.getOrders().get(0);
		assertNotNull(READ_ERR,  order);
		orderItems = order.getLineItems();
		assertNotNull(READ_ERR, orderItems);
		
		assertEquals(UPDATE_ERR, date.getTime(), order.getOrderDate().getTime());
		assertEquals(UPDATE_ERR, supplier1.getName(), order.getSupplier().getName());

		assertEquals(UPDATE_ERR, quantity, orderItems.get(0).getProductID());
		assertEquals(UPDATE_ERR, productID, orderItems.get(0).getQuantity());
		assertEquals(UPDATE_ERR, order.getID(), orderItems.get(0).getOrderID());
		assertEquals(UPDATE_ERR, productID, orderItems.get(1).getProductID());
		assertEquals(UPDATE_ERR, quantity, orderItems.get(1).getQuantity());
		assertEquals(UPDATE_ERR, order.getID(), orderItems.get(1).getOrderID());

		// Test Delete
		assertTrue(db.deleteOrder(order));
	}

	@Test
	public void testCRUDDelivery() {
		// test data
		Database db = Database.getInstance("testSystem");
		int orderID = 2;
		String suppData1 = "Amazing Carpets";
		String suppData2 = "Average Carpets";
		Supplier supplier1 = new Supplier(suppData1, suppData1, suppData1, suppData1);
		Supplier supplier2 = new Supplier(suppData2, suppData2, suppData2,
				suppData2);		
		db.addSupplier(supplier1);
		db.addSupplier(supplier2);

		// test Create & Read
		Delivery delivery = new Delivery(supplier1, orderID);
		Date date = delivery.getDate();
		
		assertTrue(CREATE_ERR, db.addDelivery(delivery));
		
		delivery = db.getDeliveries().get(0);
		assertNotNull(READ_ERR, delivery);
		assertEquals(READ_ERR, orderID, delivery.getOrderID());
		assertEquals(READ_ERR, date, delivery.getDate());
		assertEquals(READ_ERR, supplier1.getName(), delivery.getSupplier().getName());

		// test Update
		orderID = 505;
		delivery.setOrderID(orderID);
		delivery.setSupplier(supplier2);
		delivery.setDate(new Date());
		date = delivery.getDate();

		assertTrue(UPDATE_ERR, db.updateDelivery(delivery));
		assertEquals(UPDATE_ERR, orderID, delivery.getOrderID());
		assertEquals(UPDATE_ERR, date, delivery.getDate());
		assertEquals(UPDATE_ERR, supplier2.getName(), delivery.getSupplier().getName());

		// test Delete
		assertTrue(DELETE_ERR, db.deleteDelivery(delivery));
	}

	@Test
	public void testAuthorizeUser() {
		String errCorrectLogin = "Authorization with valid data failed";
		String errInvalidLogin = "Authorization with invalid data succeeded";
		
		Database db = Database.getInstance("testSystem");
		String adminUsername = "tester";
		String adminPassword = "kitten";
		
		String adminWrongUsername = "teste";
		String adminWrongPassword = "superposition";

		String nonAdminUsername = "bb84";
		String nonAdminPassword = adminWrongPassword;

		String notInSysUsername = "alice";
		String notInSysPassword = "wrong_basis";

		db.addUser(User.ADMINISTRATOR, adminUsername, adminPassword);
		db.addUser(User.NORMAL_USER, nonAdminUsername, nonAdminPassword);

		User admin = db.authorizeUser(adminUsername, adminPassword);
		User nonAdmin = db.authorizeUser(nonAdminUsername, nonAdminPassword);
		User notInSys = db.authorizeUser(notInSysUsername, notInSysPassword);

		assertEquals(errCorrectLogin, User.ADMINISTRATOR,
				admin.getAuthorizationLevel());
		assertEquals(errCorrectLogin, User.NORMAL_USER,
				nonAdmin.getAuthorizationLevel());
		assertEquals(errCorrectLogin, User.NO_AUTHORIZATION,
				notInSys.getAuthorizationLevel());
		assertEquals(errInvalidLogin, User.NO_AUTHORIZATION,
				db.authorizeUser(adminWrongUsername, adminPassword)
						.getAuthorizationLevel());
		assertEquals(errInvalidLogin, User.NO_AUTHORIZATION,
				db.authorizeUser(adminUsername, adminWrongPassword)
						.getAuthorizationLevel());
		assertEquals(errInvalidLogin, User.NO_AUTHORIZATION,
				db.authorizeUser(adminWrongUsername, adminWrongPassword)
						.getAuthorizationLevel());
	}

	@Test
	public void testGetCustomers() {
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

		Database db = Database.getInstance("testSystem");
		db.addCustomer(one);
		db.addCustomer(two);
		db.addCustomer(three);

		ArrayList<Customer> customers = db.getCustomers();
		assertEquals(name1, customers.get(0).getName());
		assertEquals(name2, customers.get(1).getName());
		assertEquals(name3, customers.get(2).getName());

		// cleanup
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

		Database db = Database.getInstance("testSystem");
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
	public void testGetProducts() {

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

		Database db = Database.getInstance("testSystem");
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
	public void testGetUsers() {

		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		String email1 = "email 1";

		User one = new User(1, name1, email1, email1);
		User two = new User(1, name2, email1, email1);
		User three = new User(1, name3, email1, email1);

		Database db = Database.getInstance("testSystem");
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
	public void testGetInvoices() {
		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";
		String email1 = "email 1";

		Supplier supplier1 = new Supplier(name1, name1, name1, name1);
		Product product1 = new Product(name1, 1, 1, 1, supplier1);
		Product product2 = new Product(name2, 2, 2, 2, supplier1);
		Product product3 = new Product(name3, 3, 3, 3, supplier1);

		Database db = Database.getInstance("testSystem");
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
	public void testGetOrders() {
		String name1 = "name 1";
		String name2 = "name 2";
		String name3 = "name 3";

		Supplier supplier1 = new Supplier(name1, name1, name1, name1);
		Product product1 = new Product(name1, 1, 1, 1, supplier1);
		Product product2 = new Product(name2, 2, 2, 2, supplier1);
		Product product3 = new Product(name3, 3, 3, 3, supplier1);

		Database db = Database.getInstance("testSystem");
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
	public void testGetDeliveries() {
		String name1 = "name 1";

		Supplier supplier1 = new Supplier(name1, name1, name1, name1);

		Delivery one = new Delivery(supplier1, 1);
		Delivery two = new Delivery(supplier1, 2);
		Delivery three = new Delivery(supplier1, 3);

		Database db = Database.getInstance("testSystem");
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
	public void cleanup() {
		for (Customer c : Database.getInstance("testSystem").getCustomers()) {
			Database.getInstance("testSystem").deleteCustomer(c);
		}
		for (Product p : Database.getInstance("testSystem").getProducts()) {
			Database.getInstance("testSystem").deleteProduct(p);
		}
		for (Supplier s : Database.getInstance("testSystem").getSuppliers()) {
			Database.getInstance("testSystem").deleteSupplier(s);
		}
		for (User u : Database.getInstance("testSystem").getUsers()) {
			Database.getInstance("testSystem").deleteUser(u);
		}
		for (Invoice i : Database.getInstance("testSystem").getInvoices()) {
			Database.getInstance("testSystem").deleteInvoice(i);
		}
		for (Delivery d : Database.getInstance("testSystem").getDeliveries()) {
			Database.getInstance("testSystem").deleteDelivery(d);
		}
		for (Order o : Database.getInstance("testSystem").getOrders()) {
			Database.getInstance("testSystem").deleteOrder(o);
		}
		for (LineItem li : Database.getInstance("testSystem").getOrderItems()) {
			Database.getInstance("testSystem").deleteOrderItem(li);
		}
		for (LineItem li : Database.getInstance("testSystem").getInvoiceItems()) {
			Database.getInstance("testSystem").deleteInvoiceItem(li);
		}
	}

	@Test
	public void testGetOrdersBetween() {
		String temp = "temp";
		Date now = new Date();
		Date now500 = new Date(now.getTime() + 500);
		Date inBetween = new Date(now.getTime() + 200);
		Date nowMinus1 = new Date(now.getTime() - 1);
		Date now500Plus1 = new Date(now500.getTime() + 1);
		Supplier supplier = new Supplier(temp, temp, temp, temp);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		Order tooOld = new Order(1, supplier, 1, lineItems, 1, nowMinus1);
		Order justOldEnough = new Order(1, supplier, 1, lineItems, 1, now);
		Order justRight = new Order(1, supplier, 1, lineItems, 1, inBetween);
		Order justNewEnough = new Order(1, supplier, 1, lineItems, 1, now500);
		Order tooNew = new Order(1, supplier, 1, lineItems, 1, now500Plus1);

		Database db = Database.getInstance("testSystem");
		db.addOrder(justRight);
		db.addOrder(justOldEnough);
		db.addOrder(tooOld);
		db.addOrder(tooNew);
		db.addOrder(justNewEnough);

		ArrayList<Order> validOrders = db.getOrdersBetween(now, now500);
		assertEquals(1, validOrders.get(0).getID());
		assertEquals(2, validOrders.get(1).getID());
		assertEquals(5, validOrders.get(2).getID());

		// cleanup
		for (Order o : db.getOrders()) {
			db.deleteOrder(o);
		}
	}

	@Test
	public void testGetInvoicesBetween() {
		String temp = "temp";
		Date now = new Date();
		Date now500 = new Date(now.getTime() + 500);
		Date inBetween = new Date(now.getTime() + 200);
		Date nowMinus1 = new Date(now.getTime() - 1);
		Date now500Plus1 = new Date(now500.getTime() + 1);
		Customer customer = new Customer(temp, temp, temp, temp);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		Invoice tooOld = new Invoice(lineItems, customer, 1, nowMinus1);
		Invoice justOldEnough = new Invoice(lineItems, customer, 1, now);
		Invoice justRight = new Invoice(lineItems, customer, 1, inBetween);
		Invoice justNewEnough = new Invoice(lineItems, customer, 1, now500);
		Invoice tooNew = new Invoice(lineItems, customer, 1, now500Plus1);

		Database db = Database.getInstance("testSystem");
		db.addInvoice(justRight);
		db.addInvoice(justOldEnough);
		db.addInvoice(tooOld);
		db.addInvoice(tooNew);
		db.addInvoice(justNewEnough);

		ArrayList<Invoice> validInvoices = db.getInvoicesBetween(now, now500);
		assertEquals(1, validInvoices.get(0).getID());
		assertEquals(2, validInvoices.get(1).getID());
		assertEquals(5, validInvoices.get(2).getID());

		// cleanup
		for (Invoice i : db.getInvoices()) {
			db.deleteInvoice(i);
		}
	}

	@Test
	public void testGetDeliveriesBetween() {
		String temp = "temp";
		Date now = new Date();
		Date now500 = new Date(now.getTime() + 500);
		Date inBetween = new Date(now.getTime() + 200);
		Date nowMinus1 = new Date(now.getTime() - 1);
		Date now500Plus1 = new Date(now500.getTime() + 1);
		Supplier supplier = new Supplier(temp, temp, temp, temp);
		Delivery tooOld = new Delivery(supplier, 1, nowMinus1, 1);
		Delivery justOldEnough = new Delivery(supplier, 2, now, 1);
		Delivery justRight = new Delivery(supplier, 3, inBetween, 1);
		Delivery justNewEnough = new Delivery(supplier, 4, now500, 1);
		Delivery tooNew = new Delivery(supplier, 5, now500Plus1, 1);

		Database db = Database.getInstance("testSystem");
		db.addDelivery(justRight);
		db.addDelivery(justOldEnough);
		db.addDelivery(tooOld);
		db.addDelivery(tooNew);
		db.addDelivery(justNewEnough);

		ArrayList<Delivery> validDeliveries = db.getDeliveriesBetween(now,
				now500);
		assertEquals(3, validDeliveries.get(0).getOrderID());
		assertEquals(2, validDeliveries.get(1).getOrderID());
		assertEquals(4, validDeliveries.get(2).getOrderID());

		// cleanup
		for (Delivery d : db.getDeliveries()) {
			db.deleteDelivery(d);
		}
	}
}
