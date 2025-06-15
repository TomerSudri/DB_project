
import java.sql.*;
import java.util.*;

/**
 * ECommerceSystem manages buyers, sellers, and product-related operations.
 * It communicates with a PostgreSQL database to persist and retrieve data.
 */
public class ECommerceSystem {
	private Map<String, Buyer> buyerMap;
	private Map<String, Seller> sellerMap;

	/**
	 * Constructor initializes in-memory maps for buyers and sellers.
	 */
	public ECommerceSystem() {
		this.buyerMap = new HashMap<>();
		this.sellerMap = new HashMap<>();
	}

	/**
	 * Loads buyers from the database and returns them as a collection.
	 */
	public Collection<Buyer> getBuyers(Connection conn) {
		loadBuyersFromDB(conn);
		return buyerMap.values();
	}

	/**
	 * Loads sellers from the database and returns them as a collection.
	 */
	public Collection<Seller> getSellers(Connection conn) {
		loadSellersFromDB(conn);
		return sellerMap.values();
	}

	/**
	 * Retrieves a buyer by username.
	 */
	public Buyer findBuyerByUsername(String username, Connection conn) {
		loadBuyersFromDB(conn);
		return buyerMap.get(username);
	}

	/**
	 * Retrieves a seller by username.
	 */
	public Seller findSellerByUsername(String username, Connection conn) {
		loadSellersFromDB(conn);
		return sellerMap.get(username);
	}

