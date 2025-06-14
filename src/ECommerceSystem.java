////Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//public class ECommerceSystem {
//	//private Buyer[] buyers;
//	//private Seller[] sellers;
//	private Map<String, Buyer> buyerMap;
//	private Map<String, Seller> sellerMap;
//	private int buyerCount;
//	private int sellerCount;
//
//	public ECommerceSystem() {
//		//this.buyers = new Buyer[10]; // Initial capacity for buyers
//		//this.sellers = new Seller[10]; // Initial capacity for sellers
//		this.buyerMap = new HashMap<>();
//		this.sellerMap = new HashMap<>();
//		this.buyerCount = 0;
//		this.sellerCount = 0;
//	}
//
//	//public Buyer[] getBuyers() {
//		//return Arrays.copyOf(buyers, buyerCount);
//	//}
//
//	public Collection<Buyer> getBuyers() {
//		return buyerMap.values();
//	}
//
//	public Collection<Seller> getSellers() {
//		return sellerMap.values();
//	}
//
//
//	//public Seller[] getSellers() {
//		//return Arrays.copyOf(sellers, sellerCount);
//	//}
//
//	public boolean addBuyer(Buyer buyer) {
//		if (buyerMap.containsKey(buyer.getUsername())) {
//			return false; // קונה כבר קיים
//		}
//		buyerMap.put(buyer.getUsername(), buyer);
//		return true;
//	}
//
//	public boolean addSeller(Seller seller) {
//		if (sellerMap.containsKey(seller.getUsername())) {
//			return false; // מוכר כבר קיים
//		}
//		sellerMap.put(seller.getUsername(), seller);
//		return true;
//	}
//
//	public Buyer findBuyerByUsername(String username) {
//		return buyerMap.get(username);
//	}
//
//	public Seller findSellerByUsername(String username) {
//		return sellerMap.get(username);
//	}
//
//
////	public Product[] getProductsByCategory(Category category) {
////		List<Product> productList = new ArrayList<>();
////
////		for (Seller seller : sellerMap.values()) {
////			Product[] sellerProducts = seller.getProducts();
////			for (Product product : sellerProducts) {
////				if (product != null && product.getCategory() == category) {
////					productList.add(product);
////				}
////			}
////		}
////
////		return productList.toArray(new Product[0]);
////	}
//
//	public Product[] getProductsByCategory(Connection conn, Category category) {
//		List<Product> productList = new ArrayList<>();
//
//		String sql = "SELECT * FROM Products WHERE category = ?";
//
//		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setString(1, category.toString()); // משתמשים בשם התצוגה של ה־ENUM
//
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				Product product = new Product(
//						rs.getString("name"),
//						rs.getDouble("price"),
//						rs.getString("category"), // המרה חזרה ל־ENUM
//						rs.getBoolean("specialPackaging"),
//						rs.getDouble("packagingCost")
//				);
//				productList.add(product);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return productList.toArray(new Product[0]);
//	}
//
//
//	private Product[] addToArray(Product[] array, Product productToAdd) {
//		Product[] newArray = Arrays.copyOf(array, array.length + 1);
//		newArray[array.length] = productToAdd;
//		return newArray;
//	}
//}
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//public class ECommerceSystem {
//	private Map<String, Buyer> buyerMap;
//	private Map<String, Seller> sellerMap;
//
//	public ECommerceSystem() {
//		this.buyerMap = new HashMap<>();
//		this.sellerMap = new HashMap<>();
//	}
//
//	// מחזיר את כל הקונים בזיכרון
//	public Collection<Buyer> getBuyers() {
//		return buyerMap.values();
//	}
//
//	// מחזיר את כל המוכרים בזיכרון
//	public Collection<Seller> getSellers() {
//		return sellerMap.values();
//	}
//
//	// הוספת קונה חדש למערכת (אם לא קיים)
//	public boolean addBuyer(Buyer buyer) {
//		if (buyerMap.containsKey(buyer.getUsername())) {
//			return false;
//		}
//		buyerMap.put(buyer.getUsername(), buyer);
//		return true;
//	}
//
//	// הוספת מוכר חדש למערכת (אם לא קיים)
//	public boolean addSeller(Seller seller) {
//		if (sellerMap.containsKey(seller.getUsername())) {
//			return false;
//		}
//		sellerMap.put(seller.getUsername(), seller);
//		return true;
//	}
//
//	// חיפוש קונה לפי שם משתמש
//	public Buyer findBuyerByUsername(String username) {
//		return buyerMap.get(username);
//	}
//
//	// חיפוש מוכר לפי שם משתמש
//	public Seller findSellerByUsername(String username) {
//		return sellerMap.get(username);
//	}
//
//	// שליפת מוצרים לפי קטגוריה מה־DB
//	public Product[] getProductsByCategory(Connection conn, Category category) {
//		List<Product> productList = new ArrayList<>();
//		String sql = "SELECT * FROM Products WHERE category = ?";
//
//		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setString(1, category.toString());
//
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				Product product = new Product(
//						rs.getInt("productId"),                         // כולל ID מהמזהה
//						rs.getString("name"),
//						rs.getDouble("price"),
//						rs.getString("category"),
//						rs.getBoolean("specialPackaging"),
//						rs.getDouble("packagingCost")
//				);
//				productList.add(product);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return productList.toArray(new Product[0]);
//	}
//
//	// טעינת קונים קיימים מה־DB (לדוגמה בעת התחברות למערכת)
//	public void loadBuyersFromDB(Connection conn) {
//		String sql = "SELECT u.userId, u.username, u.password, b.address " +
//				"FROM Users u " +
//				"JOIN Buyers b ON u.userId = b.BuyerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Buyer buyer = new Buyer(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId"),
//						rs.getString("address")
//				);
//				buyerMap.put(buyer.getUsername(), buyer);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	// טעינת מוכרים קיימים מה־DB
//	public void loadSellersFromDB(Connection conn) {
//		String sql = "SELECT u.userId, u.username, u.password " +
//				"FROM Users u " +
//				"JOIN Sellers s ON u.userId = s.SellerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Seller seller = new Seller(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId")
//				);
//				sellerMap.put(seller.getUsername(), seller);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//}

//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//public class ECommerceSystem {
//	private Map<String, Buyer> buyerMap;
//	private Map<String, Seller> sellerMap;
//
//	public ECommerceSystem() {
//		this.buyerMap = new HashMap<>();
//		this.sellerMap = new HashMap<>();
//	}
//
//	// מחזיר את כל הקונים בזיכרון
//	public Collection<Buyer> getBuyers() {
//		return buyerMap.values();
//	}
//
//	// מחזיר את כל המוכרים בזיכרון
//	public Collection<Seller> getSellers() {
//		return sellerMap.values();
//	}
//
//	// הוספת קונה חדש למערכת ול־DB
//	public boolean addBuyer(Buyer buyer, Connection conn) {
//		if (buyerMap.containsKey(buyer.getUsername())) {
//			return false;
//		}
//		try {
//			// Insert into Users
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'buyer') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, buyer.getUsername());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					buyer.setUserId(userId);
//
//					// Insert into Buyers
//					String buyerSql = "INSERT INTO Buyers (BuyerId, address) VALUES (?, ?)";
//					try (PreparedStatement buyerStmt = conn.prepareStatement(buyerSql)) {
//						buyerStmt.setInt(1, userId);
//						buyerStmt.setString(2, buyer.getAddress());
//						buyerStmt.executeUpdate();
//					}
//
//					// Add to memory map
//					buyerMap.put(buyer.getUsername(), buyer);
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// הוספת מוכר חדש למערכת ול־DB
//	public boolean addSeller(Seller seller, Connection conn) {
//		if (sellerMap.containsKey(seller.getUsername())) {
//			return false;
//		}
//		try {
//			// Insert into Users
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'seller') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, seller.getUsername());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					seller.setUserId(userId);
//
//					// Insert into Sellers
//					String sellerSql = "INSERT INTO Sellers (SellerId) VALUES (?)";
//					try (PreparedStatement sellerStmt = conn.prepareStatement(sellerSql)) {
//						sellerStmt.setInt(1, userId);
//						sellerStmt.executeUpdate();
//					}
//
//					// Add to memory map
//					sellerMap.put(seller.getUsername(), seller);
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// חיפוש קונה לפי שם משתמש
//	public Buyer findBuyerByUsername(String username) {
//		return buyerMap.get(username);
//	}
//
//	// חיפוש מוכר לפי שם משתמש
//	public Seller findSellerByUsername(String username) {
//		return sellerMap.get(username);
//	}
//
//	// שליפת מוצרים לפי קטגוריה מה־DB
//	public Product[] getProductsByCategory(Connection conn, Category category) {
//		List<Product> productList = new ArrayList<>();
//		String sql = "SELECT * FROM Products WHERE category = ?";
//
//		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setString(1, category.toString());
//
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				Product product = new Product(
//						rs.getInt("productId"),
//						rs.getString("name"),
//						rs.getDouble("price"),
//						rs.getString("category"),
//						rs.getBoolean("specialPackaging"),
//						rs.getDouble("packagingCost")
//				);
//				productList.add(product);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return productList.toArray(new Product[0]);
//	}
//
//	// טעינת קונים קיימים מה־DB
//	public void loadBuyersFromDB(Connection conn) {
//		String sql = "SELECT u.userId, u.username, u.password, b.address " +
//				"FROM Users u " +
//				"JOIN Buyers b ON u.userId = b.BuyerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Buyer buyer = new Buyer(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId"),
//						rs.getString("address")
//				);
//				buyerMap.put(buyer.getUsername(), buyer);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	// טעינת מוכרים קיימים מה־DB
//	public void loadSellersFromDB(Connection conn) {
//		String sql = "SELECT u.userId, u.username, u.password " +
//				"FROM Users u " +
//				"JOIN Sellers s ON u.userId = s.SellerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Seller seller = new Seller(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId")
//				);
//				sellerMap.put(seller.getUsername(), seller);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//import java.sql.*;
//import java.util.*;
//
//public class ECommerceSystem {
//	private Map<String, Buyer> buyerMap;
//	private Map<String, Seller> sellerMap;
//
//	public ECommerceSystem() {
//		this.buyerMap = new HashMap<>();
//		this.sellerMap = new HashMap<>();
//	}
//
//	public Collection<Buyer> getBuyers() {
//		return buyerMap.values();
//	}
//
//	public Collection<Seller> getSellers() {
//		return sellerMap.values();
//	}
//
//	public Buyer findBuyerByUsername(String username) {
//		return buyerMap.get(username);
//	}
//
//	public Seller findSellerByUsername(String username) {
//		return sellerMap.get(username);
//	}
//
//	// ✅ הוספת קונה כולל כתיבה ל־DB
//	public boolean addBuyer(Buyer buyer, Connection conn) {
//		if (buyerMap.containsKey(buyer.getUsername())) return false;
//
//		try {
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'buyer') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, buyer.getUsername());
//				userStmt.setString(2, buyer.getPassword());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					buyer.setUserId(userId);
//
//					String buyerSql = "INSERT INTO Buyers (BuyerId, address) VALUES (?, ?)";
//					try (PreparedStatement buyerStmt = conn.prepareStatement(buyerSql)) {
//						buyerStmt.setInt(1, userId);
//						buyerStmt.setString(2, buyer.getAddress());
//						buyerStmt.executeUpdate();
//					}
//
//					buyerMap.put(buyer.getUsername(), buyer);
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// ✅ הוספת מוכר כולל כתיבה ל־DB
//	public boolean addSeller(Seller seller, Connection conn) {
//		if (sellerMap.containsKey(seller.getUsername())) return false;
//
//		try {
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'seller') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, seller.getUsername());
//				userStmt.setString(2, seller.getPassword());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					seller.setUserId(userId);
//
//					String sellerSql = "INSERT INTO Sellers (SellerId) VALUES (?)";
//					try (PreparedStatement sellerStmt = conn.prepareStatement(sellerSql)) {
//						sellerStmt.setInt(1, userId);
//						sellerStmt.executeUpdate();
//					}
//
//					sellerMap.put(seller.getUsername(), seller);
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// ✅ טעינת קונים
//	public void loadBuyersFromDB(Connection conn) {
//		String sql = "SELECT u.userId, u.username, u.password, b.address " +
//				"FROM Users u JOIN Buyers b ON u.userId = b.BuyerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Buyer buyer = new Buyer(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId"),
//						rs.getString("address")
//				);
//				buyerMap.put(buyer.getUsername(), buyer);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	// ✅ טעינת מוכרים
//	public void loadSellersFromDB(Connection conn) {
//		String sql = "SELECT u.userId, u.username, u.password " +
//				"FROM Users u JOIN Sellers s ON u.userId = s.SellerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Seller seller = new Seller(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId")
//				);
//				sellerMap.put(seller.getUsername(), seller);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	// ✅ טיפול בהוספת קונה מתוך Main
//	public void handleAddBuyer(Connection conn, Scanner scanner) {
//		System.out.print("Enter buyer username: ");
//		String username = scanner.nextLine();
//		if (findBuyerByUsername(username) != null) {
//			System.out.println("Username already exists.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//		System.out.print("Enter address: ");
//		String address = scanner.nextLine();
//
//		Buyer buyer = new Buyer(username, password, -1, address);
//		if (addBuyer(buyer, conn)) {
//			System.out.println("✅ Buyer added successfully.");
//		} else {
//			System.out.println("❌ Failed to add buyer.");
//		}
//	}
//
//	// ✅ טיפול בהוספת מוכר מתוך Main
//	public void handleAddSeller(Connection conn, Scanner scanner) {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		if (findSellerByUsername(username) != null) {
//			System.out.println("Username already exists.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//
//		Seller seller = new Seller(username, password, -1);
//		if (addSeller(seller, conn)) {
//			System.out.println("✅ Seller added successfully.");
//		} else {
//			System.out.println("❌ Failed to add seller.");
//		}
//	}
//
//	// ✅ טיפול בהוספת מוצר למוכר
//	public void handleAddProductToSeller(Connection conn, Scanner scanner) {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		Seller seller = findSellerByUsername(username);
//		if (seller == null) {
//			System.out.println("Seller not found.");
//			return;
//		}
//
//		System.out.print("Enter product name: ");
//		String name = scanner.nextLine();
//
//		double price = readDoubleInput(scanner, "Enter product price: ");
//		Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS,TOYS): ");
//		boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
//		double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;
//
//		Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);
//		seller.addProduct(product, conn);
//		System.out.println("✅ Product added successfully.");
//	}
//
//	// פונקציות עזר לקריאת קלט
//	private double readDoubleInput(Scanner scanner, String prompt) {
//		while (true) {
//			System.out.print(prompt);
//			try {
//				double val = Double.parseDouble(scanner.nextLine());
//				return val;
//			} catch (NumberFormatException e) {
//				System.out.println("Invalid number. Try again.");
//			}
//		}
//	}
//
//	Category readCategoryInput(Scanner scanner, String prompt) {
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
//	private boolean readBooleanInput(Scanner scanner, String prompt) {
//		System.out.print(prompt);
//		String input = scanner.nextLine().trim().toLowerCase();
//		return input.equals("yes") || input.equals("y");
//	}
//
//	public Product[] getProductsByCategory(Connection conn, Category category) {
//		List<Product> productList = new ArrayList<>();
//		String sql = "SELECT * FROM Products WHERE category = ?";
//
//		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setString(1, category.getDisplayName()); // חשוב! לפי displayName שמתאים לערכי ה־DB
//
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				Product product = new Product(
//						rs.getInt("productId"),
//						rs.getString("name"),
//						rs.getDouble("price"),
//						rs.getString("category"),
//						rs.getBoolean("specialPackaging"),
//						rs.getDouble("packagingCost")
//				);
//				productList.add(product);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return productList.toArray(new Product[0]);
//	}
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
//}

//
//import java.sql.*;
//import java.util.*;
//
//public class ECommerceSystem {
//	private Map<String, Buyer> buyerMap;
//	private Map<String, Seller> sellerMap;
//
//	public ECommerceSystem() {
//		this.buyerMap = new HashMap<>();
//		this.sellerMap = new HashMap<>();
//	}
//
//	public Collection<Buyer> getBuyers(Connection conn) {
//		loadBuyersFromDB(conn);
//		return buyerMap.values();
//	}
//
//	public Collection<Seller> getSellers(Connection conn) {
//		loadSellersFromDB(conn);
//		return sellerMap.values();
//	}
//
//	public Buyer findBuyerByUsername(String username, Connection conn) {
//		loadBuyersFromDB(conn);
//		return buyerMap.get(username);
//	}
//
//	public Seller findSellerByUsername(String username, Connection conn) {
//		loadSellersFromDB(conn);
//		return sellerMap.get(username);
//	}
//
//	public boolean addBuyer(Buyer buyer, Connection conn) {
//		try {
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'buyer') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, buyer.getUsername());
//				userStmt.setString(2, buyer.getPassword());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					buyer.setUserId(userId);
//
//					String buyerSql = "INSERT INTO Buyers (BuyerId, address) VALUES (?, ?)";
//					try (PreparedStatement buyerStmt = conn.prepareStatement(buyerSql)) {
//						buyerStmt.setInt(1, userId);
//						buyerStmt.setString(2, buyer.getAddress());
//						buyerStmt.executeUpdate();
//					}
//
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	public boolean addSeller(Seller seller, Connection conn) {
//		try {
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'seller') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, seller.getUsername());
//				userStmt.setString(2, seller.getPassword());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					seller.setUserId(userId);
//
//					String sellerSql = "INSERT INTO Sellers (SellerId) VALUES (?)";
//					try (PreparedStatement sellerStmt = conn.prepareStatement(sellerSql)) {
//						sellerStmt.setInt(1, userId);
//						sellerStmt.executeUpdate();
//					}
//
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	private void loadBuyersFromDB(Connection conn) {
//		buyerMap.clear();
//		String sql = "SELECT u.userId, u.username, u.password, b.address " +
//				"FROM Users u JOIN Buyers b ON u.userId = b.BuyerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Buyer buyer = new Buyer(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId"),
//						rs.getString("address")
//				);
//				buyerMap.put(buyer.getUsername(), buyer);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void loadSellersFromDB(Connection conn) {
//		sellerMap.clear();
//		String sql = "SELECT u.userId, u.username, u.password " +
//				"FROM Users u JOIN Sellers s ON u.userId = s.SellerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Seller seller = new Seller(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId")
//				);
//				sellerMap.put(seller.getUsername(), seller);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public Product[] getProductsByCategory(Connection conn, Category category) {
//		List<Product> productList = new ArrayList<>();
//		String sql = "SELECT * FROM Products WHERE category = ?";
//
//		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setString(1, category.getDisplayName());
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				Product product = new Product(
//						rs.getInt("productId"),
//						rs.getString("name"),
//						rs.getDouble("price"),
//						rs.getString("category"),
//						rs.getBoolean("specialPackaging"),
//						rs.getDouble("packagingCost")
//				);
//				productList.add(product);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return productList.toArray(new Product[0]);
//	}
//}

//import java.sql.*;
//		import java.util.*;
//
//public class ECommerceSystem {
//	private Map<String, Buyer> buyerMap;
//	private Map<String, Seller> sellerMap;
//
//	public ECommerceSystem() {
//		this.buyerMap = new HashMap<>();
//		this.sellerMap = new HashMap<>();
//	}
//
//	public Collection<Buyer> getBuyers(Connection conn) {
//		loadBuyersFromDB(conn);
//		return buyerMap.values();
//	}
//
//	public Collection<Seller> getSellers(Connection conn) {
//		loadSellersFromDB(conn);
//		return sellerMap.values();
//	}
//
//	public Buyer findBuyerByUsername(String username, Connection conn) {
//		loadBuyersFromDB(conn);
//		return buyerMap.get(username);
//	}
//
//	public Seller findSellerByUsername(String username, Connection conn) {
//		loadSellersFromDB(conn);
//		return sellerMap.get(username);
//	}
//
//	public boolean addBuyer(Buyer buyer, Connection conn) {
//		try {
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'buyer') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, buyer.getUsername());
//				userStmt.setString(2, buyer.getPassword());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					buyer.setUserId(userId);
//
//					String buyerSql = "INSERT INTO Buyers (BuyerId, address) VALUES (?, ?)";
//					try (PreparedStatement buyerStmt = conn.prepareStatement(buyerSql)) {
//						buyerStmt.setInt(1, userId);
//						buyerStmt.setString(2, buyer.getAddress());
//						buyerStmt.executeUpdate();
//					}
//
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	public boolean addSeller(Seller seller, Connection conn) {
//		try {
//			String userSql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'seller') RETURNING userId";
//			try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
//				userStmt.setString(1, seller.getUsername());
//				userStmt.setString(2, seller.getPassword());
//
//				ResultSet rs = userStmt.executeQuery();
//				if (rs.next()) {
//					int userId = rs.getInt("userId");
//					seller.setUserId(userId);
//
//					String sellerSql = "INSERT INTO Sellers (SellerId) VALUES (?)";
//					try (PreparedStatement sellerStmt = conn.prepareStatement(sellerSql)) {
//						sellerStmt.setInt(1, userId);
//						sellerStmt.executeUpdate();
//					}
//
//					return true;
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	private void loadBuyersFromDB(Connection conn) {
//		buyerMap.clear();
//		String sql = "SELECT u.userId, u.username, u.password, b.address " +
//				"FROM Users u JOIN Buyers b ON u.userId = b.BuyerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Buyer buyer = new Buyer(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId"),
//						rs.getString("address")
//				);
//				buyerMap.put(buyer.getUsername(), buyer);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void loadSellersFromDB(Connection conn) {
//		sellerMap.clear();
//		String sql = "SELECT u.userId, u.username, u.password " +
//				"FROM Users u JOIN Sellers s ON u.userId = s.SellerId";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//
//			while (rs.next()) {
//				Seller seller = new Seller(
//						rs.getString("username"),
//						rs.getString("password"),
//						rs.getInt("userId")
//				);
//				sellerMap.put(seller.getUsername(), seller);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public Product[] getProductsByCategory(Connection conn, Category category) {
//		List<Product> productList = new ArrayList<>();
//		String sql = "SELECT * FROM Products WHERE category = ?";
//
//		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//			stmt.setString(1, category.getDisplayName());
//			ResultSet rs = stmt.executeQuery();
//			while (rs.next()) {
//				Product product = new Product(
//						rs.getInt("productId"),
//						rs.getString("name"),
//						rs.getDouble("price"),
//						rs.getString("category"),
//						rs.getBoolean("specialPackaging"),
//						rs.getDouble("packagingCost")
//				);
//				productList.add(product);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return productList.toArray(new Product[0]);
//	}
//
//	public void handleAddBuyer(Connection conn, Scanner scanner) {
//		System.out.print("Enter buyer username: ");
//		String username = scanner.nextLine();
//		if (findBuyerByUsername(username, conn) != null) {
//			System.out.println("Username already exists.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//		System.out.print("Enter address: ");
//		String address = scanner.nextLine();
//
//		Buyer buyer = new Buyer(username, password, -1, address);
//		if (addBuyer(buyer, conn)) {
//			System.out.println("✅ Buyer added successfully.");
//		} else {
//			System.out.println("❌ Failed to add buyer.");
//		}
//	}
//
//	public void handleAddSeller(Connection conn, Scanner scanner) {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		if (findSellerByUsername(username, conn) != null) {
//			System.out.println("Username already exists.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//
//		Seller seller = new Seller(username, password, -1);
//		if (addSeller(seller, conn)) {
//			System.out.println("✅ Seller added successfully.");
//		} else {
//			System.out.println("❌ Failed to add seller.");
//		}
//	}
//
//	public void handleAddProductToSeller(Connection conn, Scanner scanner) {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		Seller seller = findSellerByUsername(username, conn);
//		if (seller == null) {
//			System.out.println("Seller not found.");
//			return;
//		}
//
//		System.out.print("Enter product name: ");
//		String name = scanner.nextLine();
//		double price = readDoubleInput(scanner, "Enter product price: ");
//		Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
//		boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
//		double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;
//
//		Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);
//		seller.addProduct(product, conn);
//		System.out.println("✅ Product added successfully.");
//	}
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
//}

import java.sql.*;
import java.util.*;

public class ECommerceSystem {
	private Map<String, Buyer> buyerMap;
	private Map<String, Seller> sellerMap;

	public ECommerceSystem() {
		this.buyerMap = new HashMap<>();
		this.sellerMap = new HashMap<>();
	}

	public Collection<Buyer> getBuyers(Connection conn) {
		loadBuyersFromDB(conn);
		return buyerMap.values();
	}

	public Collection<Seller> getSellers(Connection conn) {
		loadSellersFromDB(conn);
		return sellerMap.values();
	}

	public Buyer findBuyerByUsername(String username, Connection conn) {
		loadBuyersFromDB(conn);
		return buyerMap.get(username);
	}

	public Seller findSellerByUsername(String username, Connection conn) {
		loadSellersFromDB(conn);
		return sellerMap.get(username);
	}

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
			System.out.println("✅ Buyer added successfully.");
		} else {
			System.out.println("❌ Failed to add buyer.");
		}
		return buyer;
	}

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
			System.out.println("✅ Seller added successfully.");
		} else {
			System.out.println("❌ Failed to add seller.");
		}
		return seller;
	}

