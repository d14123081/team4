package team4.retailsystem.view;

import java.util.ArrayList;

/**
 * A class to represent the retail system GUI interface
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class RetailSystemView {

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	private Screen screen;
	
	public RetailSystemView() {
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
}
