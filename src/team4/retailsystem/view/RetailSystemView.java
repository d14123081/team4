package team4.retailsystem.view;

import java.util.ArrayList;

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
	
	public void showMainMenuScreen(User user);
	
	public void showCustomerEditingScreen();
	
	public void showInvoiceEditingScreen();
	
	public void showOrderEditingScreen();
	
	public void showProductEditingSCreen();
	
	public void showSupplierEditingScreen();
	
	public void updateSupplierList(ArrayList<Supplier> suppliers);
	
	public void showUserEditingScreen();
	
	public void showError(String errorMessage);
}