//	public void handleAddProductToSeller(Connection conn, Scanner scanner) {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		Seller seller = findSellerByUsername(username, conn);
//		if (seller == null) {
//			System.out.println("Seller not found.");
//			return;
//		}
//
//		System.out.print("Enter product name: ");
//		String name = scanner.nextLine();
//		double price = readDoubleInput(scanner, "Enter product price: ");
//		Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
//		boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
//		double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;
//
//		Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);
//		seller.addProduct(product, conn);
//		System.out.println("✅ Product added successfully.");
//	}
public void handleAddProductToSeller(Connection conn, Scanner scanner) {
	System.out.print("Enter seller username: ");
	String username = scanner.nextLine();
	Seller seller = findSellerByUsername(username, conn);
	if (seller == null) {
		System.out.println("❌ Seller not found.");
		return;
	}

	System.out.print("Enter product name: ");
	String name = scanner.nextLine();
	double price = readDoubleInput(scanner, "Enter product price: ");
	Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS, TOYS): ");
	boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
	double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;

	Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);

	boolean success = seller.addProduct(product, conn); // שימוש בפונקציה שמחזירה true/false

	if (success) {
		System.out.println("✅ Product added successfully.");
	} else {
		System.out.println("❌ Failed to add product.");
	}
}


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

	private boolean readBooleanInput(Scanner scanner, String prompt) {
		System.out.print(prompt);
		String input = scanner.nextLine().trim().toLowerCase();
		return input.equals("yes") || input.equals("y");
	}

	// ✅ פונקציות אדמין חדשות:

	public void handleDeleteSeller(Connection conn, Scanner scanner) {
		System.out.print("Enter seller username to delete: ");
		String username = scanner.nextLine();
		Seller seller = findSellerByUsername(username, conn);
		if (seller == null) {
			System.out.println("❌ Seller not found.");
			return;
		}
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userId = ?")) {
			stmt.setInt(1, seller.getUserId());
			stmt.executeUpdate();
			System.out.println("✅ Seller deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ Failed to delete seller.");
		}
	}

	public void handleDeleteBuyer(Connection conn, Scanner scanner) {
		System.out.print("Enter buyer username to delete: ");
		String username = scanner.nextLine();
		Buyer buyer = findBuyerByUsername(username, conn);
		if (buyer == null) {
			System.out.println("❌ Buyer not found.");
			return;
		}
		try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE userId = ?")) {
			stmt.setInt(1, buyer.getUserId());
			stmt.executeUpdate();
			System.out.println("✅ Buyer deleted.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ Failed to delete buyer.");
		}
	}

