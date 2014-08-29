package team4.retailsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import team4.retailsystem.model.Customer;
import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.LineItem;
import team4.retailsystem.model.Product;
import team4.retailsystem.model.User;

import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.SpringLayout;

public class InvoicePanel extends JPanel {
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();

	private Database database;
	private JList productList;
	private JTable invoiceTable;
	private JTextField totalCostField;
	private JComboBox customerComboBox;
	private JTextField idField;
	private JCheckBox checkboxNew;
	private DefaultTableModel tableModel;
	private JButton btnCancel, btnInvoices, btnAdd, btnDelRow;
	private JLabel lblTotalCost, lblInvoiceId, lblCustomer;
	private JPanel invoiceListPanel, invoicePanel;
	private JScrollPane invoiceScrollPane, productScrollPane;
	private DecimalFormat df = new DecimalFormat("0.00");
	private UtilDateModel dateModel;
	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;
	private Calendar today = Calendar.getInstance();

	public InvoicePanel() {
		initialiseComponents();
		addListeners();
		constructView();
	}

	public void initialiseComponents() {
		// Initialise all components
		btnInvoices = new JButton("Invoices");
		btnAdd = new JButton("Submit");
		btnCancel = new JButton("Cancel");
		btnDelRow = new JButton("Remove Row");
		checkboxNew = new JCheckBox("New");
		lblTotalCost = new JLabel("Total Cost:");
		lblInvoiceId = new JLabel("Invoice ID:");
		invoiceScrollPane = new JScrollPane();
		customerComboBox = new JComboBox();
		customerComboBox.setEnabled(false);
		database = Database.getInstance();
		totalCostField = new JTextField();
		totalCostField.setEditable(false);
		invoiceListPanel = new JPanel();
		invoicePanel = new JPanel();
		invoiceTable = new JTable();
		idField = new JTextField();
		tableModel = (DefaultTableModel) invoiceTable.getModel();
		productScrollPane = new JScrollPane();
		productList = new JList();
		productScrollPane.setViewportView(productList);
		dateModel = new UtilDateModel();
		dateModel.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
				today.get(Calendar.DATE));
		dateModel.setSelected(true);
		datePanel = new JDatePanelImpl(dateModel);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.getJFormattedTextField().setEnabled(false);
		lblCustomer = new JLabel("Customer:");
		invoiceTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Product ID", "Product Name", "Quantity" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

