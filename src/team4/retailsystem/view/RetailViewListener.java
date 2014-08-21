package team4.retailsystem.view;

import java.util.ArrayList;
import java.util.Date;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

/**
 * An interface to describe the behaviour of any object that is listening to the
 * retail view for events.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public interface RetailViewListener {

	public void errorAcknowledged();
	
	public void clickTestAdd(String value);
	
	//login panel events
	public void doLogin(String username, String password);
	
	//customer panel events
	public void clickUpdateCustomer(String name, String address, String email, String phone);
	public void clickSaveNewCustomer(String name, String address, String email, String phone);
	public void clickDeleteCustomer(int id);
	
	//product panel events
	public void clickCreateProduct(String name, double cost, double markup, int stockLevel, int supplierId);
	public void clickUpdateProduct(String name, double cost, double markup, int stockLevel, int supplierId);
	public void clickDeleteProduct(int productId);
	
	//order panel events
	public void clickCreateOrder(double cost, ArrayList<LineItem> lineItems, Supplier supplier, int deliveryId);
	public void clickUpdateOrder(double cost, ArrayList<LineItem> lineItems, Supplier supplier, int deliveryId, Date date);
	public void clickDeleteOrder(int orderId);
	
	//invoice panel events
	public void clickCreateInvoice(ArrayList<LineItem> lineItems, Customer customer);
	public void clickUpdateInvoice(ArrayList<LineItem> lineItems, Customer customer); //put func to change cost/date in method?
	public void clickDeleteInvoice(int invoiceId);
	
	public void clickSelectProduct(Product p);
	public void clickSelectInvoice(Invoice i);
	public void databaseCreateInvoice(double cost, ArrayList<LineItem> lineItems, Customer customer, Date date);
	
	//user panel events
	public void clickCreateUser(String username, String pass, int authLevel);
	public void clickUpdateUser(String username, String pass, int authLevel);
	public void clickDeleteUser(int userId);
	
	public void clickSelectUser(User u);
	
	//deliver panel events
	
	//supplier panel events
	public void clickAddSupplier(String name, String address, String email, String phone);
	public void clickUpdateSupplier(String name, String address, String email, String phone);
	public void clickDeleteSupplier(int supplierId);
	
}
