package team4.retailsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;

/**
 * An Swing implementation of RetailSystemView, it holds a content pane that
 * switches between the various different retail system panels, along with a
 * selection of buttons to decide which panel to view.
 * 
 * @author Eoin Kernan D14123081
 * 
 */
public class RetailSystemSwingView 
extends JFrame
implements RetailSystemView
{

	//An array of RetailViewListeners, to be informed of interface events if required
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
	//Buttons for switching between the panels
	JButton customerButton;
	JButton invoiceButton;
	JButton orderButton;
	JButton productButton;
	JButton supplierButton;
	JButton userButton;
	
	private JPanel contentPane;//The main content frame for the frame, holds everything else
	private JPanel buttonPanel;//holds the row of buttons at the top
	private JPanel contentPanel;//holds whichever of the 'panels' it's told to
	
	//The main panel views, currently one for each aspect of the system.
	private CustomerPanel customerPanel;
	private InvoicePanel invoicePanel;
	private OrderPanel orderPanel;
	private productPanel productPanel;
	private SupplierPanel supplierPanel;
	private UserPanel userPanel;
	
	public RetailSystemSwingView() {
		
		initialiseComponents();
		addButtonListeners();
		constructView();
		
		setVisible(true);
	}

	private void initialiseComponents(){
		
		contentPane = new JPanel();
		buttonPanel = new JPanel();
		contentPanel = new JPanel();
		
		customerButton = new JButton("Customers");
		invoiceButton = new JButton("Invoices");
		orderButton = new JButton("Orders");
		productButton = new JButton("Products");
		supplierButton = new JButton("Suppliers");
		userButton = new JButton("Users");
		
		customerPanel = new CustomerPanel();
		invoicePanel = new InvoicePanel();
		orderPanel = new OrderPanel();
		productPanel = new productPanel();
		supplierPanel = new SupplierPanel(Database.getInstance().getSuppliers());
		userPanel = new UserPanel();
	}
	
	private void constructView(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 700, 700);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		buttonPanel.add(customerButton);
		buttonPanel.add(invoiceButton);
		buttonPanel.add(orderButton);
		buttonPanel.add(productButton);
		buttonPanel.add(supplierButton);
		buttonPanel.add(userButton);
		//buttonPanel.add(userPanel);
		
		contentPanel.add(customerPanel);
		
		contentPane.add(buttonPanel);
		contentPane.add(contentPanel);
		
		setContentPane(contentPane);
	}
	
	private void addButtonListeners(){
		
		customerButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showCustomerEditingScreen();
			}
			
		});
		
		invoiceButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showInvoiceEditingScreen();
			}
			
		});
		
		orderButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showOrderEditingScreen();
			}
			
		});
		
		productButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showProductEditingScreen();
			}
			
		});
		
		supplierButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showSupplierEditingScreen();
			}
			
		});
		
		userButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showUserEditingScreen();
			}
			
		});
	}
	
	@Override
	public void addRetailViewListener(RetailViewListener listener) {
		listeners.add(listener);
		customerPanel.addListener(listener);
		invoicePanel.addListener(listener);
		orderPanel.addListener(listener);
		productPanel.addListener(listener);
		supplierPanel.addListener(listener);
		userPanel.addListener(listener);
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
		contentPanel.removeAll();
		contentPanel.add(customerPanel);
		this.validate();
	}

	@Override
	public void showInvoiceEditingScreen() {
		contentPanel.removeAll();
		contentPanel.add(invoicePanel);
		this.validate();
		
	}

	@Override
	public void showOrderEditingScreen() {
		contentPanel.removeAll();
		contentPanel.add(orderPanel);
		this.validate();
	}

	@Override
	public void showProductEditingScreen() {
		//TODO: contentPanel is extending a JFrame instead of a panel
		contentPanel.removeAll();
		//contentPanel.add(productPanel);
		this.validate();
		
	}

	@Override
	public void showSupplierEditingScreen() {
		contentPanel.removeAll();
		contentPanel.add(supplierPanel);
		this.validate();
		
	}

	@Override
	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		supplierPanel.displaySuppliers(suppliers);
	}

	@Override
	public void showUserEditingScreen() {
		contentPanel.removeAll();
		contentPanel.add(userPanel);
		this.validate();
	}

	@Override
	public void showError(String errorMessage) {
		// TODO Auto-generated method stub
		
	}

}
