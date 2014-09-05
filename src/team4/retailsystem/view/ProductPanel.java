package team4.retailsystem.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;

















import team4.retailsystem.model.User;


//import team4.retailsystem.view.CustomerPanel.CRUDListener;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProductPanel extends JPanel {
	
	private static final String NAME_FIELD = "name";
	private static final String NAME_COMBO = "combo"; 
	
	
	private JTextField nameTF;
	private JTextField costsTF;
	private JTextField markUpTF;
	private JTextField idTF;
	private JTextField stockLevelTF;
	private JTextField supplierTF;
	private JTextArea infoTextArea;
	private JComboBox<Object> supplierBox;
	private JLabel productPanelLabel;
	private JPanel productDetails;
	
	private JLabel productNameLabel;
	private JLabel productCostsLabel;
	private JLabel productMarkUpLabel;
	private JLabel productIdLabel;
	private JLabel productStockLevelLabel;
	private JLabel supplierLabel;

	private JButton editItemButton;
	private JButton removeItemButton;
	private JButton newproductButton;
	private JButton saveItemButton;
	private JButton cancelBtn;
	private JList<Object> productList;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JPanel panel_3;
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
//	private ArrayList<Supplier> suppliers = new ArrayList<Supplier>();
	private Supplier s;
	
	public ProductPanel() {

		initialiseComponents();
		addListeners();
		constructView();
		
	}

	public void initialiseComponents() {

		panel = new JPanel();
		productPanelLabel = new JLabel("Product Panel");
		productDetails = new JPanel();
		productDetails.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		productDetails.setBounds(10, 84, 570, 372);
		productNameLabel = new JLabel("Product name:");
		productNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		productMarkUpLabel = new JLabel("Product mark_up:");
		productMarkUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		productStockLevelLabel = new JLabel("Stock level:");
		productStockLevelLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		productIdLabel = new JLabel("product id:");
		productIdLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		productCostsLabel = new JLabel("product Costs:");
		productCostsLabel.setDisplayedMnemonic(KeyEvent.VK_ENTER);
		productCostsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		supplierLabel = new JLabel("Supplier");
		productList = new JList<Object>();
		supplierBox = new JComboBox<Object>();
		supplierTF = new JTextField();
		nameTF = new JTextField();
		markUpTF = new JTextField();
		idTF = new JTextField();
		stockLevelTF = new JTextField();
		costsTF = new JTextField();
		saveItemButton = new JButton("Save");
		newproductButton = new JButton("New product");
		editItemButton = new JButton("Edit product");
		removeItemButton = new JButton("Delete product");
		infoTextArea = new JTextArea();
		cancelBtn = new JButton("Cancel");
		scrollPane = new JScrollPane();
		scrollPane.setBounds(590, 85, 220, 504);
		panel_3 = new JPanel();
		panel_3.setBounds(10, 465, 570, 124);
		
	}

	public void addListeners() {
		
		productList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(productList.getSelectedValue() != null && !arg0.getValueIsAdjusting()){
					Product p = (Product) productList.getSelectedValue();
					Supplier s = p.getSupplier();
					
					infoTextArea.setText("");
					nameTF.setText(p.getName());
					costsTF.setText("" + p.getCost());
					markUpTF.setText("" + p.getMarkup());
					idTF.setText("" + p.getID());
					stockLevelTF.setText("" + p.getStockLevel());
					supplierTF.setText("" + p.getSupplier().getName());
					
					supplierBox.setSelectedItem(s);
					
				}
			}
			
		});

		newproductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newproductMode();
			}
		});

		editItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please select a product to edit");
				} 
				else {
					editproductMode();
				}
			}
		});

		removeItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please select a product to remove");
				} else if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete this product") == 0) {
					infoTextArea.setText(getNameTF()
							+ " has been removed from the database.");

					for (RetailViewListener r : listeners) {
						r.clickDeleteProduct(Integer.parseInt(getIDTF()));

					}
					setToViewMode();

				}
			}

		});

		saveItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getNameTF().isEmpty() || getCostsTF().isEmpty()
						|| getMarkUpTF().isEmpty() || getStockLevelTF().isEmpty()) {

					JOptionPane.showMessageDialog(null,
							"Please make sure all fields are filled in");
				}

				// checks for if id fields in empty == new product
				else if (getIDTF().isEmpty()) {

						// inform RetailViewListeners of the event, pass the
						// information.
						for (RetailViewListener r : listeners) {
							Supplier supplier = ((Supplier) supplierBox.getSelectedItem());
							r.clickCreateProduct(getNameTF(), Double.parseDouble(getCostsTF()),
									Double.parseDouble(getMarkUpTF()), Integer.parseInt(getStockLevelTF()), supplier);

						}

						infoTextArea.setText(getNameTF()
								+ "  is added to the database.");
						// getproductNameArrayList();
						setToViewMode();

					} else {
						for (RetailViewListener r : listeners) {
							r.clickUpdateProduct(Integer.parseInt((getIDTF())),
									getNameTF(), Double.parseDouble(getCostsTF()), Double.parseDouble(getMarkUpTF()),
									Integer.parseInt(getStockLevelTF()), getSupplierBox());

						}
						
						infoTextArea.setText(getNameTF()
								+ "'s details have been updated.");
						// getproductNameArrayList();
						//clearTextFields();
						setToViewMode();

					}
				setToViewMode();

			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setToViewMode();
				
				if(productList.getSelectedValue() != null){
					Product p = (Product) productList.getSelectedValue();
					
					infoTextArea.setText("");
					nameTF.setText(p.getName());
					costsTF.setText("" + p.getCost());
					markUpTF.setText("" + p.getMarkup());
					idTF.setText("" + p.getID());
					stockLevelTF.setText("" + p.getStockLevel());
					supplierTF.setText("" + p.getSupplier().getName());
				}
			}
		});
	}

	public void constructView() {
		setLayout(null);
		panel.setBounds(10, 11, 800, 62);
		add(panel);
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		newproductButton.setFont(new Font("Arial", Font.BOLD, 12));
		newproductButton.setBounds(10, 11, 125, 34);
		panel.add(newproductButton);

		editItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		editItemButton.setBounds(146, 11, 125, 34);
		panel.add(editItemButton);

		removeItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		removeItemButton.setBounds(281, 11, 133, 34);
		panel.add(removeItemButton);

		productPanelLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		productPanelLabel.setBounds(620, 13, 170, 29);
		panel.add(productPanelLabel);

		productDetails
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		productDetails.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "product details", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		add(productDetails);
		productDetails.setLayout(null);

		productNameLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		productNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		productNameLabel.setBounds(33, 36, 148, 20);
		productDetails.add(productNameLabel);

		productCostsLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		productCostsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		productCostsLabel.setBounds(33, 92, 148, 20);
		productDetails.add(productCostsLabel);

		nameTF.setBorder(BorderFactory.createTitledBorder(""));
		nameTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nameTF.setColumns(10);
		nameTF.setBounds(237, 30, 323, 27);
		productDetails.add(nameTF);
		
		markUpTF.setBorder(BorderFactory.createTitledBorder(""));
		markUpTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		markUpTF.setColumns(10);
		markUpTF.setBounds(237, 144, 323, 27);
		productDetails.add(markUpTF);

		idTF.setEditable(false);
		idTF.setBorder(BorderFactory.createTitledBorder(""));
		idTF.setFont(new Font("Tahoma", Font.PLAIN, 13));
		idTF.setColumns(10);
		idTF.setBounds(237, 201, 323, 27);
		productDetails.add(idTF);

		stockLevelTF.setBorder(BorderFactory.createTitledBorder(""));
		stockLevelTF.setFont(new Font("Arial", Font.PLAIN, 12));
		stockLevelTF.setColumns(10);
		stockLevelTF.setBounds(237, 258, 323, 27);
		productDetails.add(stockLevelTF);
		
		supplierTF.setBorder(BorderFactory.createTitledBorder(""));
		supplierTF.setFont(new Font("Arial", Font.PLAIN, 12));
		supplierTF.setColumns(10);
		supplierTF.setBounds(237, 315, 323, 27);
		productDetails.add(supplierTF);

		productMarkUpLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		productMarkUpLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		productMarkUpLabel.setBounds(33, 148, 148, 20);
		productDetails.add(productMarkUpLabel);

		productIdLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		productIdLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		productIdLabel.setBounds(33, 204, 148, 20);
		productDetails.add(productIdLabel);

		productStockLevelLabel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		productStockLevelLabel
				.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		productStockLevelLabel.setBounds(33, 260, 148, 20);
		productDetails.add(productStockLevelLabel);

		costsTF.setBorder(BorderFactory.createTitledBorder(""));
		costsTF.setBounds(237, 87, 323, 27);
		productDetails.add(costsTF);
		
		supplierLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		supplierLabel.setBounds(33, 316, 148, 20);
		productDetails.add(supplierLabel);
		
		supplierBox.setBounds(237, 315, 323, 27);
		productDetails.add(supplierBox);

		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		add(panel_3);
		panel_3.setLayout(null);

		infoTextArea.setEditable(false);
		infoTextArea.setBackground(SystemColor.inactiveCaptionBorder);
		infoTextArea.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "Event window",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		infoTextArea.setBounds(10, 11, 550, 46);
		panel_3.add(infoTextArea);
		infoTextArea.setColumns(10);

		saveItemButton.setFont(new Font("Arial", Font.BOLD, 12));
		saveItemButton.setBounds(354, 68, 98, 34);
		panel_3.add(saveItemButton);
		cancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
		cancelBtn.setBounds(462, 68, 98, 34);
		panel_3.add(cancelBtn);

		scrollPane.setBorder(null);
		scrollPane.setViewportBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "product list",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(scrollPane);

		productList.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setViewportView(productList);

		setToViewMode();
	}

	public void addPanel(JPanel panel, GridBagLayout gbl, GridBagConstraints gbc) {
		gbl.setConstraints(panel, gbc);
		add(panel);
	}

	public void addScrollPane(JScrollPane panel, GridBagLayout gbl,
			GridBagConstraints gbc) {
		gbl.setConstraints(panel, gbc);
		add(panel);
	}

	public void addPanelName(String panelName, JScrollPane panel) {
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setBorder(BorderFactory.createTitledBorder(panelName));
	}

	public void updateProductList(ArrayList<Product> products) {
		productList.setListData(products.toArray());
		
		Product currentlySelected = null;
		int previousCount = productList.getModel().getSize();
		
		if(previousCount > 0){
			currentlySelected = (Product)productList.getSelectedValue();
		}

		productList.setListData(products.toArray());
		int newCount = productList.getModel().getSize();
		
		if( productList.getModel().getSize() > 0 ){
			if(currentlySelected!=null && (newCount == previousCount)){
				productList.setSelectedValue(currentlySelected, true);
			}
			else if( (newCount > previousCount) && (previousCount>0)){
				productList.setSelectedValue(productList.getModel().getElementAt(newCount-1), true);
			}
			else{
				productList.setSelectedValue(productList.getModel().getElementAt(0), true);
			}
		} else {
			clearTextFields();
		}
	}
	
	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		supplierBox.setModel(new DefaultComboBoxModel(suppliers.toArray()));
		
	}
	
	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	// default panel view
	public void setToViewMode() {

		//clearTextFields();

		productList.setEnabled(true);

		nameTF.setEditable(false);
		costsTF.setEditable(false);
		idTF.setEditable(false);
		markUpTF.setEditable(false);
		stockLevelTF.setEditable(false);
		supplierTF.setEditable(false);

		supplierBox.setVisible(false);
		supplierTF.setVisible(true);
		newproductButton.setEnabled(true);
		removeItemButton.setEnabled(true);
		editItemButton.setEnabled(true);
		saveItemButton.setVisible(false);
		cancelBtn.setVisible(false);
	}

	public void clearTextFields() {
		
		nameTF.setText("");
		costsTF.setText("");
		idTF.setText("");;
		markUpTF.setText("");
		stockLevelTF.setText("");
		supplierTF.setText("");


	}

	// panel view edit product
	public void editproductMode() {

		infoTextArea.setText("");
		productList.setEnabled(false);

		nameTF.setEditable(false);
		idTF.setEditable(false);
		markUpTF.setEditable(true);
		costsTF.setEditable(true);
		stockLevelTF.setEditable(true);

		// disable/enable appropriate buttons
		removeItemButton.setEnabled(false);
		newproductButton.setEnabled(false);
		saveItemButton.setVisible(true);
		cancelBtn.setVisible(true);
		supplierBox.setVisible(true);
		supplierTF.setVisible(false);
	}

	// panel view new product
	public void newproductMode() {
		clearTextFields();

		infoTextArea.setText("");
		productList.setEnabled(false);
		
		nameTF.setEditable(true);
		idTF.setEditable(false);
		markUpTF.setEditable(true);
		nameTF.setEditable(true);
		costsTF.setEditable(true);
		stockLevelTF.setEditable(true);

		editItemButton.setEnabled(false);
		removeItemButton.setEnabled(false);
		saveItemButton.setVisible(true);
		cancelBtn.setVisible(true);
		supplierBox.setVisible(true);
		supplierTF.setVisible(false);
	}

	public void updateUser(User user) {
		//
		if (user.getAuthorizationLevel() == 1) {
			newproductButton.setVisible(false);
			removeItemButton.setVisible(false);
			editItemButton.setVisible(false);
		} else {
			newproductButton.setVisible(true);
			removeItemButton.setVisible(true);
			editItemButton.setVisible(true);
		}
	}

	// A method that clears temp fields on logout.
	public void logout() {
		String empty = "";
		setNameTF(empty);
		setCostsTF(empty);
		setMarkUpTF(empty);
		setIDTF(empty);
		stockLevelTF.setText(empty);
		
		setToViewMode();

		if(productList.getModel().getSize() > 0){
			productList.setSelectedIndex(0);			
		}
	}

	// **********getters and setters***************

	public String getNameTF() {
		return nameTF.getText();
	}

	public String getCostsTF() {
		return costsTF.getText();
	}

	public String getMarkUpTF() {
		return markUpTF.getText();
	}

	public String getIDTF() {
		return idTF.getText();
	}

	public String getStockLevelTF() {
		return stockLevelTF.getText();
	}

	public void setNameTF(String newName) {
		nameTF.setText(newName);
	}

	public void setCostsTF(String newCosts) {
		costsTF.setText(newCosts);
	}

	public void setMarkUpTF(String newMarkUp) {
		markUpTF.setText(newMarkUp);
	}

	public void setIDTF(String newID) {
		idTF.setText(newID);
	}

	public Supplier getSupplierBox() {
		return (Supplier) supplierBox.getSelectedItem();
	}
}