//	public void handleShowAllProducts(Connection conn) {
//		String sql = "SELECT * FROM Products";
//		try (PreparedStatement stmt = conn.prepareStatement(sql);
//			 ResultSet rs = stmt.executeQuery()) {
//			System.out.println("📦 All Products:");
//			while (rs.next()) {
//				System.out.printf("- %s | Price: %.2f | Category: %s | Special Packaging: %s\n",
//						rs.getString("name"),
//						rs.getDouble("price"),
//						rs.getString("category"),
//						rs.getBoolean("specialPackaging") ? "Yes" : "No");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
public void handleShowAllProducts(Connection conn) {
	String sql = "SELECT * FROM Products";
	try (PreparedStatement stmt = conn.prepareStatement(sql);
		 ResultSet rs = stmt.executeQuery()) {

		System.out.println("📦 All Products:");
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

	public void handleShowAllCategories(Connection conn) {
		String sql = "SELECT category FROM Categories";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			System.out.println("📚 Categories:");
			while (rs.next()) {
				System.out.println("- " + rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void handleAddCategory(Connection conn, Scanner scanner) {
		System.out.print("Enter new category name: ");
		String category = scanner.nextLine();
		String sql = "INSERT INTO Categories (category) VALUES (?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, category);
			stmt.executeUpdate();
			System.out.println("✅ Category added.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ Failed to add category.");
		}
	}

	public void handleDeleteCategory(Connection conn, Scanner scanner) {
		System.out.print("Enter category name to delete: ");
		String category = scanner.nextLine();
		String sql = "DELETE FROM Categories WHERE category = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, category);
			int rows = stmt.executeUpdate();
			if (rows > 0) System.out.println("✅ Category deleted.");
			else System.out.println("⚠️ Category not found or in use.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ Failed to delete category.");
		}
	}

	public void handleShowAllOrders(Connection conn) {
		String sql = "SELECT orderId, shippingAddress, totalPrice, orderDate, BuyerId FROM Orders";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			System.out.println("📦 All Orders:");
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

	public void handleIncomeReport(Connection conn) {
		String sql = "SELECT SUM(totalPrice) AS totalIncome FROM Orders";
		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			if (rs.next()) {
				double total = rs.getDouble("totalIncome");
				System.out.printf("💰 Total Income: %.2f\n", total);
			} else {
				System.out.println("No orders found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	// הוספת מוצר לעגלת הקונה
//	public void handleAddToCart(Connection conn, Scanner scanner, Buyer buyer) {
//		System.out.print("Enter product ID to add to cart: ");
//		int productId = Integer.parseInt(scanner.nextLine());
//		Product product = findProductById(conn, productId);
//		if (product != null) {
//			buyer.addToCart(product, conn);
//		} else {
//			System.out.println("❌ Product not found.");
//		}
//	}

	// הצגת כל המוצרים ובחירה לפי מספור פנימי (ולא productId ישיר)
	public void handleAddToCart(Connection conn, Scanner scanner, Buyer buyer) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM Products";

		try (PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			int index = 1;
			System.out.println("🛍️ Available Products:");
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
				System.out.println("❌ No products available.");
				return;
			}

			System.out.print("Select product number to add to cart: ");
			int selection = Integer.parseInt(scanner.nextLine());

			if (selection < 1 || selection > products.size()) {
				System.out.println("❌ Invalid selection.");
				return;
			}

			Product selectedProduct = products.get(selection - 1);
			buyer.addToCart(selectedProduct, conn);
			System.out.println("✅ Product added to cart.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ Failed to retrieve products.");
		}
	}


	// הצגת עגלת הקונה
	public void handleDisplayCart(Connection conn, Buyer buyer) {
		buyer.displayCart(conn);
	}

	// הצגת סה"כ עגלת קונה
	public void handleCartTotal(Connection conn, Buyer buyer) {
		buyer.viewCartTotal(conn);
	}

	// ביצוע רכישה
	public void handlePurchase(Connection conn, Buyer buyer) {
		buyer.completePurchase(conn);
	}

	// הצגת היסטוריית הזמנות
	public void handleOrderHistory(Connection conn, Buyer buyer) {
		Order[] orders = buyer.getOrderHistory(conn);
		if (orders.length == 0) {
			System.out.println("📭 No past orders.");
			return;
		}
		System.out.println("📜 Order History:");
		for (Order order : orders) {
			System.out.printf("📦 Order #%d | Date: %s | Address: %s\n",
					order.getOrderId(), order.getOrderDate(), order.getShippingAddress());
			for (Product p : order.getProducts()) {
				System.out.println("  - " + p);
			}
		}
	}
	// חיפוש מוצר לפי מזהה
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
	public void handleViewProductDetails(Connection conn, Scanner scanner) {
		System.out.print("Enter product name to view details: ");
		String productName = scanner.nextLine();
		String sql = "SELECT * FROM Products WHERE name = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, productName);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				System.out.println("📦 Product Details:");
				System.out.println("- Name: " + rs.getString("name"));
				System.out.println("- Price: " + rs.getDouble("price"));
				System.out.println("- Category: " + rs.getString("category"));
				System.out.println("- Special Packaging: " + (rs.getBoolean("specialPackaging") ? "Yes" : "No"));
				System.out.println("- Packaging Cost: " + rs.getDouble("packagingCost"));
			} else {
				System.out.println("❌ Product not found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("❌ Failed to fetch product details.");
		}
	}


}
