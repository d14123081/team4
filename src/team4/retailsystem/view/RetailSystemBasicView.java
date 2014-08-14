package team4.retailsystem.view;

import java.util.ArrayList;

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
	public void showMainMenuScreen() {
		// TODO Auto-generated method stub
		
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
	public void showProductEditingSCreen() {
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
}
