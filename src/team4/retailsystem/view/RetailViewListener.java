package team4.retailsystem.view;

/**
 * An interface to describe the behaviour of any object that is listening to the
 * retail view for events.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public interface RetailViewListener {

	public void doLogin(String username, String password);
	
	public void errorAcknowledged();
	
	public void clickTestAdd(String value);
	
	public void clickUpdateCustomer(String name, String address, String email, String phone);
	
	public void clickSaveNewCustomer(String name, String address, String email, String phone);
	
	public void clickDeleteCustomer(int id);
}
