
/**
 * The Product class represents an item that can be sold in the system.
 * Each product has a name, price, category, and optional special packaging with extra cost.
 */
public class Product {
	private int productId;
	private String name;
	private double price;
	private Category category;
	private boolean specialPackaging;
	private double packagingCost;

	/**
	 * Constructor for a new product (not yet stored in the database).
	 *
	 * @param name name of the product
	 * @param price base price of the product
	 * @param categoryName string representation of the product category
	 * @param specialPackaging true if product requires special packaging
	 * @param packagingCost additional cost for special packaging
	 */
	public Product(String name, double price, String categoryName, boolean specialPackaging, double packagingCost) {
		this.name = name;
		this.price = price;
		this.category = Category.fromString(categoryName);
		this.specialPackaging = specialPackaging;
		this.packagingCost = packagingCost;
	}

	/**
	 * Constructor for an existing product retrieved from the database.
	 *
	 * @param productId unique product ID from the database
	 * @param name name of the product
	 * @param price base price
	 * @param categoryName string representation of the category
	 * @param specialPackaging whether the product requires packaging
	 * @param packagingCost extra cost if special packaging is required
	 */
	public Product(int productId, String name, double price, String categoryName, boolean specialPackaging, double packagingCost) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.category = Category.fromString(categoryName);
		this.specialPackaging = specialPackaging;
		this.packagingCost = packagingCost;
	}

	// Getters for all fields

	public int getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public Category getCategory() {
		return category;
	}

	public boolean hasSpecialPackaging() {
		return specialPackaging;
	}

	public double getPackagingCost() {
		return packagingCost;
	}

	/**
	 * Calculates the total price of the product,
	 * including packaging cost if special packaging is required.
	 *
	 * @return total price
	 */
	public double getTotalPrice() {
		double totalPrice = price;
		if (specialPackaging) {
			totalPrice += packagingCost;
		}
		return totalPrice;
	}

	/**
	 * Returns a formatted string representation of the product,
	 * including price, category, and packaging info.
	 *
	 * @return product string summary
	 */
	@Override
	public String toString() {
		return String.format(" %s | Price: %.2f₪ | Category: %s | Packaging: %s (%.2f₪)",
				name,
				price,
				category,
				specialPackaging ? "Yes" : "No",
				packagingCost);
	}
}
