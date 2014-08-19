package team4.retailsystem.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;

public class PermanentDatabase {

	private static PermanentDatabase db = null;
	private Connection connection = null;
	private Statement statement = null;
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
	
	private PermanentDatabase() {
		//createTables();
		//generateDatabase(); //it doesn't have any instance variables, just a handle. everything is started and closed before and after every transaction		
	}

	private void openConnection() {
		if(open)
			return;
		open = true;
		if(connection != null && statement != null){
			return;
		}
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("The database class could not be found: "
					+ e.getClass().getName());
			return;
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:testSystem.db");
			//connection.setAutoCommit(false);
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
			return;
		}
	}

	private void closeConnection() {
		if(true)
			return;
		if(connection == null){
			return;
		}
		try {
			statement.close();
			statement = null;
			connection.commit();
			connection.close();
			connection = null;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
			return;
		}
	}

	/**
	 * Helper method to format String objects for database storage.
	 * <p>
	 * Adds apostrophes before and after if the object is of type String,
	 * otherwise returns the string representation of the object.
	 * 
	 * @param o
	 *            Object, preferably a String or a char to surround with
	 *            apostrophes.
	 * @return 'o' iff o is an instance of String; otherwise o
	 */
	private String appendApostrophes(Object o) {
		if (o instanceof String || o instanceof Character) {
			return "'" + o + "'";
		}
		return "" + o;
	}

	// Methods to add rows to table
	public boolean addCustomer(Customer c) {
		if(c==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + CUSTOMERS_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(c.getName()) + ", ";
		sql += appendApostrophes(c.getTelephoneNumber()) + ", ";
		sql += appendApostrophes(c.getEmail()) + ", ";
		sql += appendApostrophes(c.getAddress()) + ");";
		
		boolean isAdded = false;
		try {
			statement.executeUpdate(sql);
			isAdded = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isAdded;
	}

	public boolean addSupplier(Supplier s) {
		if(s==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + SUPPLIERS_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(s.getName()) + ", ";
		sql += appendApostrophes(s.getTelephoneNumber()) + ", ";
		sql += appendApostrophes(s.getEmail()) + ", ";
		sql += appendApostrophes(s.getAddress()) + ");";
		
		boolean isAdded = false;
		try {
			statement.executeUpdate(sql);
			isAdded = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isAdded;		
	}

	public boolean addProduct(Product p) {
		if(p==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + PRODUCTS_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(p.getName()) + ", ";
		sql += appendApostrophes(p.getCost()) + ", ";
		sql += appendApostrophes(p.getMarkup()) + ", ";
		sql += appendApostrophes(p.getStockLevel()) + ", ";
		sql += appendApostrophes(p.getSupplier().getID()) + ");";
		
		boolean isAdded = false;
		try {
			statement.executeUpdate(sql);
			isAdded = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isAdded;		
	}

	public boolean addDelivery(Delivery d) {
		if(d==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + DELIVERIES_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(d.getDate().getTime()) + ", ";
		sql += appendApostrophes(d.getOrderID()) + ", ";
		sql += appendApostrophes(d.getSupplier().getID()) + ");";
		
		boolean isAdded = false;
		try {
			statement.executeUpdate(sql);
			isAdded = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isAdded;		
	}

	public boolean addInvoice(Invoice i) {
		if(i==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + INVOICES_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(i.getDate().getTime()) + ", ";
		sql += appendApostrophes(i.getCustomer().getID()) + ", ";
		sql += appendApostrophes(i.getCost()) + ");";
		
		boolean isAdded = true;
		int id = 0;
		try {
			statement.executeUpdate(sql);
			
			ResultSet generatedKeys = statement.getGeneratedKeys();
			while(generatedKeys.next()){			
				id = generatedKeys.getInt(1);
			}
			generatedKeys.close();
			
			for(LineItem li : i.getLineItems()){
				addInvoiceItem(li, id);
			}
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
			isAdded = false;
		}
		
		closeConnection();
		return isAdded;	
	}

	public boolean addOrder(Order o) {
		if(o==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + ORDERS_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(o.getDeliveryDate().getTime()) + ", ";
		sql += appendApostrophes(o.getSupplier().getID()) + ", ";
		sql += appendApostrophes(o.getDeliveryID()) + ", ";
		sql += appendApostrophes(o.getCost()) + ");";
		
		boolean isAdded = true;
		int id = 0;
		try {
			statement.executeUpdate(sql);
			
			ResultSet generatedKeys = statement.getGeneratedKeys();
			while(generatedKeys.next()){			
				id = generatedKeys.getInt(1);
			}
			generatedKeys.close();
			
			for(LineItem li : o.getLineItems()){
				addOrderItem(li, id);
			}
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
			isAdded = false;
		}
		
		closeConnection();
		return isAdded;	
	}

	public boolean addUser(User u) {
		if(u==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + USERS_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(u.getUsername()) + ", ";
		sql += appendApostrophes(u.getAuthorizationLevel()) + ", ";
		sql += appendApostrophes(u.getPasswordDigest()) + ", ";
		sql += appendApostrophes(u.getSalt()) + ");";
		
		boolean isAdded = false;
		try {
			statement.executeUpdate(sql);
			isAdded = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isAdded;			
	}
	
	public boolean addOrderItem(LineItem li, int orderID) {	
		if(li==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + ORDER_ITEMS_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(li.getProductID()) + ", ";
		sql += appendApostrophes(li.getQuantity()) + ", ";
		sql += appendApostrophes(orderID) + ");";
		
		boolean isAdded = false;
		try {
			statement.executeUpdate(sql);
			isAdded = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isAdded;				
	}
	
	public boolean addInvoiceItem(LineItem li, int inoviceID) {	
		if(li==null){
			return false;
		}
		openConnection();
		
		String sql = "INSERT INTO " + INVOICE_ITEMS_DEFINITION + " VALUES (NULL,";
		sql += appendApostrophes(li.getProductID()) + ", ";
		sql += appendApostrophes(li.getQuantity()) + ", ";
		sql += appendApostrophes(inoviceID) + ");";
		
		boolean isAdded = false;
		try {
			statement.executeUpdate(sql);
			isAdded = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isAdded;				
	}

	// Remove row from table
	public boolean deleteCustomer(Customer c) {
		if(c==null){
			return false;
		}
		openConnection();
		
		String sql = "DELETE FROM CUSTOMERS WHERE ID=" + c.getID() + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;		
	}

	public boolean deleteSupplier(Supplier s) {
		if(s==null){
			return false;
		}
		openConnection();
		
		String sql = "DELETE FROM SUPPLIERS WHERE ID=" + s.getID() + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;	
	}

	public boolean deleteProduct(Product p) {
		if(p==null){
			return false;
		}
		openConnection();
		
		String sql = "DELETE FROM PRODUCTS WHERE ID=" + p.getID() + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;	
	}

	public boolean deleteDelivery(Delivery d) {
		if(d==null){
			return false;
		}
		openConnection();
		
		String sql = "DELETE FROM DELIVERIES WHERE ID=" + d.getDeliveryID() + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;	
	}

	public boolean deleteInvoice(Invoice i) {
		if(i==null){
			return false;
		}
		openConnection();
		
		for(LineItem li : i.getLineItems()){
			deleteInvoiceItem(li, i.getID());
		}
		
		String sql = "DELETE FROM INVOICES WHERE ID=" + i.getID() + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		for(LineItem li : i.getLineItems()){
			deleteInvoiceItem(li, i.getID());
		}
		
		closeConnection();
		return isDeleted;	
	}

	public boolean deleteOrder(Order o) {
		if(o==null){
			return false;
		}
		openConnection();
		
		for(LineItem li : o.getLineItems()){
			deleteOrderItem(li, o.getID());
		}
		String sql = "DELETE FROM ORDERS WHERE ID=" + o.getID() + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;	
	}

	public boolean deleteUser(User u) {
		if(u==null){
			return false;
		}
		openConnection();
		
		String sql = "DELETE FROM USERS WHERE ID=" + u.getID() + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;	
	}
	
	public boolean deleteOrderItem(LineItem li, int orderID){	
		if(li==null){
			return false;
		}
		openConnection();
		
		String sql = "DELETE FROM ORDERITEMS WHERE ORDERID=" + orderID + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;	
	}
	
	public boolean deleteInvoiceItem(LineItem li, int invoiceID){		
		if(li==null){
			return false;
		}
		openConnection();
		
		String sql = "DELETE FROM INVOICEITEMS WHERE INVOICEID=" + invoiceID + ";";
		
		boolean isDeleted = false;
		try {
			statement.executeUpdate(sql);
			isDeleted = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isDeleted;	
	}
	
	// Select * from table
	public ArrayList<Customer> getCustomers() {
		String sql = "SELECT * FROM CUSTOMERS;";
		openConnection();
		
		ArrayList<Customer> output = new ArrayList<Customer>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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
		
		closeConnection();
		return output;
	}

	public ArrayList<Supplier> getSuppliers() {
		String sql = "SELECT * FROM SUPPLIERS;";
		openConnection();
		
		ArrayList<Supplier> output = new ArrayList<Supplier>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
			while(rs.next()){
				String name = rs.getString("name");
				String telephoneNo = rs.getString("phoneno");
				String email = rs.getString("email");
				String address = rs.getString("address");
				int id = rs.getInt("id");
				output.add(new Supplier(name, telephoneNo, email, address, id));
			}
			rs.close();	
		} catch (SQLException e) {
			System.err.println("Database query error: " + e.getMessage());
		}
		
		closeConnection();
		return output;
	}

	public ArrayList<Product> getProducts() {
		String sql = "SELECT * FROM PRODUCTS;";
		openConnection();
		
		ArrayList<Product> output = new ArrayList<Product>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	public ArrayList<Delivery> getDeliveries() {
		String sql = "SELECT * FROM DELIVERIES;";
		openConnection();
		
		ArrayList<Delivery> output = new ArrayList<Delivery>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	public ArrayList<Invoice> getInvoices() {
		String sql = "SELECT * FROM INVOICES;";
		openConnection();
		
		ArrayList<Invoice> output = new ArrayList<Invoice>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	public ArrayList<Order> getOrders() {
		String sql = "SELECT * FROM INVOICES;";
		openConnection();
		
		ArrayList<Order> output = new ArrayList<Order>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	public ArrayList<User> getUsers() {
		String sql = "SELECT * FROM USERS;";
		openConnection();
		
		ArrayList<User> output = new ArrayList<User>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}	
	
	/**
	 * Retrieve all order items associated with the given orderID.
	 * @param orderID Unique ID number of the order.
	 * @return ArrayList of LineItems associated with the given orderID.
	 */
	public ArrayList<LineItem> getOrderItems(int orderID){
		String sql = "SELECT * FROM ORDERITEMS WHERE ORDERID=" + orderID + ";";
		openConnection();
		
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}
	
	/**
	 * Retrieve all order items in the system.
	 * @return ArrayList of LineItems.
	 */
	public ArrayList<LineItem> getOrderItems(){
		String sql = "SELECT * FROM ORDERITEMS;";
		openConnection();
		
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	/**
	 * Retrieve all invoice items associated with the given invoiceID.
	 * @param invoiceID Unique ID number of the invoice.
	 * @return ArrayList of LineItems associated with the given invoiceID.
	 */
	public ArrayList<LineItem> getInvoiceItems(int invoiceID){	
		String sql = "SELECT * FROM INVOICEITEMS WHERE INVOICEID=" + invoiceID + ";";
		openConnection();
		
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}
	
	/**
	 * Retrieve all invoice items in the system.
	 * @return ArrayList of LineItems.
	 */
	public ArrayList<LineItem> getInvoiceItems(){	
		String sql = "SELECT * FROM INVOICEITEMS;";
		openConnection();
		
		ArrayList<LineItem> output = new ArrayList<LineItem>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	// single item getters
	public Customer getCustomer(int id) {
		String sql = "SELECT * FROM CUSTOMERS WHERE ID=" + id + ";";
		openConnection();
		
		Customer output = null;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;		
	}

	public Supplier getSupplier(int id) {
		String sql = "SELECT * FROM SUPPLIERS WHERE ID = " + id + ";";
		openConnection();
		
		Supplier output = null;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	public Product getProduct(int id) {
		String sql = "SELECT * FROM PRODUCTS WHERE ID=" + id + ";";
		openConnection();
		
		Product output = null;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	public Delivery getDelivery(int id) {
		String sql = "SELECT * FROM DELIVERIES WHERE ID=" + id + ";";
		openConnection();
		
		Delivery output = null;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();		
		return output;
	}

	public Invoice getInvoice(int id) {
		String sql = "SELECT * FROM INVOICES WHERE ID=" + id + ";";
		openConnection();
		
		Invoice output = null;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();	
		return output;		
	}

	public Order getOrder(int id) {
		String sql = "SELECT * FROM ORDERS WHERE ID = " + id + ";";
		openConnection();
		// PermanentDatabase.deleteCustomer(as.get(5));
		Order output = null;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();	
		return output;
	}

	public User getUser(int id) {
		String sql = "SELECT * FROM USERS WHERE ID=" + id + ";";
		openConnection();
		
		User output = null;
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
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

		closeConnection();
		return output;
	}

	// Methods to update objects in database
	public boolean updateCustomer(Customer cust) {
		if(cust==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE CUSTOMERS";
		sql += " SET NAME = " + appendApostrophes(cust.getName()) + ",";
		sql += " PHONENO = " + appendApostrophes(cust.getTelephoneNumber()) + ",";
		sql += " EMAIL = " + appendApostrophes(cust.getEmail()) + ",";
		sql += " ADDRESS = " + appendApostrophes(cust.getAddress());
		sql += " WHERE ID = " + cust.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;			
	}

	public boolean updateSupplier(Supplier supp) {
		if(supp==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE SUPPLIERS";
		sql += " SET NAME = " + appendApostrophes(supp.getName()) + ",";
		sql += " PHONENO = " + appendApostrophes(supp.getTelephoneNumber()) + ",";
		sql += " EMAIL = " + appendApostrophes(supp.getEmail()) + ",";
		sql += " ADDRESS = " + appendApostrophes(supp.getAddress());
		sql += " WHERE ID = " + supp.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;			
	}

	public boolean updateProduct(Product prod) {
		if(prod==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE PRODUCTS";
		sql += " SET NAME = " + appendApostrophes(prod.getName()) + ",";
		sql += " COST = " + appendApostrophes(prod.getCost()) + ",";
		sql += " MARKUP = " + appendApostrophes(prod.getMarkup()) + ",";
		sql += " STOCKLEVEL = " + appendApostrophes(prod.getStockLevel()) + ",";
		sql += " SUPPLIERID = " + appendApostrophes(prod.getSupplier().getID());
		sql += " WHERE ID = " + prod.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;	
	}

	public boolean updateDelivery(Delivery del) {
		if(del==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE DELIVERIES";
		sql += " SET DELIVERYDATE = " + appendApostrophes(del.getDate().getTime()) + ",";
		sql += " ORDERID = " + appendApostrophes(del.getOrderID()) + ",";
		sql += " SUPPLIERID = " + appendApostrophes(del.getSupplier().getID());
		sql += " WHERE ID = " + del.getDeliveryID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;	
	}

	public boolean updateInvoice(Invoice inv) {
		if(inv==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE INVOICES";
		sql += " SET DATE = " + appendApostrophes(inv.getDate().getTime()) + ",";
		sql += " CUSTOMERID = " + appendApostrophes(inv.getCustomer().getID()) + ",";
		sql += " VALUE = " + appendApostrophes(inv.getCost());
		sql += " WHERE ID = " + inv.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		for(LineItem li : inv.getLineItems()){
			updateInvoiceItem(li);
		}
		
		closeConnection();
		return isUpdated;	
	}

	public boolean updateOrder(Order order) {
		if(order==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE ORDERS";
		sql += " SET DATE = " + appendApostrophes(order.getDeliveryDate().getTime()) + ",";
		sql += " SUPPLIERID = " + appendApostrophes(order.getSupplier().getID()) + ",";
		sql += " DELIVERYID = " + appendApostrophes(order.getDeliveryID()) + ",";
		sql += " VALUE = " + appendApostrophes(order.getCost());
		sql += " WHERE ID = " + order.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;	
	}

	public boolean updateUser(User user) {
		if(user==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE USERS";
		sql += " SET USERNAME = " + appendApostrophes(user.getUsername()) + ",";
		sql += " AUTHLEVEL = " + appendApostrophes(user.getAuthorizationLevel()) + ",";
		sql += " PASSWDIGEST = " + appendApostrophes(user.getPasswordDigest()) + ",";
		sql += " SALT = " + appendApostrophes(user.getSalt());
		sql += " WHERE ID = " + user.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;	
	}
	
	public boolean updateOrderItem(LineItem li){
		if(li==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE ORDERITEMS";
		sql += " SET PRODUCTID = " + appendApostrophes(li.getProductID()) + ",";
		sql += " QUANTITY = " + appendApostrophes(li.getQuantity()) + ",";
		sql += " ORDERID = " + appendApostrophes(li.getOrderID());
		sql += " WHERE ID = " + li.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;	
	}
	
	public boolean updateInvoiceItem(LineItem li){
		if(li==null){
			return false;
		}
		openConnection();
		
		String sql = "UPDATE INVOICEITEMS";
		sql += " SET PRODUCTID = " + appendApostrophes(li.getProductID()) + ",";
		sql += " QUANTITY = " + appendApostrophes(li.getQuantity()) + ",";
		sql += " INVOICEID = " + appendApostrophes(li.getOrderID());
		sql += " WHERE ID = " + li.getID() + ";";
		
		boolean isUpdated = false;
		try {
			statement.executeUpdate(sql);
			isUpdated = true;
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
		return isUpdated;			
	}

	// Auto generate database
	void generateDatabase() {
		try {
			createTables();		//works
			//generateUsers();		//works
			//generateCustomers();	//works
			//generateSuppliers();	//works
			//generateProducts();	//works
			//generateOrders();		//works
			//generateDeliveries();	//works.. i think	
			//generateInvoices();	//works
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createTables(){
		openConnection();
		
		try {
			statement.executeUpdate(CREATE_CUSTOMERS_TABLE);
			statement.executeUpdate(CREATE_DELIVERIES_TABLE);
			statement.executeUpdate(CREATE_INVOICE_ITEMS_TABLE);
			statement.executeUpdate(CREATE_INVOICES_TABLE);
			statement.executeUpdate(CREATE_ORDER_ITEMS_TABLE);
			statement.executeUpdate(CREATE_ORDERS_TABLE);
			statement.executeUpdate(CREATE_PRODUCTS_TABLE);
			statement.executeUpdate(CREATE_SUPPLIERS_TABLE);
			statement.executeUpdate(CREATE_USERS_TABLE);
		} catch (SQLException e) {
			System.err.println("Database access error: " + e.getMessage());
		}
		
		closeConnection();
	}

	private void generateUsers() {
		EncryptionModule em;
		try {
			em = new EncryptionModule();
			addUser(new User(1, "Eoin", em.encrypt("Nioe", "testSalt"),
					"testSalt"));
			addUser(new User(0, "Szymon", em.encrypt("Nomyzs", "testSalt"),
					"testSalt"));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private void generateCustomers() {
		addCustomer(new Customer("Alan", "0857223104", "Alan@DIT.ie", "Dublin"));
		addCustomer(new Customer("John", "0854213514", "John@DIT.ie", "Cork"));
		addCustomer(new Customer("Mary", "0859434115", "Mary@DIT.ie", "Wicklow"));
	}

	private void generateSuppliers() {
		addSupplier(new Supplier("Apple", "Apple@suppliers.com", "California",
				"184234242"));
		addSupplier(new Supplier("Samsung", "Samsung@suppliers.com", "Tokyo",
				"76328843"));
	}

	private void generateProducts() {
		addProduct(new Product("iPhone 5s", 500.00, 10, 0, getSupplier(1)));
		addProduct(new Product("iPhone 4", 250.00, 5, 0,
				getSupplier(1)));
		addProduct(new Product("Galaxy S5", 480.00, 10, 0,
				getSupplier(1)));
	}

	// HAVE A CLOSER LOOK AT THIS ONE
	private void generateOrders() {
		ArrayList<LineItem> order1 = new ArrayList<>();
		order1.add(new LineItem(1, 10));
		order1.add(new LineItem(2, 10));
		ArrayList<LineItem> order2 = new ArrayList<>();
		order2.add(new LineItem(3, 20));

		addOrder(new Order(7500.0, getSupplier(1), 1, order1));
		addOrder(new Order(9600.0, getSupplier(2), 2, order2));
	}

	private void generateDeliveries() {
		addDelivery(new Delivery(getSupplier(1), getOrder(1).getID()));
		addDelivery(new Delivery(getSupplier(2), getOrder(2).getID()));
	}

	private void generateInvoices() {
		ArrayList<LineItem> items = new ArrayList<>();
		items.add(new LineItem(1, 30));
		items.add(new LineItem(2, 10));
		addInvoice(new Invoice(items, getCustomer(1)));
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

	/**
	 * Inner class that uses MD5 to generate password digests.
	 * 
	 * @author Szymon
	 */
	private class EncryptionModule {
		private MessageDigest md;

		private EncryptionModule() throws NoSuchAlgorithmException {
			md = MessageDigest.getInstance("MD5");
		}

		/**
		 * Returns a random 8-byte salt as a String.
		 * 
		 * @return Random 8-byte string.
		 */
		private String getRandomSalt() {
			SecureRandom random = new SecureRandom();
			byte bytes[] = new byte[8];
			random.nextBytes(bytes);
			return new String(bytes);
		}

		/**
		 * Returns the password digest, created using the given salt.
		 * 
		 * @param password
		 *            The password to encrypt.
		 * @param salt
		 *            The salt used to protect against dictionary attacks.
		 * @return Password digest as a String.
		 */
		private String encrypt(String password, String salt) {
			md.reset();
			md.update(salt.getBytes());
			return new String(md.digest(password.getBytes()));
		}
	}

	// Return instance of database
	public static PermanentDatabase getInstance() {
		if (db == null) {
			db = new PermanentDatabase();
		}
		return db;
	}
	
	public static void main(String [] args){
		PermanentDatabase db = PermanentDatabase.getInstance(); 
		db.generateDatabase();
	}
}
