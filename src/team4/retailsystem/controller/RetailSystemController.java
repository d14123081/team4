package team4.retailsystem.controller;

import java.util.ArrayList;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.RetailModelListener;
import team4.retailsystem.model.RetailSystemModel;
import team4.retailsystem.model.User;
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
	private User user;
	private Database database;
	
	private ArrayList<RetailSystemView> views = new ArrayList<RetailSystemView>();
	private RetailSystemModel model;
	
	public RetailSystemController(RetailSystemModel model, ArrayList<RetailSystemView> views){
		this.model = model;
		this.views = views;
		this.database = Database.getInstance();
		
		this.model.addRetailModelListener(this);
		
		for(RetailSystemView r:views){
			r.addRetailViewListener(this);
			r.showLoginScreen();
		}
	}
	
	@Override
	public void usersChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customersChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void invoicesChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void productsChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ordersChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppliersChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveriesChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doLogin(String username, String password) {
		user = database.authorizeUser(username, password);
		
		//check auth level here, decide what to do/show next
		if(user.getAuthorizationLevel() == User.NO_AUTHORIZATION){
			for(RetailSystemView r: views){
				r.showError("Unable to authorise user: "+username);
			}
		}
		else
		{
			for(RetailSystemView r: views){
				r.showMainMenuScreen(user);
			}
		}
	}

	@Override
	public void errorAcknowledged() {
		//Take user back to main menu, or login screen.
		if(user.getAuthorizationLevel() == User.NO_AUTHORIZATION){
			for(RetailSystemView r: views){
				r.showLoginScreen();
			}
		}
		else
		{
			for(RetailSystemView r: views){
				r.showMainMenuScreen(user);
			}
		}
		
	}
	
}
