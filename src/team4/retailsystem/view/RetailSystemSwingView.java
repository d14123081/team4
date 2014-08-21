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
import javax.swing.JOptionPane;
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
	private static final int FRAME_WIDTH = 820, FRAME_HEIGHT = 650;
	
	//panel names for CardLayout managers
	private final static String LOGIN = "login";
	private final static String MAIN = "main";
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
	JButton logoutButton;
	
	private Container contentPane;//The main content container for the frame, holds everything else
	private JPanel panelPane;//holds all (except login) panels in CardLayout
	private JPanel buttonPanel;//holds the row of buttons at the top
	private JPanel contentPanel;//holds whichever of the 'panels' it's told to by CardLayout manager
	
	//The main panel views, currently one for each aspect of the system.
	private LogInPanel loginPanel;
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
		panelPane = new JPanel();
		buttonPanel = new JPanel();
		contentPanel = new JPanel();
		
		customerButton = new JButton("Customers");
		invoiceButton = new JButton("Invoices");
		orderButton = new JButton("Orders");
		productButton = new JButton("Products");
		supplierButton = new JButton("Suppliers");
		userButton = new JButton("Users");
		logoutButton = new JButton("Logout");
		
		loginPanel = new LogInPanel();
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
		
		contentPane.setLayout(new CardLayout());
		panelPane.setLayout(new BoxLayout(panelPane, BoxLayout.Y_AXIS));
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonPanel.add(customerButton);
		buttonPanel.add(invoiceButton);
		buttonPanel.add(orderButton);
		buttonPanel.add(productButton);
		buttonPanel.add(supplierButton);
		buttonPanel.add(userButton);
		buttonPanel.add(logoutButton);
		//buttonPanel.add(userPanel);
		
		contentPanel.setLayout(new CardLayout());
		contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		contentPanel.add(customerPanel, CUSTOMER);
		contentPanel.add(invoicePanel, INVOICE);
		contentPanel.add(orderPanel, ORDER);
		//contentPanel.add(productPanel, PRODUCT);
		contentPanel.add(supplierPanel, SUPPLIER);
		contentPanel.add(userPanel, USER);
		
		panelPane.add(buttonPanel);
		panelPane.add(contentPanel);
		
		contentPane.add(loginPanel, LOGIN);
		contentPane.add(panelPane, MAIN);
		
		setContentPane(contentPane);
		setResizable(false);
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
		
		logoutButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showLoginScreen();
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
		loginPanel.addListener(listener);
	}

	@Override
	public void showLoginScreen() {
		((CardLayout)(contentPane.getLayout())).show(contentPane, LOGIN);
	}

	@Override
	public void showMainMenuScreen(User user) {
		((CardLayout)(contentPane.getLayout())).show(contentPane, MAIN);
		System.out.println("showMainMenu() user: "+user.getUsername());
	}

	@Override
	public void showCustomerEditingScreen() {
		((CardLayout)(panelPane.getLayout())).show(panelPane, CUSTOMER);
	}

	@Override
	public void showInvoiceEditingScreen() {
		((CardLayout)(panelPane.getLayout())).show(panelPane, INVOICE);
	}

	@Override
	public void showOrderEditingScreen() {
		((CardLayout)(panelPane.getLayout())).show(panelPane, ORDER);
	}

	@Override
	public void showProductEditingScreen() {
		//((CardLayout)(panelPane.getLayout())).show(panelPane, PRODUCT);
		
	}

	@Override
	public void showSupplierEditingScreen() {
		((CardLayout)(panelPane.getLayout())).show(panelPane, SUPPLIER);
		
	}

	@Override
	public void showUserEditingScreen() {
		((CardLayout)(panelPane.getLayout())).show(panelPane, USER);
	}

	@Override
	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		supplierPanel.updateSupplierList(suppliers);
	}
	
	@Override
	public void showError(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage);
		
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
		customerPanel.updateCustomerList(customers);
	}

	@Override
	public void updateUsers(ArrayList<User> users) {
		userPanel.updateUserList(users);
		
	}

}
