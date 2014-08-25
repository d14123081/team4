package team4.retailsystem.view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.Supplier;
import team4.retailsystem.model.User;
import team4.retailsystem.view.CustomerPanel.CRUDListener;

public class productPanel extends JPanel {

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
	private static final String NAME_FIELD = "name";
	private static final String NAME_COMBO = "combo";
	
	private JPanel namePanel;
	
	private JLabel nameLabel, idLabel, costLabel, markupLabel, stockLevelLabel,supplierLabel;
	private JComboBox nameCombo, supplierCombo;
	private JTextField nameField, idField, costField, markupField, stockLevelField;
	private JButton submit, back, remove, edit;
	
	private DecimalFormat df = new DecimalFormat("0.00");
	
	public productPanel(){
		
		this.setVisible(true);
		this.setLayout(new GridLayout(8, 2, 15, 10));
		
		namePanel = new JPanel();
		namePanel.setLayout(new CardLayout());
		
		nameCombo = new JComboBox();
		supplierCombo = new JComboBox();
		nameCombo.addActionListener(new NameBoxListener());
		
		nameLabel = new JLabel("Product name: ");
		idLabel = new JLabel("Product Id: ");
		costLabel = new JLabel("Product cost: ");
		markupLabel = new JLabel("Markup %: ");
		stockLevelLabel = new JLabel("Stock level: ");
		supplierLabel = new JLabel("Supplier: ");
		
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		costLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		markupLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		stockLevelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		supplierLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		nameField =new JTextField();
		idField = new JTextField();
		costField =new JTextField();
		markupField =new JTextField();
		stockLevelField =new JTextField();
		
		submit = new JButton("Create new Product");
		submit.addActionListener(new ButtonListener());
		 
		remove = new JButton("Remove Product");
		remove.addActionListener(new ButtonListener());
		 

		edit = new JButton("Edit Product");
		edit.addActionListener(new ButtonListener());
		 
		back = new JButton("Clear fields");
		back.addActionListener(new ButtonListener());
		 
		//adding to components to panel
		namePanel.add(nameCombo,NAME_COMBO);
		namePanel.add(nameField,NAME_FIELD);
		
		this.add(nameLabel);
		this.add(namePanel);
		
		this.add(idLabel);
		this.add(idField);
		this.add(costLabel);
		this.add(costField);
		this.add(markupLabel);
		this.add(markupField);
		this.add(stockLevelLabel);
		this.add(stockLevelField);
		this.add(supplierLabel);
		this.add(supplierCombo);
		
		this.add(submit);
		this.add(remove);
		this.add(edit);
		this.add(back);
		
		setToViewMode();
	}
	
