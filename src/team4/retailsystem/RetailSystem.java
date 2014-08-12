package team4.retailsystem;

import team4.retailsystem.controller.RetailSystemController;
import team4.retailsystem.model.RetailSystemModel;
import team4.retailsystem.view.RetailSystemView;

/**
 * The main retail system driver. Creates model, view, and controller classes.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class RetailSystem {

	private RetailSystemController controller;
	private RetailSystemModel model;
	private RetailSystemView view;
	
	public RetailSystem() {
		view = new RetailSystemView();
		model = new RetailSystemModel();
		controller = new RetailSystemController(model, view);
		
		controller.init();
	}

	public static void main(String[] args) {
		new RetailSystem();
	}

}
