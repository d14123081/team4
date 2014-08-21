package team4.retailsystem.view;

import java.util.ArrayList;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.Order;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

/**
 * A class to implement a basic text-based retail system interface
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class RetailSystemBasicView 
implements RetailSystemView
{

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	private Screen screen;
	
	public RetailSystemBasicView() {
		screen = new Screen.Builder().title("Retail System Temporary View").build();
	}
	
	public void addRetailViewListener(RetailViewListener listener){
		listeners.add(listener);
	}
	
	public void showLoginScreen(){
		String username = "";
		String password = "";
		
		screen.setScreenText("Please enter your username: ");
		while(username.equals("")){
			username = screen.getString();
			if(username.equals("")){
				screen.updateScreenText("Empty string not allowed. Please enter a username: ");
			}
		}
		
		screen.updateScreenText("Please enter your password: ");
		while(password.equals("")){
			password = screen.getString();
			if(password.equals("")){
				screen.updateScreenText("Empty string not allowed. Please enter a password: ");
			}
		}
		
		for(RetailViewListener r:listeners){
			r.doLogin(username, password);
		}
	}

	@Override
	public void showMainMenuScreen(User user) {
		
		//decide what menu options to show the user based on their auth level.
		String welcomMessage = "Welcome, "+user.getUsername()+". ";
		
		if(user.getAuthorizationLevel() == User.ADMINISTRATOR){
			welcomMessage += "You have administrator levelaccess.";
		}
		else if(user.getAuthorizationLevel() == User.NORMAL_USER){
			welcomMessage += "You have normal level access.";
		}
		
		screen.setScreenText(welcomMessage);
	}

	@Override
	public void showCustomerEditingScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showInvoiceEditingScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showOrderEditingScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showProductEditingScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSupplierEditingScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showUserEditingScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError(String errorMessage) {
		screen.setScreenText(errorMessage);
		screen.updateScreenText("Press enter to continue.");
		screen.getString();
		
		for(RetailViewListener r:listeners){
			r.errorAcknowledged();
		}
	}

	@Override
	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInvoices(ArrayList<Invoice> invoices) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProducts(ArrayList<Product> products) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCustomers(ArrayList<Customer> customers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUsers(ArrayList<User> users) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateOrders(ArrayList<Order> orders) {
		// TODO Auto-generated method stub
		
	}
}
