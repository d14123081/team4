package team4.retailsystem.view;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class productPanel extends JFrame{
	
	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	public static final String jbutton = null;

	JTextField nameField;
	JTextField costField;
	JTextField markUpField;
	JTextField stockField;
	JTextField supplierField;
	JTextField productIDField;
	
	
	public productPanel(){
	            
	                 setDefaultLookAndFeelDecorated(true);
	                 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                 setBounds(40,60,600,400);
	                 setLayout(null);
	                 //Create a panel 
	                 JPanel myPanel = new JPanel();
	                 myPanel.setBackground(new Color(150,35,90));
	                 myPanel.setOpaque(true);
	                 
	                 //Add label
	                 JLabel nameLabel = new JLabel("Enter Name of Product");
	                 myPanel.add(nameLabel);
	                 //Add Textbox
	                 nameField = new JTextField("Enter Name of Product",40);
	                 myPanel.add(nameField); 
	                 //Add button as before
	                 
	                 
	                 //Add label
	                 JLabel costLabel = new JLabel("Enter Cost of Product");
	                 myPanel.add(costLabel);
	                 //Add Textbox
	                 costField = new JTextField("Enter Cost of Product",40);
	                 myPanel.add(costField);     
	                 //Add button as before
	                 
	                 
	                 JLabel markUpLabel = new JLabel("Enter MarkUp of Product");
	                 myPanel.add(markUpLabel);
	                 //Add Textbox
	                 markUpField = new JTextField("Enter MarkUp of Product",40);
	                 myPanel.add(markUpField);     
	                 //Add button as before
	                 
	                 
	                 JLabel stockLabel = new JLabel("Enter Stock Level of Product");
	                 myPanel.add(stockLabel);
	                 //Add Textbox
	                 stockField = new JTextField("Enter Stock Level of Product",40);
	                 myPanel.add(stockField);     
	                 //Add button as before
	                
	                 
	                 JLabel supplierLabel = new JLabel("Enter Supplier of Product");
	                 myPanel.add(supplierLabel);
	                 //Add Textbox
	                 supplierField = new JTextField("Enter Supplier of Product",40);
	                 myPanel.add(supplierField);     
	                 //Add button as before
	                 
	                 
	                 JButton jbutton = new JButton("Create Product");
	                 jbutton.addActionListener(new AddProductListener());
	                 myPanel.add(jbutton);
	                
	                 setContentPane(myPanel);
	                 setVisible(true);
	            }
	
	            public static void main(String[] args){
	            	productPanel gui = new productPanel();  
	            }
	            
	            private class AddProductListener implements ActionListener{

					public void actionPerformed(ActionEvent event) {
						
						String name = nameField.getText();
						double cost =  Double.parseDouble(costField.getText());
						double markup = Double.parseDouble(markUpField.getText());
						int stockLevel = Integer.parseInt(stockField.getText());
						int supplierId = Integer.parseInt(supplierField.getText());
						
						//validation here
						
						//if valid...
						//inform RetailViewListeners of event
						for(RetailViewListener r:listeners){
							r.clickCreateProduct(name, cost, markup, stockLevel, supplierId);
						}
						//else, show an error
						
						/*System.out.println("Inner class listener handling this click!");
						
						if(event.getSource().equals(jbutton)){
							System.out.println("This product has been created");
						}*/
					}
	            }
					
					private class UpdateProductListener implements ActionListener{

						public void actionPerformed(ActionEvent event) {
							
							String name = nameField.getText();
							double cost =  Double.parseDouble(costField.getText());
							double markup = Double.parseDouble(markUpField.getText());
							int stockLevel = Integer.parseInt(stockField.getText());
							int supplierId = Integer.parseInt(supplierField.getText());
							
							//validation here
							
							//if valid...
							//inform RetailViewListeners of event
							for(RetailViewListener r:listeners){
								r.clickUpdateProduct(name, cost, markup, stockLevel, supplierId);
							}
							//else, show an error
							
							/*System.out.println("Inner class listener handling this click!");
							
							if(event.getSource().equals(jbutton)){
								System.out.println("This product has been created");
							}*/
						}
					}
					
					 private class DeleteProductListener implements ActionListener{
							public void actionPerformed(ActionEvent event) {
								int productId = Integer.parseInt(productIDField.getText());
								
								//validation here
								
								//if valid...
								//inform RetailViewListeners of event
								for(RetailViewListener r:listeners){
									r.clickDeleteProduct(productId);
								}
								//else, show an error
								
								/*System.out.println("Inner class listener handling this click!");
								
								if(event.getSource().equals(jbutton)){
									System.out.println("This product has been created");
								}*/
							}
			            }
}