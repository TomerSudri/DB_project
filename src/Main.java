////Submitters: Fabiana Daeem-211914783 , Tomer Sudri-316466432
//
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class Main {
//	private static ECommerceSystem system = new ECommerceSystem();
//	private static Scanner scanner = new Scanner(System.in);
//
//	public static void main(String[] args) {
//		int choice;
//		do {
//			printMenu();
//			while (!scanner.hasNextInt()) {
//				System.out.println("Invalid input. Please enter a number.");
//				scanner.next();
//			}
//			choice = scanner.nextInt();
//			scanner.nextLine();
//			try {
//				switch (choice) {
//				case 1:
//					addSeller();
//					break;
//				case 2:
//					addBuyer();
//					break;
//				case 3:
//					addProductToSeller();
//					break;
//				case 4:
//					addProductToBuyerCart();
//					break;
//				case 5:
//					checkoutBuyerCart();
//					break;
//				case 6:
//					displayBuyers();
//					break;
//				case 7:
//					displaySellers();
//					break;
//				case 8:
//					displayProductsByCategory();
//					break;
//				case 9:
//					createNewCartFromHistory();
//					break;
//				case 0:
//					System.out.println("Exiting...");
//					break;
//				default:
//					System.out.println("Invalid choice. Try again.");
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input. Please enter a valid number.");
//				scanner.nextLine();
//			} catch (EmptyCartException e) {
//				System.out.println(e.getMessage());
//			}
//		} while (choice != 0);
//		scanner.close();
//	}
//
//	private static void printMenu() {
//		System.out.println("\nMenu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - Add Seller");
//		System.out.println("2 - Add Buyer");
//		System.out.println("3 - Add Product to Seller");
//		System.out.println("4 - Add Product to Buyer Cart");
//		System.out.println("5 - Checkout Buyer Cart");
//		System.out.println("6 - Display all buyers sorted by username");
//		System.out.println("7 - Display all sellers sorted by number of products");
//		System.out.println("8 - Display Products by Category");
//		System.out.println("9 - Create New Cart from History");
//		System.out.print("Enter your choice: ");
//	}
//
//	private static void addSeller() {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		if (system.findSellerByUsername(username) != null) {
//			System.out.println("Username already exists. Try another.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//		Seller seller = new Seller(username, password);
//		system.addSeller(seller);
//		System.out.println("Seller added successfully.");
//	}
//
//	private static void addBuyer() {
//		System.out.print("Enter buyer username: ");
//		String username = scanner.nextLine();
//		if (system.findBuyerByUsername(username) != null) {
//			System.out.println("Username already exists. Try another.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//		System.out.print("Enter address: ");
//		String address = scanner.nextLine();
//		Buyer buyer = new Buyer(username, password, address);
//		system.addBuyer(buyer);
//		System.out.println("Buyer added successfully.");
//	}
//
//	private static void addProductToSeller() {
//		System.out.print("Enter seller username: ");
//		String sellerUsername = scanner.nextLine();
//		Seller seller = system.findSellerByUsername(sellerUsername);
//		if (seller == null) {
//			System.out.println("Seller not found.");
//			return;
//		}
//		System.out.print("Enter product name: ");
//		String name = scanner.nextLine();
//		double price = readDoubleInput("Enter product price: ");
//		Category category = readCategoryInput("Enter category (CHILDREN, ELECTRONICS, OFFICE, CLOTHING): ");
//		boolean specialPackaging = readBooleanInput("Does the product require special packaging? (yes/no): ");
//		double packagingCost = specialPackaging ? readDoubleInput("Enter packaging cost: ") : 0;
//		Product product = new Product(name, price, category, specialPackaging, packagingCost);
//		seller.addProduct(product);
//		System.out.println("Product added successfully.");
//	}
//
//	private static double readDoubleInput(String prompt) {
//		double input;
//		while (true) {
//			System.out.print(prompt);
//			try {
//				input = scanner.nextDouble();
//				scanner.nextLine(); // consume newline
//				break;
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input. Please enter a valid number.");
//				scanner.nextLine(); // consume invalid input
//			}
//		}
//		return input;
//	}
//
//	private static Category readCategoryInput(String prompt) {
//		Category category;
//		while (true) {
//			System.out.print(prompt);
//			try {
//				category = Category.valueOf(scanner.nextLine().toUpperCase());
//				break;
//			} catch (IllegalArgumentException e) {
//				System.out.println("Invalid category. Please enter one of CHILDREN, ELECTRONICS, OFFICE, CLOTHING.");
//			}
//		}
//		return category;
//	}
//
//	private static boolean readBooleanInput(String prompt) {
//		System.out.print(prompt);
//		return scanner.nextLine().equalsIgnoreCase("yes");
//	}
//
//	private static void addProductToBuyerCart() {
//		System.out.print("Enter buyer username: ");
//		String buyerUsername = scanner.nextLine();
//		Buyer buyer = system.findBuyerByUsername(buyerUsername);
//		if (buyer == null) {
//			System.out.println("Buyer not found.");
//			return;
//		}
//
//		Product[] allProducts = getAllProducts();
//		if (allProducts.length == 0) {
//			System.out.println("No products available.");
//			return;
//		}
//
//		displayProducts(allProducts);
//		int serialNumber = readSerialNumberInput("Enter product serial number to add to cart: ");
//		Product product = findProductBySerialNumber(serialNumber, allProducts);
//		if (product == null) {
//			System.out.println("Product not found.");
//			return;
//		}
//
//		buyer.getCart().addProduct(product);
//		System.out.println("Product added to cart successfully.");
//	}
//
//	private static Product findProductBySerialNumber(int serialNumber, Product[] products) {
//		for (Product product : products) {
//			if (product.getSerialNumber() == serialNumber) {
//				return product;
//			}
//		}
//		return null;
//	}
//
//	private static void displayProducts(Product[] allProducts) {
//		System.out.println("Available Products:");
//		for (Product product : allProducts) {
//			System.out.printf("Serial Number: %d, Name: %s, Price: %.2f, Category: %s%n", product.getSerialNumber(),
//					product.getName(), product.getTotalPrice(), product.getCategory());
//		}
//	}
//
//	private static int readSerialNumberInput(String prompt) {
//		int serialNumber;
//		while (true) {
//			System.out.print(prompt);
//			try {
//				serialNumber = scanner.nextInt();
//				scanner.nextLine();
//				break;
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input. Please enter a valid serial number.");
//				scanner.nextLine();
//			}
//		}
//		return serialNumber;
//	}
//
//	private static Product[] getAllProducts() {
//		List<Product> allProducts = new ArrayList<>();
//
//		for (Seller seller : system.getSellers()) {
//			Product[] sellerProducts = seller.getProducts();
//			if (sellerProducts != null) {
//				Collections.addAll(allProducts, sellerProducts);
//			}
//		}
//
//		return allProducts.toArray(new Product[0]);
//	}
//
//	private static Product[] addToArray(Product[] array, Product[] productsToAdd) {
//		Product[] newArray = new Product[array.length + productsToAdd.length];
//		System.arraycopy(array, 0, newArray, 0, array.length);
//		System.arraycopy(productsToAdd, 0, newArray, array.length, productsToAdd.length);
//		return newArray;
//	}
//
//	private static Product findProductBySerialNumber(int serialNumber) {
//		for (Seller seller : system.getSellers()) {
//			if (seller != null) {
//				for (Product product : seller.getProducts()) {
//					if (product.getSerialNumber() == serialNumber) {
//						return product;
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	private static void checkoutBuyerCart() throws EmptyCartException {
//		System.out.print("Enter buyer username: ");
//		String buyerUsername = scanner.nextLine();
//		Buyer buyer = system.findBuyerByUsername(buyerUsername);
//		if (buyer == null) {
//			System.out.println("Buyer not found.");
//			return;
//		}
//		Cart cart = buyer.getCart();
//		double totalPrice = cart.getTotalPrice();
//		System.out.printf("Total price: %.2f%n", totalPrice);
//		if (cart.isEmpty()) {
//			throw new EmptyCartException("Cannot place order. Cart is empty.");
//		}
//		System.out.print("Proceed with checkout? (yes/no): ");
//		if (scanner.nextLine().equalsIgnoreCase("yes")) {
//			buyer.placeOrder();
//			System.out.println("Order placed successfully.");
//		} else {
//			System.out.println("Checkout cancelled.");
//		}
//		LocalDate currentDate = LocalDate.now();
//		System.out.printf("Current Date: %s%n", currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//	}
//
//	private static void displayBuyers() {
//		Collection<Buyer> buyerCollection = system.getBuyers();
//
//		if (buyerCollection.isEmpty()) {
//			System.out.println("No buyers found.");
//			return;
//		}
//
//		List<Buyer> buyers = new ArrayList<>(buyerCollection);
//		buyers.sort(Comparator.comparing(Buyer::getUsername)); // מיון אלפביתי
//
//		System.out.println("All Buyers Details:");
//		for (Buyer buyer : buyers) {
//			System.out.println("Username: " + buyer.getUsername());
//			System.out.println("Address: " + buyer.getAddress());
//			System.out.println("Current Cart Total Price: " + buyer.getCart().getTotalPrice());
//			System.out.println("Order History:");
//			for (Order order : buyer.getOrderHistory()) {
//				System.out.println("- Order Date: " + order.getOrderDate());
//				System.out.println("  Shipping Address: " + order.getShippingAddress());
//				System.out.println("  Total Price: " + order.getTotalPrice());
//				System.out.println("  Products:");
//				for (Product product : order.getProducts()) {
//					System.out.println("    " + product.getName() + " - " + product.getPrice());
//				}
//			}
//			System.out.println("----------------------------");
//		}
//	}
//
//	private static void displaySellers() {
//		Collection<Seller> sellerCollection = system.getSellers();
//
//		if (sellerCollection.isEmpty()) {
//			System.out.println("No sellers found.");
//			return;
//		}
//
//		List<Seller> sellers = new ArrayList<>(sellerCollection);
//		sellers.sort((s1, s2) -> Integer.compare(s2.getProducts().length, s1.getProducts().length)); // descending
//
//		System.out.println("All Sellers Details:");
//		for (Seller seller : sellers) {
//			System.out.println("Username: " + seller.getUsername());
//			System.out.println("Number of Products for Sale: " + seller.getProducts().length);
//			System.out.println("Products for Sale:");
//			for (Product product : seller.getProducts()) {
//				System.out.println("- " + product.getName() + " - " + product.getPrice());
//			}
//			System.out.println("----------------------------");
//		}
//	}
//
//
//	private static void displayProductsByCategory() {
//		Category category = readCategoryInput("Enter category (CHILDREN, ELECTRONICS, OFFICE, CLOTHING): ");
//		Product[] products = system.getProductsByCategory(category);
//		if (products.length == 0) {
//			System.out.println("No products found in this category.");
//			return;
//		}
//		System.out.println("Products in category " + category + ":");
//		for (Product product : products) {
//			System.out.printf("%d. %s (%.2f)%n", product.getSerialNumber(), product.getName(), product.getTotalPrice());
//		}
//	}
//
//	private static void createNewCartFromHistory() {
//		System.out.print("Enter buyer username: ");
//		String buyerUsername = scanner.nextLine();
//		Buyer buyer = system.findBuyerByUsername(buyerUsername);
//		if (buyer == null) {
//			System.out.println("Buyer not found.");
//			return;
//		}
//		if (!buyer.getCart().isEmpty()) {
//			System.out.print("Current cart is not empty. Replace it? (yes/no): ");
//			if (!scanner.nextLine().equalsIgnoreCase("yes")) {
//				System.out.println("Operation cancelled.");
//				return;
//			}
//		}
//		Order[] orderHistory = buyer.getOrderHistory();
//		if (orderHistory.length == 0) {
//			System.out.println("No carts in history.");
//			return;
//		}
//		System.out.println("Select a cart from history:");
//		for (int i = 0; i < orderHistory.length; i++) {
//			System.out.printf("%d. Order Date: %s, Total Price: %.2f%n", i, orderHistory[i].getOrderDate(),
//					orderHistory[i].getTotalPrice());
//		}
//		int historyIndex = readHistoryIndexInput("Enter the number of the cart to copy: ", orderHistory.length);
//		buyer.replaceCartWithHistory(historyIndex);
//		System.out.println("New cart created from history.");
//	}
//
//	private static int readHistoryIndexInput(String prompt, int maxIndex) {
//		int historyIndex;
//		while (true) {
//			System.out.print(prompt);
//			try {
//				historyIndex = scanner.nextInt();
//				scanner.nextLine();
//				if (historyIndex >= 0 && historyIndex < maxIndex) {
//					break;
//				} else {
//					System.out.println("Invalid selection. Please choose a valid number.");
//				}
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input. Please enter a valid number.");
//				scanner.nextLine();
//			}
//		}
//		return historyIndex;
//	}
//
//
//}
//import java.sql.*;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class Main {
//	private static ECommerceSystem system = new ECommerceSystem();
//	private static Scanner scanner = new Scanner(System.in);
//
//	public static void main(String[] args) {
//		try (Connection conn = DBUtil.connect()) {
//			system.loadBuyersFromDB(conn);
//			system.loadSellersFromDB(conn);
//
//			int choice;
//			do {
//				printMenu();
//				while (!scanner.hasNextInt()) {
//					System.out.println("Invalid input. Please enter a number.");
//					scanner.next();
//				}
//				choice = scanner.nextInt();
//				scanner.nextLine();
//
//				switch (choice) {
//					case 1 -> addSeller(conn);
//					case 2 -> addBuyer(conn);
//					case 3 -> addProductToSeller(conn);
//					case 0 -> System.out.println("Exiting...");
//					default -> System.out.println("Invalid choice. Try again.");
//				}
//			} while (choice != 0);
//
//		} catch (SQLException e) {
//			System.out.println("Database connection failed.");
//			e.printStackTrace();
//		}
//	}
//
//	private static void printMenu() {
//		System.out.println("\nMenu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - Add Seller");
//		System.out.println("2 - Add Buyer");
//		System.out.println("3 - Add Product to Seller");
//		System.out.print("Enter your choice: ");
//	}
//
//	private static void addBuyer(Connection conn) {
//		System.out.print("Enter buyer username: ");
//		String username = scanner.nextLine();
//		if (system.findBuyerByUsername(username) != null) {
//			System.out.println("Username already exists. Try another.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//		System.out.print("Enter address: ");
//		String address = scanner.nextLine();
//
//		String sql = "INSERT INTO Users (username, password, address, userType) VALUES (?, ?, ?, 'buyer')";
//		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//			stmt.setString(1, username);
//			stmt.setString(2, password);
//			stmt.setString(3, address);
//			stmt.executeUpdate();
//
//			ResultSet keys = stmt.getGeneratedKeys();
//			if (keys.next()) {
//				int userId = keys.getInt(1);
//				Buyer buyer = new Buyer(username, password, userId, address);
//				system.addBuyer(buyer);
//				System.out.println("Buyer added successfully.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("❌ Failed to add buyer.");
//		}
//	}
//
//	private static void addSeller(Connection conn) {
//		System.out.print("Enter seller username: ");
//		String username = scanner.nextLine();
//		if (system.findSellerByUsername(username) != null) {
//			System.out.println("Username already exists. Try another.");
//			return;
//		}
//		System.out.print("Enter password: ");
//		String password = scanner.nextLine();
//
//		String sql = "INSERT INTO Users (username, password, userType) VALUES (?, ?, 'seller')";
//		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//			stmt.setString(1, username);
//			stmt.setString(2, password);
//			stmt.executeUpdate();
//
//			ResultSet keys = stmt.getGeneratedKeys();
//			if (keys.next()) {
//				int userId = keys.getInt(1);
//				Seller seller = new Seller(username, password, userId);
//				system.addSeller(seller);
//				System.out.println("Seller added successfully.");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println("❌ Failed to add seller.");
//		}
//	}
//
//	private static void addProductToSeller(Connection conn) {
//		System.out.print("Enter seller username: ");
//		String sellerUsername = scanner.nextLine();
//		Seller seller = system.findSellerByUsername(sellerUsername);
//		if (seller == null) {
//			System.out.println("Seller not found.");
//			return;
//		}
//		System.out.print("Enter product name: ");
//		String name = scanner.nextLine();
//		double price = readDoubleInput("Enter product price: ");
//		Category category = readCategoryInput("Enter category (CHILDREN, ELECTRONICS, OFFICE, CLOTHING): ");
//		boolean specialPackaging = readBooleanInput("Does the product require special packaging? (yes/no): ");
//		double packagingCost = specialPackaging ? readDoubleInput("Enter packaging cost: ") : 0;
//
//		Product product = new Product(name, price, category.toString(), specialPackaging, packagingCost);
//		seller.addProduct(product, conn);
//		System.out.println("Product added successfully.");
//	}
//
//	private static double readDoubleInput(String prompt) {
//		double input;
//		while (true) {
//			System.out.print(prompt);
//			try {
//				input = scanner.nextDouble();
//				scanner.nextLine();
//				break;
//			} catch (InputMismatchException e) {
//				System.out.println("Invalid input. Please enter a valid number.");
//				scanner.nextLine();
//			}
//		}
//		return input;
//	}
//
//	private static Category readCategoryInput(String prompt) {
//		Category category;
//		while (true) {
//			System.out.print(prompt);
//			try {
//				category = Category.valueOf(scanner.nextLine().toUpperCase());
//				break;
//			} catch (IllegalArgumentException e) {
//				System.out.println("Invalid category. Please enter one of CHILDREN, ELECTRONICS, OFFICE, CLOTHING.");
//			}
//		}
//		return category;
//	}
//
//	private static boolean readBooleanInput(String prompt) {
//		System.out.print(prompt);
//		return scanner.nextLine().equalsIgnoreCase("yes");
//	}
////}
//import java.sql.*;
//import java.util.*;
//
//public class Main {
//	private static ECommerceSystem system = new ECommerceSystem();
//	private static Scanner scanner = new Scanner(System.in);
//
//	public static void main(String[] args) {
//		try (Connection conn = DBUtil.connect()) {
//			system.loadBuyersFromDB(conn);
//			system.loadSellersFromDB(conn);
//
//			System.out.print("Are you an admin? (yes/no): ");
//			String role = scanner.nextLine().trim().toLowerCase();
//
//			if (role.equals("yes") || role.equals("y")) {
//				runAdminMenu(conn);
//			} else {
//				runUserMenu(conn);
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Database connection failed.");
//			e.printStackTrace();
//		}
//	}
//
//	private static void runAdminMenu(Connection conn) {
//		int choice;
//		do {
//			printAdminMenu();
//			while (!scanner.hasNextInt()) {
//				System.out.println("Invalid input. Please enter a number.");
//				scanner.next();
//			}
//			choice = scanner.nextInt();
//			scanner.nextLine();
//
//			switch (choice) {
//				case 1 -> system.handleAddSeller(conn, scanner);
//				case 2 -> system.handleAddBuyer(conn, scanner);
//				case 3 -> system.handleAddProductToSeller(conn, scanner);
//				case 4 -> system.handleViewProductsByCategory(conn, scanner);
//				case 0 -> System.out.println("Exiting admin menu...");
//				default -> System.out.println("Invalid choice. Try again.");
//			}
//		} while (choice != 0);
//	}
//
//	private static void runUserMenu(Connection conn) {
//		int choice;
//		do {
//			printUserMenu();
//			while (!scanner.hasNextInt()) {
//				System.out.println("Invalid input. Please enter a number.");
//				scanner.next();
//			}
//			choice = scanner.nextInt();
//			scanner.nextLine();
//
//			switch (choice) {
//				case 1 -> system.handleViewProductsByCategory(conn, scanner);
//				case 2 -> system.handleAddToCart(conn, scanner);
//				case 3 -> system.handleViewCart(scanner);
//				case 4 -> system.handleViewProductDetails(conn, scanner);
//				case 5 -> system.handleViewCartTotal(scanner);
//				case 6 -> system.handleCompletePurchase(conn, scanner);
//				case 7 -> system.handleViewOrderHistory(conn, scanner);
//				case 0 -> System.out.println("Exiting user menu...");
//				default -> System.out.println("Invalid choice. Try again.");
//			}
//		} while (choice != 0);
//	}
//
//	private static void printAdminMenu() {
//		System.out.println("\nAdmin Menu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - Add Seller");
//		System.out.println("2 - Add Buyer");
//		System.out.println("3 - Add Product to Seller");
//		System.out.println("4 - View Products by Category");
//		System.out.print("Enter your choice: ");
//	}
//
//	private static void printUserMenu() {
//		System.out.println("\nUser Menu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - View Products by Category");
//		System.out.println("2 - Add Product to Cart");
//		System.out.println("3 - View Cart");
//		System.out.println("4 - View Product Details");
//		System.out.println("5 - View Cart Total Price");
//		System.out.println("6 - Complete Purchase");
//		System.out.println("7 - View Order History");
//		System.out.print("Enter your choice: ");
//	}
//}
//
//
//import java.sql.*;
//import java.util.*;
//
//public class Main {
//	private static ECommerceSystem system = new ECommerceSystem();
//	private static Admin admin = new Admin(system); // ✅ יצירת Admin
//	private static Scanner scanner = new Scanner(System.in);
//
//	public static void main(String[] args) {
//		try (Connection conn = DBUtil.connect()) {
//			System.out.print("Are you an admin? (yes/no): ");
//			String role = scanner.nextLine().trim().toLowerCase();
//
//			if (role.equals("yes") || role.equals("y")) {
//				runAdminMenu(conn);
//			} else {
//				runUserMenu(conn);
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Database connection failed.");
//			e.printStackTrace();
//		}
//	}
//
//	private static void runAdminMenu(Connection conn) {
//		int choice;
//		do {
//			printAdminMenu();
//			while (!scanner.hasNextInt()) {
//				System.out.println("Invalid input. Please enter a number.");
//				scanner.next();
//			}
//			choice = scanner.nextInt();
//			scanner.nextLine();
//
//			switch (choice) {
//				case 1 -> admin.handleAddSeller(conn, scanner);
//				case 2 -> admin.handleAddBuyer(conn, scanner);
//				case 3 -> admin.handleAddProductToSeller(conn, scanner);
//				case 4 -> admin.handleShowProductsByCategory(conn, scanner);
//				case 5 -> admin.showAllBuyers(conn);
//				case 6 -> admin.showAllSellers(conn);
//				case 0 -> System.out.println("Exiting admin menu...");
//				default -> System.out.println("Invalid choice. Try again.");
//			}
//		} while (choice != 0);
//	}
//
//	private static void runUserMenu(Connection conn) {
//		int choice;
//		do {
//			printUserMenu();
//			while (!scanner.hasNextInt()) {
//				System.out.println("Invalid input. Please enter a number.");
//				scanner.next();
//			}
//			choice = scanner.nextInt();
//			scanner.nextLine();
//
//			switch (choice) {
//				case 1 -> system.handleShowProductsByCategory(conn, scanner);
//				case 2 -> system.handleAddToCart(conn, scanner);
//				case 3 -> system.handleViewCart(scanner);
//				case 4 -> system.handleViewProductDetails(conn, scanner);
//				case 5 -> system.handleViewCartTotal(scanner);
//				case 6 -> system.handleCompletePurchase(conn, scanner);
//				case 7 -> system.handleViewOrderHistory(conn, scanner);
//				case 0 -> System.out.println("Exiting user menu...");
//				default -> System.out.println("Invalid choice. Try again.");
//			}
//		} while (choice != 0);
//	}
//
//	private static void printAdminMenu() {
//		System.out.println("\nAdmin Menu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - Add Seller");
//		System.out.println("2 - Add Buyer");
//		System.out.println("3 - Add Product to Seller");
//		System.out.println("4 - View Products by Category");
//		System.out.println("5 - Show All Buyers");
//		System.out.println("6 - Show All Sellers");
//		System.out.print("Enter your choice: ");
//	}
//
//	private static void printUserMenu() {
//		System.out.println("\nUser Menu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - View Products by Category");
//		System.out.println("2 - Add Product to Cart");
//		System.out.println("3 - View Cart");
//		System.out.println("4 - View Product Details");
//		System.out.println("5 - View Cart Total Price");
//		System.out.println("6 - Complete Purchase");
//		System.out.println("7 - View Order History");
//		System.out.print("Enter your choice: ");
//	}
//}
//
//import java.sql.*;
//import java.util.*;
//
//public class Main {
//	private static ECommerceSystem system = new ECommerceSystem();
//	private static Admin admin = new Admin(system);
//	private static Scanner scanner = new Scanner(System.in);
//
//	public static void main(String[] args) {
//		try (Connection conn = DBUtil.connect()) {
//			System.out.print("Are you an admin? (yes/no): ");
//			String role = scanner.nextLine().trim().toLowerCase();
//
//			if (role.equals("yes") || role.equals("y")) {
//				runAdminMenu(conn);
//			} else {
//				runUserMenu(conn); // בשלב הבא תוכל לפצל גם לתפריט קונה ומוכר
//			}
//		} catch (SQLException e) {
//			System.out.println("Database connection failed.");
//			e.printStackTrace();
//		}
//	}
//
//	private static void runAdminMenu(Connection conn) {
//		int choice;
//		do {
//			printAdminMenu();
//			while (!scanner.hasNextInt()) {
//				System.out.println("Invalid input. Please enter a number.");
//				scanner.next();
//			}
//			choice = scanner.nextInt();
//			scanner.nextLine();
//
//			switch (choice) {
//				case 1 -> admin.handleAddSeller(conn, scanner);
//				case 2 -> admin.handleAddBuyer(conn, scanner);
//				case 3 -> admin.handleAddProductToSeller(conn, scanner);
//				case 4 -> admin.handleShowProductsByCategory(conn, scanner);
//				case 5 -> admin.showAllBuyers(conn);
//				case 6 -> admin.showAllSellers(conn);
//				case 7 -> admin.handleDeleteSeller(conn, scanner);
//				case 8 -> admin.handleDeleteBuyer(conn, scanner);
//				case 9 -> admin.handleShowAllProducts(conn);
//				case 10 -> admin.handleShowAllCategories(conn);
//				case 11 -> admin.handleAddCategory(conn, scanner);
//				case 12 -> admin.handleDeleteCategory(conn, scanner);
//				case 13 -> admin.handleShowAllOrders(conn);
//				case 14 -> admin.handleIncomeReport(conn);
//				case 0 -> System.out.println("Exiting admin menu...");
//				default -> System.out.println("Invalid choice. Try again.");
//			}
//		} while (choice != 0);
//	}
//
//	private static void runUserMenu(Connection conn) {
//		// נשאיר את זה כמו שהיה (שלב הבא נפרק למוכר/קונה)
//		int choice;
//		do {
//			printUserMenu();
//			while (!scanner.hasNextInt()) {
//				System.out.println("Invalid input. Please enter a number.");
//				scanner.next();
//			}
//			choice = scanner.nextInt();
//			scanner.nextLine();
//
//			switch (choice) {
//				case 1 -> system.handleShowProductsByCategory(conn, scanner);
//				case 2 -> system.handleAddToCart(conn, scanner, buyer);
//				case 3 -> system.handleDisplayCart(conn, buyer);
//				case 4 -> system.handleViewProductDetails(conn, scanner); // תלוי אם צריך buyer
//				case 5 -> system.handleCartTotal(conn, buyer);
//				case 6 -> system.handlePurchase(conn, buyer);
//				case 7 -> system.handleOrderHistory(conn, buyer);
//				case 0 -> System.out.println("Exiting user menu...");
//				default -> System.out.println("Invalid choice. Try again.");
//			}
//		} while (choice != 0);
//	}
//
//	private static void printAdminMenu() {
//		System.out.println("\n🛠 Admin Menu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - Add Seller");
//		System.out.println("2 - Add Buyer");
//		System.out.println("3 - Add Product to Seller");
//		System.out.println("4 - View Products by Category");
//		System.out.println("5 - Show All Buyers");
//		System.out.println("6 - Show All Sellers");
//		System.out.println("7 - Delete Seller by Username");
//		System.out.println("8 - Delete Buyer by Username");
//		System.out.println("9 - Show All Products");
//		System.out.println("10 - Show All Categories");
//		System.out.println("11 - Add New Category");
//		System.out.println("12 - Delete Category");
//		System.out.println("13 - View All Orders");
//		System.out.println("14 - View Income Report");
//		System.out.print("Enter your choice: ");
//	}
//
//	private static void printUserMenu() {
//		System.out.println("\nUser Menu:");
//		System.out.println("0 - Exit");
//		System.out.println("1 - View Products by Category");
//		System.out.println("2 - Add Product to Cart");
//		System.out.println("3 - View Cart");
//		System.out.println("4 - View Product Details");
//		System.out.println("5 - View Cart Total Price");
//		System.out.println("6 - Complete Purchase");
//		System.out.println("7 - View Order History");
//		System.out.print("Enter your choice: ");
//	}
//}

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	private static ECommerceSystem system = new ECommerceSystem();
	private static Admin admin = new Admin(system);
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try (Connection conn = DBUtil.connect()) {
			System.out.print("Are you an admin? (yes/no): ");
			String role = scanner.nextLine().trim().toLowerCase();

			if (role.equals("yes") || role.equals("y")) {
				runAdminMenu(conn);
			} else {
				System.out.print("Are you a buyer or seller? ");
				String type = scanner.nextLine().trim().toLowerCase();

				if (type.equals("buyer")) {
					Buyer buyer = admin.handleAddBuyer(conn, scanner);
					runBuyerMenu(conn, buyer);

				} else if (type.equals("seller")) {
					Seller seller = admin.handleAddSeller(conn, scanner);
					runSellerMenu(conn, seller);

				} else {
					System.out.println("Invalid type. Exiting...");
				}
			}
		} catch (SQLException e) {
			System.out.println("Database connection failed.");
			e.printStackTrace();
		}
	}

	private static void runAdminMenu(Connection conn) {
		int choice;
		do {
			printAdminMenu();
			while (!scanner.hasNextInt()) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.next();
			}
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> admin.handleAddSeller(conn, scanner);
				case 2 -> admin.handleAddBuyer(conn, scanner);
				case 3 -> admin.handleAddProductToSeller(conn, scanner);
				case 4 -> admin.handleShowProductsByCategory(conn, scanner);
				case 5 -> admin.showAllBuyers(conn);
				case 6 -> admin.showAllSellers(conn);
				case 7 -> admin.handleDeleteSeller(conn, scanner);
				case 8 -> admin.handleDeleteBuyer(conn, scanner);
				case 9 -> admin.handleShowAllProducts(conn);
				case 10 -> admin.handleShowAllCategories(conn);
				case 11 -> admin.handleAddCategory(conn, scanner);
				case 12 -> admin.handleDeleteCategory(conn, scanner);
				case 13 -> admin.handleShowAllOrders(conn);
				case 14 -> admin.handleIncomeReport(conn);
				case 0 -> System.out.println("Exiting admin menu...");
				default -> System.out.println("Invalid choice. Try again.");
			}
		} while (choice != 0);
	}

	private static void runBuyerMenu(Connection conn, Buyer buyer) {
		int choice;
		do {
			printUserMenu();
			while (!scanner.hasNextInt()) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.next();
			}
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> system.handleShowProductsByCategory(conn, scanner);
				case 2 -> system.handleAddToCart(conn, scanner, buyer);
				case 3 -> system.handleDisplayCart(conn, buyer);
				case 4 -> system.handleViewProductDetails(conn, scanner);
				case 5 -> system.handleCartTotal(conn, buyer);
				case 6 -> system.handlePurchase(conn, buyer);
				case 7 -> system.handleOrderHistory(conn, buyer);
				case 0 -> System.out.println("Exiting user menu...");
				default -> System.out.println("Invalid choice. Try again.");
			}
		} while (choice != 0);
	}

	private static void runSellerMenu(Connection conn, Seller seller) {
		System.out.println("👨‍💼 Welcome, seller " + seller.getUsername() + "! (custom seller menu coming soon...)\n");
		// future seller menu actions
	}

	private static void printAdminMenu() {
		System.out.println("\n🛠 Admin Menu:");
		System.out.println("0 - Exit");
		System.out.println("1 - Add Seller");
		System.out.println("2 - Add Buyer");
		System.out.println("3 - Add Product to Seller");
		System.out.println("4 - View Products by Category");
		System.out.println("5 - Show All Buyers");
		System.out.println("6 - Show All Sellers");
		System.out.println("7 - Delete Seller by Username");
		System.out.println("8 - Delete Buyer by Username");
		System.out.println("9 - Show All Products");
		System.out.println("10 - Show All Categories");
		System.out.println("11 - Add New Category");
		System.out.println("12 - Delete Category");
		System.out.println("13 - View All Orders");
		System.out.println("14 - View Income Report");
		System.out.print("Enter your choice: ");
	}

	private static void printUserMenu() {
		System.out.println("\nUser Menu:");
		System.out.println("0 - Exit");
		System.out.println("1 - View Products by Category");
		System.out.println("2 - Add Product to Cart");
		System.out.println("3 - View Cart");
		System.out.println("4 - View Product Details");
		System.out.println("5 - View Cart Total Price");
		System.out.println("6 - Complete Purchase");
		System.out.println("7 - View Order History");
		System.out.print("Enter your choice: ");
	}
}
