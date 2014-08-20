package team4.retailsystem.view;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JList;

import team4.retailsystem.model.Database;
import team4.retailsystem.model.Product;

public class ProductPanel2 extends JPanel {

	Database database;
	ArrayList<Product> products = new ArrayList<Product>();

	/**
	 * Create the panel.
	 */
	public ProductPanel2() {
		database = Database.getInstance();
		products = database.getProducts();
		
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(463, 6, 156, 402);
		add(scrollPane);
		
		JList list = new JList(products.toArray());
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(50, 207, 117, 29);
		add(btnNewButton);
	
		
		

	}
}
