package team4.retailsystem.model;

/**
 * An interface to describe the behaviour of any object that is listening to the
 * retail model for changes.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public interface RetailModelListener {

	public void usersChanged();
	
	public void customersChanged();
	
	public void invoicesChanged();
	
	public void productsChanged();
	
	public void ordersChanged();
	
	public void suppliersChanged();
	
	public void deliveriesChanged();
	
}
