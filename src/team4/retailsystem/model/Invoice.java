package team4.retailsystem.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents an invoice for a customer.
 * 
 * <p>
 * The invoice is dated and contains a list of line items that the customer
 * ordered. The cost is calculated based on the items and their amount. The
 * invoice is dated upon creation.
 * 
 * @author Szymon Zielinski
 */
public class Invoice {
	private static int idCounter = 0;
	private int id;
	private ArrayList<LineItem> lineItems;

	private Date date;
	private Customer customer;
<<<<<<< HEAD
	//private double cost;
=======
	private double cost;
>>>>>>> refs/remotes/origin/master

	/**
	 * Creates a brand new invoice with current date.
	 * 
	 * @param lineItems
	 *            the items that make up the invoice.
	 * @param customer
	 *            the customer that made the purchase.
	 */
	public Invoice(ArrayList<LineItem> lineItems, Customer customer) {
		setLineItems(lineItems);
		setCustomer(customer);
		setId(++idCounter);
		setDate(new Date());
<<<<<<< HEAD
		//setCost(calculateCost());
=======
		setCost(calculateCost());
>>>>>>> refs/remotes/origin/master
	}

	/**
	 * Creates an invoice object based on existing data - used to turn database
	 * information into Invoice objects.
	 * 
	 * @param lineItems
	 *            the items that make up the invoice
	 * @param customer
	 *            the customer that made the purchase
	 * @param id
	 *            a unique id of the invoice
	 * @param date
	 *            the date when the invoice was initially created
	 * @param cost
	 *            the cost of all the lineItem objects
	 */
	public Invoice(ArrayList<LineItem> lineItems, Customer customer, int id,
			Date date, double cost) {
		setLineItems(lineItems);
		setCustomer(customer);
		setId(id);
		setDate(date);
<<<<<<< HEAD
		//setCost(cost);
=======
		setCost(cost);
>>>>>>> refs/remotes/origin/master
	}

	public ArrayList<LineItem> getLineItems() {
		return lineItems;
	}

	public Customer getCustomer() {
		return customer;
	}

	public double getCost() {
		return cost;
	}

	public Date getDate() {
		return date;
	}

	public int getID() {
		return id;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	private int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private void setCost(double cost) {
		this.cost = cost;
	}

	/*private void setCost(double cost) {
		this.cost = cost;
	}*/

	public void setLineItems(ArrayList<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	/**
	 * Calculates the cost of the lineItems. sum of (product * amount) for every
	 * item.
	 * 
	 * @param lineItems
	 *            the items that make up the invoice
	 * @return the cost of all products sold to the customer
	 */
	private double calculateCost() {
		double totalCost = 0;
		for (LineItem lineItem : lineItems) {
			PermanentDatabase db = PermanentDatabase.getInstance();
			Product p = db.getProduct(lineItem.getProductID());
			totalCost += lineItem.getQuantity() * p.getPrice();
		}
		return totalCost;
	}
	
	public String toString()
	{
		return new String("Invoice No. " + id);
	}
}
