package team4.retailsystem.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A reusable screen class for providing general input/ouput using a simple Swing interface.
 * Uses a builder for instanciation.
 * 
 * @author Eoin Kernan D14123081
 *
 */
public final class Screen extends JFrame {

	//swing components
	private JTextArea textArea;
	private JPanel contentPane;
	private JPanel panel;
	private JTextField textField;
	private JButton enterButton;

	//private member varialbes
	private String title;
	private int defaultCloseOperation;
	private int textAreaRows;
	private int textAreaColumns;
	private int textFieldColumns;
	private int boundsX;
	private int boundsY;
	private int boundsWidth;
	private int boundsHeight;
	private Color backgroundColour;
	private Color foregroundColour;
	private String buttonText;
	private boolean editable;
	private boolean frameVisibility;
	
	//given the number of options available when setting up the components,
	//a builder variant is used to make the class more easily customisable.
	private Screen(Builder builder) {
		
		title = builder.title;
		defaultCloseOperation = builder.defaultCloseOperation;
		textAreaRows = builder.textAreaRows;
		textAreaColumns = builder.textAreaColumns;
		textFieldColumns = builder.textFieldColumns;
		boundsX = builder.boundsX;
		boundsY = builder.boundsY;
		boundsWidth = builder.boundsWidth;
		boundsHeight = builder.boundsHeight;
		backgroundColour = builder.backgroundColour;
		foregroundColour = builder.foregroundColour;
		buttonText = builder.buttonText;
		editable = builder.editable;
		frameVisibility = builder.frameVisibility;
		
		initialiseInterfaceComponents();

		textField.requestFocusInWindow();
	}
	
	//method will instanciate and configure interface components and cobble them together
	private void initialiseInterfaceComponents(){

		contentPane = new JPanel();
		textArea = new JTextArea();
		panel = new JPanel();
		textField = new JTextField();
		enterButton = new JButton(buttonText);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		textArea.setForeground(foregroundColour);
		textArea.setBackground(backgroundColour);
		textArea.setEditable(editable);
		textArea.setColumns(textAreaColumns);
		textArea.setRows(textAreaRows);
		contentPane.add(textArea);

		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		contentPane.add(panel);
		
		textField.setColumns(textFieldColumns);
		panel.add(textField);
		
		enterButton.addActionListener(new ButtonListener(this));
		panel.add(enterButton);
		
		setTitle(title);
		setDefaultCloseOperation(defaultCloseOperation);
		setBounds(boundsX, boundsY, boundsWidth, boundsHeight);
		
		setVisible(frameVisibility);
	}
	
	//method will append text on the screen
	public void updateScreenText(String content){
		setScreenText(textArea.getText() +
					  "\n\n" + 
					  content);
	}
	
	//method will replace text on the screen, then clear the text field.
	public void setScreenText(String content){
		textArea.setText(content);
		textField.setText("");
	}
	
	//internal method for getting the contents of the textField. Blocks until button is pressed.
	private String getTextFieldContents(){
		
		String s = "";
		
		//blocks until the 'Enter' button calls notify
		try{
			synchronized(this){
				this.wait();
			}
			s = textField.getText();
			textField.setText("");
		}
		catch(InterruptedException ie){
			//not really important, return the empty string 's' anyway.
		}
		
		return s;
	}
	
	//Method will return whatever text is in the text field
	public String getString()
	{
		return getTextFieldContents();
	}
	
	//Method will return whatever int is in the text field, or -1 for anything other than an int
	public int getInt()
	{
		int result = -1;
		
		try
		{result = Integer.parseInt(getTextFieldContents());}
		catch(NumberFormatException nfe)
		{/*wasn't a number, return the -1 instead of throwing the exception*/}
		
		return result;
	}
	
	
	//Method will return whatever int is in the text field, or -1 for anything other than an int
	public double getDouble()
	{
		double result = -1.0;
		
		try
		{result = Double.parseDouble(getTextFieldContents());}
		catch(NumberFormatException nfe)
		{/*wasn't a number, return the -1.0 instead of throwing the exception*/}
		
		return result;
	}
	
	//TODO: Add methods for getting other primitive types (float, long, short, boolean, char)
	
	/**
	 * A simple action listener for the screen's 'enter' button. Calls the Screen's 'notify'
	 * method when pressed, then sets focus back on the text field.
	 *
	 */
	private class ButtonListener implements ActionListener{
		
		private Screen screen;
		
		public ButtonListener(Screen screen)
		{this.screen = screen;}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//notify on the screen object, freeing any thread
			//waiting on screen.wait() (i.e. the AccountancyFirm class)
			synchronized(screen){
				screen.notify();
				textField.requestFocusInWindow();
			}
		}
	}

	/**
	 * The screen builder class, used to configure the Screen before creation.
	 */
	public static class Builder{
		
		//members set with default values
		private String title = "Java Swing Input Screen";
		private int defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
		private int textAreaRows = 100;
		private int textAreaColumns = 200;
		private int textFieldColumns = 10;
		private int boundsX = 100;
		private int boundsY = 100;
		private int boundsWidth = 600;
		private int boundsHeight = 600;
		private Color backgroundColour = Color.BLACK;
		private Color foregroundColour = Color.WHITE;
		private String buttonText = "Enter";
		private boolean editable = false;
		private boolean frameVisibility = true;
		
		public Screen build(){
			return new Screen(this);
		}
		
		public Builder title(String s){
			this.title = s;
			return this;
		}
		
		public Builder defaultCloseOperation(int i){
			this.defaultCloseOperation = i;
			return this;
		}
		
		public Builder textAreaRows(int i){
			this.textAreaRows = i;
			return this;
		}
		
		public Builder textAreaColumns(int i){
			this.textAreaColumns = i;
			return this;
		}
		
		public Builder textFieldColumns(int i){
			this.textFieldColumns = i;
			return this;
		}
		
		public Builder boundsX(int i){
			this.boundsX = i;
			return this;
		}
		
		public Builder boundsY(int i){
			this.boundsY = i;
			return this;
		}
		
		public Builder boundsWidth(int i){
			this.boundsWidth = i;
			return this;
		}
		
		public Builder boundsHeight(int i){
			this.boundsHeight = i;
			return this;
		}
		
		public Builder backgroundColour(Color c){
			this.backgroundColour = c;
			return this;
		}
		
		public Builder foregroundColour(Color c){
			this.foregroundColour = c;
			return this;
		}
		
		public Builder buttonText(String s){
			this.buttonText = s;
			return this;
		}
		
		public Builder editable(boolean b){
			this.editable = b;
			return this;
		}
		
		public Builder frameVisibility(boolean b){
			this.frameVisibility = b;
			return this;
		}
	}
}