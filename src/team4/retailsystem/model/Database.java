package team4.retailsystem.model;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

import team4.retailsystem.utils.EncryptionModule;

/**
 * A permanent SQLite database module with interfaces for CRUD.
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
			+ "CUSTOMERID INTEGER,"
			+ "VALUE REAL)";	
	private static final String INVOICES_DEFINITION = "INVOICES (ID,DATE,CUSTOMERID,VALUE)";
	
	private static final String CREATE_ORDERS_TABLE = "CREATE TABLE ORDERS "
			+ "(ID INTEGER PRIMARY KEY NOT NULL, "
			+ "DATE INTEGER, "
			+ "SUPPLIERID INTEGER,"
			+ "DELIVERYID INTEGER,"
			+ "VALUE REAL)";
	private static final String ORDERS_DEFINITION = "ORDERS (ID,DATE,SUPPLIERID,DELIVERYID,VALUE)";
	
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
			+ "USERNAME TEXT, " 
			+ "AUTHLEVEL INTEGER, " 
			+ "PASSWDIGEST TEXT, " 
			+ "SALT TEXT)";
	private static final String USERS_DEFINITION = "USERS (ID,USERNAME,AUTHLEVEL,PASSWDIGEST,SALT)";
	
	private Database(String dbName) {
		openConnection(dbName);
		createTables();	
	}
	
	public static Database getInstance() {
		if (db == null) {
			db = new Database("testSystem");
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

	public void closeConnection() {
		try {
			pStatement.close();
			connection.close();
			open = false;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
	}

	// Methods to add rows to table
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

	public boolean addInvoice(Invoice invoice) {
		if(invoice==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + INVOICES_DEFINITION + " VALUES (NULL,?,?,?)");
			pStatement.setLong(1, invoice.getDate().getTime());
			pStatement.setInt(2, invoice.getCustomer().getID());
			pStatement.setDouble(3, invoice.getCost());
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

	public boolean addOrder(Order order) {
		if(order==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + ORDERS_DEFINITION + " VALUES (NULL,?,?,?,?)");
			pStatement.setLong(1, order.getDeliveryDate().getTime());
			pStatement.setInt(2, order.getSupplier().getID());
			pStatement.setInt(3, order.getDeliveryID());
			pStatement.setDouble(4, order.getCost());
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
	
	public boolean addOrderItem(LineItem li, int orderID) {	
		if(li==null){
			return false;
		}		
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + ORDER_ITEMS_DEFINITION + " VALUES (NULL,?,?,?)");
			pStatement.setInt(1, li.getProductID());
			pStatement.setInt(2, li.getQuantity());
			pStatement.setInt(3, orderID);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;				
	}
	
	public boolean addInvoiceItem(LineItem li, int inoviceID) {	
		if(li==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("INSERT INTO " + INVOICE_ITEMS_DEFINITION + " VALUES (NULL,?,?,?)");
			pStatement.setInt(1, li.getProductID());
			pStatement.setInt(2, li.getQuantity());
			pStatement.setInt(3, inoviceID);
			pStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}		
		return false;				
	}

	// Remove row from table
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

	public boolean deleteInvoice(Invoice invoice) {
		if(invoice==null){
			return false;
		}		
		for(LineItem li : invoice.getLineItems()){
			deleteInvoiceItem(li, invoice.getID());
		}				
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

	public boolean deleteOrder(Order order) {
		if(order==null){
			return false;
		}
		for(LineItem li : order.getLineItems()){
			deleteOrderItem(li, order.getID());
		}
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
	
	public boolean deleteOrderItem(LineItem lineItem, int orderID){	
		if(lineItem==null){
			return false;
		}
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
	
	public boolean deleteInvoiceItem(LineItem li, int invoiceID){		
		if(li==null){
			return false;
		}
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
	
	// Select * from table
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
				double value = rs.getDouble("value");
				Customer customer = getCustomer(customerID);
				ArrayList<LineItem> invoiceItems = getInvoiceItems(id);
				output.add(new Invoice(invoiceItems, customer, id, date, value));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

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
				double value = rs.getDouble("value");
				Supplier supplier = getSupplier(supplierID);
				ArrayList<LineItem> orderItems = getOrderItems(id);
				output.add(new Order(value, supplier, deliveryID, orderItems, id, date));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

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
	 * Retrieve all order items associated with the given orderID.
	 * @param orderID Unique ID number of the order.
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
	 * Retrieve all order items in the system.
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
	 * Retrieve all invoice items associated with the given invoiceID.
	 * @param invoiceID Unique ID number of the invoice.
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
	 * Retrieve all invoice items in the system.
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

	// single item getters
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

	public Invoice getInvoice(int id) {
		Invoice output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM INVOICES WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				long dateLong = rs.getLong("date");
				Date date = new Date(dateLong);
				int customerID = rs.getInt("customerid");
				double value = rs.getDouble("value");
				Customer customer = getCustomer(customerID);
				ArrayList<LineItem> invoiceItems = getInvoiceItems(id);
				output = new Invoice(invoiceItems, customer, id, date, value);
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;		
	}

	public Order getOrder(int id) {
		Order output = null;
		try {
			pStatement = connection.prepareStatement("SELECT * FROM ORDERS WHERE ID=(?)");
			pStatement.setInt(1, id);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()){
				long dateLong = rs.getLong("date");
				Date date = new Date(dateLong);
				int supplierID = rs.getInt("supplierid");
				int deliveryID = rs.getInt("deliveryid");
				double value = rs.getDouble("value");
				Supplier supplier = getSupplier(supplierID);
				ArrayList<LineItem> orderItems = getOrderItems(id);
				output = new Order(value, supplier, deliveryID, orderItems, id, date);
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		return output;
	}

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

	// Methods to update objects in database
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

	public boolean updateInvoice(Invoice invoice) {
		if(invoice==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE INVOICES SET DATE = ?, CUSTOMERID = ?, VALUE = ? WHERE ID = ?");
			pStatement.setLong(1, invoice.getDate().getTime());
			pStatement.setInt(2, invoice.getCustomer().getID());
			pStatement.setDouble(3, invoice.getCost());
			pStatement.setInt(4, invoice.getID());
			pStatement.executeUpdate();	
			for(LineItem li : invoice.getLineItems()){
				updateInvoiceItem(li);
			}
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

	public boolean updateOrder(Order order) {
		if(order==null){
			return false;
		}
		try {
			pStatement = connection.prepareStatement("UPDATE ORDERS SET DATE = ?, SUPPLIERID = ?, DELIVERYID = ?, VALUE = ? WHERE ID = ?");
			pStatement.setLong(1,order.getDeliveryDate().getTime());
			pStatement.setInt(2, order.getSupplier().getID());
			pStatement.setInt(3, order.getDeliveryID());
			pStatement.setDouble(4, order.getCost());
			pStatement.setInt(5, order.getID());
			pStatement.executeUpdate();	
			for(LineItem li : order.getLineItems()){
				updateOrderItem(li);
			}
			return true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		return false;	
	}

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
	 * Return existing User on username+password match, otherwise return an
	 * unauthorized user.
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