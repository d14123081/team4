package team4.retailsystem.view;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class productPanel extends JFrame{
	
	public static final String jbutton = null;

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
	                 JTextField textField = new JTextField("Enter Name of Product",40);
	                 myPanel.add(textField); 
	                 //Add button as before
	                 
	                 
	                 //Add label
	                 JLabel costLabel = new JLabel("Enter Cost of Product");
	                 myPanel.add(costLabel);
	                 //Add Textbox
	                 JTextField textField1 = new JTextField("Enter Cost of Product",40);
	                 myPanel.add(textField1);     
	                 //Add button as before
	                 
	                 
	                 JLabel markUpLabel = new JLabel("Enter MarkUp of Product");
	                 myPanel.add(markUpLabel);
	                 //Add Textbox
	                 JTextField textField2 = new JTextField("Enter MarkUp of Product",40);
	                 myPanel.add(textField2);     
	                 //Add button as before
	                 
	                 
	                 JLabel stockLabel = new JLabel("Enter Stock Level of Product");
	                 myPanel.add(stockLabel);
	                 //Add Textbox
	                 JTextField textField3 = new JTextField("Enter Stock Level of Product",40);
	                 myPanel.add(textField3);     
	                 //Add button as before
	                
	                 
	                 JLabel supplierLabel = new JLabel("Enter Supplier of Product");
	                 myPanel.add(supplierLabel);
	                 //Add Textbox
	                 JTextField textField4 = new JTextField("Enter Supplier of Product",40);
	                 myPanel.add(textField4);     
	                 //Add button as before
	                 
	                 
	                 JButton jbutton = new JButton("Create Product");
	                 myPanel.add(jbutton);
	                
	                 setContentPane(myPanel);
	                 setVisible(true);	                
	            }
	
	            public static void main(String[] args){
	            	productPanel gui = new productPanel();  
	            }
	            
	            private class RetailViewListener implements ActionListener{

					public void actionPerformed(ActionEvent event) {
						System.out.println("Inner class listener handling this click!");
						
						if(event.getSource().equals(jbutton)){
							System.out.println("This product has been created");
						}	
	            }
}
}