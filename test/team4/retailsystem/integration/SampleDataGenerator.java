package team4.retailsystem.integration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.Delivery;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

/**
 * Used in integration & manual testing to populate the database with sample
 * data.
 * 
 * @author szymon
 */
public class SampleDataGenerator {
	private static void generateDatabase() {
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

	private static void addNewUser(String username, String password, int authLevel){
		Database.getInstance().addUser(authLevel, username, password);
	}
	
	private static void generateUsers() {
		addNewUser("admin", "admin", User.ADMINISTRATOR);
		addNewUser("user", "user", User.NORMAL_USER);
		addNewUser("Eoin", "nioe", User.ADMINISTRATOR);
		addNewUser("Szymon", "nomyzs", User.NORMAL_USER);
		addNewUser("Alan", "nala", User.ADMINISTRATOR);
		addNewUser("Ha", "ah", User.NORMAL_USER);
		addNewUser("Siobhain", "niahbois", User.ADMINISTRATOR);
		addNewUser("Giovanni", "innavoig", User.NORMAL_USER);
	}

	private static void generateCustomers() {
		Database.getInstance().addCustomer(new Customer("Carphone Warehouse", "4758439",
				"cwarehouse@customers.com", "Dublin"));
		Database.getInstance().addCustomer(new Customer("Meteor", "3534594", "meteor@customers.com",
				"Cork"));
		Database.getInstance().addCustomer(new Customer("Vodafone", "6849932",
				"vodafone@customer.com", "Wicklow"));
	}

	private static void generateSuppliers() {
		Database.getInstance().addSupplier(new Supplier("Apple", "California","apple@suppliers.com",
				"184234242"));
		Database.getInstance().addSupplier(new Supplier("Samsung", "Tokyo", "samsung@suppliers.com",
				"76328843"));
	}

	private static void generateProducts() {
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		Database.getInstance().addProduct(new Product("iPhone 5s", 500.00, 100.00, 50, suppliers.get(0)));
		Database.getInstance().addProduct(new Product("iPhone 4", 250.00, 50.00, 75, suppliers.get(0)));
		Database.getInstance().addProduct(new Product("Galaxy S5", 480.00, 70.00, 100, suppliers.get(1)));
	}

	private static void generateOrders() {
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		ArrayList<LineItem> order1 = new ArrayList<>();
		order1.add(new LineItem(1, 10));
		order1.add(new LineItem(2, 10));
		ArrayList<LineItem> order2 = new ArrayList<>();
		order2.add(new LineItem(3, 20));

		Database.getInstance().addOrder(new Order(7500.0, suppliers.get(0), 1, order1, "15/08/2014"));
		Database.getInstance().addOrder(new Order(9600.0, suppliers.get(1), 2, order2, "16/08/2014"));
	}

	private static void generateDeliveries() {
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		ArrayList<Order> orders = Database.getInstance().getOrders();
		try {
            Database.getInstance().addDelivery(new Delivery(suppliers.get(0), orders.get(0).getID(), new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2014")));
            //Database.getInstance().addDelivery(new Delivery(suppliers.get(1), orders.get(1).getID(), new Date()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	private static void generateInvoices() {
		ArrayList<Customer> customers = Database.getInstance().getCustomers();
		ArrayList<LineItem> items = new ArrayList<>();
		items.add(new LineItem(1, 1));
		Database.getInstance().addInvoice(new Invoice(items, customers.get(0)));
	}
	
	public static void main(String[] args){
		generateDatabase();
	}
}
