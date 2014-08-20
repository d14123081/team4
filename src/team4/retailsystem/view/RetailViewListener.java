package team4.retailsystem.view;

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
	
	//invoice panel events
	
	//user panel events
	public void clickAddUser(String username, String pass, int authLevel);
	public void clickUpdateUser(String username, String pass, int authLevel);
	public void clickDeleteUser(int userId);
	
	//deliver panel events
	
	//supplier panel events
}
