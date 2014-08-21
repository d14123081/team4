package team4.retailsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A simple example panel to show how events are passed to the controller (e.g. the RetailViewListener)
 * 
 * @author Eoin Kernan D14123081
 *
 */
public class TestPanel extends JPanel {

	private ArrayList<RetailViewListener> listeners = new ArrayList<RetailViewListener>();
	
	private JButton addButton = new JButton();
	private JTextField textField = new JTextField();
	
	public TestPanel() {
		
		addButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(RetailViewListener listener:listeners){
					//listener.clickTestAdd(textField.getText());
				}
				
			}
			
		});
		
	}

	public void addListener(RetailViewListener r){
		listeners.add(r);
	}
	
}
