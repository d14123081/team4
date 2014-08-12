package team4.retailsystem.model;
public class Customer 
{
	static int numberOfCustomers = 0;
	//Variables
	private String name;
	private String telephoneNumber;
	private String email;
	private String address;
	private int customerID;

	//Constructor
	public Customer(String name, String telephoneNumber, String email,
			String address) 
	{
		customerID = (numberOfCustomers++) + 1;
		this.name = name;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.address = address;
	}

	//Getters
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
