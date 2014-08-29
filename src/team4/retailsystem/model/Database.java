package team4.retailsystem.model;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import team4.retailsystem.utils.EncryptionModule;

/**
 * A permanent SQLite database module with interfaces for CRUD.
 * 
 * @author Szymon
 * @author Alan
 */
public class Database {

	private static Database db = null;
	private Connection connection = null;
	private PreparedStatement pStatement = null;
	boolean open = false;
			
	private static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE CUSTOMERS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "NAME TEXT, "
			+ "PHONENO TEXT, "
			+ "EMAIL TEXT, "
			+ "ADDRESS TEXT)";
	private static final String CUSTOMERS_DEFINITION = "CUSTOMERS (ID,NAME,PHONENO,EMAIL,ADDRESS)";
	
	private static final String CREATE_SUPPLIERS_TABLE = "CREATE TABLE SUPPLIERS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "NAME TEXT, "
			+ "PHONENO TEXT, "
			+ "EMAIL TEXT, "
			+ "ADDRESS TEXT)";
	private static final String SUPPLIERS_DEFINITION = "SUPPLIERS (ID,NAME,PHONENO,EMAIL,ADDRESS)";
	
	private static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE PRODUCTS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "NAME TEXT, "
			+ "COST REAL, "
			+ "MARKUP REAL, "
			+ "STOCKLEVEL INTEGER, "
			+ "SUPPLIERID INTEGER)";
	private static final String PRODUCTS_DEFINITION = "PRODUCTS (ID,NAME,COST,MARKUP,STOCKLEVEL,SUPPLIERID)";
	
	private static final String CREATE_DELIVERIES_TABLE = "CREATE TABLE DELIVERIES "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "DELIVERYDATE INTEGER, "
			+ "ORDERID INTEGER, "
			+ "SUPPLIERID INTEGER)";
	private static final String DELIVERIES_DEFINITION = "DELIVERIES (ID,DELIVERYDATE,ORDERID,SUPPLIERID)";
	
	private static final String CREATE_INVOICES_TABLE = "CREATE TABLE INVOICES "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "DATE INTEGER, "
			+ "CUSTOMERID INTEGER)";	
	private static final String INVOICES_DEFINITION = "INVOICES (ID,DATE,CUSTOMERID)";
	
	private static final String CREATE_ORDERS_TABLE = "CREATE TABLE ORDERS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "DATE INTEGER, "
			+ "SUPPLIERID INTEGER,"
			+ "DELIVERYID INTEGER)";
	private static final String ORDERS_DEFINITION = "ORDERS (ID,DATE,SUPPLIERID,DELIVERYID)";
	
	private static final String CREATE_ORDER_ITEMS_TABLE = "CREATE TABLE ORDERITEMS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "PRODUCTID INTEGER, "
			+ "QUANTITY INTEGER, "
			+ "ORDERID INTEGER)";
	private static final String ORDER_ITEMS_DEFINITION = "ORDERITEMS (ID,PRODUCTID,QUANTITY,ORDERID)";
	
	private static final String CREATE_INVOICE_ITEMS_TABLE = "CREATE TABLE INVOICEITEMS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "PRODUCTID INTEGER, " 
			+ "QUANTITY INTEGER, " 
			+ "INVOICEID INTEGER)";
	private static final String INVOICE_ITEMS_DEFINITION = "INVOICEITEMS (ID,PRODUCTID,QUANTITY,INVOICEID)";

	private static final String CREATE_USERS_TABLE = "CREATE TABLE USERS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "USERNAME TEXT NOT NULL UNIQUE, " 
			+ "AUTHLEVEL INTEGER, " 
			+ "PASSWDIGEST TEXT, " 
			+ "SALT TEXT)";
	private static final String USERS_DEFINITION = "USERS (ID,USERNAME,AUTHLEVEL,PASSWDIGEST,SALT)";
	
	private Database(String dbName) {
		openConnection(dbName);
		createTables();	
	}

	/**
	 * Get a handle to the default Database object.
	 * <p>
	 * The default database object being is a file named
	 * "Retail Storage System.db". If an existing file with that name is found
	 * in the local directory it is opened and used. If no existing files are
	 * found, a new file is created and used instead.
	 * 
	 * @return a Database handle
	 */
	public static Database getInstance() {
		if (db == null) {
			db = new Database("Retail Storage System");
		}
		return db;
	}

