package team4.retailsystem.controller;

import java.util.ArrayList;
import java.util.Date;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.RetailModelListener;
import team4.retailsystem.model.RetailSystemModel;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;
import team4.retailsystem.view.RetailSystemView;
import team4.retailsystem.view.RetailViewListener;

/**
 * Class will provide controller functionality for the team4 retail system, acting as
 * a go-between for the system's model and view. The controller will be informed of
 * interface events by the view, and will decide what action to take. The controller
 * will also be listening to the model for change events, and informing the view when
 * necessary.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class RetailSystemController
implements RetailModelListener, RetailViewListener
{
	private User user;
	
	private ArrayList<RetailSystemView> views = new ArrayList<RetailSystemView>();
	private RetailSystemModel model;
	
	public RetailSystemController(RetailSystemModel model, ArrayList<RetailSystemView> views){
		this.model = model;
		this.views = views;
		
		this.model.addRetailModelListener(this);
		
		for(RetailSystemView r:views){
			r.addRetailViewListener(this);
			r.showLoginScreen();
		}
		
		//fire change events to populate view for the first time.
		invoicesChanged();
		productsChanged();
		customersChanged();
	}
	
	@Override
	public void usersChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customersChanged() {
		for(RetailSystemView v:views){
			v.updateCustomers(model.getCustomers());
		}
	}

	@Override
	public void invoicesChanged() {
		for(RetailSystemView v:views){
			v.updateInvoices(model.getInvoices());
		}
	}

	@Override
	public void productsChanged() {
		for(RetailSystemView v:views){
			v.updateProducts(model.getProducts());
		}
	}

	@Override
	public void ordersChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppliersChanged() {
		ArrayList<Supplier> suppliers = model.getSuppliers();
		for(RetailSystemView v:views){
			v.updateSupplierList(suppliers);
		}
	}

	@Override
	public void deliveriesChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doLogin(String username, String password) {
		user = model.authoriseUser(username, password);
		
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

	@Override
	public void clickTestAdd(String value) {
		// TODO Auto-generated method stub
		
		//do something with the value here (update the model or whatever)
		
	}

	@Override
	public void clickUpdateCustomer(int id, String name, String address, String email,
			String phone) {
		Customer c = Database.getInstance().getCustomerById(id);
		c.setName(name);
		c.setAddress(address);
		c.setEmail(email);
		c.setTelephoneNumber(phone);
		
		model.updateCustomer(c);
	}

	@Override
	public void clickSaveNewCustomer(String name, String address, String email,
			String phone) {
		model.addCustomer(new Customer(name, phone, email, address));
		
	}

	@Override
	public void clickDeleteCustomer(int id) {
		model.deleteCustomer(id);
	}

	@Override
	public void clickCreateProduct(String name, double cost, double markup,
			int stockLevel, int supplierId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickUpdateProduct(String name, double cost, double markup,
			int stockLevel, int supplierId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickDeleteProduct(int productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickCreateUser(String username, String pass, int authLevel) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void clickUpdateUser(String username, String pass, int authLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickDeleteUser(int userId) {
		model.deleteUser(model.getUserById(userId));
	}
	
	

	@Override
	public void clickAddSupplier(String name, String address, String email,
			String phone) {
		model.addSupplier(new Supplier(name, address, email, phone));
		
	}

	@Override
	public void clickUpdateSupplier(String name, String address, String email,
			String phone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickDeleteSupplier(int supplierId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickCreateOrder(double cost, ArrayList<LineItem> lineItems,
			Supplier supplier, int deliveryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickUpdateOrder(double cost, ArrayList<LineItem> lineItems,
			Supplier supplier, int deliveryId, Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clickDeleteOrder(int orderId) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void clickDeleteInvoice(int invoiceId) {
		model.deleteInvoice(model.getInvoiceById(invoiceId));
		
	}

	@Override
	public void clickCreateInvoice(ArrayList<LineItem> lineItems,
			Customer customer) {
		model.addInvoice(new Invoice(lineItems, customer));
		
	}

	@Override
	public void clickUpdateInvoice(int id, ArrayList<LineItem> lineItems,
			Customer customer) {
		model.updateInvoice(id, lineItems, customer);
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void databaseCreateInvoice(double cost,
			ArrayList<LineItem> lineItems, Customer customer, Date date) {
		// TODO Auto-generated method stub
		
	}
	
}
