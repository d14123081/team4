package team4.retailsystem.view;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

public class RetailSystemSwingView 
extends JFrame
implements RetailSystemView
{

	private ArrayList<RetailViewListener> listeners;
	
	private JPanel buttonPanel;
	private JPanel contentPanel;
	
	private CustomerPanel customerPanel;
	private InvoicePanel invoicePanel;
	private OrderPanel orderPanel;
	private productPanel productPanel;
	private SupplierPanel supplierPanel;
	private UserPanel userPanel;
	
	public RetailSystemSwingView() {
		customerPanel = new CustomerPanel();
		invoicePanel = new InvoicePanel();
		orderPanel = new OrderPanel();
		productPanel = new productPanel();
		supplierPanel = new SupplierPanel(Database.getInstance().getSuppliers());
		userPanel = new UserPanel();
	}

	@Override
	public void addRetailViewListener(RetailViewListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public void showLoginScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMainMenuScreen(User user) {
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
	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		supplierPanel.displaySuppliers(suppliers);
	}

	@Override
	public void showUserEditingScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError(String errorMessage) {
		// TODO Auto-generated method stub
		
	}

}
