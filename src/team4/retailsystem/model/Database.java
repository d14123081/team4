package team4.retailsystem.model;


/**
 * Unfinished database class for the group project (Just uploading class to test GitHub)
 * 
 * Need to know constructor for external class
 * to finish the generate methods
 * 
 * Not sure how the updateTable methods and authoriseUser
 * will run yet so can't finish the methods (authorize might be right but no encryption as of yet?)
 * 
 * 
 * 
 * @author D14123080 Alan Kavanagh
 *
 */
import java.util.ArrayList;

public class Database 
{
	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<Supplier> suppliers = new ArrayList<>();
	private ArrayList<Product> products = new ArrayList<>();
	private ArrayList<Delivery> deliveries = new ArrayList<>();
	private ArrayList<Invoice> invoices = new ArrayList<>();
	private ArrayList<Order> orders = new ArrayList<>();
	private ArrayList<User> users = new ArrayList<>();

	public Database() 
	{
		generateDatabase();
	}

	// Methods to add rows to table
	public boolean addCustomer(Customer c) 
	{
		try {
			customers.add(c);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addSupplier(Supplier s) 
	{
		try {
			suppliers.add(s);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addProduct(Product p) 
	{
		try {
			products.add(p);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addDelivery(Delivery d) 
	{
		try {
			deliveries.add(d);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addInvoice(Invoice i) 
	{
		try {
			invoices.add(i);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addOrder(Order o) 
	{
		try {
			orders.add(o);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addUser(User u) 
	{
		try {
			users.add(u);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Remove row from table
	public boolean deleteCustomer(Customer c) 
	{
		try {
			customers.remove(c);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteSupplier(Supplier s) 
	{
		try {
			suppliers.remove(s);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteProduct(Product p) 
	{
		try {
			products.remove(p);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteDelivery(Delivery d) 
	{
		try {
			deliveries.remove(d);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteInvoice(Invoice i) 
	{
		try {
			invoices.remove(i);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteOrder(Order o) 
	{
		try {
			orders.remove(o);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteUser(User u) 
	{
		try {
			users.remove(u);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Select * from table
	public ArrayList<Customer> getCustomers() {  return customers; }
	public ArrayList<Supplier> getSuppliers() { return suppliers; }
	public ArrayList<Product> getProducts() { return products; }
	public ArrayList<Delivery> getDeliveries() { return deliveries; }
	public ArrayList<Invoice> getInvoices() { return invoices; }
	public ArrayList<Order> getOrders() { return orders; }
	public ArrayList<User> getUsers() { return users; }

	// Unfinished methods to update objects in database
	public boolean updateCustomer(Customer c) { return true; }
	public boolean updateSupplier(Supplier s) { return true; }
	public boolean updateProduct(Product p) { return true; }
	public boolean updateDelivery(Delivery d) { return true; }
	public boolean updateInvoice(Invoice i) { return true; }
	public boolean updateOrder(Order o) { return true; }
	public boolean updateUser(User u) { return true; }

	// Auto generate database
	void generateDatabase() 
	{
		generateCustomers();
		generateSuppliers();
		generateProducts();
		generateDeliveries();
		generateInvoices();
		generateOrders();
		generateUsers();
	}

	// Unfinished methods to generate instances of the tables
	void generateCustomers() 
	{
		addCustomer(new Customer("Alan", "0857223104", "Alan@DIT.ie", "Dublin"));
		addCustomer(new Customer("John", "0854213514", "John@DIT.ie", "Cork"));
		addCustomer(new Customer("Mary", "0859434115", "Mary@DIT.ie", "Wicklow"));
	}
	
	void generateSuppliers() {}
	void generateProducts() {}
	void generateDeliveries(){}
	void generateInvoices() {}
	void generateOrders() {}
	void generateUsers() {}

	//No encryption on passwords, temp method
	public User authorizeUser(String user, String pass)
	{
		try
		{
			for(User u: users)
			{
				if(u.getUsername().equals(user))
				{
					if(u.getPassword().equals(pass))
					{
						return u;
					}
					else
					{
						return -1;
					}
				}
			}
			return -1;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}
