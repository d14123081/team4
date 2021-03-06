package team4.retailsystem.model;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import team4.retailsystem.utils.EncryptionModule;
/**
 * 
 * @author D14123080 Alan Kavanagh
 *
 */
public class OldDatabase {

	private static OldDatabase db = new OldDatabase();

	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<Supplier> suppliers = new ArrayList<>();
	private ArrayList<Product> products = new ArrayList<>();
	private ArrayList<Delivery> deliveries = new ArrayList<>();
	private ArrayList<Invoice> invoices = new ArrayList<>();
	private ArrayList<Order> orders = new ArrayList<>();
	private ArrayList<User> users = new ArrayList<>();

	private OldDatabase() {
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
	
	public void deleteCustomerById(int id){
		Customer temp = getCustomerById(id);
		customers.remove(temp);
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
	
	public Customer getCustomerById(int id){
		for(Customer c:customers){
			if(c.getID()==id){
				return c;
			}
		}
		return null;
	}

	public ArrayList<Supplier> getSuppliers() {
		return suppliers;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public Product getProductById(int id) {
		for (Product p : products) {
			if (p.getID() == id) {
				return p;
			}
		}
		return null;
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
	//TODO: This isn't updating the object in the users arraylist
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

	//TODO: This isn't updating the object in the users arraylist
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

	//TODO: This isn't updating the object in the users arraylist
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

	//TODO: This isn't updating the object in the users arraylist
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

	//TODO: This isn't updating the object in the users arraylist
	public boolean updateInvoice(int id, Invoice inv) {
		try {
			for (Invoice i : invoices) {
				if (i.getID() == id) {
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

	//TODO: This isn't updating the object in the users arraylist
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

	//TODO: This isn't updating the object in the users arraylist
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

	public void addNewUser(String username, String password, int authLevel){
		EncryptionModule em;
		try {
			em = new EncryptionModule();
			addUser(new User(authLevel, username, em.encrypt(password,"testSalt"), "testSalt"));
		}
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateUsers() {
		addNewUser("admin", "admin", User.ADMINISTRATOR);
		addNewUser("user", "user", User.NORMAL_USER);
		addNewUser("Eoin", "nioe", User.ADMINISTRATOR);
		addNewUser("Szymon", "nomyzs", User.NORMAL_USER);
		addNewUser("Alan", "nala", User.ADMINISTRATOR);
		addNewUser("Ha", "ah", User.NORMAL_USER);
		addNewUser("Siobhain", "niahbois", User.ADMINISTRATOR);
		addNewUser("Giovanni", "innavoig", User.NORMAL_USER);
	}

	private void generateCustomers() {
		addCustomer(new Customer("Carphone Warehouse", "4758439",
				"cwarehouse@customers.com", "Dublin"));
		addCustomer(new Customer("Meteor", "3534594", "meteor@customers.com",
				"Cork"));
		addCustomer(new Customer("Vodafone", "6849932",
				"vodafone@customer.com", "Wicklow"));
	}

	private void generateSuppliers() {
		addSupplier(new Supplier("Apple", "California","apple@suppliers.com",
				"184234242"));
		addSupplier(new Supplier("Samsung", "Tokyo", "samsung@suppliers.com",
				"76328843"));
	}

	private void generateProducts() {
		addProduct(new Product("iPhone 5s", 500.00, 100.00, 50, suppliers.get(0)));
		addProduct(new Product("iPhone 4", 250.00, 50.00, 75, suppliers.get(0)));
		addProduct(new Product("Galaxy S5", 480.00, 70.00, 100, suppliers.get(1)));
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
		try {
            addDelivery(new Delivery(suppliers.get(0), orders.get(0).getID(), new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2014")));
            //addDelivery(new Delivery(suppliers.get(1), orders.get(1).getID(), new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	private void generateInvoices() {
		ArrayList<LineItem> items = new ArrayList<>();
		items.add(new LineItem(1, 1));
		addInvoice(new Invoice(items, customers.get(0)));
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
		for (User user : users) {
			if (user.getUsername().toLowerCase().equals(username.toLowerCase())) {
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

	// Return instance of database
	public static OldDatabase getInstance() {
		
		return db;
	}
}