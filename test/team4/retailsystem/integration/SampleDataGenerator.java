package team4.retailsystem.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	private static Random random = new Random(System.currentTimeMillis());
	
	private static ArrayList<String> companyNames = new ArrayList<String>();
	private static ArrayList<String> addresses = new ArrayList<String>();
	private static ArrayList<String> productNames = new ArrayList<String>();

	private static void generateDatabase() {
		generateUsers();
		generateCustomers();
		generateSuppliers();
		generateProducts();
		generateOrders();
		generateDeliveries();
		generateInvoices();
	}

	private static void addNewUser(String username, String password,
			int authLevel) {
		Database.getInstance().addUser(authLevel, username, password);
	}

	private static String getCompanyName() {
		if(companyNames.size() == 0){
			File txt = new File("names.txt");
			try {
				Scanner scan = new Scanner(txt);				
				while(scan.hasNextLine()){
					companyNames.add(scan.nextLine());
				}				
				scan.close();		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}	
		}
		
		return companyNames.remove(random.nextInt(companyNames.size()));
	}

	private static String getEmail(String name) {
		return name.replace(" ", "").toLowerCase() + "@sample.com";
	}

	private static String getTelephone() {
		String tel = "";
		for(int i=0; i<7; i++){
			tel += random.nextInt(9);
		}
		return tel;
	}

	private static String getAddress() {
		if(addresses.size() == 0){
			File txt = new File("locations.txt");
			try {
				Scanner scan = new Scanner(txt);				
				while(scan.hasNextLine()){
					addresses.add(scan.nextLine());
				}				
				scan.close();		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}	
		}
		
		return addresses.get(random.nextInt(addresses.size()));
	}

	private static String getProductName() {
		if(productNames.size() == 0){
			File txt = new File("products.txt");
			try {
				Scanner scan = new Scanner(txt);				
				while(scan.hasNextLine()){
					productNames.add(scan.nextLine());
				}				
				scan.close();		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}	
		}
		
		return productNames.remove(random.nextInt(productNames.size()));
	}

	private static void generateSuppliers(int amount) {
		System.out.print("\nGenerating suppliers");
		for(int i=0; i<amount; i++){
			String name = getCompanyName();
			String address = getAddress();
			String email = getEmail(name);
			String telephone = getTelephone();
			
			Database.getInstance().addSupplier(new Supplier(name, address, email, telephone));
			System.out.print(".");
		}
	}

	private static void generateCustomers(int amount) {
		System.out.print("\nGenerating customers");
		for(int i=0; i<amount; i++){
			String name = getCompanyName();
			String address = getAddress();
			String email = getEmail(name);
			String telephone = getTelephone();
			
			Database.getInstance().addCustomer(new Customer(name, telephone, email, address));
			System.out.print(".");
		}
	}
	
	private static void generateProducts(int amount){
		System.out.print("\nGenerating products");
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		for(int i=0; i<amount; i++){
			String name = getProductName();
			double cost = random.nextDouble()*15;
			double markup = random.nextDouble()*250;
			int stockLevel = random.nextInt(200);
			Supplier s = suppliers.get(random.nextInt(suppliers.size()-1));
			
			Database.getInstance().addProduct(new Product(name, cost, markup, stockLevel, s));
			System.out.print(".");
		}
	}
	
	private static void generateOrders(int amount){
		System.out.print("\nGenerating orders");
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		ArrayList<Product> products = Database.getInstance().getProducts();
		for(int i=0; i<amount; i++){
			ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
			int lineItemsLimit = random.nextInt(5) + 5;
			for(int j=0; j<lineItemsLimit;j++){
				lineItems.add(new LineItem(products.get(random.nextInt(products.size()-1)).getID(), random.nextInt(25)+1));
			}
			Supplier s = suppliers.get(random.nextInt(suppliers.size()-1));
			Date date = new Date();
			date = new Date(date.getTime() - 1000*60*60*24*(random.nextInt(31*6)));
			
			Database.getInstance().addOrder(new Order(1, s, 0, lineItems, 0, date));
			System.out.print(".");
		}
	}
	
	private static void generateInvoices(int amount){
		System.out.print("\nGenerating invoices");
		ArrayList<Product> products = Database.getInstance().getProducts();
		ArrayList<Customer> customers = Database.getInstance().getCustomers();
		for(int i=0; i<amount; i++){
			ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
			int lineItemsLimit = random.nextInt(5) + 5;
			for(int j=0; j<lineItemsLimit;j++){
				lineItems.add(new LineItem(products.get(random.nextInt(products.size()-1)).getID(), random.nextInt(25)+1));
			}
			Customer c = customers.get(random.nextInt(customers.size()-1));
			Date date = new Date();
			date = new Date(date.getTime() - 1000*60*60*24*(random.nextInt(31*6)));
			
			Database.getInstance().addInvoice(new Invoice(lineItems, c, date));
			System.out.print(".");
		}
	}
	
	private static void generateDeliveries(int amount){
		System.out.print("\nGenerating deliveries");
		ArrayList<Order> orders = Database.getInstance().getOrders();
		
		for(int i=0; i<amount; i++){
			int randOrderID = random.nextInt(orders.size()-1);
			Order o = orders.get(randOrderID);
			Date date = new Date(o.getOrderDate().getTime() + 1000*60*60*24*(random.nextInt(3)+2));
			
			Database.getInstance().addDelivery(new Delivery(o.getSupplier(), o.getID(), date, 0));
			orders.remove(randOrderID);
			System.out.print(".");
		}
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
		Database.getInstance().addCustomer(
				new Customer("Carphone Warehouse", "4758439",
						"cwarehouse@customers.com", "Dublin"));
		Database.getInstance().addCustomer(
				new Customer("Meteor", "3534594", "meteor@customers.com",
						"Cork"));
		Database.getInstance().addCustomer(
				new Customer("Vodafone", "6849932", "vodafone@customer.com",
						"Wicklow"));
	}

	private static void generateSuppliers() {
		Database.getInstance().addSupplier(
				new Supplier("Apple", "California", "apple@suppliers.com",
						"184234242"));
		Database.getInstance().addSupplier(
				new Supplier("Samsung", "Tokyo", "samsung@suppliers.com",
						"76328843"));
	}

	private static void generateProducts() {
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		Database.getInstance().addProduct(
				new Product("iPhone 5s", 500.00, 100.00, 50, suppliers.get(0)));
		Database.getInstance().addProduct(
				new Product("iPhone 4", 250.00, 50.00, 75, suppliers.get(0)));
		Database.getInstance().addProduct(
				new Product("Galaxy S5", 480.00, 70.00, 100, suppliers.get(1)));
	}

	private static void generateOrders() {
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		ArrayList<LineItem> order1 = new ArrayList<>();
		order1.add(new LineItem(1, 10));
		order1.add(new LineItem(2, 10));
		ArrayList<LineItem> order2 = new ArrayList<>();
		order2.add(new LineItem(3, 20));

		Database.getInstance().addOrder(
				new Order(7500.0, suppliers.get(0), 1, order1, "15/08/2014"));
		Database.getInstance().addOrder(
				new Order(9600.0, suppliers.get(1), 2, order2, "16/08/2014"));
	}

	private static void generateDeliveries() {
		ArrayList<Supplier> suppliers = Database.getInstance().getSuppliers();
		ArrayList<Order> orders = Database.getInstance().getOrders();
		try {
            Database.getInstance().addDelivery(new Delivery(suppliers.get(0), orders.get(0).getID(), new SimpleDateFormat("dd/MM/yyyy").parse("20/08/2014")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}

	private static void generateInvoices() {
		ArrayList<Customer> customers = Database.getInstance().getCustomers();
		ArrayList<LineItem> items = new ArrayList<>();
		items.add(new LineItem(1, 1));
		Database.getInstance().addInvoice(new Invoice(items, customers.get(0)));
	}
	
	private static void generateRandomDatabase(){
		generateUsers();
		generateSuppliers(30);
		generateCustomers(60);
		generateProducts(150);
		generateOrders(150);
		generateInvoices(150);		
		generateDeliveries(80);
	}

	public static void main(String[] args) {
		generateDatabase();
		//generateRandomDatabase();
	}
}
