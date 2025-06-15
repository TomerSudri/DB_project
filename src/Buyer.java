
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Buyer class represents a user who can purchase products.
 * Inherits from the User class and maintains an address and a shopping cart.
 *
 * Responsibilities:
 * - Manage personal cart (add/view/clear items)
 * - Complete purchases and store order history
 * - Interact with the database for persistence
 */
public class Buyer extends User {
	private String address;
	private Cart cart;

	/**
	 * Constructor for Buyer.
	 * @param username buyer's username
	 * @param password buyer's password
	 * @param userId unique user ID
	 * @param address shipping address
	 */
	public Buyer(String username, String password, int userId, String address) {
		super(username, password, userId);
		this.address = address;
		this.cart = new Cart();
	}

	public String getAddress() {
		return address;
	}

	public Cart getCart() {
		return cart;
	}

	/**
	 * Display the products currently in the buyer's cart.
	 * @param conn active SQL connection
	 */
	public void displayCart(Connection conn) {
		List<Product> products = getCartProducts(conn);
		if (products.isEmpty()) {
			System.out.println("🛒 Your cart is empty.");
		} else {
			System.out.println("🛒 Your cart:");
			for (Product p : products) {
				System.out.println("- " + p);
			}
		}
	}

	/**
	 * Add a product to the buyer's cart in both memory and database.
	 * @param product product to add
	 * @param conn active SQL connection
	 */
	public void addToCart(Product product, Connection conn) {
		try {
			int cartId = ensureCartExists(conn);
			String sql = "INSERT INTO CartProduct (CartId, ProductId) VALUES (?, ?) ON CONFLICT DO NOTHING";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, cartId);
				stmt.setInt(2, product.getProductId());
				stmt.executeUpdate();
			}
			cart.addProduct(product);
			System.out.println("Product added to cart.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to add product to cart.");
		}
	}

	/**
	 * Calculate the total price of items in the cart, including packaging costs.
	 * @param conn active SQL connection
	 * @return total price
	 */
	public double viewCartTotal(Connection conn) {
		List<Product> products = getCartProducts(conn);
		double total = 0;
		for (Product p : products) {
			total += p.getPrice() + (p.hasSpecialPackaging() ? p.getPackagingCost() : 0);
		}
		System.out.printf("💰 Cart total: %.2f₪\n", total);
		return total;
	}

	/**
	 * Complete the purchase: insert order into DB, add products to the order, and clear the cart.
	 * @param conn active SQL connection
	 */
	public void completePurchase(Connection conn) {
		List<Product> products = getCartProducts(conn);
		if (products.isEmpty()) {
			System.out.println("🛒 Your cart is empty.");
			return;
		}

		double totalPrice = viewCartTotal(conn); // Compute total price

		// Insert order including date and get the generated order ID
		String insertOrderSQL = "INSERT INTO Orders (buyerId, shippingAddress, totalPrice, orderDate) " +
				"VALUES (?, ?, ?, ?) RETURNING orderId";

		try (PreparedStatement stmt = conn.prepareStatement(insertOrderSQL)) {
			stmt.setInt(1, getUserId());
			stmt.setString(2, this.address);
			stmt.setDouble(3, totalPrice);
			stmt.setDate(4, Date.valueOf(LocalDate.now()));

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int orderId = rs.getInt("orderId");
				for (Product product : products) {
					insertOrderProduct(orderId, product.getProductId(), conn); // Link each product to the order
				}
				clearCart(conn); // Empty the cart after purchase
				System.out.println("Purchase complete. Order ID: " + orderId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to complete purchase.");
		}
	}

	/**
	 * Helper method to insert product into OrderProduct table.
	 * @param orderId ID of the order
	 * @param productId ID of the product
	 * @param conn active SQL connection
	 */
	private void insertOrderProduct(int orderId, int productId, Connection conn) throws SQLException {
		String sql = "INSERT INTO OrderProduct (orderId, productId) VALUES (?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, orderId);
			stmt.setInt(2, productId);
			stmt.executeUpdate();
		}
	}

	/**
	 * Retrieve all past orders made by the buyer.
	 * @param conn active SQL connection
	 * @return array of Order objects
	 */
	public Order[] getOrderHistory(Connection conn) {
		List<Order> orders = new ArrayList<>();
		String sql = "SELECT * FROM Orders WHERE buyerid = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, this.getUserId());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int orderId = rs.getInt("orderid");
				String deliveryAddress = rs.getString("shippingaddress");
				LocalDate orderDate = rs.getDate("orderdate").toLocalDate();

				List<Product> products = getOrderProducts(orderId, conn);
				Order order = new Order(orderId, products, deliveryAddress, orderDate);
				orders.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders.toArray(new Order[0]);
	}

	/**
	 * Ensure the buyer has a cart in the database; create one if not.
	 * @param conn active SQL connection
	 * @return the cart ID
	 */
	private int ensureCartExists(Connection conn) throws SQLException {
		String checkSql = "SELECT CartId FROM Cart WHERE BuyerId = ?";
		try (PreparedStatement stmt = conn.prepareStatement(checkSql)) {
			stmt.setInt(1, getUserId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) return rs.getInt("CartId");
		}

		String insertSql = "INSERT INTO Cart (BuyerId) VALUES (?) RETURNING CartId";
		try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
			stmt.setInt(1, getUserId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) return rs.getInt("CartId");
		}

		throw new SQLException("Failed to create or retrieve cart.");
	}

	/**
	 * Fetch all products currently in the buyer's cart from the database.
	 * @param conn active SQL connection
	 * @return list of products
	 */
	private List<Product> getCartProducts(Connection conn) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT p.* FROM CartProduct cp JOIN Products p ON cp.ProductId = p.ProductId WHERE cp.CartId = (SELECT CartId FROM Cart WHERE BuyerId = ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, getUserId());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Product product = new Product(
						rs.getInt("productId"),
						rs.getString("name"),
						rs.getDouble("price"),
						rs.getString("category"),
						rs.getBoolean("specialPackaging"),
						rs.getDouble("packagingCost")
				);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * Delete all product entries from the buyer's cart in the database and clear in-memory cart.
	 * @param conn active SQL connection
	 */
	private void clearCart(Connection conn) {
		try {
			int cartId = ensureCartExists(conn);
			String sql = "DELETE FROM CartProduct WHERE CartId = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, cartId);
				stmt.executeUpdate();
			}
			cart.clear();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the list of products that belong to a specific order.
	 * @param orderId ID of the order
	 * @param conn active SQL connection
	 * @return list of products
	 */
	private List<Product> getOrderProducts(int orderId, Connection conn) {
		List<Product> products = new ArrayList<>();

		String sql = "SELECT p.productId, p.name, p.price, p.category, p.specialPackaging, p.packagingCost " +
				"FROM OrderProduct op " +
				"JOIN Products p ON op.productId = p.productId " +
				"WHERE op.orderId = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, orderId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Product product = new Product(
						rs.getInt("productId"),
						rs.getString("name"),
						rs.getDouble("price"),
						rs.getString("category"),
						rs.getBoolean("specialPackaging"),
						rs.getDouble("packagingCost")
				);
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * Returns string representation of the buyer.
	 */
	@Override
	public String toString() {
		return "Buyer{" +
				"userId=" + getUserId() +
				", username='" + getUsername() + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
