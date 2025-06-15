
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * The Order class represents a customer's order.
 * It includes a set of products, shipping address, total price, and order date.
 * Orders can be either created new or reconstructed from database records.
 */
public class Order {
	private int orderId;
	private Set<Product> products;
	private String shippingAddress;
	private double totalPrice;
	private LocalDate orderDate;

	/**
	 * Constructor for creating a new order.
	 * Initializes the order date to the current date and calculates total price.
	 *
	 * @param products the set of products in the order
	 * @param shippingAddress the destination address
	 */
	public Order(Set<Product> products, String shippingAddress) {
		this.products = new HashSet<>(products);
		this.shippingAddress = shippingAddress;
		this.orderDate = LocalDate.now();
		calculateTotalPrice();
	}

	/**
	 * Constructor for loading an existing order from the database.
	 *
	 * @param orderId the unique ID of the order
	 * @param products list of products in the order
	 * @param shippingAddress destination address
	 * @param orderDate date the order was made
	 */
	public Order(int orderId, List<Product> products, String shippingAddress, LocalDate orderDate) {
		this.orderId = orderId;
		this.products = new HashSet<>(products);
		this.shippingAddress = shippingAddress;
		this.orderDate = orderDate;
		calculateTotalPrice();
	}

	/**
	 * Returns a copy of the product set.
	 *
	 * @return Set of products in the order
	 */
	public Set<Product> getProducts() {
		return new HashSet<>(products);
	}

	/**
	 * Returns a list of products in the order.
	 *
	 * @return List of products
	 */
	public List<Product> getProductList() {
		return new ArrayList<>(products);
	}

	/**
	 * Gets the shipping address of the order.
	 *
	 * @return shipping address as a string
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * Gets the total price of the order.
	 *
	 * @return total price as a double
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * Gets the date the order was placed.
	 *
	 * @return order date as a LocalDate
	 */
	public LocalDate getOrderDate() {
		return orderDate;
	}

	/**
	 * Calculates the total price of all products in the order,
	 * including any additional packaging costs if applicable.
	 */
	private void calculateTotalPrice() {
		totalPrice = 0;
		for (Product product : products) {
			totalPrice += product.getTotalPrice();
		}
	}

	/**
	 * Gets the order's ID.
	 *
	 * @return order ID as an integer
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order's ID (used when assigning a DB-generated ID).
	 *
	 * @param orderId new order ID
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
