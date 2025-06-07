////Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
//import java.util.Arrays;
//
//public class Buyer extends User {
//	private String address;
//	private Cart cart;
//	private Order[] orderHistory;
//	private int orderHistorySize;
//
//	public Buyer(String username, String password, String address) {
//		super(username, password);
//		this.address = address;
//		this.cart = new Cart();
//		this.orderHistory = new Order[10];
//		this.orderHistorySize = 0;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public Cart getCart() {
//		return cart;
//	}
//
//	public Order[] getOrderHistory() {
//		return Arrays.copyOf(orderHistory, orderHistorySize);
//	}
//
//	public void placeOrder() throws EmptyCartException {
//		if (cart.isEmpty()) {
//			throw new EmptyCartException("Cannot place order. Cart is empty.");
//		}
//		Order order = new Order(cart.getProducts(), address);
//		if (orderHistorySize == orderHistory.length) {
//			Order[] newArray = new Order[orderHistory.length * 2];
//			System.arraycopy(orderHistory, 0, newArray, 0, orderHistory.length);
//			orderHistory = newArray;
//		}
//		orderHistory[orderHistorySize++] = order;
//		cart.clear();
//	}
//
//	public void replaceCartWithHistory(int historyIndex) {
//		if (historyIndex >= 0 && historyIndex < orderHistorySize) {
//			this.cart = new Cart();
//			for (Product product : orderHistory[historyIndex].getProducts()) {
//				this.cart.addProduct(product);
//			}
//		}
//	}
//}

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Buyer extends User {
	private String address;
	private Cart cart;

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

	// שליפת כל ההזמנות של הקונה מה־DB
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


	// הוספת הזמנה חדשה ל־DB (כולל הכנסת מוצרים)
	public void placeOrder(Connection conn) throws EmptyCartException {
		if (cart.isEmpty()) {
			throw new EmptyCartException("Cannot place order. Cart is empty.");
		}

		String insertOrderSQL = "INSERT INTO Orders (buyerId, deliveryAddress) VALUES (?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setInt(1, this.getUserId());
			stmt.setString(2, this.address);
			stmt.executeUpdate();

			ResultSet keys = stmt.getGeneratedKeys();
			if (keys.next()) {
				int orderId = keys.getInt(1);

				// הכנסת המוצרים של ההזמנה לטבלת OrderItems
				for (Product product : cart.getProducts()) {
					insertOrderItem(orderId, product, conn);
				}

				System.out.println("✅ Order placed successfully.");
				cart.clear();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ Failed to place order.");
		}
	}

	// החלפת הסל במוצרים מתוך הזמנה מסוימת לפי orderId
	public void replaceCartWithOrder(int orderId, Connection conn) {
		this.cart = new Cart();
		List<Product> products = getOrderProducts(orderId, conn);
		for (Product product : products) {
			cart.addProduct(product);
		}
	}

	// פונקציית עזר לשליפת מוצרים להזמנה ספציפית
	private List<Product> getOrderProducts(int orderId, Connection conn) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM OrderItems WHERE orderId = ?";
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

	// פונקציית עזר להוספת מוצר להזמנה
	private void insertOrderItem(int orderId, Product product, Connection conn) throws SQLException {
		String sql = "INSERT INTO OrderItems (orderId, productId, name, price, category, specialPackaging, packagingCost) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, orderId);
			stmt.setInt(2, product.getProductId());
			stmt.setString(3, product.getName());
			stmt.setDouble(4, product.getPrice());
			stmt.setString(5, product.getCategory().toString());
			stmt.setBoolean(6, product.hasSpecialPackaging());
			stmt.setDouble(7, product.getPackagingCost());
			stmt.executeUpdate();
		}
	}
}