	/**
	 * Get a handle to a non-default Database object.
	 * <p>
	 * This method allows unit tests to operate on their own database file to
	 * keep "Retail Storage System.db" file clean. If an existing file with that
	 * name is found in the local directory it is opened and used. If no
	 * existing files are found, a new file is created and used instead.
	 * 
	 * @param dbName
	 *            the name of the database file to use
	 * @return a Database handle
	 */
	public static Database getInstance(String dbName) {
		if (db == null) {
			db = new Database(dbName);
		}
		return db;
	}

	private void openConnection(String databaseName) {
		if(open){
			return;			
		}
		open = true;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("The database class could not be found: "
					+ e.getClass().getName());
			return;
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+databaseName+".db");
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
			return;
		}
	}

	/**
	 * Close the connection to the database, discard all handles.
	 */
	public void closeConnection() {
		try {
			pStatement.close();
			connection.close();
			open = false;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
	}

	/**
	 * Store a new Customer.
	 * 
	 * @param customer
	 *            the customer to be stored in the database
	 * @return true if the database changed as a result of the call
	 */
	public boolean addCustomer(Customer customer) {
		if(customer==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + CUSTOMERS_DEFINITION + " VALUES (NULL,?,?,?,?)");
			pStatement.setString(1, customer.getName());
			pStatement.setString(2, customer.getTelephoneNumber());
			pStatement.setString(3, customer.getEmail());
			pStatement.setString(4, customer.getAddress());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;
	}

	/**
	 * Store a new Supplier.
	 * 
	 * @param supplier
	 *            the supplier to be stored in the database
	 * @return true if the database changed as a result of the call
	 */
	public boolean addSupplier(Supplier supplier) {
		if(supplier==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + SUPPLIERS_DEFINITION + " VALUES (NULL,?,?,?,?)");
			pStatement.setString(1, supplier.getName());
			pStatement.setString(2, supplier.getTelephoneNumber());
			pStatement.setString(3, supplier.getEmail());
			pStatement.setString(4, supplier.getAddress());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;		
	}

	/**
	 * Store a new Product.
	 * 
	 * @param product
	 *            the product to be stored in the database
	 * @return true if the database changed as a result of the call
	 */
	public boolean addProduct(Product product) {
		if(product==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + PRODUCTS_DEFINITION + " VALUES (NULL,?,?,?,?,?)");
			pStatement.setString(1, product.getName());
			pStatement.setDouble(2, product.getCost());
			pStatement.setDouble(3, product.getMarkup());
			pStatement.setInt(4, product.getStockLevel());
			pStatement.setInt(5, product.getSupplier().getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;		
	}

	/**
	 * Store a new Delivery.
	 * 
	 * @param delivery
	 *            the delivery to be stored in the database
	 * @return true if the database changed as a result of the call
	 */
	public boolean addDelivery(Delivery delivery) {
		if(delivery==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + DELIVERIES_DEFINITION + " VALUES (NULL,?,?,?)");
			pStatement.setLong(1, delivery.getDate().getTime());
			pStatement.setInt(2, delivery.getOrderID());
			pStatement.setInt(3, delivery.getSupplier().getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;		
	}

	/**
	 * Store a new Invoice.
	 * 
	 * @param invoice
	 *            the invoice to be stored in the database
	 * @return true if the database changed as a result of the call
	 */
	public boolean addInvoice(Invoice invoice) {
		if(invoice==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + INVOICES_DEFINITION + " VALUES (NULL,?,?)");
			pStatement.setLong(1, invoice.getDate().getTime());
			pStatement.setInt(2, invoice.getCustomer().getID());
			pStatement.executeUpdate();
			
			int id = 0;
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			while(generatedKeys.next()){			
				id = generatedKeys.getInt(1);
			}
			generatedKeys.close();
			
			for(LineItem li : invoice.getLineItems()){
				addInvoiceItem(li, id);
			}
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;
	}

	/**
	 * Store a new Order.
	 * 
	 * @param order
	 *            the order to be stored in the database
	 * @return true if the database changed as a result of the call
	 */
	public boolean addOrder(Order order) {
		if(order==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + ORDERS_DEFINITION + " VALUES (NULL,?,?,?)");
			pStatement.setLong(1, order.getOrderDate().getTime());
			pStatement.setInt(2, order.getSupplier().getID());
			pStatement.setInt(3, order.getDeliveryID());
			pStatement.executeUpdate();
			
			int id = 0;
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			while(generatedKeys.next()){			
				id = generatedKeys.getInt(1);
			}
			generatedKeys.close();
			
			for(LineItem li : order.getLineItems()){
				addOrderItem(li, id);
			}
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Store a new User.
	 * 
	 * @param user
	 *            the user to be stored in the database
	 * @return true if the database changed as a result of the call
	 */
	public boolean addUser(User user) {
		if(user==null){
			return false;
		}				
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + USERS_DEFINITION + " VALUES (NULL,?,?,?,?)");
			pStatement.setString(1, user.getUsername());
			pStatement.setInt(2, user.getAuthorizationLevel());
			pStatement.setString(3, user.getPasswordDigest());
			pStatement.setString(4, user.getSalt());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;			
	}

	/**
	 * Store a new User while obfuscating the process of user creation.
	 * <p>
	 * Hides complexity of salts and password digests, uses a salt generated
	 * using a cryptographically strong random number generator.
	 * 
	 * @param authorizationLevel
	 *            level of User authorization
	 * @param username
	 *            user login name
	 * @param password
	 *            password as a plaintext string
	 * @return true if the database changed as a result of the call
	 */
	public boolean addUser(int authorizationLevel, String username, String password){
		EncryptionModule em = null;
		try {
			em = new EncryptionModule();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		String salt = em.getRandomSalt();
		return addUser(new User(authorizationLevel, username, em.encrypt(password, salt), salt));
	}

	/**
	 * Store a new order item.
	 * 
	 * @param lineItem
	 *            the LineItem to be stored in the database
	 * @param orderID
	 *            the ID of the order associated with the lineItem (foreign key)
	 * @return true if the database changed as a result of the call
	 */
	public boolean addOrderItem(LineItem lineItem, int orderID) {	
		if(lineItem==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + ORDER_ITEMS_DEFINITION + " VALUES (NULL,?,?,?)");
			pStatement.setInt(1, lineItem.getProductID());
			pStatement.setInt(2, lineItem.getQuantity());
			pStatement.setInt(3, orderID);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;				
	}

	/**
	 * Store a new invoice item.
	 * 
	 * @param lineItem
	 *            the LineItem to be stored in the database
	 * @param inoviceID
	 *            the ID of the invoice associated with the lineItem (foreign
	 *            key)
	 * @return true if the database changed as a result of the call
	 */
	public boolean addInvoiceItem(LineItem lineItem, int invoiceID) {	
		if(lineItem==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + INVOICE_ITEMS_DEFINITION + " VALUES (NULL,?,?,?)");
			pStatement.setInt(1, lineItem.getProductID());
			pStatement.setInt(2, lineItem.getQuantity());
			pStatement.setInt(3, invoiceID);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;				
	}

	/**
	 * Delete the given customer from the database.
	 * 
	 * @param customer
	 *            the customer to be removed from the database - removal happens
	 *            based on the customer id
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteCustomer(Customer customer) {
		if(customer==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE ID=(?)");
			pStatement.setInt(1, customer.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;		
	}

	/**
	 * Delete the given supplier from the database.
	 * 
	 * @param supplier
	 *            the supplier to be removed from the database - removal happens
	 *            based on the supplier id
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteSupplier(Supplier supplier) {
		if(supplier==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("DELETE FROM SUPPLIERS WHERE ID=(?)");
			pStatement.setInt(1, supplier.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Delete the given product from the database.
	 * 
	 * @param product
	 *            the product to be removed from the database - removal happens
	 *            based on the product id
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteProduct(Product product) {
		if(product==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("DELETE FROM PRODUCTS WHERE ID=(?)");
			pStatement.setInt(1, product.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;	
	}

	/**
	 * Delete the given delivery from the database.
	 * 
	 * @param delivery
	 *            the delivery to be removed from the database - removal happens
	 *            based on the delivery id
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteDelivery(Delivery delivery) {
		if(delivery==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("DELETE FROM DELIVERIES WHERE ID=(?)");
			pStatement.setInt(1, delivery.getDeliveryID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;	
	}

	/**
	 * Delete the given invoice from the database. This also removes its
	 * associated LineItems.
	 * 
	 * @param invoice
	 *            the invoice to be removed from the database - removal happens
	 *            based on the invoice id
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteInvoice(Invoice invoice) {
		if(invoice==null){
			return false;
		}		
		deleteInvoiceItems(invoice.getID());
		try {
			pStatement = connection.prepareStatement("DELETE FROM INVOICES WHERE ID=(?)");
			pStatement.setInt(1, invoice.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;	
	}

	/**
	 * Delete the given order from the database. This also removes its
	 * associated LineItems.
	 * 
	 * @param order
	 *            the order to be removed from the database - removal happens
	 *            based on the order id
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteOrder(Order order) {
		if(order==null){
			return false;
		}
		deleteOrderItems(order.getID());
		try {
			pStatement = connection.prepareStatement("DELETE FROM ORDERS WHERE ID=(?)");
			pStatement.setInt(1, order.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;	
	}

	/**
	 * Delete the given user from the database.
	 * 
	 * @param user
	 *            the user to be removed from the database - removal happens
	 *            based on the user id
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteUser(User user) {
		if(user==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("DELETE FROM USERS WHERE ID=(?)");
			pStatement.setInt(1, user.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}
	
	/**
	 * Delete all order items associated with the given order ID (foreign key).
	 * 
	 * @param orderID
	 *            ID of the associated order
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteOrderItems(int orderID){	
		try {
			pStatement = connection.prepareStatement("DELETE FROM ORDERITEMS WHERE ORDERID=(?)");
			pStatement.setInt(1, orderID);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}
	
	/**
	 * Delete the given order item from the database.
	 * 
	 * @param orderItem
	 *            orderItem to be deleted
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteOrderItem(LineItem orderItem){	
		if(orderItem==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("DELETE FROM ORDERITEMS WHERE ID=(?)");
			pStatement.setInt(1, orderItem.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}
	
	/**
	 * Delete all invoice items associated with the given invoice ID (foreign
	 * key).
	 * 
	 * @param invoiceID
	 *            ID of the associated invoice
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteInvoiceItems(int invoiceID){		
		try {
			pStatement = connection.prepareStatement("DELETE FROM INVOICEITEMS WHERE INVOICEID=(?)");
			pStatement.setInt(1, invoiceID);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Delete the given invoice item from the database.
	 * 
	 * @param invoiceItem
	 *            invoiceItem to be deleted
	 * @return true if the database changed as a result of the call
	 */
	public boolean deleteInvoiceItem(LineItem invoiceItem){		
		if(invoiceItem==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("DELETE FROM INVOICEITEMS WHERE ID=(?)");
			pStatement.setInt(1, invoiceItem.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}
	
	/**
	 * Get all customers from the database as an ArrayList.
	 * 
	 * @return ArrayList of Customers.
	 */
	public ArrayList<Customer> getCustomers() {
		ArrayList<Customer> output = new ArrayList<Customer>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				String telephoneNo = rs.getString("phoneno");
				String email = rs.getString("email");
				String address = rs.getString("address");
				int id = rs.getInt("id");
				output.add(new Customer(name, telephoneNo, email, address, id));
			}		
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}		
		return output;
	}
	
	/**
	 * Get all suppliers from the database as an ArrayList.
	 * 
	 * @return ArrayList of Suppliers.
	 */
	public ArrayList<Supplier> getSuppliers() {
		ArrayList<Supplier> output = new ArrayList<Supplier>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM SUPPLIERS");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				String telephoneNo = rs.getString("phoneno");
				String email = rs.getString("email");
				String address = rs.getString("address");
				int id = rs.getInt("id");
				output.add(new Supplier(name, email, address, telephoneNo, id));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}
	
	/**
	 * Get all products from the database as an ArrayList.
	 * 
	 * @return ArrayList of Products.
	 */
	public ArrayList<Product> getProducts() {
		ArrayList<Product> output = new ArrayList<Product>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM PRODUCTS");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				double cost = rs.getDouble("cost");
				double markup = rs.getDouble("markup");
				int stockLevel = rs.getInt("stockLevel");				
				int id = rs.getInt("id");
				int supplierID = rs.getInt("supplierid");
				Supplier supplier = getSupplier(supplierID);
				output.add(new Product(name, cost, markup, stockLevel, supplier, id));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Get all deliveries from the database as an ArrayList.
	 * 
	 * @return ArrayList of Deliveries.
	 */
	public ArrayList<Delivery> getDeliveries() {
		ArrayList<Delivery> output = new ArrayList<Delivery>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM DELIVERIES");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				long deliveryDate = rs.getLong("deliverydate");
				Date date = new Date(deliveryDate);
				int orderID = rs.getInt("orderid");
				int supplierID = rs.getInt("supplierid");
				Supplier supplier = getSupplier(supplierID);
				output.add(new Delivery(supplier, orderID, date, id));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}
	
	/**
	 * Get all invoices from the database as an ArrayList.
	 * 
	 * @return ArrayList of Invoices.
	 */
	public ArrayList<Invoice> getInvoices() {
		ArrayList<Invoice> output = new ArrayList<Invoice>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM INVOICES");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				long dateLong = rs.getLong("date");
				Date date = new Date(dateLong);
				int customerID = rs.getInt("customerid");
				Customer customer = getCustomer(customerID);
				ArrayList<LineItem> invoiceItems = getInvoiceItems(id);
				output.add(new Invoice(invoiceItems, customer, id, date));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}
	
	/**
	 * Get all orders from the database as an ArrayList.
	 * 
	 * @return ArrayList of Orders.
	 */
	public ArrayList<Order> getOrders() {
		ArrayList<Order> output = new ArrayList<Order>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM ORDERS");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				long dateLong = rs.getLong("date");
				Date date = new Date(dateLong);
				int supplierID = rs.getInt("supplierid");
				int deliveryID = rs.getInt("deliveryid");
				Supplier supplier = getSupplier(supplierID);
				ArrayList<LineItem> orderItems = getOrderItems(id);
				output.add(new Order(-1, supplier, deliveryID, orderItems, id, date));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}
	
	/**
	 * Get all users from the database as an ArrayList.
	 * 
	 * @return ArrayList of Users.
	 */
	public ArrayList<User> getUsers() {
		ArrayList<User> output = new ArrayList<User>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM USERS");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String username = rs.getString("username");
				int authlevel = rs.getInt("authlevel");
				String passwdigest = rs.getString("passwdigest");
				String salt = rs.getString("salt");
				output.add(new User(id, authlevel, username, passwdigest, salt));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}	
	
	/**
	 * Get all order items associated with the given orderID as an ArrayList.
	 * 
	 * @param orderID
	 *            Unique ID number of the order.
	 * @return ArrayList of LineItems associated with the given orderID.
	 */
	public ArrayList<LineItem> getOrderItems(int orderID){
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM ORDERITEMS WHERE ORDERID=(?)");
			pStatement.setInt(1, orderID);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int productID = rs.getInt("productid");
				int id = rs.getInt("id");
				int quantity = rs.getInt("quantity");
				output.add(new LineItem(productID, quantity, id, orderID));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}
	
	/**
	 * Get all order items from the database as an ArrayList.
	 * 
	 * @return ArrayList of LineItems.
	 */
	public ArrayList<LineItem> getOrderItems(){
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM ORDERITEMS");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int productID = rs.getInt("productid");
				int orderId = rs.getInt("orderid");
				int id = rs.getInt("id");
				int quantity = rs.getInt("quantity");
				output.add(new LineItem(productID, quantity, id, orderId));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Get all invoice items associated with the given invoiceID as an
	 * ArrayList.
	 * 
	 * @param invoiceID
	 *            Unique ID number of the invoice.
	 * @return ArrayList of LineItems associated with the given invoiceID.
	 */
	public ArrayList<LineItem> getInvoiceItems(int invoiceID){	
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM INVOICEITEMS WHERE INVOICEID=(?)");
			pStatement.setInt(1, invoiceID);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int productID = rs.getInt("productid");
				int id = rs.getInt("id");
				int quantity = rs.getInt("quantity");
				output.add(new LineItem(productID, quantity, id, invoiceID));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}
	
	/**
	 * Get all invoice items from the database as an ArrayList.
	 * 
	 * @return ArrayList of LineItems.
	 */
	public ArrayList<LineItem> getInvoiceItems(){	
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM INVOICEITEMS");
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int productID = rs.getInt("productid");
				int invoiceID = rs.getInt("invoiceid");
				int id = rs.getInt("id");
				int quantity = rs.getInt("quantity");
				output.add(new LineItem(productID, quantity, id, invoiceID));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Retrieve a single Customer using the provided primary key ID.
	 * 
	 * @param id
	 *            unique customer ID (primary key)
	 * @return customer on id match; otherwise null
	 */
	public Customer getCustomer(int id) {
		Customer output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				String telephoneNo = rs.getString("phoneno");
				String email = rs.getString("email");
				String address = rs.getString("address");
				output = new Customer(name, telephoneNo, email, address, id);
			}			
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;		
	}

	/**
	 * Retrieve a single Supplier using the provided primary key ID.
	 * 
	 * @param id
	 *            unique supplier ID (primary key)
	 * @return supplier on id match; otherwise null
	 */
	public Supplier getSupplier(int id) {
		Supplier output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM SUPPLIERS WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				String telephoneNo = rs.getString("phoneno");
				String email = rs.getString("email");
				String address = rs.getString("address");
				output = new Supplier(name, email, address, telephoneNo, id);
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Retrieve a single Product using the provided primary key ID.
	 * 
	 * @param id
	 *            unique product ID (primary key)
	 * @return product on id match; otherwise null
	 */
	public Product getProduct(int id) {
		Product output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM PRODUCTS WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				String name = rs.getString("name");
				double cost = rs.getDouble("cost");
				double markup = rs.getDouble("markup");
				int stockLevel = rs.getInt("stockLevel");		
				int supplierID = rs.getInt("supplierid");
				Supplier supplier = getSupplier(supplierID);
				output = new Product(name, cost, markup, stockLevel, supplier, id);
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Retrieve a single Delivery using the provided primary key ID.
	 * 
	 * @param id
	 *            unique delivery ID (primary key)
	 * @return delivery on id match; otherwise null
	 */
	public Delivery getDelivery(int id) {
		Delivery output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM DELIVERIES WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				long deliveryDate = rs.getLong("deliverydate");
				Date date = new Date(deliveryDate);
				int orderID = rs.getInt("orderid");
				int supplierID = rs.getInt("supplierid");
				Supplier supplier = getSupplier(supplierID);
				output = new Delivery(supplier, orderID, date, id);
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Retrieve a single Invoice using the provided primary key ID.
	 * 
	 * @param id
	 *            unique invoice ID (primary key)
	 * @return invoice on id match; otherwise null
	 */
	public Invoice getInvoice(int id) {
		Invoice output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM INVOICES WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			if(!rs.next()){
				rs.close();
				return null;
			}
			long dateLong = rs.getLong("date");
			Date date = new Date(dateLong);
			int customerID = rs.getInt("customerid");
			rs.close();	
			Customer customer = getCustomer(customerID);
			ArrayList<LineItem> invoiceItems = getInvoiceItems(id);
			output = new Invoice(invoiceItems, customer, id, date);
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;		
	}

	/**
	 * Retrieve a single Order using the provided primary key ID.
	 * 
	 * @param id
	 *            unique order ID (primary key)
	 * @return order on id match; otherwise null
	 */
	public Order getOrder(int id) {
		Order output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			if(!rs.next()){
				rs.close();
				return null;
			}
			long dateLong = rs.getLong("date");
			Date date = new Date(dateLong);
			int supplierID = rs.getInt("supplierid");
			int deliveryID = rs.getInt("deliveryid");
			rs.close();
			Supplier supplier = getSupplier(supplierID);
			ArrayList<LineItem> orderItems = getOrderItems(id);
			output = new Order(-1, supplier, deliveryID, orderItems, id, date);
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Retrieve a single User using the provided primary key ID.
	 * 
	 * @param id
	 *            unique user ID (primary key)
	 * @return user on id match; otherwise null
	 */
	public User getUser(int id) {
		User output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				String username = rs.getString("username");
				int authlevel = rs.getInt("authlevel");
				String passwdigest = rs.getString("passwdigest");
				String salt = rs.getString("salt");
				output = new User(id, authlevel, username, passwdigest, salt);
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The customer to be updated will be identified using its primary key id.
	 * The customer that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param customer
	 *            customer with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateCustomer(Customer customer) {
		if(customer==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE CUSTOMERS SET NAME = ?, PHONENO = ?, EMAIL = ?, ADDRESS = ? WHERE ID = ?");
			pStatement.setString(1, customer.getName());
			pStatement.setString(2, customer.getTelephoneNumber());
			pStatement.setString(3, customer.getEmail());
			pStatement.setString(4, customer.getAddress());
			pStatement.setInt(5, customer.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;			
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The supplier to be updated will be identified using its primary key id.
	 * The supplier that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param supplier
	 *            supplier with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateSupplier(Supplier supplier) {
		if(supplier==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE SUPPLIERS SET NAME = ?, PHONENO = ?, EMAIL = ?, ADDRESS = ? WHERE ID = ?");
			pStatement.setString(1, supplier.getName());
			pStatement.setString(2, supplier.getTelephoneNumber());
			pStatement.setString(3, supplier.getEmail());
			pStatement.setString(4, supplier.getAddress());
			pStatement.setInt(5, supplier.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;			
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The product to be updated will be identified using its primary key id.
	 * The product that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param product
	 *            product with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateProduct(Product product) {
		if(product==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE PRODUCTS SET NAME = ?, COST = ?, MARKUP = ?, STOCKLEVEL = ?, SUPPLIERID = ? WHERE ID = ?");
			pStatement.setString(1, product.getName());
			pStatement.setDouble(2, product.getCost());
			pStatement.setDouble(3, product.getMarkup());
			pStatement.setInt(4, product.getStockLevel());
			pStatement.setInt(5, product.getSupplier().getID());
			pStatement.setInt(6, product.getID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}
	
	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The delivery to be updated will be identified using its primary key id.
	 * The delivery that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param delivery
	 *            delivery with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateDelivery(Delivery delivery) {
		if(delivery==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE DELIVERIES SET DELIVERYDATE = ?, ORDERID = ?, SUPPLIERID = ? WHERE ID = ?");
			pStatement.setLong(1, delivery.getDate().getTime());
			pStatement.setInt(2, delivery.getOrderID());
			pStatement.setInt(3, delivery.getSupplier().getID());
			pStatement.setInt(4, delivery.getDeliveryID());
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The invoice to be updated will be identified using its primary key id.
	 * The invoice that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param invoice
	 *            invoice with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateInvoice(Invoice invoice) {
		if(invoice==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE INVOICES SET DATE = ?, CUSTOMERID = ? WHERE ID = ?");
			pStatement.setLong(1, invoice.getDate().getTime());
			pStatement.setInt(2, invoice.getCustomer().getID());
			pStatement.setInt(3, invoice.getID());
			pStatement.executeUpdate();	
			
			Iterator<LineItem> oldItemItr = getInvoiceItems(invoice.getID()).iterator();
			LineItem oldItem = null;
			if(oldItemItr.hasNext()){
				oldItem = oldItemItr.next();				
			}
			for(LineItem newItem : invoice.getLineItems()){
				boolean updated = false;
				while(!updated){
					if(oldItem == null){
						addInvoiceItem(newItem, invoice.getID());
						updated = true;
					}else if(oldItem.getID() == newItem.getID()){
						updateInvoiceItem(newItem);
						updated = true;
					}else{
						deleteInvoiceItem(oldItem);
					}
					
					if(oldItemItr.hasNext()){
						oldItem = oldItemItr.next();				
					} else {
						oldItem = null;
					}
				}
			}
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The order to be updated will be identified using its primary key id. The
	 * order that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param order
	 *            order with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateOrder(Order order) {
		if(order==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE ORDERS SET DATE = ?, SUPPLIERID = ?, DELIVERYID = ? WHERE ID = ?");
			pStatement.setLong(1,order.getOrderDate().getTime());
			pStatement.setInt(2, order.getSupplier().getID());
			pStatement.setInt(3, order.getDeliveryID());
			pStatement.setInt(4, order.getID());
			pStatement.executeUpdate();	
			
			Iterator<LineItem> oldItemItr = getOrderItems(order.getID()).iterator();
			LineItem oldItem = null;
			if(oldItemItr.hasNext()){
				oldItem = oldItemItr.next();				
			}
			for(LineItem newItem : order.getLineItems()){
				boolean updated = false;
				while(!updated){
					if(oldItem == null){
						addOrderItem(newItem, order.getID());
						updated = true;
					}else if(oldItem.getID() == newItem.getID()){
						updateOrderItem(newItem);
						updated = true;
					}else{
						deleteOrderItem(oldItem);
					}
					
					if(oldItemItr.hasNext()){
						oldItem = oldItemItr.next();				
					} else {
						oldItem = null;
					}
				}
			}
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The user to be updated will be identified using its primary key id. The
	 * user that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param user
	 *            user with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateUser(User user) {
		if(user==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE USERS SET USERNAME = ?, AUTHLEVEL = ?, PASSWDIGEST = ?, SALT = ? WHERE ID = ?");
			pStatement.setString(1,user.getUsername());
			pStatement.setInt(2, user.getAuthorizationLevel());
			pStatement.setString(3, user.getPasswordDigest());
			pStatement.setString(4, user.getSalt());
			pStatement.setInt(5, user.getID());
			pStatement.executeUpdate();	
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The order item to be updated will be identified using its primary key id.
	 * The lineItem that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param lineItem
	 *            lineItem with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateOrderItem(LineItem lineItem){
		if(lineItem==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE ORDERITEMS SET PRODUCTID = ?, QUANTITY = ?, ORDERID = ? WHERE ID = ?");
			pStatement.setInt(1,lineItem.getProductID());
			pStatement.setInt(2, lineItem.getQuantity());
			pStatement.setInt(3, lineItem.getOrderID());
			pStatement.setInt(4, lineItem.getID());
			pStatement.executeUpdate();	
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	/**
	 * Update an existing object with the values inside the supplied argument.
	 * <p>
	 * The invoice item to be updated will be identified using its primary key
	 * id. The lineItem that is passed in as an argument must have a valid,
	 * database-assigned ID for this method to work correctly.
	 * 
	 * @param lineItem
	 *            lineItem with a database-assigned ID and updated instance
	 *            variables
	 * @return true if the database changed as a result of the call
	 */
	public boolean updateInvoiceItem(LineItem lineItem){
		if(lineItem==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE INVOICEITEMS SET PRODUCTID = ?, QUANTITY = ?, INVOICEID = ? WHERE ID = ?");
			pStatement.setInt(1,lineItem.getProductID());
			pStatement.setInt(2, lineItem.getQuantity());
			pStatement.setInt(3, lineItem.getOrderID());
			pStatement.setInt(4, lineItem.getID());
			pStatement.executeUpdate();	
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;			
	}

	/**
	 * Get all orders that fall between the two dates (inclusive).
	 * 
	 * @param start
	 *            the start date in the range
	 * @param end
	 *            the end date in the range
	 * @return all orders that fall within the specified range as an ArrayList
	 */
	public ArrayList<Order> getOrdersBetween(Date start, Date end) {
		ArrayList<Order> output = new ArrayList<Order>();
		try {
			pStatement = connection
					.prepareStatement("SELECT * FROM ORDERS WHERE DATE >= (?) AND DATE <= (?)");
			pStatement.setLong(1, start.getTime());
			pStatement.setLong(2, end.getTime());
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				long dateLong = rs.getLong("date");
				Date date = new Date(dateLong);
				int supplierID = rs.getInt("supplierid");
				int deliveryID = rs.getInt("deliveryid");
				Supplier supplier = getSupplier(supplierID);
				ArrayList<LineItem> orderItems = getOrderItems(id);
				output.add(new Order(-1, supplier, deliveryID, orderItems, id,
						date));
			}
			rs.close();
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Get all deliveries that fall between the two dates (inclusive).
	 * 
	 * @param start
	 *            the start date in the range
	 * @param end
	 *            the end date in the range
	 * @return all deliveries that fall within the specified range as an
	 *         ArrayList
	 */
	public ArrayList<Delivery> getDeliveriesBetween(Date start, Date end){
		ArrayList<Delivery> output = new ArrayList<Delivery>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM DELIVERIES WHERE DELIVERYDATE >= (?) AND DELIVERYDATE <= (?)");
			pStatement.setLong(1, start.getTime());
			pStatement.setLong(2, end.getTime());
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				long deliveryDate = rs.getLong("deliverydate");
				Date date = new Date(deliveryDate);
				int orderID = rs.getInt("orderid");
				int supplierID = rs.getInt("supplierid");
				Supplier supplier = getSupplier(supplierID);
				output.add(new Delivery(supplier, orderID, date, id));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

	/**
	 * Get all invoices that fall between the two dates (inclusive).
	 * 
	 * @param start
	 *            the start date in the range
	 * @param end
	 *            the end date in the range
	 * @return all invoices that fall within the specified range as an ArrayList
	 */
	public ArrayList<Invoice> getInvoicesBetween(Date start, Date end){
		ArrayList<Invoice> output = new ArrayList<Invoice>();
		try {
			pStatement = connection.prepareStatement("SELECT * FROM INVOICES WHERE DATE >= (?) AND DATE <= (?)");
			pStatement.setLong(1, start.getTime());
			pStatement.setLong(2, end.getTime());
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				long dateLong = rs.getLong("date");
				Date date = new Date(dateLong);
				int customerID = rs.getInt("customerid");
				Customer customer = getCustomer(customerID);
				ArrayList<LineItem> invoiceItems = getInvoiceItems(id);
				output.add(new Invoice(invoiceItems, customer, id, date));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;	
	}
	
	private void createTables(){		
		try {
			pStatement = connection.prepareStatement(CREATE_CUSTOMERS_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_DELIVERIES_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_INVOICE_ITEMS_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_INVOICES_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_ORDER_ITEMS_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_ORDERS_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_PRODUCTS_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_SUPPLIERS_TABLE);
			pStatement.executeUpdate();
			pStatement = connection.prepareStatement(CREATE_USERS_TABLE);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			if(e.getMessage().contains("table CUSTOMERS already exists")){
				return;
			}
			System.err.println("Database access error: " + e.getMessage());
		}		
	}

	/**
	 * Return the associated User on username+password match, otherwise return
	 * an unauthorized user.
	 * 
	 * @param username
	 *            The username of the User.
	 * @param password
	 *            A String password.
	 * @return User that matches the username and password, otherwise an
	 *         unauthorized User object.
	 */
	public User authorizeUser(String username, String password) {
		for (User user : getUsers()) {
			if (user.getUsername().equals(username)) {
				try {
					EncryptionModule em = new EncryptionModule();
					String passwordDigest = em
							.encrypt(password, user.getSalt());
					if (user.getPasswordDigest().equals(passwordDigest)) {
						return user;
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					break;
				}
			}
		}
		return new User(User.NO_AUTHORIZATION, "Not authorised", null, null);
	}
}