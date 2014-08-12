package team4.retailsystem.model;

import java.util.ArrayList;

/**
 * A class to represent the retail system model. It informs listeners of any
 * model state changes.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class RetailSystemModel {

	private ArrayList<RetailModelListener> listeners = new ArrayList<RetailModelListener>();

	public RetailSystemModel() {

	}

	public void addRetailModelListener(RetailModelListener listener) {
		listeners.add(listener);
	}

}
