package team4.retailsystem.view;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import team4.retailsystem.model.Database;


public class InvoicePanel extends JPanel 
{
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
	private Database db = Database.getInstance();
	private JTable table;
	private JTable table_1;
	
	public InvoicePanel() 
	{
		setBounds(10, 88, 665, 296);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 284, 201);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{"", null, null},
			},
			new String[] {
				"Name", "Stock Level", "Price"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		scrollPane.setViewportView(table);
		
		JButton button = new JButton(">");
		button.setBounds(304, 143, 57, 23);
		add(button);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(371, 56, 284, 201);
		add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Name", "Amount", "Total Cost"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, Double.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getColumnModel().getColumn(0).setResizable(false);
		table_1.getColumnModel().getColumn(1).setResizable(false);
		table_1.getColumnModel().getColumn(2).setResizable(false);
		scrollPane_1.setViewportView(table_1);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(566, 268, 89, 23);
		add(btnCancel);
		
		JButton btnCreate = new JButton("Submit");
		btnCreate.setBounds(470, 268, 89, 23);
		add(btnCreate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(371, 268, 89, 23);
		add(btnClear);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(371, 27, 74, 14);
		add(lblCustomer);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(453, 24, 89, 20);
		add(comboBox);
		
		JLabel lblSupplier = new JLabel("Supplier:");
		lblSupplier.setBounds(10, 27, 60, 14);
		add(lblSupplier);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(80, 24, 89, 20);
		add(comboBox_1);
	}
	
	public void addListener(RetailViewListener r){
		listeners.add(r);
	}
}
