
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Seller class represents a user who can sell products.
 * Extends the User class and provides database operations related to product management.
 */
public class Seller extends User {

	/**
	 * Constructor for a seller with a known user ID.
	 *
	 * @param username seller's username
	 * @param password seller's password
	 * @param id unique user ID from the database
	 */
	public Seller(String username, String password, int id) {
		super(username, password, id);
	}

	/**
	 * Retrieves all products listed by the seller from the database.
	 *
	 * @param conn active SQL connection
	 * @return array of Product objects associated with this seller
	 */
	public Product[] getProducts(Connection conn) {
		List<Product> productList = new ArrayList<>();
		String sql = "SELECT * FROM Products WHERE SellerId = ?";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, this.getUserId());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Product product = new Product(
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

	/**
	 * Adds a new product to the database under this seller's ID.
	 *
	 * @param product the product to add
	 * @param conn active SQL connection
	 * @return true if the product was successfully added; false otherwise
	 */
	public boolean addProduct(Product product, Connection conn) {
		String sql = "INSERT INTO Products (SellerId, name, price, category, specialPackaging, packagingCost) " +
				"VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, this.getUserId());
			stmt.setString(2, product.getName());
			stmt.setDouble(3, product.getPrice());
			stmt.setString(4, product.getCategory().toString());
			stmt.setBoolean(5, product.hasSpecialPackaging());
			stmt.setDouble(6, product.getPackagingCost());

			stmt.executeUpdate();
			return true; // success
		} catch (SQLException e) {
			System.out.println("Failed to add product: " + e.getMessage());
			return false; // failure
		}
	}

	/**
	 * Retrieves the total number of products owned by the seller.
	 *
	 * @param conn active SQL connection
	 * @return the number of products
	 */
	public int getProductCount(Connection conn) {
		String sql = "SELECT COUNT(*) FROM Products WHERE SellerId = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, this.getUserId());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Returns a string representation of the seller for display/logging.
	 */
	@Override
	public String toString() {
		return "Seller{" +
				"userId=" + getUserId() +
				", username='" + getUsername() + '\'' +
				'}';
	}
}
