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
import team4.retailsystem.model.User;

import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JList;

public class InvoicePanel extends JPanel {
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();

	private Database database;
	JList invoiceList;
	JList productList;
	private JTable invoiceTable;
	private JTextField totalCostField;
	JComboBox customerComboBox;
	private JTextField idField;
	JCheckBox checkboxNew;
	DefaultTableModel dtm;
	
	private DecimalFormat df = new DecimalFormat("0.00");

	public InvoicePanel() {
		setLayout(null);
		database = Database.getInstance();

		JPanel invoiceListPanel = new JPanel();
		invoiceListPanel.setLayout(null);
		invoiceListPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		invoiceListPanel.setBounds(10, 11, 159, 274);
		add(invoiceListPanel);

		JScrollPane invoiceListScrollPane = new JScrollPane();
		invoiceListScrollPane.setBounds(10, 11, 138, 252);
		invoiceListPanel.add(invoiceListScrollPane);

		invoiceList = new JList();
		invoiceListScrollPane.setViewportView(invoiceList);

		JPanel invoicePanel = new JPanel();
		invoicePanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		invoicePanel.setBounds(168, 11, 457, 274);
		add(invoicePanel);
		invoicePanel.setLayout(null);

		customerComboBox = new JComboBox();
		customerComboBox.setBounds(10, 243, 185, 20);
		invoicePanel.add(customerComboBox);

		JScrollPane invoiceScrollPane = new JScrollPane();
		invoiceScrollPane.setBounds(10, 45, 437, 187);
		invoicePanel.add(invoiceScrollPane);

		invoiceTable = new JTable();
		invoiceTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product ID", "Product Name", "Quantity"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		invoiceTable.getColumnModel().getColumn(0).setResizable(false);
		invoiceTable.getColumnModel().getColumn(1).setResizable(false);
		invoiceTable.getColumnModel().getColumn(2).setResizable(false);
		dtm = (DefaultTableModel) invoiceTable.getModel();
		invoiceTable.getModel().addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(TableModelEvent e) 
			{
					double cost=0.0;
					for(int i = 0; i < invoiceTable.getRowCount(); i++)
					{
						Product p = database.getProduct((int)invoiceTable.getValueAt(i, 0));
						cost += p.getPrice() * (int)invoiceTable.getValueAt(i, 2);
					}
					totalCostField.setText(df.format(cost));	
			}
			
		});

		invoiceScrollPane.setViewportView(invoiceTable);

		totalCostField = new JTextField();
		totalCostField.setEditable(false);
		totalCostField.setBounds(361, 243, 86, 20);
		invoicePanel.add(totalCostField);
		totalCostField.setColumns(10);

		JLabel lblTotalCost = new JLabel("Total Cost:");
		lblTotalCost.setBounds(275, 246, 75, 14);
		invoicePanel.add(lblTotalCost);

		JLabel lblInvoiceId = new JLabel("Invoice ID:");
		lblInvoiceId.setBounds(10, 11, 63, 14);
		invoicePanel.add(lblInvoiceId);

		idField = new JTextField();
		idField.setEditable(false);
		idField.setBounds(72, 8, 123, 20);
		invoicePanel.add(idField);
		idField.setColumns(10);

		checkboxNew = new JCheckBox("New Invoice");
		checkboxNew.setBounds(338, 7, 97, 23);
		invoicePanel.add(checkboxNew);

		JPanel productPanel = new JPanel();
		productPanel.setLayout(null);
		productPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
				null));
		productPanel.setBounds(624, 11, 159, 274);
		add(productPanel);

		JScrollPane productScrollPane = new JScrollPane();
		productScrollPane.setBounds(10, 11, 138, 252);
		productPanel.add(productScrollPane);

		productList = new JList();
		productScrollPane.setViewportView(productList);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(205, 296, 89, 23);
		add(btnAdd);
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if (checkboxNew.isSelected() && invoiceTable.getRowCount() > 0) 
				{
					Customer c = (Customer)customerComboBox.getSelectedItem();
					ArrayList<LineItem> lineitems = new ArrayList<>();
					for (int i = 0; i < invoiceTable.getRowCount(); i++) {
						
						int productId = (int)invoiceTable.getValueAt(i, 0);
						int quantity =  (int)invoiceTable.getValueAt(i, 2);
						
						lineitems.add(new LineItem(productId, quantity));
					}
					for (RetailViewListener r : listeners) 
					{
						r.clickCreateInvoice(lineitems, c);
					}
					clearInvoice();
				} else {
					// throw exception
				}

			}
		});

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(304, 296, 89, 23);
		add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!idField.getText().equals("")
						&& invoiceTable.getRowCount() > 0) {
					int id = Integer.parseInt(idField.getText());
					Customer c = (Customer) customerComboBox.getSelectedItem();
					ArrayList<LineItem> lineitems = new ArrayList<>();
					for (int i = 0; i < invoiceTable.getRowCount(); i++) {
						
						int productId = (int)invoiceTable.getValueAt(i, 0);
						int quantity =  (int)invoiceTable.getValueAt(i, 2);
						
						lineitems.add(new LineItem(productId, quantity));
					}
					for (RetailViewListener r : listeners) {
						r.clickUpdateInvoice(id, lineitems, c);
					}
					clearInvoice();
					
				} else {
					// throw exception
				}
			}
		});

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(403, 296, 89, 23);
		add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idField.getText().equals("")) {
					// throw exception
				} else {
					int id = Integer.parseInt(idField.getText());
					for (RetailViewListener r : listeners) {
						r.clickDeleteInvoice(id);
					}
					clearInvoice();
				}
			}
		});

		//Pulls invoice from database
		invoiceList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				clearInvoice();
				checkboxNew.setSelected(false);
				idField.setText(Integer.toString(((Invoice)invoiceList.getSelectedValue()).getID()));
				customerComboBox.setSelectedItem(((Invoice)invoiceList.getSelectedValue()).getCustomer());
				ArrayList<LineItem> lineitems = ((Invoice)invoiceList.getSelectedValue()).getLineItems();
				
				
				for(LineItem l : lineitems)
				{	
					Product product = database.getProduct(l.getProductID());
					dtm.addRow(new Object[] { l.getProductID(), product, l.getQuantity() });	
				}
			}
		});
		
		//Adds a product to the table
		productList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(idField.getText().equals(""))
				{
					checkboxNew.setSelected(true);
				}
				dtm.addRow(new Object[] { ((Product)productList.getSelectedValue()).getID(), ((Product)productList.getSelectedValue()).getName(), 1 });
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(502, 296, 89, 23);
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cancel button
			}
		});
		
		checkboxNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearInvoice();
				invoiceList.clearSelection();
			}
			
		});
	}

	public void updateInvoiceList(ArrayList<Invoice> invoices){
		invoiceList.setListData(invoices.toArray());
	}
	
	public void updateProductList(ArrayList<Product> products){
		productList.setListData(products.toArray());;
	}
	
	public void updateCustomerList(ArrayList<Customer> customers){
		customerComboBox.setModel(new DefaultComboBoxModel(customers.toArray()));
	}
	
	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}
	
	public void clearInvoice()
	{
		dtm = (DefaultTableModel) invoiceTable.getModel();
		int rowCount =dtm.getRowCount();
		for (int i = 0;i<rowCount;i++) 
		{
		    dtm.removeRow(0);
		}
		idField.setText(null);
	}
}
