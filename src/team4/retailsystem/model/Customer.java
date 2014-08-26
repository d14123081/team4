package team4.retailsystem.model;

public class Customer {
	static int numberOfCustomers = 0;
	// Variables
	private String name;
	private String telephoneNumber;
	private String email;
	private String address;
	private int customerID;

	// Constructor
	public Customer(String name, String telephoneNumber, String email,
			String address) {
		customerID = ++numberOfCustomers;
		this.name = name;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
	}
	
	public Customer(String name, String telephoneNumber, String email, String address, int customerID){
		this.customerID = customerID;
		this.name = name;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;		
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public int getID() {
		return customerID;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String toString()
	{
		return this.name;
	}

}
