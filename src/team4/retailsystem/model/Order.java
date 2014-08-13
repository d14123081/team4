package team4.retailsystem.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    private static int orderNumber = 0;
    private Product product;
    private Customer customer;
    private Supplier supplier;
    private int quantity;
    private Date date;
    private boolean ordered;
    private int ID;
    private SimpleDateFormat dateformat = new SimpleDateFormat("dd/M/yyyy");

    public Order(Product product, Customer custumer, Supplier supplier, int quatity, Date date) {
        // TODO Auto-generated constructor stub
        this.quantity = quatity;
        this.product = product;
        this.customer = custumer;
        this.supplier = supplier;
        ID = ++orderNumber;
        this.date = new Date(date.getDay() + 1);
        this.ordered = false;
    }
    
    public int getID()
    {
    	return ID;
    }

    public Product getProduct(){
        return this.product;
    }

    public Customer getCustomer() {
        return this.customer;
    }
    
    public Supplier getSupplier(){
        return this.supplier;
    }

    public int getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        Order.orderNumber = orderNumber;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isOrdered() {
        return this.ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public Date getDeliveryDate() {
        return this.date;
    }

    public void setDeliveryDate(Date date) {
        this.date = date;
    }

    public String ViewCurrentOrderingForCustomer(Order order) {
        String text = null;
        if (order.isOrdered() == false) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nCustomer Name: "
                            + order.getCustomer().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\nEstimate delivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }

    public String ViewOrderedForCustomer(Order order) {
        String text = null;
        if (order.isOrdered() == true) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nCustomer Name: "
                            + order.getCustomer().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\ndelivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }
    
    public String ViewCurrentOrderingForAdmin(Order order) {
        String text = null;
        if (order.isOrdered() == false) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nSupplier Name: "
                            + order.getSupplier().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\nEstimate delivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }

    public String ViewOrderedForAdmin(Order order) {
        String text = null;
        if (order.isOrdered() == true) {
            text =
                    "Order number: " + order.getOrderNumber()
                            + "\nCustomer Name: "
                            + order.getSupplier().getName() + "\nItem: "
                            + order.getProduct().getName() + "\nQuantity: "
                            + order.getQuantity() + "\nPrice: "
                            + order.getQuantity() * order.getProduct().getCost()
                            + "\ndelivery on: "
                            + dateformat.format(order.getDeliveryDate())
                            + "\n___________________________________________\n";
        }

        return text;
    }

}
