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
		
		//build a default screen object
		screen = new Screen.Builder().build();
	}

	public void addRetailViewListener(RetailViewListener listener){
		listeners.add(listener);
	}
}
