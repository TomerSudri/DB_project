
import java.util.HashSet;
import java.util.Set;

/**
 * The Cart class represents a collection of products a buyer wants to purchase.
 * It supports adding, retrieving, clearing, and checking products.
 *
 * Note: The cartId field is only relevant if you maintain a "Carts" table in the database.
 */
public class Cart {
	private Set<Product> products;
	private int cartId; // Relevant only if using a Carts table in the database

	/**
	 * Constructor initializes an empty cart using a HashSet to prevent duplicate products.
	 */
	public Cart() {
		this.products = new HashSet<>();
	}

	/**
	 * Adds a product to the cart.
	 * @param product the product to add
	 */
	public void addProduct(Product product) {
		products.add(product);
	}

	/**
	 * Returns a copy of the current products in the cart.
	 * @return a new Set containing the cart products
	 */
	public Set<Product> getProducts() {
		return new HashSet<>(products);
	}

	/**
	 * Calculates the total price of all products in the cart.
	 * @return total price as a double
	 */
	public double getTotalPrice() {
		double totalPrice = 0.0;
		for (Product product : products) {
			totalPrice += product.getTotalPrice();
		}
		return totalPrice;
	}

	/**
	 * Checks whether the cart is empty.
	 * @return true if the cart has no products
	 */
	public boolean isEmpty() {
		return products.isEmpty();
	}

	/**
	 * Clears all products from the cart.
	 */
	public void clear() {
		products.clear();
	}

	/**
	 * Returns the cart's ID (used only if integrated with a database table).
	 * @return cart ID
	 */
	public int getCartId() {
		return cartId;
	}

	/**
	 * Sets the cart's ID (used only if integrated with a database table).
	 * @param cartId new cart ID
	 */
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
}
