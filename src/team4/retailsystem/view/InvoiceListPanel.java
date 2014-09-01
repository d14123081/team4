package team4.retailsystem.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Invoice;
import team4.retailsystem.model.Product;

public class InvoiceListPanel extends JFrame {
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();

	private JPanel contentPane;
	private JTable invoiceTable;
	private JScrollPane scrollPane;
	private JButton btnDeleteInvoice, btnEditInvoice;
	private InvoicePanel invoicePanel;
	private DefaultTableModel tableModel;
	private Database database;

	public InvoiceListPanel(InvoicePanel p) {
		this.invoicePanel = p;
		initialiseComponents();
		addListeners();
		construct();
	}

	public void initialiseComponents() {
		database = Database.getInstance();
		contentPane = new JPanel();
		scrollPane = new JScrollPane();
		invoiceTable = new JTable();
		btnDeleteInvoice = new JButton("Delete Invoice");
		btnEditInvoice = new JButton("Edit Invoice");
		tableModel = (DefaultTableModel) invoiceTable.getModel();
	}

	public void addListeners() {
		btnEditInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				boolean editInv = false;
				for (int i = 0; i < invoiceTable.getRowCount(); i++) 
				{
					if (invoiceTable.isRowSelected(i)) 
					{
						int id = (int)invoiceTable.getValueAt(i, 0);
						Invoice inv = (Invoice)database.getInvoice(id);
						invoicePanel.updateTable(inv);
					}
				}
				if (!editInv) 
				{
					showError("Select an invoice to edit");
				}
			}
		});

		btnDeleteInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean delInv = false;
				for (int i = 0; i < invoiceTable.getRowCount(); i++) {
					if (invoiceTable.isRowSelected(i)) {
						invoiceTable.clearSelection();
						for (RetailViewListener r : listeners) {
							r.clickDeleteInvoice(i);
						}
						delInv = true;
						break;
					}
				}
				if (!delInv) {
					showError("Select an invoice to delete");
				}
			}
		});
	}

	public void construct() {
		setBounds(100, 100, 522, 418);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setBounds(5, 5, 493, 334);
		contentPane.add(scrollPane);
		invoiceTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Invoice ID", "Customer", "Date" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		invoiceTable.getColumnModel().getColumn(0).setResizable(false);
		invoiceTable.getColumnModel().getColumn(1).setResizable(false);
		invoiceTable.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(invoiceTable);
		btnDeleteInvoice.setBounds(397, 350, 101, 23);
		contentPane.add(btnDeleteInvoice);
		btnEditInvoice.setBounds(286, 350, 101, 23);
		contentPane.add(btnEditInvoice);
		invoiceTable.getTableHeader().setReorderingAllowed(false);
		invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void addListener(RetailViewListener r) {
		listeners.add(r);
	}

	public void setTableData(ArrayList<Invoice> invoices) {
		for (Invoice i : invoices) {
			tableModel.addRow(new Object[] { i.getID(), i.getCustomer(),
					i.getDate() });
		}
	}

	// Shows message
	public void showError(String errorMessage) {
		JOptionPane.showMessageDialog(null, errorMessage);
	}

}