			@Override
			public void setValueAt(Object val, int row, int column) {
				Product p = database.getProduct((int) invoiceTable.getValueAt(
						row, column - 2));
				if (val instanceof Number && ((Number) val).doubleValue() > 0
						&& ((Number) val).intValue() <= p.getStockLevel()) {
					super.setValueAt(val, row, column);
				}
			}
		});
	}

	public void constructView() {
		// Creates the layout of GUI
		setLayout(null);
		
		invoiceTable.getTableHeader().setReorderingAllowed(false);
		invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		invoiceListPanel.setLayout(null);
		invoiceListPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		invoiceListPanel.setBounds(626, 11, 184, 578);
		add(invoiceListPanel);
		productScrollPane.setBounds(10, 11, 164, 556);
		invoiceListPanel.add(productScrollPane);
		invoicePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		invoicePanel.setBounds(10, 11, 617, 578);
		add(invoicePanel);
		invoicePanel.setLayout(null);
		customerComboBox.setBounds(244, 7, 156, 23);
		invoicePanel.add(customerComboBox);
		invoiceScrollPane.setBounds(10, 37, 597, 427);
		invoicePanel.add(invoiceScrollPane);
		invoiceTable.getColumnModel().getColumn(0).setResizable(false);
		invoiceTable.getColumnModel().getColumn(1).setResizable(false);
		invoiceTable.getColumnModel().getColumn(2).setResizable(false);
		invoiceScrollPane.setViewportView(invoiceTable);
		totalCostField.setBounds(484, 475, 123, 20);
		invoicePanel.add(totalCostField);
		totalCostField.setColumns(10);
		lblTotalCost.setBounds(422, 478, 63, 14);
		invoicePanel.add(lblTotalCost);
		lblInvoiceId.setBounds(422, 11, 63, 14);
		invoicePanel.add(lblInvoiceId);
		idField.setEditable(false);
		idField.setBounds(492, 8, 115, 20);
		invoicePanel.add(idField);
		idField.setColumns(10);
		checkboxNew.setBounds(10, 7, 52, 23);
		invoicePanel.add(checkboxNew);
		btnInvoices.setBounds(68, 7, 100, 23);
		invoicePanel.add(btnInvoices);
		btnAdd.setBounds(402, 546, 100, 23);
		invoicePanel.add(btnAdd);
		btnCancel.setBounds(507, 546, 100, 23);
		invoicePanel.add(btnCancel);
		btnDelRow.setBounds(20, 474, 115, 23);
		invoicePanel.add(btnDelRow);
		datePicker.setBounds(204, 475, 164, 33);
		invoicePanel.add(datePicker);
		lblCustomer.setBounds(178, 9, 98, 19);
		invoicePanel.add(lblCustomer);
		
	}

	public void addListeners() {
		// Adds listeners to components
		invoiceTable.getModel().addTableModelListener(new TableModelListener()
		{	
			@Override
			public void tableChanged(TableModelEvent e)
			{
				double cost = 0.0;
				
				for(int i = 0; i < tableModel.getRowCount(); i++)
				{
					Product p = database.getProduct((int)invoiceTable.getValueAt(i, 0));
					cost += p.getPrice() * (int)invoiceTable.getValueAt(i,  2);
				}
				totalCostField.setText(df.format(cost));
			}
		});
		
		// Handles the submit button
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If new invoice + items added to invoice
				if (checkboxNew.isSelected() && tableModel.getRowCount() > 0) {
					Customer c = (Customer) customerComboBox.getSelectedItem();
					Date d = (Date) datePicker.getModel().getValue();
					ArrayList<LineItem> lineitems = new ArrayList<>();
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						int productId = (int) invoiceTable.getValueAt(i, 0);
						int quantity = (int) invoiceTable.getValueAt(i, 2);
						lineitems.add(new LineItem(productId, quantity));
					}
					for (RetailViewListener r : listeners) {
						r.clickCreateInvoice(lineitems, c, d);
					}
					logout();
				} // If updating invoice
				else if (!checkboxNew.isSelected()
						&& tableModel.getRowCount() > 0) {
					int id = Integer.parseInt(idField.getText());
					Customer c = (Customer) customerComboBox.getSelectedItem();
					Date d = (Date) datePicker.getModel().getValue();
					ArrayList<LineItem> lineitems = new ArrayList<>();
					for (int i = 0; i < tableModel.getRowCount(); i++) {
						int productId = (int) invoiceTable.getValueAt(i, 0);
						int quantity = (int) invoiceTable.getValueAt(i, 2);
						lineitems.add(new LineItem(productId, quantity));
					}
					for (RetailViewListener r : listeners) {
						r.clickUpdateInvoice(id, lineitems, c, d);
					}
					logout();
				} // If neither updating nor creating - throw error
				else {
					showError("Incomplete invoice");
				}

			}
		});

		btnInvoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//popup list of invoices
			}
		});

		// Handles the remove row button
		btnDelRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean delRow = false;
				for (int i = 0; i < invoiceTable.getRowCount(); i++) {
					if (invoiceTable.isRowSelected(i)) {
						tableModel.removeRow(i);
						invoiceTable.clearSelection();
						delRow = true;
						break;
					}
				}
				if (!delRow) {
					showError("Select a row to delete");
				}
			}
		});

		// Handles the cancel button
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});
		
		

		// Handles a click event on product list
		productList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// If user isn't updating nor creating a new invoice, throw
				// error
				if (!checkboxNew.isSelected() && tableModel.getRowCount() < 1) {
					showError("You must either create a new invoice or append to an existing invoice");
				} else { // If table is empty
					if (tableModel.getRowCount() == 0) {
						// add row, [ product id, product name, 1 ]
						tableModel.addRow(new Object[] {
								((Product) productList.getSelectedValue())
										.getID(),
								((Product) productList.getSelectedValue())
										.getName(), 1 });
					} else {
						// Check if item already on list, if so increase, if not
						// then add to list
						for (int i = 0; i < tableModel.getRowCount(); i++) {
							if ((int) tableModel.getValueAt(i, 0) == ((Product) productList
									.getSelectedValue()).getID()) {
								tableModel.setValueAt(
										(int) tableModel.getValueAt(i, 2) + 1,
										i, 2);
								break;
							}
							if (i == tableModel.getRowCount() - 1) {
								tableModel.addRow(new Object[] {
										((Product) productList
												.getSelectedValue()).getID(),
										((Product) productList
												.getSelectedValue()).getName(),
										1 });
								break;
							}
						}
					}
				}
			}
		});

		// Handles the "new" checkbox
		checkboxNew.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					clearInvoice();
					customerComboBox.setEnabled(true);
					datePicker.setEnabled(true);
					datePicker.getJFormattedTextField().setEnabled(true);
					datePanel.setEnabled(true);

				} else {
					logout();
					customerComboBox.setEnabled(false);
					datePicker.setEnabled(false);
					datePicker.getJFormattedTextField().setEnabled(false);
					datePanel.setEnabled(false);
				}

			}
		});
	}

	// Opens an existing invoice
	public void updateInvoice(Invoice i) {
		logout();
		idField.setText(Integer.toString(i.getID()));
		customerComboBox.setSelectedItem(i.getCustomer());
		ArrayList<LineItem> lineitems = i.getLineItems();
		for (LineItem li : lineitems) {
			Product p = database.getProduct(li.getProductID());
			tableModel.addRow(new Object[] { li.getProductID(), p.getName(),
					li.getQuantity() });
		}
	}

	// Updates invoices on panel
	public void updateInvoiceList(ArrayList<Invoice> invoices) {
		// invoiceList.setListData(invoices.toArray());
	}

	// Updates products on panel
	public void updateProductList(ArrayList<Product> products) {
		productList.setListData(products.toArray());
	}

	// Updates customers on panel
	public void updateCustomerList(ArrayList<Customer> customers) {
		customerComboBox
				.setModel(new DefaultComboBoxModel(customers.toArray()));
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	// Clears invoice
	public void clearInvoice() {
		tableModel = (DefaultTableModel) invoiceTable.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			tableModel.removeRow(0);
		}
		idField.setText(null);
		dateModel.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
				today.get(Calendar.DATE));
		
	}

	// Resets panel
	public void logout() {
		clearInvoice();
		checkboxNew.setSelected(false);
	}

	// Shows message
	public void showError(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage);
	}

	// Handles what is displayed depending on the user logged in
	public void updateUser(User u) {
		if (u.getAuthorizationLevel() == User.NORMAL_USER) {
			btnAdd.setVisible(false);
			btnInvoices.setVisible(true);
			btnCancel.setVisible(false);
			btnDelRow.setVisible(false);
			invoiceTable.setEnabled(false);
			productList.setEnabled(false);
			checkboxNew.setEnabled(false);
			customerComboBox.setEnabled(false);
			datePicker.setEnabled(false);
		} else {
			btnAdd.setVisible(true);
			btnInvoices.setVisible(true);
			btnCancel.setVisible(true);
			btnDelRow.setVisible(true);
			invoiceTable.setEnabled(true);
			productList.setEnabled(true);
			checkboxNew.setEnabled(true);
			customerComboBox.setEnabled(true);
			datePicker.setEnabled(true);
		}
	}
}
