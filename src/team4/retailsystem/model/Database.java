package team4.retailsystem.model;

/**
 * 
 * @author D14123080 Alan Kavanagh
 *
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

	private static Database db = null;

	private HashMap<String,String> passwordMap = new HashMap<String,String>();
	
	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<Supplier> suppliers = new ArrayList<>();
	private ArrayList<Product> products = new ArrayList<>();
	private ArrayList<Delivery> deliveries = new ArrayList<>();
	private ArrayList<Invoice> invoices = new ArrayList<>();
	private ArrayList<Order> orders = new ArrayList<>();
	private ArrayList<User> users = new ArrayList<>();

	private Database() {
		generateDatabase();
	}

	// Methods to add rows to table
	public boolean addCustomer(Customer c) {
		try {
			customers.add(c);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addSupplier(Supplier s) {
		try {
			suppliers.add(s);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addProduct(Product p) {
		try {
			products.add(p);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addDelivery(Delivery d) {
		try {
			deliveries.add(d);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addInvoice(Invoice i) {
		try {
			invoices.add(i);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addOrder(Order o) {
		try {
			orders.add(o);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addUser(User u) {
		try {
			users.add(u);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Remove row from table
	public boolean deleteCustomer(Customer c) {
		try {
			customers.remove(c);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteSupplier(Supplier s) {
		try {
			suppliers.remove(s);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteProduct(Product p) {
		try {
			products.remove(p);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteDelivery(Delivery d) {
		try {
			deliveries.remove(d);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteInvoice(Invoice i) {
		try {
			invoices.remove(i);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteOrder(Order o) {
		try {
			orders.remove(o);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteUser(User u) {
		try {
			users.remove(u);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Select * from table
	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public ArrayList<Supplier> getSuppliers() {
		return suppliers;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}

	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	// Unfinished methods to update objects in database
	public boolean updateCustomer(Customer cust) {
		try {
			for (Customer c : customers) {
				if (c.getID() == cust.getID()) {
					c = cust;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateSupplier(Supplier supp) {
		try {
			for (Supplier s : suppliers) {
				if (s.getID() == supp.getID()) {
					s = supp;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateProduct(Product prod) {
		try {
			for (Product p : products) {
				if (p.getID() == prod.getID()) {
					p = prod;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateDelivery(Delivery del) {
		try {
			for (Delivery d : deliveries) {
				if (d.getDeliveryID() == del.getDeliveryID()) {
					d = del;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateInvoice(Invoice inv) {
		try {
			for (Invoice i : invoices) {
				if (i.getID() == inv.getID()) {
					i = inv;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateOrder(Order order) {
		try {
			for (Order o : orders) {
				if (o.getID() == order.getID()) {
					o = order;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateUser(User user) {
		try {
			for (User u : users) {
				if (u.getID() == user.getID()) {
					u = user;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Auto generate database
	void generateDatabase() {
		try {
			generateUsers();
			generateCustomers();
			generateSuppliers();
			generateProducts();
			generateOrders();
			generateDeliveries();
			generateInvoices();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateUsers() {
		addUser(new User(User.ADMINISTRATOR, "eoin"));
		addUser(new User(User.NORMAL_USER, "szymon"));
		addUser(new User(User.ADMINISTRATOR, "alan"));
		addUser(new User(User.NORMAL_USER, "siobhain"));
		addUser(new User(User.ADMINISTRATOR, "ha"));
		addUser(new User(User.NORMAL_USER, "giovanni"));
		
		passwordMap.put("eoin", "1234");
		passwordMap.put("szymon", "1234");
		passwordMap.put("alan", "1234");
		passwordMap.put("siobhain", "1234");
		passwordMap.put("ha", "1234");
		passwordMap.put("giovanni", "1234");
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
		addProduct(new Product("iPhone 5s", 500.00, 10, 600.00, 0,
				suppliers.get(0)));
		addProduct(new Product("iPhone 4", 250.00, 5, 300.00, 0,
				suppliers.get(0)));
		addProduct(new Product("Galaxy S5", 480.00, 10, 550.00, 0,
				suppliers.get(1)));
	}

	private void generateOrders() {
		ArrayList<LineItem> order1 = new ArrayList<>();
		order1.add(new LineItem(1, 10));
		order1.add(new LineItem(2, 10));
		ArrayList<LineItem> order2 = new ArrayList<>();
		order2.add(new LineItem(3, 20));
		
		addOrder(new Order(7500.0, suppliers.get(0), 1, order1));
		addOrder(new Order(9600.0, suppliers.get(1), 2, order2));
	}

	private void generateDeliveries() {
		addDelivery(new Delivery(suppliers.get(0), orders.get(0).getID()));
		addDelivery(new Delivery(suppliers.get(1), orders.get(1).getID()));
	}

	private void generateInvoices() {
		ArrayList<LineItem> items = new ArrayList<>();
		items.add(new LineItem(1, 1));
		addInvoice(new Invoice(items, customers.get(0)));
	}

	// No encryption on passwords, temp method??
	public User authorizeUser(String username, String password) {
		User user = new User(-1, "Not authorised");
		
		for(User u:users){
			if(u.getUsername().toLowerCase().equals(username.toLowerCase())){
				if(passwordMap.get(username).equals(password)){
					user = u;
				}
			}
		}
		
		return user;
	}

	// Return instance of database
	public static Database getInstance() {
		if (db == null) {
			db = new Database();
		}
		return db;
	}
}
