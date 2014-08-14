package team4.retailsystem.view;

/**
 * An interface that describes the expected behaviours of a retail system view.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public interface RetailSystemView {

	public void addRetailViewListener(RetailViewListener listener);
	
	public void showLoginScreen();
	
	public void showMainMenuScreen();
	
	public void showCustomerEditingScreen();
	
	public void showInvoiceEditingScreen();
	
	public void showOrderEditingScreen();
	
	public void showProductEditingSCreen();
	
	public void showSupplierEditingScreen();
	
	public void showUserEditingScreen();
}
