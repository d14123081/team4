package team4.retailsystem.model;

import java.util.Date;

public class Invoice {
	public double getCost(){
		return -1;
	}
	
	public Customer getCustomer(){
		return (Customer) new Object();
	}
	
	public Date getDate(){
		return new Date();
	}
	
	public int getID(){
		return -1;
	}
	
	public LineItem[] getLineItems(){
		return new LineItem[1];
	}
}
