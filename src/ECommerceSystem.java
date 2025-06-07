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

import java.sql.*;
import java.util.*;

public class ECommerceSystem {
	private Map<String, Buyer> buyerMap;
	private Map<String, Seller> sellerMap;

	public ECommerceSystem() {
		this.buyerMap = new HashMap<>();
		this.sellerMap = new HashMap<>();
	}

	public Collection<Buyer> getBuyers() {
		return buyerMap.values();
	}

	public Collection<Seller> getSellers() {
		return sellerMap.values();
	}

	public Buyer findBuyerByUsername(String username) {
		return buyerMap.get(username);
	}

	public Seller findSellerByUsername(String username) {
		return sellerMap.get(username);
	}

	// ✅ הוספת קונה כולל כתיבה ל־DB
	public boolean addBuyer(Buyer buyer, Connection conn) {
		if (buyerMap.containsKey(buyer.getUsername())) return false;

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

					buyerMap.put(buyer.getUsername(), buyer);
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// ✅ הוספת מוכר כולל כתיבה ל־DB
	public boolean addSeller(Seller seller, Connection conn) {
		if (sellerMap.containsKey(seller.getUsername())) return false;

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

					sellerMap.put(seller.getUsername(), seller);
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// ✅ טעינת קונים
	public void loadBuyersFromDB(Connection conn) {
		String sql = "SELECT u.userId, u.username, u.password, b.address " +
				"FROM Users u JOIN Buyers b ON u.userId = b.BuyerId";
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

	// ✅ טעינת מוכרים
	public void loadSellersFromDB(Connection conn) {
		String sql = "SELECT u.userId, u.username, u.password " +
				"FROM Users u JOIN Sellers s ON u.userId = s.SellerId";
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

	// ✅ טיפול בהוספת קונה מתוך Main
	public void handleAddBuyer(Connection conn, Scanner scanner) {
		System.out.print("Enter buyer username: ");
		String username = scanner.nextLine();
		if (findBuyerByUsername(username) != null) {
			System.out.println("Username already exists.");
			return;
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
	}

	// ✅ טיפול בהוספת מוכר מתוך Main
	public void handleAddSeller(Connection conn, Scanner scanner) {
		System.out.print("Enter seller username: ");
		String username = scanner.nextLine();
		if (findSellerByUsername(username) != null) {
			System.out.println("Username already exists.");
			return;
		}
		System.out.print("Enter password: ");
		String password = scanner.nextLine();

		Seller seller = new Seller(username, password, -1);
		if (addSeller(seller, conn)) {
			System.out.println("✅ Seller added successfully.");
		} else {
			System.out.println("❌ Failed to add seller.");
		}
	}

	// ✅ טיפול בהוספת מוצר למוכר
	public void handleAddProductToSeller(Connection conn, Scanner scanner) {
		System.out.print("Enter seller username: ");
		String username = scanner.nextLine();
		Seller seller = findSellerByUsername(username);
		if (seller == null) {
			System.out.println("Seller not found.");
			return;
		}

		System.out.print("Enter product name: ");
		String name = scanner.nextLine();

		double price = readDoubleInput(scanner, "Enter product price: ");
		Category category = readCategoryInput(scanner, "Enter category (BOOKS, CLOTHING, ELECTRONICS,TOYS): ");
		boolean specialPackaging = readBooleanInput(scanner, "Does the product require special packaging? (yes/no): ");
		double packagingCost = specialPackaging ? readDoubleInput(scanner, "Enter packaging cost: ") : 0;

		Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);
		seller.addProduct(product, conn);
		System.out.println("✅ Product added successfully.");
	}

	// פונקציות עזר לקריאת קלט
	private double readDoubleInput(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				double val = Double.parseDouble(scanner.nextLine());
				return val;
			} catch (NumberFormatException e) {
				System.out.println("Invalid number. Try again.");
			}
		}
	}

	private Category readCategoryInput(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Category.valueOf(scanner.nextLine().trim().toUpperCase());
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid category. Try again.");
			}
		}
	}

	private boolean readBooleanInput(Scanner scanner, String prompt) {
		System.out.print(prompt);
		String input = scanner.nextLine().trim().toLowerCase();
		return input.equals("yes") || input.equals("y");
	}
}