	// Loads buyer data from the database into buyerMap
	private void loadBuyersFromDB(Connection conn) {
		buyerMap.clear();
		String sql = "SELECT u.userId, u.username, u.password, b.address FROM Users u JOIN Buyers b ON u.userId = b.BuyerId";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Buyer buyer = new Buyer(
						rs.getString("username"),
						rs.getString("password"),
						rs.getInt("userId"),
						rs.getString("address")
				);
				buyerMap.put(buyer.getUsername(), buyer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Loads seller data from the database into sellerMap
	private void loadSellersFromDB(Connection conn) {
		sellerMap.clear();
		String sql = "SELECT u.userId, u.username, u.password FROM Users u JOIN Sellers s ON u.userId = s.SellerId";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Seller seller = new Seller(
						rs.getString("username"),
						rs.getString("password"),
						rs.getInt("userId")
				);
				sellerMap.put(seller.getUsername(), seller);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new buyer to the database.
	 */
	public boolean addBuyer(Buyer buyer, Connection conn) {
		try {
			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'buyer') RETURNING userId";
			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
				userStmt.setString(1, buyer.getUsername());
				userStmt.setString(2, buyer.getPassword());

				ResultSet rs = userStmt.executeQuery();
				if (rs.next()) {
					int userId = rs.getInt("userId");
					buyer.setUserId(userId);

					String buyerSql = "INSERT INTO Buyers (BuyerId, address) VALUES (?, ?)";
					try (PreparedStatement buyerStmt = conn.prepareStatement(buyerSql)) {
						buyerStmt.setInt(1, userId);
						buyerStmt.setString(2, buyer.getAddress());
						buyerStmt.executeUpdate();
					}

					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Adds a new seller to the database.
	 */
	public boolean addSeller(Seller seller, Connection conn) {
		try {
			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'seller') RETURNING userId";
			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
				userStmt.setString(1, seller.getUsername());
				userStmt.setString(2, seller.getPassword());

				ResultSet rs = userStmt.executeQuery();
				if (rs.next()) {
					int userId = rs.getInt("userId");
					seller.setUserId(userId);

					String sellerSql = "INSERT INTO Sellers (SellerId) VALUES (?)";
					try (PreparedStatement sellerStmt = conn.prepareStatement(sellerSql)) {
						sellerStmt.setInt(1, userId);
						sellerStmt.executeUpdate();
					}

					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Retrieves all products from the database that belong to a given category.
	 */
	public Product[] getProductsByCategory(Connection conn, Category category) {
		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM Products WHERE category = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, category.getDisplayName());
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
				productList.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return productList.toArray(new Product[0]);
	}

	// The rest of the code remains unchanged, continuing from handleAddBuyer, handleAddSeller, etc.

//	public Buyer handleAddBuyer(Connection conn, Scanner scanner) {
//		System.out.print("Enter buyer username: ");
//		String username = scanner.nextLine();
//		if (findBuyerByUsername(username, conn) != null) {
//			System.out.println("Username already exists.");
//			return null;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//		System.out.print("Enter address: ");
//		String address = scanner.nextLine();
//
//		Buyer buyer = new Buyer(username, password, -1, address);
//		if (addBuyer(buyer, conn)) {
//			System.out.println("Buyer added successfully.");
//		} else {
//			System.out.println("Failed to add buyer.");
//		}
//		return buyer;
//	}
//
//	public Seller handleAddSeller(Connection conn, Scanner scanner) {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		if (findSellerByUsername(username, conn) != null) {
//			System.out.println("Username already exists.");
//			return null;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//
//		Seller seller = new Seller(username, password, -1);
//		if (addSeller(seller, conn)) {
//			System.out.println("Seller added successfully.");
//		} else {
//			System.out.println("Failed to add seller.");
//		}
//		return seller;
//	}
//
//public void handleAddProductToSeller(Connection conn, Scanner scanner) {
//	System.out.print("Enter seller username: ");
//	String username = scanner.nextLine();
//	Seller seller = findSellerByUsername(username, conn);
//	if (seller == null) {
//		System.out.println("Seller not found.");
//		return;
//	}
//
//	System.out.print("Enter product name: ");
//	String name = scanner.nextLine();
//	double price = readDoubleInput(scanner, "Enter product price: ");
//	Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
//	boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
//	double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;
//
//	Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);
//
//	boolean success = seller.addProduct(product, conn); // שימוש בפונקציה שמחזירה true/false
//
//	if (success) {
//		System.out.println("Product added successfully.");
//	} else {
//		System.out.println("Failed to add product.");
//	}
//}
//
//
//	public void handleShowProductsByCategory(Connection conn, Scanner scanner) {
//		Category category = readCategoryInput(scanner, "Enter category to search (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
//		Product[] products = getProductsByCategory(conn, category);
//
//		if (products.length == 0) {
//			System.out.println("No products found in this category.");
//		} else {
//			System.out.println("Products in category " + category.getDisplayName() + ":");
//			for (Product p : products) {
//				System.out.printf("- %s | Price: %.2f | Special Packaging: %s | Packaging Cost: %.2f\n",
//						p.getName(), p.getPrice(), p.hasSpecialPackaging() ? "Yes" : "No", p.getPackagingCost());
//			}
//		}
//	}
//
//	public Category readCategoryInput(Scanner scanner, String prompt) {
//		while (true) {
//			System.out.print(prompt);
//			try {
//				return Category.valueOf(scanner.nextLine().trim().toUpperCase());
//			} catch (IllegalArgumentException e) {
//				System.out.println("Invalid category. Try again.");
//			}
//		}
//	}
//
//	private double readDoubleInput(Scanner scanner, String prompt) {
//		while (true) {
//			System.out.print(prompt);
//			try {
//				return Double.parseDouble(scanner.nextLine());
//			} catch (NumberFormatException e) {
//				System.out.println("Invalid number. Try again.");
//			}
//		}
//	}
//
//	private boolean readBooleanInput(Scanner scanner, String prompt) {
//		System.out.print(prompt);
//		String input = scanner.nextLine().trim().toLowerCase();
//		return input.equals("yes") || input.equals("y");
//	}
//
//	// ✅ פונקציות אדמין חדשות:
//
//	public void handleDeleteSeller(Connection conn, Scanner scanner) {
//		System.out.print("Enter seller username to delete: ");
//		String username = scanner.nextLine();
//		Seller seller = findSellerByUsername(username, conn);
//		if (seller == null) {
//			System.out.println("Seller not found.");
//			return;
//		}
//		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userId = ?")) {
//			stmt.setInt(1, seller.getUserId());
//			stmt.executeUpdate();
//			System.out.println("Seller deleted.");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Failed to delete seller.");
//		}
//	}
//
//	public void handleDeleteBuyer(Connection conn, Scanner scanner) {
//		System.out.print("Enter buyer username to delete: ");
//		String username = scanner.nextLine();
//		Buyer buyer = findBuyerByUsername(username, conn);
//		if (buyer == null) {
//			System.out.println("Buyer not found.");
//			return;
//		}
//		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userId = ?")) {
//			stmt.setInt(1, buyer.getUserId());
//			stmt.executeUpdate();
//			System.out.println("Buyer deleted.");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("Failed to delete buyer.");
//		}
//	}
//
//
//public void handleShowAllProducts(Connection conn) {
//	String sql = "SELECT * FROM Products";
//	try (PreparedStatement stmt = conn.prepareStatement(sql);
//		 ResultSet rs = stmt.executeQuery()) {
//
//		System.out.println("All Products:");
//		while (rs.next()) {
//			int productId = rs.getInt("productId");
//			String name = rs.getString("name");
//			double price = rs.getDouble("price");
//			String category = rs.getString("category");
//			boolean specialPackaging = rs.getBoolean("specialPackaging");
//
//			System.out.printf("%d. %s | Price: %.2f₪ | Category: %s | Special Packaging: %s\n",
//					productId,
//					name,
//					price,
//					category,
//					specialPackaging ? "Yes" : "No");
//		}
//
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//}
//
//	public void handleShowAllCategories(Connection conn) {
//		String sql = "SELECT category FROM Categories";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//			System.out.println("Categories:");
//			while (rs.next()) {
//				System.out.println("- " + rs.getString("category"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	// Handles adding a new buyer to the system through user input and database insertion
	public Buyer handleAddBuyer(Connection conn, Scanner scanner) {
		System.out.print("Enter buyer username: ");
		String username = scanner.nextLine();
		if (findBuyerByUsername(username, conn) != null) {
			System.out.println("Username already exists.");
			return null;
		}
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		System.out.print("Enter address: ");
		String address = scanner.nextLine();

		Buyer buyer = new Buyer(username, password, -1, address);
		if (addBuyer(buyer, conn)) {
			System.out.println("Buyer added successfully.");
		} else {
			System.out.println("Failed to add buyer.");
		}
		return buyer;
	}

	// Handles adding a new seller to the system through user input and database insertion
	public Seller handleAddSeller(Connection conn, Scanner scanner) {
		System.out.print("Enter seller username: ");
		String username = scanner.nextLine();
		if (findSellerByUsername(username, conn) != null) {
			System.out.println("Username already exists.");
			return null;
		}
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		Seller seller = new Seller(username, password, -1);
		if (addSeller(seller, conn)) {
			System.out.println("Seller added successfully.");
		} else {
			System.out.println("Failed to add seller.");
		}
		return seller;
	}

	// Allows an admin to add a new product to an existing seller in the database
	public void handleAddProductToSeller(Connection conn, Scanner scanner) {
		System.out.print("Enter seller username: ");
		String username = scanner.nextLine();
		Seller seller = findSellerByUsername(username, conn);
		if (seller == null) {
			System.out.println("Seller not found.");
			return;
		}

		System.out.print("Enter product name: ");
		String name = scanner.nextLine();
		double price = readDoubleInput(scanner, "Enter product price: ");
		Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
		boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
		double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;

		Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);

		boolean success = seller.addProduct(product, conn);

		if (success) {
			System.out.println("Product added successfully.");
		} else {
			System.out.println("Failed to add product.");
		}
	}

	// Displays all products in a selected category
	public void handleShowProductsByCategory(Connection conn, Scanner scanner) {
		Category category = readCategoryInput(scanner, "Enter category to search (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
		Product[] products = getProductsByCategory(conn, category);

		if (products.length == 0) {
			System.out.println("No products found in this category.");
		} else {
			System.out.println("Products in category " + category.getDisplayName() + ":");
			for (Product p : products) {
				System.out.printf("- %s | Price: %.2f | Special Packaging: %s | Packaging Cost: %.2f\n",
						p.getName(), p.getPrice(), p.hasSpecialPackaging() ? "Yes" : "No", p.getPackagingCost());
			}
		}
	}

	// Reads a valid Category from the user input
	public Category readCategoryInput(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Category.valueOf(scanner.nextLine().trim().toUpperCase());
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid category. Try again.");
			}
		}
	}

	// Reads a valid double value from the user
	private double readDoubleInput(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid number. Try again.");
			}
		}
	}

	// Reads a yes/no input from the user and returns a boolean
	private boolean readBooleanInput(Scanner scanner, String prompt) {
		System.out.print(prompt);
		String input = scanner.nextLine().trim().toLowerCase();
		return input.equals("yes") || input.equals("y");
	}

	// Deletes a seller from the system using username
	public void handleDeleteSeller(Connection conn, Scanner scanner) {
		System.out.print("Enter seller username to delete: ");
		String username = scanner.nextLine();
		Seller seller = findSellerByUsername(username, conn);
		if (seller == null) {
			System.out.println("Seller not found.");
			return;
		}
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userId = ?")) {
			stmt.setInt(1, seller.getUserId());
			stmt.executeUpdate();
			System.out.println("Seller deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to delete seller.");
		}
	}

	// Deletes a buyer from the system using username
	public void handleDeleteBuyer(Connection conn, Scanner scanner) {
		System.out.print("Enter buyer username to delete: ");
		String username = scanner.nextLine();
		Buyer buyer = findBuyerByUsername(username, conn);
		if (buyer == null) {
			System.out.println("Buyer not found.");
			return;
		}
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userId = ?")) {
			stmt.setInt(1, buyer.getUserId());
			stmt.executeUpdate();
			System.out.println("Buyer deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to delete buyer.");
		}
	}

