package team4.retailsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JList;

public class InvoicePanel extends JPanel {
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();

	private Database database;
	private JList invoiceList, productList;
	private JTable invoiceTable;
	private JTextField totalCostField;
	private JComboBox customerComboBox;
	private JTextField idField;
	private JCheckBox checkboxNew;
	private DefaultTableModel tableModel;
	private JButton btnCancel, btnDelete, btnUpdate, btnAdd;
	private JLabel lblTotalCost, lblInvoiceId;
	private JPanel invoiceListPanel, invoicePanel, productPanel;
	private JScrollPane invoiceListScrollPane, invoiceScrollPane;
	private DecimalFormat df = new DecimalFormat("0.00");

	public InvoicePanel() {
		checkboxNew = new JCheckBox("New Invoice");
		invoiceListScrollPane = new JScrollPane();
		lblTotalCost = new JLabel("Total Cost:");
		lblInvoiceId = new JLabel("Invoice ID:");
		invoiceScrollPane = new JScrollPane();
		customerComboBox = new JComboBox();
		database = Database.getInstance();
		totalCostField = new JTextField();
		btnCancel = new JButton("Cancel");
		btnDelete = new JButton("Delete");
		btnUpdate = new JButton("Update");
		invoiceListPanel = new JPanel();
		invoicePanel = new JPanel();
		productPanel = new JPanel();
		btnAdd = new JButton("Add");
		invoiceTable = new JTable();
		idField = new JTextField();
		invoiceList = new JList();
		productList = new JList();
		tableModel = (DefaultTableModel) invoiceTable.getModel();
		setLayout(null);

		invoiceListPanel.setLayout(null);
		invoiceListPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		invoiceListPanel.setBounds(10, 11, 159, 274);
		add(invoiceListPanel);
		invoiceListScrollPane.setBounds(10, 11, 138, 252);
		invoiceListPanel.add(invoiceListScrollPane);
		invoiceListScrollPane.setViewportView(invoiceList);
		invoicePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		invoicePanel.setBounds(168, 11, 457, 274);
		add(invoicePanel);
		invoicePanel.setLayout(null);
		customerComboBox.setBounds(10, 243, 185, 20);
		invoicePanel.add(customerComboBox);
		invoiceScrollPane.setBounds(10, 45, 437, 187);
		invoicePanel.add(invoiceScrollPane);
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
		});
		invoiceTable.getColumnModel().getColumn(0).setResizable(false);
		invoiceTable.getColumnModel().getColumn(1).setResizable(false);
		invoiceTable.getColumnModel().getColumn(2).setResizable(false);
		invoiceScrollPane.setViewportView(invoiceTable);
		totalCostField.setEditable(false);
		totalCostField.setBounds(361, 243, 86, 20);
		invoicePanel.add(totalCostField);
		totalCostField.setColumns(10);
		lblTotalCost.setBounds(275, 246, 75, 14);
		invoicePanel.add(lblTotalCost);
		lblInvoiceId.setBounds(10, 11, 63, 14);
		invoicePanel.add(lblInvoiceId);
		idField.setEditable(false);
		idField.setBounds(72, 8, 123, 20);
		invoicePanel.add(idField);
		idField.setColumns(10);
		checkboxNew.setBounds(338, 7, 97, 23);
		invoicePanel.add(checkboxNew);
		productPanel.setLayout(null);
		productPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		productPanel.setBounds(624, 11, 159, 274);
		add(productPanel);
		JScrollPane productScrollPane = new JScrollPane();
		productScrollPane.setBounds(10, 11, 138, 252);
		productPanel.add(productScrollPane);
		productScrollPane.setViewportView(productList);
		btnAdd.setBounds(205, 296, 89, 23);
		add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkboxNew.isSelected() && invoiceTable.getRowCount() > 0 && invoiceList.isSelectionEmpty()) {
					Customer c = (Customer) customerComboBox.getSelectedItem();
					ArrayList<LineItem> lineitems = new ArrayList<>();
					for (int i = 0; i < invoiceTable.getRowCount(); i++) {

						int productId = (int) invoiceTable.getValueAt(i, 0);
						int quantity = (int) invoiceTable.getValueAt(i, 2);

						lineitems.add(new LineItem(productId, quantity));
					}
					for (RetailViewListener r : listeners) {
						r.clickCreateInvoice(lineitems, c);
					}
					logout();
				} else {
					// throw exception
				}

			}
		});
		
		btnUpdate.setBounds(304, 296, 89, 23);
		add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!invoiceList.isSelectionEmpty()) {
					int id = ((Invoice)invoiceList.getSelectedValue()).getID();
					Customer c = (Customer) customerComboBox.getSelectedItem();
					ArrayList<LineItem> lineitems = new ArrayList<>();
					for (int i = 0; i < invoiceTable.getRowCount(); i++) {

						int productId = (int) invoiceTable.getValueAt(i, 0);
						int quantity = (int) invoiceTable.getValueAt(i, 2);

						lineitems.add(new LineItem(productId, quantity));
					}
					for (RetailViewListener r : listeners) {
						r.clickUpdateInvoice(id, lineitems, c);
					}
					logout();
				} else {
					// throw exception
				}
			}
		});
		
		btnDelete.setBounds(403, 296, 89, 23);
		add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!invoiceList.isSelectionEmpty()) {
					int id = ((Invoice)invoiceList.getSelectedValue()).getID();
					for (RetailViewListener r : listeners) {
						r.clickDeleteInvoice(id);
					}
					logout();
				} else {
					//throw exception
				}
			}
		});
		
		btnCancel.setBounds(502, 296, 89, 23);
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearInvoice();
			}
		});

		invoiceList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				logout();
				idField.setText(Integer.toString(((Invoice) invoiceList
						.getSelectedValue()).getID()));
				customerComboBox.setSelectedItem(((Invoice) invoiceList
						.getSelectedValue()).getCustomer());
				ArrayList<LineItem> lineitems = ((Invoice) invoiceList
						.getSelectedValue()).getLineItems();

				for (LineItem l : lineitems) {
					Product product = database.getProductById(l.getProductID());
					tableModel.addRow(new Object[] { l.getProductID(), product,
							l.getQuantity() });
				}
			}
		});

		productList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (invoiceList.isSelectionEmpty()) {
					checkboxNew.setSelected(true);
				}
				tableModel.addRow(new Object[] {
						((Product) productList.getSelectedValue()).getID(),
						((Product) productList.getSelectedValue()).getName(), 1 });
			}
		});
		
		invoiceTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				double cost = 0.0;
				for (int i = 0; i < invoiceTable.getRowCount(); i++) {
					Product p = database.getProductById((int) invoiceTable
							.getValueAt(i, 0));
					cost += p.getPrice() * (int) invoiceTable.getValueAt(i, 2);
				}
				totalCostField.setText(df.format(cost));
			}
		});

		checkboxNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearInvoice();
				invoiceList.clearSelection();
			}

		});
	}

	public void updateInvoiceList(ArrayList<Invoice> invoices) {
		invoiceList.setListData(invoices.toArray());
	}

	public void updateProductList(ArrayList<Product> products) {
		productList.setListData(products.toArray());
	}

	public void updateCustomerList(ArrayList<Customer> customers) {
		customerComboBox
				.setModel(new DefaultComboBoxModel(customers.toArray()));
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	public void clearInvoice() {
		tableModel = (DefaultTableModel) invoiceTable.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			tableModel.removeRow(0);
		}
		idField.setText(null);
	}

	public void logout() {
		clearInvoice();
		checkboxNew.setSelected(false); // set the checkbox to the default login
										// position
	}
}
