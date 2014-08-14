package team4.retailsystem;

import java.util.ArrayList;

import team4.retailsystem.controller.RetailSystemController;
import team4.retailsystem.model.RetailSystemModel;
import team4.retailsystem.view.RetailSystemBasicView;
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
	private RetailSystemView basicView;
	
	private ArrayList<RetailSystemView> views = new ArrayList<RetailSystemView>();
	
	public RetailSystem() {
		basicView = new RetailSystemBasicView();
		model = new RetailSystemModel();
		
		views.add(basicView);
		controller = new RetailSystemController(model, views);
	}

	public static void main(String[] args) {
		new RetailSystem();
	}

}
