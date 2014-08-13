package team4.retailsystem.model;

public class Product {
	static int productCount = 0;

	private int ID;
	private String name;
	private double cost;
	private double markup;
	private double price;
	private int stockLevel;
	private Supplier supplier;

	public Product(String name, double cost, double markup, double price,
			int stockLevel, Supplier supplier) {
		this.ID = ++productCount;
		this.name = name;
		this.cost = cost;
		this.markup = markup;
		this.price = price;
		this.stockLevel = stockLevel;
		this.supplier = supplier;
	}

	// ********getters and setters*********

	public static int getProductCount() {
		return productCount;
	}

	public static void setProductCount(int productCount) {
		Product.productCount = productCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getMarkup() {
		return markup;
	}

	public void setMarkup(double markup) {
		this.markup = markup;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public int getID() {
		return ID;
	}

}
