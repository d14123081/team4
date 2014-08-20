package team4.retailsystem.model;

import java.util.ArrayList;

/**
 * A class to represent the retail system model. It informs listeners of any
 * model state changes.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class RetailSystemModel {

	public static final int USERS_CHANGED = 0;
	public static final int CUSTOMERS_CHANGED = 1;
	public static final int INVOICES_CHANGED = 2;
	public static final int PRODUCTS_CHANGED = 3;
	public static final int ORDERS_CHANGED = 4;
	public static final int SUPPLIERS_CHANGED = 5;
	public static final int DELIVERIES_CHANGED = 6;
	
	private Database database;
	private ArrayList<RetailModelListener> listeners;
	
	private ArrayList<User> users;
	private ArrayList<Customer> customers;
	private ArrayList<Invoice> invoices;
	private ArrayList<Product> products;
	private ArrayList<Order> orders;
	private ArrayList<Supplier> suppliers;
	private ArrayList<Delivery> deliveries;

	public RetailSystemModel() {
		listeners = new ArrayList<RetailModelListener>();
		database = Database.getInstance();
		
		users = database.getUsers();
		customers = database.getCustomers();
		invoices = database.getInvoices();
		products = database.getProducts();
		orders = database.getOrders();
		suppliers = database.getSuppliers();
		deliveries = database.getDeliveries();
	}
	
	//getters
	public ArrayList<User> getUsers() {
		return users;
	}
	public ArrayList<Customer> getCustomers() {
		return customers;
	}
	public ArrayList<Invoice> getInvoices() {
		return invoices;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public ArrayList<Supplier> getSuppliers() {
		return suppliers;
	}
	public ArrayList<Delivery> getDeliveries() {
		return deliveries;
	}

	//User class methods
	public void addUser(User u){
		database.addUser(u);
		notifyListeners(USERS_CHANGED);
	}
	
	public void updateUser(User u){
		database.updateUser(u);
		notifyListeners(USERS_CHANGED);
	}
	
	public void deleteUser(User u){
		database.deleteUser(u);
		notifyListeners(USERS_CHANGED);
	}
	
	//Customer class methods
	public void addCustomer(Customer c){
		database.addCustomer(c);
		notifyListeners(CUSTOMERS_CHANGED);
	}
	
	public void updateCustomer(Customer c){
		database.updateCustomer(c);
		notifyListeners(CUSTOMERS_CHANGED);
	}
	
	public void deleteCustomer(int id){
		//database.deleteCustomer(c);
		notifyListeners(CUSTOMERS_CHANGED);
	}
	
	//Invoice class methods
	public void addInvoice(Invoice i){
		database.addInvoice(i);
		notifyListeners(INVOICES_CHANGED);
	}
	
	public void updateInvoice(Invoice i){
		database.updateInvoice(i);
		notifyListeners(INVOICES_CHANGED);
	}
	
	public void deleteInvoice(Invoice i){
		database.deleteInvoice(i);
		notifyListeners(INVOICES_CHANGED);
	}
	
	//Product class methods
	public void addProduct(Product p){
		database.addProduct(p);
		notifyListeners(PRODUCTS_CHANGED);
	}
	
	public void updateProduct(Product p){
		database.updateProduct(p);
		notifyListeners(PRODUCTS_CHANGED);
	}
	
	public void deleteProduct(Product p){
		database.deleteProduct(p);
		notifyListeners(PRODUCTS_CHANGED);
	}
	
	//Order class methods
	public void addOrder(Order o){
		database.addOrder(o);
		notifyListeners(ORDERS_CHANGED);
	}
	
	public void updateOrder(Order o){
		database.updateOrder(o);
		notifyListeners(ORDERS_CHANGED);
	}
	
	public void deleteOrder(Order o){
		database.deleteOrder(o);
		notifyListeners(ORDERS_CHANGED);
	}
	
	//Supplier class methods
	public void addSupplier(Supplier s){
		database.addSupplier(s);
		notifyListeners(SUPPLIERS_CHANGED);
	}
	
	public void updateSupplier(Supplier s){
		database.updateSupplier(s);
		notifyListeners(SUPPLIERS_CHANGED);
	}
	
	public void deleteSupplier(Supplier s){
		database.deleteSupplier(s);
		notifyListeners(SUPPLIERS_CHANGED);
	}
	
	//Delivery class methods
	public void addDelivery(Delivery d){
		database.addDelivery(d);
		notifyListeners(DELIVERIES_CHANGED);
	}
	
	public void updateDelivery(Delivery d){
		database.updateDelivery(d);
		notifyListeners(DELIVERIES_CHANGED);
	}
	
	public void deleteDelivery(Delivery d){
		database.deleteDelivery(d);
		notifyListeners(DELIVERIES_CHANGED);
	}
	
	public void addRetailModelListener(RetailModelListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners(int changeType){
		switch(changeType){
			case(USERS_CHANGED):{
				for(RetailModelListener r:listeners){
					r.usersChanged();
				}
			}
			case(CUSTOMERS_CHANGED):{
				for(RetailModelListener r:listeners){
					r.customersChanged();
				}
			}
			case(INVOICES_CHANGED):{
				for(RetailModelListener r:listeners){
					r.invoicesChanged();
				}
			}
			case(PRODUCTS_CHANGED):{
				for(RetailModelListener r:listeners){
					r.productsChanged();
				}
			}
			case(ORDERS_CHANGED):{
				for(RetailModelListener r:listeners){
					r.ordersChanged();
				}
			}
			case(SUPPLIERS_CHANGED):{
				for(RetailModelListener r:listeners){
					r.suppliersChanged();
				}
			}
			case(DELIVERIES_CHANGED):{
				for(RetailModelListener r:listeners){
					r.deliveriesChanged();
				}
			}
		}
	}
}
