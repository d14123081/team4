package team4.retailsystem;

import team4.retailsystem.controller.CalculatorController;
import team4.retailsystem.model.CalculatorModel;
import team4.retailsystem.view.CalculatorView;

/**
 * A simple driver class to create the model, view, and controller components.
 * 
 * @author http://www.newthinktank.com/2013/02/mvc-java-tutorial/
 *
 */
public class MVCCalculator {
	     
    public static void main(String[] args) {
         
        CalculatorView theView = new CalculatorView();
        CalculatorModel theModel = new CalculatorModel();
        
        CalculatorController theController = new CalculatorController(theView,theModel);
         
        theView.setVisible(true);
         
    }
}
