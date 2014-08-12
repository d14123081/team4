package team4.retailsystem.model;

public class Customer {
	static int customerCount = 0;
	// Variables
	private String name;
	private String telephoneNumber;
	private String email;
	private String address;
	private int customerID;

	// Constructor
	public Customer(String name, String telephoneNumber, String email,
			String address) {
		this.name = name;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
		customerID = (customerCount++) + 1;
	}

	// Getters
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
}
