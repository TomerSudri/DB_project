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
//    public void showAllBuyers() {
//        for (Buyer b : system.getBuyers()) {
//            System.out.println(b);
//        }
//    }
//
//    public void showAllSellers() {
//        for (Seller s : system.getSellers()) {
//            System.out.println(s);
//        }
//    }
//
//    public void showProductsByCategory(Connection conn, Scanner scanner) {
//        Category category = system.readCategoryInput(scanner, "Enter category: ");
//        Product[] products = system.getProductsByCategory(conn, category);
//        for (Product p : products) {
//            System.out.println(p);
//        }
//    }
//
//        public void handleAddSeller(Connection conn, Scanner scanner) {
//            system.handleAddSeller(conn, scanner);
//        }
//
//        public void handleAddBuyer(Connection conn, Scanner scanner) {
//            system.handleAddBuyer(conn, scanner);
//        }
//
//        public void handleAddProductToSeller(Connection conn, Scanner scanner) {
//            system.handleAddProductToSeller(conn, scanner);
//        }
//
//        public void handleShowProductsByCategory(Connection conn, Scanner scanner) {
//            system.handleShowProductsByCategory(conn, scanner);
//        }
//    }
//
//
//
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
//    public void showProductsByCategory(Connection conn, Scanner scanner) {
//        Category category = system.readCategoryInput(scanner, "Enter category: ");
//        Product[] products = system.getProductsByCategory(conn, category);
//        for (Product p : products) {
//            System.out.println(p);
//        }
//    }
//
//    public void handleAddSeller(Connection conn, Scanner scanner) {
//        system.handleAddSeller(conn, scanner);
//    }
//
//    public void handleAddBuyer(Connection conn, Scanner scanner) {
//        system.handleAddBuyer(conn, scanner);
//    }
//
//    public void handleAddProductToSeller(Connection conn, Scanner scanner) {
//        system.handleAddProductToSeller(conn, scanner);
//    }
//
//    public void handleShowProductsByCategory(Connection conn, Scanner scanner) {
//        system.handleShowProductsByCategory(conn, scanner);
//    }
//}

import java.sql.Connection;
import java.util.Scanner;

public class Admin {
    private ECommerceSystem system;

    public Admin(ECommerceSystem system) {
        this.system = system;
    }

    public Seller handleAddSeller(Connection conn, Scanner scanner) {
        return system.handleAddSeller(conn, scanner);
    }

    public Buyer handleAddBuyer(Connection conn, Scanner scanner) {
        return system.handleAddBuyer(conn, scanner);
    }

    public void handleAddProductToSeller(Connection conn, Scanner scanner) {
        system.handleAddProductToSeller(conn, scanner);
    }

    public void handleShowProductsByCategory(Connection conn, Scanner scanner) {
        system.handleShowProductsByCategory(conn, scanner);
    }

    public void showAllBuyers(Connection conn) {
        for (Buyer b : system.getBuyers(conn)) {
            System.out.println(b);
        }
    }

    public void showAllSellers(Connection conn) {
        for (Seller s : system.getSellers(conn)) {
            System.out.println(s);
        }
    }

    public void handleDeleteSeller(Connection conn, Scanner scanner) {
        system.handleDeleteSeller(conn, scanner);
    }

    public void handleDeleteBuyer(Connection conn, Scanner scanner) {
        system.handleDeleteBuyer(conn, scanner);
    }

    public void handleShowAllProducts(Connection conn) {
        system.handleShowAllProducts(conn);
    }

    public void handleShowAllCategories(Connection conn) {
        system.handleShowAllCategories(conn);
    }

    public void handleAddCategory(Connection conn, Scanner scanner) {
        system.handleAddCategory(conn, scanner);
    }

    public void handleDeleteCategory(Connection conn, Scanner scanner) {
        system.handleDeleteCategory(conn, scanner);
    }

    public void handleShowAllOrders(Connection conn) {
        system.handleShowAllOrders(conn);
    }

    public void handleIncomeReport(Connection conn) {
        system.handleIncomeReport(conn);
    }
}