	private class NameBoxListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Product p = ((Product)nameCombo.getSelectedItem());
			nameField.setText(p.getName());
			idField.setText(""+p.getID());
			costField.setText(df.format(p.getCost()));
			markupField.setText(df.format(p.getMarkup()));
			stockLevelField.setText(Integer.toString(p.getStockLevel()));
			supplierCombo.setSelectedItem(p.getSupplier());
		}
		
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Clear fields")) {
				clearTextFields();
			}
			if (e.getActionCommand().equals("Create new Product"))
				newProductMode();

			// save new customer if conditions are met
			if (e.getActionCommand().equals("Save new Product")) {
				if (nameField.getText().isEmpty()
						|| costField.getText().isEmpty()
						|| markupField.getText().isEmpty()
						|| stockLevelField.getText().isEmpty()) {

					// Checks for blank fields
					Warning w = new Warning();
					w.blankWarning();
				} else {

					// inform RetailViewListeners of the event, pass the
					// information.
					for (RetailViewListener r : listeners) {
						String name = nameField.getText();
						double cost = Double.parseDouble(costField.getText());
						double markup = Double.parseDouble(markupField
								.getText());
						int level = Integer.parseInt(stockLevelField.getText());
						Supplier supplier = (Supplier) supplierCombo
								.getSelectedItem();
						r.clickCreateProduct(name, cost, markup, level,
								supplier);
					}

				}

			}
			// when back button pressed
			if (e.getActionCommand().equals("back")) {
				setToViewMode();
			}

			// when remove button pressed
			if (e.getActionCommand().equals("Remove Product")) {

				// inform listeners of the delete customer event
				for (RetailViewListener r : listeners) {
					r.clickDeleteProduct(Integer.parseInt(idField.getText()));
				}

			}

			// when edit button pressed
			if (e.getActionCommand().equals("Edit Product")) {
				editProductMode();

			}

			if (e.getActionCommand().equals("Save Changes")) {
				if (nameField.getText().isEmpty()
						|| idField.getText().isEmpty()
						|| costField.getText().isEmpty()
						|| markupField.getText().isEmpty()
						|| stockLevelField.getText().isEmpty()) {

					Warning w = new Warning();
					w.blankWarning();
				} else {
					// inform RetailViewListeners of the event, pass the
					// information.
					for (RetailViewListener r : listeners) {
						int productId = Integer.parseInt(idField.getText());
						String name = nameField.getText();
						double cost = Double.parseDouble(costField.getText());
						double markup = Double.parseDouble(markupField
								.getText());
						int level = Integer.parseInt(stockLevelField.getText());
						Supplier supplier = (Supplier) supplierCombo
								.getSelectedItem();
						r.clickUpdateProduct(productId, name, cost, markup,
								level, supplier);
					}
					setToViewMode();
				}
			}
		}
	}
	
	public void newProductMode() {

		// switch the combobox for the text field
		((CardLayout) (namePanel.getLayout())).show(namePanel, NAME_FIELD);

		clearTextFields();

		idField.setEditable(false);
		costField.setEditable(true);
		markupField.setEditable(true);
		stockLevelField.setEditable(true);
		supplierCombo.setEnabled(true);

		submit.setText("Save new Product");
		remove.setEnabled(false);
		edit.setEnabled(false);
		back.setText("back");

	}
	
	public void editProductMode() {

		// switch the text field for the combobox
		((CardLayout) (namePanel.getLayout())).show(namePanel, NAME_FIELD);

		idField.setEditable(false);
		costField.setEditable(true);
		markupField.setEditable(true);
		stockLevelField.setEditable(true);
		supplierCombo.setEnabled(true);

		submit.setEnabled(false);
		remove.setEnabled(false);
		edit.setText("Save Changes");
		back.setText("back");

	}
	
	public void setToViewMode() {

		// switch the text field for the combobox
		((CardLayout) (namePanel.getLayout())).show(namePanel, NAME_COMBO);
		if (nameCombo.getItemCount() > 0) {
			nameCombo.setSelectedIndex(nameCombo.getItemCount() - 1);
		}

		idField.setEditable(false);
		costField.setEditable(false);
		markupField.setEditable(false);
		stockLevelField.setEditable(false);
		supplierCombo.setEnabled(false);
		
		submit.setEnabled(true);
		remove.setEnabled(true);
		edit.setEnabled(true);
		edit.setText("Edit Product");
		submit.setText("Create new Product");
		back.setText("Clear fields");
	}
	
	public void clearTextFields(){	 
		nameField.setText("");
		idField.setText("");
		costField.setText("");
		markupField.setText("");
		stockLevelField.setText("");
		if (supplierCombo.getItemCount() > 0) {
			supplierCombo.setSelectedIndex(supplierCombo.getItemCount() - 1);
		}
	}
	public void addListener(RetailViewListener r){
		listeners.add(r);
	
		}
	
	
	public void updateProductList(ArrayList<Product> products){
		nameCombo.setModel(new DefaultComboBoxModel(products.toArray()));
		if (nameCombo.getItemCount() > 0) {
			nameCombo.setSelectedIndex(nameCombo.getSelectedIndex());
		}
	}
	
	private void updateSupplierList(ArrayList<Supplier> suppliers){
		supplierCombo.setModel(new DefaultComboBoxModel(suppliers.toArray()));
		if (nameCombo.getItemCount() > 0) {
			nameCombo.setSelectedIndex(nameCombo.getSelectedIndex());
		}
		
	}
	
		public void updateProductUser(User user) { 
			
			if(user.getAuthorizationLevel() == User.NORMAL_USER) { 
				submit.setVisible(false); 
				edit.setVisible(false); 
				remove.setVisible(false); 
				back.setVisible(false); 
				} 
			
			else if (user.getAuthorizationLevel() == User.ADMINISTRATOR){
				submit.setVisible(true);
				edit.setVisible(true);
				remove.setVisible(true);
				back.setVisible(true);
				
			}
		}
	}
		
	
	
	
		

