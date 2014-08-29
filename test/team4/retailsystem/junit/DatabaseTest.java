package team4.retailsystem.junit;

import static org.junit.Assert.*;

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

		// get ID-assigned objects from the DB
		supplier = db.getSuppliers().get(0);
		newSupplier = db.getSuppliers().get(1);
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

		// get ID-assigned objects from the DB
		customer1 = db.getCustomers().get(0);
		customer2 = db.getCustomers().get(1);
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

		Database db = Database.getInstance("testSystem");
		db.addSupplier(supplier1);
		db.addProduct(product1);

		// get ID-assigned objects from the DB
		supplier1 = db.getSuppliers().get(0);
		product1 = db.getProducts().get(0);
		Order order = new Order(cost, supplier1, deliveryID, orderItems);
		Date date = order.getOrderDate();

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
		String error = "Failed to retrieve one of the items";
		
		String custData1 = "name 1";
		String custData2 = "name 2";
		String custData3 = "name 3";

		Customer one = new Customer(custData1, custData1, custData1, custData1);
		Customer two = new Customer(custData2, custData2, custData2, custData2);
		Customer three = new Customer(custData3, custData3, custData3, custData3);

		Database db = Database.getInstance("testSystem");
		db.addCustomer(one);
		db.addCustomer(two);
		db.addCustomer(three);

		ArrayList<Customer> customers = db.getCustomers();
		assertEquals(error, custData1, customers.get(0).getName());
		assertEquals(error, custData2, customers.get(1).getName());
		assertEquals(error, custData3, customers.get(2).getName());
	}

	@Test
	public void testGetSuppliers() {
		String error = "Failed to retrieve one of the items";

		String supData1 = "name 1";
		String supData2 = "name 2";
		String supData3 = "name 3";

		Supplier one = new Supplier(supData1, supData1, supData1, supData1);
		Supplier two = new Supplier(supData2, supData2, supData2, supData2);
		Supplier three = new Supplier(supData3, supData3, supData3, supData3);

		Database db = Database.getInstance("testSystem");
		db.addSupplier(one);
		db.addSupplier(two);
		db.addSupplier(three);

		ArrayList<Supplier> suppliers = db.getSuppliers();
		assertEquals(error, supData1, suppliers.get(0).getName());
		assertEquals(error, supData2, suppliers.get(1).getName());
		assertEquals(error, supData3, suppliers.get(2).getName());
	}

	@Test
	public void testGetProducts() {
		String error = "Failed to retrieve one of the items";

		String prodData1 = "name 1";
		String prodData2 = "name 2";
		String prodData3 = "name 3";
		String suppData = "supplier";

		Supplier supplier = new Supplier(suppData, suppData, suppData, suppData);

		Product one = new Product(prodData1, 1, 1, 1, supplier);
		Product two = new Product(prodData2, 1, 1, 1, supplier);
		Product three = new Product(prodData3, 1, 1, 1, supplier);

		Database db = Database.getInstance("testSystem");
		db.addProduct(one);
		db.addProduct(two);
		db.addProduct(three);

		ArrayList<Product> products = db.getProducts();
		assertEquals(error, prodData1, products.get(0).getName());
		assertEquals(error, prodData2, products.get(1).getName());
		assertEquals(error, prodData3, products.get(2).getName());
	}

	@Test
	public void testGetUsers() {
		String error = "Failed to retrieve one of the items";

		String userData1 = "name 1";
		String userData2 = "name 2";
		String userData3 = "name 3";

		User one = new User(User.NORMAL_USER, userData1, userData1, userData1);
		User two = new User(User.NORMAL_USER, userData2, userData2, userData2);
		User three = new User(User.NORMAL_USER, userData3, userData3, userData3);

		Database db = Database.getInstance("testSystem");
		db.addUser(one);
		db.addUser(two);
		db.addUser(three);

		ArrayList<User> users = db.getUsers();
		assertEquals(error, userData1, users.get(0).getUsername());
		assertEquals(error, userData2, users.get(1).getUsername());
		assertEquals(error, userData3, users.get(2).getUsername());
	}

	@Test
	public void testGetInvoices() {
		String error = "Failed to retrieve one of the items";
		
		int id1 = 1;
		int id2 = 2;
		int id3 = 3;
		String tempData = "unimportant";

		Supplier supplier1 = new Supplier(tempData, tempData, tempData, tempData);
		Product product1 = new Product(tempData, 1, 1, 1, supplier1);
		Product product2 = new Product(tempData, 2, 2, 2, supplier1);
		Product product3 = new Product(tempData, 3, 3, 3, supplier1);

		Database db = Database.getInstance("testSystem");
		db.addProduct(product1);
		db.addProduct(product2);
		db.addProduct(product3);

		Customer customer = new Customer(tempData, tempData, tempData, tempData);
		LineItem lineItem1 = new LineItem(id1, id1);
		LineItem lineItem2 = new LineItem(id2, id2);
		LineItem lineItem3 = new LineItem(id3, id3);

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
		assertEquals(error, id1, invoices.get(0).getLineItems().get(0).getProductID());
		assertEquals(error, id2, invoices.get(0).getLineItems().get(1).getProductID());
		assertEquals(error, id2, invoices.get(1).getLineItems().get(0).getProductID());
		assertEquals(error, id3, invoices.get(1).getLineItems().get(1).getProductID());
		assertEquals(error, id3, invoices.get(2).getLineItems().get(0).getProductID());
		assertEquals(error, id1, invoices.get(2).getLineItems().get(1).getProductID());
	}

	@Test
	public void testGetOrders() {
		String error = "Failed to retrieve one of the items";

		int id1 = 1;
		int id2 = 2;
		int id3 = 3;
		String tempData = "unimportant";

		Supplier supplier1 = new Supplier(tempData, tempData, tempData, tempData);
		Product product1 = new Product(tempData, 1, 1, 1, supplier1);
		Product product2 = new Product(tempData, 2, 2, 2, supplier1);
		Product product3 = new Product(tempData, 3, 3, 3, supplier1);

		Database db = Database.getInstance("testSystem");
		db.addProduct(product1);
		db.addProduct(product2);
		db.addProduct(product3);

		LineItem lineItem1 = new LineItem(id1, id1);
		LineItem lineItem2 = new LineItem(id2, id2);
		LineItem lineItem3 = new LineItem(id3, id3);

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
		assertEquals(error, id1, orders.get(0).getLineItems().get(0).getProductID());
		assertEquals(error, id2, orders.get(0).getLineItems().get(1).getProductID());
		assertEquals(error, id2, orders.get(1).getLineItems().get(0).getProductID());
		assertEquals(error, id3, orders.get(1).getLineItems().get(1).getProductID());
		assertEquals(error, id3, orders.get(2).getLineItems().get(0).getProductID());
		assertEquals(error, id1, orders.get(2).getLineItems().get(1).getProductID());
	}

	@Test
	public void testGetDeliveries() {
		String error = "Failed to retrieve one of the items";
		
		int id1 = 1;
		int id2 = 2;
		int id3 = 3;
		String tempData = "unimportant";

		Supplier supplier1 = new Supplier(tempData, tempData, tempData, tempData);

		Delivery one = new Delivery(supplier1, id1);
		Delivery two = new Delivery(supplier1, id2);
		Delivery three = new Delivery(supplier1, id3);

		Database db = Database.getInstance("testSystem");
		db.addDelivery(one);
		db.addDelivery(two);
		db.addDelivery(three);

		ArrayList<Delivery> deliveries = db.getDeliveries();
		assertEquals(error, id1, deliveries.get(0).getOrderID());
		assertEquals(error, id2, deliveries.get(1).getOrderID());
		assertEquals(error, id3, deliveries.get(2).getOrderID());
	}

	@After
	public void cleanup() {
		Database db = Database.getInstance("testSystem");
		
		for (Customer c : db.getCustomers()) {
			db.deleteCustomer(c);
		}
		for (Product p : db.getProducts()) {
			db.deleteProduct(p);
		}
		for (Supplier s : db.getSuppliers()) {
			db.deleteSupplier(s);
		}
		for (User u : db.getUsers()) {
			db.deleteUser(u);
		}
		for (Invoice i : db.getInvoices()) {
			db.deleteInvoice(i);
		}
		for (Delivery d : db.getDeliveries()) {
			db.deleteDelivery(d);
		}
		for (Order o : db.getOrders()) {
			db.deleteOrder(o);
		}
		for (LineItem li : db.getOrderItems()) {
			db.deleteOrderItem(li);
		}
		for (LineItem li : db.getInvoiceItems()) {
			db.deleteInvoiceItem(li);
		}
	}

	@Test
	public void testGetOrdersBetween() {
		String error = "Failed to fetch an item withing the date boundary";
		
		Database db = Database.getInstance("testSystem");		
		String tempData = "unimportant";
		
		// boundary testing
		Date min = new Date();
		Date max = new Date(min.getTime() + 500);
		Date typical = new Date(min.getTime() + 200);
		Date justOutside1 = new Date(min.getTime() - 1);
		Date justOutside2 = new Date(max.getTime() + 1);
		
		Supplier supplier = new Supplier(tempData, tempData, tempData, tempData);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		Order invoiceJustOutside1 = new Order(1, supplier, 1, lineItems, 1, justOutside1);
		Order invoiceMin = new Order(1, supplier, 1, lineItems, 1, min);
		Order invoiceTypical = new Order(1, supplier, 1, lineItems, 1, typical);
		Order invoiceMax = new Order(1, supplier, 1, lineItems, 1, max);
		Order invoiceJustOutside2 = new Order(1, supplier, 1, lineItems, 1, justOutside2);

		db.addOrder(invoiceTypical);
		db.addOrder(invoiceMin);
		db.addOrder(invoiceJustOutside1);
		db.addOrder(invoiceJustOutside2);
		db.addOrder(invoiceMax);

		ArrayList<Order> validOrders = db.getOrdersBetween(min, max);
		assertEquals(error, 1, validOrders.get(0).getID());
		assertEquals(error, 2, validOrders.get(1).getID());
		assertEquals(error, 5, validOrders.get(2).getID());
	}

	@Test
	public void testGetInvoicesBetween() {
		String error = "Failed to fetch an item withing the date boundary";
		
		Database db = Database.getInstance("testSystem");		
		String tempData = "unimportant";
		
		// boundary testing
		Date min = new Date();
		Date max = new Date(min.getTime() + 500);
		Date typical = new Date(min.getTime() + 200);
		Date justOutside1 = new Date(min.getTime() - 1);
		Date justOutside2 = new Date(max.getTime() + 1);
		
		Customer customer = new Customer(tempData, tempData, tempData, tempData);
		ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
		Invoice orderJustOutside1 = new Invoice(lineItems, customer, 1, justOutside1);
		Invoice orderMin = new Invoice(lineItems, customer, 1, min);
		Invoice orderTypical = new Invoice(lineItems, customer, 1, typical);
		Invoice orderMax = new Invoice(lineItems, customer, 1, max);
		Invoice orderJustOutside2 = new Invoice(lineItems, customer, 1, justOutside2);

		db.addInvoice(orderTypical);
		db.addInvoice(orderJustOutside1);
		db.addInvoice(orderMin);
		db.addInvoice(orderMax);
		db.addInvoice(orderJustOutside2);

		ArrayList<Invoice> validInvoices = db.getInvoicesBetween(min, max);
		assertEquals(error, 1, validInvoices.get(0).getID());
		assertEquals(error, 3, validInvoices.get(1).getID());
		assertEquals(error, 4, validInvoices.get(2).getID());
	}

	@Test
	public void testGetDeliveriesBetween() {
		String error = "Failed to fetch an item withing the date boundary";
		
		Database db = Database.getInstance("testSystem");		
		String tempData = "unimportant";

		// boundary testing
		Date min = new Date();
		Date max = new Date(min.getTime() + 500);
		Date typical = new Date(min.getTime() + 200);
		Date justOutside1 = new Date(min.getTime() - 1);
		Date justOutside2 = new Date(max.getTime() + 1);
		
		Supplier supplier = new Supplier(tempData, tempData, tempData, tempData);
		Delivery delJustOutside1 = new Delivery(supplier, 1, justOutside1, 1);
		Delivery delMin = new Delivery(supplier, 2, min, 1);
		Delivery delTypical = new Delivery(supplier, 3, typical, 1);
		Delivery delMax = new Delivery(supplier, 4, max, 1);
		Delivery delJustOutside2 = new Delivery(supplier, 5, justOutside2, 1);

		db.addDelivery(delTypical);
		db.addDelivery(delMin);
		db.addDelivery(delJustOutside1);
		db.addDelivery(delJustOutside2);
		db.addDelivery(delMax);

		ArrayList<Delivery> validDeliveries = db.getDeliveriesBetween(min,
				max);
		assertEquals(error, delTypical.getOrderID(), validDeliveries.get(0).getOrderID());
		assertEquals(error, delMin.getOrderID(), validDeliveries.get(1).getOrderID());
		assertEquals(error, delMax.getOrderID(), validDeliveries.get(2).getOrderID());
	}
}
