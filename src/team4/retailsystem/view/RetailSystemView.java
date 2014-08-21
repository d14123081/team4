package team4.retailsystem.view;

import java.util.ArrayList;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

/**
 * An interface that describes the expected behaviours of a retail system view.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public interface RetailSystemView {

	public void addRetailViewListener(RetailViewListener listener);
	
	public void showLoginScreen();
	
	public void updateInvoices(ArrayList<Invoice> invoices);
	
	public void updateProducts(ArrayList<Product> products);
	
	public void updateCustomers(ArrayList<Customer> customers);
	
	public void updateUsers(ArrayList<User> users);
	
	public void showMainMenuScreen(User user);
	
	public void showCustomerEditingScreen();
	
	public void showInvoiceEditingScreen();
	
	public void showOrderEditingScreen();
	
	public void showProductEditingScreen();
	
	public void showSupplierEditingScreen();
	
	public void updateSupplierList(ArrayList<Supplier> suppliers);
	
	public void showUserEditingScreen();
	
	public void showError(String errorMessage);
}
