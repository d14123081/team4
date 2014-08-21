package team4.retailsystem.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.Product;
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
	
	//Frame bounds
	private static final int BOUNDS_X = 300, BOUNDS_Y = 300;
	private static final int FRAME_WIDTH = 800, FRAME_HEIGHT = 650;
	
	//panel names for CardLayout manager
	private final static String CUSTOMER = "customer";
	private final static String INVOICE = "invoice";
	private final static String ORDER = "order";
	private final static String PRODUCT = "product";
	private final static String SUPPLIER = "supplier";
	private final static String USER = "user";
	
	//Buttons for switching between the panels
	JButton customerButton;
	JButton invoiceButton;
	JButton orderButton;
	JButton productButton;
	JButton supplierButton;
	JButton userButton;
	
	private Container contentPane;//The main content frame for the frame, holds everything else
	private JPanel buttonPanel;//holds the row of buttons at the top
	private JPanel contentPanel;//holds whichever of the 'panels' it's told to by CardLayout manager
	
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
		
		contentPane = getContentPane();
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
		setBounds(BOUNDS_X, BOUNDS_Y, FRAME_WIDTH, FRAME_HEIGHT);
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonPanel.add(customerButton);
		buttonPanel.add(invoiceButton);
		buttonPanel.add(orderButton);
		buttonPanel.add(productButton);
		buttonPanel.add(supplierButton);
		buttonPanel.add(userButton);
		//buttonPanel.add(userPanel);
		
		contentPanel.setLayout(new CardLayout());
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPanel.add(customerPanel, CUSTOMER);
		contentPanel.add(invoicePanel, INVOICE);
		contentPanel.add(orderPanel, ORDER);
		//contentPanel.add(productPanel, PRODUCT);
		contentPanel.add(supplierPanel, SUPPLIER);
		contentPanel.add(userPanel, USER);
		
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
		//productPanel.addListener(listener);
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
		((CardLayout)(contentPanel.getLayout())).show(contentPanel, CUSTOMER);
	}

	@Override
	public void showInvoiceEditingScreen() {
		((CardLayout)(contentPanel.getLayout())).show(contentPanel, INVOICE);
	}

	@Override
	public void showOrderEditingScreen() {
		((CardLayout)(contentPanel.getLayout())).show(contentPanel, ORDER);
	}

	@Override
	public void showProductEditingScreen() {
		//((CardLayout)(contentPanel.getLayout())).show(contentPanel, PRODUCT);
		
	}

	@Override
	public void showSupplierEditingScreen() {
		((CardLayout)(contentPanel.getLayout())).show(contentPanel, SUPPLIER);
		
	}

	@Override
	public void showUserEditingScreen() {
		((CardLayout)(contentPanel.getLayout())).show(contentPanel, USER);
	}

	@Override
	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		supplierPanel.displaySuppliers(suppliers);
	}
	
	@Override
	public void showError(String errorMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInvoices(ArrayList<Invoice> invoices) {
		invoicePanel.updateInvoiceList(invoices);
	}

	@Override
	public void updateProducts(ArrayList<Product> products) {
		invoicePanel.updateProductList(products);
	}

	@Override
	public void updateCustomers(ArrayList<Customer> customers) {
		invoicePanel.updateCustomerList(customers);
		
	}

}
