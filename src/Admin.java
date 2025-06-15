//import java.sql.Connection;
//import java.util.Scanner;
//
//public class Admin {
//    private ECommerceSystem system;
//
//    public Admin(ECommerceSystem system) {
//        this.system = system;
//    }
//
//    public Seller handleAddSeller(Connection conn, Scanner scanner) {
//        return system.handleAddSeller(conn, scanner);
//    }
//
//    public Buyer handleAddBuyer(Connection conn, Scanner scanner) {
//        return system.handleAddBuyer(conn, scanner);
//    }
//
//    public void handleAddProductToSeller(Connection conn, Scanner scanner) {
//        system.handleAddProductToSeller(conn, scanner);
//    }
//
//    public void handleShowProductsByCategory(Connection conn, Scanner scanner) {
//        system.handleShowProductsByCategory(conn, scanner);
//    }
//
//    public void showAllBuyers(Connection conn) {
//        for (Buyer b : system.getBuyers(conn)) {
//            System.out.println(b);
//        }
//    }
//
//    public void showAllSellers(Connection conn) {
//        for (Seller s : system.getSellers(conn)) {
//            System.out.println(s);
//        }
//    }
//
//    public void handleDeleteSeller(Connection conn, Scanner scanner) {
//        system.handleDeleteSeller(conn, scanner);
//    }
//
//    public void handleDeleteBuyer(Connection conn, Scanner scanner) {
//        system.handleDeleteBuyer(conn, scanner);
//    }
//
//    public void handleShowAllProducts(Connection conn) {
//        system.handleShowAllProducts(conn);
//    }
//
//    public void handleShowAllCategories(Connection conn) {
//        system.handleShowAllCategories(conn);
//    }
//
//    public void handleAddCategory(Connection conn, Scanner scanner) {
//        system.handleAddCategory(conn, scanner);
//    }
//
//    public void handleDeleteCategory(Connection conn, Scanner scanner) {
//        system.handleDeleteCategory(conn, scanner);
//    }
//
//    public void handleShowAllOrders(Connection conn) {
//        system.handleShowAllOrders(conn);
//    }
//
//    public void handleIncomeReport(Connection conn) {
//        system.handleIncomeReport(conn);
//    }
//}

import java.sql.Connection;
import java.util.Scanner;

/**
 * Admin class acts as a controller for administrative operations in the ECommerceSystem.
 * It delegates the actual work to the ECommerceSystem instance passed to it.
 *
 * Main responsibilities:
 * - Add/Delete Buyers and Sellers
 * - Manage Products and Categories
 * - View system-wide reports like income or order listings
 */
public class Admin {
    private ECommerceSystem system;

    /**
     * Constructor for Admin.
     * @param system the instance of ECommerceSystem to delegate tasks to
     */
    public Admin(ECommerceSystem system) {
        this.system = system;
    }

    /**
     * Adds a new seller to the system.
     * @param conn database connection
     * @param scanner input scanner
     * @return the created Seller
     */
    public Seller handleAddSeller(Connection conn, Scanner scanner) {
        return system.handleAddSeller(conn, scanner);
    }

    /**
     * Adds a new buyer to the system.
     * @param conn database connection
     * @param scanner input scanner
     * @return the created Buyer
     */
    public Buyer handleAddBuyer(Connection conn, Scanner scanner) {
        return system.handleAddBuyer(conn, scanner);
    }

    /**
     * Adds a product to a seller's list.
     * @param conn database connection
     * @param scanner input scanner
     */
    public void handleAddProductToSeller(Connection conn, Scanner scanner) {
        system.handleAddProductToSeller(conn, scanner);
    }

    /**
     * Shows all products filtered by category.
     * @param conn database connection
     * @param scanner input scanner
     */
    public void handleShowProductsByCategory(Connection conn, Scanner scanner) {
        system.handleShowProductsByCategory(conn, scanner);
    }

    /**
     * Prints all buyers in the system.
     * @param conn database connection
     */
    public void showAllBuyers(Connection conn) {
        for (Buyer b : system.getBuyers(conn)) {
            System.out.println(b);
        }
    }

    /**
     * Prints all sellers in the system.
     * @param conn database connection
     */
    public void showAllSellers(Connection conn) {
        for (Seller s : system.getSellers(conn)) {
            System.out.println(s);
        }
    }

    /**
     * Deletes a seller from the system.
     * @param conn database connection
     * @param scanner input scanner
     */
    public void handleDeleteSeller(Connection conn, Scanner scanner) {
        system.handleDeleteSeller(conn, scanner);
    }

    /**
     * Deletes a buyer from the system.
     * @param conn database connection
     * @param scanner input scanner
     */
    public void handleDeleteBuyer(Connection conn, Scanner scanner) {
        system.handleDeleteBuyer(conn, scanner);
    }

    /**
     * Shows all products available in the system.
     * @param conn database connection
     */
    public void handleShowAllProducts(Connection conn) {
        system.handleShowAllProducts(conn);
    }

    /**
     * Shows all product categories available.
     * @param conn database connection
     */
    public void handleShowAllCategories(Connection conn) {
        system.handleShowAllCategories(conn);
    }

    /**
     * Adds a new category to the system.
     * @param conn database connection
     * @param scanner input scanner
     */
    public void handleAddCategory(Connection conn, Scanner scanner) {
        system.handleAddCategory(conn, scanner);
    }

    /**
     * Deletes a category from the system.
     * @param conn database connection
     * @param scanner input scanner
     */
    public void handleDeleteCategory(Connection conn, Scanner scanner) {
        system.handleDeleteCategory(conn, scanner);
    }

    /**
     * Displays all orders made in the system.
     * @param conn database connection
     */
    public void handleShowAllOrders(Connection conn) {
        system.handleShowAllOrders(conn);
    }

    /**
     * Generates and displays an income report for the system.
     * @param conn database connection
     */
    public void handleIncomeReport(Connection conn) {
        system.handleIncomeReport(conn);
    }
}
