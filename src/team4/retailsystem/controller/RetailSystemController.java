package team4.retailsystem.controller;

import team4.retailsystem.model.RetailModelListener;
import team4.retailsystem.model.RetailSystemModel;
import team4.retailsystem.view.RetailSystemView;
import team4.retailsystem.view.RetailViewListener;

/**
 * Class will provide controller functionality for the team4 retail system, acting as
 * a go-between for the system's model and view. The controller will be informed of
 * interface events by the view, and will decide what action to take. The controller
 * will also me listening to the model for change events, and informing the view when
 * necessary.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class RetailSystemController
implements RetailModelListener, RetailViewListener
{
	
	private RetailSystemView view;
	private RetailSystemModel model;
	
	public RetailSystemController(RetailSystemModel model, RetailSystemView view){
		this.model = model;
		this.view = view;
		
		this.model.addRetailModelListener(this);
		this.view.addRetailViewListener(this);
	}
	
}
