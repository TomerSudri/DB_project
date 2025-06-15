
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Entry point for the E-Commerce application.
 *
 * Handles user role selection (admin, buyer, seller),
 * initializes system components, and routes user interaction to
 * the correct menu loop.
 */
public class Main {
	private static ECommerceSystem system = new ECommerceSystem();
	private static Admin admin = new Admin(system);
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		// Connect to the database
		try (Connection conn = DBUtil.connect()) {
			System.out.print("Are you an admin? (yes/no): ");
			String role = scanner.nextLine().trim().toLowerCase();

			if (role.equals("yes") || role.equals("y")) {
				runAdminMenu(conn);
			} else {
				System.out.print("Are you a buyer or seller? ");
				String type = scanner.nextLine().trim().toLowerCase();

				System.out.print("Are you a new user? (yes/no): ");
				String isNew = scanner.nextLine().trim().toLowerCase();

				if (type.equals("buyer")) {
					Buyer buyer;
					if (isNew.equals("yes")) {
						buyer = admin.handleAddBuyer(conn, scanner);
					} else {
						buyer = system.loginBuyer(conn, scanner);
					}
					if (buyer != null) {
						runBuyerMenu(conn, buyer);
					} else {
						System.out.println("Failed to authenticate buyer.");
					}

				} else if (type.equals("seller")) {
					Seller seller;
					if (isNew.equals("yes")) {
						seller = admin.handleAddSeller(conn, scanner);
					} else {
						seller = system.loginSeller(conn, scanner);
					}
					if (seller != null) {
						runSellerMenu(conn, seller);
					} else {
						System.out.println("Failed to authenticate seller.");
					}

				} else {
					System.out.println("Invalid type. Exiting...");
				}
			}
		} catch (SQLException e) {
			System.out.println("Database connection failed.");
			e.printStackTrace();
		}
	}

	/**
	 * Displays the admin menu and handles admin choices.
	 * @param conn active SQL connection
	 */
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

	/**
	 * Displays the buyer menu and handles buyer actions.
	 * @param conn active SQL connection
	 * @param buyer the authenticated buyer
	 */
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

	/**
	 * Displays the seller menu and handles seller actions.
	 * @param conn active SQL connection
	 * @param seller the authenticated seller
	 */
	private static void runSellerMenu(Connection conn, Seller seller) {
		int choice;
		do {
			printSellerMenu();
			while (!scanner.hasNextInt()) {
				System.out.println("Invalid input. Please enter a number.");
				scanner.next();
			}
			choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1 -> system.handleShowSellerProducts(conn, seller);
				case 2 -> system.handleAddProductBySeller(conn, scanner, seller);
				case 3 -> system.handleSellerRevenue(conn, seller);
				case 4 -> system.handleSellerOrders(conn, seller);
				case 0 -> System.out.println("Exiting seller menu...");
				default -> System.out.println("Invalid choice. Try again.");
			}
		} while (choice != 0);
	}

	/**
	 * Prints the options available to an admin.
	 */
	private static void printAdminMenu() {
		System.out.println("\n Admin Menu:");
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

	/**
	 * Prints the options available to a buyer.
	 */
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

	/**
	 * Prints the options available to a seller.
	 */
	private static void printSellerMenu() {
		System.out.println("\n Seller Menu:");
		System.out.println("0 - Exit");
		System.out.println("1 - View My Products");
		System.out.println("2 - Add New Product");
		System.out.println("3 - View My Revenue");
		System.out.println("4 - View Orders of My Products");
		System.out.print("Enter your choice: ");
	}
}