	// Displays all products in the system
	public void handleShowAllProducts(Connection conn) {
		String sql = "SELECT * FROM Products";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			System.out.println("All Products:");
			while (rs.next()) {
				int productId = rs.getInt("productId");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				String category = rs.getString("category");
				boolean specialPackaging = rs.getBoolean("specialPackaging");

				System.out.printf("%d. %s | Price: %.2f₪ | Category: %s | Special Packaging: %s\n",
						productId,
						name,
						price,
						category,
						specialPackaging ? "Yes" : "No");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Displays all product categories in the system
	public void handleShowAllCategories(Connection conn) {
		String sql = "SELECT category FROM Categories";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			System.out.println("Categories:");
			while (rs.next()) {
				System.out.println("- " + rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Adds a new category to the database
	public void handleAddCategory(Connection conn, Scanner scanner) {
		System.out.print("Enter new category name: ");
		String category = scanner.nextLine();
		String sql = "INSERT INTO Categories (category) VALUES (?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, category);
			stmt.executeUpdate();
			System.out.println("Category added.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to add category.");
		}
	}
	// Deletes a category from the database
	public void handleDeleteCategory(Connection conn, Scanner scanner) {
		System.out.print("Enter category name to delete: ");
		String category = scanner.nextLine();
		String sql = "DELETE FROM Categories WHERE category = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, category);
			int rows = stmt.executeUpdate();
			if (rows > 0) System.out.println("Category deleted.");
			else System.out.println("Category not found or in use.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to delete category.");
		}
	}

	// Displays all orders from all users
	public void handleShowAllOrders(Connection conn) {
		String sql = "SELECT orderId, shippingAddress, totalPrice, orderDate, BuyerId FROM Orders";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			System.out.println("All Orders:");
			while (rs.next()) {
				System.out.printf("- Order #%d | Buyer ID: %d | Date: %s | Total: %.2f | Address: %s\n",
						rs.getInt("orderId"),
						rs.getInt("BuyerId"),
						rs.getDate("orderDate"),
						rs.getDouble("totalPrice"),
						rs.getString("shippingAddress"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Displays the total income from all orders
	public void handleIncomeReport(Connection conn) {
		String sql = "SELECT SUM(totalPrice) AS totalIncome FROM Orders";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				double total = rs.getDouble("totalIncome");
				System.out.printf("Total Income: %.2f\n", total);
			} else {
				System.out.println("No orders found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Adds a selected product to the buyer's cart after showing a numbered list of all available products
	public void handleAddToCart(Connection conn, Scanner scanner, Buyer buyer) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM Products";

		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			int index = 1;
			System.out.println("Available Products:");
			while (rs.next()) {
				int productId = rs.getInt("productId");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				String category = rs.getString("category");
				boolean specialPackaging = rs.getBoolean("specialPackaging");
				double packagingCost = rs.getDouble("packagingCost");

				Product product = new Product(productId, name, price, category, specialPackaging, packagingCost);
				products.add(product);

				System.out.printf("%d. %s | Price: %.2f₪ | Category: %s | Special Packaging: %s\n",
						index++, name, price, category, specialPackaging ? "Yes" : "No");
			}

			if (products.isEmpty()) {
				System.out.println("No products available.");
				return;
			}

			System.out.print("Select product number to add to cart: ");
			int selection = Integer.parseInt(scanner.nextLine());

			if (selection < 1 || selection > products.size()) {
				System.out.println("Invalid selection.");
				return;
			}

			Product selectedProduct = products.get(selection - 1);
			buyer.addToCart(selectedProduct, conn);
			System.out.println("Product added to cart.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to retrieve products.");
		}
	}


	// Displays the current buyer's cart contents
	public void handleDisplayCart(Connection conn, Buyer buyer) {
		buyer.displayCart(conn);
	}

	// Shows the total cost of items in the buyer's cart
	public void handleCartTotal(Connection conn, Buyer buyer) {
		buyer.viewCartTotal(conn);
	}

	// Completes the purchase process for the buyer
	public void handlePurchase(Connection conn, Buyer buyer) {
		buyer.completePurchase(conn);
	}

	// Displays the order history of a buyer
	public void handleOrderHistory(Connection conn, Buyer buyer) {
		Order[] orders = buyer.getOrderHistory(conn);
		if (orders.length == 0) {
			System.out.println("No past orders.");
			return;
		}
		System.out.println("Order History:");
		for (Order order : orders) {
			System.out.printf("Order #%d | Date: %s | Address: %s\n",
					order.getOrderId(), order.getOrderDate(), order.getShippingAddress());
			for (Product p : order.getProducts()) {
				System.out.println("  - " + p);
			}
		}
	}
	// Retrieves a product object from the database by its ID
	public Product findProductById(Connection conn, int productId) {
		String sql = "SELECT * FROM Products WHERE productId = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, productId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new Product(
						rs.getInt("productId"),
						rs.getString("name"),
						rs.getDouble("price"),
						rs.getString("category"),
						rs.getBoolean("specialPackaging"),
						rs.getDouble("packagingCost")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Shows detailed information of a specific product by name
	public void handleViewProductDetails(Connection conn, Scanner scanner) {
		System.out.print("Enter product name to view details: ");
		String productName = scanner.nextLine();
		String sql = "SELECT * FROM Products WHERE name = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, productName);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("Product Details:");
				System.out.println("- Name: " + rs.getString("name"));
				System.out.println("- Price: " + rs.getDouble("price"));
				System.out.println("- Category: " + rs.getString("category"));
				System.out.println("- Special Packaging: " + (rs.getBoolean("specialPackaging") ? "Yes" : "No"));
				System.out.println("- Packaging Cost: " + rs.getDouble("packagingCost"));
			} else {
				System.out.println("Product not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to fetch product details.");
		}
	}
	// Displays all products owned by the seller
	public void handleShowSellerProducts(Connection conn, Seller seller) {
		Product[] products = seller.getProducts(conn);
		System.out.println("Your Products:");
		for (Product p : products) {
			System.out.printf("- %s | Price: %.2f₪ | Category: %s | Special Packaging: %s | Packaging Cost: %.2f₪\n",
					p.getName(),
					p.getPrice(),
					p.getCategory().toString(),
					p.hasSpecialPackaging() ? "Yes" : "No",
					p.getPackagingCost());
		}
	}

	// Adds a new product to the system on behalf of a seller
	public void handleAddProductBySeller(Connection conn, Scanner scanner, Seller seller) {
		System.out.print("Enter product name: ");
		String name = scanner.nextLine();
		double price = readDoubleInput(scanner, "Enter product price: ");
		Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
		boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
		double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;

		Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);
		try {
			boolean added = seller.addProduct(product, conn);
			if (added) System.out.println("✅ Product added successfully.");
			else System.out.println("Failed to add product.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception while adding product: " + e.getMessage());
		}
	}

	// Calculates total revenue for the seller based on all sold products
	public void handleSellerRevenue(Connection conn, Seller seller) {
		String sql = "SELECT SUM(p.price + p.packagingCost) AS revenue FROM Orders o " +
				"JOIN OrderProduct op ON o.orderId = op.orderId " +
				"JOIN Products p ON op.productId = p.productId WHERE p.sellerId = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, seller.getUserId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				double total = rs.getDouble("revenue");
				System.out.printf("Total revenue: %.2f₪\n", total);
			} else {
				System.out.println("No revenue data found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to retrieve revenue.");
		}
	}

	// Displays all orders that contain products belonging to the seller
	public void handleSellerOrders(Connection conn, Seller seller) {
		String sql = "SELECT DISTINCT o.orderId, o.orderDate, o.shippingAddress FROM Orders o " +
				"JOIN OrderProduct op ON o.orderId = op.orderId " +
				"JOIN Products p ON op.productId = p.productId " +
				"WHERE p.sellerId = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, seller.getUserId());
			ResultSet rs = stmt.executeQuery();
			System.out.println("Orders containing your products:");
			while (rs.next()) {
				System.out.printf("- Order #%d | Date: %s | Address: %s\n",
						rs.getInt("orderId"),
						rs.getDate("orderDate"),
						rs.getString("shippingAddress"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to fetch seller orders.");
		}
	}

	// Logs in a buyer using username and password; returns Buyer object if successful
	public Buyer loginBuyer(Connection conn, Scanner scanner) {
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		String sql = "SELECT u.userId, b.address " +
				"FROM Users u JOIN Buyers b ON u.userId = b.BuyerId " +
				"WHERE u.username = ? AND u.password = ? AND u.userType = 'buyer'";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("userId");
				String address = rs.getString("address");
				System.out.println("Buyer login successful.");
				return new Buyer(username, password, userId, address);
			} else {
				System.out.println("🔄 Buyer not found, registering new one...");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to login as buyer.");
			return null;
		}
	}

	// Logs in a seller using username and password; returns Seller object if successful
	public Seller loginSeller(Connection conn, Scanner scanner) {
		System.out.print("Enter username: ");
		String username = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		String sql = "SELECT userId FROM Users WHERE username = ? AND password = ? AND userType = 'seller'";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("userId");
				System.out.println("Seller login successful.");
				return new Seller(username, password, userId);
			} else {
				System.out.println("Invalid credentials or user type.");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failed to login as seller.");
			return null;
		}
	}

}
