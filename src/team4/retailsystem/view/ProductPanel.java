package team4.retailsystem.view;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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
import team4.retailsystem.view.CustomerPanel.CRUDListener;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class ProductPanel extends JPanel {

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	private static final String NAME_FIELD = "name";
	private static final String NAME_COMBO = "combo";
	private JPanel  panel;
	private JLabel nameLabel, idLabel, costLabel, markupLabel, stockLevelLabel,
			supplierLabel;
	private JComboBox supplierCombo;
	private JTextField nameField, idField, costField, markupField,
			stockLevelField;
	private JButton submit, back, remove, edit, save, btnNewButton_1,
			clearButton, btnNewButton_3;
	private DecimalFormat df = new DecimalFormat("0.00");
	private JScrollPane scrollPane, scrollPane_1;
	private JList productList;

	public ProductPanel() {
		initialise();
		construct();
		addListeners();
	}

	public void initialise() {
		btnNewButton_1 = new JButton("Cancel");
		clearButton = new JButton("Clear Fields");
		btnNewButton_3 = new JButton("Submit");
		submit = new JButton("Add new Product");
		remove = new JButton("Remove Product");
		edit = new JButton("Edit Product");
		back = new JButton("Clear fields");
		save = new JButton("Save");
		panel = new JPanel();
		idLabel = new JLabel("Product Id: ");
		costField = new JTextField();
		idField = new JTextField();
		markupField = new JTextField();
		stockLevelField = new JTextField();
		supplierCombo = new JComboBox();
		costLabel = new JLabel("Product cost: ");
		markupLabel = new JLabel("Markup %: ");
		supplierLabel = new JLabel("Supplier: ");
		stockLevelLabel = new JLabel("Stock level: ");
		nameLabel = new JLabel("Product name: ");
		nameField = new JTextField();
		scrollPane = new JScrollPane();
		scrollPane_1 = new JScrollPane();
		
		productList = new JList();
		scrollPane_1.setViewportView(productList);
	}

	public void construct() {
		this.setVisible(true);
		setLayout(null);
		add(submit);
		submit.setBounds(6, 35, 187, 28);
		add(remove);
		remove.setBounds(420, 35, 179, 28);
		panel.setBounds(6, 75, 593, 231);
		add(panel);
		panel.setLayout(null);
		idLabel.setBounds(19, 12, 72, 16);
		panel.add(idLabel);
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idField.setBounds(103, 6, 325, 28);
		panel.add(idField);
		costField.setBounds(103, 78, 325, 28);
		panel.add(costField);
		markupField.setBounds(103, 118, 325, 28);
		panel.add(markupField);
		stockLevelField.setBounds(103, 158, 325, 28);
		panel.add(stockLevelField);
		supplierCombo.setBounds(103, 198, 217, 28);
		panel.add(supplierCombo);
		costLabel.setBounds(-1, 78, 103, 28);
		panel.add(costLabel);
		costLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		markupLabel.setBounds(-74, 118, 176, 28);
		panel.add(markupLabel);
		markupLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		supplierLabel.setBounds(-125, 197, 217, 28);
		panel.add(supplierLabel);
		supplierLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		stockLevelLabel.setBounds(-126, 158, 217, 28);
		panel.add(stockLevelLabel);
		stockLevelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setBounds(-11, 38, 116, 28);
		panel.add(nameLabel);
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameField.setBounds(103, 38, 325, 28);
		panel.add(nameField);
		scrollPane.setBounds(810, 3, -188, 303);
		add(scrollPane);
		scrollPane_1.setBounds(611, 6, 187, 471);
		add(scrollPane_1);
		add(edit);
		edit.setBounds(205, 35, 217, 28);
		
		back.setBounds(10, 283, 80, 23);
		
		save.setBounds(16, 321, 117, 29);
		add(save);
		btnNewButton_1.setBounds(150, 321, 117, 29);
		add(btnNewButton_1);
		clearButton.setBounds(270, 321, 117, 29);
		add(clearButton);
		btnNewButton_3.setBounds(399, 321, 117, 29);
		add(btnNewButton_3);
	}

	public void addListeners() {
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setToViewMode();
			}
		});

		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setToViewMode();
			}
		});
		remove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// inform listeners of the delete customer event
				for (RetailViewListener r : listeners) {
					r.clickDeleteProduct(Integer.parseInt(idField.getText()));
				}
			}
		});
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTextFields();
			}
		});

	}

	// private class NameBoxListener implements ActionListener {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// Product p = ((Product) nameCombo.getSelectedItem());
	// nameField.setText(p.getName());
	// idField.setText("" + p.getID());
	// costField.setText(df.format(p.getCost()));
	// markupField.setText(df.format(p.getMarkup()));
	// stockLevelField.setText(Integer.toString(p.getStockLevel()));
	// supplierCombo.setSelectedItem(p.getSupplier());
	// }
	//
	// }
	//
	// class ButtonListener implements ActionListener {
	// public void actionPerformed(ActionEvent e) {
	// if (e.getActionCommand().equals("Clear fields")) {
	// clearTextFields();
	// }
	//
	// // save new customer if conditions are met
	// if (e.getActionCommand().equals("Save new Product")) {
	// if (nameField.getText().isEmpty()
	// || costField.getText().isEmpty()
	// || markupField.getText().isEmpty()
	// || stockLevelField.getText().isEmpty()) {
	//
	// // Checks for blank fields
	// Warning w = new Warning();
	// w.blankWarning();
	// } else {
	//
	// // inform RetailViewListeners of the event, pass the
	// // information.
	// for (RetailViewListener r : listeners) {
	// String name = nameField.getText();
	// double cost = Double.parseDouble(costField.getText());
	// double markup = Double.parseDouble(markupField
	// .getText());
	// int level = Integer.parseInt(stockLevelField.getText());
	// Supplier supplier = (Supplier) supplierCombo
	// .getSelectedItem();
	// r.clickCreateProduct(name, cost, markup, level,
	// supplier);
	// }
	//
	// }
	//
	// }
	// // when back button pressed
	// if (e.getActionCommand().equals("back")) {
	// setToViewMode();
	// }
	//
	// // when remove button pressed
	// if (e.getActionCommand().equals("Remove Product")) {
	//
	// // inform listeners of the delete customer event
	// for (RetailViewListener r : listeners) {
	// r.clickDeleteProduct(Integer.parseInt(idField.getText()));
	// }
	//
	// }
	//
	// // when edit button pressed
	// if (e.getActionCommand().equals("Edit Product")) {
	// editProductMode();
	//
	// }
	//
	// if (e.getActionCommand().equals("Save Changes")) {
	// if (nameField.getText().isEmpty()
	// || idField.getText().isEmpty()
	// || costField.getText().isEmpty()
	// || markupField.getText().isEmpty()
	// || stockLevelField.getText().isEmpty()) {
	//
	// Warning w = new Warning();
	// w.blankWarning();
	// } else {
	// // inform RetailViewListeners of the event, pass the
	// // information.
	// for (RetailViewListener r : listeners) {
	// int productId = Integer.parseInt(idField.getText());
	// String name = nameField.getText();
	// double cost = Double.parseDouble(costField.getText());
	// double markup = Double.parseDouble(markupField
	// .getText());
	// int level = Integer.parseInt(stockLevelField.getText());
	// Supplier supplier = (Supplier) supplierCombo
	// .getSelectedItem();
	// r.clickUpdateProduct(productId, name, cost, markup,
	// level, supplier);
	// }
	// setToViewMode();
	// }
	// }
	// }
	// }

	// public void newProductMode() {
	//
	// // switch the combobox for the text field
	// ((CardLayout) (namePanel.getLayout())).show(namePanel, NAME_FIELD);
	//
	// clearTextFields();
	//
	// idField.setEditable(false);
	// costField.setEditable(true);
	// markupField.setEditable(true);
	// stockLevelField.setEditable(true);
	// supplierCombo.setEnabled(true);
	//
	// submit.setText("Save new Product");
	// remove.setEnabled(false);
	// edit.setEnabled(false);
	// back.setText("back");
	//
	// }
	//
	// public void editProductMode() {
	//
	// // switch the text field for the combobox
	// ((CardLayout) (namePanel.getLayout())).show(namePanel, NAME_FIELD);
	//
	// idField.setEditable(false);
	// costField.setEditable(true);
	// markupField.setEditable(true);
	// stockLevelField.setEditable(true);
	// supplierCombo.setEnabled(true);
	//
	// submit.setEnabled(false);
	// remove.setEnabled(false);
	// edit.setText("Save Changes");
	// back.setText("back");
	//
	// }
	//
	public void setToViewMode() {
		// switch the text field for the combobox
		
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

	public void clearTextFields() {
		nameField.setText("");
		idField.setText("");
		costField.setText("");
		markupField.setText("");
		stockLevelField.setText("");
		if (supplierCombo.getItemCount() > 0) {
			supplierCombo.setSelectedIndex(supplierCombo.getItemCount() - 1);
		}
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	public void updateProductList(ArrayList<Product> products) {
	productList.setListData(products.toArray());	
		
	}

	public void updateSupplierList(ArrayList<Supplier> suppliers) {
		supplierCombo.setModel(new DefaultComboBoxModel(suppliers.toArray()));
		
	}

	/**
	 * A method that clears temp fields on logout.
	 */
	public void logout() {
		setToViewMode();
	}
}
