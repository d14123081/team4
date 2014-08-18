package team4.retailsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import team4.retailsystem.model.LineItem;

public class LineItemPanel extends JPanel{
	
	private JLabel productIdLabel;
	private JLabel quantityLabel;
	private JTextField quantityTF;
	private JTextField productIdTF;
	private JButton lineInSubmitBtn;

	public LineItemPanel() {
		
		this.setSize(200, 300);
		this.setVisible(true);
		
		productIdLabel = new JLabel();
		quantityLabel = new JLabel();
		quantityTF = new JTextField();
		productIdTF = new JTextField();
		lineInSubmitBtn = new JButton();
		
		quantityTF.setEditable(false);
		productIdTF.setEditable(false);
		lineInSubmitBtn.addActionListener(new LineInBtnListener());
		
		
		this.add(productIdLabel);
		this.add(productIdTF);
		this.add(quantityLabel);
		this.add(quantityTF);
		
		
	}
	
	public String getProductIdTF(){
		return productIdTF.getText();
	}
	
	public String getQuantityTF(){
		return quantityTF.getText();
	}
	
	public void setQuantityTF(String newQuantity){
		this.quantityTF.setText(newQuantity);
	}
	
	public void setProductIdTF(String newProductId){
		this.productIdTF.setText(newProductId);
	}
	
	class LineInBtnListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			LineItem lineitem = new LineItem(Integer.parseInt(getProductIdTF()), Integer.parseInt(getQuantityTF()));
			// add lineitem to database
		}
	}
	
	
	
	

}
