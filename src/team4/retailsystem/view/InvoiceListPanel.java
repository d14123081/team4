package team4.retailsystem.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
/**
 * @author Alan
 **/
public class InvoiceListPanel extends JFrame 
{
	private JPanel contentPane;
	private JTable invoiceTable;
	private JScrollPane scrollPane;
	private JButton btnDeleteInvoice, btnEditInvoice;
	private InvoicePanel invoicePanel;
	private DefaultTableModel tableModel;
	private Database database = Database.getInstance();
	DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

	public InvoiceListPanel(InvoicePanel p) 
	{
		this.invoicePanel = p;
		initialiseComponents();
		constructView();
		addListeners();
	}

	public void initialiseComponents() 
	{
		invoiceTable = new JTable();
		contentPane = new JPanel();
		scrollPane = new JScrollPane();
		btnDeleteInvoice = new JButton("Delete Invoice");
		btnEditInvoice = new JButton("Edit Invoice");
	}

	public void constructView() 
	{
		setBounds(100, 100, 522, 418);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		scrollPane.setBounds(5, 5, 493, 334);
		contentPane.add(scrollPane);
		btnDeleteInvoice.setBounds(377, 350, 121, 23);
		contentPane.add(btnDeleteInvoice);
		btnEditInvoice.setBounds(246, 350, 121, 23);
		contentPane.add(btnEditInvoice);
		constructTable();
		tableModel = (DefaultTableModel) invoiceTable.getModel();
	}

	public void addListeners() 
	{
		btnEditInvoice.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//If no row selected
				if (invoiceTable.getSelectedRow() == -1) 
				{
					showError("No invoice selected");
				} 
				else //edit invoice
				{
					int invoiceId = (int)invoiceTable.getValueAt(
							invoiceTable.getSelectedRow(), 0);
					//Calls the updateTable method (which pulls an invoice from database into the GUI
					invoicePanel.updateTable(database.getInvoice(invoiceId));
					invoiceTable.clearSelection();
				}
			}
		});

		btnDeleteInvoice.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				//If no row selected
				if (invoiceTable.getSelectedRow() == -1) 
				{
					showError("No invoice selected.");
				} 
				else //Delete invoice
				{
					int invoiceId = (int) invoiceTable.getValueAt(
						invoiceTable.getSelectedRow(), 0);
					//Passes the id to the invoice panel which then passes it to the controller for removal
					invoicePanel.deleteInvoice(invoiceId);
					invoiceTable.clearSelection();
				}
			}
		});
	}

	//Sets the invoice list table rows
	public void updateTable(ArrayList<Invoice> invoices) 
	{
		int rowCount = tableModel.getRowCount();
		for (int i = 0; i < rowCount; i++) 
		{
			tableModel.removeRow(0);
		}
		for (Invoice i : invoices) 
		{
			tableModel.addRow(new Object[] { i.getID(), i.getCustomer(),
					df.format(i.getDate()) });
		}
	}

	//Cleans up constructView() method by keeping table construction seperate
	public void constructTable() 
	{
		invoiceTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Invoice ID", "Customer", "Date" }) 
		{
			Class[] columnTypes = new Class[] 
					{ Integer.class, String.class,
					Object.class };
			public Class getColumnClass(int columnIndex) 
			{
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] { false, false, false };
			public boolean isCellEditable(int row, int column) 
			{
				return columnEditables[column];
			}
		});
		invoiceTable.getTableHeader().setReorderingAllowed(false);
		invoiceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		invoiceTable.getColumnModel().getColumn(0).setResizable(false);
		invoiceTable.getColumnModel().getColumn(1).setResizable(false);
		invoiceTable.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(invoiceTable);
	}

	public void showError(String errorMessage) 
	{
		JOptionPane.showMessageDialog(null, errorMessage);
	}
	
	//Shows which buttons are seen depending on user logged in
	public void updateButtons(int access)
	{
		switch(access)
		{
			case 0:
				btnDeleteInvoice.setVisible(false);
				btnEditInvoice.setText("View Invoice");
				break;
			case 1:
				btnDeleteInvoice.setVisible(true);
				btnEditInvoice.setText("Edit Invoice");
				break;
		}
	}
}
